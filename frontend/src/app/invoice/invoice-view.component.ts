// src/app/invoice/invoice-view.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

interface CartItem {
  product: { name: string; price: number; };
  quantity: number;
}

interface InvoiceState {
  cart: CartItem[];
  tableId?: string;
  paymentMethod?: string;
  date?: string;
}

@Component({
  selector: 'app-invoice-view',
  standalone: false,
  templateUrl: './invoice-view.component.html',
  styleUrls: ['./invoice-view.component.css']
})
export class InvoiceViewComponent implements OnInit {
  saleId: string | null = null;
  cart: CartItem[] = [];
  tableId?: string;
  paymentMethod?: string;
  date: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.saleId = this.route.snapshot.paramMap.get('saleId');
    const state = history.state as InvoiceState;
    this.cart = state.cart || [];
    this.tableId = state.tableId;
    this.paymentMethod = state.paymentMethod || 'No especificada';
    this.date = state.date || new Date().toLocaleString();
  }

  get total(): number {
    return this.cart.reduce((sum, i) => sum + i.product.price * i.quantity, 0);
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }
}
