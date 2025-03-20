import { ROUTES } from './sidebar-routes.config';
import { SidebarService } from './sidebar.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouteInfo } from './sidebar.metadata';
import { Component, Input, OnInit, AfterViewChecked } from '@angular/core';


@Component({
  selector: 'app-sidebar',
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  isSidebarExpanded = true;
  public menuItems: any[] = [];



  constructor(public sidebarService: SidebarService) {
    // this.sidebarService.sidebarState$.subscribe((state) => {
    //   this.isSidebarExpanded = state;
    // });
  }

  ngOnInit() {
    this.menuItems = ROUTES;
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
