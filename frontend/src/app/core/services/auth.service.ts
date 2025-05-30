import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  // Mock de login: acepta cualquier user/pass no vacíos
  login(username: string, password: string): boolean {
    if (username && password) {
      localStorage.setItem('token', 'mock-token');
      return true;
    }
    return false;
  }

  // Mock de registro (sólo consola)
  register(username: string, password: string): boolean {
    if (username && password) {
      console.log('Usuario registrado:', username);
      return true;
    }
    return false;
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
}
