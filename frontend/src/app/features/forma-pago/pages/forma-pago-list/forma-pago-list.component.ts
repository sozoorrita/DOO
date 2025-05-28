// src/app/features/forma-pago/pages/forma-pago-list/forma-pago-list.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormaPago } from 'src/app/core/models/forma-pago.model';
import { FormaPagoService } from 'src/app/core/services/forma-pago.service';

@Component({
  selector: 'app-forma-pago-list',
  standalone : false,
  templateUrl: './forma-pago-list.component.html',
  styleUrls: ['./forma-pago-list.component.css']
})
export class FormaPagoListComponent implements OnInit {
  formas: FormaPago[] = [];
  cargando = false;
  error = '';

  constructor(
    private svc: FormaPagoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarListado();
  }

  cargarListado(): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: data => {
        this.formas = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error cargando formas de pago';
        this.cargando = false;
      }
    });
  }

  onNuevo(): void {
    this.router.navigate(['formas-pago', 'nuevo']);
  }

  onEditar(codigo: string): void {
    this.router.navigate(['formas-pago', 'editar', codigo]);
  }

  onEliminar(codigo: string): void {
    if (!confirm('¿Eliminar esta forma de pago?')) { return; }
    this.svc.eliminar(codigo).subscribe({
      next: () => this.cargarListado(),
      error: err => {
        console.error(err);
        this.error = 'Error al eliminar';
      }
    });
  }
}
