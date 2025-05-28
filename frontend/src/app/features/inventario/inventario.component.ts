import { Component, OnInit } from '@angular/core';
import { InventarioService } from '../../core/facades/inventario.service';
import { Inventario } from '../../core/models/inventario.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-inventario',
    standalone: false,
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.css']
})
export class InventarioComponent implements OnInit {
  lista: Inventario[] = [];
  nuevo: Inventario = {
    codigo: '',
    codigoProducto: '',
    nombreProducto: '',
    cantidad: 0
  };
  cargando = false;
  error = '';

  constructor(private svc: InventarioService) {}

  ngOnInit() {
    this.cargarInventario();
  }

  cargarInventario() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: datos => {
        this.lista = datos;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar inventario';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion: Observable<Inventario | void> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarInventario();
        this.nuevo = { codigo: '', codigoProducto: '', nombreProducto: '', cantidad: 0 };
      },
      error: err => {
        this.error = 'Error al guardar el registro de inventario';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar este registro de inventario?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarInventario(),
      error: err => {
        this.error = 'Error al eliminar el registro de inventario';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
