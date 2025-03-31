import { Routes } from '@angular/router';
import { ECommerceComponent } from '../../dashboard/e-commerce/e-commerce.component';
import { GridSystemComponent } from '../../content/grid-system/grid-system.component';
import { TypographyComponent } from '../../content/typography/typography.component';
import { TextUtilitiesComponent } from '../../content/text-utilities/text-utilities.component';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { ContentComponent } from '../../content/content.component';
import { ProjectManagerComponent } from '../../home/project-manager/project-manager.component';
import { HomeComponent } from '../../home/home.component';
import { SuperAdminComponent } from '../../super-admin/super-admin.component';
import { MapperComponent } from '../../home/mapper/mapper.component';
import { RequestSchemaComponent } from '../../home/mapper/request-schema/request-schema.component';
import { S1SchemaComponent } from '../../home/mapper/s1-schema/s1-schema.component';
import { MappingComponent } from '../../home/mapper/mapping/mapping.component';

export const Full_ROUTES: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    data: { roles: ['USER'] },
    children: [
      {
        path: 'project-manager', 
        component: ProjectManagerComponent
      },
      {
        path: 'mapper', 
        component: MapperComponent,
        children: [
          {
            path: 'request-schema', 
            component: RequestSchemaComponent
          },
          {
            path: 's1-schema', 
            component: S1SchemaComponent
          },
          {
            path: 'mapping', 
            component: MappingComponent
          }
        ]
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