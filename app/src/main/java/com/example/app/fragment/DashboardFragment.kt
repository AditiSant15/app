package com.example.app.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.adapter.DashboardRecyclerAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.app.model.Movie
import com.example.app.util.ConnectionManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Response
import com.android.volley.Request
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.RelativeLayout
import org.json.JSONException
import java.util.Collections

class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    val movieInfoList = arrayListOf<Movie>()

    var ratingComparator = Comparator<Movie> { movie1, movie2 ->
        if (movie1.movieRating.compareTo(movie2.movieRating, true) == 0) {
            movie1.movieName.compareTo(movie2.movieName, true)
        } else {
            movie1.movieRating.compareTo(movie2.movieRating, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard,container,false)

        setHasOptionsMenu(true)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE


        layoutManager = LinearLayoutManager(activity)


        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v2/Movies/fetch_Movie/"

       if (ConnectionManager().checkConnectivity(activity as Context)) {

           val jsonObjectRequest = object : JsonObjectRequest(
               Request.Method.GET, url, null,
               Response.Listener {
                   try {
                       progressLayout.visibility = View.GONE
                       val success = it.getBoolean("success")
                       if (success) {
                           val data = it.getJSONArray("data")
                           for (i in 0 until data.length()) {
                               val movieJsonObject = data.getJSONObject(i)
                               val movieObject = Movie(
                                   movieJsonObject.getString("movie_id"),
                                   movieJsonObject.getString("name"),
                                   movieJsonObject.getString("author"),
                                   movieJsonObject.getString("rating"),
                                   movieJsonObject.getString("price"),
                                   movieJsonObject.getString("image")
                               )
                               movieInfoList.add(movieObject)
                               recyclerAdapter =
                                   DashboardRecyclerAdapter(activity as Context, movieInfoList)

                               recyclerDashboard.adapter = recyclerAdapter
                               recyclerDashboard.layoutManager = layoutManager

                           }
                       } else {

                           Toast.makeText(
                               activity as Context,
                               "Some Error Occurred!!!",
                               Toast.LENGTH_SHORT
                           ).show()
                       }

                   } catch(e: JSONException){
                       Toast.makeText(
                           activity as Context,
                           "Some unexpected error occurred!!!",
                           Toast.LENGTH_SHORT
                       ).show()
                   }

               }, Response.ErrorListener {
                   if (activity != null){
                   Toast.makeText(
                       activity as Context,
                       "Volley error occurred!!!",
                       Toast.LENGTH_SHORT
                   ).show()
                   }

               }) {
               override fun getHeaders(): MutableMap<String, String> {
                   val headers = HashMap<String, String>()
                   headers["Content-type"] = "application/json"
                   headers["token"] = "9bf534118365f1"
                   return headers
               }

           }
           queue.add(jsonObjectRequest)

       } else{
           val dialog = AlertDialog.Builder(activity as Context)
           dialog.setTitle("Error")
           dialog.setMessage("Internet Connection is not Found")
           dialog.setPositiveButton("Open Settings") { text, listener ->
               val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
               startActivity(intent)
               activity?.finish()
           }
           dialog.setNegativeButton("Exit") { text, listener ->
               ActivityCompat.finishAffinity(activity as Activity)
           }
           dialog.create()
           dialog.show()
       }

        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId
        if (id == R.id.action_sort) {
            Collections.sort(movieInfoList, ratingComparator)
            movieInfoList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()

        return super.onOptionsItemSelected(item)
    }

}