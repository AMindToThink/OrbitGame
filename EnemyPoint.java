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

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

class EnemyPoint extends PointChanged{

   public static boolean classInitialize = true;
   
   private String type; 
 
 

   public ArrayList<String> attacks = new ArrayList<String>();
   public int attackIndex;
   public boolean inAttack;
   public boolean attackEnded;
   //Faces
   public BufferedImage face = null;
   public int imageExtraX;
   public int imageExtraY;
   public int imageFacePlayerX;
   public int imageFacePlayerY;
   
   public static BufferedImage daveFace = null;
   public static BufferedImage simpleFace = null;
   public static BufferedImage weinbergFace = null;
   
   
   
   private int projectileTimer = 0;
   private double projectileRate = CircleOrbitMouse.refreshTime*100;
   
   private double simpleEnemySpeed;
   public boolean movesOnPlayer = false;
 
   private double angle;
   private double rotation;
   

   private int damage;
   private int pointValue = 0;
   private int maxHealth=1;
   private int health = 1;
   private double enemyPlayerDistance;
   public double enemyPlayerAngle;
   private int playerLocationX;
   private int playerLocationY;
   private boolean shoots = false;
   public int numberShot = 0;
   
   private Color color;
   
   
   int rR = (int)(Math.random()*256);
   int rG = (int)(Math.random()*256);
   int rB = (int)(Math.random()*256);
   Color randomColor = new Color(rR, rG, rB);
   
   private Color stationaryColor= new Color(0x8b0000);
   private Color swoopColor= new Color(0xff3333);
   private Color simpleColor= new Color(0xff0000);
   private Color simpleRangedColor= Color.MAGENTA;
   private Color limpColor = new Color(0x550000); 
   
   private Color booColor = new Color(0xffd1dc);
   private Color rBooColor = new Color(0xffa1ac);
   private Color aBooColor = new Color(0xFFA07A);
   private Color nBooColor = new Color(0xFA8072); 
   
   private Color queenColor = new Color(0xDC143C);
   private Color rookColor = new Color(0xCD5C5C); 
   private Color bishopColor = new Color(0xED2939);
   
   private Color betterOrange = new Color(0xffa500);
   
   private static Color transparent = new Color(0,0,0,0);
   
   
   private int cooldownTimer=0;
   
   private int swoopStartX=x;
   private int swoopStartY=y;
   private int swoopStartPlayerX=x;
   private int swoopStartPlayerY=y;
   private boolean isSwooping=false;
   private boolean isReturnSwooping=false;
   private int howManySwoopUnits=0;
   
   private boolean swoop = false;
   private boolean isInSwoop=false;
   int targetX;
   int targetY;
   double trueTargetX;
   double trueTargetY;
   int targetX2;
   int targetY2;
   double trueTargetX2;
   double trueTargetY2;
   public boolean isBackSwoop = false;
   public boolean everBackSwoop = false;
   
   
   private int rememberPlayerX=0;
   private int rememberPlayerY=0;
   private double trueRememberPlayerX=0;
   private double trueRememberPlayerY=0;
   
   private boolean playerNotMoving=false;
   
   
   double bulletSpeed;
   int bulletDiameter;
   
   Projectile bullet;
   boolean onlyTargetOnce = true; 
   double slopeX;
   double slopeY;
   boolean isProjectile = false;
  
   //Trail
   int enemyTrailDiameter = 100;
   int enemyTrailLength = 70000;
   boolean hasTrail = false;
   Trail enemyTrail;
   
   //Shoot for 2Tetra and DualGuns
   boolean plusShoot = true;
   
   
   
   //Randomizer for Wall
   double wallAngle;
   
   //Health
   public boolean isHealth;
   
   //Diameters
   public static int daveDiameter = 100;
   public static int simpleDiameter = 30;
   public static int weinbergDiameter = 100;
   public boolean imageDrift = true;
   
   //Boss score
   public int spawnScore = -1;
   public boolean isBoss = false;
   
