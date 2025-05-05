import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-container',
  templateUrl: './container.component.html',
  imports:[
    CommonModule
  ],
  styleUrls: ['./container.component.scss']
})
export class ContainerComponent {
  @Input() title?: string;
  @Input() padding: boolean = true;
  @Input() fluid: boolean = false; // If true, full-width container
}
