import { useState } from 'react'
import { executeRegistrationService } from './api/AuthenticationApiService'
import './LoginComponent';

function RegisterComponent() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [email, setEmail] = useState('')
  const [showSuccessMessage, setShowSuccessMessage] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')

  
  function handleUsernameChange(event) {
    setUsername(event.target.value);
  }

  function handlePasswordChange(event) {
    setPassword(event.target.value);
  }

  function handleEmailChange(event) {
    setEmail(event.target.value);
  }

 async function handleSubmit(e) {
   e.preventDefault();

   // Validate email
   if (!/\S+@\S+\.\S+/.test(email)) {
     alert('Invalid email format.');
     return;
   }
   
   try{
     await executeRegistrationService(username, password,email)
     setShowSuccessMessage(true)
   }
   catch(error){
     console.error("Error during registration", error);

     // Check if the error response has a message
  if (error.response && error.response.data && error.response.data.error) {
    setErrorMessage(error.response.data.error);
  } else {
    setErrorMessage('An unknown error occurred.');
  }
     
     
   }
 }

 return (
  <div>
    <h1>Time to register!</h1>

    {showSuccessMessage && (
      <div className="successMessage">
        Registration Successful. Please check your email to activate your account.
      </div>
    )}

    {errorMessage && ( 
      <div className="errorMessage">
        {errorMessage}
      </div>
    )}

    <div className="LoginForm login-form">
    <div className="form-group">
          <label htmlFor="username">User Name:</label>
          <input
            type="text"
            id="username"
            name="username"
            className="form-control"
            value={username}
            onChange={handleUsernameChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            className="form-control"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>

      <div className="form-group">
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          name="email"
          value={email}
          onChange={handleEmailChange}
        />
      </div>

      <div className="form-group">
          <button
            type="button"
            className="btn btn-dark custom-button m-3"
            onClick={handleSubmit}
          >
            Register
          </button>
        </div>
    </div>
  </div>
);
}

export default RegisterComponent;
