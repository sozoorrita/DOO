import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { VentaService, Venta } from '../../../core/services/venta.service';
import { MesaService, Mesa } from '../../../core/services/mesa.service';
import { SesionTrabajoService, SesionTrabajo } from '../../../core/services/sesion-trabajo.service';
import { FormaPagoService, FormaPago } from '../../../core/services/forma-pago.service';
import { TipoVentaService, TipoVenta } from '../../../core/services/tipo-venta.service';

@Component({
  selector: 'app-venta-rapida',
  standalone: false,
  templateUrl: './venta-rapida.component.html',
  styleUrls: ['./venta-rapida.component.css']
})
export class VentaRapidaComponent implements OnInit {
  // Listas para los <select>
  mesas: Mesa[] = [];
  sesiones: SesionTrabajo[] = [];
  formasPago: FormaPago[] = [];
  tiposVenta: TipoVenta[] = [];

  // Objeto que se enviará al backend
  nuevaVenta: Venta = {
    fecha: '',                // La fecha la asignaremos justo antes de enviar
    totalVenta: 0,
    codigoFormaPago: '',
    codigoTipoVenta: '',
    codigoSesionTrabajo: '',
    codigoMesa: ''
  };

  constructor(
    private ventaService: VentaService,
    private mesaService: MesaService,
    private sesionService: SesionTrabajoService,
    private formaPagoService: FormaPagoService,
    private tipoVentaService: TipoVentaService,
    private router: Router
  ) {}

  ngOnInit() {
    // Cargar todas las opciones de cada entidad para los selects
    this.mesaService.getMesas().subscribe({
      next: mesas => this.mesas = mesas,
      error: err => alert('Error al cargar mesas: ' + (err.error?.message || err.message))
    });

    this.sesionService.getSesiones().subscribe({
      next: sesiones => this.sesiones = sesiones,
      error: err => alert('Error al cargar sesiones de trabajo: ' + (err.error?.message || err.message))
    });

    this.formaPagoService.getFormasPago().subscribe({
      next: formas => this.formasPago = formas,
      error: err => alert('Error al cargar formas de pago: ' + (err.error?.message || err.message))
    });

    this.tipoVentaService.getTipoVentas().subscribe({
      next: tipos => this.tiposVenta = tipos,
      error: err => alert('Error al cargar tipos de venta: ' + (err.error?.message || err.message))
    });
  }

  realizarVenta() {
    // Validar que todos los campos estén completos
    if (
      !this.nuevaVenta.codigoMesa ||
      !this.nuevaVenta.codigoSesionTrabajo ||
      !this.nuevaVenta.codigoFormaPago ||
      !this.nuevaVenta.codigoTipoVenta ||
      this.nuevaVenta.totalVenta <= 0
    ) {
      alert('Todos los campos son obligatorios y el total debe ser mayor a 0');
      return;
    }

    // Asignar la fecha actual en formato ISO antes de enviar
    this.nuevaVenta.fecha = new Date().toISOString();

    this.ventaService.registrarVenta(this.nuevaVenta).subscribe({
      next: () => {
        alert('Venta registrada exitosamente');
        // Reiniciar el formulario
        this.nuevaVenta = {
          fecha: '',
          totalVenta: 0,
          codigoFormaPago: '',
          codigoTipoVenta: '',
          codigoSesionTrabajo: '',
          codigoMesa: ''
        };
        // Opcional: redirigir a listado de ventas o recargar página
        this.router.navigate(['/venta-rapida']);
      },
      error: err => alert('Error al registrar la venta: ' + (err.error?.message || err.message))
    });
  }
}
