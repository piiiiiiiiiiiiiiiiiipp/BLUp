package com.company;

public class SoundPlayState implements SoundState {
    @Override
    public void playSE(Sound music, int i) {
        music.setFile(i);
        music.play();
    }

    @Override
    public void playMusic(Sound music, GamePanel gp) {
        music.setFile(0);
        music.play();
        music.loop();
    }

    @Override
    public void stop(Sound music) {

    }
}
