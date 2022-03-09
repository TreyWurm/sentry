package com.example.paginationscratchapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.paginationscratchapp.R
import com.example.paginationscratchapp.data.model.Profile

class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isLoadingAdded = false
    private val movies : MutableList<Profile> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when(viewType){
            ITEM -> {
                val viewItem = inflater.inflate(R.layout.item_list,parent,false)
                viewHolder = MovieViewHolder(viewItem)

            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.item_loading,parent,false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies[position]
        when(getItemViewType(position)){
            ITEM -> {
                val movieViewHolder = MovieViewHolder(holder.itemView)
                movieViewHolder.onBind(movie)
            }
            LOADING -> {
                val loadingViewHolder = LoadingViewHolder(holder.itemView)
                loadingViewHolder.onBind()
            }
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun getItemViewType(position: Int): Int {
        return if (position == movies.size - 1 && isLoadingAdded) LOADING else ITEM
    }
    fun addLoadingFooter(){
        isLoadingAdded = true
        add(Profile())
    }

    fun removeLoadingFooter(){
        isLoadingAdded = false
        val position = movies.size - 1

        movies.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setData(newData: List<Profile>){
        movies.addAll(newData)
    }
    fun notifyDataSet(){
        notifyDataSetChanged()
    }

    private fun add(movie: Profile){
        movies.add(movie)
        notifyItemInserted(movies.size - 1)
    }

    class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var movieTitle : TextView = itemView.findViewById(R.id.tv_movie)
        var movieImage : ImageView = itemView.findViewById(R.id.iv_movie)
        fun onBind(movie: Profile){
            movieTitle.text = movie.firstName
            Glide.with(itemView).load(movie.pictureUrl).apply(RequestOptions.centerCropTransform()).into(movieImage)
        }
    }
    class LoadingViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var loadingBar : ProgressBar =itemView.findViewById(R.id.pb_movie)
        fun onBind(){
            loadingBar.visibility =  View.VISIBLE
        }
    }
    companion object{
        private const val LOADING = 0
        private const val ITEM = 1
    }
}