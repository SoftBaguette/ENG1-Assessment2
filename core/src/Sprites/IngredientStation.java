package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;


public class IngredientStation extends InteractiveTileObject{


    /**
     * IngredientStation class constructor that initializes all the fields
     * 
     * @param world
     * @param map
     * @param bdef
     * @param rectangle
     * @param ingredient
     * @param type
     */
    public IngredientStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, Ingredient ingredient, String type) {
        super(world, map, bdef, rectangle, type);
        fixture.setUserData(this);
        this.ingredient  = ingredient;
        this.type = "IngredientSource";
    }

    /**
     * Used for testing as there is no world, tilemap or bodydef 
     * 
     * @param ingredient
     * @param type
     */
    public IngredientStation(Ingredient ingredient, String type){
        super(type);
        this.ingredient  = ingredient;
        this.type = "IngredientSource";
    }

    /**
     * Allows the chef to interact with the ingredient station and if the chef's stack isn't full
     * it will push the current ingredient to the top of the stack
     * 
     * @param chef The current chef that is interacting with the IngredientStation
     */
    public void interact(Chef chef){
        System.out.println("Interacted with ingredient source");
        if (chef.stack.isFull()){
            System.out.println("Stack Full!");
        }else{
            System.out.println("Added to stack");
            chef.stack.push(new Ingredient(ingredient.name, 0, ingredient.prepareTime, ingredient.cookTime, ingredient.tex));
        }

        if (chef.inHandsIng == null){
            Ingredient tempIngredient = new Ingredient(ingredient.name, 0, ingredient.prepareTime, ingredient.cookTime, ingredient.tex);
            chef.setInHandsIng(tempIngredient);
        }
    }
    
}
