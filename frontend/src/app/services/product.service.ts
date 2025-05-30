import { Injectable } from '@angular/core';
import { products, Product } from '../../mockData/products';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProductService {
  getAll(): Observable<Product[]> {
    return of(products);
  }
  // métodos para filtrar por categoría, etc.
}
