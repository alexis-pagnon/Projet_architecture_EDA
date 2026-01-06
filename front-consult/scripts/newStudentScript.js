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

// Function to post data to the server
function postData(url = '', data = {}) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json());
}

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

    // Clear form after submission
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('email').value = '';
    document.getElementById('year').value = '1';
    document.getElementById('formationContainer').style.display = 'none';

    // Sending data
    postData('http://localhost:3000/service/student/add', {firstName, lastName, email, formation})
    .then(data => {
        console.log('Success:', data);
        alert('Student added successfully!');
    })
    .catch((error) => {
        console.error('Error:', error);
        alert('Error adding student.');
    });

}