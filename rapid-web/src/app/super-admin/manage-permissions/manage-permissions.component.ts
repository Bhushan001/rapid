import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-manage-permissions',
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './manage-permissions.component.html',
  styleUrl: './manage-permissions.component.scss'
})
export class ManagePermissionsComponent {

}
