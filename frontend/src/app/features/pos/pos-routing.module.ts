import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MesasComponent } from './pages/mesas/mesas.component';
import { VentaNormalComponent } from './pages/venta-normal/venta-normal.component';
import { VentaMesaComponent } from './pages/venta-mesa/venta-mesa.component';

const routes: Routes = [
  { path: 'mesas', component: MesasComponent },
  { path: 'venta-normal', component: VentaNormalComponent },
  { path: 'venta-mesa/:mesaId', component: VentaMesaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PosRoutingModule {}
