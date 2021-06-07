package com.example.ndsmovieapp.Activity.MainActivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ndsmovieapp.Activity.DetailMovieActivity.DetailMovie
import com.example.ndsmovieapp.Data.Model.Movies
import com.example.ndsmovieapp.Data.Repository.NetworkState
import com.example.ndsmovieapp.Data.Utils.Credentials.Companion.IMAGE_URL
import com.example.ndsmovieapp.R
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_items.view.*

class MainAdapter(public val context: Context) :PagedListAdapter<Movies, RecyclerView.ViewHolder>(MovieDiffCallback()){

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE){
            view = layoutInflater.inflate(R.layout.movie_list_item,parent,false)
            return MovieItemViewHolder(view)
        }
        else {
            view = layoutInflater.inflate(R.layout.network_state_items,parent,false)
            return NetworkStateItemViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow():Boolean{
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount():Int{
        return super.getItemCount() + if(hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }



    class MovieDiffCallback : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem == newItem
        }
    }

        class MovieItemViewHolder (view : View) : RecyclerView.ViewHolder(view){
            fun bind(movie: Movies? ,context:Context){
                itemView.cv_movie_title.text = movie?.title

                val moviePosterURL = IMAGE_URL + movie?.posterPath
                Glide.with(itemView.context)
                    .load(moviePosterURL)
                    .into(itemView.cv_iv_movie_poster)

                itemView.setOnClickListener{
                    val intent = Intent(context,DetailMovie::class.java)
                    intent.putExtra("id",movie?.id)
                    context.startActivity(intent)
                }
            }
        }
        class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

            fun bind(networkState: NetworkState?) {
                if (networkState != null && networkState == NetworkState.LOADING) {
                    itemView.progress_bar_item.visibility = View.VISIBLE;
                }
                else  {
                    itemView.progress_bar_item.visibility = View.GONE;
                }

                if (networkState != null && networkState == NetworkState.ERROR) {
                    itemView.error_message_item.visibility = View.VISIBLE;
                    itemView.error_message_item.text = networkState.msg;
                }
                else if (networkState != null && networkState == NetworkState.LAST) {
                    itemView.error_message_item.visibility = View.VISIBLE;
                    itemView.error_message_item.text = networkState.msg;
                }
                else {
                    itemView.error_message_item.visibility = View.GONE;
                }
            }
        }


        fun setNetworkState(newNetworkState: NetworkState) {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            this.networkState = newNetworkState
            val hasExtraRow = hasExtraRow()

            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                    notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
                } else {                                       //hasExtraRow is true and hadExtraRow false
                    notifyItemInserted(super.getItemCount())   //add the progressbar at the end
                }
            } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
                notifyItemChanged(itemCount - 1)       //add the network message at the end
            }

        }

}