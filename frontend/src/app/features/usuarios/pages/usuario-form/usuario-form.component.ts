import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioFacade } from '../../../../core/facades/usuario.facade';

@Component({
  selector: 'app-usuario-form',
  standalone: false,
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent implements OnInit {
  form: FormGroup;
  editing = false;

  constructor(
    private fb: FormBuilder,
    private facade: UsuarioFacade,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editing = true;
      this.facade.getById(id).subscribe(u => {
        this.form.patchValue(u);
        this.form.get('password')!.clearValidators();
        this.form.get('password')!.updateValueAndValidity();
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const payload = this.form.value;
    if (this.editing) {
      const id = this.route.snapshot.paramMap.get('id')!;
      this.facade.update(id, payload).subscribe(() => this.router.navigate(['usuarios']));
    } else {
      this.facade.create(payload).subscribe(() => this.router.navigate(['usuarios']));
    }
  }
}
