// Copyright 2019 Pants project contributors (see CONTRIBUTORS.md).
// Licensed under the Apache License, Version 2.0 (see LICENSE).

package com.twitter.intellij.pants.projectview;

import com.intellij.icons.AllIcons;
import com.intellij.ide.SelectInTarget;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.ProjectViewSettings;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.ProjectAbstractTreeStructureBase;
import com.intellij.ide.projectView.impl.ProjectTreeStructure;
import com.intellij.ide.projectView.impl.ProjectViewPane;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.registry.Registry;
import com.twitter.intellij.pants.PantsBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class ProjectFilesViewPaneV2 extends ProjectViewPane {
  @NonNls public static final String ID = "ProjectPaneV2";

  private boolean myUseFileNestingRules = true;

  public ProjectFilesViewPaneV2(Project project) {
    super(project);
  }

  @NotNull
  @Override
  public String getTitle() {
    return PantsBundle.message("pants.title.project.files.v2");
  }

  @Override
  @NotNull
  public String getId() {
    return ID;
  }

  @NotNull
  @Override
  public Icon getIcon() {
    return AllIcons.General.ProjectTab;
  }

  @NotNull
  @Override
  public SelectInTarget createSelectInTarget() {
    return new PantsProjectPaneSelectInTargetV2(myProject);
  }

  @NotNull
  @Override
  protected ProjectAbstractTreeStructureBase createStructure() {
    return new PantsProjectViewPaneTreeStructure();
  }

  // should be last
  @Override
  public int getWeight() {
    return 300;
  }

  private class PantsProjectViewPaneTreeStructure extends ProjectTreeStructure implements ProjectViewSettings {
    PantsProjectViewPaneTreeStructure() {
      super(ProjectFilesViewPaneV2.this.myProject, ID);
    }

    @Override
    protected AbstractTreeNode createRoot(@NotNull final Project project, @NotNull ViewSettings settings) {
      return new ProjectFilesViewProjectNode(project, settings);
      //return new ProjectViewProjectNode(project, settings);
    }

    @Override
    public boolean isShowExcludedFiles() {
      return ProjectView.getInstance(myProject).isShowExcludedFiles(ID);
    }

    @Override
    public boolean isUseFileNestingRules() {
      return myUseFileNestingRules;
    }

    @Override
    public boolean isToBuildChildrenInBackground(@NotNull Object element) {
      return Registry.is("ide.projectView.ProjectViewPaneTreeStructure.BuildChildrenInBackground");
    }
  }
}
