import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ApiResponse } from "../../../home/model/request-schema.model";
import { Observable } from "rxjs";
import { PageableResponse } from "../../../home/model/pageable.model";

export interface Role {
    id?: string; // UUID, optional for create
    name: string;
    code: string;
    permissions: any;
    createdBy?: string;
    createdByName?: string;
    createdOn?: any;
    updatedBy?: string;
    updatedByName?: string;
    updatedOn?: any;
    // Add other role properties as needed
}

export interface CustomErrorResponse {
    statusCode: number;
    status: string;
    errorCode: string;
    message: string;
  }

@Injectable({
    providedIn: 'root',
})
export class RoleService {
    private apiUrl = environment.apiUrl + '/roles'; // Adjust if needed

    constructor(private http: HttpClient) { }

    createRole(role: Role): Observable<ApiResponse<Role> | CustomErrorResponse> {
        return this.http.post<ApiResponse<Role> | CustomErrorResponse>(this.apiUrl, role);
    }

    updateRole(id: string, role: Role): Observable<ApiResponse<Role>> {
        return this.http.put<ApiResponse<Role>>(`${this.apiUrl}/${id}`, role);
    }

    deleteRole(roleId: string): Observable<ApiResponse<void> | CustomErrorResponse> {
        const url = `${this.apiUrl}/${roleId}`;
        return this.http.delete<ApiResponse<void> | CustomErrorResponse>(url);
    }

    getAllRoles(): Observable<ApiResponse<Role[]>> {
        return this.http.get<ApiResponse<Role[]>>(`${this.apiUrl}/dtos`);
    }

    getRolesPage(page: number, pageSize: number): Observable<PageableResponse<Role>> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', pageSize.toString());
        return this.http.get<PageableResponse<Role>>(`${this.apiUrl}`, { params });
    }
}