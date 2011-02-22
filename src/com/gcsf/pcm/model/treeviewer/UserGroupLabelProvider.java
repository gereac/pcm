package com.gcsf.pcm.model.treeviewer;

import org.apache.commons.lang.WordUtils;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UserGroupLabelProvider extends StyledCellLabelProvider {

  public void update(ViewerCell cell) {
    StyledString styledString = null;
    Object obj = cell.getElement();
    if (obj instanceof UserGroup) {
      styledString = new StyledString(((UserGroup) obj).getGroupName());
      UserGroup parent = (UserGroup) obj;
      if (parent.getGroupMembers().size() > 0) {
        styledString.append(" (" + parent.getGroupMembers().size() + ")",
            StyledString.COUNTER_STYLER);
      }
    } else if (obj instanceof User) {
      styledString = new StyledString(((User) obj).getUserName());
    }

    cell.setText(styledString.toString());
    cell.setStyleRanges(styledString.getStyleRanges());
    cell.setImage(null);
    super.update(cell);
  }

  @Override
  public String getToolTipText(Object element) {
    String tooltipResult;
    if (element instanceof UserGroup) {
      if (((UserGroup) element).getGroupDescription() == null
          || ((UserGroup) element).getGroupDescription().equals("")) {
        tooltipResult = "@NO_TOOLTIP_AVAILABLE@";
      } else {
        tooltipResult = ((UserGroup) element).getGroupDescription();
      }
    } else {
      tooltipResult = ((User) element).toString();
    }

    return wrap(tooltipResult);
  }

  private String wrap(String s) {
    StringBuilder buffer = new StringBuilder();

    String delim = "";
    for (String line : s.trim().split("\n")) {
      buffer.append(delim);
      delim = "\n";
      buffer.append(WordUtils.wrap(line, 60, "\n", true));
    }

    return buffer.toString();
  }

}
