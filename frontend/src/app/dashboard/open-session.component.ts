import { Component, OnInit } from '@angular/core';
import { TableService } from '../services/table.service';
import { Table } from '../../mockData/tables';
import { Router } from '@angular/router';

@Component({
  selector: 'app-open-session',
  standalone: false,
  templateUrl: './open-session.component.html',
  styleUrls: ['./open-session.component.css']
})
export class OpenSessionComponent implements OnInit {
  isSessionOpen = false;
  tables: Table[] = [];

  constructor(
    private tableService: TableService,
    private router: Router
  ) {}

  ngOnInit() {
    this.tableService.getAll().subscribe(t => this.tables = [...t]);
  }

  openSession() {
    this.isSessionOpen = true;
  }

  closeSession() {
    this.isSessionOpen = false;
  }

  createTable() {
    const label = prompt('Nombre de la nueva mesa:');
    if (label) {
      this.tables.push({ id: Date.now().toString(), label, status: 'libre' });
    }
  }

  editTable(table: Table) {
    const newLabel = prompt('Editar nombre de la mesa:', table.label);
    if (newLabel) table.label = newLabel;
  }

  deleteTable(table: Table) {
    if (confirm(`¿Eliminar ${table.label}?`)) {
      this.tables = this.tables.filter(t => t.id !== table.id);
    }
  }

  selectTable(table: Table) {
    this.router.navigate(['/sale', table.id]);
  }
}
