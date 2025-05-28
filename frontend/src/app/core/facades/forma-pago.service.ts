import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { FormaPago } from '../models/forma-pago.model';

@Injectable({ providedIn: 'root' })
export class FormaPagoService {
  private url = `${environment.apiUrl}/forma-pagos`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<FormaPago[]> {
    return this.http.get<FormaPago[]>(this.url);
  }

  create(item: FormaPago): Observable<FormaPago> {
    return this.http.post<FormaPago>(this.url, item);
  }

  update(codigo: string, item: FormaPago): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
