import { Component } from '@angular/core';
import { DashboardMetric } from '../../../model/dashboard.metric.model';
import { ActivityItem } from '../../../model/activity-item.model';
import { CommonModule } from '@angular/common';
import { RecentActivityComponent } from "../../../shared/components/recent-activity/recent-activity.component";

@Component({
  selector: 'app-admin-dashboard',
  imports: [
    CommonModule,
    RecentActivityComponent
],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {
  metrics: DashboardMetric[] = [];
  recentActivities: ActivityItem[] = [];
  loading = true;
  activitiesLoading = true;

  ngOnInit(): void {
    // Simulate API call with setTimeout
    setTimeout(() => {
      this.fetchAdminMetrics();
      this.loading = false;
    }, 1000);

    // Simulate activity data loading (could be separate API call)
    setTimeout(() => {
      this.fetchRecentActivities();
      this.activitiesLoading = false;
    }, 1500);
  }

  fetchAdminMetrics(): void {
    // Mock data - replace with actual API call later
    this.metrics = [
      {
        title: 'Clients',
        value: 87,
        icon: 'fa-building',
        color: '#4CAF50'
      },
      {
        title: 'Users',
        value: 243,
        icon: 'fa-users',
        color: '#2196F3'
      },
      {
        title: 'Roles',
        value: 15,
        icon: 'fa-user-tag',
        color: '#FF9800'
      },
      {
        title: 'Permissions',
        value: 42,
        icon: 'fa-key',
        color: '#9C27B0'
      },
      {
        title: 'Request Schemas (Global)',
        value: 36,
        icon: 'fa-file-alt',
        color: '#F44336'
      },
      {
        title: 'S1 Schemas (Global)',
        value: 29,
        icon: 'fa-file-code',
        color: '#00BCD4'
      },
      {
        title: 'Mappings (Global)',
        value: 48,
        icon: 'fa-project-diagram',
        color: '#795548'
      },
      {
        title: 'APIs',
        value: 23,
        icon: 'fa-plug',
        color: '#607D8B'
      }
    ];
  }

  fetchRecentActivities(): void {
    // Mock activity data - replace with actual API call later
    this.recentActivities = [
      {
        id: 1,
        description: 'User "Super Admin" created a new client "CRIF"',
        timestamp: '10:00AM',
        icon: 'fa-user-plus'
      },
      {
        id: 2,
        description: 'New S1 Schema',
        timestamp: '10:12AM',
        icon: 'fa-file-code'
      },
      {
        id: 3,
        description: 'Schema updated by "Admin"',
        timestamp: '10:36AM',
        icon: 'fa-file-edit'
      },
      {
        id: 4,
        description: 'Report "Data Parsing" created',
        timestamp: '11:25AM',
        icon: 'fa-chart-line'
      },
      {
        id: 5,
        description: 'New support ticket #98765 created by "User Support"',
        timestamp: '11:43AM',
        icon: 'fa-ticket-alt'
      },
      {
        id: 6,
        description: 'Alfred Murray',
        timestamp: '01:05PM',
        icon: 'fa-user'
      }
    ];
  }

  // Method to refresh all dashboard data
  refreshData(): void {
    this.loading = true;
    this.activitiesLoading = true;
    
    setTimeout(() => {
      this.fetchAdminMetrics();
      this.loading = false;
    }, 800);
    
    setTimeout(() => {
      this.fetchRecentActivities();
      this.activitiesLoading = false;
    }, 1200);
  }
}
