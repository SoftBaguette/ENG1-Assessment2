package Sprites;


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
      Chef chef1 = new Chef();
      IngredientStation tomato_box = new IngredientStation(new Ingredient("Tomato", 0, 2, 0, null), "");
      IngredientStation lettuce_box = new IngredientStation(new Ingredient("Lettuce", 0, 2, 0, null), "");
      IngredientStation onion_box = new IngredientStation(new Ingredient("Onion", 0, 2, 0, null), "");
      IngredientStation potato_box = new IngredientStation(new Ingredient("Potato", 0, 0, 2, null), "");
      IngredientStation burger_bun_box = new IngredientStation(new Ingredient("Burger_buns", 0, 0, 2, null), "");
      IngredientStation steak_box = new IngredientStation(new Ingredient("Steak", 0, 2, 2, null), "");
      InteractiveTileObject chopping_station = new InteractiveTileObject("ChoppingBoard");
      InteractiveTileObject bin = new InteractiveTileObject("Bin");
      InteractiveTileObject plate = new InteractiveTileObject("Plate");
      InteractiveTileObject pan = new InteractiveTileObject("Pan");
      InteractiveTileObject oven = new InteractiveTileObject("Oven");

        
      //Making Salad
      
      // tomato_box.interact(chef1);
      // chopping_station.interact(chef1);
      // wait(2000);
      // chopping_station.update(chef1);
      // chopping_station.interact(chef1);
      // plate.interact(chef1);
      // lettuce_box.interact(chef1);
      // chopping_station.interact(chef1);
      // wait(2000);
      // chopping_station.update(chef1);
      // chopping_station.interact(chef1);
      // plate.interact(chef1);
      // onion_box.interact(chef1);
      // chopping_station.interact(chef1);
      // wait(2000);
      // chopping_station.update(chef1);
      // chopping_station.interact(chef1);
      // plate.interact(chef1);
      // bin.interact(chef1);

      //Make burger
      /*
      burger_bun_box.interact(chef1);
      pan.interact(chef1);
      wait(2000);
      pan.update(chef1);
      plate.interact(chef1);
      steak_box.interact(chef1);
      chopping_station.interact(chef1);
      wait(2000);
      chopping_station.update(chef1);
      pan.interact(chef1);
      wait(2000);
      pan.update(chef1);
      plate.interact(chef1);
*/

      //Make Potato
      potato_box.interact(chef1);
      oven.interact(chef1);
      wait(2000);
      oven.update(chef1);
      oven.interact(chef1);
    }
}
