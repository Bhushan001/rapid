import { Routes } from '@angular/router';
import { HomeComponent } from '../../home/home.component';
import { ProjectManagerComponent } from '../../home/project-manager/project-manager.component';
import { SchemaConfigComponent } from '../../home/schema-config/schema-config.component';
import { RequestSchemaComponent } from '../../home/schema-config/request-schema/request-schema.component';
import { S1SchemaComponent } from '../../home/schema-config/s1-schema/s1-schema.component';
import { MappingConfigComponent } from '../../home/mapping-config/mapping-config.component';
import { MappingComponent } from '../../home/mapping-config/mapping/mapping.component';
import { MappingListComponent } from '../../home/mapping-config/mapping-list/mapping-list.component';
import { AuthGuard } from '../../auth/guards/auth.guard';
import { RolesGuard } from '../../auth/guards/roles.guard';
import { UnauthorizedComponent } from '../../home/unauthorized/unauthorized.component';
import { DesignerComponent } from '../../home/designer/designer.component';

export const Full_ROUTES: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    children: [
      {
        path: 'project-manager', 
        component: ProjectManagerComponent,        
        data: { roles: ['USER'] },
        canActivate:[AuthGuard, RolesGuard],
      },
      {
        path: 'designer', 
        component: DesignerComponent,        
        data: { roles: ['USER'] },
        canActivate:[AuthGuard, RolesGuard],
      },
      {
        path: 'schema', 
        component: SchemaConfigComponent,
        data: { roles: ['USER'] },
        canActivate:[AuthGuard, RolesGuard],
        children: [
          {
            path: 'request-schema', 
            component: RequestSchemaComponent
          },
          {
            path: 's1-schema', 
            component: S1SchemaComponent
          }
        ]
      },
      {
        path: 'mapping',
        data: { roles: ['USER'] },
        canActivate:[AuthGuard, RolesGuard],
        component: MappingConfigComponent,
        children: [
          {
            path: 'create-mapping', 
            component: MappingComponent
          },
          {
            path: 'mapping-list', 
            component: MappingListComponent
          }
        ]
      }
    ]
  },
  { path: 'unauthorized', component: UnauthorizedComponent }
];