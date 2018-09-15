package sample.huy.gitpullrequestview.UI

import android.text.Html
import android.os.Build
import android.text.Spanned
import android.util.Log



class DiffSupporter {
    companion object {
        fun processPullRequestFileDiff(input:String?):String? {
            var output = ""
            if(input != null) {
                var lineArray: List<String> = input.split("\n")
                for (s in lineArray) {
                    var firstChar = s[0]
                    when (firstChar) {
                        '+' -> output += addColor(s)
                        '-' -> output += removeColor(s)
                        ' ' -> output += noneColor(s)
                        else -> { // Note the block
                            output += noneColor(s)
                        }
                    }
                }
            }
            return output
        }

        fun removeColor(source: String): String {
            return "<font color=#FF0000>" + source + "</font><br/>"
        }
        fun addColor(source: String): String {
            return "<font color=#008000>" + source + "</font><br/>"
        }
        fun noneColor(source: String): String {
            return "<font color=#000000>" + source + "</font><br/>"
        }

        fun fromHtml(source: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(source)
            }
        }
    }

}