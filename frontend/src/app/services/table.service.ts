import { Injectable } from '@angular/core';
import { tables, Table } from '../../mockData/tables';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TableService {
  getAll(): Observable<Table[]> {
    return of(tables);
  }
  // añadir aquí métodos de CRUD si quieres
}
