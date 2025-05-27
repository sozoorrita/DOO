import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductoListComponent } from './pages/producto-list/producto-list.component';
import { ProductoFormComponent } from './pages/producto-form/producto-form.component';

const routes: Routes = [
  { path: '', component: ProductoListComponent },
  { path: 'nuevo', component: ProductoFormComponent },
  { path: ':id/editar', component: ProductoFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductosRoutingModule {}
