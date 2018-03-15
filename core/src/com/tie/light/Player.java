package com.tie.light;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import java.util.prefs.Preferences;

public class Player {
    float x = 300;
    float y = 500;
    float speed = 1;
    float direction = 0;
    float deg = 0;
    int xdir = 1;
    int ydir = 1;
    float heading = 0;
    Vector2 vec = new Vector2();
    BitmapFont font;


    void show(SpriteBatch batch) {
        font.draw(batch, "8", x, y);
    }

    void update() {

        x += Math.sin(Math.toRadians(deg))* speed;
        y +=  Math.cos(Math.toRadians(deg))* speed;
        //vec.set((float)Math.cos(x),(float)Math.sin(y));


//        vec.set(x,y);
//        vec.setAngle(heading);
//        x=vec.x+xspeed;
//        y=vec.y+yspeed;
//        System.out.println(x+"  "+y);

    }

    float turn() {

        heading += direction;

        if (deg == 360 || deg == -360) deg = 0;
        deg += direction;


        //System.out.println(Math.toRadians(heading)/360);
        //System.out.println(deg / 360);
        return heading;

    }
}
