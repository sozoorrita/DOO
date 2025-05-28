export interface IndicadorInventario {
  codigo: string;
  codigoProducto: string;
  fecha: string;   // ISO date string
  cantidadInicial: number;
  cantidadFinal: number;
}