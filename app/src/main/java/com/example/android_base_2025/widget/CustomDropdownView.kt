package com.example.android_base_2025.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android_base_2025.R
import com.example.android_base_2025.databinding.CustomItemViewBinding

class CustomDropdownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomItemViewBinding? = null
    private val binding get() = _binding!!

    init {
        val inflater = LayoutInflater.from(context)
        _binding = CustomItemViewBinding.inflate(inflater, this, true)

        val styleAttrs =
            context.obtainStyledAttributes(attrs, R.styleable.CustomDropdownView)

        binding.run {
            val isSelectedView =
                styleAttrs.getBoolean(R.styleable.CustomDropdownView_is_selected_view, false)
            edtInput.hint = styleAttrs.getString(R.styleable.CustomDropdownView_hint_input) ?: ""
            titleInput.text = styleAttrs.getString(R.styleable.CustomDropdownView_title_input) ?: ""

            if (isSelectedView) {
                edtInput.isFocusable = false
                edtInput.isCursorVisible = false
                edtInput.isClickable = true
                imageView.visibility = View.VISIBLE
            } else {
                edtInput.isFocusableInTouchMode = true
                edtInput.isCursorVisible = true
                imageView.visibility = View.GONE
            }

            styleAttrs.recycle()
        }
    }

    internal fun setData(data: String) {
        binding.edtInput.setText(data)
    }

    internal fun onClickEditInput(listener: () -> Unit) {
        binding.edtInput.setOnClickListener {
            listener()
        }

        binding.imageView.setOnClickListener {
            listener()
        }
    }
}
