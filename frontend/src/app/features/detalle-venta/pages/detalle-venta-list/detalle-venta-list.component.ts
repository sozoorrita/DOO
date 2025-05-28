// src/app/features/detalle-venta/pages/detalle-venta-list/detalle-venta-list.component.ts

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DetalleVenta } from 'src/app/core/models/detalle-venta.model';
import { DetalleVentaService } from 'src/app/core/services/detalle-venta.service';

@Component({
  selector: 'app-detalle-venta-list',
  standalone : false,
  templateUrl: './detalle-venta-list.component.html',
  styleUrls: ['./detalle-venta-list.component.css']
})
export class DetalleVentaListComponent implements OnInit {
  detalles: DetalleVenta[] = [];
  cargando = false;
  error = '';

  constructor(
    private svc: DetalleVentaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarListado();
  }

  cargarListado(): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: data => {
        this.detalles = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error cargando detalle de ventas';
        this.cargando = false;
      }
    });
  }

  onNuevo(): void {
    this.router.navigate(['detalle-ventas', 'nuevo']);
  }

  onEditar(id: string): void {
    this.router.navigate(['detalle-ventas', 'editar', id]);
  }

  onEliminar(id: string): void {
    if (!confirm('¿Eliminar este detalle de venta?')) { return; }
    this.svc.eliminar(id).subscribe({
      next: () => this.cargarListado(),
      error: err => {
        console.error(err);
        this.error = 'Error eliminando';
      }
    });
  }
}
