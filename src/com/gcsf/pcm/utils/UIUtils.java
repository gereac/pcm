package com.gcsf.pcm.utils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

public class UIUtils {

  /**
   * @param action
   *          the dropdown action.
   * @param manager
   *          the toolbar containing the action.
   */
  public static void positionDropDownMenu(Action action, ToolBarManager manager) {
    Menu menu = action.getMenuCreator().getMenu(manager.getControl());
    if (menu != null) {

      /* Adjust Location */
      IContributionItem contributionItem = manager.find(action.getId());
      if (contributionItem != null
          && contributionItem instanceof ActionContributionItem) {
        Widget widget = ((ActionContributionItem) contributionItem).getWidget();
        if (widget != null && widget instanceof ToolItem) {
          ToolItem item = (ToolItem) widget;
          Rectangle rect = item.getBounds();
          Point pt = new Point(rect.x, rect.y + rect.height);
          pt = manager.getControl().toDisplay(pt);
          menu.setLocation(pt.x, pt.y);
        }
      }

      /* Set Visible */
      menu.setVisible(true);
    }
  }
}
