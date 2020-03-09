import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.*;

import java.awt.*;

import java.awt.geom.Line2D;
import java.util.ArrayList;
class helpful{
   public static boolean pointCollision(Point one, Point two){
      return  findDistance(one.getX(), one.getY(), two.getX(), two.getY()) <= one.getDiameter()/2+two.getDiameter()/2;

   }
   public static boolean pointTouchesTrail(Point p, Trail t){
      for(Point et: t.trailList){
         if(et.getDiameter()>=1 && pointCollision(et, p)){
            return true;
         }
      }
      return false;
   }
   public static double fixAngle(double angle){
      while(angle<0){
         angle += 2*Math.PI;
      }
      angle %= 2*Math.PI;      
      return angle;
   }
   public static double breakAngle(double angle){
      angle = fixAngle(angle); 
      if(angle>Math.PI){
         angle = angle-(2*Math.PI);
      }     
      return angle;
   }
   public static int shorterWay(double angle1, double angle2){
      angle1 = fixAngle(angle1);
      angle2 = fixAngle(angle2); 
      if(angle1-angle2<Math.PI){
         return -1;
      }else{
         return 1;
      }     
      
   }
   public static double findAngle(double vx, double vy){
      //This one works, and well with rotateXVector and rotateYVector
      double distance = findDistance(0,0,vx, vy);
     
      if(distance != 0){
         double angle = Math.acos(vx/distance);
         if(vy<0){
            angle = -angle;
         }
         
         angle = fixAngle(angle);
         return angle;
      }
      
      return 0;
   }
   
   public static double rotateAngleX(double ogVectorX, double ogVectorY, double addAngle){
      double distance = findDistance(0,0,ogVectorX, ogVectorY);
      if(distance != 0){
         double adjacent = ogVectorX/distance;
         double angle = Math.acos(adjacent);
         
         angle+=addAngle;
         
         adjacent = distance*Math.cos(angle);
         
         return adjacent;
      }else{
         return 0;
      }
   }
   
   public static double rotateXVector(double xv, double yv, double angle){
      angle = fixAngle(angle);
      double hypotenuse = findDistance(0,0,xv, yv);
      
      
      
      //double adjacent = xv/hypotenuse;
      //double opposite = yv/hypotenuse;
      double ogAngle = findAngle(xv,yv);
      ogAngle = fixAngle(ogAngle);
      
      if(angle == Math.PI/2){
         
         return hypotenuse*Math.cos(Math.PI/2+ogAngle);
         
      }
      double newCos;
        
      //double newAngle = adjacent*Math.cos(angle) - opposite*Math.sin(angle);
      newCos = Math.cos(ogAngle+angle);
      
      if(angle == 3*Math.PI/2){
         //double rstuff = -hypotenuse*Math.cos(-Math.PI/2+ogAngle);
         //System.out.println(rstuff/simpleEnemySpeed);
         //return rstuff;
         newCos = -newCos;
      }
      
      
        
      return newCos*hypotenuse;      
   }
   public static double rotateYVector(double xv, double yv, double angle){
      angle = fixAngle(angle);
      double hypotenuse = findDistance(0,0,xv, yv);
      //double adjacent = xv/hypotenuse;
      //double opposite = yv/hypotenuse;
      double newSin;
      double ogAngle = findAngle(xv,yv);
      ogAngle = fixAngle(ogAngle);
      
      if(angle == Math.PI/2){
         
         return hypotenuse*Math.sin(Math.PI/2-ogAngle);
         
      }
      newSin = Math.sin(ogAngle+angle);
      
      if(angle == 3*Math.PI/2){
         //return hypotenuse*Math.sin(-Math.PI/2+ogAngle);
         newSin = -newSin;
      }
      //double newAngle = opposite*Math.cos(angle)+ adjacent*Math.sin(angle);
      
      
      //System.out.println(newSin);
            
      return newSin*hypotenuse;
      
   }
   public static double findDistance(double x1, double y1, double x2, double y2){
      
      return Math.sqrt((((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))));
   }
   public static boolean pointInRect(int cornerX, int cornerY, int width, int height, int pointX, int pointY){
      if(pointX>=cornerX&&pointX<=cornerX+width&&pointY>=cornerY&&pointY<=cornerY+height){
         return true;
      }else{
         return false;
      }
        
   }
   /*public static boolean angleCollider(int x1, int y1, int x2, int y2, int pointX, int pointY, int distance){
      double dis1 = findDistance(pointX, pointY, x1, y1);
      double dis2 = findDistance(pointX, pointY, x2, y2);
      double realAngle = Math.acos()
      double shouldAngle
      
   }*/
   public static boolean touchesLine(int x1, int y1, int x2, int y2, int pointX, int pointY, int distance){
      
      if(x2 == x1){ 
         //System.out.println("x same"); 
         if(Math.abs(pointX-x2) < distance){
            if(pointY-distance > smaller(y1, y2) && pointY+distance < larger(y1, y2)){
               return true;
            }
         }
         return false;
      }
      if(y2 == y1){  
         if(Math.abs(pointY-y2) < distance){
            if(pointX-distance > smaller(x1, x2) && pointX+distance < larger(x1, x2)){
               return true;
            }
         }
         return false;
      }
      /*
      if(x2==x1){
         x2 += Double.MIN_VALUE;
      }
      if(y2==y1){
         y2 += Double.MIN_VALUE;
      }*/
      double testSlope = (double)(y2-y1)/(x2-x1);
      double perpTestSlope = -1/testSlope;
      double closestX = (((-testSlope*x1+y1-pointY)/perpTestSlope) + pointX)/(1+testSlope*testSlope);
      double closestY = testSlope*(closestX-x1)+y1;
         
      if((closestX > x2 && closestX > x1)){
         if(x1>x2){
            closestX = x1;
            closestY = y1;
         }
         if(x2>x1){
            closestX = x2;
            closestY = y2;
         }
         
      }else if((closestX < x2 && closestX < x1) ){
         if(x1<x2){
            closestX = x1;
            closestY = y1;
         }
         if(x2<x1){
            closestX = x2;
            closestY = y2;
         }
      }
         
      boolean touching = findDistance(pointX, pointY, closestX, closestY) <= distance;
         //boolean touching = onLine && 
      return touching;
   }
   public static double larger(double x, double y){
      if(x>y){
         return x;
      }else{
         return y;
      }
   }
   public static double smaller(double x, double y){
      if(x<y){
         return x;
      }else{
         return y;
      }
   }
   public static boolean dividesBySeven(int hmm){
      return hmm/7 == (double)hmm/7;
   }
   public static BufferedImage resize(BufferedImage img, int height, int width) {
      Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = resized.createGraphics();
      g2d.drawImage(tmp, 0, 0, null);
      g2d.dispose();
      return resized;
   }
   public static double xConsVector(int thingX, int thingY, int targetX, int targetY, double normalSpeed){
   
      double twoPointDistance=findDistance(thingX, thingY, targetX, targetY);
      if(twoPointDistance == 0){
         return 0;
      }
      double adjacent=findXDistance(thingX, targetX);
   
      double angle = java.lang.Math.acos(adjacent/twoPointDistance);
            
            /*
            if(twoPointDistance>normalSpeed){
               //tooCloseSpeed=normalSpeed;
            }else{
               normalSpeed=1;
            }
            */
            
      double xDirect = normalSpeed*java.lang.Math.cos(angle);
            
      if(thingX>targetX){
         xDirect=-xDirect;
               //speed=0;
      }
      if(java.lang.Math.abs(adjacent)<java.lang.Math.abs(xDirect)){
         if(xDirect<0){
            xDirect=-adjacent;
         }else{
            xDirect=adjacent;
         }
            
            
      }
            
           // xDirect=xDirect/(twoPointDistance*twoPointDistance);
            
             
            
            
      return((xDirect));
   
   
   }

