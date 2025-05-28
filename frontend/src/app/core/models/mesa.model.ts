export type MesaEstado = 'disponible' | 'ocupada' | 'reservada';

export interface Mesa {
  codigo: string;
  nombre: number;
  codigoEstadoMesa: MesaEstado;
}
