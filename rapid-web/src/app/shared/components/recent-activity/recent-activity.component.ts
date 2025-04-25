import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ActivityItem } from '../../../model/activity-item.model';

@Component({
  selector: 'app-recent-activity',
  imports: [
    CommonModule
  ],
  templateUrl: './recent-activity.component.html',
  styleUrl: './recent-activity.component.scss'
})
export class RecentActivityComponent implements OnInit{
  @Input() activities: ActivityItem[] = [];
  @Input() loading: boolean = false;

  ngOnInit(): void {
    
  }
}
