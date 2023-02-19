package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;


public class IngredientStation extends InteractiveTileObject{

    
    public IngredientStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, Ingredient ingredient, String type) {
        super(world, map, bdef, rectangle, type);
        //TODO Auto-generated constructor stub
        fixture.setUserData(this);
        this.ingredient  = ingredient;
        this.type = "IngredientSource";
    }

    public IngredientStation(Ingredient ingredient, String type){
        super(type);
        this.ingredient  = ingredient;
        this.type = "IngredientSource";
    }

    
    public void interact(Chef chef){
        System.out.println("Interacted with ingredient source");
        if (chef.inHandsIng == null){
            Ingredient tempIngredient = new Ingredient(ingredient.name, 0, ingredient.prepareTime, ingredient.cookTime, ingredient.tex);
            chef.setInHandsIng(tempIngredient);
        }
    }
    
}
