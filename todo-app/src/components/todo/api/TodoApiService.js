import { apiClient } from './ApiClient'

export const retrieveAllTodosForUsernameApi 
    = () => apiClient.get(`/todo`);

export const deleteTodoApi 
    = (id) => apiClient.delete(`/todo/${id}`);

export const retrieveTodoApi 
    = (id) => apiClient.get(`/todo/${id}`);

export const updateTodoApi 
    = (id, todo) => {
        console.log(id)
        return(
        apiClient.put(`/todo/${id}`, todo))};
    

export const createTodoApi 
    = (todo) => apiClient.post(`/todo`, todo);