/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
/*
Definitive Guide to Swing for Java 2, Second Edition
By John Zukowski
ISBN: 1-893115-78-X
Publisher: APress
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

public class UndoableDrawingPanel2 extends JPanel implements StateEditable {

    private static String POLYGON_KEY = "Polygon";
    UndoableEditSupport undoableEditSupport = new UndoableEditSupport(this);
    Polygon polygon = new Polygon();

    public UndoableDrawingPanel2() {
        MouseListener mouseListener = new MouseAdapter() {

            public void mouseReleased(MouseEvent mouseEvent) {
                StateEdit stateEdit = new StateEdit(UndoableDrawingPanel2.this);
                polygon.addPoint(mouseEvent.getX(), mouseEvent.getY());
                stateEdit.end();
                undoableEditSupport.postEdit(stateEdit);
                repaint();
            }
        };
        addMouseListener(mouseListener);
    }

    public void addUndoableEditListener(
            UndoableEditListener undoableEditListener) {
        undoableEditSupport.addUndoableEditListener(undoableEditListener);
    }

    public void removeUndoableEditListener(
            UndoableEditListener undoableEditListener) {
        undoableEditSupport.removeUndoableEditListener(undoableEditListener);
    }

    public void storeState(Hashtable state) {
        state.put(POLYGON_KEY, getPolygon());
    }

    public void restoreState(Hashtable state) {
        Polygon polygon = (Polygon) state.get(POLYGON_KEY);
        if (polygon != null) {
            setPolygon(polygon);
        }
    }

    public void setPolygon(Polygon newValue) {
        polygon = newValue;
        repaint();
    }

    public Polygon getPolygon() {
        Polygon returnValue;
        if (polygon.npoints == 0) {
            returnValue = new Polygon();
        } else {
            returnValue = new Polygon(polygon.xpoints, polygon.ypoints,
                    polygon.npoints);
        }
        return returnValue;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawPolygon(polygon);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Drawing Sample2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UndoableDrawingPanel2 drawingPanel = new UndoableDrawingPanel2();

        UndoManager manager = new UndoManager();
        drawingPanel.addUndoableEditListener(manager);

        JToolBar toolbar = new JToolBar();
        toolbar.add(UndoManagerHelper.getUndoAction(manager));
        toolbar.add(UndoManagerHelper.getRedoAction(manager));

        Container content = frame.getContentPane();
        content.add(toolbar, BorderLayout.NORTH);
        content.add(drawingPanel, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);
    }
}

class UndoManagerHelper {

    public static Action getUndoAction(UndoManager manager, String label) {
        return new UndoAction(manager, label);
    }

    public static Action getUndoAction(UndoManager manager) {
        return new UndoAction(manager, "Undo");
    }

    public static Action getRedoAction(UndoManager manager, String label) {
        return new RedoAction(manager, label);
    }

    public static Action getRedoAction(UndoManager manager) {
        return new RedoAction(manager, "Redo");
    }

    private abstract static class UndoRedoAction extends AbstractAction {

        UndoManager undoManager = new UndoManager();
        String errorMessage = "Cannot undo";
        String errorTitle = "Undo Problem";

        protected UndoRedoAction(UndoManager manager, String name) {
            super(name);
            undoManager = manager;
        }

        public void setErrorMessage(String newValue) {
            errorMessage = newValue;
        }

        public void setErrorTitle(String newValue) {
            errorTitle = newValue;
        }

        protected void showMessage(Object source) {
            if (source instanceof Component) {
                JOptionPane.showMessageDialog((Component) source, errorMessage,
                        errorTitle, JOptionPane.WARNING_MESSAGE);
            } else {
                System.err.println(errorMessage);
            }
        }
    }

    public static class UndoAction extends UndoRedoAction {

        public UndoAction(UndoManager manager, String name) {
            super(manager, name);
            setErrorMessage("Cannot undo");
            setErrorTitle("Undo Problem");
        }

        public void actionPerformed(ActionEvent actionEvent) {
            try {
                undoManager.undo();
            } catch (CannotUndoException cannotUndoException) {
                showMessage(actionEvent.getSource());
            }
        }
    }

    public static class RedoAction extends UndoRedoAction {

        String errorMessage = "Cannot redo";
        String errorTitle = "Redo Problem";

        public RedoAction(UndoManager manager, String name) {
            super(manager, name);
            setErrorMessage("Cannot redo");
            setErrorTitle("Redo Problem");
        }

        public void actionPerformed(ActionEvent actionEvent) {
            try {
                undoManager.redo();
            } catch (CannotRedoException cannotRedoException) {
                showMessage(actionEvent.getSource());
            }
        }
    }
}


