package com.tie.light.examples;

import static org.junit.Assert.assertTrue;

import com.tie.light.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;

@RunWith(GdxTestRunner.class)
public class AssetExistsExampleTest {

	@Test
	public void configFileExists() {
		System.out.println(Gdx.files.getLocalStoragePath());
		assertTrue("This test will only pass if the config file is present.", Gdx.files
				.internal("../android/assets/config.properties").exists());
	}
}
