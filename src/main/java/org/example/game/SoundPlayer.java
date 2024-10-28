package org.example.game;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

public class SoundPlayer {

    public static void playSound(InputStream soundStream) {
        try (BufferedInputStream bufferedIn = new BufferedInputStream(soundStream);
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
