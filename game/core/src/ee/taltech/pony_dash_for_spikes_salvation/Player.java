package ee.taltech.pony_dash_for_spikes_salvation;

import ee.taltech.pony_dash_for_spikes_salvation.sprites.PonySprite;

public class Player {
    private String playerName;
    private float x = 0.32f; // Box2D world coordinates
    private float y = 0.8f;
    private int tiledX = Math.round(x * 100); // PPM = 100, this is in pixels
    private int tiledY = Math.round(y * 100); // In pixels
    private PonySprite sprite;
    private int spriteId;
    private int gameID;

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

    public int getSpriteId() {
        return spriteId;
    }

    public void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
    }

    public int getTiledX() {
        return tiledX;
    }

    public void setTiledX(int tiledX) {
        this.tiledX = tiledX;
    }

    public int getTiledY() {
        return tiledY;
    }

    public void setTiledY(int tiledY) {
        this.tiledY = tiledY;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
