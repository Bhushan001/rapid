// src/app/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../model/user.model';
import { environment } from '../../../../environments/environment';
import { PageableResponse } from '../../../model/pageable.model';

@Injectable({
    providedIn: 'root'
})
export class AdminService {

    constructor(
        private http: HttpClient
    ) { }

    getUsers(page: number, pageSize: number): Observable<PageableResponse<User>> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', pageSize.toString());
        return this.http.get<PageableResponse<User>>(`${environment.apiUrl}/users`, { params });
    }
}