   //Momcolm
   private int spawnHealthFractions = 6;
   private int spawned = 0;
   public static void setUp(){
      try{
         daveFace = ImageIO.read(new File("daveV2.png"));
         daveFace = helpful.resize(daveFace, daveDiameter-10, daveDiameter-10);
               
      }catch (IOException e){
         daveFace = null;
               //asdf.out.println("Lol no");
      }
      try{
         simpleFace = ImageIO.read(new File("greenEyedFace.png"));
         simpleFace = helpful.resize(simpleFace, simpleDiameter-10, simpleDiameter-10);
               
      }catch (IOException e){
         simpleFace = null;
               //asdf.out.println("Lol no");
      }
      try{
            
         weinbergFace = ImageIO.read(new File("Weinberg monochrome.png"));
         weinbergFace = helpful.resize(weinbergFace, weinbergDiameter, weinbergDiameter);
               
      }catch (IOException e){
         weinbergFace = null;
               //asdf.out.println("Lol no");
      }
         
   }
   //constructor
   public EnemyPoint(String type, int x, int y, double masterSpeed, double refreshTime){
      super(x,y, 10, Color.BLACK);
      creationTime = CircleOrbitMouse.myTimerThread.myClock.millis();
   
      this.x=x;
      this.y=y;
      this.trueX=x;
      this.trueY=y;
      this.type=type;
      this.masterSpeed=masterSpeed;
      this.refreshTime = refreshTime;
      
      
      
      if(this.type=="Stationary"){
         diameter=30;
         damage = -1;
         color=stationaryColor;
         pointValue=10;
         
      }
      if(this.type=="TLessQueen"){
         pointValue=30;
         diameter=30;
         damage = -1;
         color=queenColor;
         simpleEnemySpeed=masterSpeed/15;
         
         
         
      }
      if(this.type=="TLessRook"){
         pointValue=20;
         diameter=30;
         damage = -1;
         color=rookColor;
         simpleEnemySpeed=masterSpeed/10;
         
         
            
            
         
      
      }
      if(this.type=="TLessBishop"){
         pointValue=20;
         diameter=30;
         damage = -1;
         color=bishopColor;
         simpleEnemySpeed=masterSpeed/15;
         
         
            
         
                  
      }
   
      if(this.type=="Nboo"){
      
      //I can't figure this out. I want it to move if the player is stationary
         pointValue=40;
         diameter = 30;
         damage = -1;
         color=nBooColor;
         simpleEnemySpeed= masterSpeed/5; 
         
         
         
      }    
      
      if(this.type=="Aboo"){
         pointValue=20;
         diameter = 30;
         damage = -1;
         color=aBooColor;
         simpleEnemySpeed= masterSpeed/5;
         
         
         
      }
   
      if(this.type=="Rboo"){
         pointValue=20;
         diameter = 30;
         damage = -1;
         color=rBooColor;
         simpleEnemySpeed= masterSpeed/5;
         
         
         
      }
      if(this.type=="Boo"){
         pointValue=20;
         diameter = 30;
         damage = -1;
         color=booColor;
         simpleEnemySpeed= masterSpeed/5;
         
         
         
      }
      
      if(this.type=="Swoop"){
         
         pointValue=40;
         color=swoopColor;
         damage = -1;
         diameter=30;
         int swoopMaxDistance = 200;
         int swoopMinDistance= 150;
        
      }
      if(this.type=="SwoopBack"){
         everBackSwoop = true;
         movesOnPlayer = true;
         pointValue=40;
         color=swoopColor;
         damage = -1;
         diameter=30;
         int swoopMaxDistance = 200;
         int swoopMinDistance= 150;
        
      }
   
      
      if(this.type=="Limp"){
         pointValue=10;
         color=limpColor;
         simpleEnemySpeed=masterSpeed/6;
         diameter=30;
        
      }
      if(this.type=="Simple"){
         pointValue=10;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         try{
            face = simpleFace;
          
         }catch(Exception e){}
         imageExtraX = 5;
         imageExtraY = 5;
      
      }
      if(this.type=="Strong Simple"){
         pointValue=10;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         maxHealth = (int)(500/refreshTime);
         health = maxHealth;
      
      }
      if(this.type=="Spiral"){
         pointValue=10;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/5;
         diameter=30;
         damage = -1;
      
      }
      if(this.type=="RSpiral"){
         pointValue=10;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/5;
         diameter=30;
         damage = -1;
      
      }
      
      if(this.type=="Grande"){
         pointValue=20;
         
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/6;
         diameter=40;
         damage = -1;
      
      }
      if(this.type=="Pequeño"){
         pointValue=20;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/20;
         diameter=10;
         damage = -1;
      
      }
      
      if(this.type=="Simple Ranged"){
         pointValue=50;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/4;
         bulletDiameter = 20;
         numberShot = 1;
      }
      if(this.type=="Strong Shooter"){
         pointValue=100;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/4;
         numberShot = 1;
         bulletDiameter = 20;
      }
      if(this.type=="Primal Aspid"){
         pointValue=100;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/4;
         numberShot = 3;
         bulletDiameter = 20;
      }
      if(this.type == "Primal"){
         pointValue=30;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/6;
         numberShot = 2;
         bulletDiameter = 20;
      }
      if(this.type=="Sniper"){
         pointValue=60;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/2;
         numberShot = 1;
         bulletDiameter = 10;
         projectileRate = 4000;
      }
      
      if(this.type=="Sentry"){
         pointValue=30;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/6;
         numberShot = 1;
         bulletDiameter = 40;
      }
      if(this.type=="Speed Sentry"){
         pointValue=30;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=60;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/6;
         numberShot = 1;
         bulletDiameter = 20;
         projectileRate = 500;
      }
      if(this.type=="Octo"){
         pointValue=30;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/6;
         numberShot = 8;
         bulletDiameter = 20;
         projectileRate = 1600;
      }
      if(this.type=="Wall"){
         pointValue=30;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/12;
         numberShot = 2;
         bulletDiameter = 20;
         projectileRate = 120;
         wallAngle = Math.random()*Math.PI*2;
      }
      if(this.type=="2Tetra"){
         pointValue=30;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/6;
         numberShot = 4;
         bulletDiameter = 40;
         projectileRate = 3000;
      }
      
      if(this.type=="G Shoot"){
         pointValue=50;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/4;
         numberShot = 1;
         bulletDiameter = 20;
      }
      if(this.type=="G Across"){
         pointValue=50;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/4;
         numberShot = 2;
         bulletDiameter = 20;
      }
      
      if(this.type == "Projectile"){
         pointValue = 0;
         color = Color.ORANGE;
         simpleEnemySpeed = masterSpeed/2;
         diameter = 10;
         damage = -1;
         isProjectile = true;
         
      }
      if(this.type == "Strong Projectile"){
         pointValue = 0;
         color = Color.ORANGE;
         simpleEnemySpeed = masterSpeed/2;
         diameter = 10;
         damage = -1;
         isProjectile = true;
         maxHealth = (int)(200/refreshTime);
         health = maxHealth;
         
      }
      if(this.type == "GProjectile"){
         pointValue = 0;
        // int rR = (int)(Math.random()*256);
        // int rG = (int)(Math.random()*256);
         //int rB = (int)(Math.random()*256);
         //Color randomColor = new Color(rR, rG, rB);
         color = betterOrange;
         //color = randomColor;
         simpleEnemySpeed = masterSpeed/2;
         diameter = 10;
         damage = -1;
         isProjectile = true;
      }
      
      if(this.type=="RandSpeed"){
         pointValue=10;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/(((int)(Math.random()*10))*4);
         diameter=30;
         damage = -1;
      
      }
      
      if(this.type=="Trail"){
         pointValue=30;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         enemyTrailDiameter = diameter;
         damage = -1;
          
         enemyTrailLength = (int)(enemyTrailLength/masterSpeed);
         enemyTrail = new Trail(this.x, this.y, enemyTrailDiameter, enemyTrailLength );
          
      }
      if(this.type == "Dave"){
         pointValue=200;
         
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/6;
         diameter=100;
         damage = -2;
         maxHealth = (int)(1000/refreshTime);
         health = maxHealth;
         //shoots = true;
         try{
            face = daveFace;
            
         }catch(Exception e){}
         imageExtraX = 5;
         imageExtraY = 5;
      }
      if(this.type == "Momcolm"){
         pointValue=200;
         spawnScore = 500;
         isBoss = true;
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/7;
         diameter=100;
         damage = -2;
         maxHealth = (int)(1000/refreshTime);
         health = maxHealth;
         //shoots = true;
         
         imageExtraX = 5;
         imageExtraY = 5;
         shoots = true;
         bulletSpeed = masterSpeed/6;
         numberShot = 1;
         bulletDiameter = 30;
         projectileRate = 1;
      }
       if(this.type=="MomcolmSpawn"){
         pointValue=50;
         color=simpleRangedColor;
         simpleEnemySpeed=masterSpeed/15;
         diameter=30;
         damage = -1;
         shoots = true;
         bulletSpeed = masterSpeed/4;
         bulletDiameter = 20;
         numberShot = 1;
          maxHealth = (int)(200/refreshTime);
         health = maxHealth;
      }
      if(this.type == "Weinberg"){      
         spawnScore = 5000;
         isBoss = true;
         pointValue=1000;
         imageDrift = false;
         
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/7;
         diameter=weinbergDiameter;
         face = weinbergFace;
         damage = -2;
         maxHealth = (int)(1000/refreshTime);
         health = maxHealth;
         
         shoots = true;
         bulletSpeed = masterSpeed/4;
         numberShot = 1;
         bulletDiameter = 10;
         projectileRate = 100;
         imageExtraX = 0;
         imageExtraY = -15;
         
      }
      if(this.type == "DualGuns"){ 
         movesOnPlayer = true;
         everBackSwoop = true;     
         attacks.add("SwoopBack");
         attacks.add("MoveRanged");
         
         spawnScore = 1000;
         isBoss = true;
         pointValue=1000;
         imageDrift = false;
         
         color=simpleColor;
         simpleEnemySpeed=masterSpeed/10;
         diameter=CircleOrbitMouse.FRAME_WIDTH/8;
         //face = weinbergFace;
         damage = -2;
         maxHealth = (int)(3000/refreshTime);
         health = maxHealth;
         
         shoots = true;
         bulletSpeed = masterSpeed/4;
         numberShot = 1;
         bulletDiameter = 20;
         projectileRate = 700;
         imageExtraX = 0;
         imageExtraY = -15;
         
      }
      if(this.type == "No Dont"){      
         pointValue=100;
         
         color=simpleColor;
         simpleEnemySpeed=masterSpeed;
         diameter=100;
         damage = -2;
         maxHealth = (int)(1000/refreshTime);
         health = maxHealth;
         
         shoots = true;
         bulletSpeed = masterSpeed/3;
         numberShot = 1;
         bulletDiameter = 10;
         projectileRate = 500;
      }
      if(this.type == "Health"){
         isHealth = true;
         diameter=30;
         damage = 1;
         color=transparent;
         pointValue=500;
      }
      
   }
   
