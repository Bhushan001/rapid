import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-project',
  imports: [
    CommonModule
  ],
  templateUrl: './project.component.html',
  styleUrl: './project.component.scss'
})
export class ProjectComponent {
  @Input() projects: any[] = [];
  paginatedProjects: any[] = [];
  @Input() selectedProject: any;

  currentPage = 1;
  itemsPerPage = 5;
  totalPages = 1;
  pagesArray: number[] = [];

  ngOnInit(): void {
    this.calculatePagination();
  }

  ngOnChanges(): void {
    this.calculatePagination();
  }

  calculatePagination(): void {
    this.totalPages = Math.ceil(this.projects.length / this.itemsPerPage);
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
    this.paginatedProjects = this.projects.slice(start, end);
  }
}
