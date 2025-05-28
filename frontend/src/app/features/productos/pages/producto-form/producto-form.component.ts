import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoFacade } from '../../../../core/facades/producto.service';

@Component({
  selector: 'app-producto-form',
  standalone: false,
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.css']
})
export class ProductoFormComponent implements OnInit {
  form: FormGroup;
  editing = false;

  constructor(
    private fb: FormBuilder,
    private facade: ProductoFacade,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: [''],
      precio: [0, [Validators.required, Validators.min(0.01)]],
      stock: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editing = true;
      this.facade.getById(id).subscribe(p => this.form.patchValue(p));
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const payload = this.form.value;
    if (this.editing) {
      const id = this.route.snapshot.paramMap.get('id')!;
      this.facade.update(id, payload).subscribe(() => this.router.navigate(['productos']));
    } else {
      this.facade.create(payload).subscribe(() => this.router.navigate(['productos']));
    }
  }
}
