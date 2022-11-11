package com.company;

public class SoundPlayer {
    SoundState state = new SoundTitleState();
    Sound music = new Sound();

    public void setState(SoundState state) {
        this.state = state;
    }

    void nextState(GamePanel gp) {
        if (gp.gameState == gp.titleState || gp.gameState == gp.dialogState
                || gp.gameState == gp.characterState) {
            setState(new SoundTitleState());
        } else if (gp.gameState == gp.pauseState) {
            setState(new SoundPauseState());
        } else if (gp.gameState == gp.playState) {
            setState(new SoundPlayState());
        }

    }

    void play(GamePanel gp) {
        state.playMusic(music, gp);
    }

    void playSE(int i) {
        state.playSE(music, i);
    }

    void stop() {
        state.stop(music);
    }
}
