import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentaListComponent } from './pages/venta-list/venta-list.component';
import { VentaFormComponent } from './pages/venta-form/venta-form.component';

const routes: Routes = [
  { path: '', component: VentaListComponent },
  { path: 'nuevo', component: VentaFormComponent },
  { path: ':id/editar', component: VentaFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VentasRoutingModule {}
