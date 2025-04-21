import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-manage-roles',
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './manage-roles.component.html',
  styleUrl: './manage-roles.component.scss'
})
export class ManageRolesComponent {

}
