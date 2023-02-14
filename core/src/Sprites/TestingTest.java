package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;

public class TestingTest {
    
    public static void main(String[] args) {
        //World world = new World(null, false);

        InteractiveTileObject chopping = new InteractiveTileObject( "Chopping");
        Chef chef1 = new Chef();
        IngredientStation tomato_box = new IngredientStation(new Ingredient("Tomato", 0, 2, 0, null), "");
        tomato_box.interact(chef1);
        
        System.out.println(chef1.getInHandsIng().name);

        InteractiveTileObject chopping_station = new InteractiveTileObject("ChoppingBoard");
        InteractiveTileObject bin = new InteractiveTileObject("Bin");

        //bin.interact(chef1);
        chopping_station.interact(chef1);
        try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        chopping_station.update(chef1);
        System.out.println(chef1.getInHandsIng().status);


    }
}
