package global;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
  List<AudioInputStream> playlist = new ArrayList<>();
  Clip clip;
  int playlistNumber = 0;
  int stop;

  public Music() {
    loadMusic();
    try {
      clip = AudioSystem.getClip();
      clip.open(playlist.get(0));
      clip.loop(Clip.LOOP_CONTINUOUSLY);
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
   ** Lance le lecteur de la music
   */
  public void play() {
    clip.start();
  }

  /**
   ** Stop le lecteur
   */
  public void stop() {
    clip.stop();
  }
}