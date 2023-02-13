package Recipe;

import Ingredients.Bun;
import Ingredients.Steak;
import Sprites.AllTextures;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;


/**

 The BurgerRecipe class is a subclass of the Recipe class and represents a burger dish in the kitchen game.
 It holds an ArrayList of ingredients needed to make a burger and a Texture of the completed dish image.
 The ingredients in the ArrayList consist of a {@link Ingredients.Bun} and a {@link Ingredients.Steak}.
 */


public class BurgerRecipe extends Recipe{

    public BurgerRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Bun("Burger_bun", 1,0,0, AllTextures.getBurgerBunsTextures()));
        ingredients.add(new Steak("Meat",2,0,0, AllTextures.getPattyTextures()));
        completedImg = new Texture("Food/Burger.png");
    }
}
