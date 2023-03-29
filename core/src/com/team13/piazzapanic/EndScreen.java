package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;

/**
 * This class implements the `Screen` interface and represents the start screen of the game.
 */
public class EndScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;


    public static Boolean restart = false;

    //button 1 = restart
    //button 2 = quit

    int[][] buttons = {{80,130,25,25,1},{80,100,25,25,0}};
    int current_button = 0;
    String[] button_text = {"RESTART", "QUIT"};

    BitmapFont button_font = new BitmapFont();

    Texture GreenButton = new Texture("GreenButton.png");
    Texture BlackButton = new Texture("BlackButton.png");
    Texture RedButton = new Texture("RedButton.png");

    /**
     * Constructor for StartScreen.
     *
     * @param game the game object.
     */
    public EndScreen(MainGame game) {
        this.game = game;
        backgroundImage = new Texture("GameoverScreen.png");
        backgroundSprite = new Sprite(backgroundImage);
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
    }

    /**
     * Method called when the screen is shown.
     * Initializes the sprite and camera position.
     */
    @Override
    public void show() {
        backgroundSprite.setSize(MainGame.V_WIDTH, MainGame.V_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    /**
     * Method to render the screen.
     * Clears the screen and draws the background sprite.
     *
     * @param delta the time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        backgroundSprite.draw(game.batch);

        BitmapFont button_font = new BitmapFont();
        
        for (int i = 0; i < button_text.length; i++) {
            game.batch.draw(BlackButton,buttons[i][0]+2.5f, buttons[i][1]+2.5f,buttons[i][2], buttons[i][3]);
            
            button_font.draw(game.batch, button_text[i], buttons[i][0], buttons[i][1]+buttons[i][3]);
        }

        game.batch.draw(RedButton,buttons[current_button][0], buttons[current_button][1],buttons[current_button][2]+5, buttons[current_button][3]+5);
        game.batch.draw(BlackButton,buttons[current_button][0]+2.5f, buttons[current_button][1]+2.5f,buttons[current_button][2], buttons[current_button][3]);
        button_font.draw(game.batch, button_text[current_button], buttons[current_button][0], buttons[current_button][1]+buttons[current_button][3]);

        game.batch.end();
        update();
    }

    
    public void update(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) ){
            current_button --;
            if (current_button < 0){
                current_button = buttons.length -1;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S) ){
            current_button ++;
            if (current_button > buttons.length -1){
                current_button = 0;
            }

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ){
            if (current_button == 0){
                restart = true;
            }
            else{

            }
        }


        


    }


    /**
     * Method called when the screen is resized.
     * Updates the viewport and camera position.
     *
     * @param width the new screen width.
     * @param height the new screen height.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    /**
     * Dispose method that is called when the screen is no longer used.
     * It is used to free up resources and memory that the screen was using.
     */
    @Override
    public void dispose() {
        backgroundImage.dispose();
    }
}
