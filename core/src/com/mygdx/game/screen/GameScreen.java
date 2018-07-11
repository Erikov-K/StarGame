package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Base2DScreen;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.sprite.Background;
import com.mygdx.game.sprite.EnemyShip;
import com.mygdx.game.sprite.MainShip;
import com.mygdx.game.sprite.Star;

import java.util.ArrayList;


/**
 * Created by Alexey on 04.07.2018.
 */

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 56;
//    private static final float STAR_HEIGHT = 0.01f;

    private Background background;
    private Texture bg;
    private Star star[];
    private TextureAtlas atlas;

    private MainShip mainShip;

    private float generateInterval = 2f;
    private float generateTimer;

    ArrayList<EnemyShip> enemyShipList;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        TextureRegion starRegion = atlas.findRegion("star");
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.009f, 0.001f));
        }
        mainShip = new MainShip(atlas);

        enemyShipList = new ArrayList<>();
        enemyShipList.add(new EnemyShip(atlas, "enemy0", 60f, 0.3f));
        enemyShipList.add(new EnemyShip(atlas, "enemy1", 40f, 0.2f));
        enemyShipList.add(new EnemyShip(atlas, "enemy2", 20f, 0.1f));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        mainShip.update(delta);

        generateTimer += delta;
        if (generateInterval <= generateTimer) {
            generateTimer = 0f;
            // to do
            for (int i = 0; i < enemyShipList.size(); i++) {
                if (!enemyShipList.get(i).visible) {
                    enemyShipList.get(i).visible = true;
                    break;
                }
            }

//            enemyShipList.add(new EnemyShip(atlas, "enemy0", 60f, 0.3f));
        }

        for (int i = 0; i < enemyShipList.size(); i++) {
            if (enemyShipList.get(i).visible) {
                enemyShipList.get(i).update(delta);
            }
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }

        mainShip.draw(batch);

        for (int i = 0; i < enemyShipList.size(); i++) {
            enemyShipList.get(i).draw(batch);
        }
        batch.end();
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);

        for (int i = 0; i < enemyShipList.size(); i++) {
            (enemyShipList.get(i)).resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        mainShip.touchUp(touch, pointer);
    }
}
