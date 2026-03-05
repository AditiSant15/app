package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.database.MovieEntity
import com.squareup.picasso.Picasso

class FavourtieRecyclerAdapter(
    val context: Context,
    val movieList: ArrayList<MovieEntity>
) : RecyclerView.Adapter<FavourtieRecyclerAdapter.FavourtieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavourtieViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_row, parent, false)

        return FavourtieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: FavourtieViewHolder, position: Int) {

        val movie = movieList[position]

        holder.txtFavMovieTitle.text = movie.movieName
        holder.txtFavMovieAuthor.text = movie.movieAuthor
        holder.txtFavMoviePrice.text = movie.moviePrice
        holder.txtFavMovieRating.text = movie.movieRating

        Picasso.get()
            .load(movie.movieImage)
            .error(R.drawable.default_image)
            .into(holder.imgFavMovieImage)
    }

    class FavourtieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtFavMovieTitle: TextView = view.findViewById(R.id.txtFavMovieTitle)
        val txtFavMovieAuthor: TextView = view.findViewById(R.id.txtFavMovieAuthor)
        val txtFavMoviePrice: TextView = view.findViewById(R.id.txtFavMoviePrice)
        val txtFavMovieRating: TextView = view.findViewById(R.id.txtFavMovieRating)
        val imgFavMovieImage: ImageView = view.findViewById(R.id.imgFavMovieImage)
        val llFavContent: LinearLayout = view.findViewById(R.id.llFavContent)

    }
}