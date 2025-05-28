export type MesaEstado = 'disponible' | 'ocupada' | 'reservada';

export interface Mesa {
  id: string;
  numero: number;
  estado: MesaEstado;
}
