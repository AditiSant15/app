package com.example.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.model.Movie
import java.util.ArrayList
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Movie>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size

    }

    override fun onBindViewHolder(
        holder: DashboardViewHolder,
        position: Int
    ) {
        val movie = itemList[position]
        holder.txtMovieName.text = movie.movieName
        holder.txtMovieAuthor.text = movie.movieAuthor
        holder.txtMovieCost.text = movie.movieCost
        holder.txtMovieRating.text = movie.movieRating
        holder.imgMovieImage.setImageResource(movie.movieImage)

        holder.llContent.setOnClickListener{
            Toast.makeText(context,"Clicked on ${holder.txtMovieName.text}",Toast.LENGTH_SHORT).show()
        }
    }

    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtMovieName: TextView = view.findViewById(R.id.txtMovieName)
        val txtMovieAuthor: TextView = view.findViewById(R.id.txtMovieAuthor)
        val txtMovieCost: TextView = view.findViewById(R.id.txtMovieCost)
        val txtMovieRating: TextView = view.findViewById(R.id.txtMovieRating)
        val imgMovieImage: ImageView = view.findViewById(R.id.imgMovieImage)

        val llContent: LinearLayout = view.findViewById(R.id.llContent)


    }
}