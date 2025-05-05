import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-textarea',
  templateUrl: './textarea.component.html',
  styleUrls: ['./textarea.component.scss'],
  imports: [
    CommonModule
  ]
})
export class TextAreaComponent {
  @Input() label: string = '';
  @Input() placeholder: string = '';
  @Input() value: string = '';
  @Input() required: boolean = false;
  @Input() disabled: boolean = false;
  @Input() name: string = '';
  @Input() rows: number = 4;

  @Output() valueChange = new EventEmitter<string>();

  onInputChange(event: Event): void {
    const textarea = event.target as HTMLTextAreaElement;
    this.valueChange.emit(textarea.value);
  }
}
