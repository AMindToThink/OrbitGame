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
class Weapon extends Point{
   private String type;
   private Point target; 
   private boolean initializing = true;
   /*private int x;
   private int y;
   private double trueX;
   private double trueY;
   private String targetType;
   private Point targetPoint;
   private Enemy targetEnemy;
   private Weapon targetWeapon;
   */
   
   private double masterSpeed;
   public static double weaponBaseSpeed;
   private double gravity;
   private boolean doesCamera = true;
   private double weaponPlayerDistance;
   private static double playerDiameter;
   //Boomerang
   public int returnStartX;
   public int returnStartY;
   public boolean weaponReturnStart;
   public boolean boomInit = false;
   //private Color weaponColor;
   
   boolean firstTime = true;
   
   /*
   private int vectorX;
   private int vectorY;
   
   private double velocityX;
   private double velocityY;
   */
   private int wise = -1;
   
   //private int diameter = 30;
   private int damage;
   
   private double previousFocusX;
   private double previousFocusY;
   
   
   private static Color stationaryColor = new Color(0x8b0000);
   private static Color swoopColor = new Color(0xff3333);
   private static Color simpleColor = new Color(0xff0000);
   private static Color simpleRangedColor = Color.MAGENTA;
   private static Color limpColor = new Color(0x550000); 
   private static Color booColor = new Color(0xffd1dc);
   private static Color rBooColor = new Color(0xffa1ac); 
   private static Color transparent = new Color(0,0,0,0);
   
   private Color teal = new Color(0x0099af);
   //private Color teal = new Color(0,153, 175, 100);
   
   Color transTeal = new Color(0,153, 175, 100);
         
   
   
   private int cooldownTimer=0;
   
      
   private int rememberPlayerX=0;
   private int rememberPlayerY=0;
   
   public boolean weaponEverReturns = true;
   private static boolean weaponReturn=false;
   private static int weaponReturnStartX;
   private static int weaponReturnStartY;
   boolean hasReturned = false;
   
   //For "Relative"
   double hoverDis = 100;
   double hoverAngle = 0;
   
   //For Trail
   
   
   boolean isTrail = false;
   ArrayList<Point> trailList= new ArrayList<Point>();
   
   int trailLength = 20;
   int trailDiameter = 10;
   int whichTrail = 0;
   
   
   //Trail
   int weaponTrailDiameter = 10;
   int weaponTrailLength = 700;
   boolean hasTrail = true;
   Trail weaponTrail;
   
   //Bounce
   int bounced = 0;
   private double bounceDis = 20;
   
   public Weapon(String type, int x, int y, Point target, int diameter, double masterSpeed, double gravity, Color setColor){
      super(x, y, diameter, setColor);
      weaponTrailDiameter = diameter;
      /*this.x=x;
      this.y=y;
      this.trueX = (this.x);
      this.trueY = (this.y);
      */
      this.target = target;
      this.type=type;
      this.masterSpeed=CircleOrbitMouse.masterSpeed;
      this.weaponBaseSpeed = this.masterSpeed*0.01*gravity;
      this.gravity=gravity;
      //this.weaponColor=setColor;
      
     /*
      for(int i=0; i<trailLength; i++){
         Point trailCircle = new Point(x, y, trailDiameter, Color.RED);
         trailList.add(trailCircle);
      }
      */
      weaponTrailLength = (int)(weaponTrailLength/masterSpeed);
      
      weaponTrail = new Trail(x, y, weaponTrailDiameter, weaponTrailLength );
   }
   /*
   public Weapon(String type, int x, int y, Point targetPoint, double masterSpeed, double gravity, Color setColor){
      targetType = "Point";
      this.targetPoint = targetPoint;
      this.x=x;
      this.y=y;
      this.trueX = (this.x);
      this.trueY = (this.y);
      this.type=type;
      this.masterSpeed=masterSpeed;
      this.weaponBaseSpeed = masterSpeed*0.01*gravity;
      this.gravity=gravity;
      this.weaponColor=setColor;
      
     
      for(int i=0; i<trailLength; i++){
         Point trailCircle = new Point(this.x,this.y,trailDiameter, Color.RED);
         trailList.add(trailCircle);
      }
      
      weaponTrailLength = (int)(weaponTrailLength/masterSpeed);
      weaponTrail = new Trail(this.x, this.y, weaponTrailDiameter, weaponTrailLength );
   }
   public Weapon(String type, int x, int y, Weapon targetWeapon, double masterSpeed, double gravity, Color setColor){
      targetType = "Weapon";
      this.targetWeapon = targetWeapon;
      this.x=x;
      this.y=y;
      this.trueX = (this.x);
      this.trueY = (this.y);
      this.type=type;
      this.masterSpeed=masterSpeed;
      this.weaponBaseSpeed = masterSpeed*0.01*gravity;
      this.gravity=gravity;
      this.weaponColor=setColor;
      
     
      for(int i=0; i<trailLength; i++){
         Point trailCircle = new Point(this.x,this.y,trailDiameter, Color.RED);
         trailList.add(trailCircle);
      }
      
      weaponTrailLength = (int)(weaponTrailLength/masterSpeed);
      weaponTrail = new Trail(this.x, this.y, weaponTrailDiameter, weaponTrailLength );
   }
   public Weapon(String type, int x, int y, Enemy targetEnemy, double masterSpeed, double gravity, Color setColor){
      targetType = "Enemy";
      this.targetEnemy = targetEnemy;
      this.x=x;
      this.y=y;
      this.trueX = (this.x);
      this.trueY = (this.y);
      this.type=type;
      this.masterSpeed=masterSpeed;
      this.weaponBaseSpeed = masterSpeed*0.01*gravity;
      this.gravity=gravity;
      this.weaponColor=setColor;
      
     
      for(int i=0; i<trailLength; i++){
         Point trailCircle = new Point(this.x,this.y,trailDiameter, Color.RED);
         trailList.add(trailCircle);
      }
      
      weaponTrailLength = (int)(weaponTrailLength/masterSpeed);
      weaponTrail = new Trail(this.x, this.y, weaponTrailDiameter, weaponTrailLength );
   }
   */
   public boolean getHasTrail(){
      return hasTrail;
   }
   
