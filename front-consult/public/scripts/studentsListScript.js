// Create WebSocket connection (use current host for flexibility)
// Use window.location.host to include the port (important for Kubernetes NodePort)
const wsHost = window.location.host || 'localhost:8080';
const ws = new WebSocket(`ws://${wsHost}`);

// On page load, setup WebSocket listeners
document.addEventListener('DOMContentLoaded', () => {
    const studentsListContainer = document.getElementById('studentsListContainer');
    
    // Display loading message
    studentsListContainer.innerHTML = '<p class="loading">Loading students...</p>';
    
    // WebSocket connection opened
    ws.onopen = () => {
        console.log('WebSocket connection established');
        // Request students list
        ws.send(JSON.stringify({ 
            action: 'getStudents',
            header: 'CAFEBABE'
        }));
    };
    
    // Handle incoming messages
    ws.onmessage = event => {
        const data = JSON.parse(event.data);
        
        // Clear loading message on first data
        if (studentsListContainer.querySelector('.loading')) {
            studentsListContainer.innerHTML = '';
        }
        
        // If data is an array of students
        if (Array.isArray(data)) {
            data.forEach(student => {
                createStudentCard(student, studentsListContainer);
            });
        } 
        // If data is a single student
        else if (data.firstName) {
            createStudentCard(data, studentsListContainer);
        }
    };
    
    // Handle errors
    ws.onerror = error => {
        console.error('WebSocket error:', error);
        studentsListContainer.innerHTML = '<p class="error">Error connecting to server</p>';
    };
    
    // Handle connection close
    ws.onclose = () => {
        console.log('WebSocket connection closed');
    };
});

// Function to create a student card
function createStudentCard(student, container) {
    const studentCard = document.createElement('div');
    studentCard.className = 'student-card';
    studentCard.innerHTML = `
        <h2>${student.firstName} ${student.lastName}</h2>
        <p class="student-email">Email: ${student.email}</p>
        <p class="student-year">Year: ${String(student.formation).substring(0, 2)}</p>
        <p class="student-formation">Formation: ${String(student.formation).substring(3)}</p>
    `;
    container.appendChild(studentCard);
}
