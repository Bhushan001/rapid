import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-radio',
  templateUrl: './radio.component.html',
  styleUrls: ['./radio.component.scss'],
  imports:[
    CommonModule
  ]
})
export class RadioComponent {
  @Input() label: string = '';
  @Input() name: string = '';
  @Input() options: { value: string; label: string }[] = [];
  @Input() selectedValue: string = '';
  @Input() disabled: boolean = false;

  @Output() valueChange = new EventEmitter<string>();

  onRadioChange(value: string): void {
    this.valueChange.emit(value);
  }
}
