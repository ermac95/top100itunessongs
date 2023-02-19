package com.example.top100itunessongs.presentation.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.top100itunessongs.MyApp
import com.example.top100itunessongs.R
import com.example.top100itunessongs.adapters.SongsListAdapter
import com.example.top100itunessongs.databinding.FragmentSongsListBinding
import com.example.top100itunessongs.presentation.viewmodels.AppViewModelFactory
import com.example.top100itunessongs.presentation.viewmodels.SongsViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


class SongsListFragment : Fragment(R.layout.fragment_songs_list) {

    private val viewBinding by viewBinding(FragmentSongsListBinding::bind)

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var songsViewModel: SongsViewModel

    private val songsListAdapter by lazy {
        SongsListAdapter(
            requireContext(),
            emptyList(),
        ) { shareLink ->
            shareWithFriends(shareLink)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupViews()
        setupSearchView()
    }

    private fun setupViewModels() {
        songsViewModel = ViewModelProvider(this, viewModelFactory)[SongsViewModel::class.java]
    }

    private fun setupViews() = with(viewBinding) {
        showShimmer(true)
        rvSongsList.apply {
            adapter = songsListAdapter
        }

        lifecycleScope.launchWhenResumed {
            songsViewModel.songsList.collectLatest { songsList ->
                if (songsList.isNotEmpty()) {
                    showShimmer(false)
                }
                songsListAdapter.updateData(songsList)
            }
        }

        lifecycleScope.launchWhenResumed {
            songsViewModel.errorMessage.collectLatest { error->
                changeErrorStatus(error)
            }
        }
    }

    private fun setupSearchView() = with(viewBinding) {
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                songsViewModel.searchSongsByQuery(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                songsViewModel.searchSongsByQuery(newText ?: "")
                return false
            }
        })
    }

    private fun shareWithFriends(shareLink: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val messageTitle = getString(R.string.share_text_title)
        val message = "$messageTitle $shareLink"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_intent_title)))
    }

    private fun showShimmer(isShow: Boolean) = with(viewBinding) {
        if (isShow) {
            rvSongsList.visibility = View.GONE
            shimmerLayout.startShimmer()
        } else {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            rvSongsList.visibility = View.VISIBLE
        }
    }

    private fun changeErrorStatus(error: String) {
        if (error.isEmpty()){
            viewBinding.errorMessage.visibility = View.GONE
        } else {
            viewBinding.errorMessage.visibility = View.VISIBLE
            viewBinding.errorMessage.text = error
        }
    }
}