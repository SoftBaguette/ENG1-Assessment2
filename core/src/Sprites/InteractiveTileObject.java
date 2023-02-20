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
        System.out.println("Interacted with station");
        if (type == "Bin"){
            bin_interact(chef);
        }
        if (type == "ChoppingBoard"){
            chopping_board_interact(chef);
        }


        if (type == "Plate"){
            plate_interact(chef);
        }


        if (type == "Pan"){
            pan_interact(chef);
        }
    }
   
    public void update(Chef chef){
        if (interacting == true){
            System.out.println("Updating");
            float percent = (float) (System.currentTimeMillis() - start_time_interaction+1)/(item_on_station.prepareTime *1000);
            progress = (int) (percent*100);
            //progressBar.setProgress((int) (percent*100));
            if (System.currentTimeMillis() - start_time_interaction > (item_on_station.prepareTime*1000)){
                if (item_on_station.isCooked() == false && item_on_station.isPrepared() == true){
                    item_on_station.setCooked();
                }
                else if (item_on_station.isPrepared() == false){
                    item_on_station.setPrepared();
                }
                item_on_station.status +=1;
                /*
                if (item_on_station.status >= item_on_station.tex.size()){
                    item_on_station.status = item_on_station.tex.size()-1;
                }*/
                System.out.println(item_on_station.status);
                chef.setInHandsIng(item_on_station);
                chef.chefMove = true;
                interacting = false;
                item_on_station = null;
                progress = 0;
                System.out.println("Finished");
                

                //TODO implement Burning
            }
               
        }
    }
    public void draw_progress_bar(Batch batch, Chef chef){
        if (interacting == true){
            //progressBar.change_pos(chef.getX(), chef.getY());
            progressBar.change_pos(getX(), getY());
            progressBar.setProgress(progress);
            progressBar.draw(batch, progress);
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
            if (chef.getInHandsIng().isPrepared() == false){
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
                System.out.println("burger buns");
            }


            if (chef.getInHandsIng().isPrepared()){
                item_on_station = chef.getInHandsIng();
                chef.setInHandsIng(null);
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
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
        for (Ingredient ing : plate_items){
            if (ing != null){
                System.out.println(ing.name);
                if (salad_ingdredients.contains(ing.name) && ing.status == 1){
                    salad_ingdredients.remove(ing.name);
                }
                if (salad_ingdredients.size() == 0){
                    plate_items = new ArrayList<>();
                    plate_items.add(new Ingredient("Salad", 0, 0,0, null));
                    item_made = true;
                    System.out.println("Made Salad");
                }


                if (burger_ingredients.contains(ing.name) && ing.isPrepared() && ing.isCooked()){
                    burger_ingredients.remove(ing.name);
                }
                if(burger_ingredients.size() == 0){
                    plate_items = new ArrayList<>();
                    plate_items.add(new Ingredient("Burger", 0,0,0, null));
                    item_made = true;
                    System.out.println("Made Burger");
                }            
            }
        }
        return item_made;
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

