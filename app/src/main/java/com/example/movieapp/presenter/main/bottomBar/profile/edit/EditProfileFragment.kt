package com.example.movieapp.presenter.main.bottomBar.profile.edit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.BottomSheetConfigPermitionBinding
import com.example.movieapp.databinding.BottomSheetSelectImageBinding
import com.example.movieapp.databinding.FragmentEditProfileBinding
import com.example.movieapp.domain.model.user.User
import com.example.movieapp.util.FirebaseHelper
import com.example.movieapp.util.StateView
import com.example.movieapp.util.applyScreenWindowInsets
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.initToolbar
import com.example.movieapp.util.showSnackBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()


    private val GALERY_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE

    private val CAMERA_PERMISSION = Manifest.permission.CAMERA


    private var currentPhotoUri: Uri? = null
    private var _user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyScreenWindowInsets(view = view)
        initToolbar(binding.toolbar, showIconNavigation = true)
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.validateData.observe(viewLifecycleOwner) { (validated, stringResId) ->
            if(!validated) {
                stringResId?.let {
                    showSnackBar(message = it)
                }
            } else {
                if(currentPhotoUri != null) {
                    saveUserImage()
                } else {
                    updateUser()
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnAtualizar.setOnClickListener {
            hideKeyboard()
            viewModel.validateData(
                firstName = binding.editFirstName.text.toString(),
                lastName = binding.editLastName.text.toString(),
                phone = binding.editPhone.text.toString(),
                sex = binding.editSex.text.toString(),
                country = binding.editCountry.text.toString(),
            )
        }
        getUser()
        Glide
            .with(this)
            .load(R.drawable.loading)
            .into(binding.progressBarLoading);

        binding.btnProfileEdit.setOnClickListener {
            openBottomSheetSelectImage()
        }
        binding.profileImage.setOnClickListener {
            openBottomSheetSelectImage()
        }
    }

    private fun openBottomSheetSelectImage() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetSelectImageBinding.inflate(
            layoutInflater, null, false
        )

        bottomSheetBinding.btnCamera.setOnClickListener {
            bottomSheetDialog.dismiss()
            cameraPermission()
        }
        bottomSheetBinding.btnGallery.setOnClickListener {
            bottomSheetDialog.dismiss()
            galleryPermission()
        }


        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }




    // CRIAR ARQUIVO TEMPORARIO QUE ESTA UTILIZADO PARA GERAR A IMAGEM
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)


        return imageFile
    }




    // solicitar permission Gallery
    private fun galleryPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if(checkPermissionGranted(GALERY_PERMISSION)) {
                // abrir galeria caso ja tenha sido aceita.
                openGallery.launch("image/*")
            }else {
                //solicitar permission galeria
                requestGalleryPermissionLauncher.launch(GALERY_PERMISSION)
            }
        } else {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }


    //solicitar permission Camera
    private fun cameraPermission() {
        if (checkPermissionGranted(CAMERA_PERMISSION)) {
            // abrir a camera caso ja tenha sido aceita.
            openCamera()
        } else {
            //solicitar permission camera
            requestCameraPermissionLauncher.launch(CAMERA_PERMISSION)
        }
    }

    private fun checkPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED


    private val requestGalleryPermissionLauncher = registerForActivityResult(ActivityResultContracts
        .RequestPermission()) { isGranted ->
            if(isGranted) {
                openGallery.launch("image/*")
            }else {
                showBottomSheetPermissionDenied()
            }
        }

    private val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts
        .RequestPermission()) { isGranted ->
        if(isGranted) {
            openCamera()
        }else {
            showBottomSheetPermissionDenied()
        }
    }


    // abrir galleria android -12
    private val openGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            currentPhotoUri = it
            binding.profileImage.setImageURI(it)
        }
    }
    // abrir galleria android +13
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            currentPhotoUri = uri
            binding.profileImage.setImageURI(uri)
        }
    }
    //abrir câmera
    private val takePictureLauncher = registerForActivityResult( ActivityResultContracts.TakePicture() ) { sucess: Boolean ->
        if(sucess) {
            binding.profileImage.setImageURI(currentPhotoUri)
        }
    }


    // ABRIR A CAMERA DO DISPOSITIVO
    private fun openCamera() {
        val photoFile = createImageFile()
        photoFile?.let {
            currentPhotoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                it
            )
            takePictureLauncher.launch(currentPhotoUri)
        }
    }




    private fun showBottomSheetPermissionDenied() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetConfigPermitionBinding.inflate(
            layoutInflater, null, false
        )

        bottomSheetBinding.btnCancelarPermition.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetBinding.btnAceitarPermitionConfig.setOnClickListener {
            bottomSheetDialog.dismiss()

            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireContext().packageName, null)
            )
            startActivity(intent)
        }


        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }



    private fun updateUser(url : String? = null) {

        val user = User(
            id = FirebaseHelper.getAuth().currentUser?.uid ?: "",
            name = binding.editFirstName.text.toString(),
            lastName = binding.editLastName.text.toString(),
            phone = binding.editPhone.text.toString(),
            email = FirebaseHelper.getAuth().currentUser?.email,
            sex = binding.editSex.text.toString(),
            country = binding.editCountry.text.toString(),
            photoUrl = url ?: _user?.photoUrl
        )

        println(FirebaseHelper.getAuth().currentUser?.email.toString())


        viewModel.userUpdate(user).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBarLoading.isVisible = false
                    showSnackBar(
                        R.string.fragment_edit_text_success
                    )
                }
                is StateView.Error -> {
                    binding.progressBarLoading.isVisible = false
                    showSnackBar(
                        FirebaseHelper.validError(stateView.message?: "")
                    )
                }
            }
        }
    }

    private fun saveUserImage() {
        currentPhotoUri?.let {
            viewModel.saveUserImage(it).observe(viewLifecycleOwner) { stateView ->
                when(stateView) {
                    is StateView.Loading -> {
                        binding.progressBarLoading.isVisible = true
                    }
                    is StateView.Success -> {
                        binding.progressBarLoading.isVisible = false
                        showSnackBar(
                            R.string.fragment_edit_text_success
                        )
                        updateUser(stateView.data)

                    }
                    is StateView.Error -> {
                        binding.progressBarLoading.isVisible = false
                        showSnackBar(
                            FirebaseHelper.validError(stateView.message?: "")
                        )
                    }
                }
            }
        }
    }

    private fun getUser() {
        viewModel.getUser().observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBarLoading.isVisible = false
                    _user = stateView.data
                    configData()
                }
                is StateView.Error -> {
                    binding.progressBarLoading.isVisible = false
                    showSnackBar(
                        FirebaseHelper.validError(stateView.message?: "")
                    )
                }
            }
        }
    }

    private fun configData() {
        binding.editFirstName.setText(_user?.name)
        binding.editLastName.setText(_user?.lastName)
        binding.editPhone.setText(_user?.phone)
        binding.editGmail.setText(FirebaseHelper.getAuth().currentUser?.email)
        binding.editSex.setText(_user?.sex)
        binding.editCountry.setText(_user?.country)

        if(_user?.photoUrl?.isNotEmpty() == true) {
            Glide
                .with(this)
                .load(_user!!.photoUrl)
                .into(binding.profileImage)
        } else {
            Glide
                .with(this)
                .asDrawable()
                .load(R.drawable.nulo)
                .into(binding.profileImage)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}