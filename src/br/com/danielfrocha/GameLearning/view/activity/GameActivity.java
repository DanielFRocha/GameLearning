package br.com.danielfrocha.GameLearning.view.activity;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * @author Daniel Rocha
 * @version 1.0
 */

public class GameActivity extends BaseGameActivity {
	private Camera camera;

	public EngineOptions onCreateEngineOptions()
	{
	    camera = new Camera(0, 0, 800, 480); // new camera, sized 800 x 480 pixels
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), this.camera); //our engine options, setting the full screen parameter to true, using fixed landscape orientation, and ratio resolution policy (if the game will be used on a different screen resolution the engine will resize our game, while keeping the proper ratio)
	    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		// TODO Auto-generated method stub

	}

	// Dando Override no onCreateEngine, Tipificando-o como Engine (import
	// org.andengine.engine.Engine;)
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		// Limitando FPS a 60.
		return new LimitedFPSEngine(pEngineOptions, 60); // Trabalha com 2
															// parametros o
															// segundo é o FPS
	}
	

}
