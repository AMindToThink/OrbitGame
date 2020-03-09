
import java.util.ArrayList;
import java.awt.Color;
class Trail implements java.io.Serializable{
   private int x;
   private int y;
   
   private int vectorX;
   private int vectorY;
   
   private int diameter;
   
   //number of dots
   private int length;
   //real length
   private double realLength=0.5;
   boolean isTrail = true;
   public ArrayList<Point> trailList= new ArrayList<Point>();
   int whichTrail = 0;
   
   public Trail(int x, int y, int diameter, int length){
      this.x=x;
      this.y=y;
      this.diameter=diameter;
      this.length = length;
      
         for(int i=0; i<this.length; i++){
               Point trailCircle = new Point(this.x,this.y, (int)(/*this.diameter-*/diameter*(((double)i/this.length)/*/realLength*/)), Color.RED);
               trailList.add(trailCircle);
         }
      

   }
   
   public void follow(int targetX, int targetY){
      
      if(isTrail){
         //Point lastTemp = trailList.get(trailList.size()-1);
         //Point temp = new Point(lastTemp.getX(), lastTemp.getY(), lastTemp.getDiameter(), lastTemp.getColor()); 
         for(int i = 0; i < trailList.size()-1; i++){
            trailList.set(i, new Point(trailList.get(i+1).getX(), trailList.get(i+1).getY(), trailList.get(i).getDiameter(), Color.RED ));
         }
         trailList.set(trailList.size()-1, new Point(targetX, targetY, diameter, Color.RED));
      /*
         
         if(whichTrail+1<trailList.size()){
            
            whichTrail++;
            
         }else{
            whichTrail=0;
            //trailList.get(whichTrail).setDiameter(this.diameter);
         }
         //trailList.get(whichTrail).setDiameter(trailList.get(whichTrail).getDiameter()-1);
         //trailList.get(whichTrail).setDiameter((int)(diameter/helpful.findDistance(trailList.get(whichTrail).getX(), trailList.get(whichTrail).getY(), targetX, targetY)));
         //System.out.println(diameter);
         //trailList.get(whichTrail).setDiameter(diameter);

         trailList.get(whichTrail).setXY(targetX,targetY);
         //trailList.get(whichTrail).setDiameter(trailList.get(whichTrail).getDiameter()-1);
         //trailList.get(whichTrail).setDiameter(trailList.get(whichTrail).getDiameter()+whichTrail/2);
   
         */
      }
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
   
   public int getDiameter(){
      return(this.diameter);
   }
   public void setDiameter(int newDiameter){
      diameter=newDiameter;
   }
   
   public int getX(){
      return x;
   }
   public void changeXY(int addX, int addY){
      this.x +=addX;
      this.y +=addY;
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
   public void setVector(int vectorX, int vectorY ){
      this.vectorX=vectorX;
      this.vectorY=vectorY;
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

   /*public String toString(){
      String str = "" + x + ", " + y;
      return str;     
   }*/
   public void drawCircle(int diameter){
   }
   public String toString(){
      String str = "";
      for(Point each : trailList){
         str += each;
      }
      return str;
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