package com.example.nasapictureofthedayhomework.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.nasapictureofthedayhomework.R
import com.example.nasapictureofthedayhomework.databinding.MainFragmentBinding
import com.example.nasapictureofthedayhomework.network.NasaApiService
import com.example.nasapictureofthedayhomework.viewmodel.MainViewModel
import java.time.LocalDate

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!! // how to get rid of !! here?????

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialButtonNext.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
        }

        binding.yesterdayChip.setOnClickListener {
            viewModel.getYesterdayPhoto()
        }

        binding.dayBeforeYesterdayChip.setOnClickListener {
            viewModel.getDayBYesterdayPhoto()
        }

        val someProgressBar = Observer<Boolean> { newProgressBar ->
            binding.progressBar.visibility = if (newProgressBar) View.VISIBLE else View.GONE
        }

        val someTodayButton = Observer<Boolean> { newTodayButton ->
            binding.todayChip.visibility = if (newTodayButton) View.VISIBLE else View.INVISIBLE
        }

        binding.todayChip.setOnClickListener {
            viewModel.getNasaPhoto()
        }

        val someStatus = Observer<String> { newStatus ->
            binding.status.text = newStatus
        }

        val someMediaType = Observer<String> { newMediaType ->
            if (newMediaType == "video")
                binding.pictureOfTheDay.load(R.drawable.ic_baseline_error_24)
            else {
                val somePhoto = Observer<String> { newPhoto ->
                    binding.pictureOfTheDay.load(newPhoto)
                }
                viewModel.photo.observe(viewLifecycleOwner, somePhoto)
            }
        }

        val someCopyright = Observer<String> { newCopyright ->
            binding.bottomSheetCopyright.text =
                if (newCopyright != null) "Copyright: $newCopyright" else "Copyright: "
        }

        val someDate = Observer<String> { newDate ->
            binding.bottomSheetDate.text =
                if (newDate != null) "Date: $newDate" else "Date: "
        }

        val someTitle = Observer<String> { newTitle ->
            binding.bottomSheetTitle.text =
                if (newTitle != null) "Title: $newTitle" else "Title: "
        }

        val someDescription = Observer<String> { newDescription ->
            binding.bottomSheetDescription.text =
                if (newDescription != null) "Description: $newDescription" else "Description: "
        }


        viewModel.progressBar.observe(viewLifecycleOwner, someProgressBar)
        viewModel.todayPhotoButton.observe(viewLifecycleOwner, someTodayButton)
        viewModel.status.observe(viewLifecycleOwner, someStatus)
        viewModel.copyright.observe(viewLifecycleOwner, someCopyright)
        viewModel.date.observe(viewLifecycleOwner, someDate)
        viewModel.title.observe(viewLifecycleOwner, someTitle)
        viewModel.description.observe(viewLifecycleOwner, someDescription)
        viewModel.mediaType.observe(viewLifecycleOwner, someMediaType)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}