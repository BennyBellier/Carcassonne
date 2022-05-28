package global;

import java.util.List;
import javax.sound.sampled.AudioInputStream;

public class Audio {
  float master, musicVolume, fxVolume;
  List<AudioInputStream> musicList;
  public Music music;


  public Audio() {
    music = new Music();
  }
}
