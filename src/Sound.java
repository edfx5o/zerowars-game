//Author: Akash Harriram

/* Handles playback of midis and .au sounds */

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.ArrayList;

public class Sound {
    
    private ArrayList musicList = null;
    
    public Sound()
    {
        musicList = new ArrayList();
    }
    
    //loads an external sound file and adds it to the arraylist
    public void loadClip(String clip) {

        AudioClip ac = null;
     try {
        ac = Applet.newAudioClip (
                                getClass().getResource(clip));
        if (ac!=null)
            musicList.add(ac);
     }
     catch (Exception e) {
        System.out.println ("Problem loading sound: " + e);
     }
   }

   //plays/loops a sound clip
   public void playClip(int index, boolean loop) {
        if (index>=0 && index<musicList.size())
        {
            AudioClip ac = (AudioClip)musicList.get(index);
            if (loop)
                ac.loop();
            else
                ac.play();
        }
   }
   
   //stops a single sound clip
   public void stopClip(int index) {
        if (index>=0 && index<musicList.size())
        {
            AudioClip ac = (AudioClip)musicList.get(index);
            ac.stop();
        }
   }
   
   //stops all sounds
   public void stopAll() {
        for (int i=0; i<musicList.size(); i++)
        {
            AudioClip ac = (AudioClip)musicList.get(i);
            ac.stop();
        }
   }
   
   public void destroy()
   {
       musicList.clear();
       musicList=null;
   }

}
