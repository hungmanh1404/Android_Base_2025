package com.example.android_base_2025.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.example.android_base_2025.R

class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    private var debounceEnabled = true
    private var lastClickTime = 0L
    private val debounceDelay = 500L // 500ms

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0).apply {
            try {
                debounceEnabled = getBoolean(R.styleable.CustomButton_debounceClick, true)
            } finally {
                recycle()
            }
        }

        super.setOnClickListener { view ->
            if (!debounceEnabled) {
                internalClickListener?.onClick(view)
                return@setOnClickListener
            }

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime >= debounceDelay) {
                lastClickTime = currentTime
                internalClickListener?.onClick(view)
            }
        }
    }

    private var internalClickListener: OnClickListener? = null

    override fun setOnClickListener(listener: OnClickListener?) {
        internalClickListener = listener
    }
}
