package com.gcsf.pcm.model.treeviewer;

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

}
