package sample.huy.gitpullrequestview.UI

import android.text.Html
import android.os.Build
import android.text.Spanned
import sample.huy.gitpullrequestview.Entity.PullRequestFile
import sample.huy.gitpullrequestview.UI.diff_match_patch.Operation as Operation

class DiffSupporter {
    companion object {
        fun processPullRequestFileDiff(file: PullRequestFile): PullRequestFile {
            var input = file.differences
            var diff = ""
            var origin = ""
            var new = ""
            var originForProcess = ""
            var newForProcess = ""
            if (input != null && input.isNotEmpty()) {
                var lineArray: List<String> = input.split("\n")
                for (s in lineArray) {
                    var firstChar = s[0]
                    var newString = s.drop(1)
                    when (firstChar) {
                        '+' -> {
                            //diff += addColor(s)
                            origin += newLine()
                            new += addColor(newString)
                            //
                            originForProcess += newLine()
                            newForProcess += newString + newLine()
                        }
                        '-' -> {
                            //diff += removeColor(s)
                            origin += removeColor(newString)
                            new += newLine()
                            //
                            originForProcess += newString + newLine()
                            newForProcess += newLine()
                        }
                        ' ' -> {
                            //diff += noneColor(s)
                            origin += noneColor(newString)
                            new += noneColor(newString)
                            //
                            originForProcess += newLine()
                            newForProcess += newLine()

                        }
                        else -> {
                            //diff += noneColor(s)
                        }
                    }
                }
            }
            file.originContent = origin
            file.newContent = new

            //new try
            var diffFactory = diff_match_patch()
            var diffList = diffFactory.diff_main(originForProcess, newForProcess)
            for (d in diffList) {
                when (d.operation) {
                    //add
                    Operation.INSERT -> {
                        diff += addColor(d.text, false)
                    }
                    Operation.DELETE -> {
                        diff += removeColor(d.text, false)
                    }
                    Operation.EQUAL -> {
                        diff += noneColor(d.text, false)
                    }
                }
            }
            file.differences = diff
            return file
        }

        private fun newLine(): String {
            return "<br/>"
        }

        private fun removeColor(source: String, needNewLine: Boolean = true): String {
            return if (needNewLine) "<font color=#FF0000>" + source + "</font><br/>" else "<font color=#FF0000>" + source + "</font>"
        }

        private fun addColor(source: String, needNewLine: Boolean = true): String {
            return if (needNewLine) "<font color=#008000>" + source + "</font><br/>" else "<font color=#008000>" + source + "</font>"
        }

        private fun noneColor(source: String, needNewLine: Boolean = true): String {
            return if (needNewLine) "<font color=#000000>" + source + "</font><br/>" else "<font color=#000000>" + source + "</font>"
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