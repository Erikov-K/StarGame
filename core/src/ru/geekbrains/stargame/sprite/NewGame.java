package ru.geekbrains.stargame.sprite;

        import com.badlogic.gdx.graphics.g2d.TextureAtlas;

        import ru.geekbrains.stargame.base.ActionListener;
        import ru.geekbrains.stargame.base.ScaledTouchUpButton;
        import ru.geekbrains.stargame.math.Rect;

public class NewGame extends ScaledTouchUpButton {

    public NewGame(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("button_new_game"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom()+worldBounds.getHalfHeight()-getHeight());
        setLeft(worldBounds.getLeft()+worldBounds.getHalfWidth()-getHalfWidth());
    }
}
