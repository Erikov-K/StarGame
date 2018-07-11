package com.mygdx.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;


public class MainShip extends Sprite {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.01f;

    private Vector2 v = new Vector2();
    private Vector2 v0 = new Vector2(0.5f, 0f);

    private boolean pressedLeft;
    private boolean pressedRight;

    private Rect worldBounds;


    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIP_HEIGHT);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
//        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
//        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getRight() < worldBounds.getLeft() + this.getWidth() + BOTTOM_MARGIN) {
            setLeft(worldBounds.getLeft() + BOTTOM_MARGIN);
            stop();
        }
        if (getLeft() > worldBounds.getRight() - this.getWidth() - BOTTOM_MARGIN) {
            setRight(worldBounds.getRight() - BOTTOM_MARGIN);
            stop();
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void moveRight() {
        v.set(v0);
    }

    private void stop() {
        v.setZero();
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if (touch.x < pos.x) {
            moveLeft();
        }

        if (touch.x > pos.x) {
            moveRight();
        }
    }

}

