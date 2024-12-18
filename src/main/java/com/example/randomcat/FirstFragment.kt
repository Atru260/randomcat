package com.example.randomcat


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.randomcat.databinding.FragmentFirstBinding
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val client = OkHttpClient()

    fun displayRandomCat() {
        val request = Request.Builder()
            .url("https://api.thecatapi.com/v1/images/search")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = changeCat( JSONArray( response.body()
                ?.string()).get(0) as JSONObject)
        })
    }

    fun printCat(cat:JSONObject){
        println(cat)
    }
    fun changeCat(cat:JSONObject){
        val iv = view?.findViewById<ImageView>(R.id.cat)
        val url = cat.getString("url")
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post {
            Picasso.get()
                .load(url)
                .into(iv)
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayRandomCat()


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            displayRandomCat()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}