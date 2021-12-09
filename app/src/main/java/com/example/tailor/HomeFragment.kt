package com.example.tailor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tailor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
 private lateinit var binding: FragmentHomeBinding

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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
        R.id.ExploreFragment ->{findNavController().navigate(R.id.action_homeFragment_to_exploreFragment)}
        R.id.ProfileFragment -> {findNavController().navigate(R.id.action_homeFragment_to_profileFragment)}

        }
        return super.onOptionsItemSelected(item)
    }
}