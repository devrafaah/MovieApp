package com.example.movieapp.presenter.main.bottomBar.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.BottomSheetLogoutBinding
import com.example.movieapp.databinding.FragmentProfileBinding
import com.example.movieapp.domain.model.menu.MenuProfile
import com.example.movieapp.domain.model.menu.MenuProfileType
import com.example.movieapp.domain.model.user.User
import com.example.movieapp.presenter.auth.activity.AuthActivity
import com.example.movieapp.presenter.auth.activity.AuthActivity.Companion.AUTHENTICATION_PARAMETER
import com.example.movieapp.presenter.auth.enums.AuthenticationDestinations
import com.example.movieapp.presenter.main.bottomBar.profile.adapter.ProfileAdapter
import com.example.movieapp.util.FirebaseHelper
import com.example.movieapp.util.StateView
import com.example.movieapp.util.applyScreenWindowInsets
import com.example.movieapp.util.showSnackBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileAdapter: ProfileAdapter

    private val viewmodel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyScreenWindowInsets(view = view, applyBottom = false)
        initListeners()
    }

    private fun initListeners() {
        initRecyclerView()
        getUser()
    }


    private fun initRecyclerView() {
        profileAdapter = ProfileAdapter(
            items = MenuProfile.menuOptions,
            context = requireContext(),
            onClick = { type ->
                when(type){
                    MenuProfileType.PROFILE -> {
                        findNavController().navigate(R.id.action_menu_profile_to_editProfileFragment)
                    }
                    MenuProfileType.NOTIFICATION -> {

                    }
                    MenuProfileType.DOWNLOAD -> {
                        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.btnv)
                        bottomNavigation?.selectedItemId = R.id.menu_download
                    }
                    MenuProfileType.SECURITY -> {

                    }
                    MenuProfileType.LANGUAGE -> {

                    }
                    MenuProfileType.DARK_MODE -> {

                    }
                    MenuProfileType.HELPER -> {

                    }
                    MenuProfileType.PRIVACY_POLICE -> {

                    }
                    MenuProfileType.LOGOUT -> {
                        showBottomSheetLogout()
                    }
                }
            }
        )

        with(binding.recyclerItems) {
            adapter = profileAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getUser() {
        viewmodel.getUser().observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBarLoading.isVisible = false
                    val user = stateView.data
                    configData(user)
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

    private fun configData(user: User?) {

        binding.profileUsername.text = user?.name ?: "Desconhecido"
        binding.profileEmail.text = FirebaseHelper.getAuth().currentUser?.email.toString()

        Log.i("INFOTESTE", "configData: ${user.toString()}")

        if(user?.photoUrl?.isNotEmpty() == true) {
            Glide
                .with(this)
                .load(user.photoUrl)
                .into(binding.profileImage)
        } else {
            Glide
                .with(this)
                .asDrawable()
                .load(R.drawable.nulo)
                .into(binding.profileImage)
        }

    }

    private fun showBottomSheetLogout() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetLogoutBinding.inflate(
            layoutInflater, null, false
        )

        bottomSheetBinding.btnLogout.setOnClickListener { logout() }
        bottomSheetBinding.btnCancel.setOnClickListener { bottomSheetDialog.dismiss() }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        activity?.finish()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.putExtra(AUTHENTICATION_PARAMETER, AuthenticationDestinations.LOGIN_SCREEN)
        startActivity(intent)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}