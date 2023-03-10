package Sprites;


import Ingredients.*;
import Recipe.Recipe;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Chef class extends {@link Sprite} and represents a chef in the game.
 * It has fields for the world it exists in, a Box2D body, the initial X and Y
 * positions, a wait timer, collision flag, various textures for different skins,
 * state (UP, DOWN, LEFT, RIGHT), skin needed, fixture of what it is touching, ingredient
 * and recipe in hand, control flag, circle sprite, chef notification X, Y, width and height,
 * and completed dish station.
 */


public class Chef extends Sprite {
    public World world;
    public Body b2body;

    public Stack stack;

    private final float initialX;
    private final float initialY;


    public Vector2 startVector;
    private float waitTimer;


    private float putDownWaitTimer;
    public boolean chefOnChefCollision;

    private final Texture normalChef;
    private final Texture holdingChef;

    public enum State {UP, DOWN, LEFT, RIGHT}

    public State currentState;
    private TextureRegion currentSkin;


    private Texture skinNeeded;


    private Fixture whatTouching;


    public Ingredient inHandsIng;
    private Recipe inHandsRecipe;


    private Boolean userControlChef;
    public Boolean chefMove;


    private final Sprite circleSprite;


    private float notificationX;
    private float notificationY;
    private float notificationWidth;
    private float notificationHeight;


    private CompletedDishStation completedStation;


    public int nextOrderAppearTime;
    public Recipe previousInHandRecipe;


    // Used for Testing
    public Chef(){
        initialX = 2 / MainGame.PPM;
        initialY = 2 / MainGame.PPM;

        stack = new Stack(3);

        normalChef = null;
        holdingChef = null;
        circleSprite = null;
        
        /*
        normalChef = new Texture("Chef/Chef_normal.png");
        holdingChef = new Texture("Chef/Chef_holding.png");
        skinNeeded = normalChef;*/
        currentState = State.DOWN;
        //defineChef();
        float chefWidth = 13 / MainGame.PPM;
        float chefHeight = 20 / MainGame.PPM;
        setBounds(0, 0, chefWidth, chefHeight);
        chefOnChefCollision = false;
        waitTimer = 0;
        putDownWaitTimer = 0;
        startVector = new Vector2(0, 0);
        whatTouching = null;
        inHandsIng = null;
        inHandsRecipe = null;
        userControlChef = true;
        chefMove = true;

        //Texture circleTexture = new Texture("Chef/chefIdentifier.png");
        //circleSprite = new Sprite(circleTexture);
        nextOrderAppearTime = 3;
        completedStation = null;
    }


    /**
     * Chef class constructor that initializes all the fields
     * @param world the world the chef exists in
     * @param startX starting X position
     * @param startY starting Y position
     */


    public Chef(World world, float startX, float startY) {
        initialX = startX / MainGame.PPM;
        initialY = startY / MainGame.PPM;

        stack = new Stack(3);

        normalChef = new Texture("Chef/Chef_normal.png");
        holdingChef = new Texture("Chef/Chef_holding.png");

        skinNeeded = normalChef;

        this.world = world;
        currentState = State.DOWN;

        defineChef();

        float chefWidth = 13 / MainGame.PPM;
        float chefHeight = 20 / MainGame.PPM;
        setBounds(0, 0, chefWidth, chefHeight);
        chefOnChefCollision = false;
        waitTimer = 0;
        putDownWaitTimer = 0;
        startVector = new Vector2(0, 0);
        whatTouching = null;
        inHandsIng = null;
        inHandsRecipe = null;
        userControlChef = true;
        chefMove = true;
        Texture circleTexture = new Texture("Chef/chefIdentifier.png");
        circleSprite = new Sprite(circleTexture);
        nextOrderAppearTime = 3;
        completedStation = null;
    }


   


