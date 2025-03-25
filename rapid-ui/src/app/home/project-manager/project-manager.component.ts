import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { WorkspaceComponent } from './workspace/workspace.component';
import { ProjectComponent } from './project/project.component';
import { PageComponent } from './page/page.component';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-project-manager',
  imports: [
    CommonModule,
    WorkspaceComponent,
    ProjectComponent,
    PageComponent
  ],
  templateUrl: './project-manager.component.html',
  styleUrl: './project-manager.component.scss'
})
export class ProjectManagerComponent implements OnInit {
  workspaces = [];

  selectedWorkspace: any = null;
  selectedProject: any = null;

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
  }

  onWorkspaceSelect(workspace: any) {
    this.selectedWorkspace = workspace;
    console.log(this.selectedWorkspace);
    this.selectedProject = null; // Reset project on workspace change
  }

  onProjectSelect(project: any) {
    this.selectedProject = project;
  }
}