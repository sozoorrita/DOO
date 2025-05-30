import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InformeCajaListComponent } from './informe-caja-list/informe-caja-list.component';
import { InformeCajaDetailComponent } from './informe-caja-details/informe-caja-detail.component';

const routes: Routes = [
  {
    path: 'informe-caja',
    component: InformeCajaListComponent
  },
  {
    path: 'informe-caja/:codigo',
    component: InformeCajaDetailComponent
  }
  // Agrega aquí otras rutas de reportes si las tienes
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportesRoutingModule { }
