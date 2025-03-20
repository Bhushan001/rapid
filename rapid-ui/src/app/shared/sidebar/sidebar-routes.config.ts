import { RouteInfo } from './sidebar.metadata';

//Sidebar menu Routes and data
export const ROUTES: RouteInfo[] = [
    {
        path: '', title: 'Home', icon: 'bx bx-home-alt', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
            { path: '/home/project-manager', title: 'Project Manager', icon: 'bx bx-briefcase', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
        ]
    },
    {
        path: '', title: 'Dashboard', icon: 'bx bx-grid-alt', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
            { path: '/dashboard/e-commerce', title: 'eCommerce', icon: 'bx bx-bar-chart-alt-2', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
        ]
    },
    {
        path: '', title: 'Content', icon: 'bx bx-refresh', class: 'sub', badge: '', badgeClass: '', isExternalLink: false,
        submenu: [
            { path: '/content/grid-system', title: 'Grid System', icon: 'bx bx-grid', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/content/typography', title: 'Typography', icon: 'bx bx-font', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/content/text-utilities', title: 'Text Utilities', icon: 'bx bx-text', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
        ]
    },
    {
        path: '', title: 'Auth', icon: 'bx bx-shield-quarter', class: 'sub', badge: '', badgeClass: '', isExternalLink: false,
        submenu: [
            { path: '/auth/sign-up', title: 'Sign Up', icon: 'bx bx-user-plus', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/auth/login', title: 'Login', icon: 'bx bx-log-in-circle', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/auth/reset-password', title: 'Reset Password', icon: 'bx bx-lock', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] }
        ]
    }
];