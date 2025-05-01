import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  TemplateRef,
  ViewChild,
  ViewContainerRef,
  ComponentFactoryResolver,
  AfterViewInit,
  Injector,
  EmbeddedViewRef
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { H1HeaderComponent } from './library/headers/h1-header/h1-header.component';

@Component({
  selector: 'app-designer',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './designer.component.html',
  styleUrls: ['./designer.component.scss']
})
export class DesignerComponent implements AfterViewInit {
  @ViewChild('page') pageRef!: ElementRef;
  @ViewChild('pageTooltip') pageTooltipRef!: ElementRef;
  @ViewChild('blockTooltipTemplate') blockTooltipTemplate!: TemplateRef<any>;
  @ViewChild('sectionTooltipTemplate') sectionTooltipTemplate!: TemplateRef<any>;
  @ViewChild('rowTooltipTemplate') rowTooltipTemplate!: TemplateRef<any>;
  @ViewChild('columnTooltipTemplate') columnTooltipTemplate!: TemplateRef<any>;
  @ViewChild('tooltipContainer', { read: ViewContainerRef }) tooltipContainer!: ViewContainerRef;

  private borderColors = ['red', 'green', 'orange', 'purple', 'blue', 'brown'];
  private colorIndex = 0;
  private draggedComponent: string | null = null;

  // Mapping elements to their dynamic containers
  private containerMap = new Map<HTMLElement, ViewContainerRef>();

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private injector: Injector
  ) {}

  ngAfterViewInit() {
    this.pageRef.nativeElement.addEventListener('mouseenter', () =>
      this.showTooltip(this.pageTooltipRef.nativeElement, this.pageRef.nativeElement)
    );
    this.pageRef.nativeElement.addEventListener('mouseleave', () =>
      this.hideTooltip(this.pageTooltipRef.nativeElement)
    );
  }

  startDrag(componentName: string) {
    this.draggedComponent = componentName;
  }

  allowDrop(event: DragEvent) {
    event.preventDefault();
  }

  drop(event: DragEvent, targetElement: HTMLElement) {
    event.preventDefault();
  
    if (this.draggedComponent === 'h1-header') {
      const factory = this.componentFactoryResolver.resolveComponentFactory(H1HeaderComponent);
      const compRef = this.tooltipContainer.createComponent(factory, undefined, this.injector);
  
      // Append the actual component's root node to the DOM element
      const rootNode = (compRef.hostView as EmbeddedViewRef<any>).rootNodes[0];
      targetElement.appendChild(rootNode);
    }
  
    this.draggedComponent = null;
  }

  private showTooltip(tooltipElement: HTMLElement, targetElement: HTMLElement) {
    tooltipElement.classList.add('show');
    this.positionTooltip(tooltipElement, targetElement);
  }

  private hideTooltip(tooltipElement: HTMLElement) {
    tooltipElement.classList.remove('show');
  }

  private positionTooltip(tooltip: HTMLElement, target: HTMLElement) {
    // Add positioning logic here
  }

  private getNextBorderColor(): string {
    const color = this.borderColors[this.colorIndex % this.borderColors.length];
    this.colorIndex++;
    return color;
  }

  private createElement(
    tag: string,
    styles: Partial<CSSStyleDeclaration>,
    tooltipTemplate: TemplateRef<any>
  ): HTMLElement {
    const element = document.createElement(tag);
    Object.assign(element.style, styles);
    element.style.boxSizing = 'border-box';
    element.style.position = 'relative';
  
    // Add tooltip
    const embeddedView = this.tooltipContainer.createEmbeddedView(tooltipTemplate, {
      $implicit: element
    });
    embeddedView.detectChanges();
  
    embeddedView.rootNodes.forEach((node) => {      
      node.classList.add("show");
      element.appendChild(node);
    });
    embeddedView.detectChanges();
    // Add placeholder for dynamic components
    this.containerMap.set(element, this.tooltipContainer);
  
    return element;
  }

  addBlock(parent: HTMLElement) {
    const block = this.createElement('div', {
      width: '204mm',
      height: '292mm',
      padding: '5px',
      display: 'block',
      border: `2px dotted ${this.getNextBorderColor()}`
    }, this.blockTooltipTemplate);

    block.addEventListener('dragover', this.allowDrop);
    block.addEventListener('drop', (event) => this.drop(event, block));
    parent.appendChild(block);
  }

  addSection(parent: HTMLElement) {
    const section = this.createElement('div', {
      width: '100%',
      height: '24%',
      padding: '5px',
      display: 'inline-block',
      border: `2px dashed ${this.getNextBorderColor()}`
    }, this.sectionTooltipTemplate);

    section.addEventListener('dragover', this.allowDrop);
    section.addEventListener('drop', (event) => this.drop(event, section));
    parent.appendChild(section);
  }

  addRow(parent: HTMLElement) {
    const row = this.createElement('div', {
      width: '98%',
      height: '48%',
      display: 'flex',
      padding: '5px',
      flexDirection: 'row',
      border: `2px dotted ${this.getNextBorderColor()}`
    }, this.rowTooltipTemplate);

    row.addEventListener('dragover', this.allowDrop);
    row.addEventListener('drop', (event) => this.drop(event, row));
    parent.appendChild(row);
  }

  addColumn(parent: HTMLElement) {
    const column = this.createElement('div', {
      width: '48%',
      height: '98%',
      display: 'flex',
      padding: '5px',
      flexDirection: 'column',
      border: `2px dashed ${this.getNextBorderColor()}`
    }, this.columnTooltipTemplate);

    column.addEventListener('dragover', this.allowDrop);
    column.addEventListener('drop', (event) => this.drop(event, column));
    parent.appendChild(column);
  }
}
