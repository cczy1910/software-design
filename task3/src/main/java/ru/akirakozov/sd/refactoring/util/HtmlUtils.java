package ru.akirakozov.sd.refactoring.util;

public class HtmlUtils {
    public static String wrapHtml(String s) {
        return new StringBuilder()
                .append("<html>")
                .append("<body>")
                .append(s)
                .append("</body>")
                .append("</html>")
                .toString();
    }
}
