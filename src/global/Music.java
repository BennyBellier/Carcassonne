package global;

import java.io.BufferedInputStream;
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
  int stop;

  public Music() {
    loadMusic();
    try {
      clip = AudioSystem.getClip();
      clip.open(playlist.get(0));
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      thread = new Thread(this);
    } catch (Exception e) {
      Configuration.instance().logger().warning("Impossible de charger le lecteur audio");
      e.printStackTrace();
    }
  }

  /**
   ** Charge les différentes musics dans la playlist
   */
  void loadMusic() {
    try {
      playlist.add(AudioSystem.getAudioInputStream(new BufferedInputStream(Configuration.charge("audio/music/Enchanted-Emotional_Fantasy_Music.au"))));
    } catch (Exception e) {
      Configuration.instance().logger().severe("Impossible de charger les musics");
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
    //thread.interrupt();ad.interrupt();
    thread = new Thread(this);
  }

  /**
   * Lance le clip jouant la musique
   */
  @Override
  public void run() {
    clip.start();
  }

}