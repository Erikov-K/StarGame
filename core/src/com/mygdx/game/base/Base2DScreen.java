package com.mygdx.game.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.math.MatrixUtils;
import com.mygdx.game.math.Rect;

/**
 * Базовый класс экрана
 */

public class Base2DScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;

    protected Game game;
    protected Rect screenBounds; // границы экрана в пикселях
    private Rect worldBounds; // границы проэкции мировых координат
    private Rect glBounds; // gl-левские координаты

    protected Matrix4 worldToGl; // матрица преобразований из мировых координат к gl
    protected Matrix3 screenToWorld; // матрица преобразований из экранных координат к мировым
    protected float worldHeight = 1f; // высота экрана в системе координат

    private Vector2 touch = new Vector2();

    public Base2DScreen(Game game) {
        this.game = game;
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0,0, 1f, 1f); // инициализация GL-координат
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
//        System.out.println("show");
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height); // установка размера прямоугольница в экранных координатах
        screenBounds.setLeft(0);            // установка крайней левой точки
        screenBounds.setBottom(0);          // установка крайней нижней точки

        float aspect = width / (float) height;  // вычисление соотношения сторон
        // установка границ в мировых координатах
        worldBounds.setHeight(worldHeight); // фиксация по высоте (оси Y)
        worldBounds.setWidth(worldHeight * aspect); // установка ширины с соблюдением пропорций

        // преобразование из системы мировых координат(3D) в систему координат GL(4D)
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);   // установка матрицы преобразований для "батчера"
        // преобразование из системы экранных(2D) координат в систему мировых координат(3D)
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    // перегруженный вспомогательный метод, выводящий информацию о текущих параметрах
    // прямоугольника в мировой системе координат
    public void resize(Rect worldBounds) {
//        System.out.println("resize w=" + worldBounds.getWidth() + " h=" + worldBounds.getHeight());
    }

    @Override
    public void pause() {
//        System.out.println("pause");
    }

    @Override
    public void resume() {
//        System.out.println("resume");
    }

    @Override
    public void hide() {
//        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
//        System.out.println("dispose");
        batch.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
//        System.out.println("keyDown keycode=" + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
//        System.out.println("keyUp keycode=" + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
//        System.out.println("keyTyped character=" + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // преобразование координат нажатия тача из пиксельной СК в мировую СК
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    // перегруженный вспомогательный метод, выводящий информацию о текущих координатах
    // нажатия тача в мировой системе координат
    public void touchDown(Vector2 touch, int pointer) {
//        System.out.println("touchDown X=" + touch.x + " Y=" + touch.y);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // преобразование координат отпускания тача из пиксельной СК в мировую СК
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    // перегруженный вспомогательный метод, выводящий информацию о текущих координатах
    // отпускания тача в мировой системе координат
    public void touchUp(Vector2 touch, int pointer) {
//        System.out.println("touchUp X=" + touch.x + " Y=" + touch.y);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // преобразование координат перемещения с нажатым тачем из пиксельной СК в мировую СК
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    // перегруженный вспомогательный метод, выводящий информацию о текущих координатах
    // при перемещении с нажатым тачемв мировой системе координат
    public void touchDragged(Vector2 touch, int pointer) {
//        System.out.println("touchDragged X=" + touch.x + " Y=" + touch.y);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
//        System.out.println("scrolled amount=" + amount);
        return false;
    }
}
