import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InventarioRoutingModule } from './inventario-routing.module';
import { InventoryListComponent } from './inventory-list/inventory-list.component';

@NgModule({
  declarations: [InventoryListComponent],
  imports: [
    CommonModule,
    FormsModule,
    InventarioRoutingModule
  ]
})
export class InventarioModule {}
