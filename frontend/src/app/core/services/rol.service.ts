// src/app/core/services/rol.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Rol {
  codigo: string;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class RolService {
  // Ahora apunta directamente a /api/v1/roles
  private apiUrl = '/api/v1/roles';

  constructor(private http: HttpClient) {}

  getRoles(): Observable<Rol[]> {
    return this.http.get<Rol[]>(this.apiUrl);
  }
}

