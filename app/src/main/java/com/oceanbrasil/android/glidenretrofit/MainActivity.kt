package com.oceanbrasil.android.glidenretrofit

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory







class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Glide
        val imageView = findViewById<View>(R.id.ivAvatar) as ImageView
        Glide.with(this).load("https://super.abril.com.br/wp-content/uploads/2001/06/afinal-o-que-ecc81-amor.jpeg").into(imageView)

        // Retrofit

        val tvErro = findViewById<TextView>(R.id.tvError)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubService::class.java)

        val repos: Call<List<Repo?>?>? = service.listRepos("octocat")

        repos?.enqueue(
            object : Callback<List<Repo?>?> {

                override fun onResponse(
                    call: Call<List<Repo?>?>,
                    response: Response<List<Repo?>?>
                ) {
                    tvErro.setText("O amor está no ar!!")
                    // Exercicio: Exibir a imagem do owner no ivAvatar

                    // Resposta:
                    val body = response.body()
                    body?.get(0).let {
                        Glide.with(this@MainActivity).load(it?.owner?.avatar_url).into(imageView)
                    }
                }

                override fun onFailure(call: Call<List<Repo?>?>, t: Throwable) {
                    tvErro.setText("Deu erro.. =(")
                }

            }
        )
    }
}
