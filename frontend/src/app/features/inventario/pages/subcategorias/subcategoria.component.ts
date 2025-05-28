import { Component, OnInit } from '@angular/core';
import { Subcategoria } from '../../../../core/models/subcategoria.model';
import { InventarioFacade } from '../../../../core/facades/inventario.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-subcategorias',
    standalone: false,
  templateUrl: './subcategorias.component.html'
})
export class SubcategoriasComponent implements OnInit {
  subcats: Subcategoria[] = [];
  catId!: string;
  constructor(
    private facade: InventarioFacade,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.catId = this.route.snapshot.paramMap.get('catId')!;
    this.facade.getSubcategorias(this.catId).subscribe(data => this.subcats = data);
  }
  verProductos(subId: string): void {
    this.router.navigate(['/inventario/productos', subId]);
  }
}
