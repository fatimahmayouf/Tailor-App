package com.example.tailor.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tailor.R
import com.example.tailor.databinding.FragmentExploreBinding
import com.example.tailor.util.setStatusBarColor
import com.example.tailor.views.adaptersimport.ExploreAdapter

private const val TAG = "ExploreFragment"
class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var exploreFragmentAdapter: ExploreAdapter
    private val exploreViewModel : ExploreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()
        exploreFragmentAdapter = ExploreAdapter(requireActivity(),exploreViewModel)
        binding.exploreRececlerView.adapter = exploreFragmentAdapter
        exploreViewModel.getPhoto()
        requireActivity().setStatusBarColor(R.color.cardview_light_background)
    }

    private fun observers() {
        exploreViewModel.explorePhotoLiveData
            .observe(viewLifecycleOwner, {
            it?.let {
                //photoList = it.photos
                exploreFragmentAdapter.submitList(it.photos)
                Log.d(TAG,it.photos.toString())

            }
        })
    }

}
