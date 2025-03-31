// src/app/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { ToastService } from './toast.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
    providedIn: 'root'
})
export class RegisterService {
    private apiUrl = 'http://localhost:8081/api/register';

    constructor(
        private http: HttpClient
    ) { }

    getClients(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/clients`);
    }
}
