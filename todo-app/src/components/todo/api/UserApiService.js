import { apiClient } from "./ApiClient";

export const getUserIn4Api 
    = () => apiClient.get(`/user`);

export const changePasswordApi
    = (password) => apiClient.put(`/user/change-password`, password);

    