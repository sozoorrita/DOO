import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { InformeCajaRequest, InformeCajaResponse } from '../models/informe-caja.model';

@Injectable({ providedIn: 'root' })
export class InformeCajaService {
  private url = `${environment.apiUrl}/informe-caja`;

  constructor(private http: HttpClient) {}

  generarInforme(filtro: InformeCajaRequest): Observable<InformeCajaResponse> {
    return this.http.post<InformeCajaResponse>(this.url, filtro);
  }
}
