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
import com.mygdx.game.sprite.Buttons;
import com.mygdx.game.sprite.Star;


/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {

    private Background background;
    //  private Star star;
    private Buttons startButton, exitButton;
    private Star[] stars;
    private TextureAtlas atlas;
    private float rotateAngle = 0f;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Texture[] bg = new Texture[]{   //  варианты фонов
                new Texture("textures/bg.png"),
                new Texture("background/heic0406a.jpg"),
                new Texture("background/heic1313b.jpg"),
                new Texture("background/heic0602a.jpg"),
                new Texture("background/heic0817a.jpg"),
        };

//        background = new Background(new TextureRegion(bg));
        background = new Background(new TextureRegion(bg[(int) Math.round(Math.random() * (bg.length - 1))])); //рандомный выбор фона при старте
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        TextureRegion starRegion = atlas.findRegion("star");
        TextureRegion btPlay = atlas.findRegion("btPlay");
        TextureRegion btExit = atlas.findRegion("btExit");
        startButton = new Buttons(btPlay, 0.1f);
        exitButton = new Buttons(btExit, 0.075f);
        //  star = new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), 0.01f);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.009f, 0.001f));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        //  star.update(delta);
        for (Star star : stars) star.update(delta);
        startButton.update(delta);
        exitButton.update(delta);
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        rotateBackground();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        //  star.draw(batch);
        startButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
//        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        if (startButton.isMe(touch)) startButton.setScale(0.9f);
        if (exitButton.isMe(touch)) exitButton.setScale(0.9f);

    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        if (startButton.isMe(touch)) startButton.setScale(1f);
        if (exitButton.isMe(touch)) exitButton.setScale(1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        //  background.resize(worldBounds);
        background.setHeightProportion((worldBounds.getHeight() / worldBounds.getWidth()));
        //  star.resize(worldBounds);
        for (Star star : stars) star.resize(worldBounds);
        startButton.resize(worldBounds, -0.1f, -0.1f);
        exitButton.resize(worldBounds, 0.1f, -0.085f);
    }

    // плавное вращение фона
    public void rotateBackground() {
        if (rotateAngle < 360 && rotateAngle >= 0) {
            background.setAngle(rotateAngle);
            rotateAngle += 0.005f;
//            rotateAngle += 1f;
        } else rotateAngle = 0;
    }
}
