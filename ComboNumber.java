class ComboNumber implements java.io.Serializable{
   int x;
   int y;
   int value;
   public ComboNumber(int x, int y, int value){
      this.x = x;
      this.y = y;
      this.value = value;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
   }
   public int getValue(){
      return value;
   }
   public void changeXY(int addX, int addY){
      this.x +=addX;
      this.y +=addY;
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