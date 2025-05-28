// src/app/core/facades/auth.facade.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface LoginResponse { token: string; }

@Injectable({ providedIn: 'root' })
export class AuthFacade {
  private base = '/api/auth';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.base}/login`, { email, password });
  }

  register(name: string, email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.base}/register`, { name, email, password });
  }
}
