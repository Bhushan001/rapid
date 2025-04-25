// src/app/services/ngb-toast.service.ts
import { Injectable } from '@angular/core';
import { Toast } from '../model/toast.model';

@Injectable({ providedIn: 'root' })
export class ToastService {
  toasts: Toast[] = [];

  private toastClassMap: { [key: string]: string } = {
    success: 'bg-success text-white',
    danger: 'bg-danger text-white',
    warning: 'bg-warning text-dark',
    info: 'bg-info text-white',
    error: 'bg-danger text-white',
    primary: 'bg-primary text-white'
  };

  private toastIconMap: { [key: string]: string } = {
    success: 'fa-solid fa-check-circle',
    danger: 'fa-solid fa-xmark-circle',
    warning: 'fa-solid fa-triangle-exclamation',
    info: 'fa-solid fa-circle-info',
    error: 'fa-solid fa-exclamation-circle',
    primary: 'fa-solid fa-star'
  };

  /**
   * Show toast with header, body, type and options
   * @param header Header text
   * @param body Toast message
   * @param type success | danger | info | warning | error | primary
   * @param delay Auto-dismiss delay (default 3000ms)
   * @param showCloseButton Whether to show close icon (default true)
   */
  showToast(header: string, body: string, type: string = 'info', delay: number = 3000, showCloseButton: boolean = true) {
    const classname = this.toastClassMap[type];
    const icon = this.toastIconMap[type];
    this.toasts.push({ header, body, classname, delay, icon, showCloseButton });
  }

  remove(toast: Toast) {
    this.toasts = this.toasts.filter(t => t !== toast);
  }

  clearAll() {
    this.toasts = [];
  }
}