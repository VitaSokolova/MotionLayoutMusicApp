package com.example.motionmusicapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.*
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_example_4.*

/**
 * Sample with programmatically set up constraints
 *
 * Copied from https://github.com/kaeawc/motion-photo-grid
 *
 * Mark Activity with launcher intent-filter at AndroidManifest.xml to run this sample
 */
class Example4Activity : AppCompatActivity() {

    private val allViews: Set<View> by lazy {
        setOf(photo_1, photo_2, photo_3, photo_4, photo_5, photo_6, photo_7, photo_8, photo_9)
    }
    private val topRowIds: Set<Int> by lazy {
        setOf(photo_1.id, photo_2.id, photo_3.id)
    }
    private val middleRowIds: Set<Int> by lazy {
        setOf(photo_4.id, photo_5.id, photo_6.id)
    }
    private val lastRowIds: Set<Int> by lazy {
        setOf(photo_7.id, photo_8.id, photo_9.id)
    }
    private val leftColumnIds: Set<Int> by lazy {
        setOf(photo_1.id, photo_4.id, photo_7.id)
    }
    private val middleColumnIds: Set<Int> by lazy {
        setOf(photo_2.id, photo_5.id, photo_8.id)
    }
    private val rightColumnIds: Set<Int> by lazy {
        setOf(photo_3.id, photo_6.id, photo_9.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_4)
    }

    override fun onResume() {
        super.onResume()
        allViews.forEach { it.setOnClickListener { toggleZoom(it) } }
    }

    private fun toggleZoom(view: View) {
        if (motion_scene?.currentState == R.id.zoomed_in) {
            backToGrid()
        } else {
            zoom(view)
        }
    }

    private fun backToGrid() {
        motion_scene?.transitionToStart()
        motion_scene?.rebuildScene()
    }

    private fun zoom(view: View) {
        val constraintSet = motion_scene?.getConstraintSet(R.id.zoomed_in) ?: return

        allViews.forEach { resetZoomedView(constraintSet, it.id) }

        with(constraintSet) {
            constrainWidth(view.id, matrix_root.width)
            constrainHeight(view.id, matrix_root.height)

            connect(view.id, TOP, R.id.matrix_root, TOP, 0)
            connect(view.id, START, R.id.matrix_root, START, 0)
            connect(view.id, END, R.id.matrix_root, END, 0)
            connect(view.id, BOTTOM, R.id.matrix_root, BOTTOM, 0)

            setElevation(view.id, 1f)
        }

        motion_scene?.updateState(R.id.zoomed_in, constraintSet)
        motion_scene?.transitionToEnd()
    }

    private fun resetZoomedView(constraintSet: ConstraintSet, viewId: Int?) {
        viewId?.let {

            val screenWidth = resources.displayMetrics.widthPixels
            val gridItemSize = screenWidth / 3

            with(constraintSet) {
                constrainWidth(viewId, gridItemSize)
                constrainHeight(viewId, gridItemSize)

                when (viewId) {
                    in topRowIds -> {
                        connect(viewId, TOP, R.id.matrix_root, TOP, 0)
                        connect(viewId, BOTTOM, R.id.horizontal_middle, TOP, 0)
                    }
                    in middleRowIds -> {
                        connect(viewId, TOP, R.id.horizontal_middle, TOP, 0)
                        connect(viewId, BOTTOM, R.id.horizontal_middle, BOTTOM, 0)
                    }
                    in lastRowIds -> {
                        connect(viewId, TOP, R.id.horizontal_middle, BOTTOM, 0)
                        connect(viewId, BOTTOM, R.id.matrix_root, BOTTOM, 0)
                    }
                    else -> return
                }

                when (viewId) {
                    in leftColumnIds -> {
                        connect(viewId, START, R.id.matrix_root, START, 0)
                        connect(viewId, END, R.id.vertical_middle, START, 0)
                    }
                    in middleColumnIds -> {
                        connect(viewId, START, R.id.vertical_middle, START, 0)
                        connect(viewId, END, R.id.vertical_middle, END, 0)
                    }
                    in rightColumnIds -> {
                        connect(viewId, START, R.id.vertical_middle, END, 0)
                        connect(viewId, END, R.id.matrix_root, END, 0)
                    }
                    else -> return
                }
                setElevation(viewId, 0f)
            }
        }
    }
}