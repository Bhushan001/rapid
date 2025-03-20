import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { WorkspaceComponent } from './workspace/workspace.component';
import { ProjectComponent } from './project/project.component';
import { PageComponent } from './page/page.component';

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
  workspaces = Array.from({ length: 20 }, (_, i) => ({
    name: `Workspace ${i + 1}`,
    description: `Description for workspace ${i + 1}`,
    createdDate: new Date(2024, 0, i + 1).toLocaleDateString(),
    projects: Array.from({ length: 20 }, (_, j) => ({
      name: `Project ${j + 1} of WS ${i + 1}`,
      description: `Description for project ${j + 1}`,
      createdDate: new Date(2024, 0, j + 1).toLocaleDateString(),
      pages: Array.from({ length: 20 }, (_, k) => ({
        name: `Page ${k + 1}`,
        description: `Page ${k + 1} details`,
        createdDate: new Date(2024, 0, k + 1).toLocaleDateString()
      }))
    }))
  }));

  selectedWorkspace: any = null;
  selectedProject: any = null;

  constructor() { }

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