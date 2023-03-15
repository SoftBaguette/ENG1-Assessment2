import Ingredients.Ingredient;
import Sprites.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
        Ingredient test_salad = new Ingredient("Salad", null, 0, 0, null);
        chef1.stack.push(test_salad);
        plate.interact(chef1);
        plate.update(chef1);

//      Does not pass because item_on_station variable is never updated in the plate_interact function,
//      maybe because line 302 was commented out in Interactive Tile Object class

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



}
