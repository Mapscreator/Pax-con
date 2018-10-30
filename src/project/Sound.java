package project;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by Nils Broman.
 * This class plays all the sounds in the game.
 */
public final class Sound {

    private Sound() {}

    public static void playMusic(String filepath){
	Logger logger = Logger.getLogger(Sound.class.getName());
        try{

	    InputStream music = new FileInputStream(new File(filepath));
	    AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);

        } catch (FileNotFoundException e) {
	    JOptionPane.showMessageDialog(null, "File not found");
	    logger.log(Level.SEVERE, "Exception " + e, e);
	    e.printStackTrace();
	} catch(Exception e){
            JOptionPane.showMessageDialog(null, "Failed to play music in game");
            logger.log(Level.SEVERE, "Exception " +e, e);
        }
    }

}
