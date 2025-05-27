import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriasComponent } from './pages/categorias/categorias.component';
import { SubcategoriasComponent } from './pages/subcategorias/subcategorias.component';
import { ProductosComponent } from './pages/productos/productos.component';

const routes: Routes = [
  { path: 'categorias', component: CategoriasComponent },
  { path: 'categorias/:catId/sub', component: SubcategoriasComponent },
  { path: 'productos/:subId', component: ProductosComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InventarioRoutingModule {}
