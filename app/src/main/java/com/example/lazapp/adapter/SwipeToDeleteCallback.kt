package com.example.lazapp.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    val background: GradientDrawable = GradientDrawable()
    var icon: Drawable? = null

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (icon== null) {
            icon = recyclerView.context.resources.getDrawable(android.R.drawable.ic_menu_delete)
        }
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the red delete background
        background.setColor(Color.RED)
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(canvas)

        // Calculate position of delete icon
        val iconTop = itemView.top + (itemHeight - (icon?.intrinsicHeight ?: 0)) / 2
        val iconMargin = (itemHeight - (icon?.intrinsicHeight ?: 0)) / 2
        val iconLeft = itemView.right - iconMargin - (icon?.intrinsicWidth ?: 0)
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + (icon?.intrinsicHeight ?: 0)

        // Draw the delete icon
        icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon?.draw(canvas)

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}