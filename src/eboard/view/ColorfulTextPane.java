/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.text.Style;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author elf
 */
public class ColorfulTextPane extends JTextPane{

    private int paraCounter = 0;
    DefaultStyledDocument  doc = null;
    StyleContext sc = null;
    public ColorfulTextPane(){
        super();

        sc = new StyleContext();
        doc = new DefaultStyledDocument(sc);
        super.setDocument(doc);
        super.setForeground(Color.black);
        super.setFont(new Font("serif", Font.PLAIN, 12));

    }

    public void append(String text){
        try{
            doc.insertString(doc.getLength(), text, null);
        }catch(Exception e){
            e.printStackTrace();
        }
        
//        super.setCaretPosition(doc.getLength());
//        super.replaceSelection(text);
        this.paraCounter++;
        
    }

    public void append(String text,Color color){
        try{
            int length = doc.getLength();
            Style myStyle = sc.addStyle("Heading2", null);
            myStyle.addAttribute(StyleConstants.Foreground,color);
            myStyle.addAttribute(StyleConstants.FontSize, new Integer(12));
            myStyle.addAttribute(StyleConstants.FontFamily, Font.SERIF);
            doc.insertString(length, text,null );
            doc.setParagraphAttributes(length, paraCounter,myStyle
                    , true);
        }catch(Exception e){
            e.printStackTrace();
        }

         this.paraCounter++;
    }


    
}
