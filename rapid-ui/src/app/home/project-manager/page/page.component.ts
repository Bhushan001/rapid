import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-page',
  imports: [
    CommonModule
  ],
  templateUrl: './page.component.html',
  styleUrl: './page.component.scss'
})
export class PageComponent {
  @Input() pages: any[] = [];
  paginatedPages: any[] = [];

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
    this.totalPages = Math.ceil(this.pages.length / this.itemsPerPage);
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
    this.paginatedPages = this.pages.slice(start, end);
  }
}
