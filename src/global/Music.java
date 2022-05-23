package global;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Music implements Runnable {
  List<AudioInputStream> playlist = new ArrayList<>();
  Clip clip;
  Thread thread;
  int playlistNumber = 0;

  public Music() {
    try {
      for (File f : new File("assets/audio/music").listFiles()) {
        playlist.add(AudioSystem.getAudioInputStream(new File(f.toPath().toString())));
      }
      clip = AudioSystem.getClip();
      clip.open(playlist.get(0));
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      thread = new Thread(this);
    } catch (Exception e) {
      Configuration.instance().logger().warning("Impossible de charger les fichiers audio");
      e.printStackTrace();
    }
  }

  /**
   * Lance le thread permettant de joué la music
   */
  public void play() {
    thread.start();
  }

  /**
   * Stop les
   */
  public void stop() {
    if (clip.isRunning()) {
      clip.stop();
      clip.close();
    }
    if (thread.isAlive())
      thread.interrupt();

  }

  /**
   * Lance le clip jouant la musique
   */
  @Override
  public void run() {
    clip.start();
    // while (thread.isAlive()) {
    //   if (!clip.isRunning()) {
    //     clip.close();
    //     playlistNumber = (playlistNumber + 1) % playlist.size();
    //     try {
    //       clip.open(playlist.get(playlistNumber));
    //       clip.start();
    //     }
    //     catch (Exception e) {
    //       Configuration.instance().logger().severe("Impossible de lire la musique");
    //     }
    //   }
    // }
  }

}
