// src/app/features/mesa/mesa-detail/mesa-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MesaService, Mesa, MesaItem } from '../../../core/services/mesa.service';
import { InventoryService, Product } from '../../../core/services/inventory.service';

@Component({
  selector: 'app-mesa-detail',
  standalone: false,
  templateUrl: './mesa-detail.component.html',
  styleUrls: ['./mesa-detail.component.css']
})
export class MesaDetailComponent implements OnInit {
  mesa?: Mesa;
  products: Product[] = [];
  categories: string[] = [];
  selectedCategory = '';
  filteredProducts: Product[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private mesaService: MesaService,
    private inventory: InventoryService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!;
    // Obtenemos la mesa por suscripción al observable
    this.mesaService.getMesas().subscribe((mesas: Mesa[]) => {
      this.mesa = mesas.find((m: Mesa) => m.id === id);
    });

    // Cargamos el inventario y categorías
    this.inventory.getProducts().subscribe((p: Product[]) => {
      this.products = p;
      // Ejemplo: definir categorías según la primera letra del id
      this.categories = Array.from(new Set(p.map((x: Product) => x.id.charAt(0))));
      this.applyFilter();
    });
  }

  applyFilter(): void {
    this.filteredProducts = this.selectedCategory
      ? this.products.filter((x: Product) => x.id.startsWith(this.selectedCategory))
      : [...this.products];
  }

  addProduct(prod: Product): void {
    if (this.mesa) {
      const item: MesaItem = {
        name: prod.name,
        quantity: 1,
        price: prod.price
      };
      this.mesaService.addItemToMesa(this.mesa.id, item);
    }
  }

  consolidate(): void {
    // Aquí podrías redirigir a la factura de la mesa
    this.router.navigate(['/ventas/factura', this.mesa!.id]);
  }

  goBack(): void {
    this.router.navigate(['/mesas']);
  }
}
