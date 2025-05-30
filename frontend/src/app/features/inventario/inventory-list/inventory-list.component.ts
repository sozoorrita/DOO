import { Component, OnInit } from '@angular/core';
import { InventoryService, Product } from '../../../core/services/inventory.service';

@Component({
  selector: 'app-inventory-list',
  standalone: false,
  templateUrl: './inventory-list.component.html',
  styleUrls: ['./inventory-list.component.css']
})
export class InventoryListComponent implements OnInit {
  products: Product[] = [];
  editing: Product | null = null;
  form = { name: '', stock: 0, price: 0 };

  constructor(private inv: InventoryService) {}

  ngOnInit(): void {
    this.inv.getProducts().subscribe(data => this.products = data);
  }

  startAdd(): void {
    this.editing = null;
    this.form = { name: '', stock: 0, price: 0 };
  }

  startEdit(p: Product): void {
    this.editing = p;
    this.form = { name: p.name, stock: p.stock, price: p.price };
  }

  save(): void {
    if (this.editing) {
      this.inv.updateProduct({ ...this.editing, ...this.form });
    } else {
      this.inv.addProduct({ id: Date.now().toString(), ...this.form });
    }
    this.editing = null;
    this.form = { name: '', stock: 0, price: 0 };
  }

  delete(p: Product): void {
    this.inv.deleteProduct(p.id);
  }
}
