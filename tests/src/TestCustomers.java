import Ingredients.Ingredient;
import Sprites.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestCustomers {
    
    IngredientStation tomato_box = new IngredientStation(new Ingredient("Tomato", 0, 2, 0, null), "");
    IngredientStation lettuce_box = new IngredientStation(new Ingredient("Lettuce", 0, 2, 0, null), "");
    IngredientStation onion_box = new IngredientStation(new Ingredient("Onion", 0, 2, 0, null), "");
    IngredientStation burger_bun_box = new IngredientStation(new Ingredient("Burger_buns", 0, 0, 2, null), "");
    IngredientStation steak_box = new IngredientStation(new Ingredient("Steak", 0, 2, 2, null), "");
    IngredientStation dough_box = new IngredientStation(new Ingredient("PizzaDough", 0, 2, 2, null), "");
    IngredientStation cheese_box = new IngredientStation(new Ingredient("Cheese", 0, 2, 2, null), "");
    IngredientStation potato_box = new IngredientStation(new Ingredient("Potato", 0, 2, 2, null), "");
    InteractiveTileObject chopping_station = new InteractiveTileObject("ChoppingBoard");
    InteractiveTileObject bin = new InteractiveTileObject("Bin");
    InteractiveTileObject plate = new InteractiveTileObject("Plate");
    InteractiveTileObject pan = new InteractiveTileObject("Pan");
    InteractiveTileObject oven = new InteractiveTileObject("Oven");
    Ingredient testSalad = new Ingredient("Salad", null, 0, 0, null);
    Customer cust1;


    @Test
    public void TestServeNothing(){
       Chef chef1 = new Chef();
       Customer cust1 = new Customer("Easy",60);

       cust1.desired_ingredient = new Ingredient("Burger", null, 0, 0, null);
       cust1.served(chef1.stack.pop(), 0);

       assertTrue("Serve Nothing", (cust1.status != "served"));
   }
    @Test
    public void testServingSingleIngredient(){
        Chef chef1 = new Chef();
        chef1.stack.push(new Ingredient("Burger_bun", null, 0, 0, null));
        cust1 = new Customer("Easy", 60);
        cust1.desired_ingredient = new Ingredient("Burger", null, 0, 0, null);

        Integer value = cust1.served(chef1.stack.pop(),0);


        assertTrue("Serving burger bun instead of burger", value == -1);
    }

    @Test
    public void testServingWrongOrder(){
        Chef chef1 = new Chef();
        chef1.stack.push(new Ingredient("Salad", null, 0, 0, null));
        cust1 = new Customer("Easy", 60);
        cust1.desired_ingredient = new Ingredient("Burger", null, 0, 0, null);
        Integer value = cust1.served(chef1.stack.pop(),0);

        assertTrue("Serving salad instead of burger", value == -1);

    }

    @Test
    public void testServingCorrectOrder(){
        Chef chef1 = new Chef();
        chef1.stack.push(new Ingredient("Burger", null, 0, 0, null));
        cust1 = new Customer("Easy", 60);
        cust1.desired_ingredient = new Ingredient("Burger", null, 0, 0, null);
        Integer value = cust1.served(chef1.stack.pop(),0);

        assertTrue("Serving salad instead of burger", value == 1);

    }

}
