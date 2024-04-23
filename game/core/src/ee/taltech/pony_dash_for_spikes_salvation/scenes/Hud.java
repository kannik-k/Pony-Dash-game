package ee.taltech.pony_dash_for_spikes_salvation.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer coins;
    private boolean isKeyCollected;

    private Label timerLabel;
    private Label coinAmountLabel;
    private Label timeLabel;
    private Label isKeyLabel;
    private Label keyLabel;
    private Label coinLabel;

    public Integer getCoins() {
        return coins;
    }

    public boolean isKeyCollected() {
        return isKeyCollected;
    }

    public Hud(SpriteBatch sb) {
        worldTimer = 0;
        timeCount = 0;
        coins = 0;
        isKeyCollected = false;

        viewport = new FitViewport(Main.V_WIDTH, Main.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        timerLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinAmountLabel = new Label(String.format("%02d", coins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        isKeyLabel = new Label("0", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        keyLabel = new Label("Key", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinLabel = new Label("Coins", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(coinLabel).expandX().padTop(10);
        table.add(keyLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(coinAmountLabel).expandX();
        table.add(isKeyLabel).expandX();
        table.add(timerLabel).expandX();

        stage.addActor(table);
    }
    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer++;
            timerLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public void addScore(int value) {
        coins += value;
        coinAmountLabel.setText(String.format("%02d", coins));
    }

    public void addKey() {
        isKeyLabel.setText("1");
        isKeyCollected = true;
    }

    // @Override
    public void dispose() {
        stage.dispose();
    }

}
