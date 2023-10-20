import axios from "axios";

export const apiClient = axios.create(
    {
        baseURL: 'http://localhost:8019/api/v1',
        withCredentials: true,
    }
)