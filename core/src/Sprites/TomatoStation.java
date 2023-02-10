package Sprites;

import Ingredients.Ingredient;
import Ingredients.Tomato;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;


/**
 * TomatoStation is a class extending InteractiveTileObject representing a Tomato ingredient station.
 * It creates an TomatoStation object with world, map, body definition and rectangle as parameters.
 * The fixture of the body is then set with the instance of the object as its user data.
 * The class also provides a method getIngredient() that returns a new Tomato instance
 */
public class TomatoStation extends InteractiveTileObject {

    public TomatoStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }

    public static Ingredient getIngredient(){
        return new Tomato("Tomato", "", 2,0, AllTextures.getTomatoTextures());
    }

    public static void main(String[] args) {
        //TomatoStation tomatoStation = new TomatoStation(null, null, null, null);
        
        Texture texture = new Texture(Gdx.files.internal("Food/Onion.png"));
        Ingredient tomato = getIngredient();
        System.out.println(tomato.name);
    }
    
    
}

