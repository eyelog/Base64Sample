package ru.eyelog.base64sample.utils

import android.text.Editable
import android.text.Html.TagHandler
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import org.xml.sax.XMLReader

class HtmlTagHandler : TagHandler {
    override fun handleTag(
        opening: Boolean,
        tag: String,
        output: Editable,
        xmlReader: XMLReader?
    ) {
        if (tag.equals("strike", ignoreCase = true) || tag == "s") {
            processStrike(opening, output)
        }
    }

    private fun processStrike(opening: Boolean, output: Editable) {
        val len: Int = output.length
        if (opening) {
            output.setSpan(StrikethroughSpan(), len, len, Spannable.SPAN_MARK_MARK)
        } else {
            val obj = getLast(output, Spanned::class.java)
            val where: Int = output.getSpanStart(obj)
            output.removeSpan(obj)
            if (where != len) {
                output.setSpan(StrikethroughSpan(), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private fun getLast(text: Editable, kind: Class<Spanned>): Spanned? {
        val objs: Array<Spanned> = text.getSpans(0, text.length, kind)
        return if (objs.isEmpty()) {
            null
        } else {
            for (i in objs.size downTo 1) {
                if (text.getSpanFlags(objs[i - 1]) === Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1]
                }
            }
            null
        }
    }
}
