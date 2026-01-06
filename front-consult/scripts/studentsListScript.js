// Function to receive data avec HEADER = CAFEBABE 
function getData(url = '') {
    return fetch(url, {
        method: 'GET',
        headers: {
            'HEADER': 'CAFEBABE'
        }
    })
    .then(response => response.json());
}

// On page load, fetch and display the list of students
document.addEventListener('DOMContentLoaded', () => {
    getData('/service/students')
    .then(data => {
        const studentsListContainer = document.getElementById('studentsListContainer');
        // Creation of cards with students info
        data.forEach(student => {
            const studentCard = document.createElement('div');
            studentCard.className = 'student-card';
            studentCard.innerHTML = `
                <h2>${student.firstName} ${student.lastName}</h2>
                <p>Email: ${student.email}</p>
                <p>Year: ${student.year}</p>
                <p>Formation: ${student.formation}</p>
            `;
            container.appendChild(studentCard);
        }
        );
    })
    .catch(error => {
        console.error('Error fetching students data:', error);
    });
});
