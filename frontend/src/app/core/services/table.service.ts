import { Injectable } from '@angular/core';
import { tables, Table } from '../../../mockData/tables';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TableService {
  findById(id: string): Table | undefined {
    return tables.find(t => t.id === id);
  }

  getAll(): Observable<Table[]> {
    return of(tables);
  }
  // añadir aquí métodos de CRUD si quieres
}
