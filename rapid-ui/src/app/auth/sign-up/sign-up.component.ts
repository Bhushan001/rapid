import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ToastService } from '../../services/toast.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-signup',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SignupComponent implements OnInit {
  public signupForm: FormGroup<{
    username: FormControl<string>;
    password: FormControl<string>;
    confirmPassword: FormControl<string>;
    firstName: FormControl<string>;
    lastName: FormControl<string>;
    birthDate: FormControl<string>;
    country: FormControl<string>;
  }>;
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private _userService: UserService,
    private toast: ToastService,
    private _router: Router
  ) {
    this.signupForm = new FormGroup({
      username: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(4)] }),
      password: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(6)] }),
      confirmPassword: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      firstName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      lastName: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      birthDate: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
      country: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    },{
      validators: this.passwordsMatchValidator
    });
  }

  passwordsMatchValidator(group: AbstractControl): ValidationErrors | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordsMismatch: true };
  }

  ngOnInit(): void {
    console.log("loaded");
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    if (this.signupForm.invalid) return;

    const { confirmPassword, ...payload } = this.signupForm.value;
    this._userService.signup(payload).subscribe({
      next: (res) => {
        this.toast.showToast('Success', 'User registered successfully', 'success');
        this.signupForm.reset();
        this._router.navigate(['auth','login']);
      },
      error: (err) => {
        this.toast.showToast('Error', 'Signup failed. Please try again.', 'danger');
        console.error(err);
      }
    });
  }

  get f() {
    return this.signupForm.controls;
  }
}
