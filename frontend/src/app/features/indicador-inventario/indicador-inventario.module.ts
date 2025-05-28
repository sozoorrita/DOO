// src/app/features/indicador-inventario/indicador-inventario.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { IndicadorInventarioRoutingModule } from './indicador-inventario-routing.module';
import { IndicadorInventarioListComponent } from './pages/indicador-inventario-list/indicador-inventario-list.component';
import { IndicadorInventarioFormComponent } from './pages/indicador-inventario-form/indicador-inventario-form.component';

@NgModule({
  declarations: [
    IndicadorInventarioListComponent,
    IndicadorInventarioFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    IndicadorInventarioRoutingModule
  ]
})
export class IndicadorInventarioModule {}