    /**
     * Update the position and region of the chef and set the notification position based on the chef's current state.
     *
     * @param dt The delta time.
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        currentSkin = getSkin(dt);
        setRegion(currentSkin);
        switch (currentState) {
            case UP:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x - (1.75f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (7.7f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x - (0.67f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (7.2f / MainGame.PPM);
                }
                break;
            case DOWN:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x + (0.95f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x + (0.55f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (5.3f / MainGame.PPM);
                }
                break;
            case LEFT:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x;
                    notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x - (1.92f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (4.6f / MainGame.PPM);
                }
                break;
            case RIGHT:
                if (this.inHandsIng == null && this.inHandsRecipe == null) {
                    notificationX = b2body.getPosition().x + (0.5f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (5.015f / MainGame.PPM);
                } else {
                    notificationX = b2body.getPosition().x + (0.17f / MainGame.PPM);
                    notificationY = b2body.getPosition().y - (4.63f / MainGame.PPM);
                }
                break;
        }


        //TODO change this
        if (!userControlChef && chefOnChefCollision) {
            waitTimer += dt;
            b2body.setLinearVelocity(new Vector2(startVector.x * -1, startVector.y * -1));
            if (waitTimer > 0.3f) {
                b2body.setLinearVelocity(new Vector2(0, 0));
                chefOnChefCollision = false;
                userControlChef = true;
                waitTimer = 0;
                if (inHandsIng != null) {
                    setChefSkin(inHandsIng);
                }
            }
        } else if (!userControlChef && getInHandsIng().prepareTime > 0) {
            waitTimer += dt;
            if (waitTimer > inHandsIng.prepareTime) {
                inHandsIng.prepareTime = 0;
                inHandsIng.setPrepared();
                userControlChef = true;
                waitTimer = 0;
                setChefSkin(inHandsIng);
            }
        } else if (!userControlChef && !chefOnChefCollision && getInHandsIng().isPrepared() && inHandsIng.cookTime > 0) {
            waitTimer += dt;
            if (waitTimer > inHandsIng.cookTime) {
                inHandsIng.cookTime = 0;
                inHandsIng.setCooked();
                userControlChef = true;
                waitTimer = 0;
                setChefSkin(inHandsIng);
            }
        }
    }


    public void move(){
        //TODO check this thing
        if (!this.getUserControlChef()){
            if (this.getUserControlChef()){
                this.b2body.setLinearVelocity(0, 0);
            }
        }

        setChefSkin(getInHandsIng());
        if (this.getUserControlChef()) {
                float xVelocity = 0;
                float yVelocity = 0;

                float speed_multiplier = 1;

                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    yVelocity += 0.5f * speed_multiplier;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    xVelocity -= 0.5f* speed_multiplier;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    yVelocity -= 0.5f* speed_multiplier;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    xVelocity += 0.5f* speed_multiplier;
                }
                this.b2body.setLinearVelocity(xVelocity, yVelocity);
            }
            else {
                this.b2body.setLinearVelocity(0, 0);
            }
        if (this.b2body.getLinearVelocity().x > 0){
            this.notificationSetBounds("Right");
        }
        if (this.b2body.getLinearVelocity().x < 0){
            this.notificationSetBounds("Left");
        }
        if (this.b2body.getLinearVelocity().y > 0){
            this.notificationSetBounds("Up");
        }
        if (this.b2body.getLinearVelocity().y < 0){
            this.notificationSetBounds("Down");
        }

        // The chef can't move if they are interacting with a chopping station
        if (chefMove == false){
            this.b2body.setLinearVelocity(0, 0);
        }
    }


    /**
     * This method sets the bounds for the notification based on the given direction.
     * @param direction - A string representing the direction of the notification.
     *                   Can be "Left", "Right", "Up", or "Down".
     */
    public void notificationSetBounds(String direction) {
        switch (direction) {
            case "Left":
            case "Right":
                notificationWidth = 1.5f / MainGame.PPM;
                notificationHeight = 1.5f / MainGame.PPM;
                break;
            case "Up":
                notificationWidth = 4 / MainGame.PPM;
                notificationHeight = 4 / MainGame.PPM;
                break;
            case "Down":
                notificationWidth = 2 / MainGame.PPM;
                notificationHeight = 2 / MainGame.PPM;
                break;
        }
    }


    /**
     Draws a notification to help the user understand what chef they are controlling.
     The notification is a sprite that looks like at "C" on the controlled chef.
     @param batch The sprite batch that the notification should be drawn with.
     */
    public void drawNotification(SpriteBatch batch) {
        if (this.getUserControlChef()) {
            circleSprite.setBounds(notificationX, notificationY, notificationWidth, notificationHeight);
            circleSprite.draw(batch);
        }
    }


