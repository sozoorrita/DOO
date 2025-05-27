import { Component, OnInit } from '@angular/core';
import { UsuarioFacade } from '../../../../core/facades/usuario.facade';
import { Usuario } from '../../../../core/models/usuario.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuario-list',
  standalone: false,
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {
  usuarios: Usuario[] = [];
  displayedColumns = ['id', 'nombre', 'email', 'acciones'];

  constructor(private facade: UsuarioFacade, private router: Router) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.facade.getAll().subscribe(data => this.usuarios = data);
  }

  onNuevo(): void { this.router.navigate(['usuarios/nuevo']); }
  onEditar(id: string): void { this.router.navigate([`usuarios/${id}/editar`]); }
  onEliminar(id: string): void { this.facade.delete(id).subscribe(() => this.load()); }
}
