import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface FormaPago {
  codigo?: string;
  nombre: string;
  // otros campos según tu backend
}

@Injectable({ providedIn: 'root' })
export class FormaPagoService {
  private apiUrl = '/api/formas-pago';

  constructor(private http: HttpClient) {}

  getFormasPago(): Observable<FormaPago[]> {
    return this.http.get<FormaPago[]>(this.apiUrl);
  }

  getFormaPagoPorId(codigo: string): Observable<FormaPago> {
    return this.http.get<FormaPago>(`${this.apiUrl}/${codigo}`);
  }

  registrar(forma: FormaPago): Observable<FormaPago> {
    return this.http.post<FormaPago>(this.apiUrl, forma);
  }

  modificar(codigo: string, forma: FormaPago): Observable<FormaPago> {
    return this.http.put<FormaPago>(`${this.apiUrl}/${codigo}`, forma);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
