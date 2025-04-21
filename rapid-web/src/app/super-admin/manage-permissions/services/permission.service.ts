import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ApiResponse } from "../../../home/model/request-schema.model";
import { Observable } from "rxjs";
import { PageableResponse } from "../../../home/model/pageable.model";

export interface Permission {
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
    // Add other permission properties as needed
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
export class PermissionService {
    private apiUrl = environment.apiUrl + '/permissions'; // Adjust if needed

    constructor(private http: HttpClient) { }

    createPermission(permission: Permission): Observable<ApiResponse<Permission> | CustomErrorResponse> {
        return this.http.post<ApiResponse<Permission> | CustomErrorResponse>(this.apiUrl, permission);
    }

    updatePermission(id: string, permission: Permission): Observable<ApiResponse<Permission>> {
        return this.http.put<ApiResponse<Permission>>(`${this.apiUrl}/${id}`, permission);
    }

    deletePermission(permissionId: string): Observable<ApiResponse<void> | CustomErrorResponse> {
        const url = `${this.apiUrl}/${permissionId}`;
        return this.http.delete<ApiResponse<void> | CustomErrorResponse>(url);
    }

    getAllPermissions(): Observable<ApiResponse<Permission[]>> {
        return this.http.get<ApiResponse<Permission[]>>(`${this.apiUrl}/dtos`);
    }

    getPermissionsPage(page: number, pageSize: number): Observable<PageableResponse<Permission>> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('size', pageSize.toString());
        return this.http.get<PageableResponse<Permission>>(`${this.apiUrl}`, { params });
    }
}