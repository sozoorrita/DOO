// src/app/features/indicador-inventario/pages/indicador-inventario-list/indicador-inventario-list.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IndicadorInventario } from 'src/app/core/models/indicador-inventario.model';
import { IndicadorInventarioService } from 'src/app/core/services/indicador-inventario.service';

@Component({
  selector: 'app-indicador-inventario-list',
  standalone : false,
  templateUrl: './indicador-inventario-list.component.html',
  styleUrls: ['./indicador-inventario-list.component.css']
})
export class IndicadorInventarioListComponent implements OnInit {
  indicadores: IndicadorInventario[] = [];
  cargando = false;
  error = '';

  constructor(
    private svc: IndicadorInventarioService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarListado();
  }

  cargarListado(): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: data => {
        this.indicadores = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error cargando indicadores';
        this.cargando = false;
      }
    });
  }

  onNuevo(): void {
    this.router.navigate(['indicador-inventario', 'nuevo']);
  }

  onEditar(codigo: string): void {
    this.router.navigate(['indicador-inventario', 'editar', codigo]);
  }

  onEliminar(codigo: string): void {
    if (!confirm('¿Eliminar este indicador?')) { return; }
    this.svc.eliminar(codigo).subscribe({
      next: () => this.cargarListado(),
      error: err => {
        console.error(err);
        this.error = 'Error al eliminar';
      }
    });
  }
}
