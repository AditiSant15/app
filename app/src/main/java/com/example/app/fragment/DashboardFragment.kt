package com.example.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.adapter.DashboardRecyclerAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.app.model.Movie

class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    val movieList = arrayListOf(
        "Inception",
        "The Dark Knight",
        "Titanic",
        "Avengers: Endgame",
        "The Shawshank Redemption",
        "Interstellar",
        "The Pursuit of Happyness",
        "Jumanji: Welcome to the Jungle",
        "Frozen",
        "The Conjuring"
    )

    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    val movieInfoList = arrayListOf<Movie>(
        Movie(
             "Inception",
            "Christopher Nolan",
            "Rs.160",
             "8.8/10", R.drawable.inception
        ),

        Movie(
             "The Dark Knight",
             "Christopher Nolan",
            "Rs.160",
            "9.0/10",R.drawable.knight
        ),

        Movie(
             "Titanic",
             "James Cameron",
            "Rs.160",
             "7.9/10", R.drawable.titanic
        ),

        Movie(
           "Avengers: Endgame",
            "Anthony & Joe Russo",
            "Rs.160",
            "8.4/10", R.drawable.avengers
        ),

        Movie(
             "The Shawshank Redemption",
             "Frank Darabont",
            "Rs.160",
            "9.3/10", R.drawable.redemption
        ),

        Movie(
           "Interstellar",
            "Christopher Nolan",
             "Rs.160",
             "8.7/10" , R.drawable.interstillar
        ),

        Movie(
            "The Pursuit of Happyness",
             "Gabriele Muccino","Rs.160",
            "8.0/10",
            R.drawable.happyness
        ),

        Movie(
            "Jumanji: Welcome to the Jungle",
             "Jake Kasdan",
             "Rs.160",
             "6.9/10" , R.drawable.jumanji
        ),

        Movie(
             "Frozen",
            "Chris Buck & Jennifer Lee",
            "Rs.160",
             "7.4/10", R.drawable.frozen
        ),

        Movie(
            "The Conjuring",
            "James Wan",
            "Rs.160",
             "7.5/10", R.drawable.conjuring
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard,container,false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        layoutManager = LinearLayoutManager(activity)

        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, movieInfoList)

        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager

        recyclerDashboard.addItemDecoration(
            DividerItemDecoration(
                recyclerDashboard.context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )


        return view
    }
}