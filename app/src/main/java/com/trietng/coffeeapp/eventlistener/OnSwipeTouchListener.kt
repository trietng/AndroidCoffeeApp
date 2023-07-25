package com.trietng.coffeeapp

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.GestureDetector

abstract class OnSwipeTouchListener(context: Context)  : OnTouchListener {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val diffY = e2.y - e1.x
                if (diffY > 0) {
                    onSwipeDown()
                }
                else {
                    onSwipeUp()
                }
                return true
            }
        })
    }

    final override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event!!)
    }

    abstract fun onSwipeUp()

    abstract fun onSwipeDown()
}