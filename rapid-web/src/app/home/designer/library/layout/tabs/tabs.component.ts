import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tabs',
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.scss'],
  imports:[
    CommonModule
  ]
})
export class TabsComponent {
  @Input() tabs: { label: string; content: string }[] = [];
  activeTabIndex: number = 0;

  setActiveTab(index: number): void {
    this.activeTabIndex = index;
  }
}
