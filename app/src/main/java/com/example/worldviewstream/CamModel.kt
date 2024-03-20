package com.example.worldviewstream

data class CamModel (
    val camera: Camera,
    val city: City,
    val country: Country,
    val image: String,
    val latitude: String,
    val longitude: String,
    val link: String,
    val postId: Number,
    val title: String,
)

data class Camera(
    val type: String,
    val url: String
)

data class City(
    val name: String
)

data class Country(
    val image: String,
    val name: String
)

data class Weather(
    val city: WeatherCity,
    val cod: String,
    val currentTime: String,
    val date: String,
    val icon: String,
    val list: ArrayList<WeatherList>,
    val message: Number,
)

data class WeatherCity(
    val coord: WeatherCityCoord,
    val country: Number,
    val id: Number,
    val name: String,
    val population: Number,
    val sunrise: Number,
    val sunset: Number,
    val timezone: Number,
)

data class WeatherCityCoord(
    val lat: Number,
    val lon: Number,
)

data class WeatherList(
    val clouds: WeatherListClouds,
    val dt: Number,
    val dtText: String,
    val main: WeatherListMain,
    val pop: Number,
    val sys: WeatherListSys,
    val visibility: Number,
    val weather: ArrayList<WeatherListWeatherData>,
    val wind: WeatherListWind,
)

data class WeatherListClouds(
    val all: Number,
)

data class WeatherListMain(
    val feelsLike: Number,
    val grndLevel: Number,
    val humidity: Number,
    val pressure: Number,
    val seaLevel: Number,
    val temp: Number,
    val tempKf: Number,
    val tempMax: Number,
    val tempMin: Number,
)

data class WeatherListSys(
    val pod: String,
)

data class WeatherListWeatherData(
    val description: String,
    val icon: String,
    val id: Number,
    val main: String,
)

data class WeatherListWind(
    val deg: String,
    val gust: String,
    val speed: Number,
)