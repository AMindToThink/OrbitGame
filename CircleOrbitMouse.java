/* Controls so far:
      Click and Drag to move player WASD and arrow keys work, but are not optimal
      Click on player or press 'r' to return planets
      Press 'p' or click on the pause button to pause
      'c' gets rid of UI and grid, if you want.
      Click on upgrades or press '1', '2', or '3' to upgrade
      '0' closes the program
      Press "like" and "subscribe" to see more content, and don't forget to hit the bell for notifications.
      Whatever you do, DON'T push the red button!
*/

/* Bugs so far
      "Primal Aspid" angles look wrong, but are they? Other rotated vectors look correct
      Planets orbit differently with different frame rates. This might be because of some calculus stuff I have not learned yet.
      Definitely some weirds stuff happening with DualGuns
      Changing enemies into points definitely messed stuff up.
      A try-catch gets triggered sometimes. The one that prints "aqui". I do not know if that is a problem. I think that it happens if multiple things, like player damage and projectile destruction happens in the same frame.
      Why don't all of the planet types move in exactly the same way? They should overlap eachother after a boomerang and not move apart.

            
   ToDo
      Sell game to Stermoney for Stermoney's (ster)money
      
      More enemy types (always)
         Some bosses
         Spawners?
         Blobs?
      Playtesting/fixing point values
         Some enemies are WAY more powerful than others. That is ok, but those should reward more points to balance the work.
      Enemy chances of spawning?
      DEATH
         With zombie mode
            Keep playing, but not able to get any more upgrades and score stays constant
      Menus with START, DIFFICULTY LEVELS, ACHIEVEMENTS,  JOURNAL, CREDITS (lol, just me, but with some help), TUTORIAL/CONTROL explaination?, high scores,  
         Needs remembering? Text file? CSV/JSON Ovik says json better
         Add "restart" and "menu" to the pause screen
      Consumables:
         Dropped from the harder enemies
      Different areas with bosses?
         Laser Area
         Projectile Area
         Boo Area
         Standard Area
            Starting area always standard
         Chess Area?
      Stuff you can bring into the game
         Like HK charms
         Unlocked in-game
         Needs storage
         Examples
            Weapon trail
            Start with:
               Health up
               Speed up
               Size down
         
      Website/domain?
      
   
   Half Fixed
     Weapons moving too quickly no longer move through enemies without damaging them. Fixed only for planets hitting slow enemies. Fast enemies might go through planets and fast players through enemies.

   Fixed! 
     "Malcolm" type weapons do not move in circles
     "GProjectiles" - generally work, but that is not good enough. they move away at certain angles. 
      Fix bishops in same way as rooks (angles)
      "Nboos" - who are they and who do they work for?
           The boo gang's "remember" variables do not work quite right with the camera. I will have to check player position + player vector to tell if the player is going away or towards the boos.
      Trails don't work quite right
   Done:
       More upgrades
         Planet upgrades
            Planet types?
            I have made a skill tree in my notebook. I do not know how to implement it. I think maybe buttons on the right side of the screen.
       Planet trails should deal damage
     
   TODO - Ovik edition
      Make more faces
      Icons for the weapon drops
*/
import javax.swing.*;

import java.awt.*;

import java.awt.geom.Line2D;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.awt.event.MouseMotionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import java.util.ResourceBundle;
import java.time.Clock;
import java.time.*; 
public class CircleOrbitMouse implements java.io.Serializable, Cloneable{
    public String howDoesThisWork = "reverted";
    public static String startsA = "A";
    public static String staticTest = "Original";
       
   public static boolean textBoolean = true;
   
   //Time
   public static ZoneId zoneId = ZoneId.of("US/Arizona");
   public static Clock myClock = Clock.system(zoneId);
   public static timerThread myTimerThread;
   
   public KeyboardExample keyboardExample;
    
   public static int mouseX;
   public static int mouseY;
    
    
   public String isShowing = "Menu";
   public static String mode = "Classic";
   public boolean initializingGame = true;
   public static double frameRate = 30; 
   public static double timeInSeconds=0;
   public static int refreshTime = (int)(1000/(frameRate));
   public static double masterSpeed = 1000/frameRate;
   public static boolean photoMode = false;
    
    
   ArrayList<Point> list= new ArrayList<Point>();
    
    
    
   public static double weaponBaseSpeed = masterSpeed*0.01;
    
   public static double speed;

    
   public static int gridSeparation=80;
   
   //COLORS
   Color teal = new Color(0x0099af);
   Color turquoise = new Color(0x40e0d0);


   public static double weaponXVelocity = 0.0;
   public static double weaponYVelocity = 0.0;

   public static boolean isForcePropDisSqWeaponToMouse=true;
   
   private static final int weaponDiameter = 20;
   
   private static boolean circleTapped = false;
   private static boolean weaponReturn=false;
   public static int weaponReturnStartX;
   public static int weaponReturnStartY;
   
   private static boolean secondWeaponReturn=false;
   private static int secondWeaponReturnStartX;
   private static int secondWeaponReturnStartY;
    
    
   public static final int FRAME_WIDTH = 1280;

   public static final int FRAME_HEIGHT = 720;



   private MyCircle myCircle = new MyCircle();
 
    //private Orbiter planet = new Orbiter();


   private static int x = FRAME_WIDTH/2;

   private static int y = FRAME_HEIGHT/2;
    
   private static int playerX=FRAME_WIDTH/2;
    
   private static int playerY=FRAME_HEIGHT/2;
   
   private static int playerDiameter=45;
   public static Point playerPoint = new Point(playerX, playerY,playerDiameter, Color.BLUE);
    
   
             
   private static boolean isForcePropDisSqPlayerToMouse=false;
    
   
   public static double horLineOffset=0;
   public static double vertLineOffset=0;


   //weapon
   
   ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
   ArrayList<Weapon> standbyWeaponList = new ArrayList<Weapon>();
   int numReturned = 0;
   double firstWeaponGravity=1;
   double secondWeaponGravity=0.5;
   //Weapon firstWeapon = new Weapon("Bounce",playerX-100, playerY, masterSpeed, firstWeaponGravity, teal);
   //Weapon secondaryWeapon = new Weapon("Malcolm",playerX, playerY, masterSpeed, secondWeaponGravity, turquoise);
   
   //Second weapon trail
   int secondTrailDiameter=10;
   int secondTrailLength = 10;
   //Trail secondTrail = new Trail(secondaryWeapon.getX(), secondaryWeapon.getY(), secondTrailDiameter, secondTrailLength );
   
   //Enemies
   int numberOfEnemies=10;
   
   ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
   int numberOfProjectiles = 0;
   
   //Enemy firstEnemy = new Enemy("Boo",500,500,masterSpeed);
   //EnemyPoint firstEnemyPoint = new EnemyPoint("Simple", 500,500, masterSpeed, refreshTime);
   int enemiesKilled = 0;
   int score = 0;
   int combo = 0;
   int minCombo = 3;
   ArrayList<ComboNumber> comboList= new ArrayList<ComboNumber>();
   boolean shouldDave = true;
   int daveTimer = 0;
   
   boolean shouldWeinberg = true;
   ArrayList<Enemy> bossList = new ArrayList<Enemy>();
   ArrayList<Integer> bossNumList = new ArrayList<Integer>();
   public static boolean bossShouldSpawn = false;
   public static boolean bossPresent = false;
   ArrayList<ParticlePoof> particleList = new ArrayList<ParticlePoof>();
   
   //enemyList.add(firstEnemy);
   
   
  // for (int i = 0; i < numberOfEnemies; i++) {
    //  Enemy moreEnemy = new Enemy("Limp", Math.random()*10,Math.random()*10);
  // }

   
   //GUI
   int GUIHeight=FRAME_HEIGHT/24;
   
   //Upgrades
   boolean offerUpgrade = false;
   private static int numberOfEachUp = 5;
   
   boolean firstUpgradeChosen = false;
   boolean secondUpgradeChosen = false;
   boolean thirdUpgradeChosen = false;
   
   boolean mouseInNoGood = false;
    
   
   int upgradeScore = 50;
   int previousUpgradeScore = 0;
   int numberOfHealthUpgrades = 0;
   int numberOfSpeedUpgrades = 0;
   int numberOfSizeUpgrades = 0;
   int numTotalUpgrades = 0;
   
   //Special Upgrades
   ArrayList<Drop> dropList = new ArrayList<Drop>();
   ArrayList<ArrayList<String>> specialUp2d = new ArrayList<ArrayList<String>>();
      
   int owedSpecialUp = 0;
   int gottenSpecialUp = 0;
   int upWidth = 100;
   int upHeight = 100;
   
   int firstUpRectX = (FRAME_WIDTH/3)-upWidth/2;
   int secondUpRectX = (FRAME_WIDTH/2)-upWidth/2;
   int thirdUpRectX = (2*FRAME_WIDTH/3)-upWidth/2;
   int upRectY = FRAME_HEIGHT-150;
   
   ArrayList<Button> specialUpButtonList = new ArrayList<Button>();
   
     //Player Stats
   private static double playerSpeed=masterSpeed/3;
   
   
   private static int heartSeparation = 10;
   private static int playerStartHealth = 5;
   private static int playerMaxMaxHealth = numberOfEachUp+playerStartHealth;
   private static int playerMaxHealth = playerStartHealth;
   public static int playerHealth= playerMaxHealth;
   public static boolean isPlayerInvincible=false;
   public static int properInvincibleTime=1356;
   //public static int properInvincibleTime=43*refreshTime;
   
   public static int invincibleTime=properInvincibleTime;
   public static boolean heartFlash=false;
   public static int originalTimePink = properInvincibleTime*7/40;
   public static int timePink=originalTimePink;
   
   public static int pinkTimer=0;
   
   
   //Pausing
   public static boolean isPaused = false;
   int pauseScale = 1;
   int pauseHalfWidth = 5*pauseScale;
   int pauseHalfHeight = 20*pauseScale;
   int pauseX = (int)(FRAME_WIDTH*49.0/50.0-4)*pauseScale;
   int pauseY = (int)(FRAME_HEIGHT/20.0+2)*pauseScale;
   int pauseSpacing = 10*pauseScale;
   
   //Menu
   Button playButton = new Button(0,0,100,100, "PLAY");
   Button modesButton = new Button(0,0,50,50, "Modes");
   Button backButton = new Button(0,0,50,50, "Back");
   //String play = "PLAY";
   //int playButtonSize = 100;
   //int playButtonHeight = playButtonSize;
   //int playButtonWidth;
   
   //int playButtonX;
   //int playButtonY;
   Button creditButton = new Button(0,0,25,25,"Credits");
   int creditY = FRAME_HEIGHT;

   //Image helper stuff
  
   public transient BufferedImage shrinkImage;
   public transient BufferedImage speedUpImage;
   
