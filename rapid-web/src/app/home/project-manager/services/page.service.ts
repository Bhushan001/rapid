import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { PageableResponse } from '../../../model/pageable.model';
import { Page } from '../../../model/page.model';

@Injectable({
  providedIn: 'root'
})
export class PageService {
  private apiUrl = environment.designerUrl + '/api/projects';

  constructor(private http: HttpClient) { }

  getPagesByProjectId(selectedProjectId: string, page: number, pageSize: number): Observable<PageableResponse<Page>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', pageSize.toString());
    return this.http.get<PageableResponse<Page>>(`${this.apiUrl}/${selectedProjectId}/pages`, { params });
  }

  getPageByProjectIdAndPageId(selectedProjectId: string, id: string): Observable<Page> {
    return this.http.get<Page>(`${this.apiUrl}/${selectedProjectId}/pages/${id}`);
  }

  createPage(selectedProjectId: string, page: Partial<Page>): Observable<Page> {
    return this.http.post<Page>(`${this.apiUrl}/${selectedProjectId}/pages`, page);
  }

  updatePage(selectedProjectId: string, pageId: string, page: Partial<Page>): Observable<Page> {
    return this.http.put<Page>(`${this.apiUrl}/${selectedProjectId}/pages/${pageId}`, page);
  }

  deletePage(selectedProjectId: string, id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${selectedProjectId}/pages/${id}`);
  }
}