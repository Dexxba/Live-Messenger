// Function to parse a JWT token and return its payload as a JSON object
function parseJwt(token) {
    const base64Url = token.split('.')[1]; // Get the payload part of the token
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // Replace URL-safe characters
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join('')); // Decode base64 and URI-encode each character

    return JSON.parse(jsonPayload); // Parse and return the JSON payload
}

// Function to extract the user ID from the JWT token stored in localStorage
function getUserIdFromToken() {
    const token = localStorage.getItem('token'); // Get the token from local storage
    if (!token) {
        return null; // Return null if no token is found
    }

    const decodedToken = parseJwt(token); // Decode the token
    return decodedToken.userId; // Return the user ID from the token
}

// Event listener for when the DOM is fully loaded
document.addEventListener('DOMContentLoaded', async function () {
    const token = localStorage.getItem('token'); // Get the token from local storage
    if (!token) {
        alert('User not logged in'); // Alert if user is not logged in
        window.location.href = 'http://localhost:3000'; // Redirect to login page
        return; // Exit the function
    }

    const userId = getUserIdFromToken(); // Get the user ID from the token

    try {
        // Fetch user data from the server
        const response = await fetch(`http://localhost:8080/users/${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}` // Set the Authorization header
            }
        });

        if (response.ok) {
            const user = await response.json(); // Parse the response JSON
            populateUserForm(user); // Populate the form with user data
        } else {
            const error = await response.text(); // Get error text from the response
            console.error('Failed to fetch user data:', error); // Log the error
            alert(`Failed to fetch user data: ${error}`); // Alert the user
        }
    } catch (error) {
        console.error('Failed to fetch user data:', error); // Log unexpected errors
        alert('Failed to fetch user data: An unexpected error occurred'); // Alert the user
    }
});

// Function to populate the user form with fetched user data
function populateUserForm(user) {
    document.getElementById('updateEmail').value = user.email || ''; // Set email value
    document.getElementById('updateFirstName').value = user.firstName || ''; // Set first name value
    document.getElementById('updateLastName').value = user.lastName || ''; // Set last name value
    // Password field should remain empty for security reasons
}

// Event listener for form submission
document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent form submission

    const token = localStorage.getItem('token'); // Get the token from local storage
    if (!token) {
        alert('User not logged in'); // Alert if user is not logged in
        return; // Exit the function
    }

    const userId = getUserIdFromToken(); // Get the user ID from the token
    const password = document.getElementById('updatePassword').value.trim(); // Get password value
    const email = document.getElementById('updateEmail').value.trim(); // Get email value
    const firstName = document.getElementById('updateFirstName').value.trim(); // Get first name value
    const lastName = document.getElementById('updateLastName').value.trim(); // Get last name value

    if (!email || !firstName || !lastName) {
        alert('Please enter all required fields'); // Alert if required fields are missing
        return; // Exit the function
    }

    const userDto = { password, email, firstName, lastName }; // Create a user DTO object

    try {
        // Send a PUT request to update user data
        const response = await fetch(`http://localhost:8080/users/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}` // Set the Authorization header
            },
            body: JSON.stringify(userDto), // Send the user DTO as JSON
        });

        if (response.ok) {
            alert('Profile updated successfully'); // Alert the user
            window.location.href = 'http://localhost:3000/messenger'; // Redirect to messenger page
        } else {
            const error = await response.text(); // Get error text from the response
            console.error('Update failed:', error); // Log the error
            alert(`Update failed: ${error}`); // Alert the user
        }
    } catch (error) {
        console.error('Update failed:', error); // Log unexpected errors
        alert('Update failed: An unexpected error occurred'); // Alert the user
    }
});

// Event listener for the back button click event
document.getElementById('backButton').addEventListener('click', function() {
    backToMessenger(); // Call the backToMessenger function
});

// Function to navigate back to the messenger page
function backToMessenger() {
    window.location.href = 'http://localhost:3000/messenger'; // Redirect to messenger page
}
