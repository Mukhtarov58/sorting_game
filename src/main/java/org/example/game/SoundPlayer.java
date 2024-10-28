package org.example.game;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.InputStream;

public class SoundPlayer {

    public static void playSound(InputStream soundStream) {
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundStream)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
