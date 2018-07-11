package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;


public class EnemyShip extends Sprite {
    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.01f;
    float chanceOf = 0f;
    private Rect worldBounds;
    public boolean visible = false;

    public Vector2 v = new Vector2(0f, 0f);

    public EnemyShip(TextureAtlas atlas, String name, Float chanceOf, Float speed) {
        super(atlas.findRegion(name), 1, 2, 2);
        setHeightProportion(SHIP_HEIGHT);
        this.chanceOf = chanceOf;
        this.v.y = speed * -1f;
    }


    @Override
    public void resize(Rect worldBounds) {
        setTop(worldBounds.getTop() + getHeight() + BOTTOM_MARGIN);
        this.worldBounds = worldBounds;
        startPosition();
    }


    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
        if (pos.x < 0) v.x += 0.01f;
        if (pos.x > 0) v.x -= 0.01f;
    }

    private void checkAndHandleBounds() {
        if (getBottom() < worldBounds.getBottom() - getHeight() - BOTTOM_MARGIN) {
            setTop(worldBounds.getTop() + getHeight() + BOTTOM_MARGIN);
            visible = false;
            startPosition();
        }
    }

    public void startPosition() {
        pos.x = Rnd.nextFloat(
                worldBounds.getLeft() + getHalfWidth() + BOTTOM_MARGIN,
                worldBounds.getRight() - getHalfWidth() - BOTTOM_MARGIN
        );
    }
}