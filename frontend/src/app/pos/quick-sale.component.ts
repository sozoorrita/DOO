import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../../mockData/products';
import { ProductService } from '../services/product.service';

interface CartItem {
  product: Product;
  quantity: number;
}

@Component({
  selector: 'app-quick-sale',
  standalone: false,
  templateUrl: './quick-sale.component.html',
  styleUrls: ['./quick-sale.component.css']
})
export class QuickSaleComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  cart: CartItem[] = [];
  searchTerm = '';
  selectedCategory = '';
  paymentMethod = 'Efectivo';
  categories: string[] = [];

  constructor(
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit() {
    this.productService.getAll().subscribe(prods => {
      this.products = prods;
      this.filteredProducts = prods;
      this.categories = ['Todas', ...new Set(prods.map(p => p.category))];
      this.selectedCategory = 'Todas';
    });
  }

  filterProducts() {
    this.filteredProducts = this.products.filter(p =>
      (this.selectedCategory === 'Todas' || p.category === this.selectedCategory) &&
      p.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  addToCart(product: Product) {
    const item = this.cart.find(i => i.product.id === product.id);
    if (item) item.quantity++;
    else this.cart.push({ product, quantity: 1 });
  }

  removeFromCart(productId: string) {
    this.cart = this.cart.filter(i => i.product.id !== productId);
  }

  changeQuantity(item: CartItem, delta: number) {
    item.quantity += delta;
    if (item.quantity <= 0) this.removeFromCart(item.product.id);
  }

  get total(): number {
    return this.cart.reduce((sum, i) => sum + i.product.price * i.quantity, 0);
  }

  generateInvoice() {
    const invoiceId = 'quick-' + Date.now();
    this.router.navigate(
      ['/invoice', invoiceId],
      {
        state: {
          cart: this.cart,
          paymentMethod: this.paymentMethod,
          date: new Date().toLocaleString()
        }
      }
    );
    // limpiar carrito
    this.cart = [];
    this.paymentMethod = 'Efectivo';
  }
}
