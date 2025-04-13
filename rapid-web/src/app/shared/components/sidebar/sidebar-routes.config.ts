import { RouteInfo } from "./sidebar.metadata";


//Sidebar menu Routes and data
export const ROUTES: RouteInfo[] = [
    {
      path: '',
      title: 'Home',
      icon: 'bx bx-home-alt',
      class: 'sub',
      badge: '',
      badgeClass: '',
      isExternalLink: false,
      showSubMenu: false,
      submenu: [
        {
          path: '/home/project-manager',
          title: 'Project Manager',
          icon: 'bx bx-briefcase',
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
      icon: 'bx bx-home-alt',
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
          icon: 'bx bx-briefcase',
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
          icon: 'bx bx-briefcase',
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
      icon: 'bx bx-briefcase',
      class: 'sub',
      badge: '',
      badgeClass: '',
      isExternalLink: false,
      submenu: [
        {
          path: '/home/mapping/create-mapping',
          title: 'Create Mapping',
          icon: 'bx bx-briefcase',
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
          icon: 'bx bx-briefcase',
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