import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MesaListComponent } from './mesa-list/mesa-list.component';
import { MesaDetailComponent } from './mesa-detail/mesa-detail.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'list',
    pathMatch: 'full'
  },
  {
    path: 'list',
    component: MesaListComponent
  },
  {
    path: ':codigo',
    component: MesaDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MesaRoutingModule {}
