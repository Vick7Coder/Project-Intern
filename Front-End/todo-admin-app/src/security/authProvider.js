import { AuthProvider } from "react-admin";

export const authProvider = {
    // called when the user attempts to log in
    login: async ({ username, password }) =>  {
        const request = new Request('http://localhost:8019/api/v2/auth/login', {
            method: 'POST',
            body: JSON.stringify({ username, password }),
            credentials: 'include',
            headers: new Headers({ 'Content-Type': 'application/json' }),
        });

        try{
            const response = await fetch(request);
            if(response.status < 200 || response.status >= 300){
                throw new Error(response.statusText);
            }
            const auth = await response.json();
            localStorage.setItem('auth', JSON.stringify(auth));
            return auth;

        }catch(error){
            throw new Error(error.message)
        }
    },
    // called when the user clicks on the logout button
    logout: () => {
        localStorage.removeItem('auth');
        return Promise.resolve();
    },
    // called when the API returns an error
    checkError: (error) => {
        const status = error.status;
        if (status === 401 || status === 403) {
            localStorage.removeItem('auth');
            return Promise.reject({ redirectTo: '/credentials-required' });
        }
        // other error code (404, 500, etc): no need to log out
        return Promise.resolve();
    },
    // called when the user navigates to a new location, to check for authentication
    checkAuth: () => {
        return localStorage.getItem("auth") ? Promise.resolve() : Promise.reject();
    },
    // called when the user navigates to a new location, to check for permissions / roles
    getPermissions: () => Promise.resolve(),
};