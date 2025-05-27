export interface Session {
  id: string;
  usuarioId: string;
  fechaInicio: string;
  baseCaja: number;
  fechaCierre?: string;
  totalCierre?: number;
}
