// Get elements
const showSignUpButton = document.getElementById('showSignUp'); // Button to show the sign-up form
const showSignInButton = document.getElementById('showSignIn'); // Button to show the sign-in form
const container = document.getElementById('container'); // Container that holds both forms
const loginForm = document.getElementById('loginForm'); // Form element for login
const registerForm = document.getElementById('registerForm'); // Form element for registration

// Add event listeners for form submission
loginForm.addEventListener('submit', login); // Event listener for login form submission
registerForm.addEventListener('submit', register); // Event listener for registration form submission

// Event listeners for showing sign-in or sign-up form
showSignUpButton.addEventListener('click', () => {
  container.classList.add('sign-up-mode'); // Add 'sign-up-mode' class to container to show sign-up form
});
showSignInButton.addEventListener('click', () => {
  container.classList.remove('sign-up-mode'); // Remove 'sign-up-mode' class from container to show sign-in form
});

// Login function
async function login(event) {
  event.preventDefault(); // Prevent the default form submission behavior
  const usernameInput = document.getElementById('username'); // Get username input element
  const passwordInput = document.getElementById('password'); // Get password input element

  const username = usernameInput.value.trim(); // Get and trim the username value
  const password = passwordInput.value.trim(); // Get and trim the password value

  if (!username || !password) { // Check if both username and password are provided
    alert('Please enter both username and password'); // Alert if fields are missing
    return; // Exit the function
  }

  const loginDto = { username, password }; // Create login data object

  try {
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(loginDto),
    });

    if (response.ok) { // Check if response is OK (status 200-299)
      const data = await response.json(); // Parse the response JSON
      const jwtToken = data.token; // Extract the JWT token from the response
      localStorage.setItem('token', jwtToken); // Store the token in local storage
      window.location.href = 'http://localhost:3000/messenger'; // Redirect to messenger page
    } else {
      const error = await response.text(); // Get error text from the response
      console.error('Login failed:', error); // Log the error to the console
      alert(`Login failed: ${error}`); // Alert the user about the login failure
    }
  } catch (error) {
    console.error('Login failed:', error); // Log unexpected errors to the console
    alert('Login failed: An unexpected error occurred'); // Alert the user about the unexpected error
  }
}

// Register function
async function register(event) {
  event.preventDefault(); // Prevent the default form submission behavior
  const usernameRegisterInput = document.getElementById('registerUsername'); // Get username input element
  const passwordRegisterInput = document.getElementById('registerPassword'); // Get password input element
  const emailRegisterInput = document.getElementById('registerEmail'); // Get email input element
  const firstNameRegisterInput = document.getElementById('registerFirstName'); // Get first name input element
  const lastNameRegisterInput = document.getElementById('registerLastName'); // Get last name input element

  const username = usernameRegisterInput.value.trim(); // Get and trim the username value
  const password = passwordRegisterInput.value.trim(); // Get and trim the password value
  const email = emailRegisterInput.value.trim(); // Get and trim the email value
  const firstName = firstNameRegisterInput.value.trim(); // Get and trim the first name value
  const lastName = lastNameRegisterInput.value.trim(); // Get and trim the last name value

  if (!username || !password || !email || !firstName || !lastName) { // Check if all fields are provided
    alert('Please enter all fields in order to continue'); // Alert if fields are missing
    return; // Exit the function
  }

  const userDto = { username, password, email, firstName, lastName }; // Create user data object

  try {
    const response = await fetch('http://localhost:8080/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userDto),
    });

    if (response.status === 201) { // Check if response status is 201 (Created)
      window.location.href = 'http://localhost:3000'; // Redirect to home page
      alert('Registration Successful'); // Alert the user about successful registration
    } else {
      const error = await response.text(); // Get error text from the response
      console.error('Registration failed:', error); // Log the error to the console
      alert(`Registration failed: ${error}`); // Alert the user about the registration failure
    }
  } catch (error) {
    console.error('Registration failed:', error); // Log unexpected errors to the console
    alert('Registration failed: An unexpected error occurred'); // Alert the user about the unexpected error
  }
}
