package project.io;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by Nils Broman.
 * This class plays all the sounds in the game.
 */
public final class Sound {

    private Sound() {}

    public static void playMusic(URL filepath){
	Logger logger = Logger.getLogger(Sound.class.getName());
        try{

	    InputStream music = new FileInputStream(new File(filepath.toString().substring(5)));
	    AudioStream audios = new AudioStream(music); // Warning, us of Sun-supplied class 'AudioStream' is not portable.
            AudioPlayer.player.start(audios);

        } catch (FileNotFoundException e) {
	    JOptionPane.showMessageDialog(null, "File not found");
	    logger.log(Level.SEVERE, "Exception " + e, e);
	    e.printStackTrace();
	} catch(Exception e){ // Warning catch of Exception is too broad.
	    // This warning show up despite of having caught the FileNotFoundException above, if i "fix" the warning another
	    // exactly similar catch appears.
            JOptionPane.showMessageDialog(null, "Failed to play music in game");
            logger.log(Level.SEVERE, "Exception " +e, e);
        }
    }

}
