import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { PageableResponse } from '../../../model/pageable.model';
import { CustomErrorResponse } from '../../../model/custom-error.response.model';
import { ApiResponse } from '../../../model/request-schema.model';
import { ClientRequest } from '../../../model/client.request.mode';
import { Client } from '../../../model/client.model';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private apiUrl = environment.apiUrl + '/clients'; // Adjust if needed

  constructor(private http: HttpClient) { }

  createClient(client: ClientRequest): Observable<ApiResponse<Client> | CustomErrorResponse> {
    return this.http.post<ApiResponse<Client> | CustomErrorResponse>(this.apiUrl, client);
  }

  updateClient(clientId: string, client: ClientRequest): Observable<ApiResponse<Client>> {
    return this.http.put<ApiResponse<Client>>(`${this.apiUrl}/${clientId}`, client);
  }

  deleteClient(clientId: string): Observable<ApiResponse<void> | CustomErrorResponse> {
    const url = `${this.apiUrl}/${clientId}`;
    return this.http.delete<ApiResponse<void> | CustomErrorResponse>(url);
  }

  getAllClients(): Observable<ApiResponse<Client[]>> {
    return this.http.get<ApiResponse<Client[]>>(this.apiUrl);
  }

  getAllClientsPage(page: number, pageSize: number): Observable<PageableResponse<Client>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', pageSize.toString());
    return this.http.get<PageableResponse<Client>>(`${this.apiUrl}`, { params });
  }
}