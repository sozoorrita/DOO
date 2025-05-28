// src/app/core/facades/pos.facade.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mesa } from '../models/mesa.model';

@Injectable({ providedIn: 'root' })
export class PosFacade {
  private base = '/api/mesas';

  constructor(private http: HttpClient) {}

  getMesas(): Observable<Mesa[]> {
    return this.http.get<Mesa[]>(this.base);
  }
}
