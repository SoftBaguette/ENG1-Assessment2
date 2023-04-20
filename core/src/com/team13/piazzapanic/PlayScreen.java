package com.team13.piazzapanic;


import Sprites.*;
import Recipe.Order;
import Tools.B2WorldCreator;
import Tools.WorldContactListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The PlayScreen class is responsible for displaying the game to the user and handling the user's interactions.
 * The PlayScreen class implements the Screen interface which is part of the LibGDX framework.
 *
 * The PlayScreen class contains several important fields, including the game instance, stage instance, viewport instance,
 * and several other helper classes and variables. The game instance is used to access the global game configuration and
 * to switch between screens. The stage instance is used to display the graphics and handle user interactions, while the
 * viewport instance is used to manage the scaling and resizing of the game window.
 *
 * The PlayScreen class also contains several methods for initializing and updating the game state, including the
 * constructor, show(), render(), update(), and dispose() methods. The constructor sets up the stage, viewport, and
 * other helper classes and variables. The show() method is called when the PlayScreen becomes the active screen. The
 * render() method is called repeatedly to render the game graphics and update the game state. The update() method is
 * called to update the game state and handle user inputs. The dispose() method is called when the PlayScreen is no longer
 * needed and is used to clean up resources and prevent memory leaks.
 */


public class PlayScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera gamecam;
    private final Viewport gameport;
    private final HUD hud;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private final World world;
    private final Chef chef1;
    private final Chef chef2;

    private Chef controlledChef;

    public ArrayList<Order> ordersArray;

    public PlateStation plateStation;

    public B2WorldCreator b2world;
    public ArrayList<InteractiveTileObject> tile_objects;

    public Boolean scenarioComplete;
    public Boolean createdOrder;

    public static float trayX;
    public static float trayY;

    private float timeSeconds = 0f;

    private float timeSecondsCount = 0f;
    public Customer customer1;
    public Customer[] customers = new Customer[5];
    public static int current_customer = 0;
    public int last_customer;

    public static Boolean endless = false;
    public String difficulty = "";
    public static Integer reputation = 2;
    public static int money;

    public Boolean one_customer = true;


    /** Because we are saving using CSV files, we will be reading in Strings.
     * Therefore we need a way of mapping each purchasable station with a string, so that we can write
     * e.g. "oven1" in the CSV file and the code knows which InteractiveTileObject it's referring to.
     * Each purchasable station has attribute String name. The name should be unique.
     * String name is used for the key in this mapping. This means that stations can be uniquely identified by their
     * strings in stringsToStations, and strings/names that will correspond to the station are written to the file by getName().
     */
    // TODO: Define this Map in some sort of data file. Wherever a purchasable station is instantiated, you should also have stringsToStations.put(station.name, station)

    static Map<String, InteractiveTileObject> stringsToStations = new HashMap<String, InteractiveTileObject>();





    /** Reads a CSV of a game state and sets the class variables as appropriate.
     *  CSV format: money, reputation, purchased stations
     *  The HashMap stations is the same as stringsToStations. Once finalised, feel free to remove the parameter,
     *  but stringsToStations might be moved to a separate data file at a later stage.
     *
     */

    public void loadGameData(String filename, HashMap<String, InteractiveTileObject> stations) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // Read game data from CSV file
            String[] elements = reader.readLine().split(",");
            reputation = Integer.parseInt(elements[0]);
            money = (Integer.parseInt(elements[1]));

            String purchasedStationsString = elements[2];
            String[] purchasedStations = purchasedStationsString.split(";");
            for (String stationName : purchasedStations) {
                InteractiveTileObject station = stations.get(stationName);
                if (station != null) {
                    station.setPurchased(true);
                }
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading game data: " + e.getMessage());
        }
    }


    public void saveGameData(String filename, HashMap<String, InteractiveTileObject> stations) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            // Write game data to CSV file
            writer.write(getReputation() + "," + getMoney() + ",");
            StringBuilder purchasedStations = new StringBuilder();
            for (InteractiveTileObject station : stations.values()) {
                if (station.isPurchased() && station.purchasable) {
                    if (purchasedStations.length() > 0) {
                        purchasedStations.append(";");
                    }
                    purchasedStations.append(station.getName());
                }
            }
            writer.write(purchasedStations.toString());

            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving game data: " + e.getMessage());
        }
    }
    int getReputation() {return reputation;}
    int getMoney() {return money;}



    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */
    public PlayScreen(MainGame game){
        this.game = game;
        money = 0;
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gamecam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gameport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        // create HUD for score & time
        hud = new HUD(game.batch);
        // create orders hud
        Orders orders = new Orders(game.batch);
        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("NewKitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);
       
        b2world =  new B2WorldCreator(world, map, this);
        tile_objects = b2world.getTiles();
        for (InteractiveTileObject tile : tile_objects){
            System.out.println(tile.type);
        }

        timeSeconds = 0f;

        timeSecondsCount = 0f;
        customers = new Customer[5];
        current_customer = 0;
        reputation = 2;

        one_customer = true;



        chef1 = new Chef(this.world, 31.5F,65);
        chef2 = new Chef(this.world, 128,65);
        customers[0] = new Customer(167,15, difficulty, 60);
        customers[1] = new Customer(167,10, difficulty, 60);
        last_customer = 2;
        customer1 = new Customer(145,15, null, 100);
        controlledChef = chef1;
        world.setContactListener(new WorldContactListener());
        controlledChef.notificationSetBounds("Down");

        ordersArray = new ArrayList<>();

        reputation = 2;

    }


    // Saved game constructor; use generateSaveState to create this.SavedGame
    public PlayScreen(MainGame game, String SavedGame) {
        this.game = game;
        money = 0;
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gamecam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gameport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        // create HUD for score & time
        hud = new HUD(game.batch);
        // create orders hud
        Orders orders = new Orders(game.batch);
        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("NewKitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);

        b2world =  new B2WorldCreator(world, map, this);
        tile_objects = b2world.getTiles();
        for (InteractiveTileObject tile : tile_objects){
            System.out.println(tile.type);
        }

        timeSeconds = 0f;

        timeSecondsCount = 0f;
        customers = new Customer[5];
        current_customer = 0;
        reputation = 2;

        one_customer = true;



        chef1 = new Chef(this.world, 31.5F,65);
        chef2 = new Chef(this.world, 128,65);
        customers[0] = new Customer(167,15, difficulty, 60);
        customers[1] = new Customer(167,10, difficulty, 60);
        last_customer = 2;
        customer1 = new Customer(145,15, null, 100);
        controlledChef = chef1;
        world.setContactListener(new WorldContactListener());
        controlledChef.notificationSetBounds("Down");

        ordersArray = new ArrayList<>();

        reputation = 2;
    }

    @Override
    public void show(){

    }


    /**
     * The handleInput method is responsible for handling the input events of the game such as movement and interaction with objects.
     *
     * It checks if the 'R' key is just pressed and both chefs have the user control, if so,
     * it switches the control between the two chefs.
     *
     * If the controlled chef does not have the user control,
     * the method checks if chef1 or chef2 have the user control and sets the control to that chef.
     *
     * If the controlled chef has the user control,
     * it checks if the 'W', 'A', 'S', or 'D' keys are pressed and sets the velocity of the chef accordingly.
     *
     * If the 'E' key is just pressed and the chef is touching a tile,
     * it checks the type of tile and sets the chef's in-hands ingredient accordingly.
     *
     * The method also sets the direction of the chef based on its linear velocity.
     *
     * @param dt is the time delta between the current and previous frame.
     */

    public void handleInput(float dt){
        if ((Gdx.input.isKeyJustPressed(Input.Keys.R) &&
                chef1.getUserControlChef() &&
                chef2.getUserControlChef())) {
            if (controlledChef.equals(chef1)) {
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef2;
            } else {
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef1;
            }
        }
       
        controlledChef.move();

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                if(controlledChef.getTouchingTile() != null){
                    InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
                    String tileName = tile.getClass().getName();
                    //System.out.println(tileName);

                    if (tileName == "Sprites.InteractiveTileObject"){
                        if (tile.type == "Serving"){
                            if (customers[current_customer] != null){
                                tile.serving_interact(controlledChef, customers[current_customer], 0, current_customer);
                                System.out.println(current_customer);
                            }
                            
                        }else{
                            tile.interact(chef1);
                        }
                    }

                    
                    
                    if (tile.ingredient != null){
                        System.out.println(tile.ingredient.name);
                        if (tileName == "Sprites.IngredientStation"){
                            tile.interact(controlledChef);
                        }
                    }

                    

                }
                if (controlledChef.stack.getSize() == 0){
                    controlledChef.setChefSkin(null);
                }else{
                    controlledChef.setChefSkin(null);
                }

            }
        }

    /**
     * The update method updates the game elements, such as camera and characters,
     * based on a specified time interval "dt".
     * @param dt time interval for the update
    */
    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        renderer.setView(gamecam);
        chef1.update(dt);
        chef2.update(dt);
        if (chef1.getTouchingTile() != null){
            if (chef1.getTouchingTile().getUserData().getClass().getName() == "Sprites.ChoppingBoard"){
                InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
                tile.update(chef1);
                //tile.draw_progress_bar(game.batch);
            }
        }
        
        for (InteractiveTileObject tile : tile_objects){
            tile.update(controlledChef);
        }
        world.step(1/60f, 6, 2);
        hud.updateOrder(Boolean.FALSE, reputation);
    }

    /**
     * Creates the orders randomly and adds to an array, updates the HUD.
     */
    public void createOrder() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        Texture burger_recipe = new Texture("Food/burger_recipe.png");
        Texture salad_recipe = new Texture("Food/salad_recipe.png");
        Order order;

        for(int i = 0; i<5; i++){
            if(randomNum==1) {
                order = new Order(PlateStation.burgerRecipe, burger_recipe);
            }
            else {
                order = new Order(PlateStation.saladRecipe, salad_recipe);
            }
            ordersArray.add(order);
            randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        }
        hud.updateOrder(Boolean.FALSE, 1);
    }

    /**
     * Updates the orders as they are completed, or if the game scenario has been completed.
     */
    public void updateOrder(){
        if(scenarioComplete==Boolean.TRUE) {
            hud.updateScore(Boolean.TRUE, (6 - ordersArray.size()) * 35);
            hud.updateOrder(Boolean.TRUE, 0);
            return;
        }
        if(ordersArray.size() != 0) {
            if (ordersArray.get(0).orderComplete) {
                hud.updateScore(Boolean.FALSE, (6 - ordersArray.size()) * 35);
                ordersArray.remove(0);
                hud.updateOrder(Boolean.FALSE, 6 - ordersArray.size());
                return;
            }
            ordersArray.get(0).create(trayX, trayY, game.batch);
        }
    }

    /**

     The render method updates the screen by calling the update method with the given delta time, and rendering the graphics of the game.

     It updates the HUD time, clears the screen, and renders the renderer and the hud.

     Additionally, it checks the state of the game and draws the ingredients, completed recipes, and notifications on the screen.

     @param delta The time in seconds since the last frame.
     */
    @Override
    public void render(float delta){
        update(delta);

        //Execute handleEvent each 1 second
        timeSeconds +=Gdx.graphics.getRawDeltaTime();
        timeSecondsCount += Gdx.graphics.getDeltaTime();

        if (endless == false){
            if (HUD.worldTimerS == 59 && one_customer == true  && last_customer < 4){
                //TODO determine if scenario mode is over
                System.out.println("Created customer");
                System.out.println(last_customer);
                create_customer();
                
                one_customer = false;
            }else if (HUD.worldTimerS != 59){
                one_customer = true;
            }
        }else{
            if (HUD.worldTimerS == 59 && one_customer == true){
                System.out.println("Created customer");
                System.out.println(last_customer);
                if (last_customer == 4){
                    last_customer = 0;
                }
                create_customer();
                
                one_customer = false;
            }else if (HUD.worldTimerS != 59){
                one_customer = true;
            }
        }
        

        if(Math.round(timeSecondsCount) == 5 && createdOrder == Boolean.FALSE){
            createdOrder = Boolean.TRUE;
            //createOrder();
        }
        float period = 1f;
        if(timeSeconds > period) {
            timeSeconds -= period;
            hud.updateTime(scenarioComplete);
        }

        Gdx.gl.glClear(1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        updateOrder();
        //game.batch.draw(new Texture("Chef/Chef_normal.png"), chef1.getX(), chef1.getY(), chef1.getWidth(), chef1.getHeight());
        //chef1.draw_chef(game.batch);
        //System.out.println(Gdx.input.getX());
        //System.out.println(Gdx.input.getY());
        chef1.draw(game.batch);
        chef1.draw_item(game.batch);
        game.batch.draw(new Texture("Food/Lettuce.png"), (chef1.getX()*1.01f), chef1.getY(), chef1.getWidth()/2,chef1.getHeight()/2);
        chef2.draw(game.batch);

        controlledChef.drawNotification(game.batch);
        
        for (int i = 0; i < customers.length; i++) {
            if (customers[i] != null){
                customers[i].draw(game.batch);
                customers[i].move(i - current_customer, current_customer);

                if (customers[i].status == "destroy"){
                    customers[i] = null;
                }
                //System.out.println(current_customer);
            }
        }
        if (customers[current_customer] != null){
            customers[current_customer].draw_order(game.batch);
        }
        
        
        //customer1.draw(game.batch);
        //customer1.move(1, current_customer);
        //TODO check this section about drawing on plate station
        for (InteractiveTileObject tile : tile_objects){
            // if (tile.type != "Oven"){
            //     tile.draw_item_on_station(game.batch);
            // }
            tile.draw_item_on_station(game.batch);
            
        }
        /*
        if (plateStation.getPlate().size() > 0){
            for(Object ing : plateStation.getPlate()){
                Ingredient ingNew = (Ingredient) ing;
                ingNew.create(plateStation.getX(), plateStation.getY(),game.batch);
            }
        } else if (plateStation.getCompletedRecipe() != null){
            Recipe recipeNew = plateStation.getCompletedRecipe();
            recipeNew.create(plateStation.getX(), plateStation.getY(), game.batch);
        }*/

        if (!chef1.getUserControlChef()) {
            if (chef1.getTouchingTile() != null && chef1.getInHandsIng() != null){
                if (chef1.getTouchingTile().getUserData() instanceof InteractiveTileObject){
                    chef1.displayIngStatic(game.batch);
                }
            }
        }
        if (!chef2.getUserControlChef()) {
            if (chef2.getTouchingTile() != null && chef2.getInHandsIng() != null) {
                if (chef2.getTouchingTile().getUserData() instanceof InteractiveTileObject) {
                    chef2.displayIngStatic(game.batch);
                }
            }
        }
        // if (chef1.previousInHandRecipe != null){
        //     chef1.displayIngDynamic(game.batch);
        // }
        // if (chef2.previousInHandRecipe != null){
        //     chef2.displayIngDynamic(game.batch);
        // }
        for (InteractiveTileObject tile : tile_objects){
            tile.draw_progress_bar(game.batch);
        }
        game.batch.end();
    }


    public void create_customer(){
        if (current_customer >= customers.length){
            System.out.println("AHAHHAHH");

        }else{
            Random r = new Random();
            int random_num = r.nextInt(100);
            customers[last_customer] = new Customer(167,15, difficulty, 60);
            last_customer += 1;
            if (last_customer == customers.length){
                last_customer = customers.length - 1;
            }
            if (random_num > 90 && last_customer != customers.length -1){
                customers[last_customer] = new Customer(167,-5, difficulty, 60);
                last_customer += 1;
                if (last_customer == customers.length){
                    last_customer = customers.length - 1;
                }
            }else if (random_num > 95 && last_customer != customers.length -1){
                customers[last_customer] = new Customer(167,-25, difficulty, 60);
                last_customer += 1;
                if (last_customer == customers.length){
                    last_customer = customers.length - 1;
                }
            }
        }
        
    }

    @Override
    public void resize(int width, int height){
        gameport.update(width, height);
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){
        
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        world.dispose();
        hud.dispose();
    }
}
