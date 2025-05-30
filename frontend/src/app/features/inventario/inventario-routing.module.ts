import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventoryListComponent } from './inventory-list/inventory-list.component';

const routes: Routes = [
  {
    path: 'inventario',
    component: InventoryListComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InventarioRoutingModule { }
