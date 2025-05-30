import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { ReportesRoutingModule } from './reportes-routing.module';
import { InformeCajaListComponent } from './informe-caja-list/informe-caja-list.component';
import { InformeCajaDetailComponent } from './informe-caja-details/informe-caja-detail.component';

@NgModule({
  declarations: [
    InformeCajaListComponent,
    InformeCajaDetailComponent
    // Agrega aquí otros componentes de reportes si los tienes
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    ReportesRoutingModule
  ],
  exports: [
    InformeCajaListComponent,
    InformeCajaDetailComponent
  ]
})
export class ReportesModule { }
