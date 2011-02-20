package com.gcsf.pcm.gui;

import java.io.PrintStream;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class GeneralPerspective implements IPerspectiveFactory {

  public static final String PERSPECTIVE_ID = "com.gcsf.pcm.perspective";

  public void createInitialLayout(IPageLayout layout) {
    MessageConsole myConsole = new MessageConsole("Console", null); // declare
    // console

    ConsolePlugin.getDefault().getConsoleManager()
        .addConsoles(new IConsole[] { myConsole });

    MessageConsoleStream stream = myConsole.newMessageStream();

    PrintStream myS = new PrintStream(stream);
    System.setOut(myS); // link standard output stream to the console
    System.setErr(myS); // link error output stream to the console

    layout.setEditorAreaVisible(false);
  }
}