   public static double yConsVector(int thingX, int thingY, int targetX, int targetY, double normalSpeed){
   
      double twoPointDistance=findDistance(thingX, thingY, targetX, targetY);
      if(twoPointDistance == 0){
         return 0;
      }
      double opposite=findYDistance(thingY, targetY);
   
      double angle = java.lang.Math.asin(opposite/twoPointDistance);
            /*
            
            if(twoPointDistance>normalSpeed){
               //tooCloseSpeed=normalSpeed;
            }else{
               normalSpeed=1;
            }
            */
      double yDirect = normalSpeed*java.lang.Math.sin(angle);
      if(thingY>targetY){
         yDirect=-yDirect;
               //speed=0;
      }
            
           // yDirect=yDirect/(twoPointDistance*twoPointDistance);
            
      if(java.lang.Math.abs(opposite)<java.lang.Math.abs(yDirect)){
         if(yDirect<0){
            yDirect=-opposite;
         }else{
            yDirect=opposite;
         }
            
            
      }   
            
            
      return((yDirect));
   
   
   }
   public static double findYDistance( double y1, double y2){
      
      return (Math.abs(y2 - y1));
   }
   public static double findXDistance(double x1,  double x2){
      
      return ((Math.abs(x2 - x1)));
   }
   public static boolean every(int time){
      return CircleOrbitMouse.myClock.millis()%time == 0;
      
   }
   public static String nextInCycle(ArrayList<String> lst, String ogObject){
      int index = lst.indexOf(ogObject);
      if(index < lst.size()-1){
         return lst.get(index+1);
      }else{
         return lst.get(0);
      }
   }
   public static void drawThickLine(
     Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c) {
     // The thick line is in fact a filled polygon
      g.setColor(c);
      int dX = x2 - x1;
      int dY = y2 - y1;
     // line length
      double lineLength = Math.sqrt(dX * dX + dY * dY);
   
      double scale = (double)(thickness) / (2 * lineLength);
   
     // The x,y increments from an endpoint needed to create a rectangle...
      double ddx = -scale * (double)dY;
      double ddy = scale * (double)dX;
      ddx += (ddx > 0) ? 0.5 : -0.5;
      ddy += (ddy > 0) ? 0.5 : -0.5;
      int dx = (int)ddx;
      int dy = (int)ddy;
   
     // Now we can compute the corner points...
      int xPoints[] = new int[4];
      int yPoints[] = new int[4];
   
      xPoints[0] = x1 + dx; yPoints[0] = y1 + dy;
      xPoints[1] = x1 - dx; yPoints[1] = y1 - dy;
      xPoints[2] = x2 - dx; yPoints[2] = y2 - dy;
      xPoints[3] = x2 + dx; yPoints[3] = y2 + dy;
   
      g.fillPolygon(xPoints, yPoints, 4);
   }

}