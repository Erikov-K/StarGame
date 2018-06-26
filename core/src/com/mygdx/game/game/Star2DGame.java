package com.mygdx.game.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screen.MenuScreen;

/**
 * Основной игровой класс
 */

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }



}
