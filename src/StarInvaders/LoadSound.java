/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.net.URL;
import javax.sound.sampled.*;
/**
 *
 * @author Raith
 */
public class LoadSound {

    AudioInputStream stream=null;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
    
    public Clip load(String name){
    
    URL url=null;
    try {
        url = getClass().getClassLoader().getResource(name);
        stream = AudioSystem.getAudioInputStream(url);
        format = stream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
    clip.open(stream);
    }
    catch (Exception e) {}
    
    
    return clip;
    }
}


