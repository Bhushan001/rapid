// src/app/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { ToastService } from './toast.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl = 'http://localhost:8081/api';
    private jwtHelper = new JwtHelperService();

    constructor(
        private http: HttpClient,
        private toastr: ToastService
    ) { }

    getRoles(): string[] {
        const token = localStorage.getItem('authToken');
        if (token) {
            const decodedToken = this.jwtHelper.decodeToken(token);
            return decodedToken?.roles || []; // Assuming roles are in 'roles' claim
        }
        return [];
    }

    hasRole(role: string): boolean {
        return this.getRoles().includes(role);
    }

    validateToken(): Observable<boolean> {
        return this.http.get<boolean>(`${this.apiUrl}/jwt/validate-token`);
    }

    isAuthenticated(): boolean {
        return true;
    }

    signup(payload: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/auth/signup`, payload);
    }

    login(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/auth/login`, credentials)
            .pipe(
                catchError(this.handleError.bind(this)) // Bind 'this' to handleError
            );
    }

    logout(): Observable<any> {
        return this.http.post(`${this.apiUrl}/auth/logout`, null) // Send a POST request to /logout
            .pipe(
                catchError(this.handleError.bind(this)) // Bind 'this' to handleError
            );
    }

    private handleError(error: HttpErrorResponse) {
        let errorMessage = 'An unknown error occurred; please try again later.';

        if (error.status === 0) {
            // A client-side or network error occurred.
            console.error('An error occurred:', error.error);
            errorMessage = 'Network error: Please check your connection.';
        } else {
            // The backend returned an unsuccessful response code.
            console.error(
                `Backend returned code ${error.status}, body was: `, error.error);
            if (error.error && error.error.message) {
                errorMessage = error.error.message;
            } else if (error.error && typeof error.error === 'string') {
                errorMessage = error.error;
            } else {
                errorMessage = `Backend error: ${error.status}`;
            }
        }
        this.toastr.showToast('Failed', errorMessage, 'Error'); // Display toast message
        return throwError(() => new Error(errorMessage));
    }
}
