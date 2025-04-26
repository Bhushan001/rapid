import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class RolesGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const requiredRoles: string[] = route.data['roles'] || [];   
    if (requiredRoles.length === 0) {
      return true; // No specific roles required
    }
    return requiredRoles.some(role => this.authService.hasRole(role)) || this.router.parseUrl('/unauthorized');
  }
}