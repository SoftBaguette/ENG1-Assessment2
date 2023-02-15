package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;

public class TestingTest {

    public static void wait(int time){
      try {
        Thread.sleep(time);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    public static void main(String[] args) {
        //World world = new World(null, false);

        Chef chef1 = new Chef();
        IngredientStation tomato_box = new IngredientStation(new Ingredient("Tomato", 0, 2, 0, null), "");
        IngredientStation lettuce_box = new IngredientStation(new Ingredient("Lettuce", 0, 2, 0, null), "");
        IngredientStation onion_box = new IngredientStation(new Ingredient("Onion", 0, 2, 0, null), "");
        InteractiveTileObject chopping_station = new InteractiveTileObject("ChoppingBoard");
        InteractiveTileObject bin = new InteractiveTileObject("Bin");
        InteractiveTileObject plate = new InteractiveTileObject("Plate");
        
        tomato_box.interact(chef1);
        //bin.interact(chef1);
        chopping_station.interact(chef1);
        wait(2000);
        chopping_station.update(chef1);
        //System.out.println(chef1.getInHandsIng().status);
        plate.interact(chef1);
        lettuce_box.interact(chef1);
        chopping_station.interact(chef1);
        wait(2000);
        chopping_station.update(chef1);
        plate.interact(chef1);
        onion_box.interact(chef1);
        chopping_station.interact(chef1);
        wait(2000);
        chopping_station.update(chef1);
        plate.interact(chef1);



    }
}
