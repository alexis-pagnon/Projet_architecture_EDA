// Create WebSocket connection (use current host for flexibility)
const wsHost = window.location.hostname || 'localhost';
const ws = new WebSocket(`ws://${wsHost}:8080`);

ws.onopen = () => {
    console.log('WebSocket connection established');
};

ws.onmessage = event => {
    const data = JSON.parse(event.data);
    console.log('Response from server:', data);
    
    if (data.success) {
        alert('Student added successfully!');
    } else {
        alert('Error adding student: ' + (data.error || 'Unknown error'));
    }
};

ws.onerror = error => {
    console.error('WebSocket error:', error);
    alert('Connection error with server');
};

ws.onclose = () => {
    console.log('WebSocket connection closed');
};

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('year').addEventListener('change', function() {
        const year = parseInt(this.value);
        const formationContainer = document.getElementById('formationContainer');
        
        if (year >= 3) {
            formationContainer.style.display = 'block';
        } else {
            formationContainer.style.display = 'none';
        }
    });
});

// Function to handle form submission
function submitForm() {
    // Gather form data
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('email').value;

    // We check required fields
    if(!firstName || !lastName || !email) {
        alert('Please fill in all required fields.');
        return;
    }

    // Determine formation based on year
    const yearSelect = document.getElementById('year');
    const yearValue = parseInt(yearSelect.value);
    const yearText = yearSelect.options[yearSelect.selectedIndex].text;
    
    let temp;
    if(yearValue >= 3) {
        temp = document.getElementById('formation').options[document.getElementById('formation').selectedIndex].text;
    }
    else{
        temp = "SHPI";
    }
    
    const formation = yearText + "-" + temp;
    console.log(`First Name: ${firstName}, Last Name:  ${lastName}, Email: ${email}, Formation: ${formation}`);

    // Send data via WebSocket
    ws.send(JSON.stringify({
        action: 'addStudent',
        header: 'CAFEBABE',
        data: {
            firstName,
            lastName,
            email,
            formation
        }
    }));

    // Clear form after submission
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('email').value = '';
    document.getElementById('year').value = '1';
    document.getElementById('formationContainer').style.display = 'none';
}