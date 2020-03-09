import java.time.Clock;
import java.time.*; 
class timerThread extends Thread implements java.io.Serializable
{ 
   public static ZoneId zoneId = ZoneId.of("US/Arizona");
    public static Clock myClock = Clock.system(zoneId);
    long startTime;
    long previousTime;

   /*public static void main(String[] args){
      int count = 0;
      timerThread why = new timerThread();
      why.setStart();
      while(true){
         if(why.every(1000)){
            count++;
            System.out.println(count);
         }
         
      }
   }*/
    //@Override
        public timerThread(){
         super();
         setStart();
        }
    public void setStart(){
      startTime = myClock.millis();
    }
    public void time() throws InterruptedException  
    { 
      previousTime = myClock.millis();
            
    } 
    public boolean every(long ms){
      return (myClock.millis()-startTime)%ms <= CircleOrbitMouse.refreshTime;
      
    }
    public boolean every(long ms, long creation){
      return (myClock.millis()-creation)%ms <= CircleOrbitMouse.refreshTime;
      
    }
}