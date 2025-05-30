import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { SessionListComponent } from './session-list/session-list.component';
import { SessionOpenComponent } from './session-open/session-open.component';
import { SessionRoutingModule } from './session-routing.module';

@NgModule({
  declarations: [
    SessionListComponent,
    SessionOpenComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    SessionRoutingModule
  ],
  exports: [
    SessionListComponent,
    SessionOpenComponent
  ]
})
export class SessionModule { }
