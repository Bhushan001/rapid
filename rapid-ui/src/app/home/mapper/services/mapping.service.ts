import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mapping } from '../../model/mapping.model';

@Injectable({
    providedIn: 'root'
})
export class MappingService {
    private baseUrl = 'http://localhost:8080/api/mappings'; // Your Spring Boot API URL

    constructor(private http: HttpClient) { }

    getAllMappings(): Observable<Mapping[]> {
        return this.http.get<Mapping[]>(this.baseUrl);
    }

    getAllMappingByRequestSchemaId(id: string): Observable<Mapping[]> {
        return this.http.get<Mapping[]>(`${this.baseUrl}/requestschema/${id}`);
    }

    getMappingById(id: string): Observable<Mapping> {
        return this.http.get<Mapping>(`${this.baseUrl}/${id}`);
    }

    saveMapping(mapping: Mapping, selectedRequestSchemaId: string): Observable<Mapping> {
        return this.http.post<Mapping>(`${this.baseUrl}/${selectedRequestSchemaId}`, mapping);
    }

    updateMapping(id: string, requestSchema: Mapping): Observable<Mapping> {
        return this.http.put<Mapping>(`${this.baseUrl}/${id}`, requestSchema);
    }

    deleteMapping(id: string): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}