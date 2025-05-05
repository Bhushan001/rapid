import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
  imports:[
    CommonModule
  ]
})
export class ModalComponent {
  @Input() title: string = 'Modal Title';
  @Input() show: boolean = false;

  @Output() close = new EventEmitter<void>();

  onClose(): void {
    this.close.emit();
  }
}
