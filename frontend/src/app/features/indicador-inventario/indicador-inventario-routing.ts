// src/app/features/indicador-inventario/indicador-inventario-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndicadorInventarioListComponent } from './pages/indicador-inventario-list/indicador-inventario-list.component';
import { IndicadorInventarioFormComponent } from './pages/indicador-inventario-form/indicador-inventario-form.component';

const routes: Routes = [
  { path: '', component: IndicadorInventarioListComponent },
  { path: 'nuevo', component: IndicadorInventarioFormComponent },
  { path: 'editar/:id', component: IndicadorInventarioFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IndicadorInventarioRoutingModule {}
