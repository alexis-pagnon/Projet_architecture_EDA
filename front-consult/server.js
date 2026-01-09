const express = require("express");
const WebSocket = require("ws");
const { Kafka } = require("kafkajs");

const app = express();
app.use(express.static("public"));

// Kafka setup
const kafka = new Kafka({
  clientId: "front-consult",
  brokers: [process.env.KAFKA_BROKER || "kafka:19092"]
});

const producer = kafka.producer();
const consumer = kafka.consumer({ groupId: "front-consult-group" });

// Redirect root to navigationPage.html
app.get('/', (req, res) => {
  res.redirect('/html/navigationPage.html');
});

const server = app.listen(8080, () =>
  console.log("Front Consult sur http://localhost:8080")
);

// WebSocket
const wss = new WebSocket.Server({ server });

let clients = [];

wss.on("connection", ws => {
  console.log("New WebSocket connection");
  clients.push(ws);
  
  // Handle messages from clients
  ws.on("message", async (message) => {
    try {
      const data = JSON.parse(message);
      console.log("Received from client:", data);
      
      // Call REST API based on action
      if (data.action === 'addStudent') {
        const apiUrl = process.env.API_URL || 'http://ws-template:8800';
        const response = await fetch(`${apiUrl}/service/student/add`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'x-api-key': 'CAFEBABE'
          },
          body: JSON.stringify({
            nom: data.data.lastName,
            prenom: data.data.firstName,
            mail: data.data.email,
            formation: data.data.formation
          })
        });
        
        const result = await response.json();
        ws.send(JSON.stringify({ 
          success: true, 
          message: 'Student added successfully',
          data: result 
        }));
        
      } 
      else if (data.action === 'getStudents') {
        // Send request via Kafka to integration-services
        console.log("Sending getStudents request via Kafka");
        await producer.send({
          topic: 'students-request',
          messages: [
            { key: 'getStudents', value: JSON.stringify({ action: 'getStudents' }) }
          ]
        });
        // Response will come via Kafka consumer and be broadcast to WebSocket clients
        console.log("getStudents request sent, waiting for Kafka response...");
      }
    } catch (error) {
      console.error("Error handling message:", error);
      ws.send(JSON.stringify({ 
        success: false, 
        error: error.message 
      }));
    }
  });
  
  ws.on("close", () => {
    console.log("WebSocket connection closed");
    clients = clients.filter(c => c !== ws);
  });
});

// Kafka consumer - listen for responses
async function initKafka(retryCount = 0, maxRetries = 10) {
  const retryDelay = 5000; // 5 seconds between retries
  
  try {
    await producer.connect();
    console.log("Kafka producer connected");
    
    await consumer.connect();
    console.log("Kafka consumer connected");
    
    const responseTopic = process.env.KAFKA_RESPONSE_TOPIC || "students-response";
    await consumer.subscribe({ topic: responseTopic, fromBeginning: false });
    console.log("Subscribed to topic:", responseTopic);
    
    await consumer.run({
      eachMessage: async ({ topic, partition, message }) => {
        const rawData = message.value.toString();
        const messageKey = message.key ? message.key.toString() : '';
        console.log("Received from Kafka (key:", messageKey, "):", rawData);
        
        try {
          let parsedData = JSON.parse(rawData);
          
          // If it's a getStudents response (array of students), transform it
          if (Array.isArray(parsedData)) {
            parsedData = parsedData.map(student => ({
              firstName: student.prenom,
              lastName: student.nom,
              email: student.mail,
              formation: student.formation
            }));
          }
          
          // Broadcast to all connected WebSocket clients
          clients.forEach(client => {
            if (client.readyState === WebSocket.OPEN) {
              client.send(JSON.stringify(parsedData));
            }
          });
        } catch (e) {
          console.error("Error parsing Kafka message:", e);
        }
      }
    });
    
    console.log("Kafka initialization completed successfully!");
    
  } catch (error) {
    console.error(`Kafka init error (attempt ${retryCount + 1}/${maxRetries}):`, error.message);
    
    if (retryCount < maxRetries - 1) {
      console.log(`Retrying in ${retryDelay / 1000} seconds...`);
      await new Promise(resolve => setTimeout(resolve, retryDelay));
      return initKafka(retryCount + 1, maxRetries);
    } else {
      console.error("Max retries reached. Kafka initialization failed.");
      throw error;
    }
  }
}

initKafka().catch(console.error);
