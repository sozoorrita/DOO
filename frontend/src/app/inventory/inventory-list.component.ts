import { Component, OnInit } from '@angular/core';
import { Product } from '../../mockData/products';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-inventory-list',
  standalone: false,
  templateUrl: './inventory-list.component.html',
  styleUrls: ['./inventory-list.component.css']
})
export class InventoryListComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getAll().subscribe(prods => this.products = prods);
  }

  getStatus(stock: number): 'Abastecido' | 'Bajo' | 'Agotado' {
    if (stock === 0) return 'Agotado';
    if (stock <= 5) return 'Bajo';
    return 'Abastecido';
  }

  onCreate() {
    alert('Función Crear Producto (placeholder)');
  }

  onEdit(product: Product) {
    alert(`Editar ${product.name} (placeholder)`);
  }

  onDelete(product: Product) {
    if (confirm(`¿Eliminar ${product.name}?`)) {
      this.products = this.products.filter(p => p.id !== product.id);
    }
  }
}