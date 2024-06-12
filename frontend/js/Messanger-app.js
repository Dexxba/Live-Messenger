// Event listener for DOMContentLoaded to ensure the DOM is fully loaded before running the script
document.addEventListener('DOMContentLoaded', (event) => {
    connect(); // Connect to the WebSocket server
    loadMessages(); // Load existing messages from the server
    document.getElementById('chat-form').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent form submission
        sendMessage(); // Send the message
    });
});

// Event listener for the send button click event
const sendButton = document.getElementById('send');
sendButton.addEventListener('click', function(event) {
    event.preventDefault(); // Prevent default button action
    sendMessage(); // Send the message
});

// Create a new STOMP client
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws' // WebSocket endpoint
});

// Function to handle successful connection
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame); // Log connection frame
    // Subscribe to the topic and handle incoming messages
    stompClient.subscribe('/topic/public', (messageOutput) => {
        showMessageOutput(JSON.parse(messageOutput.body)); // Display the message
    });
};

// Function to handle WebSocket errors
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error); // Log the error
};

// Function to handle STOMP errors
stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']); // Log the error message from broker
    console.error('Additional details: ' + frame.body); // Log additional error details
};

// Function to connect to the WebSocket server
function connect() {
    const token = localStorage.getItem('token'); // Retrieve the token from local storage
    if (!token) {
        alert('User not authenticated'); // Alert if no token is found
        return; // Exit the function
    }

    stompClient.connectHeaders = {
        'Authorization': `Bearer ${token}` // Set the Authorization header
    };
    stompClient.activate(); // Activate the STOMP client
}

// Function to disconnect from the WebSocket server
function disconnect() {
    stompClient.deactivate(); // Deactivate the STOMP client
    console.log("Disconnected"); // Log the disconnection
}

// Function to parse JWT token
function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload); // Return the parsed JSON payload
}

// Function to get user ID from JWT token
function getUserIdFromToken() {
    const token = localStorage.getItem('token'); // Retrieve the token from local storage
    if (!token) {
        return null; // Return null if no token is found
    }

    const decodedToken = parseJwt(token); // Decode the token
    return decodedToken.userId; // Return the user ID from the token
}

// Function to send a message
function sendMessage() {
    const messageInput = document.getElementById('message-input'); // Get the message input element
    const messageContent = messageInput.value.trim(); // Get and trim the message content
    const userId = getUserIdFromToken(); // Get the user ID from the token

    if (!userId) {
        alert('User not authenticated'); // Alert if user is not authenticated
        return; // Exit the function
    }

    if (messageContent && stompClient) {
        const message = {
            senderId: userId, 
            content: messageContent,
            timestamp: new Date().toISOString() // Set the current timestamp
        };
        // Publish the message to the server
        stompClient.publish({
            destination: "/app/chat/sendMessage",
            body: JSON.stringify(message)
        });
        messageInput.value = ''; // Clear the message input
    }
}

// Function to display the message output
function showMessageOutput(messageOutput) {
    const chatMessages = document.getElementById('chat-messages'); // Get the chat messages container
    const messageElement = document.createElement('div'); // Create a new message element
    messageElement.classList.add('message'); // Add the 'message' class

    const messageContent = document.createElement('div'); // Create a new element for message content
    messageContent.textContent = `${messageOutput.userDtoSender.firstName}: ${messageOutput.content}`; // Set the message content

    const messageTimestamp = document.createElement('div'); // Create a new element for message timestamp
    messageTimestamp.classList.add('timestamp'); // Add the 'timestamp' class
    messageTimestamp.textContent = new Date(messageOutput.timestamp).toLocaleString(); // Format and set the timestamp

    messageElement.appendChild(messageContent); // Append message content to the message element
    messageElement.appendChild(messageTimestamp); // Append timestamp to the message element

    chatMessages.appendChild(messageElement); // Append the message element to the chat messages container
    chatMessages.scrollTop = chatMessages.scrollHeight; // Scroll to the bottom of the chat messages
}

// Function to load messages from the server
function loadMessages() {
    fetch('http://localhost:8080/messages/list', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}` // Set the Authorization header
        }
    })
    .then(response => response.json()) // Parse the JSON response
    .then(data => {
        data.forEach(message => {
            showMessageOutput(message); // Display each message
        });
    })
    .catch(error => {
        console.error('Error loading messages:', error); // Log any errors
    });
}

// Event listener for the logout button
document.getElementById('logout').addEventListener('click', function() {
    logout(); // Call the logout function
});

// Function to handle user logout
function logout() {
    localStorage.removeItem('token'); // Remove the token from local storage
    window.location.href = 'http://localhost:3000'; // Redirect to the login page
}

// Event listener for the edit profile button
document.getElementById('edit-profile').addEventListener('click', function() {
    editProfile(); // Call the editProfile function
});

// Function to handle profile editing
function editProfile() {
    window.location.href = 'http://localhost:3000/editeditProfile'; // Redirect to the edit profile page
}
