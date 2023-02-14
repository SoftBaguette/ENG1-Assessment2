package Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;

/**
 * The ChoppingBoard class extends the InteractiveTileObject class.
 *
 * This class is used to define a chopping board object in the game that can chop up steak into a patty and
 * to cut up fruit and vegetables
 */

public class ChoppingBoard extends InteractiveTileObject {
    long start_time_interaction;
    boolean interacting;
    Ingredient item_on_station;
    ProgressBar progressBar;
    

    public ChoppingBoard(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, String type) {
        super(world, map, bdef, rectangle, type);
        fixture.setUserData(this);
        interacting = false;
        item_on_station = null;
        progressBar = new ProgressBar(this.getX(), this.getY(), 50,10);

    }

    public float getX(){
        return super.bdefNew.position.x;
    }

    public float getY(){
        return super.bdefNew.position.y;
    }

    @Override
    public void update(Chef chef){
        if (interacting == true){
            float percent = (float) (System.currentTimeMillis() - start_time_interaction+1)/(item_on_station.prepareTime *1000);
            progressBar.setProgress((int) (percent*100));
            if (System.currentTimeMillis() - start_time_interaction > (item_on_station.prepareTime));
                item_on_station.status +=1;
                item_on_station.isPrepared();
                chef.setInHandsIng(item_on_station);
                interacting = false;
                progressBar.setProgress(0);
                System.out.println("Finished");
            }
    }

    @Override
    public void draw_progress_bar(Batch batch){
        if (interacting == true){
            progressBar.draw(batch, 0);
        }
    }
    
    public void interact(Chef chef){
        if (chef.getInHandsIng() != null){
            Ingredient temp_ingredient = chef.getInHandsIng();
            item_on_station = chef.getInHandsIng();
            if (temp_ingredient.isPrepared() == false){
                //Stop the chef from moving
                start_time_interaction = System.currentTimeMillis();
                interacting = true;
            }
            //temp_ingredient.status += 1;
            //chef.setInHandsIng(temp_ingredient);
        }
    }
}
