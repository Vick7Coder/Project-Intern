import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from './security/AuthContext'
import './LoginComponent.css';
import { Link } from 'react-router-dom'
function LoginComponent() {
  const [username, setUsername] = useState('Username')
  const [password, setPassword] = useState('')
  const [showErrorMessage, setShowErrorMessage] = useState(false)
  const navigate = useNavigate();
  const authContext = useAuth();

  function handleUsernameChange(event) {
    setUsername(event.target.value);
  }

  function handlePasswordChange(event) {
    setPassword(event.target.value);
  }

  //async await
  async function handleSubmit() {
    if (await authContext.login(username, password)) {
      navigate(`/welcome`)
    } else {
      setShowErrorMessage(true)
    }
  }

  return (
    <div>
      <h1>Time to login!</h1>
      {showErrorMessage && (
        <div className="errorMessage">
          Authentication Failed. Please check your credentials.
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
          <button
            type="button"
            className="btn btn-dark custom-button m-3"
            onClick={handleSubmit}
          >
            Login
          </button>
          <a className='btn bg-transparent' style={{ display: 'inline-block', width: 'auto' }}>
            <Link to="/forgot-password"  style={{ color: 'gray' }}>Forgot password!</Link>
          </a>

        </div>
      </div>
    </div>
  );
}

export default LoginComponent;