import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-input-text',
  templateUrl: './input-text.component.html',
  styleUrls: ['./input-text.component.scss'],
  imports:[
    CommonModule
  ]
})
export class InputTextComponent {
  @Input() label: string = '';
  @Input() placeholder: string = '';
  @Input() value: string = '';
  @Input() required: boolean = false;
  @Input() disabled: boolean = false;
  @Input() name: string = '';

  @Output() valueChange = new EventEmitter<string>();

  onInputChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.valueChange.emit(input.value);
  }
}
