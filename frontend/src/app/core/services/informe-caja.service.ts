import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface InformeCaja {
  codigo?: string;
  sesionTrabajo: any;
  fechaApertura: string;
  fechaCierre: string;
  // agrega los demás campos según tu backend
}

@Injectable({ providedIn: 'root' })
export class InformeCajaService {
  private apiUrl = '/api/informe-caja';

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