   public Trail getWeaponTrail(){
      return weaponTrail;
   }
   
   public String getType(){
      return type;
   }
   
   public void setWise(int nWise){
      wise = nWise;
   }
   public int getWise(){
      return wise;
   }
   
   /*public int getX(){
      return x;
   }*/
   /*public void changeXY(double addX, double addY){
      this.trueX +=addX;
      this.trueY +=addY;
      previousFocusX += addX;
      previousFocusY += addY;
      
      this.x = (int)(Math.round(trueX));
      this.y = (int)(Math.round(trueY));
      
   }
      
   public void setX(int x){
      this.x = x;
   }
      
   public int getY(){
      return y;
   }
      
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
   public Color getColor(){
      return weaponColor;
   }*/
   public Point getTarget(){
      return target;
      
   }
   
/*
   public String toString(){
      String str = "" + x + ", " + y;
      return str;     
   }*/
   
   public void drawCircle(int diameter){
   }
   public void shiftTarget(int xShift, int yShift){
      //targetX+=xShift;
     // targetY+=yShift;
   }
   /*public int getTargetX(){
      return targetX;
   }*/
   /*public int getTargetY(){
      return targetY;
   }*/
   /*public boolean getSwoop(){
      return swoop;
   }*/
   
   public int getRememberX(){
      return rememberPlayerX;
   }
   public int getRememberY(){
      return rememberPlayerY;
   }
   public void setRememberX(int change){
      rememberPlayerX+=change;
   }
   public void setRememberY(int change){
      rememberPlayerY+=change;
   }
   
   public int getTrailListXAt(int position){
      return trailList.get(position).getX();
   }
   public int getTrailListYAt(int position){
      return trailList.get(position).getY();
   }
   public void setTrailListXY(int index, int positionX, int positionY){
      trailList.get(index).setXY(positionX,positionY);
   }
   public void moveTrailListXY(int index, int positionX, int positionY){
      trailList.get(index).changeXY(positionX,positionY);
   }
   
   
   public int getTrailListSize(){
      return trailList.size();
   }
   public int getTrailDiameter(int index){
      return trailList.get(index).getDiameter();
   }
   
