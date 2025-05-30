export interface Product {
  id: string;
  name: string;
  category: string;
  subcategory: string;
  price: number;
  stock: number;
}

export const products: Product[] = [
  { id: 'p1', name: 'Pan', category: 'Alimentos', subcategory: 'Carne', price: 2.5, stock: 20 },
  { id: 'p2', name: 'Leche', category: 'Bebidas', subcategory: 'Lácteos', price: 1.2, stock: 5 },
  { id: 'p3', name: 'Huevos', category: 'Alimentos', subcategory: 'Ovo', price: 0.3, stock: 0 },
];