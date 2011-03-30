package com.gcsf.pcm.model.treeviewer.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.gcsf.pcm.model.User;

public class UserTransfer extends ByteArrayTransfer {

  private static UserTransfer ourInstance;

  private static final String TYPE_NAME = "user-transfer-format";

  private static final int TYPEID = registerType(TYPE_NAME);

  private UserTransfer() {
    // nothing to do yet
  }

  public static UserTransfer getInstance() {
    if (ourInstance == null) {
      ourInstance = new UserTransfer();
    }
    return ourInstance;
  }

  protected User[] fromByteArray(byte[] bytes) {
    DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));

    try {
      /* read number of gadgets */
      int n = in.readInt();
      /* read gadgets */
      User[] users = new User[n];
      for (int i = 0; i < n; i++) {
        User user = readUser(null, in);
        if (user == null) {
          return null;
        }
        users[i] = user;
      }
      return users;
    } catch (IOException e) {
      return null;
    }
  }

  /*
   * Method declared on Transfer.
   */
  protected int[] getTypeIds() {
    return new int[] { TYPEID };
  }

  /*
   * Method declared on Transfer.
   */
  protected String[] getTypeNames() {
    return new String[] { TYPE_NAME };
  }

  /*
   * Method declared on Transfer.
   */
  protected void javaToNative(Object object, TransferData transferData) {
    byte[] bytes = toByteArray((User[]) object);
    if (bytes != null)
      super.javaToNative(bytes, transferData);
  }

  /*
   * Method declared on Transfer.
   */
  protected Object nativeToJava(TransferData transferData) {
    byte[] bytes = (byte[]) super.nativeToJava(transferData);
    return fromByteArray(bytes);
  }

  /**
   * Reads and returns a single user from the given stream.
   */
  private User readUser(User user, DataInputStream dataIn) throws IOException {
    /**
     * User serialization format is as follows: (String) name of user (String)
     * phone of user (String) email of user
     */
    String userName = dataIn.readUTF();
    String userPhone = dataIn.readUTF();
    String userEmail = dataIn.readUTF();
    User aUser = new User(userName, userPhone, userEmail);
    return aUser;
  }

  protected byte[] toByteArray(User[] users) {
    /**
     * User serialization format is as follows: (String) name of user (String)
     * phone of user (String) email of user
     */
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(byteOut);

    byte[] bytes = null;

    try {
      /* write number of markers */
      out.writeInt(users.length);

      /* write markers */
      for (int i = 0; i < users.length; i++) {
        writeUser((User) users[i], out);
      }
      out.close();
      bytes = byteOut.toByteArray();
    } catch (IOException e) {
      // when in doubt send nothing
    }
    return bytes;
  }

  /**
   * Writes the given user to the stream.
   */
  private void writeUser(User user, DataOutputStream dataOut)
    throws IOException {
    /**
     * User serialization format is as follows: (String) name of user (String)
     * phone of user (String) email of user
     */
    dataOut.writeUTF(user.getUserName());
    dataOut.writeUTF(user.getUserPhone());
    dataOut.writeUTF(user.getUserEmail());
  }

  @Override
  protected boolean validate(Object object) {
    System.out.println("inside validate in usertransfer" + object.toString());
    return true;
  }

}
