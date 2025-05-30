import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface VentaItem {
  name: string;
  quantity: number;
  price: number;
}

@Injectable({ providedIn: 'root' })
export class VentaRapidaService {
  private itemsSubject = new BehaviorSubject<VentaItem[]>([]);

  getItems(): Observable<VentaItem[]> {
    return this.itemsSubject.asObservable();
  }

  addItem(item: VentaItem): void {
    this.itemsSubject.next([ ...this.itemsSubject.getValue(), item ]);
  }

  clear(): void {
    this.itemsSubject.next([]);
  }
}
