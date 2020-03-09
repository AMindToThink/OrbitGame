import javax.swing.*;

import java.awt.*;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.awt.event.MouseMotionListener;

import java.lang.Object; 
import java.lang.Math;

import java.io.*; 
import java.util.*;

import java.util.Collection;
import java.util.Map;

import java.util.ArrayList;
class Projectile extends JComponent{
   private String type; 
   private int x;
   private int y;
   
   private int targetX;
   private int targetY;
   
   private int simpleProjectileSpeed;
   
   private int vectorX;
   private int vectorY;
   
   private int diameter;
   private int damage;
   
   private int cooldownTimer=0;
   
   /*
   private int swoopStartX=x;
   private int swoopStartY=y;
   private int swoopStartPlayerX=x;
   private int swoopStartPlayerY=y;
   private boolean isSwooping=false;
   private boolean isReturnSwooping=false;
   private int howManySwoopUnits=0;
   */
   
   public Projectile(String type, int x, int y, int targetX, int targetY){
      this.x=x;
      this.y=y;
      this.type=type;
      this.targetX=targetX;
      this.targetY=targetY;
   }
   
   /*public static int getsX(){
      return x;
   }*/
   public void changeXY(int addX, int addY){
      this.x +=addX;
      this.y +=addY;
   }
      
   public void setX(int x){
      this.x = x;
   }
      
   /*public static int getsY(){
      return y;
   }*/
      
   public void setY(int y){
      this.y = y;
   }
   public int getDiameter(){
      return(this.diameter);
   }
   public void setVector(int vectorX, int vectorY ){
      this.vectorX=vectorX;
      this.vectorY=vectorY;
      changeXY(vectorX,vectorY);
         }
   public void changeVector(double vectorChangeX, double vectorChangeY){
      this.vectorX += vectorChangeX;
      this.vectorY += vectorChangeY;
   }
   public int getXVector(){
      return(this.vectorX);
   }
   public int getYVector(){
      return(this.vectorY);
   }

   public void updateVector(){
      
   }

   public String toString(){
      String str = "" + x + ", " + y;
      return str;     
   }
   public void drawCircle(int diameter){
   }
   public int shoot(int weaponLocationX, int weaponLocationY, int weaponDiameter, int playerDiameter) throws InterruptedException{
      double projectilePlayerDistance=findDistance(targetX, targetY, x , y );
      
      
      if(this.type=="Straight"){
         int simpleProjectileSpeed=5;
         diameter=10;
          setVector((int)(Math.round(xToStraightVector(this.x, this.y, targetX, targetY,simpleProjectileSpeed,"towards",false, 10))),
                      (int)(Math.round(yToStraightVector(this.x, this.y, targetX, targetY,simpleProjectileSpeed,"towards",false, 10))) );
         

         
        
        }

      
      
      
      try{
         if(findDistance(targetX, targetY, this.x,this.y)<=diameter/2+playerDiameter/2){
            //System.out.println("hit");
            
            return(damage);
            
         }else{
            return(0);
         }
         
      }catch(Exception e){
         return(0);
      }
      

   }
   
   public static double findDistance(double x1, double y1, double x2, double y2){
      
      return Math.sqrt((((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))));
   }
   
   public static double findYDistance( double y1, double y2){
      
      return (Math.abs(y2 - y1));
   }
   public static double findXDistance(double x1,  double x2){
      
      return ((Math.abs(x2 - x1)));
   }
   public static double findslope(double rise,  double run){
      
      return (Math.atan(Math.tan(rise/run)));
   }
   public static double xToStraightVector(int thingX, int thingY, int targetX, int targetY, double normalSpeed, String direction, boolean dissquared, int CIRCLE_DIAMETER){
   
      double twoPointDistance=findDistance(thingX, thingY, targetX, targetY);
      double adjacent=findXDistance(thingX, targetX);
   
      if(dissquared&&(twoPointDistance<CIRCLE_DIAMETER||adjacent==0)){
         return(0);
      }else{
         
         /*
         if(twoPointDistance<normalSpeed){
            normalSpeed=1;
         }
         */
         double angle = java.lang.Math.acos(adjacent/twoPointDistance);
            
            /*
            if(twoPointDistance>normalSpeed){
               //tooCloseSpeed=normalSpeed;
            }else{
               normalSpeed=1;
            }
            */
         double bigF;
         if(dissquared){
            bigF = 10000/(twoPointDistance*twoPointDistance);
         }else{
            bigF = 1;
         }
            
         double xDirect = normalSpeed*bigF*java.lang.Math.cos(angle);
            
         if(thingX>targetX){
            xDirect=-xDirect;
               //speed=0;
         }
         if(direction=="towards"){
            
         }else if(direction=="away"){
            xDirect=-xDirect;
         }
            /*if(!dissquared&&xDirect>twoPointDistance){
               xDirect=adjacent;
            }*/
            
           // xDirect=xDirect/(twoPointDistance*twoPointDistance);
            
             
         if(java.lang.Math.abs(adjacent)<java.lang.Math.abs(xDirect)){
            if(xDirect<0){
               xDirect=-adjacent;
            }else{
               xDirect=adjacent;
            }
            
            
         }
            
         return(xDirect);
      }
   
   }

   public static double yToStraightVector(int thingX, int thingY, int targetX, int targetY, double normalSpeed, String direction, boolean dissquared, int CIRCLE_DIAMETER){
   
      double twoPointDistance=findDistance(thingX, thingY, targetX, targetY);
      double opposite=findYDistance(thingY, targetY);
      if(dissquared&&(twoPointDistance<CIRCLE_DIAMETER||opposite==0)){
         return(0);
      }else{
         /*if(twoPointDistance<normalSpeed){
            normalSpeed=1;
         }
      */
      
         double angle = java.lang.Math.asin(opposite/twoPointDistance);
            /*
            
            if(twoPointDistance>normalSpeed){
               //tooCloseSpeed=normalSpeed;
            }else{
               normalSpeed=1;
            }
            */
         double bigF;
         if(dissquared){
            bigF = 10000/(twoPointDistance*twoPointDistance);
         }else{
            bigF = 1;
         }
      
           // double bigF = 10000/(twoPointDistance*twoPointDistance);
            
         double yDirect = normalSpeed*bigF*java.lang.Math.sin(angle);
            
         if(thingY>targetY){
            yDirect=-yDirect;
               //speed=0;
         }
         if(direction=="towards"){
            
         }else if(direction=="away"){
            yDirect=-yDirect;
         }
            
           // yDirect=yDirect/(twoPointDistance*twoPointDistance);
            
             //System.out.println(yDirect);
            
            /*if(!dissquared&&yDirect>twoPointDistance){
               yDirect=opposite;
            }*/
      
      if(java.lang.Math.abs(opposite)<java.lang.Math.abs(yDirect)){
            if(yDirect<0){
               yDirect=-opposite;
            }else{
               yDirect=opposite;
            }
            
            
         }

      
         return(yDirect);
      
      }
   }
   
   public void camera(boolean north, boolean east, boolean south, boolean west, int playerVectorX, int playerVectorY){
      if(north){
         changeXY(0,-playerVectorY);
      }
      if(east){
         changeXY(-playerVectorX,0);
      }
      if(south){
         changeXY(0,-playerVectorY);
      }
      if(west){
         changeXY(-playerVectorX,0);
      }
   }

}