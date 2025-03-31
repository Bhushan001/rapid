import { Component, OnInit } from '@angular/core';
import { User } from '../../../model/user.model';
import { AdminService } from '../services/admin.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { ROLE } from '../../constants/role.constants';

@Component({
  selector: 'app-list-users',
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    NgbPaginationModule
  ],
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss'
})
export class ListUsersComponent implements OnInit {
  users: User[] = [];
  searchText = '';

  page = 1;
  pageSize = 5;
  totalItems = 0;


  loading = false; // Add loading flag

  constructor(
    private _adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  search(): void {
    this.page = 1;
  }

  getAllUsers() {
    this.loading = true; // Set loading to true
    this._adminService.getUsers(this.page - 1, this.pageSize).subscribe(
      (res: any) => {
        this.users = res.body.content;
        this.totalItems = res.body.totalElements;
        this.loading = false; // Set loading to false after data is loaded
      },
      (error) => {
        console.error('Error fetching users:', error);
        this.loading = false; // Set loading to false on error
      }
    );
  }

  pageChanged(newPage: number) {
    this.page = newPage;
    this.getAllUsers();
  }

  getRole(roles: string[] | undefined): string {
    if (roles && roles.length > 0) {
      return ROLE[roles[0] as keyof typeof ROLE] || 'NA'; // Type assertion
    } else {
      return 'NA';
    }
  }

}
