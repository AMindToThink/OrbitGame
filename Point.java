import java.awt.Color;
import java.io.*; 
import java.util.*;
import java.awt.Graphics;
class Point implements java.io.Serializable{
   public long creationTime;
   public double masterSpeed;
   public double refreshTime;
   public int x;
   public int y;
   public double trueX;
   public double trueY;
   private int  fromOriginX;
   private int  fromOriginY;
   public int previousX;
   public int previousY;
   
   public double vectorX;
   public double vectorY;
   
   private int ogDiameter;
   public int diameter;
   public Color color;
   
   
   public Point(int x, int  y, int diameter, Color color){
      refreshTime = CircleOrbitMouse.refreshTime;
      masterSpeed = CircleOrbitMouse.masterSpeed;
      creationTime = CircleOrbitMouse.myTimerThread.myClock.millis();
      this.x=x;
      this.y=y;
      trueX = x;
      trueY = y;
      previousX = x;
      previousY = y;
      
      fromOriginX = x;
      fromOriginY =y;
      this.diameter=diameter;
      ogDiameter = diameter;
      this.color = color;
      vectorX = 0;
      vectorY= 0;
   }
   public int getDiameter(){
      return(this.diameter);
   }
   public int getOgDiameter(){
      return(this.ogDiameter);
   }
   public void setDiameter(int newDiameter){
      diameter=newDiameter;
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
   
   
   public int getX(){
      return x;
   }
   public void changeXY(double addX, double addY){
      trueX += addX;
      trueY += addY;
      this.x = (int)(Math.round(trueX));
      this.y = (int)(Math.round(trueY));
   }
   public void changeFromOriginXY(double addX, double addY){
      fromOriginX+=addX;
      fromOriginY+=addY;
   }
   public int getFromOriginX(){
      return fromOriginX;
   }
   public int getFromOriginY(){
      return fromOriginY;
   }
   public void setXY(double theX, double theY){
      trueX = theX;
      trueY = theY;
      this.x =(int)(Math.round(trueX));
      this.y =(int)(Math.round(trueY));
   }
      
   public void setX(double x){
      trueX = x;
      this.x = (int)(Math.round(trueX));
   }
      
   public int getY(){
      return y;
   }
      
   public void setY(double y){
      trueY = y;
      this.y = (int)(Math.round(trueY));
   }
   //@Override
   public void drawIt(Graphics g){
      g.drawLine(100,100,500,500);
   }
   public void setVector(double vectorX, double vectorY ){
      this.vectorX=vectorX;
      this.vectorY=vectorY;
      
   }
   public void go(){
      changeXY(vectorX, vectorY);
   }
   public void changeVector(double vectorChangeX, double vectorChangeY){
      this.vectorX += vectorChangeX;
      this.vectorY += vectorChangeY;
   }
   public int getXVector(){
      return((int)this.vectorX);
      
   }
   public int getYVector(){
      return((int)(this.vectorY));
   }
   public Color getColor(){
      return color;
   }
   public void setItsColor(Color nColor){
      color = nColor;
   }
   public void increaseDiameter(double change){
      diameter = (int)(diameter*change);
   }

   public void updateVector(){
      
   }

   public String toString(){
      String str = "" + x + ", " + y;
      return str;     
   }
   public void drawACircle(Graphics g){
      g.setColor(color);
      g.fillOval(x, y, diameter, diameter);
   }
   public double rotateXVector(double xv, double yv, double angle){
      angle = fixAngle(angle);
      double hypotenuse = findDistance(0,0,xv, yv);
      
      
      
      //double adjacent = xv/hypotenuse;
      //double opposite = yv/hypotenuse;
      double ogAngle = findAngle(xv,yv);
      ogAngle = fixAngle(ogAngle);
      
      if(angle == Math.PI/2){
         
         return hypotenuse*Math.cos(Math.PI/2+ogAngle);
         
      }
      if(angle == 3*Math.PI/2){
         double rstuff = -hypotenuse*Math.cos(Math.PI/2+ogAngle);
         //System.out.println(rstuff/simpleEnemySpeed);
         return rstuff;
      }
      
      double newCos;
        
      //double newAngle = adjacent*Math.cos(angle) - opposite*Math.sin(angle);
      newCos = Math.cos(ogAngle+angle);
        
      return newCos*hypotenuse;      
   }
   public double rotateYVector(double xv, double yv, double angle){
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
      if(angle == 3*Math.PI/2){
         return hypotenuse*Math.sin(Math.PI/2-ogAngle);
      }
      //double newAngle = opposite*Math.cos(angle)+ adjacent*Math.sin(angle);
      newSin = Math.sin(ogAngle+angle);
      
      //System.out.println(newSin);
            
      return newSin*hypotenuse;
      
   }
   
   public double fixAngle(double angle){
      while(angle<0){
         angle+= 2*Math.PI;
      }
      angle %= 2*Math.PI;      
      return angle;
   }
   public double breakAngle(double angle){
      angle = fixAngle(angle); 
      if(angle>Math.PI){
         angle = angle-(2*Math.PI);
      }     
      return angle;
   }
   public int shorterWay(double angle1, double angle2){
      angle1 = fixAngle(angle1);
      angle2 = fixAngle(angle2); 
      if(angle1-angle2<Math.PI){
         return -1;
      }else{
         return 1;
      }     
      
   }
   public double findAngle(double vx, double vy){
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
   public static double findDistance(double x1, double y1, double x2, double y2){
      
      return Math.sqrt((((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))));
   }
}
