package com.tie.light.examples;

import com.badlogic.gdx.math.Vector2;
import com.tie.light.GdxTestRunner;
import com.tie.light.entities.Wall;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class WallTest {
    Vector2 aP1 = new Vector2(0.46f, 1.51f);
    Vector2 aP2 = new Vector2(2.48f, 3.29f);
    Vector2 bP1 = new Vector2(0.52f, 3.53f);
    Vector2 bP2 = new Vector2(2.44f, 1.51f);
    Vector2 cP1 = new Vector2(1, 1);
    Vector2 cP2 = new Vector2(4, 1);
    Vector2 dP1 = new Vector2(4, 1);
    Vector2 dP2 = new Vector2(7, 1);
    Vector2 eP1 = new Vector2(5, 0);
    Vector2 eP2 = new Vector2(7, 0);

    @Test
    public void notFrontalCollisionTest() {
        Assert.assertTrue(Wall.doIntersect(aP1, aP2, bP1, bP2));
    }

    @Test
    public void FrontalCollisionTest() {
        Assert.assertTrue(Wall.doIntersect(cP1, cP2, dP1, dP2));
    }

    @Test
    public void noCollisionWithNotParallelLineTest() {
        Assert.assertFalse(Wall.doIntersect(aP1, aP2, cP1, cP2));
    }

    @Test
    public void noCollisionWithParallelLineTest() {
        Assert.assertFalse(Wall.doIntersect(cP1, cP2, eP1, eP2));
    }
}