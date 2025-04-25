import { CommonModule } from '@angular/common';
import { Component, TemplateRef } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbModal, NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { Role, RoleService } from '../services/role.service';
import { ToastService } from '../../../services/toast.service';
import { PermissionService } from '../../manage-permissions/services/permission.service';
import { Permission } from '../../../model/permission.model';

@Component({
  selector: 'app-list-roles',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgbPaginationModule
  ],
  templateUrl: './list-roles.component.html',
  styleUrl: './list-roles.component.scss'
})
export class ListRolesComponent {
  roles: Role[] = [];
  permissions: Permission[] = [];
  filteredRoles: Role[] = [];
  selectedRole: any = "";

  editForm: FormGroup;
  editMode: boolean = false;
  searchText = '';

  page = 1;
  pageSize = 5;
  totalItems = 0;

  loading = false;

  constructor(
    private modalService: NgbModal,
    private _roleService: RoleService,
    private _permissionService: PermissionService,
    private toastr: ToastService,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      name: [null, [Validators.required]],
      code: [null, [Validators.required, Validators.pattern('^[A-Z_]+$')]],
      permissions: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.getAllRoles();
    this.getAllPermissions();
  }

  addPermissionControls(): void {    
    const permissionsArray = this.editForm.get('permissions') as FormArray;
    this.permissions.forEach(permission => {
      permissionsArray.push(new FormControl(false));
    });
  }

  openEditModal(event: Event, role: Role, editModal: TemplateRef<any>) {
    this.editForm.reset();
    if (role) {
      this.selectedRole = role;
      this.addPermissionControlsForRole(role);
    }
    this.editMode = true;
    event.stopPropagation();
    // Patch the name and code
    this.editForm.patchValue({ name: role.name, code: role.code });
    this.modalService.open(editModal, { centered: false, size: 'lg', backdrop: 'static' })
  }

  addPermissionControlsForRole(role: Role): void {    
    const permissionsArray = this.editForm.get('permissions') as FormArray;
    permissionsArray.clear();
    this.permissions.forEach(permission => {
      const isSelected = role.permissions?.includes(permission.code);
      permissionsArray.push(new FormControl(isSelected));
    });
  }

  getSelectedPermissionIds(): string[] {
    const selectedIds: string[] = [];
    const permissionsArray = this.editForm.get('permissions') as FormArray;
    permissionsArray.controls.forEach((control, index) => {
      if (control.value && this.permissions[index] && this.permissions[index].id) {
        selectedIds.push(this.permissions[index].id);
      } else {
      }
    });
    const filteredIds = selectedIds.filter(id => id !== null);
    return filteredIds;
  }

  getPermissionControl(index: number): FormControl {
    return (this.editForm.get('permissions') as FormArray).controls[index] as FormControl;
  }

  search(): void {
  }

  pageChanged(newPage: number) {
    this.page = newPage;
    this.getAllRoles();
  }

  getAllPermissions() {
    this._permissionService.getAllPermissions().subscribe(
      (res) => {
        this.permissions = res.body;
        this.addPermissionControls();
      },
      (err) => { }
    );
  }

  getAllRoles() {
    this.selectedRole = "";
    this._roleService.getRolesPage(this.page - 1, this.pageSize).subscribe(
      (res: any) => {
        this.roles = res.body.content;
        this.totalItems = res.body.page.totalElements;
        this.loading = false; // Set loading to false after data is loaded
      },
      (error) => {
        console.error('Error fetching roles:', error);
        this.loading = false; // Set loading to false on error
      }
    );
  }

  openCreateModal(content: TemplateRef<any>) {
    this.selectedRole = "";
    this.editForm.reset();
    this.editMode = false;
    this.modalService.open(content, { centered: false, size: 'lg', backdrop: 'static' });
  }

  saveRole(modal: any): void {
    if (this.editForm.valid) {
      const selectedPermissionIds = this.getSelectedPermissionIds();
      const roleData = { ...this.editForm.value, permissions: selectedPermissionIds };
      this._roleService.createRole(roleData).subscribe(
        (res)=>{
          this.toastr.showToast('Success!', res.status, 'success'); 
          this.resetFormAndGetPermissions();
          modal.dismiss();       
        },
        (err)=>{
          this.toastr.showToast('Failed!', 'Role Could not be saved', 'danger');         
        }
      );
    }
  }

  resetFormAndGetPermissions() {
    this.editForm.reset();
    this.getAllRoles();
    this.getAllPermissions();
  }

  updateRole(modal: any): void {
    if (this.editForm.valid && this.editMode) {
      const selectedPermissionIds = this.getSelectedPermissionIds();
      const roleData = { ...this.editForm.value, permissions: selectedPermissionIds };
      this._roleService.updateRole(this.selectedRole.id,roleData).subscribe(
        (res)=>{
          this.toastr.showToast('Success!', res.status, 'success');
          this.resetFormAndGetPermissions(); 
          modal.dismiss();       
        },
        (err)=>{
          this.toastr.showToast('Failed!', 'Role Could not be Updated', 'danger');         
        }
      );
    }
  }

  openDeleteConfirmationModal(event: Event, role: Role, modalContent: TemplateRef<any>) {
    event.stopPropagation(); // Prevent workspace selection when clicking delete
    if (!role.id) {
      console.error("Invalid role ID:", role);
      this.toastr.showToast('Error', 'Invalid Role ID.', 'danger');
      return;
    }

    // Open the modal and pass workspace data
    const modalRef = this.modalService.open(modalContent, { centered: true, size: 'sm', backdrop: 'static' });
    modalRef.result.then(
      (result) => {
        if (result === 'confirm') {
          if (role.id) {
            this.deleteRole(role.id);
          }
        }
      },
      () => { } // Handle dismiss
    );
  }

  deleteRole(roleId: string): void {
    this._roleService.deleteRole(roleId).subscribe(
      (res) => {
        this.getAllRoles();
        this.toastr.showToast('Success', `Role deleted successfully.`, 'success');
      },
      (err) => {
        this.toastr.showToast('Failed', `Role could not be deleted`, 'danger');
      }
    );
  }
}
