package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Tomato extends Ingredient{

    /**
     * The Tomato class represents a specific type of ingredient in the game, specifically tomatoes.
     * It extends the {@link Ingredient} class and has a preparation time and cooking time.
     * The Tomato class sets up an ArrayList of textures for its different skins.
     */

    public Tomato(String name, String status, float prepareTime, float cookTime, ArrayList<Texture> tex) {
        super(name, status, prepareTime, cookTime, tex);
        super.tex = new ArrayList<>();
        super.name = name;
        super.status = status;
        tex.add(new Texture("Food/Tomato.png"));
        tex.add(new Texture("Food/Chopped_tomato.png"));

    }
}