   public transient BufferedImage weinbergImage = null;
   boolean initializeImages = true;
               
               
   public SavedInfo mySavedInfo;
   public CircleOrbitMouse()
   {
      mySavedInfo = new SavedInfo();
      //ResourceBundle rsc = ResourceBundle.getBundle("ImageBundle");
      Enemy.setUp();
      
      ArrayList<String> level0Up = new ArrayList<String>();
      level0Up.add("Size");
      level0Up.add("Trail");
         
      ArrayList<String> level1Up = new ArrayList<String>();  
      level1Up.add("+Planet"); 
      level1Up.add("+Tangent");
      level1Up.add("+RTangent");
      level1Up.add("+CTowards");
      ArrayList<String> level2Up = new ArrayList<String>();
      ArrayList<String> level3Up = new ArrayList<String>();
      ArrayList<String> level4Up = new ArrayList<String>();
      
      specialUp2d.add(level0Up);
      specialUp2d.add(level1Up);
      specialUp2d.add(level2Up);
      specialUp2d.add(level3Up);
      specialUp2d.add(level4Up);
      
      //Bosses
      bossList.add(new Enemy("Weinberg",FRAME_WIDTH/2, -FRAME_HEIGHT/2, masterSpeed, refreshTime));
      bossList.add(new Enemy("DualGuns",FRAME_WIDTH/2, 3*FRAME_HEIGHT/2, masterSpeed, refreshTime));
      bossList.add(new Enemy("Momcolm",FRAME_WIDTH/2, 3*FRAME_HEIGHT/2, masterSpeed, refreshTime));

     // bossNumList.add(5000);
      //enemyList.add(firstEnemy);
      
     // numberOfEnemies = 20;
      //spawnEnemies(0,0,FRAME_WIDTH,FRAME_HEIGHT,numberOfEnemies);
      
      //asdf.out.println(enemyList);
      
      
   }
   CircleOrbitMouse tempCircleOrbitMouse;
   //The class as a whole is called "CircleOrbitMouse"
   //The instance of CircleOrbitMouse is "circleViewer"
   //serialize is called really early in the program, pretty much immediately
   //deserialize is called when (playerHealth<=0)
   public void serialize(){
      try {
         FileOutputStream fileOut =
         new FileOutputStream("/tmp/CircleGame.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(circleViewer);
         out.close();
         fileOut.close();
         System.out.printf("Serialized data is saved in /tmp/CircleGame.ser");
      } catch (IOException i) {
         i.printStackTrace();
      }
      
   }
   public void deserialize(){
      try {
         System.out.println("Tried");
         FileInputStream fileIn = new FileInputStream("/tmp/CircleGame.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         /*The toString just prints the value of isShowing, 
         the variable that tells the program if it should show the 
         menu, the game, etc. This works as intended; it prints "Menu"
          which is what isShowing was when serialization occurred
          */
         //System.out.println((CircleOrbitMouse) in.readObject());
         //This is the line that causes the java.io.EOFException:
         circleViewer = (CircleOrbitMouse) in.readObject(); 
         in.close();
         fileIn.close();
         System.out.println("Tried");

      } catch (IOException i) {
         System.out.println("2nd deserialize catch");
         i.printStackTrace();
         return;
      } catch (ClassNotFoundException c) {
         System.out.println("CircleOrbitMouse class not found");
         c.printStackTrace();
         return;
      }
  }
   public CircleOrbitMouse getSerialFile() throws FileNotFoundException, IOException, ClassNotFoundException{
      FileInputStream fileIn = new FileInputStream("/tmp/CircleGame.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         //The toString just prints the value of isShowing, the variable that tells the program if it should show the menu, the game, etc.
         return (CircleOrbitMouse) in.readObject();
   }
   
   public String toString(){
      return isShowing;
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
            
             //asdf.out.println(yDirect);
            
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

   public static boolean pointInRect(int cornerX, int cornerY, int width, int height, int pointX, int pointY){
      if(pointX>=cornerX&&pointX<=cornerX+width&&pointY>=cornerY&&pointY<=cornerY+height){
         return true;
      }else{
         return false;
      }
        
   }
    
   public void spawnEnemies(int xCorner, int yCorner, int width, int height, int quantity){
      
      for(int i=1; i<=quantity; i++){
         
         int typeRandomInt = (int)(Math.random()*33+1);
         String typeRandom;
         
         switch(typeRandomInt){
            
            case 1: typeRandom = "TLessQueen"; 
               break;
            case 2: typeRandom = "TLessRook"; 
               break;
            case 3: typeRandom = "TLessBishop"; 
               break;
            case 4: typeRandom = "Nboo"; 
               break;
            case 5: typeRandom = "Aboo"; 
               break;
            case 6: typeRandom = "Rboo"; 
               break;
            case 7: typeRandom = "Boo"; 
               break;
            case 8: typeRandom = "Swoop"; 
               break;
            case 9: typeRandom = "Limp"; 
               break;
            case 10: typeRandom = "Simple"; //Has image
               break;
            case 11: typeRandom = "Simple Ranged"; 
               break;
            case 12: typeRandom = "Grande"; 
               break;
            case 13: typeRandom = "Pequeño"; 
               break;
            case 14: typeRandom = "Trail"; 
               break;
            case 15: typeRandom = "Sentry"; 
               break;
            case 16: typeRandom = "Speed Sentry"; 
               break;  
            case 17: typeRandom = "Primal Aspid";
               break;
            case 18: typeRandom = "Octo";
               break;
            case 19: typeRandom = "Spiral";
               break;
            case 20: typeRandom = "RSpiral";
               break;
            case 21: typeRandom = "Wall"; //Has Image
               break;
            case 22: typeRandom = "2Tetra";
               break;
            case 23: typeRandom = "Primal";
               break;
            case 24: typeRandom = "G Shoot";
               break;
            case 25: typeRandom = "G Across";
               break;
            case 26: typeRandom = "Health";
               break;
            case 27: typeRandom = "Strong Shooter";
               break;
            case 28: typeRandom = "SwoopBack";
               break;
            case 29: typeRandom = "RandLaser";
               break;
            case 30: typeRandom = "SentryLaser";
               break;
            case 31: typeRandom = "LaserAspid";
               break;
            case 32: typeRandom = "RotateLaser";
               break;   
            case 33: typeRandom = "SNboo";
               break;      
            
            default: typeRandom = "Stationary";             
         }
         
         /*if(shouldDave && timeInSeconds>10000){
            typeRandom = "Dave";
         }*/
         //Set All Enemies
         
         //typeRandom = "Wall";
         int randomPositionX= (int)(Math.random()*(width+1)+xCorner);
         int randomPositionY= (int)(Math.random()*(height+1)+yCorner);
         Enemy myEnemy = new Enemy(typeRandom, randomPositionX, randomPositionY,  masterSpeed, refreshTime);
         //if(enemyList.size()==0){
         enemyList.add(myEnemy);
         //}
      }
   }
   
   public void spawnEnemy(int x, int y, int quantity, String type){
      for(int i = 0; i<quantity; i++){
         Enemy myEnemy = new Enemy(type, x, y,  masterSpeed, refreshTime);
         enemyList.add(myEnemy);
      }
   } 

 
   public static FileOutputStream outputStream;
   public static ObjectOutputStream objectOutputStream;
   public static CircleOrbitMouse circleViewer;
   public static void main(String[] args) throws InterruptedException, IOException, CloneNotSupportedException   {
   
    
           //enemyList.add(firstEnemy);
     /* CircleOrbitMouse*/ circleViewer = new CircleOrbitMouse();
        
        //CircleViewer planet = new CircleViewer();
   outputStream = new FileOutputStream("C:\\Users\\Username\\Desktop\\save.ser");
   objectOutputStream = new ObjectOutputStream(outputStream);


      circleViewer.run();
      
   
   }

 
   private void moveWeapon(){
      try{
      for(Weapon eachWeapon : weaponList){
         eachWeapon.orbit(eachWeapon.getTarget().getX(), eachWeapon.getTarget().getY(), eachWeapon.getTarget().getDiameter());
      }
      }catch (Exception e){System.out.println("seriously");}
      
      //secondaryWeapon.orbit(firstWeapon.getX(), firstWeapon.getY(), playerPoint.getDiameter());
   
   
      //weaponXVelocity = weaponXVelocity+(xToStraightVector(x, y, playerPoint.getX(), playerPoint.getY(), weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, weaponDiameter));
      //weaponYVelocity = weaponYVelocity+(yToStraightVector(x, y, playerPoint.getX(), playerPoint.getY(), weaponBaseSpeed, "towards",isForcePropDisSqWeaponToMouse, weaponDiameter));
         //asdf.out.println(weaponXVelocity);
      
      
            //x=x+xStraightVector(x, y, mouseX, mouseY, weaponBaseSpeed);
           // y=y+yStraightVector(x, y, mouseX, mouseY, weaponBaseSpeed);
      
      //x= (int)(Math.round(x+(weaponXVelocity)));
      //y= (int)(Math.round(y+(weaponYVelocity)));
      
      
      //asdf.out.println(firstWeapon.getX()+" "+ x);
   }
 
   private void movePlayer(){
      //Checks that the mouse is not in an upgrade box
      if(pointInRect(pauseX, pauseY, pauseX+pauseHalfWidth+pauseSpacing, pauseY+pauseHalfHeight, mouseX, mouseY)||offerUpgrade&&(pointInRect(firstUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)||pointInRect(secondUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)||pointInRect(thirdUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY))){
         mouseInNoGood = true;
      }else{
         mouseInNoGood = false;
      }
      boolean usedKeys = false;
      if(mouseY>GUIHeight && !mouseInNoGood && !(keyboardExample.getMyKeyListener().downDown || keyboardExample.getMyKeyListener().upDown || keyboardExample.getMyKeyListener().leftDown || keyboardExample.getMyKeyListener().rightDown)){
         playerPoint.setVector((int)(Math.round(xToStraightVector(playerPoint.getX(), playerPoint.getY(), mouseX, mouseY,playerSpeed,"towards",isForcePropDisSqPlayerToMouse, playerPoint.getDiameter()))),
                                  (int)(Math.round(yToStraightVector(playerPoint.getX(), playerPoint.getY(), mouseX, mouseY,playerSpeed,"towards",isForcePropDisSqPlayerToMouse, playerPoint.getDiameter()))) ); 
      } else{
         //Keyboard controls
         if(keyboardExample.getMyKeyListener().rightDown){
            playerPoint.setVector(playerSpeed,0);
         }
         if(keyboardExample.getMyKeyListener().leftDown){
            playerPoint.setVector(-playerSpeed,0);
         }
         if(keyboardExample.getMyKeyListener().upDown){
            playerPoint.setVector(0,-playerSpeed);
         }
         if(keyboardExample.getMyKeyListener().downDown){
            playerPoint.setVector(0,playerSpeed);
         }
         if(keyboardExample.getMyKeyListener().rightDown && keyboardExample.getMyKeyListener().upDown){
            playerPoint.setVector(playerSpeed/Math.sqrt(2),-playerSpeed/Math.sqrt(2));
         }
         if(keyboardExample.getMyKeyListener().upDown && keyboardExample.getMyKeyListener().leftDown){
            playerPoint.setVector(-playerSpeed/Math.sqrt(2),-playerSpeed/Math.sqrt(2));
         }
         if(keyboardExample.getMyKeyListener().leftDown && keyboardExample.getMyKeyListener().downDown){
            playerPoint.setVector(-playerSpeed/Math.sqrt(2),playerSpeed/Math.sqrt(2));
         }
         if(keyboardExample.getMyKeyListener().downDown && keyboardExample.getMyKeyListener().rightDown){
            playerPoint.setVector(playerSpeed/Math.sqrt(2),playerSpeed/Math.sqrt(2));
         }
         if(keyboardExample.getMyKeyListener().leftDown && keyboardExample.getMyKeyListener().rightDown){
            playerPoint.setVector(0,0);
         }
         if(keyboardExample.getMyKeyListener().downDown && keyboardExample.getMyKeyListener().upDown){
            playerPoint.setVector(0,0);
         }
         if(keyboardExample.getMyKeyListener().rightDown && keyboardExample.getMyKeyListener().upDown && keyboardExample.getMyKeyListener().leftDown){
            playerPoint.setVector(0,-playerSpeed);
         }
         if(keyboardExample.getMyKeyListener().upDown && keyboardExample.getMyKeyListener().leftDown && keyboardExample.getMyKeyListener().downDown){
            playerPoint.setVector(-playerSpeed,0);
         }
         if(keyboardExample.getMyKeyListener().leftDown && keyboardExample.getMyKeyListener().downDown && keyboardExample.getMyKeyListener().rightDown){
            playerPoint.setVector(0,playerSpeed);
         }
         if(keyboardExample.getMyKeyListener().downDown && keyboardExample.getMyKeyListener().rightDown && keyboardExample.getMyKeyListener().upDown){
            playerPoint.setVector(playerSpeed,0);
         }
         if(keyboardExample.getMyKeyListener().downDown && keyboardExample.getMyKeyListener().rightDown && keyboardExample.getMyKeyListener().upDown  && keyboardExample.getMyKeyListener().leftDown){
            playerPoint.setVector(0,0);
         }
         
         
         /*if(keyboardExample.getMyKeyListener().downDown && !keyboardExample.getMyKeyListener().upDown){
            if(keyboardExample.getMyKeyListener().leftDown && !keyboardExample.getMyKeyListener().rightDown){
               playerPoint.setVector(-playerSpeed/Math.sqrt(2), playerSpeed/Math.sqrt(2));
            }else if(keyboardExample.getMyKeyListener().rightDown&& !keyboardExample.getMyKeyListener().leftDown){
               playerPoint.setVector(playerSpeed/Math.sqrt(2), playerSpeed/Math.sqrt(2));
               
            }else{
               playerPoint.setVector(0, playerSpeed*1);
            }
         }else if(keyboardExample.getMyKeyListener().upDown&& !keyboardExample.getMyKeyListener().downDown){
            if(keyboardExample.getMyKeyListener().leftDown && !keyboardExample.getMyKeyListener().rightDown){
               playerPoint.setVector(-playerSpeed/Math.sqrt(2), -playerSpeed/Math.sqrt(2));
            }else if(keyboardExample.getMyKeyListener().rightDown&& !keyboardExample.getMyKeyListener().leftDown){
               playerPoint.setVector(playerSpeed/Math.sqrt(2), -playerSpeed/Math.sqrt(2));
               
            }else{
               playerPoint.setVector(0, -playerSpeed*1);
            }
         }else if(keyboardExample.getMyKeyListener().leftDown && !keyboardExample.getMyKeyListener().rightDown){
            if(keyboardExample.getMyKeyListener().upDown && !keyboardExample.getMyKeyListener().downDown){
               playerPoint.setVector(-playerSpeed/Math.sqrt(2), -playerSpeed/Math.sqrt(2));
            }else if(keyboardExample.getMyKeyListener().downDown&& !keyboardExample.getMyKeyListener().upDown){
               playerPoint.setVector(-playerSpeed/Math.sqrt(2), playerSpeed/Math.sqrt(2));
               
            }else{
               playerPoint.setVector(-playerSpeed*1,0);
            }
         
         }else if(keyboardExample.getMyKeyListener().rightDown && !keyboardExample.getMyKeyListener().leftDown){
            if(keyboardExample.getMyKeyListener().upDown && !keyboardExample.getMyKeyListener().downDown){
               playerPoint.setVector(playerSpeed/Math.sqrt(2), -playerSpeed/Math.sqrt(2));
            }else if(keyboardExample.getMyKeyListener().downDown&& !keyboardExample.getMyKeyListener().upDown){
               playerPoint.setVector(playerSpeed/Math.sqrt(2), playerSpeed/Math.sqrt(2));
               
            }else{
               playerPoint.setVector(playerSpeed*1,0);
            }
         }
         if(keyboardExample.getMyKeyListener().rightDown && keyboardExample.getMyKeyListener().leftDown){
            playerPoint.setVector(0,playerPoint.getYVector());
         }
         if(keyboardExample.getMyKeyListener().upDown && keyboardExample.getMyKeyListener().downDown){
            playerPoint.setVector(playerPoint.getXVector(),0);
         }
         if(keyboardExample.getMyKeyListener().rightDown && keyboardExample.getMyKeyListener().leftDown && (keyboardExample.getMyKeyListener().upDown)){
            playerPoint.setVector(0,-playerSpeed/Math.sqrt(2));
         }
         if(keyboardExample.getMyKeyListener().rightDown && keyboardExample.getMyKeyListener().leftDown && (keyboardExample.getMyKeyListener().downDown)){
            playerPoint.setVector(0,playerSpeed/Math.sqrt(2));
         }
         if(keyboardExample.getMyKeyListener().upDown && keyboardExample.getMyKeyListener().downDown && (keyboardExample.getMyKeyListener().rightDown)){
            playerPoint.setVector(-playerPoint.getXVector(),0);
         }
         if(keyboardExample.getMyKeyListener().upDown && keyboardExample.getMyKeyListener().downDown && (keyboardExample.getMyKeyListener().leftDown)){
            playerPoint.setVector(playerPoint.getXVector(),0);
         }*/
         usedKeys = true;
      }
      playerPoint.changeXY(playerPoint.getXVector(), playerPoint.getYVector());
         //playerX=playerX+playerXVector;
      //playerPoint.setY(playerPoint.getY()+playerPoint.getYVector());
      playerPoint.changeFromOriginXY(playerPoint.getXVector(), playerPoint.getYVector());
      if(usedKeys){
         mouseX = playerPoint.getX();
         mouseY = playerPoint.getY();
         //playerPoint.setVector(0,0);
      }
   
      
   }
 
   private void playerMoveCamera(){
      
      boolean north = false;
      boolean south = false;
      boolean east = false;
      boolean west = false;
      if(playerPoint.getX()>FRAME_WIDTH-FRAME_WIDTH/3&&playerPoint.getXVector()>0){
         
         east = true;
         
         //x=x-playerPoint.getXVector();
         //playerPoint.setX(playerPoint.getX()-playerPoint.getXVector());
         vertLineOffset=(vertLineOffset-playerPoint.getXVector());
            
          //move enemies accordingly  
          /*
         for(int i = 0; i<enemyList.size(); i++){
            enemyList.get(i).changeXY(-playerPoint.getXVector(),0);
            enemyList.get(i).shiftTarget(-playerPoint.getXVector(),0);
            
            if(enemyList.get(i).getType()=="Nboo"||enemyList.get(i).getType()=="Boo"||enemyList.get(i).getType()=="Rboo"||enemyList.get(i).getType()=="Aboo"){
               enemyList.get(i).setRememberX(-playerPoint.getXVector());
            }
            
            if(enemyList.get(i).getType()=="Trail"){
               for(int k=0; k<enemyList.get(i).getEnemyTrail().getTrailListSize(); k++){
                  enemyList.get(i).getEnemyTrail().moveTrailListXY(k,-playerPoint.getXVector(),0);
                  //g.fillOval(enemyList.get(i).getEnemyTrail().getTrailListXAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailListYAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailDiameter(k),  enemyList.get(i).getEnemyTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
               }
            }
            
            
         }
         //move weapon accordingly
         firstWeapon.changeXY(-playerPoint.getXVector(),0);
         secondaryWeapon.changeXY(-playerPoint.getXVector(),0);
         
         //move trail
         if(firstWeapon.getIsTrail()){
            for(int i=0; i<firstWeapon.getTrailListSize(); i++){
               firstWeapon.moveTrailListXY(i, -playerPoint.getXVector(),0  );
            }
         }
         
         for(int i = 0; i<secondTrail.getTrailListSize(); i++ ){
            secondTrail.moveTrailListXY(i, -playerPoint.getXVector(),0);
         }
      
         for(int i=0; i<comboList.size(); i++){
            comboList.get(i).changeXY(-playerPoint.getXVector(),0);
            
         }
           */ 
      }
      if(playerPoint.getX()<FRAME_WIDTH/3&&playerPoint.getXVector()<0){
         west = true;
         
         x=x-playerPoint.getXVector();
         //playerPoint.setX(playerPoint.getX()-playerPoint.getXVector());
         vertLineOffset=vertLineOffset-playerPoint.getXVector();
            
         /*for(int i = 0; i<enemyList.size(); i++){
            enemyList.get(i).changeXY(-playerPoint.getXVector(),0);
            enemyList.get(i).shiftTarget(-playerPoint.getXVector(),0);
            
            if(enemyList.get(i).getType()=="Nboo"||enemyList.get(i).getType()=="Boo"||enemyList.get(i).getType()=="Rboo"||enemyList.get(i).getType()=="Aboo"){
               enemyList.get(i).setRememberX(-playerPoint.getXVector());
            }
            
            if(enemyList.get(i).getType()=="Trail"){
               for(int k=0; k<enemyList.get(i).getEnemyTrail().getTrailListSize(); k++){
                  enemyList.get(i).getEnemyTrail().moveTrailListXY(k,-playerPoint.getXVector(),0);
                  //g.fillOval(enemyList.get(i).getEnemyTrail().getTrailListXAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailListYAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailDiameter(k),  enemyList.get(i).getEnemyTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
               }
            }
         
         }
         
         //move weapon accordingly
         firstWeapon.changeXY(-playerPoint.getXVector(),0);
         secondaryWeapon.changeXY(-playerPoint.getXVector(),0);
         
         //move trail
         if(firstWeapon.getIsTrail()){
            for(int i=0; i<firstWeapon.getTrailListSize(); i++){
               firstWeapon.moveTrailListXY(i, -playerPoint.getXVector(),0  );
            }
         }
         
         for(int i = 0; i<secondTrail.getTrailListSize(); i++ ){
            secondTrail.moveTrailListXY(i, -playerPoint.getXVector(),0);
         }
         for(int i=0; i<comboList.size(); i++){
            comboList.get(i).changeXY(-playerPoint.getXVector(),0);
            
         }*/
      }
      if(playerPoint.getY()>FRAME_HEIGHT-FRAME_HEIGHT/3&&playerPoint.getYVector()>0){
         south = true;
         y=y-playerPoint.getYVector();
         //playerPoint.setY(playerPoint.getY()-playerPoint.getYVector());
         horLineOffset=horLineOffset-playerPoint.getYVector(); 
         /*      
         for(int i = 0; i<enemyList.size(); i++){
            enemyList.get(i).changeXY(0,-playerPoint.getYVector());
            enemyList.get(i).shiftTarget(0,-playerPoint.getYVector());
               
            if(enemyList.get(i).getType()=="Nboo"||enemyList.get(i).getType()=="Boo"||enemyList.get(i).getType()=="Rboo"||enemyList.get(i).getType()=="Aboo"){
               enemyList.get(i).setRememberY(-playerPoint.getYVector());
            }
            
            if(enemyList.get(i).getType()=="Trail"){
               for(int k=0; k<enemyList.get(i).getEnemyTrail().getTrailListSize(); k++){
                  enemyList.get(i).getEnemyTrail().moveTrailListXY(k,0,-playerPoint.getYVector());
                  //g.fillOval(enemyList.get(i).getEnemyTrail().getTrailListXAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailListYAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailDiameter(k),  enemyList.get(i).getEnemyTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
               }
            }
         
         }
         
         //move weapon accordingly
         firstWeapon.changeXY(0,-playerPoint.getYVector());
         secondaryWeapon.changeXY(0,-playerPoint.getYVector());
         
         //move trail
         if(firstWeapon.getIsTrail()){
            for(int i=0; i<firstWeapon.getTrailListSize(); i++){
               firstWeapon.moveTrailListXY(i, 0,-playerPoint.getYVector()  );
            }
         }
         for(int i = 0; i<secondTrail.getTrailListSize(); i++ ){
            secondTrail.moveTrailListXY(i, 0,-playerPoint.getYVector());
         }
         for(int i=0; i<comboList.size(); i++){
            comboList.get(i).changeXY(0,-playerPoint.getYVector());
            
         }
      */}
      if(playerPoint.getY()<FRAME_HEIGHT/3&&playerPoint.getYVector()<0){
         north = true;
         
         y=y-playerPoint.getYVector();
         //playerPoint.setY(playerPoint.getY()-playerPoint.getYVector());
               
         horLineOffset=horLineOffset-playerPoint.getYVector();  
          /*  
         for(int i = 0; i<enemyList.size(); i++){
            enemyList.get(i).changeXY(0,-playerPoint.getYVector());
            enemyList.get(i).shiftTarget(0,-playerPoint.getYVector());
         
         
            if(enemyList.get(i).getType()=="Nboo"||enemyList.get(i).getType()=="Boo"||enemyList.get(i).getType()=="Rboo"||enemyList.get(i).getType()=="Aboo"){
               enemyList.get(i).setRememberY(-playerPoint.getYVector());
            }
            
            if(enemyList.get(i).getType()=="Trail"){
               for(int k=0; k<enemyList.get(i).getEnemyTrail().getTrailListSize(); k++){
                  enemyList.get(i).getEnemyTrail().moveTrailListXY(k,0,-playerPoint.getYVector());
                  //g.fillOval(enemyList.get(i).getEnemyTrail().getTrailListXAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailListYAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailDiameter(k),  enemyList.get(i).getEnemyTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
               }
            }
         
         }
         
         //move weapon accordingly
         firstWeapon.changeXY(0,-playerPoint.getYVector());
         secondaryWeapon.changeXY(0,-playerPoint.getYVector());
         if(firstWeapon.getIsTrail()){
            for(int i=0; i<firstWeapon.getTrailListSize(); i++){
               firstWeapon.moveTrailListXY(i, 0,-playerPoint.getYVector()  );
            }
         }
         for(int i = 0; i<secondTrail.getTrailListSize(); i++ ){
            secondTrail.moveTrailListXY(i, 0,-playerPoint.getYVector());
         }
         for(int i=0; i<comboList.size(); i++){
            comboList.get(i).changeXY(0,-playerPoint.getYVector());
            
         }
      */}
      
      playerPoint.camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
      
      for(int i = 0; i<enemyList.size(); i++){
         enemyList.get(i).camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
         /*for(Laser eachLaser : enemyList.get(i).laserList){
                  eachLaser.camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
               }*/
      }
         
         //move weapon accordingly
      for(Weapon each : weaponList){
         each.camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
      }
      //firstWeapon.camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
      //secondaryWeapon.camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
         
         //move trail
         /*
         if(firstWeapon.getIsTrail()){
            for(int i=0; i<firstWeapon.getTrailListSize(); i++){
               firstWeapon.moveTrailListXY(i, -playerPoint.getXVector(),0  );
            }
         }
         */
      /*for(int i = 0; i<secondTrail.getTrailListSize(); i++ ){
         secondTrail.moveTrailListXY(i, -playerPoint.getXVector(),0);
      }*/
         
      for(int i=0; i<comboList.size(); i++){
         comboList.get(i).camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
            
      }
      
      for(int i = 0; i<particleList.size(); i++){
         for(int k = 0; k < particleList.get(i).getPoofNum(); k++){
            particleList.get(i).getPoof(k).camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
         }
         //particleList.get(i).camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
      }
      for(Drop d : dropList){
         d.camera(north, east, south, west, playerPoint.getXVector(), playerPoint.getYVector());
      }
      
      
   }
 
   private void enemyMoveAndAttack() throws InterruptedException{
      /*This is the part of the code where enemies are moved, 
      damage is dealt, and enemies are killed. 
      It does not spawn the enemies, that is enemySpawner. 
      This also governs player invincibility after taking damage from an enemy.*/
      int playerHealthBeforeAttack=playerHealth;
      
      for(int i=0; i<enemyList.size();i++){
         
         boolean healthShouldRemove = false;
         boolean projShouldRemove = false;
         boolean alreadyDead = false;
         if(enemyList.get(i).getIsProjectile() && findDistance(playerPoint.getX(), playerPoint.getY(), enemyList.get(i).getX(),enemyList.get(i).getY())<=enemyList.get(i).getDiameter()/2+playerPoint.getDiameter()/2){
            projShouldRemove = true;
         }
      
         if((!isPlayerInvincible || enemyList.get(i).isHealth)&&enemyList.get(i).isDamaging(playerPoint.getX(), playerPoint.getY(), playerPoint.getDiameter(), isPlayerInvincible)){
            
            if(!enemyList.get(i).isHealth || playerHealth < playerMaxHealth){
               playerHealth+=enemyList.get(i).getDamage();
            }
            
            if(!enemyList.get(i).isHealth){
               isPlayerInvincible = true;
               if(enemyList.get(i).getIsProjectile()){
                  projShouldRemove = true;
               }
            }else{
               healthShouldRemove = true;
            }
            
            
            ParticlePoof myParticles = new ParticlePoof(playerPoint.getX(), playerPoint.getY(), playerPoint.getDiameter(), playerPoint.getColor(), refreshTime);
            particleList.add(myParticles);
            
            
         }else{  
         
         
         }
         
         //asdf.out.println(enemyList.get(i).getX());
         enemyList.get(i).attack(playerPoint.getX(), playerPoint.getY(),playerPoint.getDiameter(), playerHealth,isPlayerInvincible, x,y,weaponDiameter);
         for(Laser l : enemyList.get(i).laserList){
            l.lase();
         }
         
         
         //if(timeInSeconds)
         //asdf.out.println(Enemy.rotateAngleX(1, 1, 30));
         int shouldAddI = 0;
         if(enemyList.get(i).getShoots()){
            enemyList = enemyList.get(i).addProjectiles(enemyList, playerPoint.getX(), playerPoint.getY());
            shouldAddI += enemyList.get(i).numberShot;
            /*
            if(enemyList.get(i).getType() == "Primal Aspid"){
               for(int j = 1; j>=-1; j--){
                  double leftAngle = Math.PI/8.0;
                  Enemy myProjectile = new Enemy("Projectile", enemyList.get(i).getX(), enemyList.get(i).getY(),  masterSpeed, refreshTime);
                  myProjectile.setDiameter(enemyList.get(i).getShootDiameter());
                  myProjectile.rotateProjectileV(j*Math.PI/8.0, playerPoint.getX(), playerPoint.getY());
                  myProjectile.setSpeed(enemyList.get(i).getShootSpeed());
                  enemyList.add(myProjectile);
                  
                  //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                  
                  
                  //asdf.out.println("hmm " + j);
                  numberOfProjectiles++;
               }
            }else if(enemyList.get(i).getType()=="Octo"){
               for(int q = 0; q<8; q++){
                  Enemy myProjectile = new Enemy("Projectile", enemyList.get(i).getX(), enemyList.get(i).getY(),  masterSpeed, refreshTime);
                  myProjectile.setDiameter(enemyList.get(i).getShootDiameter());
                  myProjectile.rotateProjectileV(q*2*Math.PI/8.0, enemyList.get(i).getX()+10, enemyList.get(i).getY());
                  myProjectile.setSpeed(enemyList.get(i).getShootSpeed());
                  enemyList.add(myProjectile);
                  
                  //enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                  
                  
                  //asdf.out.println("hmm " + q);
                  numberOfProjectiles++;
               }
            
            }else{
               Enemy myProjectile = new Enemy("Projectile", enemyList.get(i).getX(), enemyList.get(i).getY(),  masterSpeed, refreshTime);
               enemyList.add(myProjectile);
                enemyList.get(enemyList.size()-1).setDiameter(enemyList.get(i).getShootDiameter());
                enemyList.get(enemyList.size()-1).setSpeed(enemyList.get(i).getShootSpeed());
                numberOfProjectiles++;
            }
            */
            
         }
         //}
         
         //Kill enemy
         
         try{
            for(Weapon eachW : weaponList){
            //System.out.println(eachW.previousX + ", " + eachW.getX());
            
               if(findDistance(eachW.getX(), eachW.getY(), enemyList.get(i).getX(),enemyList.get(i).getY()) <= enemyList.get(i).getDiameter()/2+eachW.getDiameter()/2
                || (helpful.touchesLine(eachW.previousX, eachW.previousY, eachW.getX(), eachW.getY(), enemyList.get(i).getX(), enemyList.get(i).getY(), enemyList.get(i).getDiameter()/2+eachW.getDiameter()/2))
                || helpful.pointTouchesTrail(enemyList.get(i), eachW.getWeaponTrail())){
                  if(helpful.touchesLine(eachW.previousX, eachW.previousY, eachW.getX(), eachW.getY(), enemyList.get(i).getX(), enemyList.get(i).getY(), enemyList.get(i).getDiameter()/2+eachW.getDiameter()/2)){
                     //System.out.println("ajdfhkljasg");
                  }
               //asdf.out.println("nice shot");
               
               
               
                  if(enemyList.get(i).getHealth()<=1){
                  
                     ParticlePoof myParticles = new ParticlePoof(enemyList.get(i).getX(),enemyList.get(i).getY(), enemyList.get(i).getDiameter(), enemyList.get(i).getColor(), refreshTime);
                     particleList.add(myParticles);
                  
                     if(!enemyList.get(i).getIsProjectile()){
                        combo++;
                        ComboNumber myComboNumber = new ComboNumber(enemyList.get(i).getX(),enemyList.get(i).getY(),combo);
                        comboList.add(myComboNumber);
                        if(combo>=minCombo){
                           score+=(enemyList.get(i).getPointValue())*combo;
                        }else{
                           score+=(enemyList.get(i).getPointValue());
                        }
                        enemiesKilled++;
                        
                     }else{
                        numberOfProjectiles--;
                     }
                     if(enemyList.get(i).isBoss){
                        enemyList.get(i).bossDrop.setX(enemyList.get(i).getX());
                        enemyList.get(i).bossDrop.setY(enemyList.get(i).getY());
                        dropList.add(enemyList.get(i).bossDrop);
                        bossPresent = false;
                     }
                     enemyList.remove(i);
                     alreadyDead = true;
                  
                  
                  }else{
                  /*if((findDistance(secondaryWeapon.getX(), secondaryWeapon.getY(), enemyList.get(i).getX(),enemyList.get(i).getY())<=enemyList.get(i).getDiameter()/2+secondaryWeapon.getDiameter()/2)&&(findDistance(firstWeapon.getX(), firstWeapon.getY(), enemyList.get(i).getX(),enemyList.get(i).getY())<=enemyList.get(i).getDiameter()/2+firstWeapon.getDiameter()/2)){
                  enemyList.get(i).changeHealth(2);
                  }else{
                  enemyList.get(i).changeHealth(1);
                  }*/
                     enemyList.get(i).changeHealth(1);
                  }
                              
               }
            /*if((findDistance(secondaryWeapon.getX(), secondaryWeapon.getY(), enemyList.get(i).getX(),enemyList.get(i).getY())<=enemyList.get(i).getDiameter()/2+secondaryWeapon.getDiameter()/2)||(findDistance(firstWeapon.getX(), firstWeapon.getY(), enemyList.get(i).getX(),enemyList.get(i).getY())<=enemyList.get(i).getDiameter()/2+firstWeapon.getDiameter()/2)){
            //asdf.out.println("nice shot");
            
            
            
            if(enemyList.get(i).getHealth()<1){
            
               ParticlePoof myParticles = new ParticlePoof(enemyList.get(i).getX(),enemyList.get(i).getY(), enemyList.get(i).getDiameter(), enemyList.get(i).getColor(), refreshTime);
               particleList.add(myParticles);
               
               if(!enemyList.get(i).getIsProjectile()){
                  combo++;
                  ComboNumber myComboNumber = new ComboNumber(enemyList.get(i).getX(),enemyList.get(i).getY(),combo);
                  comboList.add(myComboNumber);
                  if(combo>=minCombo){
                     score+=(enemyList.get(i).getPointValue())*combo;
                  }else{
                     score+=(enemyList.get(i).getPointValue());
                  }
                  enemiesKilled++;
               }else{
                  numberOfProjectiles--;
               }
               enemyList.remove(i);
               
               
            }else{
               if((findDistance(secondaryWeapon.getX(), secondaryWeapon.getY(), enemyList.get(i).getX(),enemyList.get(i).getY())<=enemyList.get(i).getDiameter()/2+secondaryWeapon.getDiameter()/2)&&(findDistance(firstWeapon.getX(), firstWeapon.getY(), enemyList.get(i).getX(),enemyList.get(i).getY())<=enemyList.get(i).getDiameter()/2+firstWeapon.getDiameter()/2)){
                  enemyList.get(i).changeHealth(2);
               }else{
                  enemyList.get(i).changeHealth(1);
               }
            }
            
            
            
            }*/
            }
         } catch (Exception e){
            System.out.println("aqui");
         }
         
         
         try{
         
            //Despawn far enemies
            int despawnDistance = 2*FRAME_WIDTH;
            if(!alreadyDead){
               if(findDistance(enemyList.get(i).getX(), enemyList.get(i).getY(), playerPoint.getX(), playerPoint.getY()) > despawnDistance){
                  enemyList.remove(i);
               }else if(healthShouldRemove){
                  enemyList.remove(i);
               }else if(projShouldRemove){
                  enemyList.remove(i);
               }
            }
         } catch (Exception e){
            System.out.println("hhere");
         }
         i += shouldAddI;        
      }
      if(playerHealthBeforeAttack>playerHealth){
         isPlayerInvincible=true;
      }
      if(isPlayerInvincible){
         invincibleTime-=refreshTime;
      }
      if(invincibleTime<=0){
         invincibleTime=properInvincibleTime;
         isPlayerInvincible=false;
            //asdf.out.println(isPlayerInvincible);
         
      }
   }
 
   private void weaponBoomerang(){
      
      if(keyboardExample.getMyKeyListener().getPressedChar() == 'r' || circleTapped){
         combo = 0;
         comboList.clear();
         circleTapped = false;
         //asdf.out.println("Huzzah");
         
         //asdf.out.println(keyboardExample.getMyKeyListener().getPressedChar());
         for(Weapon eachW : weaponList){
            eachW.startBoom();
         }
         /*
         weaponReturnStartX=firstWeapon.getX();
         weaponReturnStartY=firstWeapon.getY();
         weaponReturn=true;
         secondWeaponReturn=true;
         secondWeaponReturnStartX=secondaryWeapon.getX();
         secondWeaponReturnStartY=secondaryWeapon.getY();*/
      }
      for(Weapon eachW : weaponList){
         //asdf.out.println(eachW.boomerang(playerPoint.getX(), playerPoint.getY(), eachW.weaponReturnStart));
         
         if(eachW.weaponReturnStart){ 
            eachW.weaponReturnStart = eachW.boomerang(playerPoint.getX(), playerPoint.getY(), eachW.weaponReturnStart);
         }
         
         if(eachW.getHasReturned() || (eachW.x == playerPoint.getX() && eachW.y == playerPoint.getY())){
            numReturned++;
            eachW.weaponReturnStart = false;
            //asdf.out.println(eachW.weaponReturnStart);
            
            //eachW.hasReturned = false;
         }
         
      }
      /*if(numReturned == weaponList.size()){
         System.out.println("Combo Over");
         combo = 0;
         comboList.clear();
      }*
      /*
      secondWeaponReturn = secondaryWeapon.boomerang(playerPoint.getX(), playerPoint.getY(), secondWeaponReturn);
      weaponReturn = firstWeapon.boomerang(playerPoint.getX(), playerPoint.getY(), weaponReturn);
      */
      /*
      if(secondaryWeapon.getHasReturned()||firstWeapon.getHasReturned()){
         combo = 0;
         comboList.clear();
      }
      */
      
      //NO
      //asdf.out.println(combo);
      //weaponReturn=placeholderReturn;
      
      /*
      double returnSpeed=1.5*masterSpeed;
            
      if(weaponReturn&&(x!=playerPoint.getX()||y!=playerPoint.getY())){
               
         x=x+(int)(Math.round(xToStraightVector(x, y, playerPoint.getX(), playerPoint.getY(),returnSpeed,"towards",false, 10)));
         y=y+(int)(Math.round(yToStraightVector(x, y, playerPoint.getX(), playerPoint.getY(),returnSpeed,"towards",false, 10)));
         if(x==playerPoint.getX()){
            weaponXVelocity=0;
         }
               
         if(y==playerPoint.getY()){
            weaponYVelocity=0;
         }
         if(x==playerPoint.getX()&&y==playerPoint.getY()){
            weaponReturn=false;
         }
      
                              
      
      }else{
            
         weaponReturn=false;
      
      }
      */
      //End NO
   }

   private void realEnemySpawner(){
      int enemyDensity;
      if(enemiesKilled<2){
         enemyDensity=1;
      }else{
         enemyDensity = (int)(Math.sqrt(2*enemiesKilled));
      }
      bossShouldSpawn = false;
      for(Enemy eE : bossList){
         if(score>=eE.spawnScore){
            bossShouldSpawn = true;
         }
      }
      if(!bossShouldSpawn && !bossPresent){
      
      if((playerPoint.getFromOriginX()-playerX)>FRAME_WIDTH){
               //int enemyDensity = 10;
         spawnEnemies(FRAME_WIDTH,0,FRAME_WIDTH,FRAME_HEIGHT, enemyDensity);
         playerX=playerPoint.getFromOriginX();
         //asdf.out.println("spawnRight");
         daveTimer = 0;
      }
      if((playerPoint.getFromOriginX()-playerX)<-FRAME_WIDTH){
              
         spawnEnemies(-FRAME_WIDTH,0,FRAME_WIDTH,FRAME_HEIGHT, enemyDensity);
         playerX=playerPoint.getFromOriginX();
         //asdf.out.println("spawnLeft");
         daveTimer = 0;
      
      }
      if((playerPoint.getFromOriginY()-playerY)>FRAME_HEIGHT){
               //int enemyDensity = 10;
         spawnEnemies(0,FRAME_HEIGHT,FRAME_WIDTH,FRAME_HEIGHT, enemyDensity);
         playerY=playerPoint.getFromOriginY();
         //asdf.out.println("spawnDown");
         daveTimer = 0;
      
      }
      if((playerPoint.getFromOriginY()-playerY)<-FRAME_HEIGHT){
              
         spawnEnemies(0,-FRAME_HEIGHT,FRAME_WIDTH,FRAME_HEIGHT, enemyDensity);
         playerY=playerPoint.getFromOriginY();
         //asdf.out.println("spawnUp");
         daveTimer = 0;
      
      }
      }
      if(enemyList.size() == 0){
         daveTimer += refreshTime;
      }
      
      int daveWait = 20000;
      
      if(shouldDave && daveTimer>daveWait && enemyList.size() == 0){
         //Spawns Dave
         spawnEnemy(3*FRAME_WIDTH/2, FRAME_HEIGHT/2, 1, "Dave");
            //shouldDave = false;
         //asdf.out.println(daveTimer);
         daveTimer = 0;
      
      }
      if(bossShouldSpawn){
         for(int eIndex = 0; eIndex < enemyList.size(); eIndex++){
            
            if(enemyList.get(eIndex).getIsProjectile() && (enemyList.get(eIndex).getX()-enemyList.get(eIndex).getDiameter()/2>FRAME_WIDTH || enemyList.get(eIndex).getY()-enemyList.get(eIndex).getDiameter()/2>FRAME_HEIGHT || enemyList.get(eIndex).getX()+enemyList.get(eIndex).getDiameter()/2<0 || enemyList.get(eIndex).getY()+enemyList.get(eIndex).getDiameter()/2<0)){
               enemyList.remove(eIndex);
               eIndex--;
               bossPresent = true;
            }
         }
      }
      /*
      int weinbergScore = 5000;
      if(shouldWeinberg && score>=5000){
         spawnEnemy(FRAME_WIDTH/2, -FRAME_HEIGHT/2, 1, "Weinberg");
         shouldWeinberg = false;
      }*/
      for(int eachBoss = 0; eachBoss<bossList.size(); eachBoss++){
         if(score >= bossList.get(eachBoss).spawnScore && enemyList.size() == 0 && bossList.get(eachBoss).spawnScore>0){
            spawnEnemy(bossList.get(eachBoss).getX(), bossList.get(eachBoss).getY(), 1, bossList.get(eachBoss).getType());
            bossList.remove(eachBoss);
         }
      }
      
   
   }
   public void run() throws InterruptedException, IOException, CloneNotSupportedException {
   
      
      
      myCircle.addMouseListener(new MyCircleMouseListener());
      myCircle.addMouseMotionListener(new MyCircleMouseListener());
      //JFrame frame = new JFrame("Mini Tennis");
      keyboardExample = new KeyboardExample();
      myCircle.add(keyboardExample);
   	//frame.setSize(200, 200);
   	//frame.setVisible(true);
   	//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	//addKeyListener(listener);
   	//setFocusable(true);
      
         
         //planet.addMouseListener(new MyCircleMouseListener());
         //planet.addMouseMotionListener(new MyCircleMouseListener());
         
         
         
         //JFrame frame = new JFrame();
      JFrame secondFrame = new JFrame();
        
        
        //Why does the second replace the first????? SOLVED! :)
        //frame.add(planet);
      secondFrame.add(myCircle);
        
   
      
         
       // frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
   
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        
       // frame.setVisible(true);
        
      secondFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
   
      secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        
      secondFrame.setVisible(true);
      //System.out.println(howDoesThisWork);
      serialize();
      System.out.println();
      //howDoesThisWork = "original";
      //System.out.println(howDoesThisWork);
      
      
      //Container contentPane = secondFrame.getContentPane();
        myTimerThread = new timerThread();
         //myTimerThread.setStart();
         //myTimerThread.time();
         
         int segundos = 0;
         
      while(true){
         Thread.sleep(refreshTime);
         
         if(myTimerThread.every(1000)){
            //System.out.println("Second");
            segundos++;
            //ystem.out.println(segundos);
         }
         try{
            //System.out.println("File should be menu, but it is: " + getSerialFile());
         }catch (Exception e){}
         if(isShowing.equals("Game")){
            runGame(secondFrame);  
         }else if(isShowing.equals("Menu")){

            secondFrame.repaint();
         }else if(isShowing.equals("Credits")){
            secondFrame.repaint();
         }else if(isShowing.equals("Modes")){
            secondFrame.repaint();
         }
         keyboardExample.getMyKeyListener().setChar('`'); 
         //myTimerThread.time();  
      }
      
   
      
   
   }
   public void runGame(JFrame secondFrame) throws InterruptedException{
      if(initializingGame){
         
         //Start weapons
         Weapon firstWeapon = new Weapon("Planet", playerX+100, playerY, playerPoint, FRAME_WIDTH/40, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(firstWeapon);
         /*
         Weapon irstWeapon = new Weapon("Malcolm", playerX-100, playerY, playerPoint, 30, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(irstWeapon);
         Weapon rstWeapon = new Weapon("Malcolm", playerX, playerY+100, playerPoint, 30, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(rstWeapon);
         Weapon stWeapon = new Weapon("Malcolm", playerX, playerY-100, playerPoint, 30, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(stWeapon);
         Weapon tWeapon = new Weapon("Malcolm", Math.round(playerX+(int)(100/Math.sqrt(2))), Math.round(playerY+(int)(100/Math.sqrt(2))), playerPoint, 30, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(tWeapon);
         Weapon Weapon = new Weapon("Malcolm", Math.round(playerX-(int)(100/Math.sqrt(2))), Math.round(playerY-(int)(100/Math.sqrt(2))), playerPoint, 30, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(Weapon);
         Weapon eapon = new Weapon("Malcolm", Math.round(playerX-(int)(100/Math.sqrt(2))), Math.round(playerY+(int)(100/Math.sqrt(2))), playerPoint, 30, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(eapon);
         Weapon apon = new Weapon("Malcolm", Math.round(playerX+(int)(100/Math.sqrt(2))), Math.round(playerY-(int)(100/Math.sqrt(2))), playerPoint, 30, masterSpeed, firstWeaponGravity, teal);
         weaponList.add(apon);
         */
         /*
         Weapon secondaryWeapon = new Weapon("Planet",playerX, playerY, firstWeapon, 30, masterSpeed, secondWeaponGravity, turquoise);
            weaponList.add(secondaryWeapon);
            
            Weapon thirdWeapon = new Weapon("Planet",playerX, playerY, secondaryWeapon, 30, masterSpeed, secondWeaponGravity, Color.GREEN);
            weaponList.add(thirdWeapon);
            
            Color ben = new Color(0x06aeae);
            Weapon fourWeapon = new Weapon("Planet",playerX, playerY, thirdWeapon, 30, masterSpeed, secondWeaponGravity, ben);
            weaponList.add(fourWeapon);
            */
         initializingGame = false;
      }
   /*
            //asdf.out.println(findDistance(mouseX, mouseY, x, y));
            
            //asdf.out.println(findXDistance(mouseX,x)+", "+findYDistance(mouseY,y));
            
            double mouseCircleDistance=findDistance(mouseX, mouseY, x, y);
            
            double adjacent=findXDistance(mouseX,x);
            double opposite=findYDistance(mouseY,y);
            
            double angle = java.lang.Math.asin(opposite/mouseCircleDistance);
            
            if(mouseCircleDistance>weaponBaseSpeed){
               speed=weaponBaseSpeed;
            }else{
               speed=1;
            }
            
            double xDirect = speed*java.lang.Math.cos(angle);
            if(x>mouseX){
               xDirect=-xDirect;
               //speed=0;
               }
            
            double yDirect = speed*java.lang.Math.sin(angle);
             if(y>mouseY){
               yDirect=-yDirect;
               //speed=0;
            }
             
            */
                
      moveWeapon();
                  
         
            //myCircle.repaint();
            //frame.repaint();
            
      movePlayer();
               
        // int playerXVector=(int)(Math.round(xToStraightVector(playerX, playerY, mouseX, mouseY,playerSpeed,"towards",isForcePropDisSqPlayerToMouse, playerDiameter)));
        // int playerYVector=(int)(Math.round(yToStraightVector(playerX, playerY, mouseX, mouseY,playerSpeed,"towards",isForcePropDisSqPlayerToMouse, playerDiameter)));
      enemyMoveAndAttack();
      playerMoveCamera();
         
      //secondTrail.follow((int)(secondaryWeapon.getX()), (int)(secondaryWeapon.getY()));
         //secondTrail.makeTrail(true);
                 
      /*
         if(findDistance(playerX, playerY, FRAME_WIDTH/2, FRAME_HEIGHT/2)> 200&&playerXVector>0){
            x=x-playerXVector;
            playerX=playerX-playerXVector;
            vertLineOffset=vertLineOffset-playerXVector;
         }
         if(findDistance(playerX, playerY, FRAME_WIDTH/2, FRAME_HEIGHT/2)> 200&&playerXVector<0){
            x=x-playerXVector;
            playerX=playerX-playerXVector;
            vertLineOffset=vertLineOffset-playerXVector;
               
         }
         if(findDistance(playerX, playerY, FRAME_WIDTH/2, FRAME_HEIGHT/2)> 200&&playerYVector>0){
            y=y-playerYVector;
            playerY=playerY-playerYVector;
            horLineOffset=horLineOffset-playerYVector; 
               
         }
         if(findDistance(playerX, playerY, FRAME_WIDTH/2, FRAME_HEIGHT/2)> 200&&playerYVector<0){
            y=y-playerYVector;
            playerY=playerY-playerYVector;
               
            horLineOffset=horLineOffset-playerYVector;  
         }
         
         
         */
        /*
        findDistance((playerX, playerY, FRAME_WIDTH/2, FRAME_HEIGHT/2)> 200))
         if(playerX+playerXVector>(findDistance((playerX, playerY, FRAME_WIDTH/2, FRAME_HEIGHT/2)> 200))){
            
            y=y-playerYVector;
            playerY=playerY-playerYVector;
               
            horLineOffset=horLineOffset-playerYVector;
            
            x=x-playerXVector;
            playerX=playerX-playerXVector;
            vertLineOffset=vertLineOffset-playerXVector;
         }
         */
         
      
         
         
                //Boomerang
      weaponBoomerang();
         
           //Enemy Spawns
      realEnemySpawner();  
         
         //Upgrades
         
         //if(score/upgradeScore > 0 && !offerUpgrade){
      
      giveUpgrades();
          
                     
        // System.out.println(playerHealth);
         //playerY=playerY+playerYVector;
         
          
         //Pauses
      if(keyboardExample.getMyKeyListener().getPressedChar() == 'p' && isShowing.equals("Game")){
         isPaused = true;
         keyboardExample.getMyKeyListener().setChar('`');
      }
         
         
         dropStuff();
         //Redraws the shapes
      secondFrame.repaint();
      
      
      
      setPreviouses();
               
      while(isPaused){
         Thread.sleep(refreshTime);
         if(keyboardExample.getMyKeyListener().getPressedChar() == 'p'){
            isPaused = false;
         }
         keyboardExample.getMyKeyListener().setChar('`');
      }
         
      
          //sets the keyboard input to a really useless character so that it now will depend on the next thing typed
      keyboardExample.getMyKeyListener().setChar('`');  
      if(!howDoesThisWork.equals("original")){
         
      }
      //System.out.println(this);
      //System.out.println(howDoesThisWork);
      if(playerHealth <= 0){
         System.out.println("deserialize Time");
         deserialize();
         //Thread.sleep(10000);
      }
   }
   public void dropStuff(){
      for(Drop d : dropList){
         if(helpful.pointCollision(d, playerPoint)){
            Weapon temp = new Weapon(d.info, playerPoint.getX(), playerPoint.getY(), playerPoint, FRAME_WIDTH/40, masterSpeed, firstWeaponGravity, Color.BLUE);
            //temp.orbit(0,0,0);
            if(temp.getType().equals("Malcolm")){
               temp.setX(playerPoint.getX()+FRAME_WIDTH/20);
               temp.setY(playerPoint.getY());
            }else{
               temp.setX(playerPoint.getX());
               temp.setY(playerPoint.getY());
            }
            standbyWeaponList.add(temp);
         }
      }
      for(int d = 0; d<dropList.size(); d++){
         if(helpful.pointCollision(dropList.get(d), playerPoint)){
            dropList.remove(d);
            d--;
         }
      }
      
   }
   public void giveUpgrades(){
      if(score>=upgradeScore && !offerUpgrade){
         offerUpgrade = true;
            
            //upgradeScore *= 2;
            
            
      }    
      if(offerUpgrade){
         if(keyboardExample.getMyKeyListener().getPressedChar() == '1'){
            firstUpgradeChosen = true;
         }else if(keyboardExample.getMyKeyListener().getPressedChar() == '2'){
            secondUpgradeChosen = true;
         } else if (keyboardExample.getMyKeyListener().getPressedChar() == '3'){
            thirdUpgradeChosen = true;
         }
            
         if(firstUpgradeChosen&&numberOfHealthUpgrades<numberOfEachUp){
            playerMaxHealth++;
            playerHealth++;
            offerUpgrade=false;
            numberOfHealthUpgrades++;
                  
            firstUpgradeChosen = false;
            previousUpgradeScore = upgradeScore;
            upgradeScore *= 2;
         }
         if(secondUpgradeChosen&&numberOfSpeedUpgrades<numberOfEachUp){
            
            playerSpeed*=1.2;
            offerUpgrade=false;
            numberOfSpeedUpgrades++;
                  
            secondUpgradeChosen = false;
            previousUpgradeScore = upgradeScore;
            upgradeScore *= 2;
         }
         if(thirdUpgradeChosen&&numberOfSizeUpgrades<numberOfEachUp){
            playerPoint.increaseDiameter(0.8);
            offerUpgrade=false;
            numberOfSizeUpgrades++;
               
            thirdUpgradeChosen = false;
            previousUpgradeScore = upgradeScore;
            upgradeScore *= 2;
         } 
         numTotalUpgrades = numberOfHealthUpgrades + numberOfSpeedUpgrades + numberOfSizeUpgrades;
         if(numTotalUpgrades % 3 == 0){
            owedSpecialUp++;
         } 
      } 
      if(owedSpecialUp > gottenSpecialUp){
         
      }
   }
   public void setPreviouses(){
      for(Weapon every : weaponList){
         every.previousX = every.getX();
         every.previousY = every.getY();
      }
      playerPoint.previousX = playerPoint.getX();
      playerPoint.previousY = playerPoint.getY();
      for(Enemy every : enemyList){
         every.previousX = every.getX();
         every.previousY = every.getY();
      }
   
   }
 

   class MyCircle extends JComponent {
      public void fillHeart(Graphics g, int x, int y, int width, int height) {
         double fixFactor = width/20.0;
         int[] triangleX = {
            (int)(x - 2*width/18.0+(int)fixFactor+1),
            (int)(x + width + 2*width/18.0-(int)fixFactor),
            (int)((x - 2*width/18.0 + x + width + 2*width/18.0)/2.0)};
         int[] triangleY = { 
            y + height - 2*height/3, 
            y + height - 2*height/3, 
            y + height };
         g.fillOval(
            x - width/12,
            y, 
            width/2 + width/6, 
            height/2); 
         g.fillOval(
            x + width/2 - width/12,
            y,
            width/2 + width/6,
            height/2);
         g.fillPolygon(triangleX, triangleY, triangleX.length);
      }
    
      /*public void drawHeart(Graphics g, int heartX, int heartY, int heartSize){
         int heartHeight = heartSize*5/8;
         int heartWidth = heartSize*5/8;
         int heartStartX = heartX+heartWidth/3;
         int heartStartY = heartY+heartHeight/3;
         int fixFactorX = 0;
         int fixFactorY= heartSize/13;
         
            
         int[] triangleX = {
               heartStartX - heartWidth/12-fixFactorX,
               heartStartX + heartWidth/2 - heartWidth/12+heartWidth/2 + heartWidth/6+fixFactorX,
               (heartStartX - 2*heartWidth/18 + heartStartX + heartWidth + 2*heartWidth/18)/2};
         int[] triangleY = { 
               heartStartY + heartHeight/4+fixFactorY, 
               heartStartY + heartHeight/4+fixFactorY, 
               heartStartY + heartHeight };
            
             
         g.fillOval(
               heartStartX + heartWidth/2 - heartWidth/12,
               heartStartY,
               heartWidth/2 + heartWidth/6,
               heartHeight/2);
         
         g.fillOval(
               heartStartX - heartWidth/12,
               heartStartY, 
               heartWidth/2 + heartWidth/6, 
               heartHeight/2); 
         g.fillPolygon(triangleX, triangleY, triangleX.length);
            //g.fillPolygon();
            
         heartStartX+=heartSeparation+heartWidth;
            
            
         
      }*/
      
      public void gridLayer(Graphics g){
      
       // g.setColor(Color.WHITE);
       // g.fillRect(0,0, FRAME_WIDTH, FRAME_HEIGHT);
         Color superLightGray = new Color(0xe0e0e0);
         g.setColor(superLightGray);
        
         boolean secondGrid = false;
         if(secondGrid && !photoMode){
         
         
         
            for (int i = 0; i < FRAME_WIDTH; i=i+gridSeparation/2) {
            
               /*if(i+vertLineOffset>FRAME_WIDTH){
                  vertLineOffset=vertLineOffset%FRAME_WIDTH-i;
                  i=0;
               }
               */
               if(i+vertLineOffset<0){
                  vertLineOffset=vertLineOffset+FRAME_WIDTH;
               }
               
            
               g.drawLine((i+(int)vertLineOffset/2)%FRAME_WIDTH,0, (i+(int)vertLineOffset/2)%FRAME_WIDTH, FRAME_HEIGHT);
            
            }
               //Put horizontal lines
            for (int i = 0; i < FRAME_HEIGHT; i=i+gridSeparation/2) {
               /*if(i+horLineOffset>FRAME_HEIGHT){
                  horLineOffset=horLineOffset%FRAME_HEIGHT-i;
                  i=0;
               }
               */
                          
               if(i+horLineOffset<0){
                  horLineOffset=horLineOffset+FRAME_HEIGHT;
               }
               g.drawLine(0,(i+(int)horLineOffset/2)%FRAME_HEIGHT, FRAME_WIDTH, (i+(int)horLineOffset/2)%FRAME_HEIGHT);
            
            }
         }
         if(!photoMode){
            g.setColor(Color.LIGHT_GRAY);
                  //Put vertical lines
            for (int i = 0; i < FRAME_WIDTH; i=i+gridSeparation) {
               /*if(i+vertLineOffset>FRAME_WIDTH){
                  vertLineOffset=vertLineOffset%FRAME_WIDTH-i;
                  i=0;
               }
               */
               if(i+vertLineOffset<0){
                  vertLineOffset=vertLineOffset+FRAME_WIDTH;
               }
               
            
               g.drawLine((i+(int)vertLineOffset)%FRAME_WIDTH,0, (i+(int)vertLineOffset)%FRAME_WIDTH, FRAME_HEIGHT);
               
            }
            
                  //Put horizontal lines
            for (int i = 0; i < FRAME_HEIGHT; i=i+gridSeparation) {
               /*if(i+horLineOffset>FRAME_HEIGHT){
                  horLineOffset=horLineOffset%FRAME_HEIGHT-i;
                  i=0;
               }
               */
                          
               if(i+horLineOffset<0){
                  horLineOffset=horLineOffset+FRAME_HEIGHT;
               }
               g.drawLine(0,(i+(int)horLineOffset)%FRAME_HEIGHT, FRAME_WIDTH, (i+(int)horLineOffset)%FRAME_HEIGHT);
            
            }
         
         }
      }
      
      public void GUILayer (Graphics g){
         //Put GUI
         /*
         g.drawRect(FRAME_WIDTH-100,75,75,75);
         g.drawRect(FRAME_WIDTH-100,160,75,75);
         g.drawRect(FRAME_WIDTH-100,270,75,75);
         g.drawRect(FRAME_WIDTH-100,355,75,75);
         g.drawRect(FRAME_WIDTH-100,440,75,75);
         g.drawRect(FRAME_WIDTH-100,525,75,75);
         g.drawRect(FRAME_WIDTH-100,610,75,75);
         */
         
         g.setColor(Color.BLACK);
         int nextUpMetricBar = 3*FRAME_WIDTH/5;
         int rightBufferUpMetricBar = (FRAME_WIDTH-nextUpMetricBar)/2;
         int metricBarY = 35;
         g.drawRect(rightBufferUpMetricBar, metricBarY,nextUpMetricBar, 10);
         
         g.setColor(Color.GREEN);
         //System.out.println(upgradeScore-previousUpgradeScore);
         //asdf.out.println((int)((((double)score-previousUpgradeScore)/upgradeScore)));
         if(score<upgradeScore){
            if(upgradeScore == 50){
               g.fillRect(rightBufferUpMetricBar, metricBarY, (int)((double)nextUpMetricBar*(((double)score-previousUpgradeScore)/upgradeScore)), 10);
            }else{
               g.fillRect(rightBufferUpMetricBar, metricBarY, (int)(2*nextUpMetricBar*(((double)score-previousUpgradeScore)/upgradeScore)), 10);
            }
         }else{
            g.setColor(Color.YELLOW);
            g.fillRect(rightBufferUpMetricBar, metricBarY,nextUpMetricBar, 10);
         }
         g.setColor(Color.BLACK);
         
         g.fillRect(0,0,FRAME_WIDTH, GUIHeight);
         
         //Put Health Hearts
         
         Color betterPink = new Color(0xff5070);
         
         
         pinkTimer+=refreshTime;
         
         int timePink = 2*refreshTime;
         int timeRed = 2*refreshTime;
         
         
         if(pinkTimer<timePink&&isPlayerInvincible){
            g.setColor(betterPink);
            Color transBlue = new Color(0,0,255,0);
            playerPoint.setItsColor(transBlue);
            //playerPoint.setDiameter(2*playerPoint.getDiameter());
         }else{
            g.setColor(Color.RED);
            playerPoint.setItsColor(Color.BLUE);
            
            //playerPoint.setDiameter(playerPoint.getOgDiameter());
            
            //playerPoint.setDiameter(playerPoint.getDiameter()*2);
         }
         
         if(pinkTimer>timeRed+timePink){
            
            pinkTimer=0;
         }
         /*
         
         if(isPlayerInvincible){
            heartFlash = !heartFlash;
            
            
         }
         
         if(heartFlash){
            
            g.setColor(betterPink);
            heartFlash = !heartFlash;
         }else{
            g.setColor(Color.RED);
            timePink=originalTimePink;
         }*/
         
         
         int heartHeight = 25;
         int heartWidth = 25;
         int heartStartX = heartSeparation;
         int heartStartY = 2;
         int fixFactorX = 0;
         int fixFactorY= 2;
         for (int i = 0; i < playerMaxHealth; i++) {
            if(i>=playerHealth){
               g.setColor(Color.GRAY);
            
            }
            int[] triangleX = {
               heartStartX - heartWidth/12-fixFactorX,
               heartStartX + heartWidth/2 - heartWidth/12+heartWidth/2 + heartWidth/6+fixFactorX,
               (heartStartX - 2*heartWidth/18 + heartStartX + heartWidth + 2*heartWidth/18)/2};
            int[] triangleY = { 
               heartStartY + heartHeight/4+fixFactorY, 
               heartStartY + heartHeight/4+fixFactorY, 
               heartStartY + heartHeight };
            
             
            g.fillOval(
               heartStartX + heartWidth/2 - heartWidth/12,
               heartStartY,
               heartWidth/2 + heartWidth/6,
               heartHeight/2);
         
            g.fillOval(
               heartStartX - heartWidth/12,
               heartStartY, 
               heartWidth/2 + heartWidth/6, 
               heartHeight/2); 
            g.fillPolygon(triangleX, triangleY, triangleX.length);
            //g.fillPolygon();
            
            heartStartX+=heartSeparation+heartWidth;
            
            
         }
         // Weapon return timer
         
         weaponTimer(g);
         
         //Score
         int fontSize = 30;
         g.setFont(new Font("Impact", Font.PLAIN, fontSize));
         String scoreString = ("Enemies killed: " + enemiesKilled + " Score: " + score);
         if(textBoolean){
            g.setColor(Color.GREEN);
            
            
            g.drawString(scoreString, heartSeparation+(heartSeparation+heartWidth)*playerMaxMaxHealth, 26);
            
            //Player's position
            g.setColor(Color.BLACK);
            g.drawString("("+playerPoint.getFromOriginX()+", " +playerPoint.getFromOriginY()+")", heartSeparation,FRAME_HEIGHT-fontSize-heartSeparation);
            
            //Number of enemies
            int notProjectiles = 0;
            for(Enemy each : enemyList){
               if(!each.getIsProjectile() && !each.isHealth){
                  notProjectiles++;
               }
               
            }
            g.drawString("Number of enemies: "+(/*enemyList.size()-numberOfProjectiles*/notProjectiles), heartSeparation,FRAME_HEIGHT-fontSize-2*heartSeparation-fontSize);
         
            //Timer
            makeTimer(g);
         }
         //Show the player's upgrades
         g.setColor(Color.RED);
         fillHeart(g, g.getFontMetrics().stringWidth(scoreString)+2*heartSeparation+(heartSeparation+heartWidth)*playerMaxMaxHealth, 2, 8, 8);
         
         for(int i = 0; i<playerMaxMaxHealth-playerStartHealth; i++){
            if(i>= numberOfHealthUpgrades){
               g.setColor(Color.GRAY);
            }
            g.fillOval(12+12*i+g.getFontMetrics().stringWidth(scoreString)+2*heartSeparation+(heartSeparation+heartWidth)*playerMaxMaxHealth, 2, 8, 8);
         }
         
         //Speed metric
         g.setColor(Color.YELLOW);
         for(int i = 0; i<numberOfEachUp; i++){
            if(i>= numberOfSpeedUpgrades){
               g.setColor(Color.GRAY);
            }
            g.fillOval(12+12*i+g.getFontMetrics().stringWidth(scoreString)+2*heartSeparation+(heartSeparation+heartWidth)*playerMaxMaxHealth, 11, 8, 8);
         }
         //Size metric
         g.setColor(Color.BLUE);
         for(int i = 0; i<numberOfEachUp; i++){
            if(i>= numberOfSizeUpgrades){
               g.setColor(Color.GRAY);
            }
            g.fillOval(12+12*i+g.getFontMetrics().stringWidth(scoreString)+2*heartSeparation+(heartSeparation+heartWidth)*playerMaxMaxHealth, 20, 8, 8);
         }
         //Compass
         enemyCompass(g, 25,FRAME_HEIGHT-225,100,10);
         
         //Special Upgrades
         specialUpgrades(g);
         //Pause stuff
         playPause(g);
         
      }
      public void specialUpgrades(Graphics g){
         for(Button eB : specialUpButtonList){
            g.drawRect(eB.getX(), eB.getY(), eB.getWidth(), eB.getHeight());
            g.drawImage(eB.icon, eB.getX(), eB.getY(), eB.getWidth(), eB.getHeight(), null);
         }
      }
      
      public void weaponTimer(Graphics g){
         boolean weaponReturnTimer = true;
         
         for(int i = 0; i<weaponList.size(); i++){
            g.setColor(weaponList.get(i).getColor());
         
            int weaponReturnDiameter=25-(6*i);
            int weaponReturnTimerX=FRAME_WIDTH-25-heartSeparation;
            int weaponReturnTimerY=2;
            int startDistanceWeaponReturn=(int)Math.round(findDistance(weaponList.get(i).getReturnStartX(),weaponList.get(i).getReturnStartY(),playerPoint.getX(),playerPoint.getY()));
            int currentDistanceWeaponReturn=(int)Math.round(findDistance(weaponList.get(i).getX(),weaponList.get(i).getY(),playerPoint.getX(),playerPoint.getY()));
            int returnTimeToDegrees;
            if(startDistanceWeaponReturn>0){
               returnTimeToDegrees= (int)Math.round(360*currentDistanceWeaponReturn/startDistanceWeaponReturn);
            }else{
               returnTimeToDegrees=360;
            }
         
         // g.fillArc(weaponReturnTimerX,weaponReturnTimerY, weaponReturnDiameter, weaponReturnDiameter, 90, 360 );
         //g.setColor(Color.BLACK);
            if(weaponList.get(i).weaponReturnStart){
               g.fillArc(weaponReturnTimerX+(6*i/2),weaponReturnTimerY+(6*i/2), weaponReturnDiameter, weaponReturnDiameter, 90, returnTimeToDegrees );
            //asdf.out.println("Change");
            }else{
               g.fillArc(weaponReturnTimerX+(6*i/2),weaponReturnTimerY+(6*i/2), weaponReturnDiameter, weaponReturnDiameter, 90, 360 );
            }
         }
         /*
         g.setColor(firstWeapon.getColor());
      
         int weaponReturnDiameter=25;
         int weaponReturnTimerX=FRAME_WIDTH-weaponReturnDiameter-heartSeparation;
         int weaponReturnTimerY=2;
         int startDistanceWeaponReturn=(int)Math.round(findDistance(weaponReturnStartX,weaponReturnStartY,playerPoint.getX(),playerPoint.getY()));
         int currentDistanceWeaponReturn=(int)Math.round(findDistance(firstWeapon.getX(),firstWeapon.getY(),playerPoint.getX(),playerPoint.getY()));
         int returnTimeToDegrees;
         if(startDistanceWeaponReturn>0){
            returnTimeToDegrees= (int)Math.round(360*currentDistanceWeaponReturn/startDistanceWeaponReturn);
         }else{
            returnTimeToDegrees=360;
         }
         
        // g.fillArc(weaponReturnTimerX,weaponReturnTimerY, weaponReturnDiameter, weaponReturnDiameter, 90, 360 );
        //g.setColor(Color.BLACK);
         if(weaponReturn){
            g.fillArc(weaponReturnTimerX,weaponReturnTimerY, weaponReturnDiameter, weaponReturnDiameter, 90, returnTimeToDegrees );
         }else{
            g.fillArc(weaponReturnTimerX,weaponReturnTimerY, weaponReturnDiameter, weaponReturnDiameter, 90, 360 );
         }
      */   
         
         
         // 2nd Weapon return timer
         /*
         g.setColor(secondaryWeapon.getColor());
      
         int weaponReturnDiameter2=15;
         int weaponReturnTimerX2=FRAME_WIDTH-weaponReturnDiameter2-3*heartSeparation/2;
         int weaponReturnTimerY2=weaponReturnDiameter2/2;
         int startDistanceWeaponReturn2=(int)Math.round(findDistance(secondWeaponReturnStartX,secondWeaponReturnStartY,playerPoint.getX(),playerPoint.getY()));
         int currentDistanceWeaponReturn2=(int)Math.round(findDistance(secondaryWeapon.getX(),secondaryWeapon.getY(),playerPoint.getX(),playerPoint.getY()));
         int returnTimeToDegrees2;
         if(startDistanceWeaponReturn2>0){
            returnTimeToDegrees2= (int)Math.round(360*currentDistanceWeaponReturn2/startDistanceWeaponReturn2);
         }else{
            returnTimeToDegrees2=360;
         }
         
        // g.fillArc(weaponReturnTimerX,weaponReturnTimerY, weaponReturnDiameter, weaponReturnDiameter, 90, 360 );
        //g.setColor(Color.BLACK);
         if(secondWeaponReturn){
            g.fillArc(weaponReturnTimerX2,weaponReturnTimerY2, weaponReturnDiameter2, weaponReturnDiameter2, 90, returnTimeToDegrees2 );
         }else{
            g.fillArc(weaponReturnTimerX2,weaponReturnTimerY2, weaponReturnDiameter2, weaponReturnDiameter2, 90, 360 );
         }
         */
      }
      
      public void makeTimer(Graphics g){
         if(((int)timeInSeconds/1000)%60<10){
            g.drawString("Time: "+((int)timeInSeconds/60000)%60+":0"+((int)timeInSeconds/1000)%60+":"+(int)timeInSeconds%1000, heartSeparation, GUIHeight+3*heartSeparation);
         }else{
            g.drawString("Time: "+((int)timeInSeconds/60000)%60+":"+((int)timeInSeconds/1000)%60+":"+(int)timeInSeconds%1000, heartSeparation, GUIHeight+3*heartSeparation);
         }
         timeInSeconds += refreshTime;
         
         if(timeInSeconds==5000){
            //asdf.out.println("timer: "+timeInSeconds);
         }
      }
      
      public void enemyCompass(Graphics g, int x, int y, int diameter, int thickness){
         g.setColor(Color.BLACK);
         g.drawOval(x,y,diameter,diameter);
         for(int i = 0; i<enemyList.size(); i++){
         //int i = 0;
            if(enemyList.get(i).getDistance()>0 && (!enemyList.get(i).getIsProjectile())){
            
               g.setColor(enemyList.get(i).getColor());
            
               double angle = (180/(Math.PI))*(Math.asin((((playerPoint.getY() - enemyList.get(i).getY())))/enemyList.get(i).getDistance()));
            
            
            // if(enemyList.get(i).getY()<playerPoint.getY()){
            
            //}else{
               //angle = -angle;
            //}
            
               int start = 0;
            
               angle %= 360;
            
               if(enemyList.get(i).getX()>playerPoint.getX()){
                  //start = 0;
               }else{
               
                  //start = 180;
                  angle = 180-angle;
               
               
               
               
               }
            
            //Why is this necessary?
            
               if((angle<=45&&angle>=-45)|| (angle>=135 && angle <= 225)){
               
               }else{
                  angle = (180/Math.PI)*(Math.acos((((-playerPoint.getX() + enemyList.get(i).getX())))/enemyList.get(i).getDistance()));
               
                  if(enemyList.get(i).getY()>playerPoint.getY()){
                  //start = 0;
                     angle = -angle;
                  }
               
               }
            
                        
            
               g.fillArc(x,y, diameter,diameter, (int)angle-thickness/2,thickness);
               
            
            //asdf.out.println("angle "+angle);
            }
            /*
            if(enemyList.get(i).getY()<playerPoint.getY()){
               
               
               if(enemyList.get(i).getX()>playerPoint.getX()){
                  g.fillArc(100,100, 100,100, (int)angle,(int)angle);
               }else{
                  g.fillArc(100,100, 100,100, 180-(int)angle, 180-(int)angle);
               }
               
            }else{
               
               
               if(enemyList.get(i).getX()>playerPoint.getX()){
                  g.fillArc(100,100, 100,100, -(int)angle,-(int)angle);
               }else{
                  g.fillArc(100,100, 100,100, 180-(int)angle, 180-(int)angle);
               }
            }
            */
            
            
         }
      }
      public void fillPoof(Graphics g){
         for(int i = 0; i < particleList.size(); i++){
            
            particleList.get(i).happen();
            if(particleList.get(i).getTransparency()<=0){
               particleList.remove(i);
            }else{
            
               for(int k = 0; k < particleList.get(i).getPoofNum(); k++){
                  g.setColor(particleList.get(i).getColor());
                  g.fillOval(particleList.get(i).getPoof(k).getX()-particleList.get(i).getPoof(k).getDiameter()/2,
                     particleList.get(i).getPoof(k).getY()-particleList.get(i).getPoof(k).getDiameter()/2,
                     particleList.get(i).getPoof(k).getDiameter(),
                     particleList.get(i).getPoof(k).getDiameter());
               }
            }
         }
      }
      
      public void playPause(Graphics g){
         g.setColor(Color.GRAY);
         boolean wantShapes = photoMode;
         if(isPaused  && !photoMode){
            
            int[] playTriangleX = { pauseX, pauseX, pauseX+pauseSpacing+pauseHalfWidth };
            int[] playTriangleY = { pauseY, pauseY+pauseHalfHeight, pauseY+pauseHalfHeight/2 };
            g.drawPolygon(playTriangleX, playTriangleY, playTriangleX.length);
            
            Color transparentGray = new Color(100,100,100,50);
            g.setColor(transparentGray);
            g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
         }else{
            g.drawRect(pauseX, pauseY, pauseHalfWidth, pauseHalfHeight);
            g.drawRect(pauseX+pauseSpacing, pauseY, pauseHalfWidth, pauseHalfHeight);
         }
      }
   
      public void drawGame(Graphics g){
         
         if(initializeImages){
            try{
               shrinkImage = ImageIO.read(new File("shrink icon.png"));
               shrinkImage = resize(shrinkImage, upWidth, upHeight);
                  //g.drawImage(shrinkImage, thirdUpRectX + upWidth/4, upRectY+upHeight/4, null);
            }catch (IOException e){System.out.println("heere");
               shrinkImage = null;
               //asdf.out.println("Lol no");
            }
            try{
               speedUpImage = ImageIO.read(new File("arrowOutline.png"));
               speedUpImage = resize(speedUpImage, 3*upWidth/4, 3*upHeight/4);
                  //g.drawImage(shrinkImage, thirdUpRectX + upWidth/4, upRectY+upHeight/4, null);
            }catch (IOException e){System.out.println("heeere");
               speedUpImage = null;
               //asdf.out.println("Lol no");
            }
            

            initializeImages = false;
         }
         gridLayer(g);
      
            //Put Grid
                     //g.fillOval(firstEnemyPoint.getX(), firstEnemyPoint.getY(), firstEnemyPoint.getDiameter(), firstEnemyPoint.getDiameter());            
       // firstEnemyPoint.attack(playerPoint.getX(), playerPoint.getY(),playerPoint.getDiameter(), playerHealth,isPlayerInvincible, x,y,weaponDiameter);
         //Put PLAYER
         //playerPoint.drawIt(g);
         //g.setColor(Color.BLUE);         
         
         //private Color teal = new Color(0,153, 175, 100);
            
         g.setColor(playerPoint.getColor());
         g.fillOval(playerPoint.getX()-playerPoint.getDiameter()/2, playerPoint.getY()-playerPoint.getDiameter()/2, playerPoint.getDiameter(), playerPoint.getDiameter());
         
         
         //Put POOF
         fillPoof(g);
         //Put Projectiles
         for(int i=0; i<enemyList.size(); i++){
            if(enemyList.get(i).getIsProjectile()){
                    g.setColor(enemyList.get(i).getColor());

                    g.fillOval(enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2,enemyList.get(i).getY()-enemyList.get(i).getDiameter()/2, enemyList.get(i).getDiameter(),enemyList.get(i).getDiameter());

            }
         }
         //Put ENEMY
         for(int i=0; i<enemyList.size(); i++){
            if(!enemyList.get(i).getIsProjectile()){
            g.setColor(enemyList.get(i).getColor());
            
            if(enemyList.get(i).getType().equals("Trail")){
               for(int k=0; k<enemyList.get(i).getEnemyTrail().getTrailListSize(); k++){
                  
                  try{
                     g.fillOval(enemyList.get(i).getEnemyTrail().getTrailListXAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailListYAt(k)- enemyList.get(i).getEnemyTrail().getTrailDiameter(k)/2, enemyList.get(i).getEnemyTrail().getTrailDiameter(k),  enemyList.get(i).getEnemyTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
                  } catch (Exception e){
                     System.out.println("aquii");
                  }
               }
            }
            
                       
            try{
               g.fillOval(enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2,enemyList.get(i).getY()-enemyList.get(i).getDiameter()/2, enemyList.get(i).getDiameter(),enemyList.get(i).getDiameter());
            
               if(enemyList.get(i).getSwoop()){
               
                  int targetDiameter=10;
                  g.drawOval(enemyList.get(i).getTargetX()-targetDiameter/2,enemyList.get(i).getTargetY()-targetDiameter/2,targetDiameter,targetDiameter);
               }
               if((enemyList.get(i).isBackSwoop || enemyList.get(i).everBackSwoop) && (enemyList.get(i).getTargetX2()!=0 || enemyList.get(i).getTargetY2()!=0) && (enemyList.get(i).getTargetX()!=0 || enemyList.get(i).getTargetY()!=0)){
               
                  int targetDiameter=10;
                  g.drawOval(enemyList.get(i).getTargetX()-targetDiameter/2,enemyList.get(i).getTargetY()-targetDiameter/2,targetDiameter,targetDiameter);
                  g.drawLine(enemyList.get(i).getX(),enemyList.get(i).getY(), enemyList.get(i).getTargetX2(), enemyList.get(i).getTargetY2());
               }
            
               if(enemyList.get(i).getMaxHealth() > 1){
                  int healthBarWidth=5;
                  int healthBarHeight = 5;
                  g.fillRect(enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2,enemyList.get(i).getY()+enemyList.get(i).getDiameter()/2+1, (int)(enemyList.get(i).getDiameter()*((double)enemyList.get(i).getHealth()/enemyList.get(i).getMaxHealth())), healthBarHeight);
                  g.setColor(Color.BLACK);
                  g.drawRect(enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2,enemyList.get(i).getY()+enemyList.get(i).getDiameter()/2+1,enemyList.get(i).getDiameter(), healthBarHeight);
                  g.setColor(enemyList.get(i).getColor());
               }
            
               if(enemyList.get(i).getType().equals("Sniper")){
                  g.drawLine(enemyList.get(i).getX(), enemyList.get(i).getY(), playerPoint.getX(), playerPoint.getY());
                  int targetDiameter = 10;
                  g.drawOval(playerPoint.getX()-targetDiameter/2, playerPoint.getY()-targetDiameter/2,targetDiameter,targetDiameter);
               }
            
               if(enemyList.get(i).getType().equals("Health")){
                  g.setColor(Color.BLUE);
                  fillHeart(g, enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2,enemyList.get(i).getY()-enemyList.get(i).getDiameter()/2, enemyList.get(i).getDiameter(),enemyList.get(i).getDiameter());
               }
               boolean allWein = false;
               g.drawImage(enemyList.get(i).face, enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2+enemyList.get(i).imageExtraX+enemyList.get(i).imageFacePlayerX, enemyList.get(i).getY()-enemyList.get(i).getDiameter()/2+enemyList.get(i).imageExtraY+enemyList.get(i).imageFacePlayerY, null);
            
               if(enemyList.get(i).getType().equals("Weinberg") || allWein){
                  //weinbergImage = resize(weinbergImage, enemyList.get(i).getDiameter(), enemyList.get(i).getDiameter());
                  //g.drawImage(weinbergImage, enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2, enemyList.get(i).getY()-enemyList.get(i).getDiameter()/2-15, null);
                  //g.drawImage(enemyList.get(i).face, enemyList.get(i).getX()-enemyList.get(i).getDiameter()/2, enemyList.get(i).getY()-enemyList.get(i).getDiameter()/2+enemyList.get(i).imageExtraY, null);
               
               
               
               } 
               for(Laser eachLaser : enemyList.get(i).laserList){
                  eachLaser.draw(g);
               }      
            }catch (Exception e){
               System.out.println("mitt");
            } 
            }
         }
         

         
         
         
         //Put TRAIL
         for(Weapon eachW : weaponList){
            try{
            if(eachW.getHasTrail()){
               g.setColor(eachW.getColor());
            /*
            for(int i=0; i<firstWeapon.getTrailListSize(); i++){
               g.fillOval(firstWeapon.getTrailListXAt(i)-firstWeapon.getTrailDiameter(i)/2,firstWeapon.getTrailListYAt(i)-firstWeapon.getTrailDiameter(i)/2, firstWeapon.getTrailDiameter(i),firstWeapon.getTrailDiameter(i));
               
            
            }
            */
               for(int k=0; k<eachW.getWeaponTrail().getTrailListSize(); k++){
                  
                  try{
                     g.fillOval(eachW.getWeaponTrail().getTrailListXAt(k)- eachW.getWeaponTrail().getTrailDiameter(k)/2, eachW.getWeaponTrail().getTrailListYAt(k)- eachW.getWeaponTrail().getTrailDiameter(k)/2, eachW.getWeaponTrail().getTrailDiameter(k),  eachW.getWeaponTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
                  } catch (Exception e){
                     System.out.println("yooour out");
                  }
               }
            }
            }catch(Exception e){System.out.println("how many of these did I use?");}
         }
         /*
         if(firstWeapon.getHasTrail()){
            g.setColor(Color.BLUE);
            
            for(int k=0; k<firstWeapon.getWeaponTrail().getTrailListSize(); k++){
                  
               try{
                  g.fillOval(firstWeapon.getWeaponTrail().getTrailListXAt(k)- firstWeapon.getWeaponTrail().getTrailDiameter(k)/2, firstWeapon.getWeaponTrail().getTrailListYAt(k)- firstWeapon.getWeaponTrail().getTrailDiameter(k)/2, firstWeapon.getWeaponTrail().getTrailDiameter(k),  firstWeapon.getWeaponTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
               } catch (Exception e){
                     
               }
            }
         }
         */
         /*
         if(secondaryWeapon.getHasTrail()){
            g.setColor(secondaryWeapon.getColor());
            /*
            for(int i=0; i<firstWeapon.getTrailListSize(); i++){
               g.fillOval(firstWeapon.getTrailListXAt(i)-firstWeapon.getTrailDiameter(i)/2,firstWeapon.getTrailListYAt(i)-firstWeapon.getTrailDiameter(i)/2, firstWeapon.getTrailDiameter(i),firstWeapon.getTrailDiameter(i));
               
            
            }
            *//*
            for(int k=0; k<secondaryWeapon.getWeaponTrail().getTrailListSize(); k++){
                  
               try{
                  g.fillOval(secondaryWeapon.getWeaponTrail().getTrailListXAt(k)- secondaryWeapon.getWeaponTrail().getTrailDiameter(k)/2, secondaryWeapon.getWeaponTrail().getTrailListYAt(k)- secondaryWeapon.getWeaponTrail().getTrailDiameter(k)/2, secondaryWeapon.getWeaponTrail().getTrailDiameter(k),  secondaryWeapon.getWeaponTrail().getTrailDiameter(k) );
                  //asdf.out.println("Run "+enemyList.get(i).getEnemyTrail());
               } catch (Exception e){
                     
               }
            }
         }
         */
         
         //Put Drops
         //System.out.println(dropList.size());
         for(Drop d : dropList){
            g.setColor(d.getColor());
            g.fillOval(d.getX()-d.getDiameter()/2, d.getY()-d.getDiameter()/2, d.getDiameter(),d.getDiameter());
         }
         
         //Put WEAPON
         //Color teal = new Color(0x0099af);
         for(Weapon eachW : weaponList){
            g.setColor(eachW.getColor());
         
            g.fillOval(eachW.getX()-eachW.getDiameter()/2, eachW.getY()-eachW.getDiameter()/2, eachW.getDiameter(), eachW.getDiameter());
         
         
         }
         for(int w = 0; w<standbyWeaponList.size(); w++){
            weaponList.add(standbyWeaponList.get(w));
            
         }
         standbyWeaponList.clear();
         /*
         g.setColor(firstWeapon.getColor());
         
         
         
         //g.drawOval(x-weaponDiameter/2, y-weaponDiameter/2, weaponDiameter, weaponDiameter);
      
         //g.fillOval(x-weaponDiameter/2, y-weaponDiameter/2, weaponDiameter, weaponDiameter);
         
         g.fillOval(firstWeapon.getX()-firstWeapon.getDiameter()/2, firstWeapon.getY()-firstWeapon.getDiameter()/2, firstWeapon.getDiameter(), firstWeapon.getDiameter());
      
         g.setColor(secondaryWeapon.getColor());
         
         g.fillOval(secondaryWeapon.getX()-secondaryWeapon.getDiameter()/2, secondaryWeapon.getY()-secondaryWeapon.getDiameter()/2, secondaryWeapon.getDiameter(), secondaryWeapon.getDiameter());
      */
      
        // g.fillOval(x-weaponDiameter/2, y-weaponDiameter/2, weaponDiameter, weaponDiameter);
      
         
         
         
         
         //Put comboNumbers
         if(textBoolean){
            if(combo>=minCombo){
               g.setColor(Color.BLUE);
               for(int i=0; i<comboList.size(); i++){
                  g.drawString(""+comboList.get(i).getValue(),comboList.get(i).getX(),comboList.get(i).getY());
                  
               }
            }
         }
            //Put Projectiles
         try{
              // g.fillOval(firstEnemy.getFirstProjectileX(),firstEnemy.getFirstProjectileY(), 10,10);
              
         }catch(Exception e){
               System.out.println("too many");
         }
         
         //Put text, but it takes forever to load
         
         
         g.setColor(Color.BLACK);
         if(offerUpgrade){
            g.drawRect(firstUpRectX, upRectY, upWidth, upHeight);
            //Heart Icon
            g.setColor(Color.RED);
            fillHeart(g, firstUpRectX+upWidth/4, upRectY+upHeight/4, upWidth/2, upHeight/2);
            
            g.setColor(Color.BLACK);
            g.drawRect(secondUpRectX, upRectY, upWidth, upHeight);
            g.drawImage(speedUpImage, secondUpRectX+upWidth/8, upRectY+upHeight/8, null);
            //BufferedImage speedometer;
            //try{
            //   speedometer = ImageIO.read(new File("speedometer.png"));
            //}catch (IOException e){
               //speedometer = null;
               //asdf.out.println("Lol no");
           // }
            //speedometer = resize(speedometer, upWidth/2, upHeight/2);
            ///Users/matthew.khoriaty/Desktop/Code/Orbit\ Game/Holy\ Trinity/Classes/speedometer.png
            //g.drawImage(speedometer, secondUpRectX+upWidth/4, upRectY+upHeight/4, null);
            //Shrink Icon
            //ImageIcon shrinkIcon = (ImageIcon) rsc.getObject("shrink icon.png");
            g.drawRect(thirdUpRectX, upRectY, upWidth, upHeight);
            g.drawImage(shrinkImage, thirdUpRectX , upRectY, null);
            //g.drawImage(shrinkIcon.getImage(), thirdUpRectX + upWidth/4, upRectY+upHeight/4, null);
            /*BufferedImage shrinkImage;
               try{
                  shrinkImage = ImageIO.read(new File("shrink icon.png"));
                  shrinkImage = resize(shrinkImage, upWidth/2, upHeight/2);
                  g.drawImage(shrinkImage, thirdUpRectX + upWidth/4, upRectY+upHeight/4, null);
               }catch (IOException e){
                  shrinkImage = null;
               //asdf.out.println("Lol no");
               }
               */
               
         
            
         }
         
         
         
         if(!photoMode)
         {
            GUILayer(g);
         }
         
         /*int testx1 = 800;
         int testy1 = 600;
         int testx2 = 600;
         int testy2 = 300;
         g.drawLine(testx1, testy1, testx2, testy2);
         double testSlope = (double)(testy2-testy1)/(testx2-testx1);
         double perpTestSlope = -1/testSlope;
         double closestX = (((-testSlope*testx1+testy1-playerPoint.getY())/perpTestSlope) + playerPoint.getX())/(1+testSlope*testSlope);
         double closestY = testSlope*(closestX-testx1)+testy1;
         
         if((closestX > testx2 && closestX > testx1)){
            if(testx1>testx2){
               closestX = testx1;
               closestY = testy1;
            }
            if(testx2>testx1){
               closestX = testx2;
               closestY = testy2;
            }
         
         }else if((closestX < testx2 && closestX < testx1) ){
            if(testx1<testx2){
               closestX = testx1;
               closestY = testy1;
            }
            if(testx2<testx1){
               closestX = testx2;
               closestY = testy2;
            }
         }
         
         boolean touching = findDistance(playerPoint.getX(), playerPoint.getY(), closestX, closestY) <= playerPoint.getDiameter()/2;
         //boolean touching = onLine && 
          //System.out.println(helpful.touchesLine(testx1,testy1,testx2,testy2, playerPoint.getX(), playerPoint.getY(), playerPoint.getDiameter()/2));
         */
      }
      public void paintComponent(Graphics g) {
      
         super.paintComponent(g);
         
         if(isShowing.equals("Game")){
            drawGame(g);
         }else if(isShowing.equals("Menu")){
            fillPlayButton(g);
            fillCreditButton(g);
            fillModesButton(g);
            
         }else if(isShowing.equals("Credits")){
            fillCredits(g);
            if(creditY<-500){
               isShowing = "Menu";
               creditY = FRAME_HEIGHT;
            }
         }else if(isShowing.equals("Modes")){
            fillModes(g);
         }         
      }
      public void fillModes(Graphics g){
         fillBackButton(g);
      }
      public void fillCredits(Graphics g){
         g.setFont(new Font("Impact", Font.PLAIN, 100));
         g.drawString("Director: Matthew Khoriaty", 100, creditY);
         g.drawString("Coding: Matthew Khoriaty", 100, creditY+100);
         g.drawString("Art: Ovik Das", 100, creditY+200);
         g.drawString("\"Art\": Matthew Khoriaty", 100, creditY+300);
         g.drawString("Intern: Matthew Khoriaty", 100, creditY+400);
         g.setFont(new Font("Impact", Font.PLAIN, 25));
         g.drawString("Some help from: Mrs. Saunders, Mr. Perez, and Gabe", 100, creditY+500);
         
         creditY-=10;
      }
      public void fillCreditButton(Graphics g){
         g.setFont(new Font("Impact", Font.PLAIN, creditButton.getHeight()));
         creditButton.setWidth(g.getFontMetrics().stringWidth(creditButton.getText()));
            
         creditButton.setX(FRAME_WIDTH/2-creditButton.getWidth()/2);
         creditButton.setY(7*FRAME_HEIGHT/8);
            
         g.drawString(creditButton.getText(), creditButton.getX(), creditButton.getY());
      }
      public void fillBackButton(Graphics g){
         backButton.setWidth(g.getFontMetrics().stringWidth(backButton.getText()));
         backButton.setX(100);
         backButton.setY(100);
         g.drawString(backButton.getText(), backButton.getX(), backButton.getY());
      }
      public void fillModesButton(Graphics g){
         modesButton.setWidth(g.getFontMetrics().stringWidth(modesButton.getText()));
         modesButton.setX((int)(FRAME_WIDTH/2)-modesButton.getWidth()/2);
         modesButton.setY(FRAME_HEIGHT/2);
         g.drawString(modesButton.getText(), modesButton.getX(), modesButton.getY());
      }
      
      public void fillPlayButton(Graphics g){
         g.setFont(new Font("Impact", Font.PLAIN, playButton.getHeight()));
         playButton.setWidth(g.getFontMetrics().stringWidth(playButton.getText()));
            
         playButton.setX(FRAME_WIDTH/2-playButton.getWidth()/2);
         playButton.setY(FRAME_HEIGHT/3);
            
         g.drawString(playButton.getText(), playButton.getX(), playButton.getY());
      }
      
      public BufferedImage resize(BufferedImage img, int height, int width) {
         Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
         BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g2d = resized.createGraphics();
         g2d.drawImage(tmp, 0, 0, null);
         g2d.dispose();
         return resized;
      }
   
   }
   /*
   for (int i = 0; i < playerMaxHealth; i++) {
            int[] triangleX = {
              heartStartX - 2*heartWidth/18,
              heartStartX + heartWidth + 2*heartWidth/18,
             (heartStartX - 2*heartWidth/18 + heartStartX + heartWidth + 2*heartWidth/18)/2};
            int[] triangleY = { 
              heartStartY + heartHeight - 2*heartHeight/3, 
              heartStartY + heartHeight - 2*heartHeight/3, 
              heartStartY + heartHeight };
            g.fillOval(
              heartStartX - heartWidth/12,
              heartStartY, 
              heartWidth/2 + heartWidth/6, 
              heartHeight/2); 
            g.fillOval(
              heartStartX + heartWidth/2 - heartWidth/12,
              heartStartY,
              heartWidth/2 + heartWidth/6,
              heartHeight/2);
            g.fillPolygon(triangleX, triangleY, triangleX.length);
            g.fillPolygon();
            
            heartStartX+=heartSeparation+heartWidth;
         }
*/
   /* class Orbiter extends JComponent {

 
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

 

            g.setColor(Color.GREEN);

            g.drawOval(x+100-CIRCLE_DIAMETER/2, y-CIRCLE_DIAMETER/2, CIRCLE_DIAMETER*2, CIRCLE_DIAMETER*2);

            g.fillOval(x+100-CIRCLE_DIAMETER/2, y-CIRCLE_DIAMETER/2, CIRCLE_DIAMETER*2, CIRCLE_DIAMETER*2);
         
        }

    }*/

   
 
   class MyCircleMouseListener implements MouseListener, MouseMotionListener {
   
   
      public void mousePressed(MouseEvent e) {
      
           // x = e.getX();
      
           // y = e.getY();
      
            //myCircle.repaint();
         //mouseX=e.getX();
         //mouseY=e.getY();
         int rememberMouseX = mouseX;
         int rememberMouseY = mouseY;
         mouseX = e.getX();
         mouseY = e.getY();
      
         if(isShowing.equals("Game")){
            if(pointInRect(pauseX, pauseY, pauseX+pauseHalfWidth+pauseSpacing, pauseY+pauseHalfHeight, mouseX, mouseY)){
               //isPaused = !isPaused;
            }
            
            if(owedSpecialUp > gottenSpecialUp && !isPaused){
               
            }
            
            if(offerUpgrade && !isPaused){
               if(pointInRect(firstUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  
                  //firstUpgradeChosen = true;
               }else if(pointInRect(secondUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  //secondUpgradeChosen = true;
               }else if(pointInRect(thirdUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  //thirdUpgradeChosen = true;
               }
                  
            }else{
               
               
            }
            
         
         }else if(isShowing.equals("Menu")){
            //asdf.out.println("MenuClick");
            if(pointInRect(playButton.getX(), playButton.getY()-playButton.getHeight(), playButton.getWidth(), playButton.getHeight(), mouseX, mouseY)){
               //asdf.out.println("Switch");
               //isShowing = "Game";
               mouseX = playerPoint.getX();
               mouseY = playerPoint.getY();
            }
            if(creditButton.isClicked(mouseX, mouseY)){
               //asdf.out.println("CreditClicked");
               //isShowing = "Credits";
            }
         }
      
      }
   
   
   
        
        
       
      public void mouseEntered(MouseEvent e){
      // x = e.getX();
      
        //    y = e.getY();
      
         //   myCircle.repaint();
           // System.out.println(x + ", " + y);
      }
      public void mouseExited(MouseEvent e){
      // x = e.getX();
      
        //    y = e.getY();
      
          //  myCircle.repaint();
            //asdf.out.println(x + ", " + y);
      }
      public void mouseReleased(MouseEvent e){
      //x = e.getX();
      
        //    y = e.getY();
      
          //  myCircle.repaint();
            //asdf.out.println(x + ", " + y);
         int rememberMouseX = mouseX;
         int rememberMouseY = mouseY;
         mouseX = e.getX();
         mouseY = e.getY();
      
         if(isShowing.equals("Game")){
            if(pointInRect(pauseX, pauseY, pauseX+pauseHalfWidth+pauseSpacing, pauseY+pauseHalfHeight, mouseX, mouseY)){
               isPaused = !isPaused;
            }
            
            if(owedSpecialUp > gottenSpecialUp && !isPaused){
               
            }
            
            if(offerUpgrade && !isPaused){
               if(pointInRect(firstUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  
                  firstUpgradeChosen = true;
               }else if(pointInRect(secondUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  secondUpgradeChosen = true;
               }else if(pointInRect(thirdUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  thirdUpgradeChosen = true;
               }
                  
            }else{
               
               
            }
            
         
         }else if(isShowing.equals("Menu")){
            //asdf.out.println("MenuClick");
            if(modesButton.isClicked(mouseX, mouseY)){
               //asdf.out.println("Switch");
               isShowing = "Modes";
               //mouseX = playerPoint.getX();
               //mouseY = playerPoint.getY();
            }
            if(pointInRect(playButton.getX(), playButton.getY()-playButton.getHeight(), playButton.getWidth(), playButton.getHeight(), mouseX, mouseY)){
               //asdf.out.println("Switch");
               isShowing = "Game";
               mouseX = playerPoint.getX();
               mouseY = playerPoint.getY();
            }
            
            if(creditButton.isClicked(mouseX, mouseY)){
               //asdf.out.println("CreditClicked");
               isShowing = "Credits";
            }
         }else if(isShowing.equals("Modes")){
            if(pointInRect(backButton.getX(), backButton.getY()-backButton.getHeight(), backButton.getWidth(), backButton.getHeight(), mouseX, mouseY)){
               isShowing = "Menu";
            }
         }
      }
      public void mouseDragged(MouseEvent e) {
      //x = e.getX();
      
        //    y = e.getY();
      
          //  myCircle.repaint();
            //asdf.out.println(x + ", " + y);
         int rememberMouseX = mouseX;
         int rememberMouseY = mouseY;
         mouseX = e.getX();
         mouseY = e.getY();
      
         if(isShowing.equals("Game")){
            if(pointInRect(pauseX, pauseY, pauseX+pauseHalfWidth+pauseSpacing, pauseY+pauseHalfHeight, mouseX, mouseY)){
               //isPaused = !isPaused;
            }
            
            if(owedSpecialUp > gottenSpecialUp && !isPaused){
               
            }
            
            if(offerUpgrade && !isPaused){
               if(pointInRect(firstUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  
                  //firstUpgradeChosen = true;
               }else if(pointInRect(secondUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  //secondUpgradeChosen = true;
               }else if(pointInRect(thirdUpRectX, upRectY, upWidth, upHeight, mouseX, mouseY)){
                  mouseX = rememberMouseX;
                  mouseY = rememberMouseY;
                  
                  //thirdUpgradeChosen = true;
               }
                  
            }else{
               
               
            }
            
         
         }else if(isShowing.equals("Menu")){
            //asdf.out.println("MenuClick");
            if(pointInRect(playButton.getX(), playButton.getY()-playButton.getHeight(), playButton.getWidth(), playButton.getHeight(), mouseX, mouseY)){
               //asdf.out.println("Switch");
               //isShowing = "Game";
               mouseX = playerPoint.getX();
               mouseY = playerPoint.getY();
            }
            if(creditButton.isClicked(mouseX, mouseY)){
               //asdf.out.println("CreditClicked");
               //isShowing = "Credits";
            }
         }
      
         
      
      }
      public void mouseMoved(MouseEvent e) {
      //x = e.getX();
      
         //   y = e.getY();
            
                    
            //myCircle.repaint();
                  
      }
      public void updateLocation(MouseEvent e) {
      //x = e.getX();
      
        //    y = e.getY();
      
          //  myCircle.repaint();
            
            
      }
      public void mouseClicked(MouseEvent e)  {
      //x = e.getX();
      
        //    y = e.getY();
      
          //  myCircle.repaint();
            
      //asdf.out.println(x + ", " + y);
         if(isShowing.equals("Game")){
            if (findDistance(mouseX,mouseY,playerPoint.getX(),playerPoint.getY())<=playerDiameter/2 && !isPaused){
               circleTapped = true;
            }else{
                     
                  //weaponReturn=false;
                  
            }
            
         }
      }
   
   
   }

}

class KeyboardExample extends JPanel {

   public static char pressedChar;
   MyKeyListener listener;
   public KeyboardExample() {
      listener = new MyKeyListener();
      addKeyListener(listener);
      setFocusable(true);
   }
   
   public MyKeyListener getMyKeyListener(){
      return listener;
   }
	
	

   public class MyKeyListener implements KeyListener, java.io.Serializable{
      @Override
      public void keyTyped(KeyEvent e) {
         if(e.getKeyCode() == KeyEvent.VK_DOWN || pressedChar == 's'){
            downDown = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_UP || pressedChar == 'w'){
            upDown = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_LEFT || pressedChar == 'a'){
            leftDown = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_RIGHT || pressedChar == 'd'){
            rightDown = true;
         }
      }
      public boolean upDown;
      public boolean downDown;
      public boolean leftDown;
      public boolean rightDown;
      @Override
      public void keyPressed(KeyEvent e) {
         //asdf.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
         
         pressedChar = e.getKeyChar();
         if(pressedChar == '0' || e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
         }
         if(pressedChar == 'c'){
            CircleOrbitMouse.photoMode = !CircleOrbitMouse.photoMode;
         }
         if(e.getKeyCode() == KeyEvent.VK_DOWN || pressedChar == 's'){
            downDown = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_UP || pressedChar == 'w'){
            upDown = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_LEFT || pressedChar == 'a'){
            leftDown = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_RIGHT || pressedChar == 'd'){
            rightDown = true;
         }
         if(pressedChar == 'y'){
            
         }
         
         if(pressedChar == ' '){
            //asdf.out.println("Spaaace");
            /*weaponReturnStartX=firstWeapon.getX();
            weaponReturnStartY=firstWeapon.getY();
            weaponReturn=true;
            secondWeaponReturn=true;
            secondWeaponReturnStartX=secondaryWeapon.getX();
            secondWeaponReturnStartY=secondaryWeapon.getY();*/
         }
         //if(pressedChar == ''){
        //    System.out.println("nada");
         //}
      }
   
      @Override
      public void keyReleased(KeyEvent e) {
         if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's'){
            downDown = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w'){
            upDown = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a'){
            leftDown = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd'){
            rightDown = false;
         }
         
         //asdf.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
      }
      
      public char getPressedChar(){
         return pressedChar;
      } 
      public void setChar(char setChar){
         pressedChar = setChar;
      }
   }
}