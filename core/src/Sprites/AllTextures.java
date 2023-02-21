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
        burger_buns_textures.add(new Texture("Food/Burger_bunsToasted.png"));
        burger_buns_textures.add(new Texture("Food/Burger_bunsToasted.png"));
        return burger_buns_textures;
    }

    public static ArrayList<Texture> getPattyTextures(){
        ArrayList<Texture> patty_textures = new ArrayList<Texture>();
        patty_textures.add(new Texture("Food/Meat.png"));
        patty_textures.add(new Texture("Food/Patty.png"));
        patty_textures.add(new Texture("Food/PattyCooked.png"));
        patty_textures.add(new Texture("Food/PattyBurnt.png"));
        return patty_textures;
    }

    public static ArrayList<Texture> getSaladTextures(){
        ArrayList<Texture> salad_textures = new ArrayList<Texture>();
        salad_textures.add(new Texture("Food/Salad.png"));
        return salad_textures;
    }

    public static ArrayList<Texture> getBurgerTextures(){
        ArrayList<Texture> burger_textures = new ArrayList<Texture>();
        burger_textures.add(new Texture("Food/Burger.png"));
        return burger_textures;
    }


    public static ArrayList<Texture> getTextures(String item){
        if (item == "Burger_buns"){
            return getBurgerBunsTextures();
        }
        else if (item == "Steak"){
            return getPattyTextures();
        }
        else if (item == "Tomato"){
            return getTomatoTextures();
        }else if (item == "Lettuce"){
            return getLettuceTextures();
        }else if (item == "Onion"){
            return getOnionTextures();
        }else if (item == "Salad"){
            return getSaladTextures();
        }else if (item == "Burger"){
            return getBurgerTextures();
        }
        else{
            return null;
        }
    }

    /*
    public static ArrayList<Texture> get_Textures(){

        return _textures;
    }*/

    
}
