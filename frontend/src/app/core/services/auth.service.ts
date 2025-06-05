// src/app/core/services/auth.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LoginRequest {
  nombre: string;
  contrasena: string;
  codigoRol: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = '/api/v1/usuarios/iniciar-sesion';

  constructor(private http: HttpClient) {}

  /**
   * Llama al endpoint de login y devuelve directamente el UUID (string)
   */
  login(credentials: LoginRequest): Observable<string> {
    return this.http.post<string>(
      this.loginUrl,
      credentials,
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        responseType: 'text' as 'json' // Asegura que Angular lo trate como texto plano
      }
    );
  }

  storeUserId(id: string): void {
    localStorage.setItem('usuarioId', id);
  }

  getUserId(): string | null {
    return localStorage.getItem('usuarioId');
  }

  logout(): void {
    localStorage.removeItem('usuarioId');
  }

  isLoggedIn(): boolean {
    return !!this.getUserId();
  }
}