   public boolean getIsTrail(){
      return isTrail;
   }
   public void makeTrail(boolean trail){
      isTrail = trail;
   }
   
   
   public void orbit(int focusX,int focusY, int playerDiameter){
      if(firstTime){
         firstTime = false;
         previousFocusX = focusX;
         previousFocusY = focusY;
         
      }
      
      this.playerDiameter = playerDiameter;
      weaponPlayerDistance=findDistance(focusX, focusY, x , y );
      
      if(this.type.equals("Planet")){
         
         
         
         boolean isForcePropDisSqWeaponToMouse = true;
         //diameter=20;
         setVector(vectorX+xToStraightVector(x, y, focusX, focusY, weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter),vectorY+yToStraightVector(x, y, focusX, focusY, weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
         go();
         /*velocityX = velocityX+(xToStraightVector(x, y, focusX, focusY, weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
         velocityY = velocityY+(yToStraightVector(x, y, focusX, focusY, weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
         
         trueX = velocityX + trueX;
         trueY = velocityY + trueY;
         
         x= (int)(Math.round(trueX));
       
         y= (int)(Math.round(trueY));*/
      }
      if(this.type.equals("Screensaver")){
         if(initializing){
            doesCamera = false;
            
            double initialAngle = Math.random()*2*Math.PI;
         
            setVector(weaponBaseSpeed*20,0);
            setVector(rotateXVector(getXVector(), getYVector(), initialAngle), rotateYVector(getXVector(), getYVector(), initialAngle));
            
            initializing = false;
         }
         
         if(x-diameter/2 <= 0){
            setVector(Math.abs(getXVector()), getYVector());
         }
         if(x+diameter/2 >= CircleOrbitMouse.FRAME_WIDTH){
            setVector(-Math.abs(getXVector()), getYVector());
         }
          
         if(y-diameter/2 <= 0){
            setVector(getXVector(), Math.abs(getYVector()));
         }
         if(y+diameter/2 >= CircleOrbitMouse.FRAME_HEIGHT){
            setVector(getXVector(), -Math.abs(getYVector()));
         }
         go();
         
      }
      if(this.type.equals("Bounce")){
         
         
         
         boolean isForcePropDisSqWeaponToMouse = true;
         //diameter=20;
         
         
         double strongForce = 10;
         double collisionSpeedX = focusX-previousFocusX;
         double collisionSpeedY = focusY-previousFocusX;
         //double colSpeed = findDistance(x+velocityX,y+velocityY,focusX,focusY);
         //double angleMultiplier = Math.cos(-helpful.findAngle(velocityX, velocityY)+helpful.findAngle(focusX-x, focusY-y));
         if(weaponPlayerDistance <= playerDiameter/2+diameter/2+bounceDis /*&& findDistance(x+velocityX,y+velocityY,focusX,focusY)<weaponPlayerDistance*/){
            //setVector(-xToStraightVector(x, y, focusX, focusY, strongForce*weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter),-yToStraightVector(x, y, focusX, focusY, strongForce*weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
            setVector(getXVector()-xToStraightVector(x, y, focusX, focusY, strongForce*weaponBaseSpeed, "towards",false, diameter), getYVector()-yToStraightVector(x, y, focusX, focusY, strongForce*weaponBaseSpeed, "towards",false, diameter));
         
           // velocityX = velocityX-(xToStraightVector(x, y, focusX, focusY, strongForce*weaponBaseSpeed, "towards",false, diameter));
            //velocityY = velocityY-(yToStraightVector(x, y, focusX, focusY, strongForce*weaponBaseSpeed, "towards",false, diameter));
            
         }else{
            double bounceSpeed = weaponBaseSpeed;
         //velocityX = velocityX+(xToStraightVector(x, y, focusX, focusY, bounceSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
         //velocityY = velocityY+(yToStraightVector(x, y, focusX, focusY, bounceSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
            //setVector(getXVector()+xToStraightVector(x, y, focusX, focusY, bounceSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter), getYVector()+yToStraightVector(x, y, focusX, focusY, bounceSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
            setVector(vectorX+xToStraightVector(x, y, focusX, focusY, weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter),vectorY+yToStraightVector(x, y, focusX, focusY, weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
         
         }
         go();
         /*trueX = velocityX + trueX;
         trueY = velocityY + trueY;
         
         x= (int)(Math.round(trueX));
         y= (int)(Math.round(trueY));*/
      }
     
      if(this.type.equals("ConstantPull")){
         
         double conSpeed = weaponBaseSpeed*5;
         
         //boolean isForcePropDisSqWeaponToMouse = false;
         //diameter=20;
         setVector(vectorX+xConsVector(x, y, focusX, focusY, conSpeed), vectorY + yConsVector(x, y, focusX, focusY, conSpeed));
         go();
         /*velocityX = velocityX+(xConsVector(x, y, focusX, focusY, conSpeed));
         velocityY = velocityY+(yConsVector(x, y, focusX, focusY, conSpeed));
         
         trueX = velocityX + trueX;
         trueY = velocityY + trueY;
         
         x= (int)(Math.round(trueX));
         y= (int)(Math.round(trueY));*/
      }
      /*if(this.type == "Malcolm"){
         double malcolmSpeed = weaponBaseSpeed*20;
         
      }*/
      if(this.type.equals("Malcolm")){
         weaponEverReturns = false;
         //doesCamera = true;
         double tanSpeed = weaponBaseSpeed*20;
         double tempVX = tanSpeed;
         double tempVY = 0;
         double angleToP = helpful.findAngle(x-focusX, y-focusY);
         //boolean isForcePropDisSqWeaponToMouse = false;
         //diameter=20;
         //System.out.println(xConsVector(x, y, focusX, focusY, tanSpeed));
         //velocityX = helpful.rotateXVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2);
         //velocityY = helpful.rotateYVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2);
         ////setVector(helpful.rotateXVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2), helpful.rotateYVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2));
         //System.out.println(focusX+rotateXVector(x-previousFocusX, y-previousFocusY, Math.PI/180));
         setXY(focusX+rotateXVector(x-previousFocusX, y-previousFocusY, weaponBaseSpeed*Math.PI/20), focusY+rotateYVector(x-previousFocusX, y-previousFocusY, weaponBaseSpeed*Math.PI/20));
         
         
         int okMinDis = 40;
         int okMaxDis = 70;
         /*
         if(weaponPlayerDistance < okMinDis){
            setVector(getXVector()-xConsVector(x, y, focusX, focusY, 5), getYVector()-yConsVector(x, y, focusX, focusY, 5));
            //velocityX -= xConsVector(x, y, focusX, focusY, 1);
            //velocityY -= yConsVector(x, y, focusX, focusY, 1);
         } else if(weaponPlayerDistance > okMaxDis){
            setVector(getXVector()+xConsVector(x, y, focusX, focusY, 5), getYVector()+yConsVector(x, y, focusX, focusY, 5));
           // velocityX += xConsVector(x, y, focusX, focusY, 1);
           // velocityY += yConsVector(x, y, focusX, focusY, 1);
         }
         */
         ////go();
         /*
         trueX = velocityX + trueX-previousFocusX+focusX;
         trueY = velocityY + trueY-previousFocusY+focusY;
         
         x= (int)(Math.round(trueX));
         y= (int)(Math.round(trueY));
         */
      }
      if(this.type.equals("Bugcolm")){
         //doesCamera = true;
         double tanSpeed = weaponBaseSpeed*20;
         double tempVX = tanSpeed;
         double tempVY = 0;
         double angleToP = helpful.findAngle(x-focusX, y-focusY);
         //boolean isForcePropDisSqWeaponToMouse = false;
         //diameter=20;
         //System.out.println(xConsVector(x, y, focusX, focusY, tanSpeed));
         //velocityX = helpful.rotateXVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2);
         //velocityY = helpful.rotateYVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2);
         ////setVector(helpful.rotateXVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2), helpful.rotateYVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2));
         //System.out.println(focusX+rotateXVector(x-previousFocusX, y-previousFocusY, Math.PI/180));
         setXY(previousFocusX+rotateXVector(x-previousFocusX, y-previousFocusY, weaponBaseSpeed*Math.PI/20), previousFocusY+rotateYVector(x-previousFocusX, y-previousFocusY, weaponBaseSpeed*Math.PI/20));
         
         
         int okMinDis = 40;
         int okMaxDis = 70;
         /*
         if(weaponPlayerDistance < okMinDis){
            setVector(getXVector()-xConsVector(x, y, focusX, focusY, 5), getYVector()-yConsVector(x, y, focusX, focusY, 5));
            //velocityX -= xConsVector(x, y, focusX, focusY, 1);
            //velocityY -= yConsVector(x, y, focusX, focusY, 1);
         } else if(weaponPlayerDistance > okMaxDis){
            setVector(getXVector()+xConsVector(x, y, focusX, focusY, 5), getYVector()+yConsVector(x, y, focusX, focusY, 5));
           // velocityX += xConsVector(x, y, focusX, focusY, 1);
           // velocityY += yConsVector(x, y, focusX, focusY, 1);
         }
         */
         ////go();
         /*
         trueX = velocityX + trueX-previousFocusX+focusX;
         trueY = velocityY + trueY-previousFocusY+focusY;
         
         x= (int)(Math.round(trueX));
         y= (int)(Math.round(trueY));
         */
      }
      
      if(this.type.equals("Tangent")){
         
         double tanSpeed = weaponBaseSpeed*30;
         double tempVX = tanSpeed;
         double tempVY = 0;
         double angleToP = helpful.findAngle(x-focusX, y-focusY);
         //boolean isForcePropDisSqWeaponToMouse = false;
         //diameter=20;
        // System.out.println(xConsVector(x, y, focusX, focusY, tanSpeed));
         //velocityX = helpful.rotateXVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2);
         //velocityY = helpful.rotateYVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2);
         setVector(helpful.rotateXVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2), helpful.rotateYVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), wise*Math.PI/2));
         go();
         /*trueX = velocityX + trueX;
         trueY = velocityY + trueY;
         
         x= (int)(Math.round(trueX));
         y= (int)(Math.round(trueY));*/
      }
      
      if(this.type.equals("CTowards")){
         
         double tanSpeed = weaponBaseSpeed*20;
         
         setVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed));
         go();
        
      }
      if(this.type.equals("CAway")){
         
         double tanSpeed = weaponBaseSpeed*20;
         
         setVector(-xConsVector(x, y, focusX, focusY, tanSpeed), -yConsVector(x, y, focusX, focusY, tanSpeed));
         go();
        
      }
      /*
      if(this.type=="CAway"){
         
         double tanSpeed = weaponBaseSpeed*20;
         double tempVX = tanSpeed;
         double tempVY = 0;
         double angleToP = helpful.findAngle(x-focusX, y-focusY);
         //boolean isForcePropDisSqWeaponToMouse = false;
         //diameter=20;
         System.out.println(xConsVector(x, y, focusX, focusY, tanSpeed));
         velocityX = helpful.rotateXVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), Math.PI);
         velocityY = helpful.rotateYVector(xConsVector(x, y, focusX, focusY, tanSpeed), yConsVector(x, y, focusX, focusY, tanSpeed), Math.PI);
         
         trueX = velocityX + trueX;
         trueY = velocityY + trueY;
         
         x= (int)(Math.round(trueX));
         y= (int)(Math.round(trueY));
      }
      if(this.type == "Relative"){
         double hoverX = helpful.rotateXVector(hoverDis, 0, hoverAngle);
         double hoverY = helpful.rotateYVector(hoverDis, 0, hoverAngle);
         trueX = focusX + hoverX;
         trueY = focusY + hoverY;
         
         x = (int)trueX;
         y = (int)trueY;
      }
      if(this.type=="IPlanet"){
         
         
         double iPlanetSpeed = weaponBaseSpeed/2;
         boolean isForcePropDisSqWeaponToMouse = true;
         //diameter=20;
         
         velocityX = velocityX-(xToStraightVector(x, y, focusX, focusY, iPlanetSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
         velocityY = velocityY-(yToStraightVector(x, y, focusX, focusY, iPlanetSpeed, "towards",isForcePropDisSqWeaponToMouse, diameter));
         
         trueX = velocityX + trueX;
         trueY = velocityY + trueY;
         
         x= (int)(Math.round(trueX));
         y= (int)(Math.round(trueY));
      }
      */
      if(isTrail){
         if(whichTrail+1<trailList.size()){
            whichTrail++;
         }else{
            whichTrail=0;
         }
         trailList.get(whichTrail).setXY(x,y);
         //trailList.get(whichTrail).setDiameter(trailList.get(whichTrail).getDiameter()+whichTrail/2);
      
      }
      if(hasTrail){
         weaponTrail.follow(x,y);
      }
      
