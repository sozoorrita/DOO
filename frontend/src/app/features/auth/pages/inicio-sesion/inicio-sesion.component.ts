import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthFacade } from '../../../../core/facades/auth.facade';

@Component({
  selector: 'app-inicio-sesion',
  standalone: false,
  templateUrl: './inicio-sesion.component.html',
  styleUrls: ['./inicio-sesion.component.css']
})
export class InicioSesionComponent implements OnInit {
  form!: FormGroup;
  errorMsg = '';

  constructor(
    private fb: FormBuilder,
    private facade: AuthFacade,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const { email, password } = this.form.value;
    this.facade.login(email, password).subscribe({
      next: res => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['/session/abrir']);
      },
      error: err => {
        this.errorMsg = err.error?.message || 'Error al iniciar sesión';
      }
    });
  }
}
