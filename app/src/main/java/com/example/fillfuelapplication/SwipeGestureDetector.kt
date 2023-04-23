package com.example.fillfuelapplication

import android.view.GestureDetector
import android.view.MotionEvent
import java.lang.Math.abs

class SwipeGestureDetector {class SwipeGestureDetector : GestureDetector.SimpleOnGestureListener() {
    companion object {
        const val SWIPE_THRESHOLD = 100
        const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val diffX = e2?.x?.minus(e1?.x ?: 0f) ?: 0f
        val diffY = e2?.y?.minus(e1?.y ?: 0f) ?: 0f
        if (abs(diffX) > abs(diffY)
            && abs(diffX) > SWIPE_THRESHOLD
            && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
        ) {
            if (diffX > 0) {
                onSwipeRight()
            } else {
                onSwipeLeft()
            }
            return true
        }
        return false
    }

    open fun onSwipeRight() {}
    open fun onSwipeLeft() {}
}

}