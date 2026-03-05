package com.example.app.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.app.R
import com.example.app.adapter.FavourtieRecyclerAdapter
import com.example.app.database.MovieDatabase
import com.example.app.database.MovieEntity

class FavouritesFragment : Fragment() {
    lateinit var recyclerFavourite: RecyclerView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavourtieRecyclerAdapter
    var dbMovieList = listOf<MovieEntity>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)

        layoutManager = GridLayoutManager(activity as Context, 2)

        dbMovieList = RetrieveFavourites(activity as Context).execute().get()

        if (activity != null) {
            progressLayout.visibility = View.GONE
            recyclerAdapter = FavourtieRecyclerAdapter(activity as Context,
                dbMovieList as ArrayList<MovieEntity>
            )
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }
        return view
    }

    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<MovieEntity>>() {
        override fun doInBackground(vararg params: Void?): List<MovieEntity>? {
            val db = Room.databaseBuilder(context, MovieDatabase::class.java, "movies-db").build()
            return db.MovieDao().getAllMovies()
        }

    }
}