import { CommonModule } from '@angular/common';
import { ApplicationRef, Component, ComponentFactoryResolver, ElementRef, Injector, Renderer2, TemplateRef, ViewChild, ViewContainerRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-designer',
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './designer.component.html',
  styleUrl: './designer.component.scss'
})
export class DesignerComponent {
  @ViewChild('page') pageRef!: ElementRef;
  @ViewChild('pageTooltip') pageTooltipRef!: ElementRef;
  @ViewChild('blockTooltipTemplate') blockTooltipTemplate!: TemplateRef<any>;
  @ViewChild('sectionTooltipTemplate') sectionTooltipTemplate!: TemplateRef<any>;
  @ViewChild('tooltipContainer', { read: ViewContainerRef }) tooltipContainer!: ViewContainerRef; // For dynamically created tooltips

  private borderColors = ['red', 'green', 'orange', 'purple', 'blue', 'brown'];
  private colorIndex = 0;
  private currentPage: HTMLElement | null = null;

  ngAfterViewInit() {
    this.pageRef.nativeElement.addEventListener('mouseenter', () => this.showTooltip(this.pageTooltipRef.nativeElement, this.currentPage!));
    this.pageRef.nativeElement.addEventListener('mouseleave', () => this.hideTooltip(this.pageTooltipRef.nativeElement));
  }

  addBlock(parent: HTMLElement) {
    const newBlock = this.createElement('div', {
      width: '100%',
      height: '20%',
      display: 'block',
      border: `2px dotted ${this.getNextBorderColor()}`,
      boxSizing: 'border-box',
      position: 'relative' // For positioning the block tooltip
    });
    parent.appendChild(newBlock);
    this.attachTooltip(newBlock, this.blockTooltipTemplate);
  }

  addSection(parent: HTMLElement) {
    const newSection = this.createElement('div', {
      width: '98%',
      height: '96%',
      margin: '5px',
      display: 'inline-block',
      border: `2px dashed ${this.getNextBorderColor()}`,
      boxSizing: 'border-box',
      position: 'relative' // For positioning the section tooltip
    });
    parent.appendChild(newSection);
    this.attachTooltip(newSection, this.sectionTooltipTemplate);
  }

  addRow(parent: HTMLElement) {
    const newRow = this.createElement('div', {
      width: '98%',
      height: '48%',
      display: 'flex',
      flexDirection: 'row',
      border: `2px dotted ${this.getNextBorderColor()}`,
      boxSizing: 'border-box'
    });
    parent.appendChild(newRow);
  }

  addColumn(parent: HTMLElement) {
    const newColumn = this.createElement('div', {
      width: '48%',
      height: '98%',
      display: 'flex',
      flexDirection: 'column',
      border: `2px dashed ${this.getNextBorderColor()}`,
      boxSizing: 'border-box'
    });
    parent.appendChild(newColumn);
  }

  private createElement(tag: string, styles: Partial<CSSStyleDeclaration>): HTMLElement {
    const element = document.createElement(tag);
    for (const key in styles) {
      if (styles.hasOwnProperty(key) && styles[key] !== undefined) {
        element.style[key] = styles[key]!; // Non-null assertion because we checked for undefined
      }
    }
    return element;
  }

  private attachTooltip(element: HTMLElement, template: TemplateRef<any>) {
    element.addEventListener('mouseenter', (event) => {
      const tooltip = document.createElement('div');
      tooltip.classList.add('tooltip-container'); // Add a class for styling      
      this.tooltipContainer.createEmbeddedView(template, { $implicit: element })
        .rootNodes.forEach(
          node => {
            node.classList.add("show");
            tooltip.appendChild(node)
          }
        );
      element.appendChild(tooltip);
      this.positionTooltip(tooltip, event.target as HTMLElement);
    });

    element.addEventListener('mouseleave', () => {
      const tooltip = document.querySelector('.tooltip-container');
      if (tooltip) {
        tooltip.remove();
      }
    });
  }

  private showTooltip(tooltipElement: HTMLElement, targetElement: HTMLElement) {
    tooltipElement.classList.add('show');
    this.positionTooltip(tooltipElement, targetElement);
  }

  private hideTooltip(tooltipElement: HTMLElement) {
    tooltipElement.classList.remove('show');
  }

  private positionTooltip(tooltipElement: HTMLElement, targetElement: HTMLElement) {    
    // const rect = targetElement.getBoundingClientRect();
    // tooltipElement.style.position = 'absolute';
    // tooltipElement.style.top = `${rect.top + window.scrollY + rect.height + 5}px`;
    // tooltipElement.style.left = `${rect.left + window.scrollX + rect.width / 2 - tooltipElement.offsetWidth / 2}px`;
  }

  private getNextBorderColor(): string {
    const color = this.borderColors[this.colorIndex % this.borderColors.length];
    this.colorIndex++;
    return color;
  }
}
