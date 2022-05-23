package global;

import java.util.List;
import javax.sound.sampled.AudioInputStream;

public class Audio {
  float master, musicVolume, fxVolume;
  List<AudioInputStream> musicList;
  public Music music;


  public Audio() {
    master = Integer.parseInt(Configuration.instance().lis("Volume"));
    musicVolume = Integer.parseInt(Configuration.instance().lis("Music"));
    fxVolume = Integer.parseInt(Configuration.instance().lis("FX"));
    music = new Music();
  }

  /**
   * Lance la music
   */
  public void playMusic() {
    Thread t = new Thread(music);
    t.start();
  }
}