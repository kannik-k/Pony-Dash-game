package ee.taltech.pony_dash_for_spikes_salvation;

import ee.taltech.pony_dash_for_spikes_salvation.sprites.PonySprite;

public class Player {
    private String playerName;
    private float x = 0.32f;
    private float y = 0.8f;
    private PonySprite sprite;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public PonySprite getSprite() {
        return sprite;
    }

    public void setSprite(PonySprite sprite) {
        this.sprite = sprite;
    }
}
