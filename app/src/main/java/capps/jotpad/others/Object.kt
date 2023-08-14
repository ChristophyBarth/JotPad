package capps.jotpad.others

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.Random

object Object {

    fun isColorBright(hexColor: String) : Boolean {
        val backgroundColor = Color.parseColor(hexColor)
        val luminance = (backgroundColor.red * 0.299 + backgroundColor.green * 0.587 + backgroundColor.blue * 0.114)

//        return luminance > 186
        return luminance > 150
    }

    fun getRandomColorCode(): String {
        val random = Random()
        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)

        return String.format("#%02X%02X%02X", red, green, blue)
    }

    fun textViewDrawableColor(textView: TextView, drawablePosition:Int, color: Int){
        val drawables = textView.compoundDrawablesRelative
        val drawable = drawables[drawablePosition].mutate()
        drawable.setTint(color)

        when(drawablePosition){
            0 -> textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawable,
                drawables[1], drawables[2], drawables[3]
            )
            1 -> textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawables[0],
                drawable, drawables[2], drawables[3]
            )
            2 -> textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawables[0],
                drawables[1], drawable, drawables[3]
            )
            3 -> textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawables[0],
                drawables[1], drawables[2], drawable
            )
        }
    }

    fun changeDrawableTintColor(context: Context, drawable: Int, newColor: Int, view: View){
        var shape = ContextCompat.getDrawable(context, drawable)

        shape = shape!!.mutate()
        DrawableCompat.setTint(shape, ContextCompat.getColor(context, newColor))
        view.background = shape
    }

    class MyGsonUtil {
        private var gson : Gson? = null
        fun getGsonParser(): Gson {
            if (gson == null) {
                val builder = GsonBuilder()
                gson = builder.create()
            }

            return gson!!
        }

    }
}