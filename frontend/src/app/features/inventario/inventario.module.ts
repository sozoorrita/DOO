import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventarioRoutingModule } from './inventario-routing.module';
import { InventoryListComponent } from './inventory-list/inventory-list.component';
import { MaterialModule } from '../../material.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [InventoryListComponent],
  imports: [CommonModule, InventarioRoutingModule, MaterialModule, FormsModule]
})
export class InventarioModule {}
