import { Component, OnInit } from '@angular/core';
import { Mesa, MesaService } from '../../../core/services/mesa.service';

@Component({
  selector: 'app-mesa-list',
  standalone: false,
  templateUrl: './mesa-list.component.html',
  styleUrls: ['./mesa-list.component.css']
})
export class MesaListComponent implements OnInit {
  mesas: Mesa[] = [];
  formName = '';
  editing: Mesa | null = null;

  constructor(private mesaService: MesaService) {}

  ngOnInit(): void {
    this.mesaService.getMesas().subscribe(data => this.mesas = data);
  }

  startAdd(): void {
    this.editing = null;
    this.formName = '';
  }

  startEdit(m: Mesa): void {
    this.editing = m;
    this.formName = m.name;
  }

  save(): void {
    if (this.editing) {
      this.mesaService.updateMesa({...this.editing, name: this.formName});
    } else {
      this.mesaService.createMesa(this.formName);
    }
    this.startAdd();
  }

  delete(m: Mesa): void {
    this.mesaService.deleteMesa(m.id);
  }
}
