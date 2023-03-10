
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;

import Ingredients.Ingredient;
import Sprites.Chef;
import Sprites.IngredientStation;
import Sprites.InteractiveTileObject;

import org.junit.Test;
//import junit.*;

public class BurgerUnitTest {
    IngredientStation tomato_box = new IngredientStation(new Ingredient("Tomato", 0, 2, 0, null), "");
    IngredientStation lettuce_box = new IngredientStation(new Ingredient("Lettuce", 0, 2, 0, null), "");
    IngredientStation onion_box = new IngredientStation(new Ingredient("Onion", 0, 2, 0, null), "");
    IngredientStation burger_bun_box = new IngredientStation(new Ingredient("Burger_buns", 0, 0, 2, null), "");
    IngredientStation steak_box = new IngredientStation(new Ingredient("Steak", 0, 2, 2, null), "");
    InteractiveTileObject chopping_station = new InteractiveTileObject("ChoppingBoard");
    InteractiveTileObject bin = new InteractiveTileObject("Bin");
    InteractiveTileObject plate = new InteractiveTileObject("Plate");
    InteractiveTileObject pan = new InteractiveTileObject("Pan");

    public static void wait(int time){
        try {
          Thread.sleep(time);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }

    @Test
    public void testMakingBurger(){
        Chef chef1 = new Chef();
        burger_bun_box.interact(chef1);
        pan.interact(chef1);
        wait(2000);
        pan.update(chef1);
        pan.interact(chef1);
        plate.interact(chef1);
        steak_box.interact(chef1);
        chopping_station.interact(chef1);
        wait(2000);
        chopping_station.update(chef1);
        chopping_station.interact(chef1);
        pan.interact(chef1);
        wait(2000);
        pan.update(chef1);
        pan.interact(chef1);
        plate.interact(chef1);
        assertTrue("Makeing a burger", chef1.getInHandsIng().name == "Burger");
        //assertTrue("This test will only pass if chef sprite exists", Gdx.file.internal("Chef_holding.png"));
    }

}
