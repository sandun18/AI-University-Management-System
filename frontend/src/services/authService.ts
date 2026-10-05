import api from '@/api/axios';

export interface LoginPayload {
  username: string;
  password: string;
}

export interface RegisterPayload {
  username: string;
  email: string;
  password: string;
  role: 'STUDENT' | 'LECTURER' | 'ADMIN';
}

export interface AuthResponse {
  id: number;
  username: string;
  email: string;
  role: 'STUDENT' | 'LECTURER' | 'ADMIN';
  message: string;
  token: string;
}

export const authService = {
  async login(payload: LoginPayload): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/api/auth/login', payload);
    return response.data;
  },

  async register(payload: RegisterPayload): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/api/auth/register', payload);
    return response.data;
  },

  async healthCheck(): Promise<string> {
    const response = await api.get<string>('/api/auth/health');
    return response.data;
  },
};
