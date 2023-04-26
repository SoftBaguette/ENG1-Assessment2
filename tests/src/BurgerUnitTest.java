import Ingredients.Ingredient;
import Sprites.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BurgerUnitTest {
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
        assertTrue("Making a burger", chef1.stack.peak().name == "Burger");
    }

    @Test
    public void testMakingSalad(){
        Chef chef1 = new Chef();
        lettuce_box.interact(chef1);
        chopping_station.interact(chef1);
        wait(2000);

        chopping_station.update(chef1);
        chopping_station.interact(chef1);
        plate.interact(chef1);
        onion_box.interact(chef1);
        chopping_station.interact(chef1);


        wait(2000);
        chopping_station.update(chef1);
        chopping_station.interact(chef1);
        plate.interact(chef1);
        tomato_box.interact(chef1);
        chopping_station.interact(chef1);


        wait(2000);
        chopping_station.update(chef1);
        chopping_station.interact(chef1);
        plate.interact(chef1);


        assertTrue("Making a salad", chef1.stack.peak().name == "Salad");

    }

    @Test
    public void testMakePizza(){
        //Chop tomatoe
        //Chop cheese
        //get pizza dough
        //add to assembly station
        //put in oven
    }

    @Test
    public void testMakePotato(){
        Chef chef1 = new Chef();
        cheese_box.interact(chef1);
        chopping_station.interact(chef1);
        wait(2000);
        chopping_station.update(chef1);
        chopping_station.interact(chef1);
        plate.interact(chef1);
        potato_box.interact(chef1);
        plate.interact(chef1);
        oven.interact(chef1);
        wait(2000);
        oven.update(chef1);
        oven.interact(chef1);

        assertTrue("Making a salad", chef1.stack.peak().name == "PotatoCheese");

    }

    @Test
    public void testBurning(){
        Chef chef1 = new Chef();
        Ingredient steak = new Ingredient("Steak", 0, 2, 2, null);
        steak.setPrepared();
        steak.setCooked();
        chef1.stack.push(steak);
        pan.interact(chef1);
        pan.update(chef1);
        wait(5000);
        pan.update(chef1);
        pan.interact(chef1);
        assertTrue("Burning steak", chef1.stack.peak().burnt);
    }

    @Test
    public void testBinning(){
        Chef chef1 = new Chef();
        Ingredient lettuce = new Ingredient("Lettuce", 0, 2, 2, null);
        chef1.setInHandsIng(lettuce);
        bin.interact(chef1);
        assertTrue("Binning lettuce", chef1.stack.isEmpty());
    }





    @Test
    public void testCustomerLeavesAfterTimeLimit(){
        Chef chef1 = new Chef();
        chef1.setInHandsIng(new Ingredient("Burger_bun", null, 0, 0, null));
        int timeLimit = 2;
        cust1 = new Customer("Normal", timeLimit);
        cust1.move(0,0);
        wait((timeLimit * 4500) + 1);
        cust1.move(0,0);
        assertTrue("Ignoring customer for longer than their time limit", (cust1.leaving == true));

    }

//     @Test
//     public void testLoseRepOnFailedOrder(){
//         Chef chef1 = new Chef();
//         chef1.setInHandsIng(testSalad);
//         cust1 = new Customer(0,0,"Easy", 60);
//         cust1.desired_ingredient = new Ingredient("Burger", null, 0, 0, null);
//         cust1.served(chef1.getInHandsIng(),0);

// //        Check if reputation has decreased once it's implemented.

//         assertTrue(false);

//     }


}
