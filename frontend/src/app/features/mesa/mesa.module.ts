import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { MesaListComponent } from './mesa-list/mesa-list.component';
import { MesaDetailComponent } from './mesa-detail/mesa-detail.component';
import { MesaRoutingModule } from './mesa-routing.module';

@NgModule({
  declarations: [
    MesaListComponent,
    MesaDetailComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MesaRoutingModule
  ],
  exports: [
    MesaListComponent,
    MesaDetailComponent
  ]
})
export class MesaModule {}
