import { apiClient } from './ApiClient'

export const executeJwtAuthenticationService
    = (username, password) => apiClient.post(`/auth/login`,{username, password});

export const executeLogoutService = () => apiClient.post(`/auth/logout`);

export const executeRegistrationService 
    = (username, password, email) => apiClient.post(`/auth/register`, {username, password, email});

export const executeForgotPasswordService 
    = (email) => apiClient.post(`/auth/reset-password-request`, {email});

export const executeResetPasswordService
    =(newPassword, tokenl) => apiClient.post(`/auth/reset-password`, {newPassword}, {params:{token:tokenl}});

