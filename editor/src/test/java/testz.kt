
import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep
import androidx.annotation.Keep

class testz : ArrayList<testzItem>(){
    @Keep
    @Keep
    data class testzItem(
        @SerializedName("foldingStartMarker")
        var foldingStartMarker: String, // b(function|local\s+function|then|do|repeat)\b|{[ \t]*$|\[\[
        @SerializedName("foldingStopMarker")
        var foldingStopMarker: String, // \bend\b|^\s*}|\]\]
        @SerializedName("name")
        var name: String // Lua
    )
}