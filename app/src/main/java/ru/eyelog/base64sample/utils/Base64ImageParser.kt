package ru.eyelog.base64sample.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Base64
import android.widget.TextView

class Base64ImageParser(private val textContainer: TextView, private val context: Context) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable? {
        return if (source.matches("data:image.*base64.*".toRegex())) {
            val base64Source = source.replace("data:image.*base64".toRegex(), "")
            val data: ByteArray = Base64.decode(base64Source, Base64.DEFAULT)
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            val image: Drawable = BitmapDrawable(
                context.resources, bitmap
            )
            val textSize = textContainer.textSize
            val coeff = textSize / image.intrinsicHeight
            val iconWidth = image.intrinsicWidth * coeff * SHIFT_15_PERCENT_UP
            val iconHeight = image.intrinsicHeight * coeff
            val topPadding = - image.intrinsicHeight * SHIFT_15_PERCENT_DOWN
            image.setBounds(
                0,
                topPadding.toInt(),
                iconWidth.toInt(),
                iconHeight.toInt()
            )
            image
        } else {
            return null
        }
    }

    companion object {
        const val SHIFT_15_PERCENT_UP = 1.15f
        const val SHIFT_15_PERCENT_DOWN = 0.15f
    }
}