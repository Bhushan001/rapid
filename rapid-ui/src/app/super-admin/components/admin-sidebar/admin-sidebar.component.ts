import { Component } from '@angular/core';
import { AdminSidebarService } from './admin-sidebar.service';
import { ADMINROUTES } from './admin-sidebar-routes.config';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin-sidebar',
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './admin-sidebar.component.html',
  styleUrl: './admin-sidebar.component.scss'
})
export class AdminSidebarComponent {
  isSidebarExpanded = true;
    public menuItems: any[] = [];
  
  
  
    constructor(public sidebarService: AdminSidebarService) {
      // this.sidebarService.sidebarState$.subscribe((state) => {
      //   this.isSidebarExpanded = state;
      // });
    }
  
    ngOnInit() {
      this.menuItems = ADMINROUTES;
    }
  
    toggleSidebar() {
      this.isSidebarExpanded = !this.isSidebarExpanded;
    }
  
    isSubmenu(route: any): boolean {
      return route.submenu && route.submenu.length > 0;
    }
  
    generateDropdownId(title: string): string {
      return `dropdown-${title.replace(/\s+/g, '-').toLowerCase()}`;
    }
  
    generateParentId(title: string, depth: number, parentTitle?: string): string {
      if (depth > 0 && parentTitle) {
        return `#${this.generateDropdownId(parentTitle)}`;
      } else {
        return '#sidebar';
      }
    }
  
    getIconClass(icon: string): string {
      if (icon) {
        return `bx ${icon.replace('fa fa-', 'bxs-').replace('fa-', 'bx-')}`;
      }
      return '';
    }
}
