package com.example.android_base_2025

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.android_base_2025.data.local.NoteRepository
import com.example.android_base_2025.data.local.UserFormRepository
import com.example.android_base_2025.data.vo.PickerItem
import com.example.android_base_2025.data.vo.UserData
import com.example.android_base_2025.databinding.ActivityMainBinding
import com.example.android_base_2025.ui.FlowContainerFragment
import com.example.android_base_2025.ui.FormFragment
import com.example.android_base_2025.ui.adapter.UserFormAdapter
import com.example.android_base_2025.utils.AppPrefs
import com.example.android_base_2025.widget.CustomDialog
import com.example.android_base_2025.widget.CustomDropdownView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private var selectedImageUri: Uri? = null
    private var selectedBitmap: Bitmap? = null
    private lateinit var noteRepository: NoteRepository
    private var formsAdapter: UserFormAdapter? = null
    private lateinit var userFormRepository: UserFormRepository

    private val itemsProject = listOf(
        PickerItem(
            "1",
            "Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 1Dự án 12222"
        ),
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

        AppPrefs.init(this) //init SharePreferences

        restoreFlowData()

        // Room demo: init repository
        noteRepository = NoteRepository(this)

        userFormRepository = UserFormRepository(this)
        observeForms()

        binding?.run {
            rvForms.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
            formsAdapter = UserFormAdapter { item ->
                lifecycleScope.launch { userFormRepository.delete(item.id) }
            }
            rvForms.adapter = formsAdapter
            val b = ctvJob.apply {
                onClickDropdownItem = {
                    handleShowItem(
                        data = itemsProject, view = this
                    )
                }

                onClearSelectedItem = {
                    itemsProject.forEach {
                        it.isSelected = false
                    }
                    AppPrefs.saveFlowData(AppPrefs.KEY_JOB, "")
                }

                "String"
            }

            val aLet = ctvJob.let {

            }

            val aAlso = ctvJob.also {

            }

            val aWith = with(ctvJob) {

            }

            val a = ctvJob.run {
                onClickDropdownItem = {
                    handleShowItem(
                        data = itemsProject, view = this
                    )
                }

                onClearSelectedItem = {
                    itemsProject.forEach {
                        it.isSelected = false
                    }
                    AppPrefs.saveFlowData(AppPrefs.KEY_JOB, "")
                }

                "String"
            }

            with(ctvJob) {
                onClickDropdownItem = {
                    handleShowItem(
                        data = itemsProject, view = this
                    )
                }

                onClearSelectedItem = {
                    itemsProject.forEach {
                        it.isSelected = false
                    }
                    AppPrefs.saveFlowData(AppPrefs.KEY_JOB, "")
                }
            }

            with(ctvFolder) {
                onClickDropdownItem = {
                    handleShowItem(
                        data = itemsFolder, view = ctvFolder
                    )
                }
                onClearSelectedItem = {
                    itemsFolder.forEach {
                        it.isSelected = false
                    }
                    AppPrefs.saveFlowData(AppPrefs.KEY_FOLDER, "")
                }
            }

            bgUpload.setOnClickListener {
                showImagePickerDialog()
            }

            ctbUpload.setOnClickListener {
                handleUpload()
            }
            ctvComment.onChangeTextListener {
                validateForm()
                AppPrefs.saveFlowData(AppPrefs.KEY_COMMENT, ctvComment.getData())
            }

            btnUserInfo.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FormFragment())
                    .addToBackStack("FormFragment").commit()
            }
            btnNavigation.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, FlowContainerFragment())
                    .addToBackStack("FlowContainerFragment").commit()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleShowItem(data: List<PickerItem>, view: CustomDropdownView) {
        CustomDialog(data).apply {
            onItemSelected = { selectedItem ->
                view.setData(selectedItem.name)
                validateForm()

                data.onEach {
                    it.isSelected = it == selectedItem
                }
                adapter?.notifyDataSetChanged()

                // Lưu dữ liệu khi chọn item
                if (view == binding?.ctvJob) {
                    AppPrefs.saveFlowData(AppPrefs.KEY_JOB, selectedItem.name)
                } else if (view == binding?.ctvFolder) {
                    AppPrefs.saveFlowData(AppPrefs.KEY_FOLDER, selectedItem.name)
                }
            }
        }.show(supportFragmentManager, "SearchPicker")
    }

    private fun validateForm(): Boolean {
        val allValid: Boolean = binding?.run {
            val jobValid = ctvJob.validate()
            val folderValid = ctvFolder.validate()
            val commentValid = ctvComment.validate()

            val valid = jobValid && folderValid && commentValid
            ctbUpload.isEnabled = valid
            valid
        } ?: false
        return allValid
    }

    private fun handleUpload() {
        if (!validateForm()) return

        binding?.run {
            val job = ctvJob.getData()
            val folder = ctvFolder.getData()
            val comment = ctvComment.getData()
            Log.d("UPLOAD", "Job: $job")
            Log.d("UPLOAD", "Folder: $folder")
            Log.d("UPLOAD", "Comment: $comment")
            Log.d("UPLOAD", "ImageUri: $selectedImageUri")
            Log.d("UPLOAD", "Bitmap: ${selectedBitmap != null}")
            AppPrefs.clearFlowData()
            ctvJob.setData("")
            ctvFolder.setData("")
            ctvComment.setData("")
            itemsProject.forEach {
                it.isSelected = false
            }
            itemsFolder.forEach {
                it.isSelected = false
            }

            android.widget.Toast.makeText(
                this@MainActivity,
                "Upload thành công!",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeForms() {
        lifecycleScope.launch {
            com.example.android_base_2025.data.local.AppDatabase.getInstance(this@MainActivity)
                .userFormDao()
                .getAll()
                .collect { forms ->
                    formsAdapter?.submitList(forms)
                }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf(getString(R.string.take_photo), getString(R.string.select_photo))

        AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }.show()
    }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as? Bitmap
                bitmap?.let {
                    selectedBitmap = it
                    binding?.myImageView?.setImageBitmap(it)
                }
            }
        }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureLauncher.launch(intent)
        }
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it
                binding?.myImageView?.setImageURI(it)
            }
        }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    internal fun onFlowCompleted(flowData: UserData) {
        binding?.ctvComment?.setData(flowData.toString())
    }

    private fun restoreFlowData() {
        binding?.run {
            val savedComment = AppPrefs.getFlowData(AppPrefs.KEY_COMMENT)
            val savedFolder = AppPrefs.getFlowData(AppPrefs.KEY_FOLDER)
            val savedJob = AppPrefs.getFlowData(AppPrefs.KEY_JOB)

            if (savedComment.isNotEmpty()) {
                ctvComment.setData(savedComment)
            }
            if (savedFolder.isNotEmpty()) {
                ctvFolder.setData(savedFolder)
            }
            if (savedJob.isNotEmpty()) {
                ctvJob.setData(savedJob)
            }
        }
    }
}