/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.view;

import eboard.itf.model.IUser;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author elf
 */
public class ColorfulList extends JList{

//    ListCellRenderer renderer = new ColorFulListCellRenderer();
//    this.lstUser.setCellRenderer(renderer);
    DefaultListModel dlm = null;
    Color basicColor = new Color(100,100,0);
    ColorfulList(){
        super();
        dlm = new DefaultListModel();
        super.setModel(dlm);
        ListCellRenderer renderer = new eboard.view.ColorfulListCellRenderer();
        super.setCellRenderer(renderer);
    }
//    public void setCellRenderer(ListCellRenderer cellRenderer) {
//        super.setCellRenderer(new ColorfulListCellRenderer());
//    }

    
    public void addUser(IUser user, Color c){
        int size = dlm.getSize();
        if (size == 0) {
            dlm.addElement(
                    new ColorfulCell(
                        user.getUserName(),
                        user.getUserId(),
                        basicColor)
                     );
        }else{
            ColorfulCell lastCell = (ColorfulCell)dlm.get(size-1);
            int green = (int)(lastCell.c.getGreen()+Math.random()*100)%255;
            int red = (int)(lastCell.c.getRed()+Math.random()*100)%255;
            int blue = (int)(lastCell.c.getBlue()+Math.random()*100)%255;
            dlm.addElement(
                    new ColorfulCell(
                        user.getUserName(),
                        user.getUserId(),
                        new Color(red,green,blue))
                     );
        }

    }

    public void addUsers(HashMap<String, IUser> users, ArrayList<Color > colors){
        Iterator iter = users.entrySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            Map.Entry<String, IUser> entry = (Map.Entry<String, IUser>) iter.next();
            addUser(entry.getValue(),colors.get(i++));
        }
    }

    public void removeUser(String userId){
        if(userId == null) return;
        int size = dlm.getSize();
        int i = 0;
        for (; i < size; i ++){
            ColorfulCell c = (ColorfulCell) dlm.get(i);
            if (userId.equals(c.i)) break;
        }

        if(super.getSelectedIndex() == i)
            super.setSelectedIndex(-1);
        dlm.remove(i);
    }




    public void addElement(ColorfulCell cell){
        dlm.addElement(cell);
    }

    public void removeElement(ColorfulCell cell){
        dlm.removeElement(cell);
    }

    public void removeElementAt(int index){
        dlm.removeElementAt(index);
    }

    public void removeAllElements(){
        dlm.removeAllElements();

    }

    public ColorfulCell elementAt(int index){
       return (ColorfulCell) dlm.getElementAt(index);
    }

    
}
