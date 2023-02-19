package Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ProgressBar extends Actor{
    ShapeRenderer shapeRenderer;
    int progress;
    float x;
    float y;
    float width;
    float height;
    Texture back;
    Texture loading;


    public ProgressBar(float f, float g, float width, float height){
        this.x = f;
        this.y = g;
        this.width = width;
        this.height = height;
        progress = 0;
        shapeRenderer = new ShapeRenderer();
        this.setPosition(f, g);
        back = new Texture("ProgressBarBack.png");
        loading = new Texture("ProgressBarFull.png");
    }


    //The progress bar is 2 imgs and the green progress bar
    //changes in width depending on progress
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(back, x,y, width,height);
        batch.draw(loading, x,y,(float) width * progress/100f,height);
    }


    // Set the progress of the progress bar
    public void setProgress (int progress){
        this.progress = progress;
    }


    public void change_pos (float x, float y){
        this.x = x;
        this.y = y + 0.2f;


    }
}



