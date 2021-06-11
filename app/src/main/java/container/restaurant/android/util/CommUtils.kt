package container.restaurant.android.util

import android.content.Context
import android.graphics.Bitmap
import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.io.*

object CommUtils {
    var cookieForm = ""

    fun fromHtml(contents: String): Spanned {
        var contentsText = contents
        if (contentsText.startsWith("<p>")) {
            contentsText = contents.substring(3)
        }
        if (contentsText.endsWith("</p>")) {
            contentsText = contentsText.substring(0, contentsText.length - 4)
        }
        return HtmlCompat.fromHtml(contentsText, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    fun convertBitmapToFile(ctx: Context, fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(ctx.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}