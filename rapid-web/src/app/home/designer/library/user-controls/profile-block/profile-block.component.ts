import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-profile-block',
  templateUrl: './profile-block.component.html',
  styleUrls: ['./profile-block.component.scss'],
  imports:[
    CommonModule
  ]
})
export class ProfileBlockComponent {
  @Input() name: string = 'John Doe';
  @Input() email: string = 'john.doe@example.com';
  @Input() avatarUrl: string = 'https://via.placeholder.com/100';
  @Input() showEdit: boolean = true;
  @Input() showLogout: boolean = true;

  onEdit() {
    console.log('Edit profile clicked');
  }

  onLogout() {
    console.log('Logout clicked');
  }
}
