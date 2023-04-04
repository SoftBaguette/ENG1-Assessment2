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
public class StartScreen implements Screen {
    private final MainGame game;
    private final Texture backgroundImage;
    private final Sprite backgroundSprite;
    private final Texture instructionImage;
    private final Sprite instructionSprite;
    private final OrthographicCamera camera;
    private final Viewport viewport;


    public String difficulty = "Easy";
    public Boolean endless = false;
    public Boolean load = false;
    public Boolean start = false;

    //button 1 = Easy
    //Button 2 = Medium
    //button 3 = hard
    //Button 4 = Scenario
    //Button 5 = Endless
    //Button 6 = load
    //Button 7 = Start


    int[][] buttons = {{15,100,40,20,1},{65,100,55,20,0}, {130,100,40,20,0}, {10,65,80,20,1}, {100,65,75,20,0}, {80,40,45,20,0}, {100,10,50,20,0}};
    int current_button = 0;
    String[] button_text = {"EASY", "MEDIUM", "HARD", "SCENARIO", "ENDLESS", "LOAD", "START"};

    BitmapFont button_font = new BitmapFont();

    Texture GreenButton = new Texture("GreenButton.png");
    Texture GreyButton = new Texture("GreyButton.png");
    Texture BlackButton = new Texture("BlackButton.png");
    Texture RedButton = new Texture("RedButton.png");


    Boolean show_instruction;
    Long startTime;
    

    /**
     * Constructor for StartScreen.
     *
     * @param game the game object.
     */
    public StartScreen(MainGame game) {
        this.game = game;
        backgroundImage = new Texture("StartMenuImage.png");
        backgroundSprite = new Sprite(backgroundImage);
        instructionImage = new Texture("startImage.png");
        instructionSprite = new Sprite(instructionImage);
        camera = new OrthographicCamera();
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        show_instruction = false;
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
        Gdx.gl.glClear(1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        backgroundSprite.draw(game.batch);

        

        for (int i = 0; i < button_text.length; i++) {
            if (buttons[i][4] == 1){
                game.batch.draw(GreenButton,buttons[i][0], buttons[i][1],buttons[i][2]+5, buttons[i][3]+5);
            }
            game.batch.draw(GreyButton,buttons[i][0]+2.5f, buttons[i][1]+2.5f,buttons[i][2], buttons[i][3]);
           
            
            button_font.draw(game.batch, button_text[i], buttons[i][0]+2.5f, buttons[i][1]+buttons[i][3]-1);
        }

        game.batch.draw(RedButton,buttons[current_button][0], buttons[current_button][1],buttons[current_button][2]+5, buttons[current_button][3]+5);
        game.batch.draw(GreyButton,buttons[current_button][0]+2.5f, buttons[current_button][1]+2.5f,buttons[current_button][2], buttons[current_button][3]);
        button_font.draw(game.batch, button_text[current_button], buttons[current_button][0]+2.5f, buttons[current_button][1]+buttons[current_button][3]-1);
        
        if (show_instruction){
            //instructionSprite.draw(game.batch);
            game.batch.draw(instructionImage, 0, 0, backgroundImage.getWidth()+30, backgroundImage.getHeight()+30);
        }

        game.batch.end();
        update();
    }

    
    public void update(){
        if (show_instruction){
            if (System.currentTimeMillis() - startTime > 3000){
                start = true;
            }
        }

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


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && show_instruction == false){
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
            

            if (current_button == 0){
                difficulty = "Easy";
            }else if (current_button == 1){
                difficulty = "Medium";
            }else if (current_button == 2){
                difficulty = "Hard";
            }else if (current_button == 3){
                endless = false;
            }else if (current_button == 4){
                endless = true;
            }else if (current_button == 5){
                load = true;
            }else if (current_button == 6){
                show_instruction = true;
                startTime = System.currentTimeMillis();
                //start = true;
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
