package Sprites;


import java.util.*;
import java.lang.String;


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








public class InteractiveTileObject {
   
    protected Fixture fixture;
    Map<String, ArrayList<String>> IngredientsToStations;

    public BodyDef bdefNew;
    public Ingredient ingredient;
    public String type;


    long start_time_interaction;
    boolean interacting;
    Ingredient item_on_station;
    ProgressBar progressBar;
    int progress;

    long start_time_burning;
    boolean burning;
    float burn_time = 5000f;


    ArrayList<Ingredient> plate_items;


   


    /**
     * Constructor for the class, initialises b2bodies.
     *
     * @param world The playable world.
     * @param map The tiled map.
     * @param bdef The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public InteractiveTileObject(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, String type) {


        bdefNew = bdef;
       
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

        this.IngredientsToStations = new HashMap<String, ArrayList<String>>();
        IngredientsToStations.put("Chop", new ArrayList<String>(Arrays.asList("Tomato", "Lettuce", "Onion", "Cheese", "Steak")));
        IngredientsToStations.put("Pan", new ArrayList<String>(Arrays.asList("Steak", "Burger_buns")));
        IngredientsToStations.put("Oven", new ArrayList<String>(Arrays.asList("PotatoCheese", "Pizza")));


       
    }


    public InteractiveTileObject(String type){
        ingredient = null;
        this.type = type;
        interacting = false;
        item_on_station = null;
        progress = 0;

        this.IngredientsToStations = new HashMap<String, ArrayList<String>>();
        IngredientsToStations.put("Chop", new ArrayList<String>(Arrays.asList("Tomato", "Lettuce", "Onion", "Cheese", "Steak")));
        IngredientsToStations.put("Pan", new ArrayList<String>(Arrays.asList("Steak", "Burger_buns")));
        IngredientsToStations.put("Oven", new ArrayList<String>(Arrays.asList("PotatoCheese", "Pizza")));
        plate_items = new ArrayList<>();
    }


    public void interact(Chef chef){
        System.out.println("Interacted with " + type + " station");
        System.out.println("Item on station: " + item_on_station);

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
   
    public void update(Chef chef){
        
        if (interacting == true){
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
                chef.chefMove = true;
                interacting = false;
                progress = 0;
                System.out.println("Finished");

                if (item_on_station.isCooked() && item_on_station.name == "Steak"){
                    burning = true;  
                    progress = 0;
                    start_time_burning = System.currentTimeMillis();
                }

                
            }    
        }
        if (burning == true){
            float burn_percent = (float) (System.currentTimeMillis() - start_time_burning+1)/(5000);
            progress = (int) (burn_percent*100);
            if (System.currentTimeMillis() - start_time_burning > (5000)){
                item_on_station.status +=1;
                burning = false;
                item_on_station.burnt = true;
                
            }
        
        }
    }
    public void draw_progress_bar(Batch batch, Chef chef){
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




    public void bin_interact(Chef chef){
        System.out.println("Binned");
        chef.setInHandsIng(null);
        chef.setInHandsRecipe(null); //Check what this does
        chef.setChefSkin(null);
    }


    public void chopping_board_interact(Chef chef){
        //ChoppingBoardIngredeints:
        if (chef.getInHandsIng() != null){
            if (chef.getInHandsIng().isPrepared() == false && IngredientsToStations.get("Chop").contains(chef.getInHandsIng().name)){
                item_on_station = chef.getInHandsIng();
                chef.setInHandsIng(null);
                //Stop the chef from moving
                chef.chefMove = false;
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
           
        }
    }


    public void pan_interact(Chef chef){
        //Pan ingredients:
        if (chef.getInHandsIng() != null){
            if (chef.getInHandsIng().name == "Burger_buns"){
                chef.getInHandsIng().setPrepared();
            }


            if (chef.getInHandsIng().isPrepared() && chef.getInHandsIng().burnt == false &&
                    IngredientsToStations.get("Pan").contains(chef.getInHandsIng().name)){
                item_on_station = chef.getInHandsIng();
                chef.setInHandsIng(null);
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
        }
    }


    public void oven_interact(Chef chef){

        if (chef.getInHandsIng() != null && IngredientsToStations.get("Oven").contains(chef.getInHandsIng().name) && chef.getInHandsIng().isCooked() == false){
            item_on_station = chef.getInHandsIng();
            item_on_station.setPrepared();
            chef.setInHandsIng(null);
            start_time_interaction = System.currentTimeMillis();
            interacting = true;
        }
    }



    public void plate_interact(Chef chef){
        if (chef.getInHandsIng() != null){
            plate_items.add(chef.getInHandsIng());
            chef.setInHandsIng(null);
            if (checkRecipeCreated()){
                chef.setInHandsIng(plate_items.get(0));
                System.out.println(chef.getInHandsIng().name);
                plate_items = new ArrayList<>();
            }
        }
        
    }  
   
    public Boolean checkRecipeCreated(){

        Map<String, Set<String>> RecipeIngredients = new HashMap<String, Set<String>>();
        RecipeIngredients.put("Salad", new HashSet<String>(Arrays.asList("Tomato", "Lettuce", "Onion")));
        RecipeIngredients.put("Burger", new HashSet<String>(Arrays.asList("Burger_buns", "Steak")));
        RecipeIngredients.put("Pizza", new HashSet<String>(Arrays.asList("PizzaDough", "Cheese", "Tomato")));
        RecipeIngredients.put("PotatoCheese", new HashSet<String>(Arrays.asList("Cheese", "Potato")));
        // Initialises mappings from a recipe name & the name of its ingredients

        Set<String> plate_itemsAsSet = new HashSet<String>();
        for (Ingredient plate_item : plate_items) {
                 if(plate_item.isPrepared()){
                     plate_itemsAsSet.add(plate_item.name);}
             }
        { // Gives us a Set<String> instead of an ArrayList<Ingredient> - easier to check with.

        }
        String[] allRecipes = new String[]{"Salad","Burger","Pizza","PotatoCheese"};


        for(String rec : allRecipes) {
            // Each recipe is mapped in RecipeIngredients to a HashSet<String>.
            // This checks each one of those HashSets to see if any are a subset of plate_itemsAsSet.
           if(plate_itemsAsSet.containsAll(RecipeIngredients.get(rec)))  {
               plate_items.add(new Ingredient(rec, 0, 0, 0, null));
               // This is the best way I could think of that'll remove one of each ingredient
               HashSet<String> removedItems = new HashSet<String>();
               for(Ingredient ing : plate_items) {
                   if(RecipeIngredients.get(rec).contains(ing.name) && !removedItems.contains(ing.name)) {
                        removedItems.add(ing.name);
                        plate_items.remove(ing);
                   }
                   {
               return true;
            }}
        }}
        // If no completed recipe can be made


               return false;
    }

    public void pickUpItem(Chef chef){
        chef.setInHandsIng(item_on_station);
        item_on_station = null;
        burning = false;
        
    }

    public void completed_dish(){
        Set<String> available_dishes = new HashSet<>();
        available_dishes.add("Burger");
        available_dishes.add("Salad");

    }

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


    public void serving_interact(Chef chef, Customer customer, int reputation, int current_customer){
        reputation += customer.served(chef.getInHandsIng(), current_customer);
        chef.setInHandsIng(null);

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
}

