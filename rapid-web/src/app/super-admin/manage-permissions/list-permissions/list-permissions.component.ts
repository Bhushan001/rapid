import { CommonModule } from '@angular/common';
import { Component, TemplateRef } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbModal, NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { Permission, PermissionService } from '../services/permission.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-list-permissions',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgbPaginationModule
  ],
  templateUrl: './list-permissions.component.html',
  styleUrl: './list-permissions.component.scss'
})
export class ListPermissionsComponent {
  permissions: Permission[] = [];
  filteredPermissions: Permission[] = [];
  selectedPermissionId: any = "";

  editForm: FormGroup;
  editMode: boolean = false;
  searchText = '';

  page = 1;
  pageSize = 5;
  totalItems = 0;

  loading = false;

  constructor(
    private modalService: NgbModal,
    private _permissionService: PermissionService,
    private toastr: ToastService,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      name: ['', [Validators.required]],
      code: ['',[Validators.required, Validators.pattern('^[a-z_]+$')]]
    });
  }

  ngOnInit(): void {
    this.getAllPermissions();
  }

  search(): void {
  }

  pageChanged(newPage: number) {
    this.page = newPage;
    this.getAllPermissions();
  }


  getAllPermissions() {
    this.selectedPermissionId = "";
    this.loading = true; // Set loading to true
    this._permissionService.getPermissionsPage(this.page - 1, this.pageSize).subscribe(
      (res: any) => {
        this.permissions = res.body.content;
        this.totalItems = res.body.page.totalElements;
        this.loading = false; // Set loading to false after data is loaded
      },
      (error) => {
        console.error('Error fetching permissions:', error);
        this.loading = false; // Set loading to false on error
      }
    );
  }

  openCreateModal(content: TemplateRef<any>) {
    this.selectedPermissionId = "";
    this.editForm.reset();
    this.editMode = false;
    this.modalService.open(content, { centered: true, size: 'lg', backdrop: 'static' });
  }

  openEditModal(event: Event, permission: Permission, editModal: TemplateRef<any>) {
    if(permission) {
      this.selectedPermissionId = permission.id;
    }
    this.editMode = true;
    event.stopPropagation();
    this.editForm.patchValue(permission);
    this.modalService.open(editModal, { centered: true, size: 'lg', backdrop: 'static' })
  }

  savePermission(modal: any): void {
    if (this.editForm.valid) {
      this._permissionService.createPermission(this.editForm.value).subscribe(
        (res) => {
          this.getAllPermissions();
          this.toastr.showToast('Success', `Permission updated successfully.`, 'success');
          this.editForm.reset();
          modal.dismiss();
        },
        (err) => {
          console.log(err);
          this.toastr.showToast('Failed', `Permission could not be updated`, 'danger');
        }
      );
    }
  }


  updatePermission(modal: any): void {
    if (this.editForm.valid && this.editMode) {
      this._permissionService.updatePermission(this.selectedPermissionId, this.editForm.value).subscribe(
        (res) => {
          this.getAllPermissions();
          this.toastr.showToast('Success', `Permission updated successfully.`, 'success');
          this.editForm.reset();
          modal.dismiss();
        },
        (err) => {
          console.log(err);
          this.toastr.showToast('Failed', `Permission could not be updated`, 'danger');
        }
      );
    }
  }


  openDeleteConfirmationModal(event: Event, permission: Permission, modalContent: TemplateRef<any>) {
    event.stopPropagation(); // Prevent workspace selection when clicking delete

    if (!permission.id) {
      console.error("Invalid permission ID:", permission);
      this.toastr.showToast('Error', 'Invalid Permission ID.', 'danger');
      return;
    }

    // Open the modal and pass workspace data
    const modalRef = this.modalService.open(modalContent, { centered: true, size: 'sm', backdrop: 'static' });
    modalRef.result.then(
      (result) => {
        if (result === 'confirm') {
          if (permission.id) {
            this.deletePermission(permission.id);
          }
        }
      },
      () => { } // Handle dismiss
    );
  }

  deletePermission(permissionId: string): void {
    this._permissionService.deletePermission(permissionId).subscribe(
      (res) => {
        this.getAllPermissions();
        this.toastr.showToast('Success', `Permission deleted successfully.`, 'success');
      },
      (err) => {
        this.toastr.showToast('Failed', `Permission could not be deleted`, 'danger');
      }
    );
  }
}
