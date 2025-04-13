import { RouteInfo } from "../sidebar/sidebar.metadata";

//Sidebar menu Routes and data
export const ADMINROUTES: RouteInfo[] = [
    {
      path: '/admin/home',
      title: 'Home',
      icon: 'bx bx-home-alt',
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
      title: 'Manage Clients',
      icon: 'bx bx-group',
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
          icon: 'bx bx-bar-chart-alt-2',
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
      title: 'Manage Users',
      icon: 'bx bx-user',
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
          icon: 'bx bx-list-ul',
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