// src/app/features/estado-mesa/pages/estado-mesa-list/estado-mesa-list.component.ts

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EstadoMesa } from 'src/app/core/models/estado-mesa.model';
import { EstadoMesaService } from 'src/app/core/services/estado-mesa.service';

@Component({
  selector: 'app-estado-mesa-list',
  standalone : false,
  templateUrl: './estado-mesa-list.component.html',
  styleUrls: ['./estado-mesa-list.component.css']
})
export class EstadoMesaListComponent implements OnInit {
  estados: EstadoMesa[] = [];
  cargando = false;
  error = '';

  constructor(
    private svc: EstadoMesaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarListado();
  }

  cargarListado(): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: data => {
        this.estados = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error al cargar estados de mesa';
        this.cargando = false;
      }
    });
  }

  onNuevo(): void {
    this.router.navigate(['estado-mesas', 'nuevo']);
  }

  onEditar(codigo: string): void {
    this.router.navigate(['estado-mesas', 'editar', codigo]);
  }

  onEliminar(codigo: string): void {
    if (!confirm('¿Deseas eliminar este estado de mesa?')) {
      return;
    }
    this.svc.eliminar(codigo).subscribe({
      next: () => this.cargarListado(),
      error: err => {
        console.error(err);
        this.error = 'Error al eliminar';
      }
    });
  }
}
