import { apiClient } from "./ApiClient";

export const retrieveAllNoteForTodo
    = (id) => apiClient.get(`/note/todo/${id}`)

export const createNoteApi 
    = (note) => apiClient.post(`/note`, note);

export const deleteNoteApi 
    = (id) => apiClient.delete(`/note/${id}`);