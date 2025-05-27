import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthFacade } from '../../../../core/facades/auth.facade';

@Component({
  selector: 'app-registro',
  standalone: false,
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {
  form!: FormGroup;
  errorMsg = '';

  constructor(
    private fb: FormBuilder,
    private facade: AuthFacade,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const { name, email, password } = this.form.value;
    this.facade.register(name, email, password).subscribe({
      next: res => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['/usuarios']);
      },
      error: err => {
        this.errorMsg = err.error?.message || 'Error al registrarse';
      }
    });
  }
}
