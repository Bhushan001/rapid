import { Routes } from "@angular/router";
import { SuperAdminComponent } from "../../super-admin/super-admin.component";
import { AdminHomeComponent } from "../../super-admin/admin-home/admin-home.component";
import { ManageClientsComponent } from "../../super-admin/manage-clients/manage-clients.component";
import { ListClientsComponent } from "../../super-admin/manage-clients/list-clients/list-clients.component";
import { ListUsersComponent } from "../../super-admin/manage-users/list-users/list-users.component";
import { ManageRolesComponent } from "../../super-admin/manage-roles/manage-roles.component";
import { ListRolesComponent } from "../../super-admin/manage-roles/list-roles/list-roles.component";
import { ManagePermissionsComponent } from "../../super-admin/manage-permissions/manage-permissions.component";
import { ListPermissionsComponent } from "../../super-admin/manage-permissions/list-permissions/list-permissions.component";



export const ADMIN_ROUTES: Routes = [
    {
      path: 'admin',
      component: SuperAdminComponent,
      data: { roles: ['SUPERADMIN'] },
      children: [
        { path: '', redirectTo: 'home', pathMatch: 'full' },
        {
          path: 'home', 
          component: AdminHomeComponent
        },
        {
          path: 'manage-clients', 
          component: ManageClientsComponent,
          children:[
            { path: '', redirectTo: 'list-clients', pathMatch: 'full' },
            { path: 'list-clients', component: ListClientsComponent}
          ]
        },
        {
          path: 'manage-users', 
          component: ManageClientsComponent,
          children:[
            { path: '', redirectTo: 'list-users', pathMatch: 'full' },
            { path: 'list-users', component: ListUsersComponent}
          ]
        },
        {
          path: 'manage-roles', 
          component: ManageRolesComponent,
          children:[
            { path: '', redirectTo: 'list-roles', pathMatch: 'full' },
            { path: 'list-roles', component: ListRolesComponent}
          ]
        },
        {
          path: 'manage-permissions', 
          component: ManagePermissionsComponent,
          children:[
            { path: '', redirectTo: 'list-permissions', pathMatch: 'full' },
            { path: 'list-permissions', component: ListPermissionsComponent}
          ]
        }
      ]
    }
  ];