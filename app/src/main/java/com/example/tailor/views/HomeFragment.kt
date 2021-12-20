package com.example.tailor.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tailor.R
import com.example.tailor.databinding.FragmentHomeBinding
import com.example.tailor.views.adaptersimport.ExploreAdapter
import com.example.tailor.views.adaptersimport.HomeFragmentAdapter


private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeFragmentAdapter: HomeFragmentAdapter
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFragmentAdapter = HomeFragmentAdapter(requireActivity())
        binding.recyclerView.adapter = homeFragmentAdapter
        observers()
        homeViewModel.getHomePhoto()

    }

   private fun observers(){
       homeViewModel.homePhotoLiveData.observe(viewLifecycleOwner,{
           it?.let {
                   homeFragmentAdapter.submitList(it)
                   Log.d(TAG,it.toString())
           }
       })
   }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
        R.id.exploreFragment ->{findNavController().navigate(R.id.action_homeFragment_to_exploreFragment)}
        R.id.profileFragment -> {// if user is logged in .. show profile else show login screen
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment) }
        }
        return super.onOptionsItemSelected(item)
    }

}