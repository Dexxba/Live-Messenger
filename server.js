const express = require('express'); // Importing the Express framework
const path = require('path'); // Importing the Path module

const app = express(); // Creating an Express application
const PORT = 3000; // Defining the port the server will listen on

// Serving static files (HTML, CSS, JS)
app.use(express.static(path.join(__dirname, 'frontend'))); // All static files will be served from the 'frontend' directory

// Route for the home page
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'frontend', 'index.html')); // Sends the 'index.html' file from the 'frontend' directory
});

// Route for the messenger page
app.get('/messenger', (req, res) => {
    res.sendFile(path.join(__dirname, 'frontend', 'index-messenger.html')); // Sends the 'index-messenger.html' file from the 'frontend' directory
});

// Route for the edit profile page
app.get('/editeditProfile', (req, res) => {
    res.sendFile(path.join(__dirname, 'frontend', 'index-edit-profile.html')); // Sends the 'index-edit-profile.html' file from the 'frontend' directory
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`); // Log to the console that the server is running and on which port
});
