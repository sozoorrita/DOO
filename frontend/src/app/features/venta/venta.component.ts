import { Component, OnInit } from '@angular/core';
import { VentaService } from '../../core/facades/venta.service';
import { Venta } from '../../core/models/venta.model';

@Component({
  selector: 'app-venta',
  standalone: false,
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.css']
})
export class VentaComponent implements OnInit {
  lista: Venta[] = [];
  nuevo: Venta = {
    codigo: '',
    fecha: '',
    codigoSesion: '',
    codigoMesa: '',
    total: 0
  };
  cargando = false;
  error = '';

  constructor(private svc: VentaService) {}

  ngOnInit() {
    this.cargarVentas();
  }

  cargarVentas() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: datos => {
        this.lista = datos;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar ventas';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarVentas();
        this.nuevo = { codigo: '', fecha: '', codigoSesion: '', codigoMesa: '', total: 0 };
      },
      error: err => {
        this.error = 'Error al guardar la venta';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas borrar esta venta?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarVentas(),
      error: err => {
        this.error = 'Error al eliminar la venta';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
