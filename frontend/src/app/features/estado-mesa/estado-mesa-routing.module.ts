// src/app/features/estado-mesa/estado-mesa-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EstadoMesaListComponent } from './pages/estado-mesa-list/estado-mesa-list.component';
import { EstadoMesaFormComponent } from './pages/estado-mesa-form/estado-mesa-form.component';

const routes: Routes = [
  { path: '', component: EstadoMesaListComponent },
  { path: 'nuevo', component: EstadoMesaFormComponent },
  { path: 'editar/:id', component: EstadoMesaFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstadoMesaRoutingModule { }
