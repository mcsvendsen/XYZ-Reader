package com.example.xyzreader.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

/**************************************************************************
 * Code borrowed from Patrick Ivarsson
 * https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-2-grids-688829b1d29b
 * ************************************************************************/

/**
 * RecyclerView with support for staggered grid animations.
 *
 * Based on:
 * https://gist.github.com/Musenkishi/8df1ab549857756098ba
 * Credit to Freddie (Musenkishi) Lust-Hed
 *
 * ...which in turn is based on the GridView implementation of attachLayoutParameters(...):
 * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/widget/GridView.java
 *
 */
public class StaggeredGridRecyclerView extends RecyclerView {

    /** @see View#View(Context) */
    public StaggeredGridRecyclerView(Context context) { super(context); }

    /** @see View#View(Context, AttributeSet) */
    public StaggeredGridRecyclerView(Context context, AttributeSet attrs) { super(context, attrs); }

    /** @see View#View(Context, AttributeSet, int) */
    public StaggeredGridRecyclerView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params,
                                                   int index, int count) {
        final LayoutManager layoutManager = getLayoutManager();
        if (getAdapter() != null && layoutManager instanceof StaggeredGridLayoutManager){

            GridLayoutAnimationController.AnimationParameters animationParams =
                    (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;

            if (animationParams == null) {
                // If there are no animation parameters, create new once and attach them to
                // the LayoutParams.
                animationParams = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParams;
            }

            // Next we are updating the parameters

            // Set the number of items in the RecyclerView and the index of this item
            animationParams.count = count;
            animationParams.index = index;

            // Calculate the number of columns and rows in the grid
            final int columns = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            animationParams.columnsCount = columns;
            animationParams.rowsCount = count / columns;

            // Calculate the column/row position in the grid
            final int invertedIndex = count - 1 - index;
            animationParams.column = columns - 1 - (invertedIndex % columns);
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;

        } else {
            // Proceed as normal if using another type of LayoutManager
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }
}