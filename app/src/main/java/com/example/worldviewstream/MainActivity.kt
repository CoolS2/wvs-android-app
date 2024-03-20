package com.example.worldviewstream

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private lateinit var startProcessButton: Button
    private lateinit var videoOrMapContainer: FrameLayout
    private lateinit var loadingProgressBar: ProgressBar

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://worldviewstream.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация view
        startProcessButton = findViewById(R.id.startProcessButton)
        videoOrMapContainer = findViewById(R.id.videoOrMapContainer)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        // Установка слушателя кликов для кнопки
        startProcessButton.setOnClickListener {
            startProcess()
        }
    }

    private fun startProcess() {
        // Показать прогресс бар и скрыть контейнер
        loadingProgressBar.visibility = ProgressBar.VISIBLE
        videoOrMapContainer.visibility = FrameLayout.GONE

        // Здесь вы можете выполнить запрос к вашему API для получения данных
        var currentCameraData = getCamData();
        //mapFlyProcess()

        if (currentCameraData != null) {
            when (currentCameraData.camera.type) {
                "youtube" -> youtubeProcess(currentCameraData.camera)
                "stream" -> streamProcess(currentCameraData.camera)
                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }
        }
        // После получения данных, обработайте их и отобразите видео или карту
        // Загрузите видео или карту в videoOrMapContainer
        // После завершения загрузки скройте прогресс бар и покажите контейнер
    }

    private fun getCamData(): CamModel? {
        var data: CamModel? = null;

        apiService.getData().enqueue(object : Callback<CamModel> {
            override fun onResponse(
                call: Call<CamModel>,
                response: Response<CamModel>
            ) {
                if (response.isSuccessful) {
                    println("success")
                    // Обработка успешного ответа и отображение данных
                    data = response.body()
                    println(data)

                    if (data?.camera === null) {
                        errorProcess()

                        return
                    }

                } else {
                    println("NOT success")
                    errorProcess();
                }

                loadingProgressBar.visibility = ProgressBar.GONE
                videoOrMapContainer.visibility = FrameLayout.VISIBLE
            }

            override fun onFailure(call: Call<CamModel>, t: Throwable) {
                // Обработка ошибки
                loadingProgressBar.visibility = ProgressBar.GONE
                videoOrMapContainer.visibility = FrameLayout.VISIBLE
            }
        })

        return data
    }

    private fun youtubeProcess(data: Camera) {
        print("x == youtube")

        val youTubePlayerView = YouTubePlayerView(this)
        videoOrMapContainer.addView(youTubePlayerView)
    }

    private fun streamProcess(data: Camera) {
        print("x == stream")
        // Предположим, что ваши данные содержат ссылку на видео
        val videoUrl = data.url // Предположим, что data - это ваш объект с данными

        val playerView = PlayerView(this)
        val player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player

        val mediaItem = MediaItem.fromUri(videoUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        // Добавляем playerView в ваш контейнер
        videoOrMapContainer.addView(playerView)
    }

    private fun errorProcess() {
        // TODO errror process
    }

    private fun mapFlyProcess() {

    }
}