    /**
     * Get the texture region for the current state of the player.
     *
     * @param dt the time difference between this and the last frame
     * @return the texture region for the player's current state
     */
    private TextureRegion getSkin(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case UP:
                region = new TextureRegion(skinNeeded, 0, 0, 33, 46);
                break;
            case DOWN:
                region = new TextureRegion(skinNeeded, 33, 0, 33, 46);
                break;
            case LEFT:
                region = new TextureRegion(skinNeeded, 64, 0, 33, 46);
                break;
            case RIGHT:
                region = new TextureRegion(skinNeeded, 96, 0, 33, 46);
                break;
            default:
                region = currentSkin;
        }
        return region;
    }

    /**
     Returns the current state of the player based on the controlled chefs velocity.
     @return current state of the player - UP, DOWN, LEFT, or RIGHT
     */
    public State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.UP;
        if (b2body.getLinearVelocity().y < 0)
            return State.DOWN;
        if (b2body.getLinearVelocity().x > 0)
            return State.RIGHT;
        if (b2body.getLinearVelocity().x < 0)
            return State.LEFT;
        else
            return currentState;
    }


    /**
     * Define the body and fixture of the chef object.
     *
     * This method creates a dynamic body definition and sets its position with the `initialX` and `initialY`
     * variables, then creates the body in the physics world. A fixture definition is also created and a
     * circle shape is set with a radius of `4.5f / MainGame.PPM` and a position shifted by `(0.5f / MainGame.PPM)`
     * in the x-axis and `-(5.5f / MainGame.PPM)` in the y-axis. The created fixture is then set as the user data
     * of the chef object.
     */
    public void defineChef() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(initialX, initialY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4.5f / MainGame.PPM);
        shape.setPosition(new Vector2(shape.getPosition().x + (0.5f / MainGame.PPM), shape.getPosition().y - (5.5f / MainGame.PPM)));

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }




    /**
     * Method to set the skin of the chef character based on the item the chef is holding.
     *
     * @param item the item that chef is holding
     *
     * The skin is set based on the following cases:
     * - if item is null, then the skin is set to normalChef
     * - if item is not null, then the skin is set to holdingChef
     */
    public void setChefSkin(Object item) {
        if (item == null) {
            skinNeeded = normalChef;
        }else{
            skinNeeded = holdingChef;
        }
    }


    public void draw_item(Batch batch){
        float offset = 0;
        for (Ingredient chef_ing : stack.arr) {
            if (chef_ing !=null){
                ArrayList<Texture> texture = AllTextures.getTextures(chef_ing.name);
                batch.draw(texture.get(chef_ing.status), this.getX(),(float) this.getY() + offset, this.getWidth(),this.getHeight());
                offset +=0.1f;

            }
        }

        // Ingredient chefs_ingredient = this.getInHandsIng();
        // if (chefs_ingredient != null){
        //     ArrayList<Texture> texture = AllTextures.getTextures(chefs_ingredient.name);
        //     //batch.draw(texture.get(chefs_ingredient.status), this.getX(), this.getY(), this.getWidth(),this.getHeight());
        //     //batch.draw(chefs_ingredient.tex.get(this.getInHandsIng().status), this.getX(), this.getY(), this.getWidth(),this.getHeight());
        // }
    }


    /**
     * Method to display the ingredient on the specific interactive tile objects (ChoppingBoard/Pan)
     * @param batch the SpriteBatch used to render the texture.
     */

    public void displayIngStatic(SpriteBatch batch) {
        Gdx.app.log("", inHandsIng.toString());
        if (whatTouching != null && !chefOnChefCollision) {
            //TODO update displaying Ingredient

            
            /*
            InteractiveTileObject tile = (InteractiveTileObject) whatTouching.getUserData();
            if (tile instanceof ChoppingBoard) {
                ChoppingBoard tileNew = (ChoppingBoard) tile;
                inHandsIng.create(tileNew.getX() - (0.5f / MainGame.PPM), tileNew.getY() - (0.2f / MainGame.PPM), batch);
                setChefSkin(null);
            } else if (tile instanceof Pan) {
                Pan tileNew = (Pan) tile;
                inHandsIng.create(tileNew.getX(), tileNew.getY() - (0.01f / MainGame.PPM), batch);
                setChefSkin(null);
            }*/
        }
    }


    /**
     * The method creates an instance of the recipe and sets its position on the completed station coordinates.
     * The method also implements a timer for each ingredient which gets removed from the screen after a certain amount of time.
     *
     * @param batch The batch used for drawing the sprite on the screen
     */

    public void displayIngDynamic(SpriteBatch batch){
        putDownWaitTimer += 1/60f;
        previousInHandRecipe.create(completedStation.getX(), completedStation.getY() - (0.01f / MainGame.PPM), batch);
        if (putDownWaitTimer > nextOrderAppearTime) {
            previousInHandRecipe = null;
            putDownWaitTimer = 0;
        }
    }


    /**
      * This method updates the state of the chef when it is in a collision with another chef.
      * The method sets the userControlChef to false, meaning the user cannot control the chef while it's in collision.
      * It also sets the chefOnChefCollision to true, indicating that the chef is in collision with another chef.
      * Finally, it calls the setStartVector method to update the position of the chef.
     */
        public void chefsColliding () {
            userControlChef = false;
            chefOnChefCollision = true;
            setStartVector();
        }

    /**
     * Set the starting velocity vector of the chef
     * when the chef collides with another chef
     *
     */
    public void setStartVector () {
        startVector = new Vector2(b2body.getLinearVelocity().x, b2body.getLinearVelocity().y);
    }


    /**
     * Set the touching tile fixture
     *
     * @param obj fixture that the chef is touching
     */
    public void setTouchingTile (Fixture obj){
        this.whatTouching = obj;
    }


    /**
     * Get the fixture that the chef is touching
     *
     * @return the fixture that the chef is touching
     */
    public Fixture getTouchingTile () {
        if (whatTouching == null) {
            return null;
        } else {
            return whatTouching;
        }
    }

    /**
     * Get the ingredient that the chef is holding
     *
     * @return the ingredient that the chef is holding
     */
    public Ingredient getInHandsIng () {
        return inHandsIng;
    }


    /**
     * Get the recipe that the chef is holding
     *
     * @return the recipe that the chef is holding
     */
    public Recipe getInHandsRecipe () {
        return inHandsRecipe;
    }


    /**
     * Set the ingredient that the chef is holding
     *
     * @param ing the ingredient that the chef is holding
     */
    public void setInHandsIng (Ingredient ing){
        inHandsIng = ing;
        inHandsRecipe = null;
    }


    /**
     * Set the recipe that the chef is holding
     *
     * @param recipe the recipe that the chef is holding
     */
    public void setInHandsRecipe (Recipe recipe){
        inHandsRecipe = recipe;
        inHandsIng = null;
    }


    /**
     * Set the chef's control by the user
     *
     * @param value whether the chef is controlled by the user
     */
    public void setUserControlChef ( boolean value){
        userControlChef = value;


    }


    /**


     * Returns a boolean value indicating whether the chef is under user control.
     * If not specified, returns false.
     *
     * @return userControlChef The boolean value indicating chef's control.
     */
    public boolean getUserControlChef () {
            return Objects.requireNonNullElse(userControlChef, false);
        }




    /**
      * Drops the given ingredient on a plate station.
      * @param station The plate station to drop the ingredient on.
      * @param ing The ingredient to be dropped.
     */


    public void dropItemOn (InteractiveTileObject station, Ingredient ing){
        if (station instanceof PlateStation) {
                ((PlateStation) station).dropItem(ing);
        }
        setInHandsRecipe(null);
    }


    /**
     * Drops the in-hand recipe on a completed dish station and saves the previous in-hand recipe.
     *
     * @param station The completed dish station to drop the recipe on.
     */
        public void dropItemOn (InteractiveTileObject station){
            if (station instanceof CompletedDishStation) {
                previousInHandRecipe = getInHandsRecipe();
                completedStation = (CompletedDishStation) station;
            }
            setInHandsRecipe(null);
        }


    /**
     * Picks up an item from a plate station and sets it as in-hand ingredient or recipe.
     *
     * @param station The plate station to pick up the item from.
     */
    public void pickUpItemFrom(InteractiveTileObject station){
        if (station instanceof PlateStation){
            PlateStation pStation = (PlateStation) station;
            Object item = pStation.pickUpItem();
            if (item instanceof Ingredient){
                setInHandsIng((Ingredient) item);
                setChefSkin(item);
            } else if (item instanceof Recipe){
                setInHandsRecipe(((Recipe) item));
                setChefSkin(item);
            }
        }
    }
}









