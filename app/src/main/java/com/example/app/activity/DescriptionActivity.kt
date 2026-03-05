package com.example.app.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.app.R
import com.example.app.database.MovieDatabase
import com.example.app.database.MovieEntity
import com.example.app.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtMovieName: TextView
    lateinit var txtMovieAuthor: TextView
    lateinit var txtMoviePrice: TextView
    lateinit var txtMovieRating: TextView
    lateinit var imgMovieImage: ImageView
    lateinit var txtMovieDesc: TextView
    lateinit var btnAddToFav: Button
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    lateinit var toolbar: Toolbar

    var movieId: String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_description)

        txtMovieName = findViewById(R.id.txtMovieName)
        txtMovieAuthor = findViewById(R.id.txtMovieAuthor)
        txtMoviePrice = findViewById(R.id.txtMoviePrice)
        txtMovieRating = findViewById(R.id.txtMovieRating)
        imgMovieImage = findViewById(R.id.imgMovieImage)
        txtMovieDesc = findViewById(R.id.txtMovieDesc)
        btnAddToFav = findViewById(R.id.btnAddToFav)
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Movie Details"


        if (intent != null) {
            movieId = intent.getStringExtra("movie_id")
        }else{
            finish()
            Toast.makeText(this@DescriptionActivity, "Some unexpected error occurred!", Toast.LENGTH_SHORT).show()

        }
        if (movieId == "100") {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some unexpected error occurred!", Toast.LENGTH_SHORT).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/Movies/get_movie/"

        val jsonParams = JSONObject()
        jsonParams.put("movie_id", movieId)

        if (ConnectionManager().checkConnectivity(this@DescriptionActivity)) {
            val jsonRequest = object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {

                try {
                    val success = it.getBoolean("success")
                    if (success) {
                        val movieJsonObject = it.getJSONObject("movie_data")
                        progressLayout.visibility = View.GONE

                        val movieImageUrl = movieJsonObject.getString("image")

                        Picasso.get().load(movieJsonObject.getString("image"))
                            .error(R.drawable.default_image).into(imgMovieImage)
                        txtMovieName.text = movieJsonObject.getString("name")
                        txtMovieAuthor.text = movieJsonObject.getString("author")
                        txtMoviePrice.text = movieJsonObject.getString("price")
                        txtMovieRating.text = movieJsonObject.getString("rating")
                        txtMovieDesc.text = movieJsonObject.getString("description")

                        val movieEntity = MovieEntity(
                            movieId?.toInt() as Int,
                            txtMovieName.text.toString(),
                            txtMovieAuthor.text.toString(),
                            txtMoviePrice.text.toString(),
                            txtMovieRating.text.toString(),
                            txtMovieDesc.text.toString(),
                            movieImageUrl
                        )
                        val checkFav = DBAsyncTask(applicationContext, movieEntity, 1).execute()
                        val isFav = checkFav.get()

                        if (isFav) {
                            btnAddToFav.text = "Remove from Favourites"
                            val favColor = ContextCompat.getColor(applicationContext, R.color.colorFavourtie)
                            btnAddToFav.setBackgroundColor(favColor)
                        } else {
                            btnAddToFav.text = "Add to Favourites"
                            val noFavColor = ContextCompat.getColor(applicationContext, R.color.purple_200)
                            btnAddToFav.setBackgroundColor(noFavColor)
                        }
                        btnAddToFav.setOnClickListener {
                            if (!DBAsyncTask(applicationContext, movieEntity, 1).execute().get()) {

                                val async =
                                    DBAsyncTask(applicationContext, movieEntity, 2).execute()
                                val result = async.get()
                                if (result) {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Movie added to Favourites",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    btnAddToFav.text = "Remove from Favourites"
                                    val favColor = ContextCompat.getColor(applicationContext, R.color.colorFavourtie)
                                } else{
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some error occurred!!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                val async = DBAsyncTask(applicationContext, movieEntity, 3).execute()
                                val result = async.get()
                                if (result) {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Movie removed from Favourites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    btnAddToFav.text = "Add to Favourites"
                                    val noFavColor = ContextCompat.getColor(applicationContext, R.color.purple_200)
                                    btnAddToFav.setBackgroundColor(noFavColor)
                                } else{
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some error occurred!!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                    }else {
                        Toast.makeText(this@DescriptionActivity, "Some Error Occurred!!!", Toast.LENGTH_SHORT).show()

                    }
                } catch (e: Exception){
                    Toast.makeText(this@DescriptionActivity, "Some error occurred!!!", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                Toast.makeText(this@DescriptionActivity, "Volley error $it", Toast.LENGTH_SHORT).show()

            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }
            }
            queue.add(jsonRequest)

        } else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }
            dialog.create()
            dialog.show()
        }
    }

    class DBAsyncTask(val context: Context, val movieEntity: MovieEntity, val mode: Int) : AsyncTask<Void, Void, Boolean>(){

        val db = Room.databaseBuilder(context, MovieDatabase::class.java, "movies-db").build()

        override fun doInBackground(vararg params: Void?): Boolean? {
            when(mode) {
                1 -> {
                    val movie: MovieEntity? =
                        db.MovieDao().getMovieById(movieEntity.movie_id.toString())
                    db.close()
                    return movie != null
                }

                2 -> {
                    db.MovieDao().insertMovie(movieEntity)
                    db.close()
                    return true
                }

                3 -> {
                    db.MovieDao().deleteMovie(movieEntity)
                    db.close()
                    return true

                }
            }
            return false
        }
    }
}