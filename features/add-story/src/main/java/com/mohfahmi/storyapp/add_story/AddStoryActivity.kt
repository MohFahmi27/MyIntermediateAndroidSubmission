package com.mohfahmi.storyapp.add_story

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.viewbinding.library.activity.viewBinding
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.mohfahmi.storyapp.add_story.databinding.ActivityAddStoryBinding
import com.mohfahmi.storyapp.core.R.drawable.img_photo_loading
import com.mohfahmi.storyapp.core.R.string.*
import com.mohfahmi.storyapp.core.common_ui.ErrorBottomSheetDialogFragment
import com.mohfahmi.storyapp.core.common_ui.LoadingDialogFragment
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.*
import com.shashank.sony.fancytoastlib.FancyToast
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddStoryActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val binding: ActivityAddStoryBinding by viewBinding()
    private val viewModel: AddStoryViewModel by viewModel()
    private val loadingFragment: LoadingDialogFragment by lazy {
        LoadingDialogFragment()
    }
    private lateinit var launcherIntentCamera: ActivityResultLauncher<Intent>
    private lateinit var launcherIntentGallery: ActivityResultLauncher<Intent>
    private lateinit var currentPhotoPath: String
    private var myImgStory: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCameraIntent()
        initGalleryIntent()
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnCamera.setOnClickListener {
                if (hasCameraPermission()) {
                    startCamera()
                } else {
                    requestCameraPermission()
                }
            }
            btnGallery.setOnClickListener {
                if (hasStoragePermission()) {
                    startGallery()
                } else {
                    requestStoragePermission()
                }
            }
            edAddDescription.doAfterTextChanged {
                viewModel.descriptionValue.value = it.toString()
            }
            btnUpload.setOnClickListener {
                val file = reduceFileImage(myImgStory as File)
                uploadStory(edAddDescription.text.toString(), file)
            }
            lifecycleScope.launch {
                viewModel.isUploadButtonEnable.collect {
                    btnUpload.isEnabled = it
                }
            }
        }
    }

    private fun initCameraIntent() {
        launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val myFile = File(currentPhotoPath)
                val bitmapResult = rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    true
                )

                viewModel.isImagePicked.value = true
                myImgStory = myFile
                binding.imgStory.setImageBitmap(bitmapResult)
            } else {
                binding.imgStory.setImageResource(img_photo_loading)
            }
        }
    }

    private fun initGalleryIntent() {
        launcherIntentGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
                myImgStory = uriToFile(selectedImg, this)
                viewModel.isImagePicked.value = true
                binding.imgStory.setImageURI(selectedImg)
            } else {
                binding.imgStory.setImageResource(img_photo_loading)
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.mohfahmi.storyapp",
                it
            )

            currentPhotoPath = it.absolutePath
            myImgStory = File(currentPhotoPath)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        FancyToast.makeText(
            this,
            getString(permission_granted),
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        ).show()
    }

    private fun hasCameraPermission() =
        EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)

    private fun requestCameraPermission() {
        if (!hasCameraPermission()) {
            EasyPermissions.requestPermissions(
                this,
                getString(permission_camera_request),
                REQUEST_CAMERA_PERMISSION,
                Manifest.permission.CAMERA
            )
        }
    }

    private fun requestStoragePermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(permission_storage_request),
            REQUEST_STORAGE_PERMISSION,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun hasStoragePermission() =
        EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)

    private fun uploadStory(description: String, img: File) {
        viewModel.token.observe(this) {
            viewModel.uploadStory(it, description, img).observe(
                this,
                ::manageUploadStoryResponse
            )
        }
    }

    private fun manageUploadStoryResponse(uiState: UiState<UploadStory>) {
        when (uiState.status) {
            Status.LOADING -> {
                loadingFragment.show(supportFragmentManager, LoadingDialogFragment.TAG)
            }
            Status.HIDE_LOADING -> {
                loadingFragment.dismiss()
            }
            Status.SUCCESS -> {
                FancyToast.makeText(
                    this@AddStoryActivity,
                    getString(upload_story_success),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                finish()
            }
            Status.ERROR -> {
                ErrorBottomSheetDialogFragment(
                    uiState.message ?: getString(something_went_wrong)
                ).show(supportFragmentManager, ErrorBottomSheetDialogFragment.TAG)
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_STORAGE_PERMISSION = 2
    }
}