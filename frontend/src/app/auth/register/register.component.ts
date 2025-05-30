import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { users, User } from '../../../mockData/users';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-register',
    standalone: false,
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent {
    name = '';
    role: 'mesero' | 'administrador' = 'mesero';
    password = '';
    confirmPassword = '';

    constructor(
        private auth: AuthService,
        private router: Router
    ) { }

    onRegister() {
        if (!this.name || !this.password) {
            alert('Todos los campos son obligatorios');
            return;
        }
        if (this.password !== this.confirmPassword) {
            alert('Las contraseñas no coinciden');
            return;
        }
        const newUser: User = {
            id: (users.length + 1).toString(),
            name: this.name,
            role: this.role,
            password: this.password
        };
        users.push(newUser);
        alert('Cuenta creada exitosamente');
        this.router.navigate(['/login']);
    }

    goToLogin() {
        this.router.navigate(['/login']);
    }
}
