import java.util.ArrayList;
import java.awt.Color;
import java.io.*; 
import java.util.*;
import java.awt.Graphics;



class ParticlePoof extends Point{

   ArrayList<Point> poofList= new ArrayList<Point>();
   double refreshTime;
   int poofAmount;
   int transparency = 255;
   
   public ParticlePoof(int x, int  y, int diameter, Color color, double refreshTime){
      super(x, y, diameter, color);
      this.refreshTime = refreshTime;
      
      poofAmount = 10;
      double poofSpeedRange = 100;
      for(int i = 0; i < poofAmount; i++){
         
         Point myPoof = new Point(x, y, diameter/2, color);
         double poofAngle = Math.random()*2*Math.PI;
         double poofSpeed = Math.random()*poofSpeedRange+5;
         myPoof.setVector(poofSpeed,0);
         myPoof.setVector(rotateXVector(myPoof.getXVector(), myPoof.getYVector(), poofAngle),rotateYVector(myPoof.getXVector(), myPoof.getYVector(), poofAngle));
         poofList.add(myPoof);
      }
      
   }
   
   public void happen(){
      for(int i = 0; i < poofList.size(); i++){
         
         
         if(transparency<=0){
            poofList.remove(i);
         }else{
            poofList.get(i).go();
            transparency -= 2 ;
            if(transparency>=0){
               //System.out.println(poofList.get(i).getColor().getTransparency());
            Color poofColor = new Color(poofList.get(i).getColor().getRed(),poofList.get(i).getColor().getGreen(),poofList.get(i).getColor().getBlue(),transparency );
            
            this.setItsColor(poofColor);
            }else{
               poofList.remove(i);
            }
            
         }
         
         
      }
   }
   
   public int getPoofNum(){
      return poofList.size();
   }
   
   public Point getPoof(int i){
      return poofList.get(i);
   }
   
   public int getTransparency(){
      return transparency;
   }
}