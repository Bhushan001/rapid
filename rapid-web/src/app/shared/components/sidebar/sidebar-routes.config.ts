import { RouteInfo } from "./sidebar.metadata";

//Sidebar menu Routes and data
export const ROUTES: RouteInfo[] = [
  {
    path: '',
    title: 'Home',
    icon: 'fa-solid fa-home',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    showSubMenu: false,
    submenu: [
      {
        path: '/home/project-manager',
        title: 'Project Manager',
        icon: 'fa-solid fa-tasks',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      },
    ],
    isSubMenuOpen: false
  },
  {
    path: '',
    title: 'Schema',
    icon: 'fa-solid fa-layer-group',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    isSubMenuOpen: false,
    showSubMenu: false,
    submenu: [
      {
        path: '/home/schema/request-schema',
        title: 'Request Schema',
        icon: 'fa-solid fa-file-lines',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      },
      {
        path: '/home/schema/s1-schema',
        title: 'S1 Schema',
        icon: 'fa-solid fa-database',
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
    title: 'Mapping',
    icon: 'fa-solid fa-right-left',
    class: 'sub',
    badge: '',
    badgeClass: '',
    isExternalLink: false,
    submenu: [
      {
        path: '/home/mapping/create-mapping',
        title: 'Create Mapping',
        icon: 'fa-solid fa-plus-circle',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      },
      {
        path: '/home/mapping/mapping-list',
        title: 'List Mappings',
        icon: 'fa-solid fa-list-ul',
        class: '',
        badge: '',
        badgeClass: '',
        isExternalLink: false,
        submenu: [],
        isSubMenuOpen: false,
        showSubMenu: false
      }
    ],
    isSubMenuOpen: false,
    showSubMenu: false
  }
];