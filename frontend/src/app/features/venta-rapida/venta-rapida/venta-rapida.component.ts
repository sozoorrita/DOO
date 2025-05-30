import { Component, OnInit } from '@angular/core';
import { VentaRapidaService, VentaItem } from '../../../core/services/venta-rapida.service';
import { InventoryService, Product }     from '../../../core/services/inventory.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-venta-rapida',
  standalone: false,
  templateUrl: './venta-rapida.component.html',
  styleUrls: ['./venta-rapida.component.css']
})
export class VentaRapidaComponent implements OnInit {
  items: VentaItem[] = [];
  products: Product[] = [];
  categories: string[] = [];
  selectedCategory = '';

  constructor(
    private ventaService: VentaRapidaService,
    private inventory: InventoryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ventaService.getItems().subscribe(i => this.items = i);
    this.inventory.getProducts().subscribe(p => {
      this.products = p;
      this.categories = Array.from(new Set(p.map(x => x.id.charAt(0))));
      this.applyFilter();
    });
  }

  applyFilter(): void {
    this.products = this.selectedCategory
      ? this.products.filter(x => x.id.startsWith(this.selectedCategory))
      : this.products;
  }

  addProduct(prod: Product): void {
    this.ventaService.addItem({name: prod.name, quantity: 1, price: prod.price});
  }

  consolidate(): void {
    // navegación a factura rápida
    this.router.navigate(['/ventas/factura-rapida']);
  }

  clear(): void {
    this.ventaService.clear();
  }
}
