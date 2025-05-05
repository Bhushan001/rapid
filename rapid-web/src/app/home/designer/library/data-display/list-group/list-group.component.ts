import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-list-group',
  templateUrl: './list-group.component.html',
  styleUrls: ['./list-group.component.scss'],
  imports:[
    CommonModule
  ]
})
export class ListGroupComponent {
  @Input() items: { label: string; value: any; actionLabel?: string }[] = [];
  @Input() showAction: boolean = false;
  @Output() itemClicked = new EventEmitter<any>();

  onItemClick(item: any): void {
    this.itemClicked.emit(item);
  }
}
