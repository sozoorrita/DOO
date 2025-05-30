import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentaRapidaComponent } from './venta-rapida/venta-rapida.component';

const routes: Routes = [
  {
    path: 'venta-rapida',
    component: VentaRapidaComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VentaRapidaRoutingModule { }
