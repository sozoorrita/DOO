export interface Session {
  id: string;
  date: string;
  user: string;
  startTime: string;
  endTime: string;
  total: number;
  cash: number;
  card: number;
  productsOutOfStock: string[];
}

export const sessions: Session[] = [
  {
    id: '123',
    date: '2025-05-29',
    user: 'Juan',
    startTime: '08:00',
    endTime: '12:00',
    total: 500,
    cash: 300,
    card: 200,
    productsOutOfStock: ['Pan', 'Huevos']
  }
];