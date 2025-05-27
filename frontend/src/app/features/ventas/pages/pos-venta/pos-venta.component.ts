import { Component, OnInit } from '@angular/core';
import { FormControl }        from '@angular/forms';
import { Observable }         from 'rxjs';
import { map, startWith }     from 'rxjs/operators';
import { ProductoFacade } from '../../../../core/facades/producto.facade';
import { Producto } from '../../../../core/models/producto.model';
import { VentaFacade } from '../../../../core/facades/venta.facade';
import { VentaItem } from '../../../../core/models/venta-item.model';
import { Router } from '@angular/router';


@Component({
  selector: 'app-pos-venta',
  standalone: false,
  templateUrl: './pos-venta.component.html',
  styleUrls: ['./pos-venta.component.css']
})
export class PosVentaComponent implements OnInit {
  productos: Producto[] = [];
  productosFiltrados$!: Observable<Producto[]>;
  filterControl = new FormControl('');                  // ← declara el filtro
  carrito: VentaItem[] = [];

  constructor(
    private productoFacade: ProductoFacade,
    private ventaFacade: VentaFacade,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productoFacade.getAll().subscribe(data => {
      this.productos = data;
      this.productosFiltrados$ = this.filterControl.valueChanges.pipe(
        startWith(''),
        map(text => this.filtrar(text ?? ''))
      );
    });
  }

  private filtrar(text: string): Producto[] {
    const term = text.toLowerCase();
    return this.productos.filter(p => p.nombre.toLowerCase().includes(term));
  }

  addToCart(prod: Producto): void {
    this.carrito.push({ productoId: prod.id, cantidad: 1, precio: prod.precio });
  }

  checkout(): void {
    // Lógica de checkout...
  }

  backToList(): void {
    this.router.navigate(['ventas']);
  }
}