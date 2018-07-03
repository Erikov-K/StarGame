package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.game.Star2DGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //	config.resizable = false;
        //	float aspect = 9f/16f;
        float aspect = 3f / 4f;
        config.width = 450;
        config.height = (int) (config.width / aspect);
        new LwjglApplication(new Star2DGame(), config);
    }
}
