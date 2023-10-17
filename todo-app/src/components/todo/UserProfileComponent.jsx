import { useEffect, useState } from "react";
import { getUserIn4Api} from "./api/UserApiService";
import { useNavigate } from "react-router-dom";

function UserProfileComponent() {
    const[userDto, setUserDto] = useState({ user: {} })
    const navigate = useNavigate();
    useEffect(() => refreshTodos(), [])

    function refreshTodos() {
        getUserIn4Api()
            .then(response => {
                setUserDto(response.data)
            })
            .catch(error => console.log(error))

    }
    function changePassword(){
        navigate(`/change-password`)

    }


    return (
        <div className="container d-flex justify-content-center align-items-center">
             
        <div className="card">

         <div className="upper">

           <img alt="cover" src="https://i.imgur.com/Qtrsrk5.jpg" className="img-fluid"/>
           
         </div>

         <div className="user text-center">

           <div className="profile">

             <img alt="avatar" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg" class="rounded-circle" width="80"/>
             
           </div>

         </div>


         <div className="mt-5 text-center">

           <h4 className="mb-0">{userDto.user.username}</h4>
           <span className="text-muted d-block mb-2"><i className='btn bg-transparent' style={{display: 'inline-block', width: 'auto'}}>
                    {userDto.user.email}
                </i></span>

           <button className="btn btn-dark btn-sm follow" onClick={() => changePassword()}>Change Password</button>


           
         </div>
          
        </div>

      </div>
    )
}

export default UserProfileComponent