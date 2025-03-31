import { RouteInfo } from "./admin-sidebar.metadata";

export const ADMINROUTES: RouteInfo[] = [
    {
        path: '/admin/home', title: 'Home', icon: 'bx bx-home-alt', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: []
    },
    {
        path: '', title: 'Dashboard', icon: 'bx bx-line-chart', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
            { path: '/admin/metrics', title: 'Key Metrics', icon: 'bx bx-bar-chart-alt-2', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/admin/reports', title: 'Reports', icon: 'bx bx-file-blank', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/admin/export', title: 'Export Data', icon: 'bx bx-export', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
        ]
    },
    {
        path: '', title: 'Manage Clients', icon: 'bx bx-group', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
            { path: '/admin/manage-clients/list-clients', title: 'List Clients', icon: 'bx bx-bar-chart-alt-2', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/admin/manage-clients/create-client', title: 'Create Client', icon: 'bx bx-file-blank', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
        ]
    },
    {
        path: '', title: 'User Management', icon: 'bx bx-user', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
            { path: '/admin/manage-users/list-users', title: 'View Users', icon: 'bx bx-list-ul', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/admin/manage-users/list-roles', title: 'Assign Roles', icon: 'bx bx-shield-alt-2', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/admin/users/activate', title: 'Activate/Deactivate', icon: 'bx bx-power-off', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
            { path: '/admin/users/password-reset', title: 'Password Reset', icon: 'bx bx-key', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
        ]
    },
    // {
    //     path: '', title: 'Role & Permissions', icon: 'bx bx-shield-alt', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
    //         { path: '/admin/roles', title: 'Manage Roles', icon: 'bx bx-list-check', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/permissions', title: 'Assign Permissions', icon: 'bx bx-lock-open-alt', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //     ]
    // },
    // {
    //     path: '', title: 'Content Management', icon: 'bx bx-hdd', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
    //         { path: '/admin/pages', title: 'Manage Pages', icon: 'bx bx-file', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/blogs', title: 'Manage Blogs', icon: 'bx bx-news', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/faqs', title: 'Manage FAQs', icon: 'bx bx-help-circle', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/media', title: 'Media Files', icon: 'bx bx-image-alt', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //     ]
    // },
    // {
    //     path: '', title: 'System Settings', icon: 'bx bx-cog', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
    //         { path: '/admin/config', title: 'App Settings', icon: 'bx bx-wrench', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/integrations', title: 'Integrations', icon: 'bx bx-plug', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //     ]
    // },
    // {
    //     path: '', title: 'Logs & Activities', icon: 'bx bx-history', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
    //         { path: '/admin/logins', title: 'User Logins', icon: 'bx bx-log-in', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/actions', title: 'User Actions', icon: 'bx bx-list-ul', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/realtime', title: 'Real-time Activity', icon: 'bx bx-time-five', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //     ]
    // },
    // {
    //     path: '', title: 'Notifications', icon: 'bx bx-bell', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
    //         { path: '/admin/notifications/send', title: 'Send Notifications', icon: 'bx bx-send', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/support', title: 'Support Tickets', icon: 'bx bx-support', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/announcements', title: 'Announcements', icon: 'bx bx-megaphone', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //     ]
    // },
    // {
    //     path: '', title: 'Reports', icon: 'bx bx-data', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
    //         { path: '/admin/reports-download', title: 'Download Reports', icon: 'bx bx-download', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/backup-recovery', title: 'Backup & Recovery', icon: 'bx bx-hdd', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/logs-monitor', title: 'Monitor Logs', icon: 'bx bx-search-alt-2', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //     ]
    // },
    // {
    //     path: '', title: 'Other Features', icon: 'bx bx-grid', class: 'sub', badge: '', badgeClass: '', isExternalLink: false, submenu: [
    //         { path: '/admin/api-keys', title: 'API Keys', icon: 'bx bx-key', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/multilingual', title: 'Multi-language', icon: 'bx bx-globe', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/dark-mode', title: 'Dark/Light Mode', icon: 'bx bx-adjust', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //         { path: '/admin/profile', title: 'Admin Profile', icon: 'bx bx-user-circle', class: '', badge: '', badgeClass: '', isExternalLink: false, submenu: [] },
    //     ]
    // },
];