/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.model;

/**
 *
 * @author albertwang
 */
import java.awt.*;
//import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;




public  class  IShape implements Serializable

{
            private   Color  StokeColor = null;

            private   Shape    ShapeModel = null;
            private   String   text = null;
            private   byte[] newImage = null;
            private   int drawControl;
//            private   int stroke = 1;
        public IShape(Color color, Shape shape, int drawControl){
//          this.stroke = stroke;
          this.StokeColor = color;
  
            this.ShapeModel = shape;
            this.drawControl = drawControl;


 }

        public IShape( BufferedImage newImage,int drawControl){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try{
                
                ImageIO.write(newImage, "png", baos);
            }catch(IOException ioe){
                System.out.println("Parse image io exception.");
               
            }
            
            this.newImage = baos.toByteArray();
            this.drawControl = drawControl;

        }

         public  void  ShapeNeedToDraw(IShape ShapeDataBase) {                                                                                                      // whenever a new shape is drawn, this method will

         }




    public Shape getShapeModel() {
        return ShapeModel;
    }

    public void setShapeModel(Shape ShapeModel) {
        this.ShapeModel = ShapeModel;
    }


    public Color getStokeColor() {
        return StokeColor;
    }

    public void setStokeColor(Color StokeColor) {
        this.StokeColor = StokeColor;
    }
    public int getDrawControl()   {
        return drawControl;
    }
    
    public void setDrawControl(int i) {
        this.drawControl = i;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)    {
        this.text = text;
    }
 
    
    public BufferedImage getImage(){
        ByteArrayInputStream baos = new ByteArrayInputStream(this.newImage);
            try{

                return ImageIO.read(baos);
            }catch(IOException ioe){
                System.out.println("Parse image io exception.");

            }

            return null;
    }
//
//    public int getStoke() {
//        return stroke;
//    }
//
//    public void setStoke(int stoke) {
//        this.stroke = stoke;
//    }



}
