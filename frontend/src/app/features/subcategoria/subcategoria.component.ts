import { Component, OnInit } from '@angular/core';
import { SubcategoriaService } from '../../core/facades/subcategoria.service';
import { Subcategoria } from '../../core/models/subcategoria.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-subcategoria',
    standalone: false,
  templateUrl: './subcategoria.component.html',
  styleUrls: ['./subcategoria.component.css']
})
export class SubcategoriaComponent implements OnInit {
  lista: Subcategoria[] = [];
  nuevo: Subcategoria = { codigo: '', nombre: '', codigoCategoria: '' };
  cargando = false;
  error = '';

  constructor(private svc: SubcategoriaService) {}

  ngOnInit() {
    this.cargarSubcategorias();
  }

  cargarSubcategorias() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: (datos: Subcategoria[]) => {
        this.lista = datos;
        this.cargando = false;
      },
      error: (err: any) => {
        this.error = 'Error al cargar subcategorías';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion: Observable<Subcategoria | void> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarSubcategorias();
        this.nuevo = { codigo: '', nombre: '', codigoCategoria: '' };
      },
      error: err => {
        this.error = 'Error al guardar la subcategoría';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar esta subcategoría?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarSubcategorias(),
      error: err => {
        this.error = 'Error al eliminar la subcategoría';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
