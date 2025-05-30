import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Subcategoria {
  codigo: string;
  nombre: string;
  // otros campos según tu DTO
}

export interface Producto {
  codigo?: string;
  nombre: string;
  precioLugar: number;
  precioLlevar: number;
  codigoSubcategoria: Subcategoria; // o string si solo envías el UUID
  limiteCantidad: number;
}

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private apiUrl = '/api/v1/productos';

  constructor(private http: HttpClient) {}

  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  getProductoPorId(codigo: string): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${codigo}`);
  }

  registrar(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  modificar(codigo: string, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${codigo}`, producto);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
