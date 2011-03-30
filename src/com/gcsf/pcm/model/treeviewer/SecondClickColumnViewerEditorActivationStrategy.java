package com.gcsf.pcm.model.treeviewer;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class SecondClickColumnViewerEditorActivationStrategy extends
    ColumnViewerEditorActivationStrategy implements ISelectionChangedListener {

  private Object selectedElement;

  public SecondClickColumnViewerEditorActivationStrategy(ColumnViewer viewer) {
    super(viewer);
    viewer.addSelectionChangedListener(this);
  }

  @Override
  protected boolean isEditorActivationEvent(
      ColumnViewerEditorActivationEvent event) {
    IStructuredSelection selection = (IStructuredSelection) getViewer()
        .getSelection();

    return selection.size() == 1 && super.isEditorActivationEvent(event)
        && selectedElement == selection.getFirstElement();
  }

  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    IStructuredSelection ss = (IStructuredSelection) event.getSelection();

    if (ss.size() == 1) {
      selectedElement = ss.getFirstElement();
      return;
    }

    selectedElement = null;
  }

}
