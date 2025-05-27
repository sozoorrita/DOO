import { Component, OnInit } from '@angular/core';
import { ProductoFacade } from '../../../../core/facades/producto.facade';
import { Producto } from '../../../../core/models/producto.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-producto-list',
  standalone: false,
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.css']
})
export class ProductoListComponent implements OnInit {
  productos: Producto[] = [];
  displayedColumns = ['id', 'nombre', 'precio', 'stock', 'acciones'];

  constructor(private facade: ProductoFacade, private router: Router) {}

  ngOnInit(): void { this.load(); }
  load(): void { this.facade.getAll().subscribe(data => this.productos = data); }
  onNuevo(): void { this.router.navigate(['productos/nuevo']); }
  onEditar(id: string): void { this.router.navigate([`productos/${id}/editar`]); }
  onEliminar(id: string): void { this.facade.delete(id).subscribe(() => this.load()); }
}
