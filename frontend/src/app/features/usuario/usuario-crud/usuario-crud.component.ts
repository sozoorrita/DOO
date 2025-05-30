import { Component, OnInit } from '@angular/core';
import { UsuarioService, Usuario } from '../../../core/services/usuario.service';

@Component({
  selector: 'app-usuario-crud',
    standalone: false,
  templateUrl: './usuario-crud.component.html',
  styleUrls: ['./usuario-crud.component.css']
})
export class UsuarioCrudComponent implements OnInit {
  users: Usuario[] = [];
  editing: Usuario | null = null;
  form = { name: '', role: '' };

  constructor(private userService: UsuarioService) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data => this.users = data);
  }

  startAdd(): void {
    this.editing = null;
    this.form = { name: '', role: '' };
  }

  startEdit(u: Usuario): void {
    this.editing = u;
    this.form = { name: u.name, role: u.role };
  }

  save(): void {
    if (this.editing) {
      this.userService.updateUser({ ...this.editing, ...this.form });
    } else {
      this.userService.addUser({ id: Date.now().toString(), ...this.form });
    }
    this.form = { name: '', role: '' };
    this.editing = null;
  }

  delete(u: Usuario): void {
    this.userService.deleteUser(u.id);
  }
}
