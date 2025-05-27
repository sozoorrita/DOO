import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AbrirSesionComponent } from './pages/abrir-sesion/abrir-sesion.component';
import { InformeCajaComponent } from './pages/informe-caja/informe-caja.component';

const routes: Routes = [
  { path: 'abrir', component: AbrirSesionComponent },
  { path: 'cierre', component: InformeCajaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SessionRoutingModule {}
