import { Component } from '@angular/core';
import { InformeCajaService } from '../../core/facades/informe-caja.service';
import { InformeCajaRequest, InformeCajaResponse } from '../../core/models/informe-caja.model';

@Component({
  selector: 'app-informe-caja',
    standalone: false,
  templateUrl: './informe-caja.component.html',
  styleUrls: ['./informe-caja.component.css']
})
export class InformeCajaComponent {
  filtro: InformeCajaRequest = { fechaInicio: '', fechaFin: '' };
  resultado?: InformeCajaResponse;
  cargando = false;
  error = '';

  constructor(private svc: InformeCajaService) {}

  generar() {
    if (!this.filtro.fechaInicio || !this.filtro.fechaFin) {
      this.error = 'Debes seleccionar ambas fechas';
      return;
    }

    this.cargando = true;
    this.error = '';
    this.resultado = undefined;

    this.svc.generarInforme(this.filtro).subscribe({
      next: data => {
        this.resultado = data;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al generar el informe';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
