import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SessionFacade } from '../../../../core/facades/session.facade';
import { Router } from '@angular/router';

@Component({
  selector: 'app-abrir-sesion',
  templateUrl: './abrir-sesion.component.html',
  styleUrls: ['./abrir-sesion.component.css']
})
export class AbrirSesionComponent implements OnInit {
  form!: FormGroup;
  constructor(
    private fb: FormBuilder,
    private facade: SessionFacade,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.form = this.fb.group({
      usuarioId: ['', Validators.required],
      baseCaja: [0, [Validators.required, Validators.min(0)]]
    });
  }
  onSubmit(): void {
    if (this.form.invalid) return;
    this.facade.open(this.form.value).subscribe(() => {
      this.router.navigate(['/pos/mesas']);
    });
  }
}
