// src/app/features/estado-mesa/estado-mesa.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { EstadoMesaRoutingModule } from './estado-mesa-routing.module';
import { EstadoMesaListComponent } from './pages/estado-mesa-list/estado-mesa-list.component';
import { EstadoMesaFormComponent } from './pages/estado-mesa-form/estado-mesa-form.component';

@NgModule({
  declarations: [
    EstadoMesaListComponent,
    EstadoMesaFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    EstadoMesaRoutingModule
  ]
})
export class EstadoMesaModule { }
