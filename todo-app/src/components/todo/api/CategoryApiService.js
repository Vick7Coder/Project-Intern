import { apiClient } from './ApiClient'

export const retrieveAllCategoryForUser 
    = () => apiClient.get(`/category/enabled-list`);

export const retrieveAllTodoForCategory
    = (id) => apiClient.get(`/todo/category/${id}`)

export const deleteCategoryApi 
    = (id) => apiClient.delete(`/category/${id}`);

export const retrieveCategoryApi 
    = (id) => apiClient.get(`/category/${id}`);

export const updateCategoryApi 
    = (id, category) => apiClient.put(`/category/${id}`, category);

export const createCategoryApi 
    = (name) => apiClient.post(`/category`, name);

export const enableCategoryApi 
    = (id) => apiClient.put(`/category/enable/${id}`) 