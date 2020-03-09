import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
class Button implements java.io.Serializable{
   int x;
   int y;
   int width;
   int height;
   String text;
   public BufferedImage icon = null;
   public static BufferedImage anotherPlanet;
   
   boolean accessible = false;
   public static void initializeButtons(){
      
   }
   public Button(int x, int y, int w, int h){
      this.x = x;
      this.y = y;
      width = w;
      height = h;
   }
   public Button(int x, int y, int w, int h, String s){
      this.x = x;
      this.y = y;
      width = w;
      height = h;
      text = s;
   }
   public boolean isAccessible(){
      return accessible;
   }
   public void setAccessible(boolean it){
      accessible = it;
   }
   public boolean isClicked(int pointX, int pointY){
      return helpful.pointInRect(x, y-height, width, height, pointX, pointY);
   }
   public String getText(){
      return text;
   }
   public void setText(String s){
      text = s;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
   }
   public int getWidth(){
      return width;
   }
   public int getHeight(){
      return height;
   }
   public void setX(int nx){
      x = nx;
   }
   public void setY(int ny){
      y = ny;
   }
   public void setWidth(int nw){
      width = nw;
   }
   public void setHeight(int nh){
      height = nh;
   }
}