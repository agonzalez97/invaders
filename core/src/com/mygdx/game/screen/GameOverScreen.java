package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Controls;
import com.mygdx.game.SpaceInvaders;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.objects.Ship;


public class GameOverScreen extends SpaceInvadersScreen {
    BitmapFont bitmapFont;
    SpriteBatch batch;

    @Override
    void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    public GameOverScreen(SpaceInvaders game) {
        super(game);
    }

    @Override
    public void show() {
        bitmapFont = new BitmapFont();
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        batch.begin();
        bitmapFont.draw(batch, "GAME OVER", 270, 280);
        bitmapFont.draw(batch, "REINICIAR PARTIDA ?", 250, 200);
        bitmapFont.draw(batch, "Yes[Y]", 240, 150);
        bitmapFont.draw(batch, "No[N]", 360, 150);
        if(Controls.yes()){
            setScreen(new GameScreen(game));
        }
        if(Controls.no()){
            System.exit(0);
        }
        batch.end();

    }
}
