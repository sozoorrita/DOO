import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface InformeCaja {
  codigo?: string;
  codigoSesionTrabajo: string;
  fecha: string;
  totalVenta: string;
  pagoEfectivo: string;
  pagoTransferencia: string;
}

@Injectable({ providedIn: 'root' })
export class InformeCajaService {
  private apiUrl = '/api/v1/informe-caja'; // Asegúrate que esta es la ruta correcta

  constructor(private http: HttpClient) {}

  consolidarInforme(informe: InformeCaja): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/consolidar`, informe);
  }

  getInformes(): Observable<InformeCaja[]> {
    return this.http.get<InformeCaja[]>(this.apiUrl);
  }

  getInformePorId(codigo: string): Observable<InformeCaja> {
    return this.http.get<InformeCaja>(`${this.apiUrl}/${codigo}`);
  }
}
