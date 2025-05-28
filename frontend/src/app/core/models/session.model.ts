export interface Session {
  codigo: string;
  Idusuario: string;
  nombreUsuario: string;   // ISO date string
  baseCaja: number;
  fechaApertura: string;  // ISO date string al cerrar
  fechaCierre: number;
}
