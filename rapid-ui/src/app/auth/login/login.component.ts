import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports:[
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class LoginComponent {
  loginForm: FormGroup;
  showPassword: boolean = false;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private _router: Router,
    private toastr: ToastService
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  validateAuthToken() {
    this.userService.validateToken().subscribe(
      (res)=>{
        if(res) {
          localStorage.setItem("isAuthenticated", JSON.stringify(true));
        }        
      }
    );
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.userService.login(this.loginForm.value).subscribe({
        next: (response) => {
          // Handle successful login
          console.log('Login successful', response);
          localStorage.setItem("authToken", response.token);
          this.validateAuthToken();
          this._router.navigate(['home','project-manager']);
          this.toastr.showToast('Success', 'Loggen In Successfully', 'success'); // Display toast message
        },
        error: (error) => {
          // Error handling is now done in the service, toasts will be displayed
          console.error('Login failed', error);
          this.toastr.showToast('Failed', error.message, 'Error'); // Display toast message
        }
      });
    }
  }
}
