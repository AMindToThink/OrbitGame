import java.awt.Color;
import java.io.*; 
import java.util.*;
import java.awt.Graphics;
class Drop extends Point{
   public String info;
   public Drop(int x, int y, String info){
      super(x, y, CircleOrbitMouse.FRAME_WIDTH/40, Color.BLUE);
      this.info = info;
   }
}