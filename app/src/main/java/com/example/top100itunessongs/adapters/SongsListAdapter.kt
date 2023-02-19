package com.example.top100itunessongs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.top100itunessongs.R
import com.example.top100itunessongs.data.model.SongUiModel
import com.example.top100itunessongs.databinding.SongItemBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class SongsListAdapter(
    private val context: Context,
    private var songsList: List<SongUiModel>,
    private val onShareSongAction: (songLink: String) -> Unit
): RecyclerView.Adapter<SongsListAdapter.SongItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
        val itemBinding = SongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongItemViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        val songItem = songsList[position]
        holder.bind(songItem)
    }

    override fun getItemCount(): Int = songsList.size

    fun updateData(updatedSongsList: List<SongUiModel>){
        this.songsList = updatedSongsList
        notifyDataSetChanged()
    }

    inner class SongItemViewHolder(
        private val context: Context,
        private val itemBinding: SongItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val shimmer = Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(1800) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        // This is the placeholder for the imageView
        private val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        fun bind(songItem: SongUiModel) = with(itemBinding){
            Glide.with(itemView)
                .load(songItem.imageUrl)
                .placeholder(shimmerDrawable)
                .into(image)
            title.text = songItem.title
            artistGenreTitle.text = String.format(context.getString(R.string.artist_genre, songItem.artist, songItem.genre))
            price.text = songItem.price
            shareAction.setOnClickListener {
                onShareSongAction.invoke(songItem.shareUrl)
            }
        }
    }
}
