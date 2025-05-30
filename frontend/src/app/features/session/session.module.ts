import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SessionRoutingModule } from './session-routing.module';
import { SessionListComponent } from './session-list/session-list.component';

// Importa tu MaterialModule en lugar de sólo MatButtonModule
import { MaterialModule } from '../../material.module';

@NgModule({
  declarations: [SessionListComponent],
  imports: [
    CommonModule,
    SessionRoutingModule,
    MaterialModule      // <-- aquí
  ]
})
export class SessionModule {}
