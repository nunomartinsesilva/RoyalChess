package com.royalchess.gui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Random;

public class SFXUtils {
    public static void playSound(final String sound)  {
        File soundPath = null;
        if (sound == "win"){
            soundPath = new File("../sound/chesswin.wav");
        }
        else if (sound == "move") {
            String[] availableMoveSounds = { "1","2", "3", "4", "5", "6", "7"};
            Random random = new Random();
            int randomMoveSound = random.nextInt(availableMoveSounds.length);
            soundPath = new File("../sound/chessmove" + availableMoveSounds[randomMoveSound] + ".wav");
        }
        else {
            return;
        }

        try {

            if(soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

            }
            else {
                System.out.println("Can't find file");
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



}

