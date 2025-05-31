// src/app/features/inventario/inventario.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';                   // <<< FormsModule
import { InventarioRoutingModule } from './inventario-routing.module';
import { InventoryListComponent } from './inventory-list/inventory-list.component';

@NgModule({
  declarations: [
    InventoryListComponent   // Declaramos el componente aquí
  ],
  imports: [
    CommonModule,
    FormsModule,             // <<< Debe estar importado para ngModel
    InventarioRoutingModule
  ]
})
export class InventarioModule {}
