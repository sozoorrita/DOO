import { Component } from '@angular/core';
import { Router }    from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  name = '';
  password = '';
  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.auth.login(this.name, this.password)) {
      this.router.navigate(['/session/list']);
    } else {
      this.error = 'Usuario o contraseña inválidos';
    }
  }
}
