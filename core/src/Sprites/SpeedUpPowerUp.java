package Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class SpeedUpPowerUp {
    
    float x;
    float y;
    float width;
    float height;
    Texture speedUp_texture = new Texture("SpeedPowerUp.png");
    public SpeedUpPowerUp(float x, float y, float width,float  height){
        this.x = x;
        this.y = y; 
        this.width = width;
        this.height = height;
    }

    public void increase_speed_mult(Chef chef){
        chef.speed_multiplier += 0.25f;
    }

    public Boolean isColliding(Chef chef){
        if (chef.getX() > (x-width) && chef.getX() < x + width ){
            if (chef.getY() > y && chef.getY() < y + height ){
                return true;
            }
        }
        return false;
    }

    public void draw(Batch batch){

        batch.draw(speedUp_texture, x, y, width, height);
    }


}
