package com.example.worldviewstream

import retrofit2.Call
import retrofit2.http.GET
interface ApiService {
    @GET("/wp-admin/admin-ajax.php?action=get_app_random_cam")
    fun getData(): Call<CamModel>
}