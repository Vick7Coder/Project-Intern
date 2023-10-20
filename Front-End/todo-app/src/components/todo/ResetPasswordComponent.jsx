import { useState } from 'react'
import { executeResetPasswordService } from './api/AuthenticationApiService'
import './LoginComponent.css';

function ResetPasswordComponent() {
    const [password, setPassword] = useState('')
    const [showSuccessMessage, setShowSuccessMessage] = useState(false)
    const [errorMessage, setErrorMessage] = useState('')
    // Lấy token từ URL
    const search = window.location.search;
    const params = new URLSearchParams(search);
    const token = params.get('token');

    function handlePasswordChange(event) {
        setPassword(event.target.value);
        console.log(token)
    }

    async function handleSubmit(e) {
        e.preventDefault();

        console.log(token)

        try {
            await executeResetPasswordService(password, token)
            setShowSuccessMessage(true)
        }
        catch (error) {
            console.error("Invalid Token.", error);

            setErrorMessage('Expired Form! You can request new in Forgot Password.');



        }
    }

    return (
        <div>
            <h1>Reset Your Password!</h1>

            {showSuccessMessage && (
                <div className="successMessage">
                    Now! You can login with new Password!
                </div>
            )}

            {errorMessage && (
                <div className="errorMessage">
                    {errorMessage}
                </div>
            )}

            <div className="LoginForm login-form">

                <div className="form-group">
                    <label htmlFor="password">New Password:</label>
                    <input
                        type="text"
                        id="password"
                        name="password"
                        value={password}
                        onChange={handlePasswordChange}
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

export default ResetPasswordComponent;
