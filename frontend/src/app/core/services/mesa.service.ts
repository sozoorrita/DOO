import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface MesaItem {
  name: string;
  quantity: number;
  price: number;
}

export interface Mesa {
  id: string;
  name: string;
  occupied: boolean;
  items: MesaItem[];
}

@Injectable({ providedIn: 'root' })
export class MesaService {
  private mesasSubject = new BehaviorSubject<Mesa[]>([]);

  getMesas(): Observable<Mesa[]> {
    return this.mesasSubject.asObservable();
  }

  createMesa(name: string): void {
    const newMesa: Mesa = { id: Date.now().toString(), name, occupied: false, items: [] };
    this.mesasSubject.next([...this.mesasSubject.getValue(), newMesa]);
  }

  updateMesa(mesa: Mesa): void {
    const updated = this.mesasSubject.getValue().map(m => m.id === mesa.id ? mesa : m);
    this.mesasSubject.next(updated);
  }

  deleteMesa(id: string): void {
    this.mesasSubject.next(this.mesasSubject.getValue().filter(m => m.id !== id));
  }

  addItemToMesa(id: string, item: MesaItem): void {
    const updated = this.mesasSubject.getValue().map(m => {
      if (m.id === id) {
        m.items.push(item);
        m.occupied = true;
      }
      return m;
    });
    this.mesasSubject.next(updated);
  }
}
