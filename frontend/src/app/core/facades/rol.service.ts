import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Rol } from '../models/rol.model';

@Injectable({ providedIn: 'root' })
export class RolService {
  private url = `${environment.apiUrl}/roles`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Rol[]> {
    return this.http.get<Rol[]>(this.url);
  }

  create(item: Rol): Observable<Rol> {
    return this.http.post<Rol>(this.url, item);
  }

  update(codigo: string, item: Rol): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
