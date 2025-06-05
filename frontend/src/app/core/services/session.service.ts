// src/app/core/services/session.service.ts

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  constructor() {}

  /**
   * Retorna true si el usuario tiene sesión activa.
   * Revisamos si hay un UUID guardado en localStorage.
   */
  hasActiveSession(): boolean {
    return !!localStorage.getItem('usuarioId');
  }

  /** Guardar el UUID del usuario */
  setUserId(id: string): void {
    localStorage.setItem('usuarioId', id);
  }

  /** Obtener el UUID del usuario */
  getUserId(): string | null {
    return localStorage.getItem('usuarioId');
  }

  /** Limpiar la sesión completamente */
  clearSession(): void {
    localStorage.removeItem('usuarioId');
  }
}
