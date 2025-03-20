import { Routes } from '@angular/router';
import { FullLayoutComponent } from './layouts/full-layout/full-layout.component';
import { ContentLayoutComponent } from './layouts/content-layout/content-layout.component';
import { Full_ROUTES } from './shared/routes/full-layout.routes';
import { CONTENT_ROUTES } from './shared/routes/content-layout.routes';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard/e-commerce',
        pathMatch: 'full',
      },
      { path: '', component: FullLayoutComponent, data: { title: 'full Views' }, children: Full_ROUTES },
      { path: '', component: ContentLayoutComponent, data: { title: 'content Views' }, children: CONTENT_ROUTES },
      { path: '**', redirectTo: 'dashboard/e-commerce' }
];
