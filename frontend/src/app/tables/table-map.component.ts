import { Component, OnInit } from '@angular/core';
import { TableService } from '../services/table.service';
import { Table } from '../../mockData/tables';
import { Router } from '@angular/router';

@Component({
  selector: 'app-table-map',
  standalone: false,
  templateUrl: './table-map.component.html',
  styleUrls: ['./table-map.component.css']
})
export class TableMapComponent implements OnInit {
  tables: Table[] = [];

  constructor(
    private tableService: TableService,
    private router: Router
  ) {}

  ngOnInit() {
    this.tableService.getAll().subscribe(t => this.tables = t);
  }

  selectTable(table: Table) {
    if (table.status === 'libre') {
      this.router.navigate(['/sale', table.id]);
    }
  }
}