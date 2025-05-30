import { Injectable } from '@angular/core';
import { users, User } from '../../mockData/users';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUser: User | null = null;

  login(name: string, password: string): boolean {
    const user = users.find(u => u.name === name && u.password === password);
    if (user) this.currentUser = user;
    return !!user;
  }

  logout() { this.currentUser = null; }

  getUser() { return this.currentUser; }
}
