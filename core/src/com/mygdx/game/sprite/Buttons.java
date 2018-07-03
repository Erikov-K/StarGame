package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.base.Sprite;
import com.mygdx.game.math.Rect;

/**
 * Created by Konstantin on 03.07.2018.
 */

public class Buttons extends Sprite {

    public Buttons(TextureRegion region, float height) {
        super(region);
        setHeightProportion(height);
    }

    public void resize(Rect worldBounds, float offsetX, float offsetY) {
        float posX = 0;
        if (offsetX < 0) posX = worldBounds.getLeft() - offsetX;
        else if (offsetX > 0) posX = worldBounds.getRight() - offsetX;
        float posY = 0;
        if (offsetY < 0) posY = worldBounds.getBottom() - offsetY;
        else if (offsetY > 0) posY = worldBounds.getTop() - offsetY;
        pos.set(posX, posY);
    }
}
