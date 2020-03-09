import java.awt.Color;
import java.io.*; 
import java.util.*;
import java.awt.Graphics;
class Laser extends Point{
   public static Color purple = new Color(0x6a0dad);
   public static Color lightPurple = new Color(0xea1dfd);
   public static Color thanosPurple = new Color(0x6f3c89);
   public static Color indigo = new Color(0x4b0082);

   String type;
   Point shooterPoint;
   Point targetPoint;
   private int length = 10;
   private int damage;
   private int extraX;
   private int extraY; 
   private boolean isVisible = false;
   private boolean isDamaging = false;
   public boolean laserEnded = false;
   
   private double angle;
   private int fireTime;
   
   private int telegraphTime = 1000;
   private int damageTime = 200;
   private long timeStartTelegraph;
   private long timeStartDamage;
   
   public Laser(String type, Point shooterPoint, Point targetPoint, Color color, int diameter, int fireTime){
      super(shooterPoint.getX(), shooterPoint.getY(), diameter, color);
      this.type = type;
      this.shooterPoint = shooterPoint;
      this.targetPoint = targetPoint;
      this.fireTime = fireTime;
   }
   public Laser(String type, Point shooterPoint, Point targetPoint, Color color, int diameter, int fireTime, int extraX, int extraY){
      super(shooterPoint.getX(), shooterPoint.getY(), diameter, color);
      this.extraX = extraX;
      this.extraY = extraY;
      this.type = type;
      this.shooterPoint = shooterPoint;
      this.targetPoint = targetPoint;
      this.fireTime = fireTime;
   }
   //Angle Constructor
   public Laser(String type, Point shooterPoint, double angle, Color color, int diameter, int fireTime, int extraX, int extraY){
      super(shooterPoint.getX(), shooterPoint.getY(), diameter, color);
      this.extraX = extraX;
      this.extraY = extraY;
      this.type = type;
      this.shooterPoint = shooterPoint;
      this.targetPoint = shooterPoint;
      this.angle = angle;
      this.fireTime = fireTime;
   }
   public int getDamage(){
      return damage;
   }
   public void setAngle(double angle){
      this.angle = angle;
   }
   public double getAngle(){
      return angle;
   }
   public void setTelegraphTime(int t){
      telegraphTime = t;
   }
   public void lase(){
      
      if(creationTime != CircleOrbitMouse.myTimerThread.myClock.millis() && CircleOrbitMouse.myTimerThread.every(fireTime, creationTime)){
         isVisible = true;
         timeStartTelegraph = CircleOrbitMouse.myTimerThread.myClock.millis();
      }
      if(isVisible &&  CircleOrbitMouse.myTimerThread.myClock.millis()-timeStartTelegraph > telegraphTime){
         isDamaging = true;
         timeStartDamage = CircleOrbitMouse.myTimerThread.myClock.millis();
      }
      if(isDamaging && CircleOrbitMouse.myTimerThread.myClock.millis()-timeStartTelegraph-telegraphTime > damageTime){
         isVisible = false;
         isDamaging = false;
         laserEnded = true;
      }
   }
   public void draw(Graphics g){
      if(type.equals("Normal")){
         angle = helpful.findAngle(targetPoint.getX()-shooterPoint.getX(), (targetPoint.getY()-shooterPoint.getY()));
      }
      if(type.equals("Angle")){
         targetPoint = new Point(shooterPoint.getX()+(int)(length*CircleOrbitMouse.FRAME_WIDTH*Math.cos(angle)), shooterPoint.getY()+(int)(length*CircleOrbitMouse.FRAME_WIDTH*Math.sin(angle)), 1, null);
      }
      g.setColor(indigo);
      //g.fillOval(targetPoint.getX(), targetPoint.getY(), 100, 100);
      if(type.equals("Angle")){
         
      }
      if(type.equals("Normal") || type.equals("Angle")){
         if(isVisible && isDamaging){
            g.setColor(purple);
            g.fillOval(shooterPoint.getX()+extraX-diameter/2, shooterPoint.getY()+extraX-diameter/2, diameter, diameter);
            //helpful.drawThickLine(g, shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, (shooterPoint.getX()+length*(int)helpful.xConsVector(shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, targetPoint.getX(), targetPoint.getY(), length*CircleOrbitMouse.FRAME_WIDTH)), 
            //(shooterPoint.getY()+length*(int)helpful.yConsVector(shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, targetPoint.getX(), targetPoint.getY(), length*CircleOrbitMouse.FRAME_WIDTH)), diameter, color);
            helpful.drawThickLine(g, shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, (int)(shooterPoint.getX()+length*CircleOrbitMouse.FRAME_WIDTH*Math.cos(angle)), 
            (int)(shooterPoint.getY()+length*CircleOrbitMouse.FRAME_WIDTH*Math.sin(angle)), diameter, color);
   

         }else if(isVisible){
            g.setColor(thanosPurple);
            g.fillOval(shooterPoint.getX()+extraX-diameter/2, shooterPoint.getY()+extraX-diameter/2, diameter, diameter);
            //helpful.drawThickLine(g, shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, (shooterPoint.getX()+length*(int)helpful.xConsVector(shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, targetPoint.getX(), targetPoint.getY(), length*CircleOrbitMouse.FRAME_WIDTH)), 
            //(shooterPoint.getY()+length*(int)helpful.yConsVector(shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, targetPoint.getX(), targetPoint.getY(), length*CircleOrbitMouse.FRAME_WIDTH)), diameter/4, color);
            helpful.drawThickLine(g, shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, (int)(shooterPoint.getX()+length*CircleOrbitMouse.FRAME_WIDTH*Math.cos(angle)), 
            (int)(shooterPoint.getY()+length*CircleOrbitMouse.FRAME_WIDTH*Math.sin(angle)), diameter/4, color);
   

         }else{
            //System.out.println("here");
            g.setColor(indigo);
            helpful.drawThickLine(g, shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, (int)(shooterPoint.getX()+length*CircleOrbitMouse.FRAME_WIDTH*Math.cos(angle)), 
            (int)(shooterPoint.getY()+length*CircleOrbitMouse.FRAME_WIDTH*Math.sin(angle)), diameter/8, color);
   
   
         }
      }
   }
   public boolean laserCollision(Point p){
      
      return isDamaging && helpful.touchesLine(shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, targetPoint.getX(), targetPoint.getY(), p.getX(), p.getY(), diameter/2+p.getDiameter()/2);
   }
   public boolean laserCollision(int px, int py, int pDiameter){
      System.out.println(shooterPoint);
      System.out.println(targetPoint);
      return isDamaging && helpful.touchesLine(shooterPoint.getX()+extraX, shooterPoint.getY()+extraY, targetPoint.getX(), targetPoint.getY(), px, py, diameter/2+pDiameter/2);
   }
   public void setIsDamging(boolean b){
      isDamaging = b;
   }
   public void setIsVisible(boolean b){
      isVisible = b;
   }
   @Override
   public void camera(boolean north, boolean east, boolean south, boolean west, int playerVectorX, int playerVectorY){
      if(north){
         targetPoint.changeXY(0,-playerVectorY);
      }
      if(east){
         targetPoint.changeXY(-playerVectorX,0);
      }
      if(south){
         targetPoint.changeXY(0,-playerVectorY);
      }
      if(west){
         targetPoint.changeXY(-playerVectorX,0);
      }
   }
}