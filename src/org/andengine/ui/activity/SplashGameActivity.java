package org.andengine.ui.activity;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;



import android.app.Activity;
import android.content.Intent;
import android.view.Display;

public abstract class SplashGameActivity extends SimpleBaseGameActivity {
       private TextureRegion mLoadingScreenTextureRegion;
	   Display d; 

	   private int mWidth = 720;
	   private int mHeight = 480;
	     protected abstract ScreenOrientation getScreenOrientation();

	        protected abstract String onGetSplashTextureAtlasPath();

	        protected abstract float getSplashDuration();

	        protected abstract Class<? extends Activity> getFollowUpActivity();
	   public EngineOptions onCreateEngineOptions() {

			Display display = getWindowManager().getDefaultDisplay();

			mWidth = display.getWidth();
			mHeight = display.getHeight();
		      return new EngineOptions(true,this.getScreenOrientation(),
		            new RatioResolutionPolicy(this.mWidth,this.mHeight),
		            new Camera(0,0,this.mWidth,this.mHeight));
		      
		}
		@Override
		protected void onCreateResources() {
			
		      BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		      final BitmapTextureAtlas atlas = new BitmapTextureAtlas(getTextureManager(),320,240, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		      this.mLoadingScreenTextureRegion = (TextureRegion) BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, this, onGetSplashTextureAtlasPath(), 0, 0);  
		      this.mEngine.getTextureManager().loadTexture(atlas);
			
		}
		@Override
		protected Scene onCreateScene() {
			 final Scene scene = new Scene();
			
		      scene.setBackground(new SpriteBackground(new Sprite(0,0,this.mWidth, this.mHeight, this.mLoadingScreenTextureRegion, this.getVertexBufferObjectManager())));
		      scene.registerUpdateHandler(new TimerHandler(this.getSplashDuration(), new ITimerCallback() {

		         public void onTimePassed(TimerHandler pTimerHandler) {
		            Intent intent = new Intent(SplashGameActivity.this, SplashGameActivity.this.getFollowUpActivity());
		            SplashGameActivity.this.finish();
		            SplashGameActivity.this.startActivity(intent);
		         }

		      }));
		      return scene;
		}

}
