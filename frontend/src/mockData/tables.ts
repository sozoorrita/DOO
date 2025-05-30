export interface Table {
  id: string;
  label: string;
  status: 'libre' | 'ocupada' | 'reservada';
}

export const tables: Table[] = [
  { id: '1', label: 'Mesa 1', status: 'libre' },
  { id: '2', label: 'Mesa 2', status: 'ocupada' },
  { id: '3', label: 'Mesa 3', status: 'reservada' },
  { id: '4', label: 'Mesa 4', status: 'libre' },
];