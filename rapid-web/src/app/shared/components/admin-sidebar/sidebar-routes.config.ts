import { RouteInfo } from "../sidebar/sidebar.metadata";

//Sidebar menu Routes and data
export const ADMINROUTES: RouteInfo[] = [
  {
    path: '/admin/home',
    title: 'Home',
    icon: 'fa-solid fa-home',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    showSubMenu: false,
    submenu: [],
    isSubMenuOpen: false
  },
  {
    path: '',
    title: 'Clients',
    icon: 'fa-solid fa-building',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    isSubMenuOpen: false,
    showSubMenu: false,
    submenu: [
      {
        path: '/admin/manage-clients/list-clients',
        title: 'List Clients',
        icon: 'fa-solid fa-list-ol',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      }
    ],
  },
  {
    path: '',
    title: 'Users',
    icon: 'fa-solid fa-user-circle',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    isSubMenuOpen: false,
    showSubMenu: false,
    submenu: [
      {
        path: '/admin/manage-users/list-users',
        title: 'List Users',
        icon: 'fa-solid fa-users',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      }
    ],
  },
  {
    path: '',
    title: 'Roles',
    icon: 'fa-solid fa-user-tag',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    isSubMenuOpen: false,
    showSubMenu: false,
    submenu: [
      {
        path: '/admin/manage-roles/list-roles',
        title: 'List Roles',
        icon: 'fa-solid fa-list-check',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      }
    ],
  },
  {
    path: '',
    title: 'Permissions',
    icon: 'fa-solid fa-shield-halved',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    isSubMenuOpen: false,
    showSubMenu: false,
    submenu: [
      {
        path: '/admin/manage-permissions/list-permissions',
        title: 'List Permissions',
        icon: 'fa-solid fa-check-double',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      }
    ],
  },
];