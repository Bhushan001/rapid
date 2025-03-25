import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-workspace',
  imports: [
    CommonModule
  ],
  templateUrl: './workspace.component.html',
  styleUrl: './workspace.component.scss'
})
export class WorkspaceComponent {
  page = 1;
  pageSize = 5;
  @Input() workspaces: any[] = [];
  @Input() selectedWorkspace: any;
  @Output() selectWorkspace = new EventEmitter<any>();
  currentPage = 1;
  itemsPerPage = 5;
  totalPages = 1;
  pagesArray: number[] = [];
  paginatedWorkspaces: any[] = [];

  // get paginatedWorkspaces() {
  //   const start = (this.page - 1) * this.pageSize;
  //   return this.workspaces.slice(start, start + this.pageSize);
  // }

  nextPage() {
    if ((this.page * this.pageSize) < this.workspaces.length) this.page++;
  }

  prevPage() {
    if (this.page > 1) this.page--;
  }

  onWorkspaceSelect(workspace: any) {
    this.selectWorkspace.emit(workspace);
  }

  ngOnInit(): void {
    this.calculatePagination();
  }

  ngOnChanges(): void {
    this.calculatePagination();
  }

  calculatePagination(): void {
    this.totalPages = Math.ceil(this.workspaces.length / this.itemsPerPage);
    this.pagesArray = Array.from({ length: this.totalPages }, (_, i) => i + 1);
    this.paginateData();
  }

  changePage(page: number): void {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;
    this.paginateData();
  }

  paginateData(): void {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    const end = start + this.itemsPerPage;
    this.paginatedWorkspaces = this.workspaces.slice(start, end);
  }
}
