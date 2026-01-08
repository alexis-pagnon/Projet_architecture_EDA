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
        const apiUrl = process.env.API_URL || 'http://ws-template:8800';
        const response = await fetch(`${apiUrl}/service/students`, {
          method: 'GET',
          headers: {
            'x-api-key': 'CAFEBABE'
          }
        });
        
        const students = await response.json();
        
        // Transform to match frontend format
        const transformedStudents = students.map(student => ({
          firstName: student.prenom,
          lastName: student.nom,
          email: student.mail,
          formation: student.formation
        }));
        
        ws.send(JSON.stringify(transformedStudents));
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
async function initKafka() {
  await producer.connect();
  console.log("Kafka producer connected");
  
  await consumer.connect();
  console.log("Kafka consumer connected");
  
  await consumer.subscribe({ topic: "student-responses", fromBeginning: false });
  console.log("Subscribed to topic: student-responses");
  
  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const data = message.value.toString();
      console.log("Received from Kafka:", data);
      
      // Broadcast to all connected WebSocket clients
      clients.forEach(client => {
        if (client.readyState === WebSocket.OPEN) {
          client.send(data);
        }
      });
    }
  });
}

initKafka().catch(console.error);
