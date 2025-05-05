import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren, ElementRef } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-otp-field',
  templateUrl: './otp-field.component.html',
  styleUrls: ['./otp-field.component.scss'],
  imports:[
    CommonModule,
    FormsModule
  ]
})
export class OtpFieldComponent implements OnInit {
  @Input() length: number = 6;
  @Input() isPassword: boolean = false;
  @Output() otpComplete = new EventEmitter<string>();

  otpValues: string[] = [];

  @ViewChildren('otpInput') inputs!: QueryList<ElementRef>;

  ngOnInit(): void {
    this.otpValues = Array(this.length).fill('');
  }

  onInput(event: any, index: number) {
    const value = event.target.value;
    if (/^[0-9a-zA-Z]$/.test(value)) {
      this.otpValues[index] = value;
      if (index < this.length - 1) {
        this.inputs.toArray()[index + 1].nativeElement.focus();
      }
    } else {
      this.otpValues[index] = '';
    }

    if (this.otpValues.every(val => val)) {
      this.otpComplete.emit(this.otpValues.join(''));
    }
  }

  onKeyDown(event: KeyboardEvent, index: number) {
    const key = event.key;
    if (key === 'Backspace') {
      if (this.otpValues[index]) {
        this.otpValues[index] = '';
      } else if (index > 0) {
        this.inputs.toArray()[index - 1].nativeElement.focus();
      }
    } else if (key === 'ArrowLeft' && index > 0) {
      this.inputs.toArray()[index - 1].nativeElement.focus();
    } else if (key === 'ArrowRight' && index < this.length - 1) {
      this.inputs.toArray()[index + 1].nativeElement.focus();
    }
  }
}
