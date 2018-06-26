package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Base2DScreen;

/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {
    SpriteBatch batch;

    Texture img, background;
    Vector2 startPos, endPos;
    Vector2 imgCenter = new Vector2();
    int windowHeight, windowWight;
    float distance, directionX, directionY;
    boolean moving;

    float speed = 500;
    float elapsed = 0.05f;

    //


    public MenuScreen(Game game) {
        super(game);
//        img = new Texture("badlogic.jpg");
        img = new Texture("aliensspaceship.png");
        background = new Texture("background/heic1313b.jpg");
        imgCenter.x = img.getWidth() / 2;
        imgCenter.y = img.getHeight() / 2;
        startPos = new Vector2(Gdx.graphics.getWidth()/2 - imgCenter.x, Gdx.graphics.getHeight()/2 - imgCenter.y);
        endPos = new Vector2(0, 0);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Moving object
        if (moving == true) {
            distance = (float) Math.sqrt(Math.pow(endPos.x - startPos.x, 2) + Math.pow(endPos.y - startPos.y, 2));

            startPos.x += directionX * speed * elapsed;
            startPos.y += directionY * speed * elapsed;

            float movedDistance = (float) Math.sqrt(Math.pow(endPos.x - startPos.x, 2) + Math.pow(endPos.y - startPos.y, 2));
            if (movedDistance >= distance) {
                // переопределение стартовой позиции после перемещения
                startPos.x = endPos.x;
                startPos.y = endPos.y;
                moving = false;
            }
        }

        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(img, startPos.x, startPos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        moving = true;
        endPos.x = screenX - imgCenter.x;
        endPos.y = windowHeight - screenY - imgCenter.y;
        // проверка выхода картинки за границы
        checkBorders();

        // Move distance
        distance = (float) Math.sqrt(Math.pow((endPos.x - startPos.x), 2) + Math.pow((endPos.y - startPos.y), 2));
        // проверка координат (0,0).
        // при таком условии картинка навсегда улетает за переделы экрана :)
        if (distance < 1) distance = 1;
//        System.out.println("distance = " + distance);

        // X & Y directions
        directionX = (endPos.x - startPos.x) / distance;
        directionY = (endPos.y - startPos.y) / distance;
//        System.out.println("directionX = " + directionX);
//        System.out.println("directionY = " + directionY);

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void resize(int width, int height) {

        // нужно доделать учет изменения размеров картинки при растягивании экрана...
//        img = new Texture("badlogic.jpg");
//        imgCenter.x = img.getWidth() / 2;
//        imgCenter.y = img.getHeight() / 2;
//
        windowHeight = height;
        windowWight = width;

        super.resize(width, height);
    }

    // Учёт выхода картинки за пределы экрана
    public void checkBorders() {
        if (endPos.x < 0) endPos.x = 0;
        if (endPos.x > (windowWight - img.getWidth())) endPos.x = windowWight - img.getWidth();
        if (endPos.y < 0) endPos.y = 0;
        if (endPos.y > (windowHeight - img.getHeight())) endPos.y = windowHeight - img.getHeight();
    }
}
