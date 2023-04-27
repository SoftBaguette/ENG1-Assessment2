package Tools;


import Sprites.*;


import java.util.ArrayList;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;
import com.team13.piazzapanic.PlayScreen;


import Ingredients.Ingredient;




/**
 * B2WorldCreator is a class used to create Box2D World objects from a TiledMap.
 * This class uses the map objects to create various objects like worktop, plates,
 * chopperboard, bin, etc. based on the name assigned to the objects in the TiledMap.
 *
 * The class is instantiated with a World object, TiledMap object and PlayScreen object.
 * It then uses the first layer of the TiledMap to create the objects and assign their
 * positions. The objects are created as BodyDef objects and are passed to different sprite
 * classes, where they are further defined and added to the world.
 *
 */
public class B2WorldCreator {


/**
 * Constructor method for B2WorldCreator. It accepts a World, TiledMap and PlayScreen
 * objects. The method then iterates over the cells in the first layer of the TiledMap and
 * uses the map objects to create various objects like worktop, plates, chopperboard,
 * bin, etc. based on the name assigned to the objects in the TiledMap.
 *
 * The objects are created as BodyDef objects and are passed to different sprite classes,
 * where they are further defined and added to the world.
 *
 * @param world The Box2D World object.
 * @param map The TiledMap object.
 * */
    ArrayList<InteractiveTileObject> tile_objects;

    Integer num_chopping_boards = 0;
    Integer num_pans = 0;


    public B2WorldCreator(World world, TiledMap map, PlayScreen screen) {
        ArrayList<Texture> lettuce_textures = new ArrayList<Texture>();
        lettuce_textures.add(new Texture("Food/Lettuce.png"));
        lettuce_textures.add(new Texture("Food/LettuceChopped.png"));


        // SAVING TEST
        PlayScreen.money = 0;
        PlayScreen.reputation = 0;
        BodyDef bdef = null;
        Rectangle rectangle = null;
        InteractiveTileObject pan50 = new InteractiveTileObject("pan50", "Pan");
        pan50.writeName();
        PlayScreen.saveGameData("savedgame.txt");


        ArrayList<Texture> tomato_textures = new ArrayList<Texture>();
        tomato_textures.add(new Texture("Food/Tomato.png"));
        tomato_textures.add(new Texture("Food/TomatoChopped.png"));


        ArrayList<Texture> onion_textures = new ArrayList<Texture>();
        onion_textures.add(new Texture("Food/Onion.png"));
        onion_textures.add(new Texture("Food/OnionChopped.png"));


        ArrayList<Texture> burger_buns_textures = new ArrayList<Texture>();
        burger_buns_textures.add(new Texture("Food/Burger_buns.png"));
        burger_buns_textures.add(new Texture("Food/Burger_bunsToasted.png"));
        burger_buns_textures.add(new Texture("Food/Burger_bunsToasted.png"));

        ArrayList<Texture> patty_textures = new ArrayList<Texture>();
        patty_textures.add(new Texture("Food/Meat.png"));
        patty_textures.add(new Texture("Food/Patty.png"));
        patty_textures.add(new Texture("Food/PattyCooked.png"));
        patty_textures.add(new Texture("Food/PattyBurnt.png"));

        ArrayList<Texture> cheese_textures = new ArrayList<Texture>();
        cheese_textures.add(new Texture("Food/Cheese.png"));
        cheese_textures.add(new Texture("Food/CheeseChopped.png"));

        ArrayList<Texture> potato_textures = new ArrayList<Texture>();
        potato_textures.add(new Texture("Food/Potato.png"));
        potato_textures.add(new Texture("Food/PotatoCooked.png"));

        ArrayList<Texture> dough_textures = new ArrayList<Texture>();
        dough_textures.add(new Texture("Food/PizzaDough.png"));

        tile_objects = new ArrayList<InteractiveTileObject>();


        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        bdef = null;
        rectangle = null;
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) {
                    continue;
                }


                MapObjects cellObjects = cell.getTile().getObjects();
                if (cellObjects.getCount() != 1)
                    continue;


                MapObject mapObject = cellObjects.get(0);
                RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                rectangle = rectangleObject.getRectangle();


                bdef = new BodyDef();
                float position_x = x * MainGame.TILE_SIZE + MainGame.TILE_SIZE / 2f + rectangle.getX()
                        - (MainGame.TILE_SIZE - rectangle.getWidth()) / 2f;
                float position_y = y * MainGame.TILE_SIZE + MainGame.TILE_SIZE / 2f + rectangle.getY()
                        - (MainGame.TILE_SIZE - rectangle.getHeight()) / 2f;
                bdef.position.set(position_x / MainGame.PPM, position_y / MainGame.PPM);
                bdef.type = BodyDef.BodyType.StaticBody;


