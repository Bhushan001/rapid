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
import { CardComponent } from './library/layout/card/card.component';
import { ContainerComponent } from './library/layout/container/container.component';
import { TabsComponent } from './library/layout/tabs/tabs.component';
import { AccordionComponent } from './library/layout/accordion/accordion.component';
import { ModalComponent } from './library/layout/modal/modal.component';
import { InputTextComponent } from './library/form-controls/input-text/input-text.component';
import { SelectDropdownComponent } from './library/form-controls/select-dropdown/select-dropdown.component';
import { CheckboxComponent } from './library/form-controls/checkbox/checkbox.component';
import { RadioComponent } from './library/form-controls/radio/radio.component';
import { TextAreaComponent } from './library/form-controls/textarea/textarea.component';
import { SliderComponent } from './library/form-controls/slider/slider.component';
import { TableComponent } from './library/data-display/table/table.component';
import { ListGroupComponent } from './library/data-display/list-group/list-group.component';
import { BadgeComponent } from './library/data-display/badge/badge.component';
import { TooltipComponent } from './library/data-display/tooltip/tooltip.component';
import { BarChartComponent } from './library/charts/bar-chart/bar-chart.component';
import { ProgressBarComponent } from './library/charts/progress-bar/progress-bar.component';
import { LoginBlockComponent } from './library/user-controls/login-block/login-block.component';
import { ProfileBlockComponent } from './library/user-controls/profile-block/profile-block.component';
import { OtpFieldComponent } from './library/user-controls/otp-field/otp-field.component';
import { NotificationCenterComponent } from './library/user-controls/notification-center/notification-center.component';
import { AccountSelectorComponent } from './library/custom-widgets/account-selector/account-selector.component';
import { TransactionWizardComponent } from './library/custom-widgets/transaction-wizard/transaction-wizard.component';
import { CurrencyInputComponent } from './library/custom-widgets/currency-input/currency-input.component';
import { IfscAutofillComponent } from './library/custom-widgets/ifsc-autofill/ifsc-autofill.component';
import { TopbarComponent } from './library/navigation/topbar/topbar.component';
import { FooterComponent } from './library/navigation/footer/footer.component';
import { BreadcrumbsComponent } from './library/navigation/breadcrumbs/breadcrumbs.component';

@Component({
  selector: 'app-designer',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    CardComponent,
    ContainerComponent,
    TabsComponent,
    AccordionComponent,
    ModalComponent,
    InputTextComponent,
    SelectDropdownComponent,
    CheckboxComponent,
    RadioComponent,
    TextAreaComponent,
    SliderComponent,
    TableComponent,
    ListGroupComponent,
    BadgeComponent,
    TooltipComponent,
    BarChartComponent,
    ProgressBarComponent,
    LoginBlockComponent,
    ProfileBlockComponent,
    OtpFieldComponent,
    NotificationCenterComponent,
    AccountSelectorComponent,
    TransactionWizardComponent,
    CurrencyInputComponent,
    IfscAutofillComponent,
    TopbarComponent,
    FooterComponent,
    BreadcrumbsComponent
  ],
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


  // modal component
  showModal: boolean = false;

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }


  // Input text 
  accountName: string = '';

  // Select Dropdown
  accountTypes = [
    { value: 'savings', label: 'Savings Account' },
    { value: 'current', label: 'Current Account' },
    { value: 'loan', label: 'Loan Account' }
  ];
  selectedAccountType: string = '';


  // Chckbox
  isChecked: boolean = false;

  onCheckboxChange(checked: boolean): void {
    this.isChecked = checked;
    console.log('Checkbox checked: ', checked);
  }

  // Radio
  accountOptions = [
    { value: 'savings', label: 'Savings Account' },
    { value: 'current', label: 'Current Account' },
    { value: 'loan', label: 'Loan Account' }
  ];


  onAccountTypeChange(value: string): void {
    this.selectedAccountType = value;
    console.log('Selected account type:', value);
  }


  // textarea
  description: string = '';

  onDescriptionChange(value: string): void {
    this.description = value;
    console.log('Updated description:', value);
  }


  // slider
  balance: number = 5000;

  onBalanceChange(value: number): void {
    this.balance = value;
    console.log('Updated balance:', value);
  }


  // Table
  tableColumns = [
    { header: 'Name', field: 'name' },
    { header: 'Age', field: 'age' },
    { header: 'City', field: 'city' }
  ];

  tableData = [
    { name: 'John Doe', age: 28, city: 'New York' },
    { name: 'Jane Smith', age: 34, city: 'Los Angeles' },
    { name: 'Sam Johnson', age: 22, city: 'Chicago' }
  ];

  // list-group
  listItems = [
    { label: 'Item 1', value: 1, actionLabel: 'View' },
    { label: 'Item 2', value: 2, actionLabel: 'Edit' },
    { label: 'Item 3', value: 3, actionLabel: 'Delete' }
  ];

  onItemClicked(item: any): void {
    console.log('Item clicked:', item);
  }

  // badge
  isBadgeVisible: boolean = true;

  toggleBadgeVisibility(): void {
    this.isBadgeVisible = !this.isBadgeVisible;
  }

  // bar chart
  data: number[] = [12, 19, 3, 5, 2, 3];
  labels: string[] = ['January', 'February', 'March', 'April', 'May', 'June'];
  colors: string[] = ['#FF5733', '#33FF57', '#3357FF', '#F7DC6F', '#D35400', '#8E44AD'];

  // otp
  handleOtp(code: string) {
    console.log('Entered OTP:', code);
  }
  


  private borderColors = ['red', 'green', 'orange', 'purple', 'blue', 'brown'];
  private colorIndex = 0;
  private draggedComponent: string | null = null;

  // Mapping elements to their dynamic containers
  private containerMap = new Map<HTMLElement, ViewContainerRef>();

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private injector: Injector
  ) { }

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

    if (this.draggedComponent === 'app-card') {
      const factory = this.componentFactoryResolver.resolveComponentFactory(CardComponent);
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
