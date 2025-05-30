import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Product {
  id: string;
  name: string;
  stock: number;
  price: number;
}

@Injectable({ providedIn: 'root' })
export class InventoryService {
  private productsSubject = new BehaviorSubject<Product[]>([
    { id: 'p1', name: 'Cerveza', stock: 20, price: 5000 },
    { id: 'p2', name: 'Arepa', stock: 10, price: 3000 }
  ]);

  getProducts(): Observable<Product[]> {
    return this.productsSubject.asObservable();
  }

  addProduct(p: Product): void {
    this.productsSubject.next([...this.productsSubject.getValue(), p]);
  }

  updateProduct(p: Product): void {
    const updated = this.productsSubject.getValue().map(x => x.id === p.id ? p : x);
    this.productsSubject.next(updated);
  }

  deleteProduct(id: string): void {
    this.productsSubject.next(this.productsSubject.getValue().filter(x => x.id !== id));
  }
}
