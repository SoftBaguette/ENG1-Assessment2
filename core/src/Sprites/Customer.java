package Sprites;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.team13.piazzapanic.MainGame;
import com.team13.piazzapanic.PlayScreen;

import Ingredients.Ingredient;

public class Customer {

    public float x;
    public float y;
    public float speed;
    public float width;
    public float height;

    public String status;

    public String difficulty;
    public float leave_time;
    public long start_time;

    public Ingredient desired_ingredient;

    public Texture customer_texture;
    public Texture salad_recipe_texture;
    public Texture burger_recipe_texture;
    public Texture pizza_recipe_texture;
    public Boolean leaving = false;
    public Boolean now_serving = false;


    public Customer( String difficulty, float leave_time){


        this.difficulty = difficulty;

        this.leave_time = leave_time;
        calculate_start_time();

        //Meal Options
        Ingredient salad = new Ingredient("Salad", null, 0, 0, null);
        Ingredient burger = new Ingredient("Burger", null, 0, 0, null);
        Ingredient pizza = new Ingredient("Pizza", null, 0,0,null);

        Ingredient[] meal_options = new Ingredient[] {salad, burger, pizza};

        desired_ingredient = meal_options[(int)(Math.random() * meal_options.length)];

        start_time = System.currentTimeMillis();

        status = "started";

        /*
        customer_texture = new Texture("Customer.png");
        salad_recipe_texture = new Texture("Food/salad_recipe.png");
        burger_recipe_texture = new Texture("Food/burger_recipe.png");
        pizza_recipe_texture = new Texture("Food/pizza_recipe.png");*/


    }
    public Customer(float startx, float starty, String difficulty, float leave_time){

        this.x = startx / MainGame.PPM;
        this.y = starty / MainGame.PPM;

        this.difficulty = difficulty;

        this.leave_time = leave_time;
        calculate_start_time();

        //Meal Options
        Ingredient salad = new Ingredient("Salad", null, 0, 0, null);
        Ingredient burger = new Ingredient("Burger", null, 0, 0, null);
        Ingredient pizza = new Ingredient("Pizza", null, 0,0,null);

        Ingredient[] meal_options = new Ingredient[] {salad, burger, pizza};

        desired_ingredient = meal_options[(int)(Math.random() * meal_options.length)];

        start_time = System.currentTimeMillis();

        status = "started";


        customer_texture = new Texture("Customer.png");
        salad_recipe_texture = new Texture("Food/salad_recipe.png");
        burger_recipe_texture = new Texture("Food/burger_recipe.png");
        pizza_recipe_texture = new Texture("Food/pizza_recipe.png");


    }

    /**
     * Calculates a the start time that the customer arrived
     * Used later for checking if the customer needs to leave
     */
    public void calculate_start_time(){
        if (difficulty == "Easy"){
            leave_time = leave_time * 1.2f;
        }
        else if (difficulty == "Hard"){
            leave_time = leave_time *0.8f;
        }
        System.out.println("Changed leave time to: " + leave_time);

    }

    /**
     * Will move the chef up determining on its status:
     *  - 
     * 
     * @param queue_position will move the chef depending on where it is in the queue
     * @param current_customer TODO is this needed?
     */
    public void move(int queue_position, int current_customer){
        if (queue_position == 0 && now_serving == false){
            now_serving = true;
            calculate_start_time();
            start_time = System.currentTimeMillis();
            System.out.println("reset timer");
        }
        if (status == "started"){

            if (y < 0.65f - (0.2f * queue_position)){
                y += 0.001;
            }
            //move up TO 0.65
            //if location is okay:
            //  go to the midle, then all subsequent customers are lower
        }

        if (status == "served"){
            y += 0.001;
            if (y > 1.2f){
                status = "destroy";
            }
            //move up
            //if y > something:
                //status = "destroy"
        }
        //System.out.println(System.currentTimeMillis() - start_time);
        if (System.currentTimeMillis() - start_time > leave_time * 1000 && leaving==false && queue_position == 0){
            status = "served";
            leaving = true;
            PlayScreen.reputation --;

            //TODO fix this thing!!!!
            served(null, current_customer);
            System.out.println("Leaving" + current_customer);

        }
    
    }

    /**
     * 
     * @param recipe_ingredient
     * @param current_customer
     * @return
     */
    public int served(Ingredient recipe_ingredient, int current_customer){
        Integer value = 0;
        if (recipe_ingredient != null){
            if (recipe_ingredient.name == desired_ingredient.name){
                if (PlayScreen.reputation < 3){
                    PlayScreen.reputation ++;
                    value = 1;
                }
                
            }else{
                PlayScreen.reputation --;
                value = -1;
            }
            //TODO change this to be different
            status = "served";
            PlayScreen.current_customer +=1;
            if (PlayScreen.endless == true && PlayScreen.current_customer == 4){
                PlayScreen.current_customer = 0;
            }
            current_customer += 1;
            System.out.println("Current customer: " + current_customer);
            
        }
        return value;
        
        
    }

    /**
     * Draws the customer to the screen
     * 
     * @param batch The batch used for drawing sprites to the screen
     */
    public void draw(Batch batch){
        if (y < 1.2f){
            batch.draw(customer_texture, this.x, this.y, 0.1f, 0.1f);
        }   
    }

    /**
     * Draws the desired food item that the customer wants and its instructions
     * 
     * @param batch The batch used for drawing sprites to the screen
     */
    public void draw_order(Batch batch){
        if (status != "served"){
            if (desired_ingredient.name == "Salad"){
                batch.draw(salad_recipe_texture, 1.1f,1.5f, 0.4f, 0.3f);
            } else if (desired_ingredient.name == "Burger"){
                batch.draw(burger_recipe_texture, 1.1f,1.5f, 0.4f, 0.3f);
            }
            else if (desired_ingredient.name == "Pizza"){
                batch.draw(pizza_recipe_texture, 1.1f,1.5f, 0.4f, 0.3f);
            }
        }
    }
    
}
