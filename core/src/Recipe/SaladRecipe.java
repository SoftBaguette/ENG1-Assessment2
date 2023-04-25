package Recipe;

import Ingredients.*;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import Sprites.AllTextures;

/**
 * The SaladRecipe class is a subclass of the Recipe class.
 * It holds an ArrayList of ingredients that makes up a salad dish, and the texture of the completed salad dish.
 * The salad dish consists of {@link Ingredients.Lettuce}, {@link Ingredients.Tomato} and {@link Ingredients.Onion} ingredients.
 */

public class SaladRecipe extends Recipe {
    public SaladRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Lettuce", 1,0,0, AllTextures.getLettuceTextures()));
        ingredients.add(new Ingredient("Tomato", 1,0,0, AllTextures.getTomatoTextures()));
        ingredients.add(new Ingredient("Onion", 1, 0,0, AllTextures.getOnionTextures()));
        completedImg = new Texture("Food/Salad.png");
    }
}
