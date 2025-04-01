import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { FooterComponent } from '../../shared/footer/footer.component';
import { SidebarService } from '../../shared/sidebar/sidebar.service';
import { AdminSidebarComponent } from '../../super-admin/components/admin-sidebar/admin-sidebar.component';

@Component({
  selector: 'app-admin-layout',
  imports: [
    CommonModule,
    RouterModule,
    AdminSidebarComponent,
    NavbarComponent,
    FooterComponent
  ],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.scss'
})
export class AdminLayoutComponent {
  isSidebarExpanded = true;

  constructor(public sidebarservice: SidebarService) { }

  toggleSidebar() {
    this.isSidebarExpanded = !this.isSidebarExpanded;
    this.sidebarservice.setSidebarState(!this.sidebarservice.getSidebarState());
  }

}
