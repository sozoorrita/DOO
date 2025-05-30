import { Injectable } from '@angular/core';
import { Product } from '../../../mockData/products';

export interface CartItem {
  product: Product;
  quantity: number;
}

@Injectable({ providedIn: 'root' })
export class SaleService {
  private carts: Record<string,CartItem[]> = {};

  getCart(tableId: string): CartItem[] {
    return this.carts[tableId] || [];
  }

  setCart(tableId: string, cart: CartItem[]): void {
    this.carts[tableId] = [...cart];
  }

  clearCart(tableId: string): void {
    delete this.carts[tableId];
  }
}
