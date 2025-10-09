package com.example.android_base_2025.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.android_base_2025.R
import com.example.android_base_2025.databinding.CustomItemViewBinding

class CustomDropdownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomItemViewBinding? = null
    private val binding get() = _binding!!

    private var required: Boolean = false
    private var errorText: String = ""

    internal var onClickDropdownItem: (name: String) -> Unit = { }
    internal var onClearSelectedItem: () -> Unit = {}

    private var isSelectedView: Boolean = false

    init {
        initView(attrs)
        initListener()
    }

    private fun initView(attrs: AttributeSet?) {
        val inflater = LayoutInflater.from(context)
        _binding = CustomItemViewBinding.inflate(inflater, this, true)

        val styleAttrs =
            context.obtainStyledAttributes(attrs, R.styleable.CustomDropdownView)

        binding.run {
            isSelectedView =
                styleAttrs.getBoolean(R.styleable.CustomDropdownView_is_selected_view, false)
            edtInput.hint = styleAttrs.getString(R.styleable.CustomDropdownView_hint_input) ?: ""
            titleInput.text = styleAttrs.getString(R.styleable.CustomDropdownView_title_input) ?: ""
            required = styleAttrs.getBoolean(R.styleable.CustomDropdownView_is_required, false)
            errorText = styleAttrs.getString(R.styleable.CustomDropdownView_error_text)
                ?: context.getString(R.string.default_error)

            if (isSelectedView) {
                edtInput.isFocusable = false
                edtInput.isClickable = true

                edtInput.isCursorVisible = false
                imageView.visibility = View.VISIBLE
            } else {
                edtInput.isFocusableInTouchMode = true

                edtInput.isCursorVisible = true
                imageView.visibility = View.GONE
            }

            tvError.visibility = View.GONE

            styleAttrs.recycle()
        }
    }

    private fun initListener() {
        with(binding) {
            edtInput.setOnClickListener {
                onClickDropdownItem("man")
            }
            imageView.setOnClickListener {
                clearData()
            }
        }
    }

    internal fun setData(data: String) {
        binding.edtInput.setText(data)
        updateIcon()
        hideError()
    }

    internal fun getData(): String {
        return binding.edtInput.text.toString()
    }

    internal fun validate(): Boolean {
        val valid = !required || getData().isNotEmpty()
        if (!valid) showError(errorText) else hideError()
        return valid
    }

    private fun showError(msg: String) {
        with(binding) {
            tvError.text = msg
            tvError.visibility = View.VISIBLE
        }
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    internal fun onChangeTextListener(listener: () -> Unit) {
        binding.edtInput.addTextChangedListener {
            listener()
        }
    }

    private fun clearData() {
        binding.edtInput.text?.clear()
        updateIcon()
        onClearSelectedItem()
    }

    private fun updateIcon() {
        if (!isSelectedView) return
        val hasData = binding.edtInput.text.isNotEmpty()
        val iconRes = if (hasData) R.drawable.ic_close_modal else R.drawable.ic_arrow_down
        binding.imageView.setImageResource(iconRes)
    }
}
