package com.company;

public class SoundPauseState implements SoundState {

    //Pause State of game then music should only stop
    @Override
    public void playSE(Sound music, int i) {

    }

    @Override
    public void playMusic(Sound music, GamePanel gp) {

    }

    @Override
    public void stop(Sound music) {
        music.stop();
    }
}
