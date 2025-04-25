import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { ApiResponse } from "../../../model/request-schema.model";
import { PageableResponse } from "../../../model/pageable.model";
import { Permission } from "../../../model/permission.model";
import { CustomErrorResponse } from "../../../model/custom-error.response.model";


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