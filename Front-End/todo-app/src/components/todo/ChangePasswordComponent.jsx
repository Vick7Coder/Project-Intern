import { useState } from "react"
import './LoginComponent.css'
import{changePasswordApi} from './api/UserApiService'
function ChangePasswordComponent() {
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [showSuccessMessage, setShowSuccessMessage] = useState(false)
    function handleOldPasswordChange(event) {
        setOldPassword(event.target.value);
      }
    function handleNewPasswordChange(event){
        setNewPassword(event.target.value);
    }


    async function handleSubmit() {
        const password = {
            oldPassword: oldPassword,
            newPassword: newPassword
        }
        await changePasswordApi(password)
            .then(() => {
                setShowSuccessMessage(true)
            
            })
            .catch(error => console.log(error));
    }
    



    return(
        <div>
        <h1>Change Password!</h1>
        {showSuccessMessage && (
          <div className="errorMessage">
            Change Password successfully!
          </div>
        )}
        <div className="LoginForm login-form">
          <div className="form-group">
            <label htmlFor="oldPassword">Old Password:</label>
            <input
              type="text"
              id="oldPassword"
              name="username"
              className="form-control"
              value={oldPassword}
              onChange={handleOldPasswordChange}
            />
          </div>
  
          <div className="form-group">
            <label htmlFor="newPassword">New Password:</label>
            <input
              type="text"
              id="newPassword"
              name="password"
              className="form-control"
              value={newPassword}
              onChange={handleNewPasswordChange}
            />
          </div>
  
          <div className="form-group">
            <button
              type="button"
              className="btn btn-dark custom-button m-3"
              onClick={handleSubmit}
            >
              Change
            </button>
  
          </div>
        </div>
      </div>

    );

}
export default ChangePasswordComponent