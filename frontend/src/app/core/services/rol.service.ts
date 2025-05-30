import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Rol {
  codigo: string;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class RolService {
  private apiUrl = '/api/v1/roles';

  constructor(private http: HttpClient) {}

  getRoles(): Observable<Rol[]> {
    return this.http.get<Rol[]>(this.apiUrl);
  }
}
