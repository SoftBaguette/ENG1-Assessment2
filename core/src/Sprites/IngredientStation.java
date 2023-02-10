package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;


public class IngredientStation extends InteractiveTileObject{

    
    public IngredientStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, Ingredient ingredient) {
        super(world, map, bdef, rectangle);
        //TODO Auto-generated constructor stub
        fixture.setUserData(this);
        this.ingredient  = ingredient;
    }

    public void interact(Chef chef){
        System.out.println("Interacted");
        if (chef.inHandsIng != null){
            //chef.setInHandsIng(ingredient);
        }
    }
    
}
