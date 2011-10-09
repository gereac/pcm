package com.gcsf.pcm.utils;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

/**
 * Suppress unwanted text widget, when SWT.Table or SWT.List
 * is selected and keyboard input is given
 * Important for linux.
 */
public class SuppressKeyboardListener implements Listener {
  /**
   * @param aComposite Composite
   */
  public SuppressKeyboardListener(Composite aComposite) {
    aComposite.addListener(SWT.KeyDown, this);
    aComposite.addListener(SWT.KeyUp, this);
  }
  
  /**
   * @param aList SWT.List
   */
  public SuppressKeyboardListener(List aList) {
    aList.addListener(SWT.KeyDown, this);
    aList.addListener(SWT.KeyUp, this);
  }
  
  /**
   * Catch keyboard events and suppress further event handling
   * @param aEvent
   *          event
   */
  public void handleEvent(Event aEvent) {
    switch (aEvent.type) {
      case SWT.KeyUp:
      case SWT.KeyDown: {
        if (aEvent.keyCode != SWT.ARROW_DOWN &&
            aEvent.keyCode != SWT.ARROW_UP &&
            aEvent.keyCode != SWT.ARROW_LEFT &&
            aEvent.keyCode != SWT.ARROW_RIGHT) {
          aEvent.doit = false;
        }
        break;
      }
      default:
        break;
    }
  }
}
