import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  imports:[
    CommonModule
  ],
  styleUrls: ['./card.component.scss']
})
export class CardComponent {
  @Input() title: string = 'Card Title';
  @Input() subtitle?: string;
  @Input() icon?: string; // optional icon
  @Input() contentLines: string[] = []; // list of lines to render inside card
}
