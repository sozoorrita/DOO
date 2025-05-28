import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventarioRoutingModule } from './inventario-routing.module';
import { CategoriasComponent } from './pages/categorias/categorias.component';
import { SubcategoriasComponent } from './pages/subcategorias/subcategorias.component';
import { ProductosComponent } from './pages/productos/productos.component';
import { MaterialModule } from '../../../material.module';

@NgModule({
  declarations: [
    CategoriasComponent,
    SubcategoriasComponent,
    ProductosComponent
  ],
  imports: [
    CommonModule,
    InventarioRoutingModule,
    MaterialModule
  ]
})
export class InventarioModule {}
