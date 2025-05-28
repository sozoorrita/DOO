import { Component, OnInit } from '@angular/core';
import { Categoria } from '../../../../core/models/categoria.model';
import { InventarioFacade } from '../../../../core/facades/inventario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categorias',
    standalone: false,
  templateUrl: './categorias.component.html'
})
export class CategoriasComponent implements OnInit {
  categorias: Categoria[] = [];
  constructor(private facade: InventarioFacade, private router: Router) {}
  ngOnInit(): void {
    this.facade.getCategorias().subscribe(data => this.categorias = data);
  }
  verSub(catId: string): void {
    this.router.navigate(['/inventario/categorias', catId, 'sub']);
  }
}
