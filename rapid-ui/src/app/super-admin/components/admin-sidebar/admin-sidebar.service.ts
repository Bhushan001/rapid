import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminSidebarService {
  toggled = false;

  constructor() { }

  toggleSidebar() {
    this.toggled = !this.toggled;
  }

  getSidebarState() {
    return this.toggled;
  }

  setSidebarState(state: boolean) {
    this.toggled = state;
  }
}