   public String getType(){
      return type;
   }
   public boolean getIsProjectile(){
      return isProjectile;
   }
   public boolean getShoots(){
      if(shoots && (projectileTimer > projectileRate)){
         projectileTimer = 0;
         return true;
      }else{
         return false;
      }
      
   }
   public int getShootDiameter(){
      return bulletDiameter;
   }
   
   public double getShootSpeed(){
      return bulletSpeed;
   }
   public boolean getIsRanged(){
      return shoots;
   }
   public void rotateProjectileV(double angle, double playerX, double playerY){
      if(onlyTargetOnce){
         slopeX = xToStraightVector(this.x, this.y, (int)playerX, (int)playerY,simpleEnemySpeed,"towards",false, 10);
         slopeY = yToStraightVector(this.x, this.y, (int)playerX, (int)playerY,simpleEnemySpeed,"towards",false, 10);
         onlyTargetOnce = false;
         slopeX =  rotateXVector(slopeX, slopeY, angle);
         slopeY =  rotateYVector(slopeX, slopeY, angle);
      }
      //rotation = angle;
   }
   public void setProjectileAngle(double angle){
      if(onlyTargetOnce){
         onlyTargetOnce = false;
         slopeX = simpleEnemySpeed*Math.cos(angle);
         slopeY = simpleEnemySpeed*Math.sin(angle);
      }
   }
   public void rotateP(double angle){
      
         
      double tempSlopeX = slopeX;
      slopeX =  rotateXVector(slopeX, slopeY, angle);
      slopeY =  rotateYVector(tempSlopeX, slopeY, angle);
      
      //rotation = angle;
   }
   public ArrayList<Enemy> addProjectiles(ArrayList<Enemy> enemyList, double playerX, double playerY){
      //ArrayList<Enemy> = 
      
      switch(type){
         case "Primal Aspid":
            for(int j = 1; j>=-1; j--){
               double leftAngle = Math.PI/8.0;
               Enemy myProjectile = new Enemy("Projectile", x,y,  masterSpeed, refreshTime);
               myProjectile.setDiameter(getShootDiameter());
               myProjectile.rotateProjectileV(j*Math.PI/8.0, playerX, playerY);
               myProjectile.setSpeed(getShootSpeed());
               enemyList.add(0,myProjectile);
                  
                  //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                  
                  
                  ////System.out.println("hmm " + j);
                  //numberOfProjectiles++;
            }
            break;
         case "Primal":
            for(int j = 1; j>=-1; j=j-2){
               double leftAngle = Math.PI/8.0;
               Enemy myProjectile = new Enemy("Projectile", x,y,  masterSpeed, refreshTime);
               myProjectile.setDiameter(getShootDiameter());
               myProjectile.rotateProjectileV(j*Math.PI/8.0, playerX, playerY);
               myProjectile.setSpeed(getShootSpeed());
               enemyList.add(0,myProjectile);
                  
                  //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                  
                  
                  ////System.out.println("hmm " + j);
                  //numberOfProjectiles++;
            }
            break;
         case "Octo":
            for(int q = 0; q<8; q++){
               Enemy myProjectile = new Enemy("Projectile", x,y,  masterSpeed, refreshTime);
               myProjectile.setDiameter(getShootDiameter());
               myProjectile.rotateProjectileV(q*2*Math.PI/8.0, x+10, y);
               myProjectile.setSpeed(getShootSpeed());
               enemyList.add(0,myProjectile);
                  
                  //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                  
                  
                  ////System.out.println("hmm " + q);
                  //numberOfProjectiles++;
            }
            break;
         case "Wall":
            for(int q = 0; q<2; q++){
               if(q==0){
                  wallAngle = -wallAngle;
               }
               Enemy myProjectile = new Enemy("Projectile", x,y,  masterSpeed, refreshTime);
               myProjectile.setDiameter(getShootDiameter());
               myProjectile.rotateProjectileV((wallAngle)+q*2*Math.PI/2, x+10, y);
               myProjectile.setSpeed(getShootSpeed());
               enemyList.add(0,myProjectile);
                     
                     //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                     
                     
                     ////System.out.println("hmm " + q);
                     //numberOfProjectiles++;
               if(q==0){
                  wallAngle = -wallAngle;
               }
            }
            break;
         case "2Tetra":
            plusShoot = !plusShoot;
               
            double plusShootAngle = 0;
               
            if(plusShoot){
               plusShootAngle = Math.PI/4;
            }
               
            for(int q = 0; q<4; q++){
               Enemy myProjectile = new Enemy("Projectile", x,y,  masterSpeed, refreshTime);
               myProjectile.setDiameter(getShootDiameter());
               myProjectile.rotateProjectileV(plusShootAngle+q*2*Math.PI/4.0, x+10, y);
               myProjectile.setSpeed(getShootSpeed());
               enemyList.add(0,myProjectile);
                  
                  //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                  
                  
                  ////System.out.println("hmm " + q);
                  //numberOfProjectiles++;
            }
            break;
         case "G Shoot":
            Enemy myGProjectile = new Enemy("GProjectile", x,y,  masterSpeed, refreshTime);
            myGProjectile.setDiameter(getShootDiameter());
            myGProjectile.setSpeed(getShootSpeed());
            enemyList.add(0,myGProjectile);
            break;
         case "G Across":
            for(int j = 1; j>=-1; j -=2){
               double leftAngle = Math.PI/8.0;
               Enemy myProjectile = new Enemy("GProjectile", x,y,  masterSpeed, refreshTime);
               myProjectile.setDiameter(getShootDiameter());
               myProjectile.rotateProjectileV(j*Math.PI/4, playerX, playerY);
               myProjectile.setSpeed(getShootSpeed());
               enemyList.add(0,myProjectile);
                  
                  //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                  
                  
                  ////System.out.println("hmm " + j);
                  //numberOfProjectiles++;
            }
               
               
            break;
         case "Strong Shooter":
            Enemy mySProjectile = new Enemy("Strong Projectile", x,y,  masterSpeed, refreshTime);
            mySProjectile.setDiameter(getShootDiameter());
            mySProjectile.setSpeed(getShootSpeed());
            enemyList.add(0,mySProjectile);
            break;
         case "Momcolm":
            if((maxHealth-health)/spawnHealthFractions>spawned){
               spawned++;
               Enemy mymmProjectile = new Enemy("MomcolmSpawn", x,y,  masterSpeed, refreshTime);
            mymmProjectile.setDiameter(getShootDiameter());
            mymmProjectile.setSpeed(getShootSpeed());
            enemyList.add(0,mymmProjectile);
            }
            
            break;
         case "DualGuns":
            plusShoot = !plusShoot;
            if(plusShoot){
               Enemy myDProjectile = new Enemy("Strong Projectile", (int)(x + helpful.rotateXVector(helpful.xConsVector(x,y,(int)playerX,(int)playerY,diameter/2), helpful.yConsVector(x,y,(int)playerX,(int)playerY,diameter/2), Math.PI/2)),(int)(y + helpful.rotateYVector(helpful.xConsVector(x,y,(int)playerX,(int)playerY,diameter/2), helpful.yConsVector(x,y,(int)playerX,(int)playerY,diameter/2), Math.PI/2)),  masterSpeed, refreshTime);
               myDProjectile.setDiameter(getShootDiameter());
               myDProjectile.setSpeed(getShootSpeed());
               myDProjectile.setProjectileAngle(enemyPlayerAngle);
               enemyList.add(0,myDProjectile);
            }else{
               Enemy myD2Projectile = new Enemy("Strong Projectile", (int)(x - helpful.rotateXVector(helpful.xConsVector(x,y,(int)playerX,(int)playerY,diameter/2), helpful.yConsVector(x,y,(int)playerX,(int)playerY,diameter/2), Math.PI/2)),(int)(y - helpful.rotateYVector(helpful.xConsVector(x,y,(int)playerX,(int)playerY,diameter/2), helpful.yConsVector(x,y,(int)playerX,(int)playerY,diameter/2), Math.PI/2)),  masterSpeed, refreshTime);
               myD2Projectile.setDiameter(getShootDiameter());
               myD2Projectile.setSpeed(getShootSpeed());
               myD2Projectile.setProjectileAngle(enemyPlayerAngle);
               enemyList.add(0,myD2Projectile);
            }
              
               
            break;
               
         default:
            Enemy myProjectile = new Enemy("Projectile", x,y,  masterSpeed, refreshTime);
            myProjectile.setDiameter(getShootDiameter());
            myProjectile.setSpeed(getShootSpeed());
            enemyList.add(0,myProjectile);
                
                //numberOfProjectiles++;   
      }
      
      return enemyList;
   }
   public boolean inHitbox(int hmX, int hmY){
      switch(type){
         case "": 
            break;
         default:
            
         
      }
      return true;
   }
 
   
   public void setSpeed(double speed){
      slopeX /=simpleEnemySpeed;
      slopeY /=simpleEnemySpeed;
      
      simpleEnemySpeed = speed; 
      slopeX *=speed;
      slopeY *=speed;
          
   }
   public void setDiameter(int newDiameter){
      diameter = newDiameter;      
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
     
   public void changeTrueXY(double addX, double addY){
      trueX+=addX;
      trueY+=addY;
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
   public void setVector(double vectorX, double vectorY ){
      this.vectorX=vectorX;
      this.vectorY=vectorY;
      //changeXY(vectorX,vectorY);
   }
   public void changeVector(double vectorChangeX, double vectorChangeY){
      this.vectorX += vectorChangeX;
      this.vectorY += vectorChangeY;
   }


   
   public int getDamage(){
      return damage;
   }
   
   public int getHealth()
   {
      return health;
   }
   
   public int getMaxHealth(){
      return maxHealth;
   }
   
   public void changeHealth(int damage){
      health-=damage;
   }

   public void updateVector(){
      
   }
   public Color getColor(){
      return color;
   }
   
   public int getPointValue(){
      return pointValue;
   }
   
   public double getDistance(){
      return enemyPlayerDistance;
   }

   public String toString(){
      String str = "" + x + ", " + y;
      return str;     
   }
   
   public void drawCircle(int diameter){
   }
   public void shiftTarget(double xShift, double yShift){
      trueTargetX +=xShift;
      trueTargetY +=yShift;
      
      targetX=(int)(Math.round(trueTargetX));
      targetY=(int)(Math.round(trueTargetY));
   }
   public void shiftTarget2(double xShift, double yShift){
      trueTargetX2 +=xShift;
      trueTargetY2 +=yShift;
      
      targetX2=(int)(Math.round(trueTargetX2));
      targetY2=(int)(Math.round(trueTargetY2));
   }
   public void setTarget(double xPosition, double yPosition){
      trueTargetX =xPosition;
      trueTargetY =yPosition;
      
      targetX=(int)(Math.round(trueTargetX));
      targetY=(int)(Math.round(trueTargetY));
   }
   public void setTarget2(double xPosition, double yPosition){
      trueTargetX2 =xPosition;
      trueTargetY2 =yPosition;
      
      targetX2=(int)(Math.round(trueTargetX2));
      targetY2=(int)(Math.round(trueTargetY2));
   }
   public int getTargetX(){
      return targetX;
   }
   public int getTargetY(){
      return targetY;
   }
   public int getTargetX2(){
      return targetX2;
   }
   public int getTargetY2(){
      return targetY2;
   }
   public boolean getSwoop(){
      return swoop;
   }
   
   public int getRememberX(){
      return rememberPlayerX;
   }
   public int getRememberY(){
      return rememberPlayerY;
   }
   public void setRememberX(double change){
      trueRememberPlayerX += change;
      rememberPlayerX=(int)(Math.round(trueRememberPlayerX));
   }
   public void setRememberY(int change){
      trueRememberPlayerY += change;
      rememberPlayerY=(int)(Math.round(trueRememberPlayerY));
   }
   
   public boolean getHasTrail(){
      return hasTrail;
   }
   
   public Trail getEnemyTrail(){
      return enemyTrail;
   }
   
   public void attack(int playerLocationX,int playerLocationY, int playerDiameter, int playerHealths, boolean playerInvincible, int weaponLocationX, int weaponLocationY, int weaponDiameter){
      this.playerLocationX = playerLocationX;
      this.playerLocationY = playerLocationY;
      if(imageDrift){
         int faceFromCenter = diameter/4;
         imageFacePlayerX = (int)xToStraightVector(this.x, this.y, (int)playerLocationX, (int)playerLocationY,faceFromCenter,"towards",false, 10);
         imageFacePlayerY = (int)yToStraightVector(this.x, this.y, (int)playerLocationX, (int)playerLocationY,faceFromCenter,"towards",false, 10);
      }
      enemyPlayerAngle = helpful.findAngle(playerLocationX-x,playerLocationY-y);
      if (movesOnPlayer || x != playerLocationX || y != playerLocationY){
         enemyPlayerDistance=findDistance(playerLocationX, playerLocationY, x , y );
      
         if(this.type=="Stationary"){
            
            /*diameter=30;
            damage = -1;
            color=stationaryColor;
            pointValue=10;*/
         
         }
         if(this.type=="TLessQueen"){
            /*pointValue=30;
            diameter=30;
            damage = -1;
            color=queenColor;
            simpleEnemySpeed=masterSpeed/15;
         
         */
            if(playerLocationX>x){
               setVector(simpleEnemySpeed,0);
            }
            if(playerLocationX<x){
               setVector(-simpleEnemySpeed,0);
            }
            if(playerLocationY>y){
               setVector(0,simpleEnemySpeed);
            }
            if(playerLocationY<y){
               setVector(0,-simpleEnemySpeed);
            }
         
         }
         if(this.type=="TLessRook"){
            /*pointValue=20;
            diameter=30;
            damage = -1;
            color=rookColor;
            simpleEnemySpeed=masterSpeed/10;
         */
            if(Math.abs(playerLocationX-x)!=Math.abs(playerLocationY-y)){
            
               if(Math.abs(playerLocationX-x)>Math.abs(playerLocationY-y)){
                  if(playerLocationX>x){
                     setVector(simpleEnemySpeed,0);
                  }else{
                     setVector(-simpleEnemySpeed,0);
                  }
               }else{
                  if(playerLocationY>y){
                     setVector(0,simpleEnemySpeed);
                  }else{
                     setVector(0,-simpleEnemySpeed);
                  }
               
               }
            }
         
         }
         if(this.type=="TLessBishop"){
            /*pointValue=20;
            diameter=30;
            damage = -1;
            color=bishopColor;
            simpleEnemySpeed=masterSpeed/15;
         */
            if((playerLocationX==x||playerLocationY!=y)){
            
               if(playerLocationX>x){
               
               
                  if(playerLocationY>y){
                     setVector(0,simpleEnemySpeed);
                     setVector(simpleEnemySpeed,0);
                  }else{
                     setVector(0,-simpleEnemySpeed);
                     setVector(simpleEnemySpeed,0);
                  }
               }else if (playerLocationX<x){
               
               
                  if(playerLocationY>y){
                     setVector(0,simpleEnemySpeed);
                     setVector(-simpleEnemySpeed,0);
                  }else{
                     setVector(0,-simpleEnemySpeed);
                     setVector(-simpleEnemySpeed,0);
                  }
               }
            }
                  
         }
      
         if(this.type=="Nboo"){
         
         //I can't figure this out. I want it to move if the player is stationary
            /*pointValue=40;
            diameter = 30;
            damage = -1;
            color=nBooColor;
         */
            if(playerLocationX==rememberPlayerX&&playerLocationY==rememberPlayerY){
               playerNotMoving=true;
            }else{
               playerNotMoving=false;
            }
            if(playerNotMoving){
               simpleEnemySpeed= masterSpeed/5;
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
             
            }else{
               simpleEnemySpeed= -masterSpeed/5;
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            }
         
            rememberPlayerY=playerLocationY;
            rememberPlayerX=playerLocationX;
         
         }    
      
         if(this.type=="Aboo"){
            /*
            pointValue=20;
            diameter = 30;
            damage = -1;
            color=aBooColor;
         */
            if(rememberPlayerY!=0&&rememberPlayerX!=0&&((playerLocationX==rememberPlayerX) && (playerLocationY==rememberPlayerY))){
               setVector(0,0);
            }else{
            
               simpleEnemySpeed=masterSpeed/5;
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            
            
            }
            rememberPlayerY=playerLocationY;
            rememberPlayerX=playerLocationX;
         
         
         }
      
         if(this.type=="Rboo"){
            /*pointValue=20;
            diameter = 30;
            damage = -1;
            color=rBooColor;
         */
            if(rememberPlayerY!=0&&rememberPlayerX!=0&&(findDistance(playerLocationX, playerLocationY, x, y)<findDistance(rememberPlayerX, rememberPlayerY, x, y))){
               simpleEnemySpeed=masterSpeed/5;
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }
            rememberPlayerY=playerLocationY;
            rememberPlayerX=playerLocationX;
         
         
         }
         if(this.type=="Boo"){
           /*
            pointValue=20;
            diameter = 30;
            damage = -1;
            color=booColor;
         */
            if(rememberPlayerY!=0&&rememberPlayerX!=0&&(findDistance(playerLocationX, playerLocationY, x, y)>findDistance(rememberPlayerX, rememberPlayerY, x, y))){
               simpleEnemySpeed=masterSpeed/5;
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }
            rememberPlayerY=playerLocationY;
            rememberPlayerX=playerLocationX;
         
         
         }
      
         if(this.type=="Swoop"){
            /*pointValue=40;
            color=swoopColor;
            damage = -1;
            diameter=30;
            */
            int swoopMaxDistance = 200;
            int swoopMinDistance= 150;
            doSwoop(swoopMinDistance, swoopMaxDistance);
         
         }
         if(this.type=="SwoopBack"){
            /*pointValue=40;
            color=swoopColor;
            damage = -1;
            diameter=30;
            */
            int swoopMaxDistance = 200;
            int swoopMinDistance= 150;
            simpleEnemySpeed = masterSpeed/5;
            swoopAndBack(simpleEnemySpeed, swoopMinDistance, swoopMaxDistance);
         
         }
      
      
         if(this.type=="Limp"){
            /*pointValue=10;
            color=limpColor;
            double simpleEnemySpeed=masterSpeed/6;
            diameter=30;
            */
          //Thread.sleep(500);
            cooldownTimer++;
         
            int timeMoving = 10;
            int timeStopped = 10;
         
            if(cooldownTimer<timeMoving){
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            }
         
            if(cooldownTimer>timeStopped+timeMoving){
            
               cooldownTimer=0;
            }
         
            if(findDistance(weaponLocationX, weaponLocationY, this.x,this.y)<=diameter/2+weaponDiameter/2){
            ////System.out.println("nice shot");
            }
            damage = -1;
         
         
         
         
          //this.x=
         //this.y=
         
          
               
         
         }
         if(this.type == "Simple" || this.type == "Strong Simple"){
            setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
         
         }
         if(this.type=="Spiral"){
            /*pointValue=10;
            color=simpleColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            damage = -1;
            */
            double itsXV = xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10);
            double itsYV = yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10);
            setVector(rotateXVector(itsXV, itsYV, Math.PI/3), rotateYVector(itsXV,itsYV, Math.PI/3));
            
         
         }
         if(this.type=="RSpiral"){
            /*pointValue=10;
            color=simpleColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            damage = -1;
            */
            double itsXV = xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10);
            double itsYV = yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10);
            setVector(rotateXVector(itsXV, itsYV, -Math.PI/3), rotateYVector(itsXV,itsYV, -Math.PI/3));
            
         
         }
      
