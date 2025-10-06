package com.example.android_base_2025

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android_base_2025.data.vo.PickerItem
import com.example.android_base_2025.databinding.ActivityMainBinding
import com.example.android_base_2025.widget.CustomDialog

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val itemsProject = listOf(
        PickerItem("1", "Dự án 1"),
        PickerItem("2", "Dự án 2"),
        PickerItem("3", "Dự án 3"),
        PickerItem("4", "Dự án 4"),
        PickerItem("5", "Dự án 5")
    )

    private val itemsFolder = listOf(
        PickerItem("1", "Image"),
        PickerItem("2", "Document"),
        PickerItem("3", "Music"),
        PickerItem("4", "Media"),
        PickerItem("5", "Sound")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.run {
            ctvJob.onClickEditInput {
                CustomDialog(itemsProject) { selectedItem ->
                    ctvJob.setData(selectedItem.name)
                }.show(supportFragmentManager, "SearchPicker")
            }

            ctvFolder.onClickEditInput {
                CustomDialog(itemsFolder) { selectedItem ->
                    ctvFolder.setData(selectedItem.name)
                }.show(supportFragmentManager, "SearchPicker")
            }
            ctbUpload.setOnClickListener {
                Log.d("123", "vaoday")
            }
        }
    }
}