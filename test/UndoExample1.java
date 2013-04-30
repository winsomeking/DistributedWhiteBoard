/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

public class UndoExample1 extends JFrame {
  public UndoExample1() {
    super("Undo/Redo Example 1");

    pane = new JTextPane();
    pane.setEditable(true);    // Editable
    getContentPane().add(new JScrollPane(pane), "Center");

    // Add a menu bar
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    // Populate the menu bar
    createMenuBar();
  }

  public void createMenuBar() {
    // Remove the existing menu items
    int count = menuBar.getMenuCount();
    for (int i = 0; i < count; i++) {
      menuBar.remove(menuBar.getMenu(0));
    }

    // Build the new menu.
    Action[] actions = pane.getActions();
    Hashtable actionHash = new Hashtable();
    count = actions.length;
    for (int i = 0; i < count; i++) {
      actionHash.put(actions[i].getValue(Action.NAME), actions[i]);
    }

    // Add the font menu
    JMenu menu = MenuBuilder.buildMenu("Font", fontSpec, actionHash);
    if (menu != null) {
      menuBar.add(menu);
    }

    // Add the alignment menu
    menu = MenuBuilder.buildMenu("Align", alignSpec, actionHash);
    if (menu != null) {
      menuBar.add(menu);
    }
  }

  public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception evt) {}

    JFrame f = new UndoExample1();
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    f.setSize(250, 300);
    f.setVisible(true);

    // Create and show a frame monitoring undoable edits
    JFrame undoMonitor = new JFrame("Undo Monitor");
    final JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    undoMonitor.getContentPane().add(new JScrollPane(textArea));
    undoMonitor.setBounds(f.getLocation().x + f.getSize().width,
              f.getLocation().y, 400, 200);
    undoMonitor.setVisible(true);

    pane.getDocument().addUndoableEditListener(new UndoableEditListener() {
      public void undoableEditHappened(UndoableEditEvent evt) {
        UndoableEdit edit = evt.getEdit();
        textArea.append(edit.getPresentationName() + "(" +
                edit.toString() + ")\n");
      }
    });

    // Create and show a frame monitoring document edits
    JFrame editMonitor = new JFrame("Edit Monitor");
    final JTextArea textArea2 = new JTextArea();
    textArea2.setEditable(false);
    editMonitor.getContentPane().add(new JScrollPane(textArea2));
    editMonitor.setBounds(undoMonitor.getLocation().x,
          undoMonitor.getLocation().y + undoMonitor.getSize().height,
          400, 200);
    editMonitor.setVisible(true);

    pane.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent evt) {
        textArea2.append("Attribute change\n");
      }
      public void insertUpdate(DocumentEvent evt) {
        textArea2.append("Text insertion\n");
      }
      public void removeUpdate(DocumentEvent evt) {
        textArea2.append("Text removal\n");
      }
    });
  }

  private static JTextPane pane;
  private static JMenuBar menuBar;

  private static MenuSpec[] sizeSpec = new MenuSpec[] {
      new MenuSpec("Size 8",  "font-size-8"),
      new MenuSpec("Size 10", "font-size-10"),
      new MenuSpec("Size 12", "font-size-12"),
      new MenuSpec("Size 14", "font-size-14"),
      new MenuSpec("Size 16", "font-size-16"),
      new MenuSpec("Size 18", "font-size-18"),
      new MenuSpec("Size 24", "font-size-24"),
      new MenuSpec("Size 36", "font-size-36"),
      new MenuSpec("Size 48", "font-size-48")
  };

  private static MenuSpec[] familySpec = new MenuSpec[] {
      new MenuSpec("Sans Serif", "font-family-SansSerif"),
      new MenuSpec("Monospaced", "font-family-Monospaced"),
      new MenuSpec("Serif", "font-family-Serif")
  };

  private static MenuSpec[] styleSpec = new MenuSpec[] {
      new MenuSpec("Bold", "font-bold"),
      new MenuSpec("Italics", "font-italic"),
      new MenuSpec("Underline", "font-underline")
  };

  // Menu definitions for fonts
  private static MenuSpec[] fontSpec = new MenuSpec[] {
      new MenuSpec("Size", sizeSpec),
      new MenuSpec("Family", familySpec),
      new MenuSpec("Style", styleSpec)
  };

  // Alignment
  private static MenuSpec[] alignSpec = new MenuSpec[] {
      new MenuSpec("Left", "left-justify"),
      new MenuSpec("Center", "center-justify"),
      new MenuSpec("Right", "right-justify")
  };
}


class MenuSpec {
  public MenuSpec(String name, MenuSpec[] subMenus) {
    this.name = name;
    this.subMenus = subMenus;
  }

  public MenuSpec(String name, String actionName) {
    this.name = name;
    this.actionName = actionName;
  }

  public MenuSpec(String name, Action action) {
    this.name = name;
    this.action = action;
  }

  public boolean isSubMenu() {
    return subMenus != null;
  }

  public boolean isAction() {
    return action != null;
  }

  public String getName() {
    return name;
  }

  public MenuSpec[] getSubMenus() {
    return subMenus;
  }

  public String getActionName() {
    return actionName;
  }

  public Action getAction() {
    return action;
  }

  private String name;

  private String actionName;

  private Action action;

  private MenuSpec[] subMenus;
}


class MenuBuilder {
  public static JMenu buildMenu(String name, MenuSpec[] menuSpecs,
      Hashtable actions) {
    int count = menuSpecs.length;

    JMenu menu = new JMenu(name);
    for (int i = 0; i < count; i++) {
      MenuSpec spec = menuSpecs[i];
      if (spec.isSubMenu()) {
        // Recurse to handle a sub menu
        JMenu subMenu = buildMenu(spec.getName(), spec.getSubMenus(),
            actions);
        if (subMenu != null) {
          menu.add(subMenu);
        }
      } else if (spec.isAction()) {
        // It's an Action - add it directly to the menu
        menu.add(spec.getAction());
      } else {
        // It's an action name - add it if possible
        String actionName = spec.getActionName();
        Action targetAction = (Action) actions.get(actionName);

        // Create the menu item
        JMenuItem menuItem = menu.add(spec.getName());
        if (targetAction != null) {
          // The editor kit knows the action
          menuItem.addActionListener(targetAction);
        } else {
          // Action not known - disable the menu item
          menuItem.setEnabled(false);
        }
      }
    }

    // Return null if nothing was added to the menu.
    if (menu.getMenuComponentCount() == 0) {
      menu = null;
    }

    return menu;
  }
}


