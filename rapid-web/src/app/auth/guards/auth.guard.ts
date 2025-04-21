import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { catchError, map, Observable, of, tap } from 'rxjs';


@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private _authService: AuthService
    ) { }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this._authService.validateToken().pipe(
            map((isAuthenticated) => {
                console.log("is User authenticated to access this route");
                console.log(isAuthenticated);
                if (isAuthenticated) {
                    return true;
                } else {
                    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
                    return false;
                }
            }),
            catchError(() => {
                this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
                return of(false);
            })
        );
    }
}
