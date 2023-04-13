import Ingredients.Ingredient;
import Sprites.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TestIngredientStation {
    IngredientStation tomato_box = new IngredientStation(new Ingredient("Tomato", 0, 2, 0, null), "");
    IngredientStation lettuce_box = new IngredientStation(new Ingredient("Lettuce", 0, 2, 0, null), "");
    IngredientStation onion_box = new IngredientStation(new Ingredient("Onion", 0, 2, 0, null), "");
    IngredientStation burger_bun_box = new IngredientStation(new Ingredient("Burger_buns", 0, 0, 2, null), "");
    IngredientStation steak_box = new IngredientStation(new Ingredient("Steak", 0, 2, 2, null), "");

    @Test
    public void testPickupLettuce(){
        Chef chef1 = new Chef();
        lettuce_box.interact(chef1);
        assertTrue("Picking up lettuce", (chef1.getInHandsIng().name == "Lettuce"));


    }

    @Test
    public void testPickupOnion(){
        Chef chef1 = new Chef();
        onion_box.interact(chef1);
        assertTrue("Picking up onion", (chef1.getInHandsIng().name == "Onion"));

    }

    @Test
    public void testPickupBurgerBun(){
        Chef chef1 = new Chef();
        burger_bun_box.interact(chef1);
        assertTrue("Picking up burger bun", (chef1.getInHandsIng().name == "Burger_buns"));

    }

    @Test
    public void testPickupTomato(){
        Chef chef1 = new Chef();
        tomato_box.interact(chef1);
        assertTrue("Picking up tomato", (chef1.getInHandsIng().name == "Tomato"));

    }

    @Test
    public void testPickupSteak(){
        Chef chef1 = new Chef();
        steak_box.interact(chef1);
        assertTrue("Picking up steak", (chef1.getInHandsIng().name == "Steak"));

    }

    @Test
    public void testPickupCheese(){}

    @Test
    public void testPickupPotato(){}

    @Test
    public void testPickupPizzaBase(){}
}
