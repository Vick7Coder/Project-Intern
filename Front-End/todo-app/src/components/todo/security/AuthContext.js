import { createContext, useContext, useState } from "react";
import { executeJwtAuthenticationService, executeLogoutService } from "../api/AuthenticationApiService";

//1:  Create a context
export const AuthContext = createContext()

export const useAuth = () => useContext(AuthContext)

//2: Share the created context with other component
export default function AuthProvider({ children }) {
    // Put some state in the context
    const [isAuthenticated, setAuthenticated] = useState(false)
    async function login(usernameInput, password) {

        try {

            const response = await executeJwtAuthenticationService(usernameInput, password)

            if(response.status==200){
                
                setAuthenticated(true)

                return true            
            } else {
                logout()
                return false
            }    
        } catch(error) {
            logout()
            return false
        }
    }

   async function logout() {
       try{
         await executeLogoutService(); // Send request to backend to invalidate session.
         setAuthenticated(false);
       }
       catch(error){
          console.error("Error during logout", error);
          return false;
        }
   }

    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}
