package ru.gb.pictureoftheday.api

import com.google.gson.annotations.SerializedName
data class EarthNasaResponse(
    val photoList:List<EarthPhoto>
)
data class EarthPhoto(
    @SerializedName("identifier")
    val identifier:String,
    @SerializedName("caption")
    val caption:String,
    @SerializedName("image")
    val image:String,
    @SerializedName("version")
    val version:String,
    @SerializedName("centroid_coordinates")
    val centroidCoordinates:Coordinates,
    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position:Position,
    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position:Position,
    @SerializedName("sun_j2000_position")
    val sunJ2000Position:Position,
    @SerializedName("attitude_quaternions")
    val attitudeQuaternions:Quaternions,
    @SerializedName("date")
    val date:String,
    @SerializedName("coords")
    val coords:Coords,
)
data class Position(
    @SerializedName("x")
    val x:Float,
    @SerializedName("y")
    val y:Float,
    @SerializedName("z")
    val z:Float
)
data class Coordinates(
    @SerializedName("lat")
    val lat:Float,
    @SerializedName("lon")
    val lon:Float
)
data class Quaternions(
    @SerializedName("q0")
    val q0:Float,
    @SerializedName("q1")
    val q1:Float,
    @SerializedName("q2")
    val q2:Float,
    @SerializedName("q3")
    val q3:Float
)
data class Coords(
    @SerializedName("centroid_coordinates")
    val centroidCoordinates :Coordinates,
    @SerializedName("dscovr_j2000_position")
    val dscovrJ2000Position:Position,
    @SerializedName("lunar_j2000_position")
    val lunarJ2000Position:Position,
    @SerializedName("sun_j2000_position")
    val sunJ2000Position:Position,
    @SerializedName("attitude_quaternions")
    val attitudeQuaternions:Quaternions
)
