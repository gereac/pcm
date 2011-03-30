package com.gcsf.pcm.model.treeviewer.dnd;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.part.IDropActionDelegate;

import com.gcsf.pcm.model.User;

public class UserPluginDropAdapter implements IDropActionDelegate {

  @Override
  public boolean run(Object source, Object target) {
    if (target instanceof IContainer) {
      UserTransfer transfer = UserTransfer.getInstance();
      User[] users = transfer.fromByteArray((byte[]) source);
      IContainer parent = (IContainer) target;
      for (int i = 0; i < users.length; i++) {
        writeUserFile(parent, users[i]);
      }
      return true;
    }
    // drop was not successful so return false
    return false;
  }

  private void writeUserFile(IContainer parent, User user) {
    try {
      IFile file = parent.getFile(new Path(user.getUserName()));
      ByteArrayInputStream in = createFileContents(user);
      if (file.exists()) {
        file.setContents(in, IResource.NONE, null);
      } else {
        file.create(in, IResource.NONE, null);
      }
    } catch (CoreException e) {
      e.printStackTrace();
    }
  }

  private ByteArrayInputStream createFileContents(User user) {
    // write the hierarchy of gadgets to string
    StringBuffer buf = new StringBuffer();
    writeUserString(user, buf, 0);
    return new ByteArrayInputStream(buf.toString().getBytes());
  }

  private void writeUserString(User user, StringBuffer buf, int depth) {
    for (int i = 0; i < depth; i++)
      buf.append('\t');
    buf.append(user.getUserName());
    buf.append(" :: ");
    buf.append(user.getUserPhone());
    buf.append(" :: ");
    buf.append(user.getUserEmail());
    buf.append('\n');

  }

}
