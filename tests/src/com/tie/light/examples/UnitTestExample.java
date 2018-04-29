package com.tie.light.examples;

import static org.junit.Assert.assertEquals;

import com.tie.light.GdxTestRunner;
import com.tie.light.entities.Bike;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class UnitTestExample {

	@Test
	public void oneEqualsOne() {
		assertEquals(1, 1);
		Bike bike = new Bike("p1", null);
	}

}
