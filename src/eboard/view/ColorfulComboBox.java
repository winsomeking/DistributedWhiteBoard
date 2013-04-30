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
import javax.swing.JComboBox;

/**
 *
 * @author elf
 */
public class ColorfulComboBox extends JComboBox{

    ColorfulComboBox(){
        super();
        super.setRenderer(new ColorfulListCellRenderer());
        super.addItem(new ColorfulCell(
                        "All",
                        null,
                        new Color(255,255,255)));
    }

        public void addUser(IUser user, Color c){

        int size = super.getItemCount();
        if (size == 0) {
            super.addItem(
                    new ColorfulCell(
                        "All",
                        null,
                        new Color(255,255,255))
                     );
        }else{
            ColorfulCell lastCell = (ColorfulCell)super.getItemAt(size-1);
            int green = (int)(lastCell.c.getGreen()+Math.random()*200)%255;
            int red = (int)(lastCell.c.getRed()+Math.random()*200)%255;
            int blue = (int)(lastCell.c.getBlue()+Math.random()*200)%255;
            super.addItem(
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

    public Color getUserColor(String userId){
        if(userId == null) return Color.black;
        int size = super.getItemCount();
        int i = 0;
        ColorfulCell c = null;
        for (; i < size; i ++){
            c = (ColorfulCell)super.getItemAt(i);
            if (userId.equals(c.i)) break;
        }
        return i == size ? Color.black:c.c;
    }


    public void removeUser(String userId){
        if(userId == null) return;
        int size = super.getItemCount();
        int i = 0;
        for (; i < size; i ++){
            ColorfulCell c = (ColorfulCell)super.getItemAt(i);
            if (userId.equals(c.i)) break;
        }
        
        if(super.getSelectedIndex() == i)
            super.setSelectedIndex(0);
        this.removeItem(i);
    }
}