         if(this.type=="Grande"){
            /*pointValue=20;
            damage = -1;
            color=simpleColor;
            double simpleEnemySpeed=masterSpeed/6;
            diameter=40;
            */
            setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
         
         }
         if(this.type=="Pequeño"){
            /*
            pointValue=20;
            color=simpleColor;
            double simpleEnemySpeed=masterSpeed/20;
            diameter=10;
            damage = -1;
            */
            setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
         
         }
      
         if(this.type=="Simple Ranged" || this.type == "G Across" || this.type == "MomcolmSpawn"){
            /*
            pointValue=30;
            color=simpleRangedColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            */
            int minShootingRange=200;
            int maxShootingRange=230;
            if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=minShootingRange){
               setVector(-( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   -( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            }else if(findDistance(playerLocationX, playerLocationY, this.x,this.y)>=maxShootingRange){
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                      ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }else{
            //Projectiles
            //bullet = new Projectile("Straight", this.x,this.y,playerLocationX,playerLocationY);
            
            
            }
         
            try{
            //bullet.shoot(weaponLocationX, weaponLocationY, weaponDiameter, playerDiameter);
            
            }catch(Exception e){
            
            }
         }
         if(this.type=="G Shoot"){
            /*
            pointValue=30;
            color=simpleRangedColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            */
            int minShootingRange=200;
            int maxShootingRange=230;
            if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=minShootingRange){
               setVector(-( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   -( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            }else if(findDistance(playerLocationX, playerLocationY, this.x,this.y)>=maxShootingRange){
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                      ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }else{
            //Projectiles
            //bullet = new Projectile("Straight", this.x,this.y,playerLocationX,playerLocationY);
            
            
            }
         
            try{
            //bullet.shoot(weaponLocationX, weaponLocationY, weaponDiameter, playerDiameter);
            
            }catch(Exception e){
            
            }
         }
         if(this.type=="Primal Aspid"){
            /*
            pointValue=30;
            color=simpleRangedColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            */
            int minShootingRange=200;
            int maxShootingRange=230;
            if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=minShootingRange){
               setVector(-( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   -( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            }else if(findDistance(playerLocationX, playerLocationY, this.x,this.y)>=maxShootingRange){
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                      ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }else{
            
            
            }
         
            
         }
         if(this.type=="Primal"){
            /*
            pointValue=30;
            color=simpleRangedColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            */
            int minShootingRange=200;
            int maxShootingRange=230;
            if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=minShootingRange){
               setVector(-( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   -( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            }else if(findDistance(playerLocationX, playerLocationY, this.x,this.y)>=maxShootingRange){
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                      ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }else{
            
            
            }
         
            
         }
         if(this.type == "DualGuns"){
            int temp = 1;
            if((CircleOrbitMouse.myTimerThread.every(5000/*, this.creationTime*/))){
               
               //temp = (int)(Math.random()*attacks.size());
               temp = attacks.indexOf(helpful.nextInCycle(attacks, attacks.get(attackIndex)));
               
            }
            if(!inAttack){
               attackIndex = temp;
            }
            System.out.println(attacks.get(attackIndex));
            int swoopMinDistance = CircleOrbitMouse.FRAME_WIDTH/5;
            int swoopMaxDistance = CircleOrbitMouse.FRAME_WIDTH/5 + CircleOrbitMouse.FRAME_WIDTH/10;
             
            if(attacks.get(attackIndex).equals("SwoopBack")){
               simpleEnemySpeed = masterSpeed/9;
               swoopAndBack(simpleEnemySpeed, swoopMinDistance, swoopMaxDistance);
            
            
            }else{
               moveRanged(masterSpeed/8, swoopMinDistance, swoopMaxDistance);
            
            }
         
         }
         if(this.type=="Sniper"){
            /*
            pointValue=30;
            color=simpleRangedColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            */
            int minShootingRange=300;
            int maxShootingRange=330;
            if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=minShootingRange){
               setVector(-( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   -( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            }else if(findDistance(playerLocationX, playerLocationY, this.x,this.y)>=maxShootingRange){
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                      ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }else{
            //Projectiles
            //bullet = new Projectile("Straight", this.x,this.y,playerLocationX,playerLocationY);
            
            
            }
         
            try{
            //bullet.shoot(weaponLocationX, weaponLocationY, weaponDiameter, playerDiameter);
            
            }catch(Exception e){
            
            }
         }
         
         if(this.type == "Projectile" || this.type == "Strong Projectile"){   
            
            if(onlyTargetOnce){
               setTarget(playerLocationX, playerLocationY);
               
               onlyTargetOnce = false;
               //slopeX = slopeX(x,y, playerLocationX, playerLocationY, simpleEnemySpeed);
               //slopeY = slopeY(x,y, playerLocationX, playerLocationY, simpleEnemySpeed);
               slopeX = xToStraightVector(this.x, this.y, (int)trueTargetX, (int)trueTargetY,simpleEnemySpeed,"towards",false, 10);
               slopeY = yToStraightVector(this.x, this.y, (int)trueTargetX, (int)trueTargetY,simpleEnemySpeed,"towards",false, 10);
               
               //slopeX = rotateXVector(slopeX, slopeY, rotation);
               //slopeY = rotateYVector(slopeX, slopeY, rotation);
            }
            
            setVector(slopeX, slopeY);
            
            
         }
         
         if(this.type == "GProjectile"){   
            
            if(onlyTargetOnce){
               setTarget(playerLocationX, playerLocationY);
               
               onlyTargetOnce = false;
               //slopeX = slopeX(x,y, playerLocationX, playerLocationY, simpleEnemySpeed);
               //slopeY = slopeY(x,y, playerLocationX, playerLocationY, simpleEnemySpeed);
               slopeX = xToStraightVector(this.x, this.y, (int)trueTargetX, (int)trueTargetY,simpleEnemySpeed,"towards",false, 10);
               slopeY = yToStraightVector(this.x, this.y, (int)trueTargetX, (int)trueTargetY,simpleEnemySpeed,"towards",false, 10);
               
               //slopeX = rotateXVector(slopeX, slopeY, rotation);
               //slopeY = rotateYVector(slopeX, slopeY, rotation);
            }
             
            double turnAngle = refreshTime*2*Math.PI/50000;
            
            if(findDistance(x+rotateXVector(slopeX, slopeY, turnAngle), y+rotateYVector(slopeX, slopeY, turnAngle), playerLocationX, playerLocationY)>findDistance(x+rotateXVector(slopeX, slopeY, -turnAngle), y+rotateYVector(slopeX, slopeY, -turnAngle), playerLocationX, playerLocationY)){
               
               slopeX = rotateXVector(slopeX, slopeY, -turnAngle);
               slopeY = rotateYVector(slopeX, slopeY, -turnAngle);
               
            }else{
               slopeX = rotateXVector(slopeX, slopeY, turnAngle);
               slopeY = rotateYVector(slopeX, slopeY, turnAngle);
            }
            
            setVector(slopeX, slopeY);
            
            
         }
         
         if(this.type=="RandSpeed"){
            /*
            pointValue=10;
            color=simpleColor;
            simpleEnemySpeed=masterSpeed/(((int)(Math.random()*10))*4);
            diameter=30;
            damage = -1;
            */
            setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
         
         }
      
         if(this.type=="Trail"){
            /*
            pointValue=30;
            color=simpleColor;
            double simpleEnemySpeed=masterSpeed/20;
            diameter=30;
            damage = -1;
            */
            setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
                         
         }
         
         if(this.type == "Dave" || this.type == "Momcolm"){
            setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
         }
         if(this.type == "Weinberg"){
            setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
         }
         if(this.type=="No Dont"){
            /*
            pointValue=30;
            color=simpleRangedColor;
            double simpleEnemySpeed=masterSpeed/15;
            diameter=30;
            */
            int minShootingRange=200;
            int maxShootingRange=minShootingRange+(int)(2*simpleEnemySpeed);
            if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=minShootingRange){
               setVector(-( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   -( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            }else if(findDistance(playerLocationX, playerLocationY, this.x,this.y)>=maxShootingRange){
               setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                      ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
            }else{
            //Projectiles
            //bullet = new Projectile("Straight", this.x,this.y,playerLocationX,playerLocationY);
            
            
            }
         
            try{
            //bullet.shoot(weaponLocationX, weaponLocationY, weaponDiameter, playerDiameter);
            
            }catch(Exception e){
            
            }
         }
         
      
      /*
      try{
         if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=diameter/2+playerDiameter/2){
            ////System.out.println("hit");
            
            return(damage);
            
         }else{
            
            if(this.type == "Trail"){
               for(int k=0; k<enemyTrail.getTrailListSize(); k++){
                  
                  if(findDistance (enemyTrail.getTrailListXAt(k),enemyTrail.getTrailListYAt(k), playerLocationX, playerLocationY  )<=diameter/2+playerDiameter/2){
                     return(damage);
                  }
                  
                  
               }
            }
            
            
            return(0);
         }
         
      }catch(Exception e){
         return(0);
      }
      
      */
      go();
      }
      if(this.type=="Trail"){
         
         
         enemyTrail.follow(x,y);
          //System.out.println(enemyTrail);
      }
      
      if(shoots){
         projectileTimer += refreshTime;
      }
      //angle = findAngle(vectorX, vectorY);
      ////System.out.println(angle);
   }
   public void moveRanged(double speed, int minDistance, int maxDistance){
      int minShootingRange=minDistance;
      int maxShootingRange=maxDistance;
      if(findDistance(playerLocationX, playerLocationY, this.x,this.y)<=minShootingRange){
         setVector(-( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                   -( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
      }else if(findDistance(playerLocationX, playerLocationY, this.x,this.y)>=maxShootingRange){
         setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                      ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
      }else{
            //Projectiles
            //bullet = new Projectile("Straight", this.x,this.y,playerLocationX,playerLocationY);
            
            
      }
   
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
   
   public double rotateAngleX(double ogVectorX, double ogVectorY, double addAngle){
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
   public boolean isDamaging(int playerLocationX,int playerLocationY, int playerDiameter, boolean playerInvincible){
      if(!playerInvincible&&findDistance(playerLocationX, playerLocationY, this.x,this.y)<=diameter/2+playerDiameter/2){
            ////System.out.println("hit");
            
         return true;
            
      }else{
            
         if(this.type == "Trail"&&!playerInvincible){
            for(int k=0; k<enemyTrail.getTrailListSize(); k++){
                  
               if(findDistance (enemyTrail.getTrailListXAt(k),enemyTrail.getTrailListYAt(k), playerLocationX, playerLocationY  )<=enemyTrail.getTrailDiameter(k)/2+playerDiameter/2){
                  return true;
               }
                  
                  
            }
         }
            
            
         return(false);
      }
   }
   public void doSwoop(int swoopMinDistance, int swoopMaxDistance){
      
            
      if(!isInSwoop&enemyPlayerDistance> swoopMaxDistance){
         simpleEnemySpeed=masterSpeed/15;
         setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
      }else if(!isInSwoop&&enemyPlayerDistance<swoopMinDistance){
         simpleEnemySpeed=masterSpeed/10;
         setVector(( (-xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (-yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
      }else{
         simpleEnemySpeed=masterSpeed/6;
            
         if(swoop==false){
            swoop=true;
            setTarget(playerLocationX, playerLocationY);
               //targetX=playerLocationX;
               // targetY= playerLocationY;
            isInSwoop=true;
         }
         setVector(( (xToStraightVector(this.x, this.y, targetX, targetY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, targetX, targetY,simpleEnemySpeed,"towards",false, 10))) );
         if(this.x==targetX&&this.y==targetY){
            isInSwoop=false;
            swoop=false;
         }
            
            
            
      }
   }
   public void swoopAndBack(double speed, int swoopMinDistance, int swoopMaxDistance){
      attackEnded = false;
            
      if(!isBackSwoop && !isInSwoop&enemyPlayerDistance > swoopMaxDistance){
         simpleEnemySpeed=speed;
         setVector(( (xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
         setTarget2(0,0);
      
      }else if(!isBackSwoop && !isInSwoop&&enemyPlayerDistance<swoopMinDistance){
         setTarget2(0,0);
         simpleEnemySpeed=speed*1.5;
         setVector(( (-xToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))),
                    ( (-yToStraightVector(this.x, this.y, playerLocationX, playerLocationY,simpleEnemySpeed,"towards",false, 10))) );
            
            
      }else if (!isBackSwoop){
         simpleEnemySpeed=speed*3;
            
         if(swoop==false){
            setTarget2(x,y);
            swoop=true;
            setTarget(playerLocationX, playerLocationY);
               //targetX=playerLocationX;
               // targetY= playerLocationY;
            isInSwoop=true;
         }
         setVector(( (xToStraightVector(this.x, this.y, targetX, targetY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, targetX, targetY,simpleEnemySpeed,"towards",false, 10))) );
         if(this.x==targetX&&this.y==targetY){
            isInSwoop=false;
            swoop=false;
            isBackSwoop=true;
                  
         }
            
            
            
      }else{
         setTarget(targetX2, targetY2);
         setVector(( (xToStraightVector(this.x, this.y, targetX, targetY,simpleEnemySpeed,"towards",false, 10))),
                    ( (yToStraightVector(this.x, this.y, targetX, targetY,simpleEnemySpeed,"towards",false, 10))) );
         if(this.x==targetX&&this.y==targetY){
            isInSwoop=false;
            swoop=false;
            isBackSwoop=false;
            attackEnded = true;
            setTarget(0, 0);
            setTarget2(0, 0);
                  
         }               
      }
      inAttack = isInSwoop || swoop || isBackSwoop;     
   }
   public double rotateXVector(double xv, double yv, double angle){
      angle = fixAngle(angle);
      double hypotenuse = findDistance(0,0,xv, yv);
      
      
      
      //double adjacent = xv/hypotenuse;
      //double opposite = yv/hypotenuse;
      double ogAngle = findAngle(xv,yv);
      ogAngle = fixAngle(ogAngle);
      
      if(angle == Math.PI/2){
         
         return simpleEnemySpeed*Math.cos(Math.PI/2+ogAngle);
         
      }
      if(angle == 3*Math.PI/2){
         double rstuff = -simpleEnemySpeed*Math.cos(Math.PI/2+ogAngle);
         ////System.out.println(rstuff/simpleEnemySpeed);
         return rstuff;
      }
      
      double newCos;
        
      //double newAngle = adjacent*Math.cos(angle) - opposite*Math.sin(angle);
      newCos = Math.cos(ogAngle+angle);
        
      return newCos*simpleEnemySpeed;      
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
         
         return simpleEnemySpeed*Math.sin(Math.PI/2-ogAngle);
         
      }
      if(angle == 3*Math.PI/2){
         return simpleEnemySpeed*Math.sin(Math.PI/2-ogAngle);
      }
      //double newAngle = opposite*Math.cos(angle)+ adjacent*Math.sin(angle);
      newSin = Math.sin(ogAngle+angle);
      
      ////System.out.println(newSin);
            
      return newSin*simpleEnemySpeed;
      
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
            
             ////System.out.println(yDirect);
            
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
   
      
   public static double slopeY(int thingX, int thingY, int targetX, int targetY, double normalSpeed){
      double twoPointDistance=findDistance(thingX, thingY, targetX, targetY);
      double opposite=findYDistance(thingY, targetY);
      double angle = java.lang.Math.asin(opposite/twoPointDistance);
      double slope = java.lang.Math.sin(angle);
      
     // if(targetX<thingX){
     //    slope = -slope;
      //}
      
      return slope;
      
   }
   public static double slopeX(int thingX, int thingY, int targetX, int targetY, double normalSpeed){
      double twoPointDistance=findDistance(thingX, thingY, targetX, targetY);
      double adjacent=findXDistance(thingX, targetX);
      double angle = java.lang.Math.acos(adjacent/twoPointDistance);
      double slope = java.lang.Math.cos(angle);
      
      //if(targetX<thingX){
      //   slope = -slope;
      //}
      
      return slope;
      
   }
   public void camera(boolean north, boolean east, boolean south, boolean west, int playerVectorX, int playerVectorY){
      if(north){
         changeXY(0,-playerVectorY);
         if(targetX != 0 && targetY != 0){
            shiftTarget(0,-playerVectorY);
         }
         
         if(targetX2 != 0 && targetY2 != 0){
            shiftTarget2(0,-playerVectorY);
         }
         
         if(type=="Nboo"||type=="Boo"||type=="Rboo"||type=="Aboo"){
            setRememberY(-playerVectorY);
         }
            
         if(type=="Trail"){
            for(int k=0; k<getEnemyTrail().getTrailListSize(); k++){
               getEnemyTrail().moveTrailListXY(k,0,-playerVectorY);
                  
            }
         }
         
         
      }
      if(east){
         changeXY(-playerVectorX,0);
         if(targetX != 0 && targetY != 0){
            shiftTarget(-playerVectorX,0);
         }
         
         if(targetX2 != 0 && targetY2 != 0){
            shiftTarget2(-playerVectorX,0);
         }
         
         
         if(type=="Nboo"||type=="Boo"||type=="Rboo"||type=="Aboo"){
            setRememberX(-playerVectorX);
         }
            
         if(type=="Trail"){
            for(int k=0; k<getEnemyTrail().getTrailListSize(); k++){
               getEnemyTrail().moveTrailListXY(k,-playerVectorX,0);
                  
            }
         }
      }
      if(south){
         changeXY(0,-playerVectorY);
         
         
         if(targetX != 0 && targetY != 0){
            shiftTarget(0,-playerVectorY);
         }
         
         if(targetX2 != 0 && targetY2 != 0){
            shiftTarget2(0,-playerVectorY);
         }
         
         if(type=="Nboo"||type=="Boo"||type=="Rboo"||type=="Aboo"){
            setRememberY(-playerVectorY);
         }
            
         if(type=="Trail"){
            for(int k=0; k<getEnemyTrail().getTrailListSize(); k++){
               getEnemyTrail().moveTrailListXY(k,0,-playerVectorY);
                  
            }
         }
      }
      if(west){
         changeXY(-playerVectorX,0);
         
         if(targetX != 0 && targetY != 0){
            shiftTarget(-playerVectorX, 0);
         }
         
         if(targetX2 != 0 && targetY2 != 0){
            shiftTarget2(-playerVectorX, 0);
         }
         
         
         
         if(type=="Nboo"||type=="Boo"||type=="Rboo"||type=="Aboo"){
            setRememberX(-playerVectorX);
         }
            
         if(type=="Trail"){
            for(int k=0; k<getEnemyTrail().getTrailListSize(); k++){
               getEnemyTrail().moveTrailListXY(k,-playerVectorX,0);
                  
            }
         }
      }
   }
}