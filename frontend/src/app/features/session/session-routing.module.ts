import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SessionListComponent } from './session-list/session-list.component';
import { SessionOpenComponent } from './session-open/session-open.component';

const routes: Routes = [
  {
    path: 'session-list',
    component: SessionListComponent
  },
  {
    path: 'session-open',
    component: SessionOpenComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SessionRoutingModule { }
