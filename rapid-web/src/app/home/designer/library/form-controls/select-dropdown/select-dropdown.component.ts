import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-select-dropdown',
  templateUrl: './select-dropdown.component.html',
  styleUrls: ['./select-dropdown.component.scss'],
  imports:[
    CommonModule
  ]
})
export class SelectDropdownComponent {
  @Input() label: string = '';
  @Input() placeholder: string = '-- Select an option --';
  @Input() options: { value: string; label: string }[] = [];
  @Input() value: string = '';
  @Input() disabled: boolean = false;
  @Input() name: string = '';

  @Output() valueChange = new EventEmitter<string>();

  onValueChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    this.valueChange.emit(select.value);
  }
}
