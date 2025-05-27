import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VentaFacade } from '../../../../core/facades/venta.facade';
import { ProductoFacade } from '../../../../core/facades/producto.facade';
import { VentaItem } from '../../../../core/models/venta-item.model';
import { Producto } from '../../../../core/models/producto.model';

@Component({
  selector: 'app-venta-form',
  standalone: false,
  templateUrl: './venta-form.component.html',
  styleUrls: ['./venta-form.component.css']
})
export class VentaFormComponent implements OnInit {
  form: FormGroup;
  productos: Producto[] = [];
  editing = false;

  constructor(
    private fb: FormBuilder,
    private facade: VentaFacade,
    private productoFacade: ProductoFacade,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      usuarioId: ['', Validators.required],
      fecha: [new Date().toISOString().substring(0,10), Validators.required],
      items: this.fb.array([]),
      total: [{ value: 0, disabled: true }]
    });
  }

  ngOnInit(): void {
    this.productoFacade.getAll().subscribe(data => this.productos = data);
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editing = true;
      this.facade.getById(id).subscribe(v => this.patchForm(v));
    }
    this.items.valueChanges.subscribe(() => this.calculateTotal());
  }

  get items(): FormArray {
    return this.form.get('items') as FormArray;
  }

  addItem(): void {
    const group = this.fb.group({
      productoId: ['', Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      precio: [{ value: 0, disabled: true }]
    });
    group.get('productoId')!.valueChanges.subscribe(pid => {
      const p = this.productos.find(x => x.id === pid);
      group.get('precio')!.setValue(p ? p.precio : 0);
    });
    this.items.push(group);
  }

  removeItem(i: number): void { this.items.removeAt(i); }

  calculateTotal(): void {
    const sum = this.items.controls
      .map(g => g.get('precio')!.value * g.get('cantidad')!.value)
      .reduce((a,b) => a+b, 0);
    this.form.get('total')!.setValue(sum);
  }

  patchForm(v: any): void {
    this.form.patchValue({ usuarioId: v.usuarioId, fecha: v.fecha, total: v.total });
    v.items.forEach((it: VentaItem) => {
      this.addItem();
      const idx = this.items.length - 1;
      this.items.at(idx).patchValue(it);
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const payload = this.form.getRawValue();
    if (this.editing) {
      const id = this.route.snapshot.paramMap.get('id')!;
      this.facade.update(id, payload).subscribe(() => this.router.navigate(['ventas']));
    } else {
      this.facade.create(payload).subscribe(() => this.router.navigate(['ventas']));
    }
  }
}
