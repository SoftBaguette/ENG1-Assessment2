package Sprites;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.utils.Array;
import com.team13.piazzapanic.MainGame;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;
import com.team13.piazzapanic.PlayScreen;


public class InteractiveTileObject {
   
    protected Fixture fixture;


    public BodyDef bdefNew;
    public Ingredient ingredient;
    public String type;
    public String name;
    public int price;

    long start_time_interaction;
    boolean interacting;
    Ingredient item_on_station;
    ProgressBar progressBar;
    int progress;
    public boolean purchasable;
    public boolean isPurchased;
    long start_time_burning;
    boolean burning;
    float burn_time = 5000f;
    


    ArrayList<Ingredient> plate_items;

    public Texture padlock_texture;
   


    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world The playable world.
     * @param map The tiled map.
     * @param bdef The body definition of a tile.
     * @param rectangle Rectangle shape.
     * @param type The type of interactibeTileObject:
     *             - Chopping - Chopping board
     *             - Pan - Frying Pan
     *             - Oven - Oven
     *             - Plate - Place to assemble the food
     */

    // This constructor is only used for stations that exist initially in the game
    public InteractiveTileObject(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, String type) {


        bdefNew = bdef;
        // No need to set a price.
        price = 0;
        Body b2body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((rectangle.getWidth() / 2f) / MainGame.PPM, (rectangle.getHeight() / 2f) / MainGame.PPM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        ingredient = null;
        this.type = type;
        interacting = false;
        burning = false;
        item_on_station = null;
        isPurchased = true;
       
        progressBar = new ProgressBar(0.5f, 0.5f, 0.25f,0.075f);
       
        plate_items = new ArrayList<>();
        purchasable = false;

        padlock_texture = new Texture("PadLock.png");
       
    }
    // Use this constructor for purchasable stations
    public InteractiveTileObject(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, String type, boolean isPurchased, int price) {

        purchasable = true;
        this.isPurchased = isPurchased;
        bdefNew = bdef;
        this.price = price;
        Body b2body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((rectangle.getWidth() / 2f) / MainGame.PPM, (rectangle.getHeight() / 2f) / MainGame.PPM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        ingredient = null;
        this.type = type;
        interacting = false;
        burning = false;
        item_on_station = null;

        progressBar = new ProgressBar(0.5f, 0.5f, 0.25f,0.075f);

        plate_items = new ArrayList<>();

        padlock_texture = new Texture("PadLock.png");


    }


    /**
     * Constructor for the class without b2bodies and textures which causes issues when testing.
     *
     * @param type The type of interactibeTileObject:
     *             - Chopping - Chopping board
     *             - Pan - Frying Pan
     *             - Oven - Oven
     *             - Plate - Place to assemble the food
     */
    public InteractiveTileObject(String type){
        this.type = type;
        ingredient = null;
        this.type = type;
        interacting = false;
        item_on_station = null;
        progress = 0;
        isPurchased = true;


        plate_items = new ArrayList<>();
    }

    /**
     * Method that allows the chef to interact with a tile object. 
     * Depending on what type of interactive tile object it is will result in a different function being called
     * 
     * @param chef the chef that interacted with the station
     */
    public void interact(Chef chef){
        if (!isPurchased){return;}
        System.out.println("Interacted with " + type + " station");

        if (item_on_station  != null && interacting == false){
            System.out.println("Picked up");
            pickUpItem(chef);
        }else if (type == "Bin"){
            bin_interact(chef);
        }else if (type == "ChoppingBoard"){
            chopping_board_interact(chef);
        }else if (type == "Plate"){
            plate_interact(chef);
        } else if (type == "Pan"){
            pan_interact(chef);
        } else if (type == "Oven"){
            oven_interact(chef);
        }
    }


   /**
    * Updates the station if it is either burning or interacting. 
    * It is responsible for the progress bar and changing the status of the ingredient.
    * 
    * @param chef TODO is this param needed
    */
    public void update(Chef chef){

        if(!isPurchased){return;}

        
        if (interacting == true && burning == false){
            float percent = (float) (System.currentTimeMillis() - start_time_interaction+1)/(item_on_station.prepareTime *1000);
            progress = (int) (percent*100);
            if (System.currentTimeMillis() - start_time_interaction > (item_on_station.prepareTime*1000)){
                if (item_on_station.isCooked() == false && item_on_station.isPrepared() == true){
                    item_on_station.setCooked();
                }
                else if (item_on_station.isPrepared() == false){
                    item_on_station.setPrepared();
                }
                item_on_station.status +=1;
                //chef.chefMove = true;
                interacting = false;
                progress = 0;
                System.out.println("Finished");
 
            }    
            if (item_on_station.isCooked() && item_on_station.name == "Steak"){
                burning = true;  
                progress = 0;
                start_time_burning = System.currentTimeMillis();
            }
        }
        if (burning == true){
            float burn_percent = (float) (System.currentTimeMillis() - start_time_burning+1)/(5000);
            progress = (int) (burn_percent*100);
            System.out.println(progress);
            System.out.println(System.currentTimeMillis() - start_time_burning);
            if (System.currentTimeMillis() - start_time_burning > (5000)){
                item_on_station.status +=1;
                burning = false;
                item_on_station.burnt = true;
                interacting = false;
            }
        
        }
    }

    /**
     * Draws the progress bar above the object.
     * If it is interacting, it will be green.
     * If it is burning, it will be red (indicating to the player that it is burning)
     * 
     * @param batch The batch used for drawing sprites to the screen
     */
    public void draw_progress_bar(Batch batch){
        if(!isPurchased){return;}


        if (interacting == true){
            progressBar.change_pos(getX(), getY());
            progressBar.setProgress(progress);
            progressBar.draw(batch, progress, "Green");
        }
        else if (burning == true){
            progressBar.change_pos(getX(), getY());
            progressBar.setProgress(progress);
            progressBar.draw(batch, progress, "Red");
        }
       
    }



    /**
     * This will bin the item that is at the top of the chef's stack. 
     * If the chef's stack is empty, nothing will happen
     * 
     * @param chef the chef that interacted with the station
     */
    public void bin_interact(Chef chef){
        if (chef.stack.isEmpty()){
            System.out.println("Stack Already Empty");
        }else{
            chef.stack.pop();
        }

        System.out.println("Binned");

    }

    /**
     * Responsibe for Chopping the set of items that need to be chopped for each recipe
     * Only raw things can be chopped (not chopped and not cooked)
     * 
     * The code will set interacting to true which is responsible for the progress bar and the timer determining when the item will be done.
     * 
     * 
     * @param chef the chef that interacted with the station
     */
    public void chopping_board_interact(Chef chef){
        //ChoppingBoardIngredeints:
        ArrayList<String> chopping_items = new ArrayList<>();
        chopping_items.add("Tomato");
        chopping_items.add("Lettuce");
        chopping_items.add("Onion");
        chopping_items.add("Cheese");
        chopping_items.add("Steak");
        
        if (chef.stack.isEmpty()){
            System.out.println("Stack empty");
        }else{
            if (chef.stack.peak().isPrepared() == false && chopping_items.contains(chef.stack.peak().name) && item_on_station == null){
                item_on_station = chef.stack.pop();
                //chef.chefMove = false;
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
        }
        

    }

    /**
     * Responsibe for Cooking the set of items that need to be cooked for each recipe
     * 
     * The code will set interacting to true which is responsible for the progress bar and the timer determining when the item will be done.
     * 
     * @param chef the chef that interacted with the station
     */
    public void pan_interact(Chef chef){
        //Pan ingredients:
        ArrayList<String> pan_items = new ArrayList<>();
        pan_items.add("Steak");
        pan_items.add("Burger_buns");

        if (chef.stack.isEmpty()){
            System.out.println("Stack empty");
        }else{
            if (chef.stack.peak().name == "Burger_buns"){
                chef.stack.arr[chef.stack.top].setPrepared();
            }
            if (chef.stack.peak().isPrepared() && chef.stack.peak().burnt == false && pan_items.contains(chef.stack.peak().name) && item_on_station == null){
                item_on_station = chef.stack.pop();
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
        }

    }

    /**
     * Responsibe for cooking the set of items that need to be cooked for each recipe
     * 
     * The code will set interacting to true which is responsible for the progress bar and the timer determining when the item will be done.
     * 
     * @param chef the chef that interacted with the station
     */
    public void oven_interact(Chef chef){
        ArrayList<String> oven_items = new ArrayList<>();
        oven_items.add("PotatoCheese");
        oven_items.add("Pizza");

        if (chef.stack.isEmpty()){
            System.out.println("Stack empty");
        }else{
            if (chef.stack.peak().isPrepared() == false && oven_items.contains(chef.stack.peak().name) && item_on_station == null){
                item_on_station = chef.stack.pop();
                item_on_station.setPrepared();
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
        }


    }


    /**
     * Assembles ingredients together.
     * 
     * @param chef the chef that interacted with the station
     */
    public void plate_interact(Chef chef){
        if (chef.stack.isEmpty()){
            System.out.println("Stack empty");
        }else{
            if (chef.stack.peak().name == "Burger" ||chef.stack.peak().name == "Salad" ||chef.stack.peak().name == "Pizza" ||chef.stack.peak().name == "PotatoCheese"){
                System.out.println("Can't put premade items onto plate");
            }else{
                plate_items.add(chef.stack.pop());
                if (checkRecipeCreated()){
                    chef.stack.push(plate_items.get(0));
                    System.out.println(chef.getInHandsIng().name);
                    //item_on_station = plate_items.get(0);
                    plate_items = new ArrayList<>();
                }
            }
            
            
        }

        
    }  
   
    /**
     * Checks if all the ingredients on the plate tile object can make a recipe
     * 
     * @return true if a recipe has been made, false otherwise
     */
    public Boolean checkRecipeCreated(){
        Boolean item_made = false;
        System.out.println("CheckingRecipe");
        Set<String> salad_ingdredients = new HashSet<String>();
        salad_ingdredients.add("Tomato");
        salad_ingdredients.add("Lettuce");
        salad_ingdredients.add("Onion");
       
        Set<String> burger_ingredients = new HashSet<String>();
        burger_ingredients.add("Burger_buns");
        burger_ingredients.add("Steak");

        Set<String> pizza_ingredients = new HashSet<String>();
        pizza_ingredients.add("PizzaDough");
        pizza_ingredients.add("Cheese");
        pizza_ingredients.add("Tomato");

        Set<String> potato_ingredients = new HashSet<String>();
        potato_ingredients.add("Potato");
        potato_ingredients.add("Cheese");
        for (Ingredient ing : plate_items){
            if (ing != null){
                if (salad_ingdredients.contains(ing.name) && ing.status == 1){
                    salad_ingdredients.remove(ing.name);
                }
                if (salad_ingdredients.size() == 0){
                    plate_items = new ArrayList<>();
                    plate_items.add(new Ingredient("Salad", 0, 0,0, null));
                    item_made = true;
                    System.out.println("Made Salad");
                }


                if (burger_ingredients.contains(ing.name) && ing.isPrepared() && ing.isCooked() && ing.burnt == false){
                    burger_ingredients.remove(ing.name);
                }
                if(burger_ingredients.size() == 0){
                    plate_items = new ArrayList<>();
                    plate_items.add(new Ingredient("Burger", 0,0,0, null));
                    item_made = true;
                    System.out.println("Making a burger");
                }    
                
                if (pizza_ingredients.contains(ing.name)){
                    if (ing.name == "Cheese" || ing.name == "Tomato"){
                        if (ing.isPrepared()){
                            pizza_ingredients.remove(ing.name);
                        }
                    }else if (ing.name == "PizzaDough"){
                        pizza_ingredients.remove(ing.name);
                    }
                }

                if (pizza_ingredients.size()==0){
                    plate_items = new ArrayList<>();
                    plate_items.add(new Ingredient("Pizza", 0, 3, 3, null));
                    item_made = true;
                }
                if (potato_ingredients.contains(ing.name)){
                    if ((ing.name == "Cheese" && ing.isPrepared()) || ing.name == "Potato"){
                        potato_ingredients.remove(ing.name);
                    }
                }
                
                if (potato_ingredients.size() == 0){
                    plate_items = new ArrayList<>();
                    plate_items.add(new Ingredient("PotatoCheese", 0, 3, 3, null));
                    item_made = true;
                    System.out.println("Potato made");
                }
                
            }
        }
        return item_made;
    }

    /**
     * Items can be picked up and added to the chef's stack if it isn't interacting 
     * 
     * @param chef the chef that interacted with the station
     */
    public void pickUpItem(Chef chef){



        //chef.setInHandsIng(item_on_station);
        if (chef.stack.isFull()){
            System.out.println("stack full");
        }else{
            chef.stack.push(item_on_station);
            item_on_station = null;
            burning = false;
        }
        
        
    }

    public void completed_dish(){
        Set<String> available_dishes = new HashSet<>();
        available_dishes.add("Burger");
        available_dishes.add("Salad");

    }

    /**
     * This will draw the item (or items) currently on the station
     * 
     * @param batch The batch used for drawing sprites to the screen
     */
    public void draw_item_on_station(SpriteBatch batch){
        if (plate_items.size() > 0){
            for(Object ing : plate_items){
                Ingredient ingNew = (Ingredient) ing;
                ingNew.create(getX(), getY(),batch);
            }
        }
        if (item_on_station != null){
            item_on_station.create(getX(), getY(), batch);
        }
    }

    public void draw_padlock(Batch batch){
        if (isPurchased == false){
            //drawing_font = new BitmapFont();
            batch.draw(padlock_texture, getX()-0.05f, getY()-0.05f, 0.1f, 0.1f);
            // drawing_font.draw(batch, "Something", getX()-0.05f, getY()-0.2f);
            // drawing_font.draw(batch, "Something", 130, 90);
        }
    }


    public void serving_interact(Chef chef, Customer customer, int reputation, int current_customer){

        reputation += customer.served(chef.stack.pop(), current_customer);
        //chef.setInHandsIng(null);

    }
    /**
     * Gets the x-coordinate of the plate station.
     *
     * @return The x-coordinate of the plate station.
     */
    public float getX(){
        return bdefNew.position.x;
    }

    /**
     * Gets the y-coordinate of the plate station.
     *
     * @return The y-coordinate of the plate station.
     */
    public float getY(){
        return bdefNew.position.y;
    }


    /**
     * Added for testing purposes
     * 
     * @return the ingredient on the station
     */
    public Ingredient getItem_on_station(){
        return item_on_station;
    }

    public ArrayList<Ingredient> get_plate_ingredients(){
        return plate_items;
    }

//    Added for testing purposes
    public Boolean getInteractingStatus() {return interacting;}

    public boolean isPurchased() {return this.isPurchased;}

    public String getName() {return this.name;}

    public boolean purchase(){
        if(PlayScreen.money >= price) {
            setPurchased(true);
            PlayScreen.money -= price;
            System.out.println("Purchased!");
            return true;
        }
        System.out.println("Insufficient funds!");
        return false;
    }
    public void setPurchased(boolean b) {this.isPurchased = b;
    }
    }

