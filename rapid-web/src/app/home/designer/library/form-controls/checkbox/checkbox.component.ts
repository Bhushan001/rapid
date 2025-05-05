import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-checkbox',
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss'],
  imports:[
    CommonModule
  ]
})
export class CheckboxComponent {
  @Input() label: string = '';
  @Input() checked: boolean = false;
  @Input() disabled: boolean = false;
  @Input() name: string = '';

  @Output() change = new EventEmitter<boolean>();

  onCheckboxChange(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    this.change.emit(checkbox.checked);
  }
}
