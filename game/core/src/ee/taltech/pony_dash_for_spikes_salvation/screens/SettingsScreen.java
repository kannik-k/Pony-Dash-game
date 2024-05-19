package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;

public class SettingsScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final Texture backgroundTexture;


    public SettingsScreen(Main game) {
        this.game = game;
        backgroundTexture = new Texture("Game Assets/Sunny land winter forest sky.png");
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }
    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("Skin/terramotherui/terra-mother-ui.json"));
        Skin sliderSkin = new Skin(Gdx.files.internal("Skin2/neon/neon-ui.json"));

        TextButton back = new TextButton("Back", skin);
        Label titleLabel = new Label("Settings", skin);
        Label volumeMusicLabel = new Label("Music", skin);
        Label volumeSoundLabel = new Label("Sound", skin);

        Table topTable = new Table();
        topTable.top().left();
        topTable.setFillParent(true);
        topTable.add(back).pad(10).center();

        Table textTable = new Table();
        textTable.setFillParent(true);
        textTable.row().pad(0, 0, 140, 0);
        textTable.add(titleLabel).fillX().uniformX();
        textTable.row();
        stage.addActor(textTable);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        stage.addActor(topTable);


        //volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, sliderSkin);
        volumeMusicSlider.setValue(game.getPlayScreen().getMusic().getVolume());
        volumeMusicSlider.addListener(event -> {
            game.getPlayScreen().getMusic().setVolume(volumeMusicSlider.getValue());
            return false;
        });

        final Slider soundMusicSlider = new Slider( 0f, 1f, 0.05f,false, sliderSkin);
        soundMusicSlider.setValue(game.getSoundVolume());
        soundMusicSlider.addListener(event -> {
            game.setSoundVolume(soundMusicSlider.getValue());
            return false;
        });

        table.row().pad(0, 0, 10, 0);
        table.add(volumeMusicLabel).pad(0, 0, 0, 20);
        table.add(volumeMusicSlider);
        table.row();
        table.add(volumeSoundLabel).pad(0, 0, 0, 20);
        table.add(soundMusicSlider);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                changeCursorToDefault();
            }
        });

        InputListener inputListener = new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                // Change cursor to pointer when mouse enters the button
                changeCursorToPointer();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                // Change cursor back to default when mouse exits the button
                changeCursorToDefault();
            }
        };

        back.addListener(inputListener);
        volumeMusicSlider.addListener(inputListener);
        soundMusicSlider.addListener(inputListener);

        Gdx.input.setInputProcessor(stage);
    }


    /**
     * Change cursor to pointer.
     */
    private void changeCursorToPointer() {
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("cursor-png-1115.png")); // Replace with your pointer image path
        int desiredWidth = 32; // Desired width of the cursor
        int desiredHeight = 32; // Desired height of the cursor
        Pixmap resizedPixmap = new Pixmap(desiredWidth, desiredHeight, originalPixmap.getFormat());
        resizedPixmap.drawPixmap(originalPixmap, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(), 0, 0, desiredWidth, desiredHeight);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(resizedPixmap, 0, 0));
        originalPixmap.dispose();
        resizedPixmap.dispose();
    }

    /**
     * Change cursor to default.
     */
    private void changeCursorToDefault() {
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("cursor-png-1127.png")); // Replace with your default cursor image path
        int desiredWidth = 32; // Desired width of the cursor
        int desiredHeight = 32; // Desired height of the cursor
        Pixmap resizedPixmap = new Pixmap(desiredWidth, desiredHeight, originalPixmap.getFormat());
        resizedPixmap.drawPixmap(originalPixmap, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(), 0, 0, desiredWidth, desiredHeight);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(resizedPixmap, 0, 0));
        originalPixmap.dispose();
        resizedPixmap.dispose();
    }




    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(.1f, .1f, .15f, 1);  // screen color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Updates the size of the viewport.
     *
     * @param width of viewport
     * @param height of viewport
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Called when the application is paused
    }

    @Override
    public void resume() {
        // Called when the application is resumed from paused state
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
