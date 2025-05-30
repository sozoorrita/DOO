// src/mockData/products.ts
export interface Product {
  id: string;
  name: string;
  category: 'Bebidas' | 'Mecato' | 'Cigarrillos';
  subcategory: 'Alcohólica' | 'Gaseosa' | 'Pasabocas' | 'Dulces' | 'Medio paquete' | 'Paquete';
  price: number;    // en pesos colombianos
  stock: number;
}

export const products: Product[] = [
  { id:'b1',  name:'Cerveza Pilsen', category:'Bebidas', subcategory:'Alcohólica', price:2500, stock:50 },
  { id:'g1',  name:'Vitafer',       category:'Bebidas', subcategory:'Gaseosa',    price:1500, stock:30 },
  { id:'d1',  name:'Keta',     category:'Mecato',  subcategory:'Pasabocas',  price:1200, stock:20 },
  { id:'c1',  name:'Caneludo',        category:'Cigarrillos', subcategory:'Paquete', price:12000,stock:40 },
  // etc.
];
