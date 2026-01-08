// Create WebSocket connection (use current host for flexibility)
const wsHost = window.location.hostname || 'localhost';
const ws = new WebSocket(`ws://${wsHost}:8080`);

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
        <p class="student-year">Year: ${student.year}</p>
        <p class="student-formation">Formation: ${student.formation}</p>
    `;
    container.appendChild(studentCard);
}
