import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-badge',
  templateUrl: './badge.component.html',
  styleUrls: ['./badge.component.scss'],
  imports:[
    CommonModule
  ]
})
export class BadgeComponent {
  @Input() label: string = '';
  @Input() color: string = 'primary'; // default color
  @Input() visibility: boolean = true; // Controls visibility of the badge
}
