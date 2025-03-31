import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { S1Schema } from '../../model/s1-schema.model';

@Injectable({
    providedIn: 'root'
})
export class S1SchemaService {
    private baseUrl = 'http://localhost:8080/api/s1-schemas'; // Your Spring Boot API URL

    constructor(private http: HttpClient) { }

    getAllS1Schemas(): Observable<S1Schema[]> {
        return this.http.get<S1Schema[]>(this.baseUrl);
    }

    getAllS1SchemasByRequestSchemaId(id: string): Observable<S1Schema[]> {
        return this.http.get<S1Schema[]>(`${this.baseUrl}/requestschema/${id}`);
    }

    getS1SchemaById(id: string): Observable<S1Schema> {
        return this.http.get<S1Schema>(`${this.baseUrl}/${id}`);
    }

    createS1Schema(formData: FormData): Observable<S1Schema> {
        return this.http.post<S1Schema>(this.baseUrl, formData);
    }

    updateS1Schema(id: string, requestSchema: S1Schema): Observable<S1Schema> {
        return this.http.put<S1Schema>(`${this.baseUrl}/${id}`, requestSchema);
    }

    deleteS1Schema(id: string): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}