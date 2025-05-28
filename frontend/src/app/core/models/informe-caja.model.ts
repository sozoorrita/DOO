export interface InformeCajaRequest {
  fechaInicio: string; // YYYY-MM-DD
  fechaFin: string;
}

export interface InformeCajaResponse {
  totalVentas: number;
  totalIngresos: number;
}