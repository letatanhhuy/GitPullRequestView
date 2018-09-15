package sample.huy.gitpullrequestview.UI

import android.text.Html
import android.os.Build
import android.text.Spanned
import sample.huy.gitpullrequestview.Entity.PullRequestFile

class DiffSupporter {
    companion object {
        fun processPullRequestFileDiff(file:PullRequestFile):PullRequestFile {
            var input = file.differences
            var diff = ""
            var origin = ""
            var new = ""
            if(input != null && input.isNotEmpty()) {
                var lineArray: List<String> = input.split("\n")
                for (s in lineArray) {
                    var firstChar = s[0]
                    var newString = s.drop(1)
                    when (firstChar) {
                        '+' -> {
                            diff += addColor(s)
                            origin += newLine()
                            new += addColor(newString)
                        }
                        '-' -> {
                            diff += removeColor(s)
                            origin += removeColor(newString)
                            new += newLine()
                        }
                        ' ' -> {
                            diff += noneColor(s)
                            origin += noneColor(newString)
                            new += noneColor(newString)
                        }
                        else -> { // Note the block
                            diff += noneColor(s)
//                            origin += noneColor(newString)
//                            new += noneColor(newString)
                        }
                    }
                }
            }
            file.differences = diff
            file.originContent = origin
            file.newContent = new
            return file
        }
        fun newLine(): String {
            return "<br/>"
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