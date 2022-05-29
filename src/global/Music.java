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
      // clip.loop(Clip.LOOP_CONTINUOUSLY);
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
      playlist.add(AudioSystem.getAudioInputStream(new BufferedInputStream(Configuration.charge("audio/music/Minstrel_s-Song-Medieval-Fantasy-Music.au"))));
      playlist.add(AudioSystem.getAudioInputStream(new BufferedInputStream(Configuration.charge("audio/music/Magic-Lights-Calm-Classical-Music.au"))));
    } catch (Exception e) {
      Configuration.instance().logger().severe("Impossible de charger les musics");
    }
  }
    
  
  /**
   * Lance le thread permettant de joué la music
   */
  public void play() {
    clip.start();
  }

  /**
   * Stop les
   */
  public void stop() {
    clip.stop();
  }

  /**
   * Lance le clip jouant la musique
   */
  @Override
  public void run() {
    while(clip.isRunning());

    try {
    if(playlistNumber == 2){
      playlistNumber=0;
    }
    else{
      playlistNumber += 1;
    }
    clip.open(playlist.get(playlistNumber));
    } catch (Exception e) {}
    play();
  }

}