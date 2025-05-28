export interface Producto {
  codigo: string;
  nombre: string;
  descripcion?: string;
  precio: number;
  codigoCategoria: string;
  codigoSubcategoria: string;
}