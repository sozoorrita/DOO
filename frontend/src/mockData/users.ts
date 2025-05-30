export interface User {
  id: string;
  name: string;
  password: string;
  role: 'mesero' | 'administrador';
}

export const users: User[] = [
  { id: '1', name: 'juan', password: 'abc123', role: 'mesero' },
  { id: '2', name: 'ana', password: '123abc', role: 'administrador' },
];