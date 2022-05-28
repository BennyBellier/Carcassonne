package controller;

public class Animation {
  int slow, decompte;

  Animation(int l) {
    slow = l;
    decompte = 0;
  }

  void car() {
    decompte++;
    if (decompte >= slow) {
      decompte = 0;
      return;
    }
  }
}
