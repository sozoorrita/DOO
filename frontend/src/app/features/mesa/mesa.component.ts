import { Component, OnInit } from '@angular/core';
import { MesaService } from '../../core/facades/mesa.service';
import { Mesa } from '../../core/models/mesa.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-mesa',
    standalone: false,
  templateUrl: './mesa.component.html',
  styleUrls: ['./mesa.component.css']
})
export class MesaComponent implements OnInit {
  lista: Mesa[] = [];
  nuevo: Mesa = { codigo: '', nombre: '', codigoEstado: '' };
  cargando = false;
  error = '';

  constructor(private svc: MesaService) {}

  ngOnInit() {
    this.cargarMesas();
  }

  cargarMesas() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: datos => {
        this.lista = datos;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar mesas';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion: Observable<Mesa | void> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarMesas();
        this.nuevo = { codigo: '', nombre: '', codigoEstado: '' };
      },
      error: err => {
        this.error = 'Error al guardar la mesa';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar esta mesa?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarMesas(),
      error: err => {
        this.error = 'Error al eliminar la mesa';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
