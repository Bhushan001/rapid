import { Component } from '@angular/core';
import { AdminDashboardComponent } from "./admin-dashboard/admin-dashboard.component";

@Component({
  selector: 'app-admin-home',
  imports: [AdminDashboardComponent],
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.scss'
})
export class AdminHomeComponent {

}
