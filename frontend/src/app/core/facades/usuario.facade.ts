import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface LoginResponse { token: string; }

@Injectable({ providedIn: 'root' })
export class AuthFacade {
  private base = '/api/usuarios';
  constructor(private http: HttpClient) {}
  login(email: string, pw: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.base}/login`, { email, pw });
  }
  register(name: string, email: string, pw: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.base}/register`, { name, email, pw });
  }
}
