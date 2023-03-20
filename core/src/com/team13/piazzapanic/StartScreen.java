package com.team13.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;

/**
 * This class implements the `Screen` interface and represents the start screen of the game.
 */
public class StartScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    //button 1 = Easy
    //Button 2 = Medium
    //button 3 = hard
    //Button 4 = Scenario
    //Button 5 = Endless
    //Button 6 = load
    //Button 7 = Start


    int[][] buttons = {{80,130,25,25,0},{80,100,25,25,0}, {80,70,25,25,0}, {20,30,25,25,0}, {80,30,25,25,0}, {140,30,25,25,0}};
    int current_button = 0;

    /**
     * Constructor for StartScreen.
     *
     * @param game the game object.
     */
    public StartScreen(MainGame game) {
        this.game = game;
        backgroundImage = new Texture("startImage.png");
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

        game.batch.draw(new Texture("GreenButton.png"),buttons[current_button][0], buttons[current_button][1],buttons[current_button][2]+5, buttons[current_button][3]+5);

        for (int[] button : buttons) {
            if (button[4] == 1){
                game.batch.draw(new Texture("GreenButton.png"),button[0], button[1],button[2]+5, button[3]+5);
            }
            game.batch.draw(new Texture("BlackButton.png"),button[0]+2.5f, button[1]+2.5f,button[2], button[3]);
        }

        game.batch.end();
        update();
    }

    
    public void update(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) ){
            if(current_button >0){
                current_button --;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S) ){
            if(current_button < buttons.length-1 ){
                current_button ++;
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ){
            if (current_button >=0 && current_button < 3){
                buttons[0][4] = 0;
                buttons[1][4] = 0;
                buttons[2][4] = 0;
                buttons[current_button][4] = 1;
            }else if (current_button >=3 && current_button < 6){
                buttons[3][4] = 0;
                buttons[4][4] = 0;
                buttons[5][4] = 0;
                buttons[current_button][4] = 1;
            }
            
        }

        for (int[] button : buttons) {
            //System.err.println(MainGame.V_WIDTH);

            //System.out.println(Gdx.input.getX());
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
