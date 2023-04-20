import Ingredients.Ingredient;
import Sprites.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class TestInteractiveTileObject {

    InteractiveTileObject chopping_board = new InteractiveTileObject("ChoppingBoard");
    InteractiveTileObject bin = new InteractiveTileObject("Bin");
    InteractiveTileObject plate = new InteractiveTileObject("Plate");
    InteractiveTileObject pan = new InteractiveTileObject("Pan");
    InteractiveTileObject oven = new InteractiveTileObject("Oven");


    @Test
    public void testPanInteract(){
        Chef chef1 = new Chef();
        Ingredient test_steak = new Ingredient("Steak", 0, 2, 2, null);
        test_steak.setPrepared();
        chef1.stack.push(test_steak);
        pan.interact(chef1);
        pan.update(chef1);

        assertTrue("Placing steak on pan", pan.getItem_on_station() == test_steak);
    }

    @Test
    public void testChoppingBoardInteract(){
        Chef chef1 = new Chef();
        Ingredient test_steak = new Ingredient("Steak", 0, 2, 2, null);
        chef1.stack.push(test_steak);
        chopping_board.interact(chef1);
        chopping_board.update(chef1);

        assertTrue("Placing steak on chopping board", chopping_board.getItem_on_station() == test_steak);
    }

    @Test
    public void testBinInteract(){
        Chef chef1 = new Chef();
        Ingredient test_steak = new Ingredient("Steak", 0, 2, 2, null);
        chef1.stack.push(test_steak);
        bin.interact(chef1);

        assertTrue("Binning steak", chef1.stack.isEmpty());
    }

    @Test
    public void testPlateInteract(){
        Chef chef1 = new Chef();
        Ingredient test_salad = new Ingredient("Lettuce", 0, 0, 0, null);
        chef1.stack.push(test_salad);
        plate.interact(chef1);
        plate.update(chef1);


        //need plate.plate_items i think
        assertTrue("Placing salad on plate", plate.get_plate_ingredients().get(0) == test_salad);

    }

    @Test
    public void testOvenInteract(){
        Chef chef1 = new Chef();
        Ingredient test_potato = new Ingredient("PotatoCheese", 0, 2, 2, null);
        chef1.stack.push(test_potato);
        oven.interact(chef1);
        oven.update(chef1);

        assertTrue("Placing potato in oven", oven.getItem_on_station() == test_potato);

    }

    @Test
    public void testEmptyHandInteract_Oven(){
        Chef chef1 = new Chef();
        chef1.stack = new Stack(1);
        oven.interact(chef1);
        oven.update(chef1);

        assertTrue("Interacting with oven while holding no ingredients", oven.getInteractingStatus() == false);

    }
    @Test
    public void testEmptyHandInteract_Plate(){
        Chef chef1 = new Chef();
        chef1.stack = new Stack(1);
        plate.interact(chef1);
        plate.update(chef1);

        assertTrue("Interacting with plate while holding no ingredients", plate.getInteractingStatus() == false);
    }
    @Test
    public void testEmptyHandInteract_Pan(){
        Chef chef1 = new Chef();
        chef1.stack = new Stack(1);
        pan.interact(chef1);
        pan.update(chef1);

        assertTrue("Interacting with pan while holding no ingredients", pan.getInteractingStatus() == false);
    }
    @Test
    public void testEmptyHandInteract_Bin(){
        Chef chef1 = new Chef();
        chef1.stack = new Stack(1);
        bin.interact(chef1);
        bin.update(chef1);

        assertTrue("Interacting with bin while holding no ingredients", bin.getInteractingStatus() == false);
    }


}
