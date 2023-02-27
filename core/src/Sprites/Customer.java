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
    public Boolean leaving = false;
    public Boolean now_serving = false;

    public Customer(float startx, float starty, String difficulty, float leave_time){

        this.x = startx / MainGame.PPM;
        this.y = starty / MainGame.PPM;

        this.difficulty = difficulty;

        this.leave_time = leave_time;
        if (difficulty == "Easy"){
            leave_time = leave_time * 2;
        }
        else if (difficulty == "Hard"){
            leave_time = leave_time / 2;
        }

        //Meal Options
        Ingredient salad = new Ingredient("Salad", null, 0, 0, null);
        Ingredient burger = new Ingredient("Burger", null, 0, 0, null);

        Ingredient[] meal_options = new Ingredient[] {salad, burger};

        desired_ingredient = meal_options[(int)(Math.random() * meal_options.length)];

        start_time = System.currentTimeMillis();

        status = "started";

        customer_texture = new Texture("Customer.png");
        salad_recipe_texture = new Texture("Food/salad_recipe.png");
        burger_recipe_texture = new Texture("Food/burger_recipe.png");



    }

    public void calculate_start_time(){
        

        //leave_time = System.currentTimeMillis();
        if (difficulty == "Easy"){
            leave_time = leave_time * 2;
        }
        else if (difficulty == "Hard"){
            leave_time = leave_time / 2;
        }
        System.out.println("Changed");
        System.out.println(leave_time);

    }
    public void move(int queue_position, int current_customer){
        if (queue_position == 0 && now_serving == false){
            now_serving = true;
            calculate_start_time();
            start_time = System.currentTimeMillis();
            System.out.println("reset timer");
        }
        if (status == "started"){
            //System.out.println(y);

            if (y < 0.65f - (0.2f * queue_position)){
                //System.out.println(queue_position);
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

        if (System.currentTimeMillis() - start_time > leave_time * 1000 && leaving==false && queue_position == 0){
            status = "served";
            leaving = true;

            //TODO fix this thing!!!!
            served(null, current_customer);
            System.out.println("Leaving" + current_customer);



        }
    
    }

    public int served(Ingredient recipe_ingredient, int current_customer){
        if (recipe_ingredient != null){
            if (recipe_ingredient.name == desired_ingredient.name){

            }
        }
        
        status = "served";
        PlayScreen.current_customer +=1;
        if (PlayScreen.endless == true && PlayScreen.current_customer == 4){
            PlayScreen.current_customer = 0;
        }
        current_customer += 1;
        System.out.println(current_customer);
        return 0;
    }

    public void draw(Batch batch){
        if (y < 1.2f){
            batch.draw(customer_texture, this.x, this.y, 0.1f, 0.1f);
        }
        
        
        
       
    }

    public void draw_order(Batch batch){
        if (status != "served"){
            if (desired_ingredient.name == "Salad"){
                batch.draw(salad_recipe_texture, 1.1f,1.5f, 0.4f, 0.3f);
            } else if (desired_ingredient.name == "Burger"){
                batch.draw(burger_recipe_texture, 1.1f,1.5f, 0.4f, 0.3f);
            }
        }
    }
    
}
