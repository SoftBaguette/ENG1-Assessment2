package Sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;

import Ingredients.Ingredient;




public class InteractiveTileObject {
    
    protected Fixture fixture;

    protected BodyDef bdefNew;
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
        
        ingredient = null;
        this.type = type;
        interacting = false;
        item_on_station = null;
        progressBar = new ProgressBar(0.5f, 0.5f, 50,10);
        System.out.println(progressBar.height);
        System.out.println("hello");

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
    }
    
    public void update(Chef chef){
        if (interacting == true){
            System.out.println("Updating");
            float percent = (float) (System.currentTimeMillis() - start_time_interaction+1)/(item_on_station.prepareTime *1000);
            progress = (int) (percent*100);
            //progressBar.setProgress((int) (percent*100));
            System.out.println(progress);
            if (System.currentTimeMillis() - start_time_interaction > (item_on_station.prepareTime*1000)){
                item_on_station.status +=1;
                item_on_station.isPrepared();
                chef.setInHandsIng(item_on_station);
                interacting = false;
                progress = 0;
                System.out.println("Finished");
            }
                
        }
    }
    public void draw_progress_bar(Batch batch){
    }


    public void bin_interact(Chef chef){
        System.out.println("Binned");
        chef.setInHandsIng(null);
        chef.setInHandsRecipe(null); //Check what this does
    }

    public void chopping_board_interact(Chef chef){
        if (chef.getInHandsIng() != null){
            if (chef.getInHandsIng().isPrepared() == false){
                item_on_station = chef.getInHandsIng();
                chef.setInHandsIng(null);
                //Stop the chef from moving
                start_time_interaction = System.currentTimeMillis();
                System.out.println(start_time_interaction);
                interacting = true;
            }
            
        }
    }


    public void plate_interact(Chef chef){
        if (chef.getInHandsIng() != null){
            plate_items.add(chef.getInHandsIng());
            chef.setInHandsIng(null);
            checkRecipeCreated();
        }
    }   
    
    public void checkRecipeCreated(){
        for (Ingredient ing : plate_items){
            
        }
    }
}