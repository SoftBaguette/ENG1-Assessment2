package Sprites;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


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


       
    }


    public InteractiveTileObject(String type){
        this.type = type;
        ingredient = null;
        this.type = type;
        interacting = false;
        item_on_station = null;
        progress = 0;


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
            System.out.println(interacting);
        }
    }
   
    public void update(Chef chef){
        
        if (interacting == true){
            float percent = (float) (System.currentTimeMillis() - start_time_interaction+1)/(item_on_station.prepareTime *1000);
            progress = (int) (percent*100);
            //System.out.println(progress);
            if (System.currentTimeMillis() - start_time_interaction > (item_on_station.prepareTime*1000)){
                if (item_on_station.isCooked() == false && item_on_station.isPrepared() == true){
                    item_on_station.setCooked();
                }
                else if (item_on_station.isPrepared() == false){
                    item_on_station.setPrepared();
                }
                item_on_station.status +=1;
                //chef.setInHandsIng(item_on_station);
                chef.chefMove = true;
                interacting = false;
                //item_on_station = null;
                progress = 0;
                System.out.println("Finished");

                //TODO implement Burning
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
            //progressBar.change_pos(chef.getX(), chef.getY());
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
        //TODO add choppping board ingredients set (string)
        //ChoppingBoardIngredeints:
        ArrayList<String> chopping_items = new ArrayList<>();
        chopping_items.add("Tomato");
        chopping_items.add("Lettuce");
        chopping_items.add("Onion");
        chopping_items.add("Cheese");
        chopping_items.add("Steak");
        
        
        if (chef.getInHandsIng() != null){
            if (chef.getInHandsIng().isPrepared() == false && chopping_items.contains(chef.getInHandsIng().name)){
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
        //TODO add pan incredients set
        //Pan ingredients:
        ArrayList<String> pan_items = new ArrayList<>();
        pan_items.add("Steak");
        pan_items.add("Burger_buns");

        if (chef.getInHandsIng() != null){
            if (chef.getInHandsIng().name == "Burger_buns"){
                chef.getInHandsIng().setPrepared();
            }


            if (chef.getInHandsIng().isPrepared() && chef.getInHandsIng().burnt == false && pan_items.contains(chef.getInHandsIng().name)){
                item_on_station = chef.getInHandsIng();
                chef.setInHandsIng(null);
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
        }
    }


    public void oven_interact(Chef chef){
        ArrayList<String> oven_items = new ArrayList<>();
        oven_items.add("PotatoCheese");
        oven_items.add("Pizza");
        if (chef.getInHandsIng() != null && oven_items.contains(chef.getInHandsIng().name) && chef.getInHandsIng().isCooked() == false){
            item_on_station = chef.getInHandsIng();
            item_on_station.setPrepared();
            chef.setInHandsIng(null);
            start_time_interaction = System.currentTimeMillis();
            //System.out.println("Start time: " + start_time_interaction " ");
            interacting = true;
            System.out.println(interacting);
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
                    System.out.println("Made Burger");
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
                        System.out.println(ing.name);
                        System.out.println(potato_ingredients.size());
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

