import { VentaItem } from './venta-item.model';
export interface Venta {
  id: string;
  usuarioId: string;
  mesaId?: string;  // opcional si es venta de mesa
  fecha: string;
  items: VentaItem[];
  total: number;
}
