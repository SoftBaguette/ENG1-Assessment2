package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Bun extends Ingredient{

    /**

     The Bun class represents a specific type of ingredient in the game, specifically the burger buns.
     It extends the {@link Ingredient} class and has a preparation time and cooking time.
     The Bun class sets the prepared flag to true in the constructor, and sets up an ArrayList of textures for its different skins.

     */

    public Bun(String name, Integer status, float prepareTime, float cookTime, ArrayList<Texture> tex) {
        super(name, status, prepareTime, cookTime, tex);
        super.setPrepared();
        super.name = name;
        super.status = status;
        super.tex = new ArrayList<>();
        super.tex.add(null);
        super.tex.add(new Texture("Food/Burger_buns.png"));
        super.tex.add(new Texture("Food/Toasted_burger_buns.png"));
    }
}
