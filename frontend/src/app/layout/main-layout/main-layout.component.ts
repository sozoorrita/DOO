import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  standalone: false,
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent {
  public currentUrl: string = '';

  constructor(
    public router: Router,
    private authService: AuthService
  ) {
    this.currentUrl = this.router.url;

    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.currentUrl = event.urlAfterRedirects;
      });
  }

  isAuthRoute(): boolean {
    return this.currentUrl.startsWith('/auth');
  }

  logout() {
    this.authService.logout(); // ← aquí invalida sesión y borra datos
    this.router.navigate(['/auth/login']);
  }
}
