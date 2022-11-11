package com.company;

public class SoundTitleState implements SoundState {

    @Override
    public void playSE(Sound music, int i) {

    }


    @Override
    public void playMusic(Sound music, GamePanel gp) {
        music.setFile(7);
        music.play();
        music.loop();
    }

    @Override
    public void stop(Sound music) {

    }
}
