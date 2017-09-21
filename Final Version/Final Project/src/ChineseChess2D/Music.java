/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import java.applet.AudioClip; 
import java.io.*; 
import java.applet.Applet; 
import java.awt.Frame; 
import java.net.MalformedURLException; 
import java.net.URL; 

/**
 *
 * @author konatan
 */
public class Music implements Runnable{
    AudioClip aau; 
    public Music(String a){  
        try {
            URL cb; 
            File f = new File(a); 
            cb = f.toURL();
            aau = Applet.newAudioClip(cb);
            //aau.loop();
            //aau.play();
            //
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } 
    }
    
    public Music(String a,int b){  
        try { 
            URL cb; 
            File f = new File(a); 
            cb = f.toURL();  
            aau = Applet.newAudioClip(cb); 
            aau.play();
            //aau.loop();
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } 
    }
    
    public void run(){
        aau.loop();
    }
    
    public void stopmusic(){
        aau.stop();
    }
    public void playmusic(){
        aau.play();
    }
    public void loopmusic(){
        aau.loop();
    }
}

