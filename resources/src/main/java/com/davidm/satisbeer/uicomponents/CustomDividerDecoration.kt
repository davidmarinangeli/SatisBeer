package com.davidm.satisbeer.uicomponents

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class CustomDividerDecoration(context: Context) : ItemDecoration() {
    private val DIVIDER_PADDING = 64

    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    private var divider: Drawable

    init {
        val styledAttributes = context.obtainStyledAttributes(ATTRS)
        divider = styledAttributes.getDrawable(0)
            ?: throw IllegalStateException("Attribute ${ATTRS[0]} not found")
        styledAttributes.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = DIVIDER_PADDING
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}