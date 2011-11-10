package com.gcsf.pcm.intro;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.handlers.IHandlerService;

import com.gcsf.pcm.Activator;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

  private TrayItem trayItem;

  private Image trayImage;

  private IWorkbenchWindow window;

  private final static String EXIT_COMMAND_ID = "com.gcsf.pcm.tray.exitCommand";

  private final static String ABOUT_COMMAND_ID = "org.eclipse.ui.help.aboutAction";

  public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
    super(configurer);
  }

  public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
    return new ApplicationActionBarAdvisor(configurer);
  }

  public void preWindowOpen() {
    IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
    configurer.setInitialSize(new Point(700, 550));
    configurer.setShowCoolBar(true);
    configurer.setShowMenuBar(true);
    configurer.setShowStatusLine(true);
    configurer.setTitle(Platform.getProduct().getProperty("appName"));
    configurer.setShowProgressIndicator(true);
  }

  @Override
  public void postWindowOpen() {
    super.postWindowOpen();
    window = getWindowConfigurer().getWindow();
    trayItem = initTaskItem(window);
    // Some OS might not support tray items
    if (trayItem != null) {
      minimizeBehavior();
      // Create exit and about action on the icon
      hookPopupMenu();
    }
  }

  // This methods create the tray item and return a reference
  private TrayItem initTaskItem(IWorkbenchWindow window) {
    Tray tray = window.getShell().getDisplay().getSystemTray();
    TrayItem trayItem = new TrayItem(tray, SWT.NONE);
    trayImage = Activator.getImageDescriptor(
        Platform.getProduct().getProperty("aboutImage")).createImage();
    trayItem.setImage(trayImage);
    trayItem.setToolTipText(Platform.getProduct().getProperty("appName"));
    return trayItem;

  }

  private void minimizeBehavior() {
    // Add a listener to the shell
    window.getShell().addShellListener(new ShellAdapter() {
      // If the window is minimized hide the window
      public void shellIconified(ShellEvent e) {
        window.getShell().setVisible(false);
      }
    });
    // If user double-clicks on the tray icons the application will be
    // visible again
    trayItem.addListener(SWT.DefaultSelection, new Listener() {
      public void handleEvent(Event event) {
        Shell shell = window.getShell();
        if (!shell.isVisible()) {
          window.getShell().setMinimized(false);
          shell.setVisible(true);
        }
      }
    });
  }

  // We hook up on menu entry which allows to close the application
  private void hookPopupMenu() {
    trayItem.addListener(SWT.MenuDetect, new Listener() {
      public void handleEvent(Event event) {
        Menu menu = new Menu(window.getShell(), SWT.POP_UP);

        MenuItem about = new MenuItem(menu, SWT.NONE);
        about.setText("About");
        about.addListener(SWT.Selection, new Listener() {

          public void handleEvent(Event event) {
            // Lets call our command
            IHandlerService handlerService = (IHandlerService) window
                .getService(IHandlerService.class);
            try {
              handlerService.executeCommand(ABOUT_COMMAND_ID, null);
            } catch (Exception ex) {
              throw new RuntimeException(ABOUT_COMMAND_ID);
            }
          }
        });
        // Creates a new menu item that terminates the program
        // when selected
        MenuItem exit = new MenuItem(menu, SWT.NONE);
        exit.setText("Exit");
        exit.addListener(SWT.Selection, new Listener() {
          public void handleEvent(Event event) {
            // Lets call our command
            IHandlerService handlerService = (IHandlerService) window
                .getService(IHandlerService.class);
            try {
              handlerService.executeCommand(EXIT_COMMAND_ID, null);
            } catch (Exception ex) {
              throw new RuntimeException(EXIT_COMMAND_ID);
            }
          }
        });
        // We need to make the menu visible
        menu.setVisible(true);
      }
    });
  }

  // We need to clean-up after ourself
  @Override
  public void dispose() {
    if (trayImage != null) {
      trayImage.dispose();
    }
    if (trayItem != null) {
      trayItem.dispose();
    }
    if (window != null) {
      window = null;
    }
  }

  @Override
  public void postWindowCreate() {
    Shell shell = getWindowConfigurer().getWindow().getShell();
    shell.setMaximized(true);
  }
}
