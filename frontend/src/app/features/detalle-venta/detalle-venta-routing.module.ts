import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetalleVentaListComponent } from './pages/detalle-venta-list/detalle-venta-list.component';
import { DetalleVentaFormComponent } from './pages/detalle-venta-form/detalle-venta-form.component';

const routes: Routes = [
  { path: '', component: DetalleVentaListComponent },
  { path: 'nuevo', component: DetalleVentaFormComponent },
  { path: 'editar/:id', component: DetalleVentaFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DetalleVentaRoutingModule { }
