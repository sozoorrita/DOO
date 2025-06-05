// src/app/core/services/usuario.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Usuario {
  id?: string;
  nombre: string;
  codigoRol: string;
  contrasena: string;
}

export interface UsuarioResponse {
  id: string;
  nombre: string;
  codigoRol: string;
}

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private baseUrl = '/api/v1/usuarios';

  constructor(private http: HttpClient) {}

  registrar(usuario: Usuario): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(
      this.baseUrl,
      usuario,
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      }
    );
  }

  iniciarSesion(credentials: { nombre: string; contrasena: string; codigoRol: string }): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(
      `${this.baseUrl}/iniciar-sesion`,
      credentials,
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      }
    );
  }

  getUsuarios(): Observable<UsuarioResponse[]> {
    return this.http.get<UsuarioResponse[]>(this.baseUrl);
  }

  modificar(id: string, usuario: Usuario): Observable<UsuarioResponse> {
    return this.http.put<UsuarioResponse>(
      `${this.baseUrl}/${id}`,
      usuario,
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      }
    );
  }

  eliminar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
