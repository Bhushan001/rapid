import { CommonModule } from '@angular/common';
import { Component, Input, HostListener, ElementRef, Renderer2 } from '@angular/core';

@Component({
  selector: '[app-tooltip]',
  templateUrl: './tooltip.component.html',
  styleUrls: ['./tooltip.component.scss'],
  imports:[
    CommonModule
  ]
})
export class TooltipComponent {
  @Input() tooltipText: string = ''; // The content of the tooltip
  @Input() position: 'top' | 'bottom' | 'left' | 'right' = 'top'; // Tooltip position

  tooltipVisible: boolean = false;

  constructor(private el: ElementRef, private renderer: Renderer2) { }

  @HostListener('mouseenter') onMouseEnter(): void {
    this.tooltipVisible = true;
  }

  @HostListener('mouseleave') onMouseLeave(): void {
    this.tooltipVisible = false;
  }
}
