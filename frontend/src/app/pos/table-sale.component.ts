import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../mockData/products';
import { ProductService } from '../services/product.service';
import { SaleService, CartItem } from '../services/sale.service';

@Component({
  selector: 'app-table-sale',
  standalone: false,
  templateUrl: './table-sale.component.html',
  styleUrls: ['./table-sale.component.css']
})
export class TableSaleComponent implements OnInit {
  tableId: string | null = null;
  products: Product[] = [];
  filteredProducts: Product[] = [];
  cart: CartItem[] = [];
  searchTerm = '';
  selectedCategory = '';
  paymentMethod = 'Efectivo';
  categories: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private saleService: SaleService
  ) {}

  ngOnInit() {
    this.tableId = this.route.snapshot.paramMap.get('tableId');
    this.productService.getAll().subscribe(prods => {
      this.products = prods;
      this.filteredProducts = prods;
      this.categories = ['Todas', ...Array.from(new Set(prods.map(p => p.category)))];
      this.selectedCategory = 'Todas';
    });
    if (this.tableId) {
      this.cart = this.saleService.getCart(this.tableId) || [];
    }
  }

  filterProducts() {
    this.filteredProducts = this.products.filter(p =>
      (this.selectedCategory === 'Todas' || p.category === this.selectedCategory) &&
      p.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  addToCart(product: Product) {
    const item = this.cart.find(i => i.product.id === product.id);
    if (item) {
      item.quantity++;
    } else {
      this.cart.push({ product, quantity: 1 });
    }
    if (this.tableId) {
      this.saleService.setCart(this.tableId, this.cart);
    }
  }

  changeQuantity(item: CartItem, delta: number) {
    item.quantity += delta;
    if (item.quantity <= 0 && this.tableId) {
      this.removeFromCart(item.product.id);
    } else if (this.tableId) {
      this.saleService.setCart(this.tableId, this.cart);
    }
  }

  removeFromCart(productId: string) {
    this.cart = this.cart.filter(i => i.product.id !== productId);
    if (this.tableId) {
      this.saleService.setCart(this.tableId, this.cart);
    }
  }

  closeSale() {
    const saleId = Date.now().toString();
    this.router.navigate(
      ['/invoice', saleId],
      {
        state: {
          cart: this.cart,
          tableId: this.tableId,
          paymentMethod: this.paymentMethod,
          date: new Date().toLocaleString()
        }
      }
    );
    if (this.tableId) {
      this.saleService.clearCart(this.tableId);
    }
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }

  get total(): number {
    return this.cart.reduce((sum, i) => sum + i.product.price * i.quantity, 0);
  }
}
