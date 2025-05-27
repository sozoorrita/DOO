// src/app/features/ventas/ventas-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentaListComponent } from './pages/venta-list/venta-list.component';
import { VentaFormComponent } from './pages/venta-form/venta-form.component';
import { PosVentaComponent } from './pages/pos-venta/pos-venta.component';

const routes: Routes = [
  { path: '', component: VentaListComponent },
  { path: 'nuevo', component: VentaFormComponent },
  { path: ':id/editar', component: VentaFormComponent },
  { path: 'pos', component: PosVentaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]          // <- aquí exportas RouterModule
})
export class VentasRoutingModule {}  // <- y aquí exportas VentasRoutingModule
