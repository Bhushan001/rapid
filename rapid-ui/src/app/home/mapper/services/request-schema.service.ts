import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RequestSchema } from '../../model/request-schema.model';

@Injectable({
    providedIn: 'root'
})
export class RequestSchemaService {
    private baseUrl = 'http://localhost:8080/api/request-schemas'; // Your Spring Boot API URL

    constructor(private http: HttpClient) { }

    getAllRequestSchemas(): Observable<RequestSchema[]> {
        return this.http.get<RequestSchema[]>(this.baseUrl);
    }

    getRequestSchemaById(id: string): Observable<RequestSchema> {
        return this.http.get<RequestSchema>(`${this.baseUrl}/${id}`);
    }

    createRequestSchema(formData: FormData): Observable<RequestSchema> {
        return this.http.post<RequestSchema>(this.baseUrl, formData);
    }

    updateRequestSchema(id: string, requestSchema: RequestSchema): Observable<RequestSchema> {
        return this.http.put<RequestSchema>(`${this.baseUrl}/${id}`, requestSchema);
    }

    deleteRequestSchema(id: string): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}