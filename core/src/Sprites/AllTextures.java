package Sprites;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;

public class AllTextures {
    


    public AllTextures(){
        ArrayList<Texture> lettuce_textures = new ArrayList<Texture>();
        lettuce_textures.add(new Texture("Food/Lettuce.png"));
        lettuce_textures.add(new Texture("Food/LettuceChopped.png"));

        ArrayList<Texture> tomato_textures = new ArrayList<Texture>();
        tomato_textures.add(new Texture("Food/Tomato.png"));
        tomato_textures.add(new Texture("Food/TomatoChopped.png"));

        ArrayList<Texture> onion_textures = new ArrayList<Texture>();
        onion_textures.add(new Texture("Food/Onion.png"));
        onion_textures.add(new Texture("Food/OnionChopped.png"));

        ArrayList<Texture> burger_buns_textures = new ArrayList<Texture>();
        burger_buns_textures.add(new Texture("Food/Burger_buns.png"));
        burger_buns_textures.add(new Texture("Food/Burger_bunsToasted.png"));

        ArrayList<Texture> patty_textures = new ArrayList<Texture>();
        patty_textures.add(new Texture("Food/Meat.png"));
        patty_textures.add(new Texture("Food/Patty.png"));
        patty_textures.add(new Texture("Food/PattyCooked.png"));
    }

    public static ArrayList<Texture> getLettuceTextures(){
        ArrayList<Texture> lettuce_textures = new ArrayList<Texture>();
        lettuce_textures.add(new Texture("Food/Lettuce.png"));
        lettuce_textures.add(new Texture("Food/LettuceChopped.png"));
        return lettuce_textures;
    }

    public static ArrayList<Texture> getTomatoTextures(){
        ArrayList<Texture> tomato_textures = new ArrayList<Texture>();
        tomato_textures.add(new Texture("Food/Tomato.png"));
        tomato_textures.add(new Texture("Food/TomatoChopped.png"));
        return tomato_textures;
    }
    public static ArrayList<Texture> getOnionTextures(){
        ArrayList<Texture> onion_textures = new ArrayList<Texture>();
        onion_textures.add(new Texture("Food/Onion.png"));
        onion_textures.add(new Texture("Food/OnionChopped.png"));
        return onion_textures;
    }

    public static ArrayList<Texture> getBurgerBunsTextures(){
        ArrayList<Texture> burger_buns_textures = new ArrayList<Texture>();
        burger_buns_textures.add(new Texture("Food/Burger_buns.png"));
        burger_buns_textures.add(new Texture("Food/Burger_bunsToasted.png"));
        return burger_buns_textures;
    }

    public static ArrayList<Texture> getPattyTextures(){
        ArrayList<Texture> patty_textures = new ArrayList<Texture>();
        patty_textures.add(new Texture("Food/Meat.png"));
        patty_textures.add(new Texture("Food/Patty.png"));
        patty_textures.add(new Texture("Food/PattyCooked.png"));
        return patty_textures;
    }

    /*
    public static ArrayList<Texture> get_Textures(){

        return _textures;
    }*/

    
}
