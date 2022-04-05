package com.planradar.cities.model


data class weatherResponse (
    var weather: List<Weather>? = null,
    val main: Main,
    val  wind: Wind,
    val cod: Int,
    val name:String
    )

class Weather{
    var main: String? = null
    var description: String? = null
    var icon: String? = null
}
class Main{
    var temp: Double = 0.0
    var humidity: Double = 0.0
}
class Wind{
    var speed: Double = 0.0
}

