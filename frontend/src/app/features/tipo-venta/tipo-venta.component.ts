import { Component, OnInit } from '@angular/core';
import { TipoVentaService } from '../../core/facades/tipo-venta.service';
import { TipoVenta } from '../../core/models/tipo-venta.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-tipo-venta',
    standalone: false,
  templateUrl: './tipo-venta.component.html',
  styleUrls: ['./tipo-venta.component.css']
})
export class TipoVentaComponent implements OnInit {
  lista: TipoVenta[] = [];
  nuevo: TipoVenta = { codigo: '', nombre: '' };
  cargando = false;
  error = '';

  constructor(private svc: TipoVentaService) {}

  ngOnInit() {
    this.cargarTipos();
  }

  cargarTipos() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: datos => {
        this.lista = datos;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar tipos de venta';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion: Observable<TipoVenta | void> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarTipos();
        this.nuevo = { codigo: '', nombre: '' };
      },
      error: err => {
        this.error = 'Error al guardar tipo de venta';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar este tipo de venta?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarTipos(),
      error: err => {
        this.error = 'Error al eliminar tipo de venta';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
