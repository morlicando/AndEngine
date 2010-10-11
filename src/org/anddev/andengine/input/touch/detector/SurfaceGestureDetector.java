package org.anddev.andengine.input.touch.detector;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.input.touch.TouchEvent;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

/**
 * @author rkpost
 * @author Nicolas Gramlich
 * @since 11:36:26 - 11.10.2010
 */
public abstract class SurfaceGestureDetector implements IOnSceneTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final float SWIPE_MIN_DISTANCE_DEFAULT = 120;

	// ===========================================================
	// Fields
	// ===========================================================

	private final GestureDetector mGestureDetector;

	// ===========================================================
	// Constructors
	// ===========================================================

	public SurfaceGestureDetector() {
		this(SWIPE_MIN_DISTANCE_DEFAULT);
	}

	public SurfaceGestureDetector(final float pSwipeMinDistance) {
		this.mGestureDetector = new GestureDetector(new InnerOnGestureDetectorListener(pSwipeMinDistance));
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected abstract boolean onSingleTap();
	protected abstract boolean onDoubleTap();
	protected abstract boolean onSwipeUp();
	protected abstract boolean onSwipeDown();
	protected abstract boolean onSwipeLeft();
	protected abstract boolean onSwipeRight();

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		return this.mGestureDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private class InnerOnGestureDetectorListener extends SimpleOnGestureListener {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		private final float mSwipeMinDistance;

		// ===========================================================
		// Constructors
		// ===========================================================

		public InnerOnGestureDetectorListener(final float pSwipeMinDistance) {
			this.mSwipeMinDistance = pSwipeMinDistance;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		@Override
		public boolean onSingleTapConfirmed(final MotionEvent pMotionEvent) {
			return SurfaceGestureDetector.this.onSingleTap();
		}

		@Override
		public boolean onDoubleTap(final MotionEvent e) {
			return SurfaceGestureDetector.this.onDoubleTap();
		}

		@Override
		public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float pVelocityX, final float pVelocityY) {
			final float swipeMinDistance = this.mSwipeMinDistance;

			final boolean isHorizontalFling = Math.abs(pVelocityX) > Math.abs(pVelocityY);

			if(isHorizontalFling) {
				if(e1.getX() - e2.getX() > swipeMinDistance) {
					return SurfaceGestureDetector.this.onSwipeLeft();
				} else if(e2.getX() - e1.getX() > swipeMinDistance) {
					return SurfaceGestureDetector.this.onSwipeRight();
				}
			} else {
				if(e1.getY() - e2.getY() > swipeMinDistance) {
					return SurfaceGestureDetector.this.onSwipeUp();
				} else if(e2.getY() - e1.getY() > swipeMinDistance) {
					return SurfaceGestureDetector.this.onSwipeDown();
				}
			}

			return false;
		}

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}

	public static class SurfaceGestureDetectorAdapter extends SurfaceGestureDetector {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		// ===========================================================
		// Constructors
		// ===========================================================

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		@Override
		protected boolean onDoubleTap() {
			return false;
		}

		@Override
		protected boolean onSingleTap() {
			return false;
		}

		@Override
		protected boolean onSwipeDown() {
			return false;
		}

		@Override
		protected boolean onSwipeLeft() {
			return false;
		}

		@Override
		protected boolean onSwipeRight() {
			return false;
		}

		@Override
		protected boolean onSwipeUp() {
			return false;
		}

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}