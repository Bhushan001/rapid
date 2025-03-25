import { Routes } from '@angular/router';
import { ECommerceComponent } from '../../dashboard/e-commerce/e-commerce.component';
import { GridSystemComponent } from '../../content/grid-system/grid-system.component';
import { TypographyComponent } from '../../content/typography/typography.component';
import { TextUtilitiesComponent } from '../../content/text-utilities/text-utilities.component';
import { SignupComponent } from '../../auth/sign-up/sign-up.component';
import { AuthComponent } from '../../auth/auth.component';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { ContentComponent } from '../../content/content.component';
import { ProjectManagerComponent } from '../../home/project-manager/project-manager.component';
import { HomeComponent } from '../../home/home.component';

export const Full_ROUTES: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    data: { roles: ['USER'] },
    children: [
      {
        path: 'project-manager', 
        component: ProjectManagerComponent
      }
    ]
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'e-commerce',
        component: ECommerceComponent
      }
    ]
  },
  {
    path: 'content',
    component: ContentComponent,
    children: [
      { path: 'grid-system', component: GridSystemComponent },
      { path: 'typography', component: TypographyComponent },
      { path: 'text-utilities', component: TextUtilitiesComponent }
    ]
  }
];