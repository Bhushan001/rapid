import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.scss'],
  imports:[
    CommonModule
  ]
})
export class SliderComponent {
  @Input() label: string = '';
  @Input() min: number = 0;
  @Input() max: number = 100;
  @Input() step: number = 1;
  @Input() value: number = 50;
  @Input() disabled: boolean = false;
  @Input() name: string = '';

  @Output() valueChange = new EventEmitter<number>();

  onSliderChange(event: Event): void {
    const slider = event.target as HTMLInputElement;
    this.valueChange.emit(Number(slider.value));
  }
}
