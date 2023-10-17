import { useState } from 'react'
import { executeForgotPasswordService } from './api/AuthenticationApiService'
import './LoginComponent.css';

function ForgotPasswordComponent() {
  const [email, setEmail] = useState('')
  const [showSuccessMessage, setShowSuccessMessage] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')

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
     await executeForgotPasswordService(email)
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
    <h1>Forgot Password!</h1>

    {showSuccessMessage && (
      <div className="successMessage">
        Send request success!.
      </div>
    )}

    {errorMessage && ( 
      <div className="errorMessage">
        {errorMessage}
      </div>
    )}

    <div className="LoginForm login-form">

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
            Send
          </button>
        </div>
    </div>
  </div>
);
}

export default ForgotPasswordComponent;
