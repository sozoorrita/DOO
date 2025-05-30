import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MesaService, Mesa } from '../../../core/services/mesa.service';
import { EstadoMesaService, EstadoMesa } from '../../../core/services/estado-mesa.service';

@Component({
  selector: 'app-mesa-detail',
  standalone: false,
  templateUrl: './mesa-detail.component.html',
  styleUrls: ['./mesa-detail.component.css']
})
export class MesaDetailComponent implements OnInit {
  mesa: Mesa = { nombre: '', codigoEstadoMesa: '' };
  estados: EstadoMesa[] = [];
  codigoMesa: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private mesaService: MesaService,
    private estadoMesaService: EstadoMesaService
  ) {}

  ngOnInit() {
    this.cargarEstados();
    this.codigoMesa = this.route.snapshot.paramMap.get('codigo');
    if (this.codigoMesa) {
      this.mesaService.getMesaPorId(this.codigoMesa).subscribe({
        next: mesa => this.mesa = mesa,
        error: err => alert('Error al cargar mesa: ' + (err.error?.message || err.message))
      });
    }
  }

  private cargarEstados() {
    this.estadoMesaService.getEstadoMesas().subscribe({
      next: estados => this.estados = estados,
      error: err => alert('Error al cargar estados de mesa: ' + (err.error?.message || err.message))
    });
  }

  guardarMesa() {
    if (!this.mesa.nombre || !this.mesa.codigoEstadoMesa) {
      alert('Todos los campos son obligatorios');
      return;
    }
    this.mesaService.modificar(this.codigoMesa!, this.mesa).subscribe({
      next: () => {
        alert('Mesa actualizada');
        this.router.navigate(['/mesas']);
      },
      error: err => alert('Error al actualizar mesa: ' + (err.error?.message || err.message))
    });
  }
}
