import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.scss'],
  imports:[
    CommonModule
  ]
})
export class ProgressBarComponent {
  @Input() value: number = 0; // Progress value (0–100)
  @Input() label: string = ''; // Optional label inside the bar
  @Input() color: string = 'primary'; // Bootstrap color: primary, success, danger, etc.

  get progressBarWidth(): string {
    return `${Math.min(Math.max(this.value, 0), 100)}%`;
  }

  get barClass(): string {
    return `bg-${this.color}`;
  }
}