                if (mapObject.getName().equals("bin")) {
                    //new Bin(world, map, bdef, rectangle, "Bin");
                    new InteractiveTileObject(world, map, bdef, rectangle, "Bin");

                    tile_objects.add(new InteractiveTileObject(world, map, bdef, rectangle, "Bin"));


                } else if (mapObject.getName().equals("worktop")) {
                    new Worktop(world, map, bdef, rectangle, "Worktop");
                } else if (mapObject.getName().equals("chopping_board")) {
                    //new ChoppingBoard(world, map, bdef, rectangle,"ChoppingBoard");
                    if (num_chopping_boards < 2) {
                        new InteractiveTileObject(world, map, bdef, rectangle, "ChoppingBoard");
                        tile_objects.add(new InteractiveTileObject(world, map, bdef, rectangle, "ChoppingBoard"));
                        num_chopping_boards++;
                    } else {
                        new InteractiveTileObject("chop1", world, map, bdef, rectangle, "ChoppingBoard", false, 20);
                        tile_objects.add(new InteractiveTileObject("chop2", world, map, bdef, rectangle, "ChoppingBoard", false, 20));
                    }

                } else if (mapObject.getName().equals("plate")) {

                    new InteractiveTileObject(world, map, bdef, rectangle, "Plate");

                    tile_objects.add(new InteractiveTileObject(world, map, bdef, rectangle, "Plate"));
                    //screen.plateStation = new PlateStation(world, map, bdef, rectangle, "Plate");
                } else if (mapObject.getName().equals("tomato")) {
                    //new TomatoStation(world, map, bdef, rectangle);
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("Tomato", 0, 2, 0, tomato_textures), "");
                } else if (mapObject.getName().equals("lettuce")) {
                    //new LettuceStation(world, map, bdef, rectangle);
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("Lettuce", 0, 2, 0, lettuce_textures), "");
                } else if (mapObject.getName().equals("buns")) {
                    //new BunsStation(world, map, bdef, rectangle);
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("Burger_buns", 0, 2, 2, burger_buns_textures), "");
                } else if (mapObject.getName().equals("onion")) {
                    //new OnionStation(world, map, bdef, rectangle);
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("Onion", 0, 2, 0, onion_textures), "");
                } else if (mapObject.getName().equals("pan1")) {
                    if (num_pans < 1) {
                        new InteractiveTileObject(world, map, bdef, rectangle, "Pan");
                        tile_objects.add(new InteractiveTileObject(world, map, bdef, rectangle, "Pan"));
                        num_pans++;
                    } else {
                        new InteractiveTileObject("pan1", world, map, bdef, rectangle, "Pan", false, 20);
                        tile_objects.add(new InteractiveTileObject("pan2", world, map, bdef, rectangle, "Pan", false, 20));
                    }


                } else if (mapObject.getName().equals("steak")) {
                    //new SteakStation(world, map, bdef, rectangle, "");
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("Steak", 0, 2, 2, patty_textures), "");
                } else if (mapObject.getName().equals("cheese")) {
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("Cheese", 0, 2, 2, cheese_textures), "");
                } else if (mapObject.getName().equals("potato")) {
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("Potato", 0, 2, 2, potato_textures), "");
                } else if (mapObject.getName().equals("pizza_dough")) {
                    new IngredientStation(world, map, bdef, rectangle, new Ingredient("PizzaDough", 0, 2, 2, dough_textures), "");
                } else if (mapObject.getName().equals("pan2")) {
                    if (num_pans <= 0) {
                        new InteractiveTileObject(world, map, bdef, rectangle, "Pan");
                        tile_objects.add(new InteractiveTileObject(world, map, bdef, rectangle, "Pan"));
                        num_pans++;
                    } else {
                        new InteractiveTileObject("pan3", world, map, bdef, rectangle, "Pan", false, 20);
                        tile_objects.add(new InteractiveTileObject("pan4", world, map, bdef, rectangle, "Pan", false, 20));
                    }
                } else if (mapObject.getName().equals("oven")) {
                    new InteractiveTileObject(world, map, bdef, rectangle, "Oven");

                    tile_objects.add(new InteractiveTileObject(world, map, bdef, rectangle, "Oven"));
                } else if (mapObject.getName().equals("completed_dish")) {
                    //new CompletedDishStation(world, map, bdef, rectangle, "CompletedDish");
                    new InteractiveTileObject(world, map, bdef, rectangle, "Serving");

                    tile_objects.add(new InteractiveTileObject(world, map, bdef, rectangle, "Serving"));
                } else if (mapObject.getName().equals("order_top")) {
                    PlayScreen.trayX = rectangle.x;
                    PlayScreen.trayY = rectangle.y;
                }


            }
        }


    }


    public ArrayList<InteractiveTileObject> getTiles(){
        return tile_objects;
    }
}



