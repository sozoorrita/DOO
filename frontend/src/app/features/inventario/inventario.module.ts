import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { InventoryListComponent } from './inventory-list/inventory-list.component';
import { InventarioRoutingModule } from './inventario-routing.module';

@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    InventarioRoutingModule,
    InventoryListComponent
  ],
  exports: [
    InventoryListComponent
  ]
})
export class InventarioModule { }
