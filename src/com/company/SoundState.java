package com.company;

public interface SoundState {

    void playSE(Sound music, int i);

    void playMusic(Sound music, GamePanel gp);

    void stop(Sound music);
}
