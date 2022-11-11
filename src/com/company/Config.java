package com.company;

import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter((new FileWriter("src/config.txt")));
            if (gp.fullScreenOn) {
                bw.write("On");
            }
            if (!gp.fullScreenOn) {
                bw.write("Off");
            }
            bw.newLine();
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            bw.write(String.valueOf(gp.soundEffect.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/config.txt"));

            String s = br.readLine();
            if ((s.equals("On"))) {
                gp.fullScreenOn = true;
            }
            if (s.equals("Off")) {
                gp.fullScreenOn = false;
            }


            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            s = br.readLine();

            gp.soundEffect.volumeScale = Integer.parseInt(s);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
