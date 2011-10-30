/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.gcsf.pcm.gui;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PinPropertySheetAction;
import org.eclipse.ui.views.properties.PropertySheet;

public class CustomPropertiesView extends PropertySheet {

  @Override
  public void createPartControl(Composite parent) {

    super.createPartControl(parent);
    IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
    IContributionItem[] items = menuManager.getItems();
    for (IContributionItem iContributionItem : items) {
      if (iContributionItem instanceof ActionContributionItem) {
        if (((ActionContributionItem) iContributionItem).getAction() instanceof PinPropertySheetAction) {
          menuManager.remove(iContributionItem);
          break;
        }
      }
    }
    for (IContributionItem iContributionItem : items) {
      if (iContributionItem.getId().equals("categories")) {
        menuManager.remove(iContributionItem);
        break;
      }
    }
    for (IContributionItem iContributionItem : items) {
      if (iContributionItem.getId().equals("filter")) {
        menuManager.remove(iContributionItem);
        break;
      }
    }

    IToolBarManager toolBarManager = getViewSite().getActionBars()
        .getToolBarManager();
    items = toolBarManager.getItems();
    for (IContributionItem iContributionItem : items) {
      if (iContributionItem instanceof ActionContributionItem) {
        if (((ActionContributionItem) iContributionItem).getAction() instanceof PinPropertySheetAction) {
          toolBarManager.remove(iContributionItem);
          break;
        }
      }
    }

    for (IContributionItem iContributionItem : items) {
      if (iContributionItem.getId().equals("categories")) {
        toolBarManager.remove(iContributionItem);
        break;
      }
    }

    for (IContributionItem iContributionItem : items) {
      if (iContributionItem.getId().equals("filter")) {
        toolBarManager.remove(iContributionItem);
        break;
      }
    }
  }

  @Override
  public boolean isPinned() {
    return false;
  }
}