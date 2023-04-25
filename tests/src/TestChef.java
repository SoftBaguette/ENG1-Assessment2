import Ingredients.Ingredient;
import Sprites.Chef;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestChef {

    @Test
    public void testSwitchChef(){
        Chef chef1 = new Chef();
        Chef chef2 = new Chef();

        chef1.setUserControlChef(false);
        chef2.setUserControlChef(true);

        assertTrue("Switching from Chef 1 to Chef 2", chef2.getUserControlChef());

    }

    @Test
    public void testStackRecipeIngredients(){
        Chef chef1 = new Chef();

        chef1.stack.push(new Ingredient("Lettuce", 1,0,0, null));
        chef1.stack.push(new Ingredient("Tomato", 1,0,0, null));
        chef1.stack.push(new Ingredient("Onion", 1,0,0, null));

        assertTrue("Stacking all ingredients in a salad", chef1.stack.isFull());

    }


}
