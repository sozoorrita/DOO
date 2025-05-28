export interface Session {
  id: string;
  usuarioId: string;
  fechaInicio: string;   // ISO date string
  baseCaja: number;
  fechaCierre?: string;  // ISO date string al cerrar
  totalCierre?: number;
}
