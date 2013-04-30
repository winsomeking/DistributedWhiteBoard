/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.view;

import java.awt.Color;

/**
 *
 * @author elf
 */
public class ColorfulCell {

        Color c = null;
        String n = "";
        String i = "";
        ColorfulCell(String name, String id, Color color){
            n = name;
            i = id;
            c = color;
        }

}
