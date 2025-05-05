import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  imports:[
    CommonModule
  ]
})
export class TableComponent {
  @Input() columns: { header: string; field: string }[] = [];
  @Input() data: any[] = [];
}
