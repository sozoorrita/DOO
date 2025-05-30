import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InformeCajaComponent } from './informe-caja/informe-caja.component';

const routes: Routes = [
  { path: 'informe-caja', component: InformeCajaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportesRoutingModule {}
