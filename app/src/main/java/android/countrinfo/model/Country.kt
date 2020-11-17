package android.countrinfo.model

import com.google.gson.annotations.SerializedName

data class Base (

    @SerializedName("name") val name : String,
    @SerializedName("topLevelDomain") val topLevelDomain : List<String>,
    @SerializedName("alpha2Code") val alpha2Code : String,
    @SerializedName("alpha3Code") val alpha3Code : String,
   // @SerializedName("callingCodes") val callingCodes : List<Int>
    @SerializedName("capital") val capital : String,
    @SerializedName("altSpellings") val altSpellings : List<String>,
    @SerializedName("region") val region : String,
    @SerializedName("subregion") val subregion : String,
    @SerializedName("population") val population : Int,
    @SerializedName("latlng") val latlng : List<Double>,
    @SerializedName("demonym") val demonym : String,
    @SerializedName("area") val area : Double,
    @SerializedName("gini") val gini : Double,
    @SerializedName("timezones") val timezones : List<String>,
    @SerializedName("borders") val borders : List<String>,
    @SerializedName("nativeName") val nativeName : String,
    @SerializedName("numericCode") val numericCode : Int,
    @SerializedName("currencies") val currencies : List<Currencies>,
    @SerializedName("languages") val languages : List<Languages>,
    @SerializedName("translations") val translations : Translations,
    @SerializedName("flag") val flag : String,
    @SerializedName("regionalBlocs") val regionalBlocs : List<RegionalBlocs>,
    @SerializedName("cioc") val cioc : String
)
data class Translations (

    @SerializedName("de") val de : String,
    @SerializedName("es") val es : String,
    @SerializedName("fr") val fr : String,
    @SerializedName("ja") val ja : String,
    @SerializedName("it") val it : String,
    @SerializedName("br") val br : String,
    @SerializedName("pt") val pt : String,
    @SerializedName("nl") val nl : String,
    @SerializedName("hr") val hr : String,
    @SerializedName("fa") val fa : String
)
data class RegionalBlocs (

    @SerializedName("acronym") val acronym : String,
    @SerializedName("name") val name : String,
    @SerializedName("otherAcronyms") val otherAcronyms : List<String>,
    @SerializedName("otherNames") val otherNames : List<String>
)
data class Languages (

    @SerializedName("iso639_1") val iso639_1 : String,
    @SerializedName("iso639_2") val iso639_2 : String,
    @SerializedName("name") val name : String,
    @SerializedName("nativeName") val nativeName : String
)
data class Currencies (

    @SerializedName("code") val code : String,
    @SerializedName("name") val name : String,
    @SerializedName("symbol") val symbol : String
)