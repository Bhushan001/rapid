export interface Toast {
    header: string;
    body: string;
    classname: string;
    delay?: number;
    icon?: string;
    position?: 'top-right' | 'top-left' | 'bottom-right' | 'bottom-left' | 'center'; // <-- THIS LINE is important
    showCloseButton?: boolean;
  }