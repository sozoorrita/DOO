import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Usuario {
  id: string;
  name: string;
  role: string;
}

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private usersSubject = new BehaviorSubject<Usuario[]>([
    { id: 'u1', name: 'Admin', role: 'Administrador' },
    { id: 'u2', name: 'Cajero1', role: 'Cajero' }
  ]);

  getUsers(): Observable<Usuario[]> {
    return this.usersSubject.asObservable();
  }

  addUser(u: Usuario): void {
    this.usersSubject.next([...this.usersSubject.getValue(), u]);
  }

  updateUser(u: Usuario): void {
    const updated = this.usersSubject.getValue().map(x => x.id === u.id ? u : x);
    this.usersSubject.next(updated);
  }

  deleteUser(id: string): void {
    this.usersSubject.next(this.usersSubject.getValue().filter(x => x.id !== id));
  }
}