      previousFocusX = focusX;
      previousFocusY = focusY;
   
   }
   public void startBoom(){
      returnStartX=x;
      returnStartY=y;
      weaponReturnStart=true;
      boomInit = true;
   }
   public int getReturnStartX(){
      return returnStartX;
   }
   public int getReturnStartY(){
      return returnStartY;
   }
   public boolean boomerang(int playerLocationX, int playerLocationY, boolean weaponReturn){
      
      if(weaponReturn && boomInit && weaponReturnStart){
         
         if(type.equals("Planet") || type.equals("ConstantPull")){
            setVector(0,0);
            //velocityX = 0;
            //velocityY = 0;
            double returnSpeed = 1.5*masterSpeed;
         
            if(weaponReturn&&(x!=playerLocationX||y!=playerLocationY)){
            
               changeXY(xToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10), yToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10) );
                  
            //  x=x+(int)(Math.round(xToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10)));
            // y=y+(int)(Math.round(yToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10)));
               
               /*if(x==playerLocationX){
                  setVector(0, vectorY);
                  
                  //velocityX=0;
               }
                  
               if(y==playerLocationY){
                  //velocityY=0;
                  setVector(vectorX, 0);
               }*/
               
               if(x==playerLocationX&&y==playerLocationY){
                  hasReturned = true;
                  boomInit = false;
                  return false;
               }else{
                  hasReturned = false;
                  return true;
               }
            }else{
               hasReturned = false;
               return false;
            }
         }else if(type.equals("Screensaver") || type.equals("Tangent") || type.equals("CTowards") || type.equals("CAway") ||  type.equals("Bugcolm") || type.equals("Mine")){
            //setVector(0,0);
            //velocityX = 0;
            //velocityY = 0;
            double returnSpeed = 1.5*masterSpeed;
         
            if(weaponReturn&&(x!=playerLocationX||y!=playerLocationY)){
            
               changeXY(xToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards", false, 10), yToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10) );
                  
            //  x=x+(int)(Math.round(xToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10)));
            // y=y+(int)(Math.round(yToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10)));
               
               /*if(x==playerLocationX){
                  setVector(0, vectorY);
                  
                  //velocityX=0;
               }
                  
               if(y==playerLocationY){
                  //velocityY=0;
                  setVector(vectorX, 0);
               }*/
               
               if(x==playerLocationX&&y==playerLocationY){
                  hasReturned = true;
                  boomInit = false;
                  return false;
               }else{
                  hasReturned = false;
                  return true;
               }
            }else{
               hasReturned = false;
               return false;
            }
         }else if(type.equals("Bounce")){
            setVector(0,0);
            //velocityX = 0;
            //velocityY = 0;
            double returnSpeed = 1.5*masterSpeed;
         
            if(weaponReturn&&(weaponPlayerDistance>bounceDis+diameter/2+playerDiameter/2)){
            
               changeXY(xToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10), yToStraightVector(x, y, playerLocationX, playerLocationY,returnSpeed,"towards",false, 10) );
                  
            
               
               if(weaponPlayerDistance<=bounceDis+diameter/2+playerDiameter/2){
                  hasReturned = true;
                  boomInit = false;
                  return false;
               }else{
                  hasReturned = false;
                  return true;
               }
            }else{
               hasReturned = false;
               return false;
            }
         
         }else{
            hasReturned = true;
            return false;
         }
      } else{
         hasReturned = false;
         return false;
      }
   }
   public boolean getHasReturned(){
      return hasReturned;
   }
   public static int getFirstProjectileX(){
      //int bulletX=bullet.getsX();
      return 1;//bulletX;
      
   }
   public static int getFirstProjectileY(){
      
      //int bulletY = bullet.getsY();
      return 1;
      
   }
   public static void setProjectileX(Projectile setItsX, int setterX ){
      setItsX.setX(setterX);
      
   }
   public static void setProjectileY(Projectile setItsY, int setterY){
      
      setItsY.setY(setterY);
      
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
         if(direction.equals("towards")){
            
         }else if(direction.equals("away")){
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
         if(direction.equals("towards")){
            
         }else if(direction.equals("away")){
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
   
   public void camera(boolean north, boolean east, boolean south, boolean west, int playerVectorX, int playerVectorY){
      if(doesCamera){
         if(north){
            previousFocusY -= playerVectorY;
            changeXY(0,-playerVectorY);
         
            if(hasTrail){
               for(int k=0; k<getWeaponTrail().getTrailListSize(); k++){
                  getWeaponTrail().moveTrailListXY(k,0,-playerVectorY);
                  
               }
            }
         }
         if(east){
            previousFocusX -= playerVectorX;
            changeXY(-playerVectorX,0);
         
            if(hasTrail){
               for(int k=0; k<getWeaponTrail().getTrailListSize(); k++){
                  getWeaponTrail().moveTrailListXY(k,-playerVectorX, 0);
                  
               }
            }
         }
         if(south){
            previousFocusY -= playerVectorY;
            changeXY(0,-playerVectorY);
         
            if(hasTrail){
               for(int k=0; k<getWeaponTrail().getTrailListSize(); k++){
                  getWeaponTrail().moveTrailListXY(k,0,-playerVectorY);
                  
               }
            }
         }
         if(west){
            previousFocusX -= playerVectorX;
            changeXY(-playerVectorX,0);
         
         
            if(hasTrail){
               for(int k=0; k<getWeaponTrail().getTrailListSize(); k++){
                  getWeaponTrail().moveTrailListXY(k,-playerVectorX, 0);
                  
               }
            }
         }
      }
      
      
   }
   
}