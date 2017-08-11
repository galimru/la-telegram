package org.lizaalert.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForumUtils {

    private static final Pattern INACTIVE_PATTERN
            = Pattern.compile("найден[\\s\\.]|жив[\\s\\.]|жива[\\s\\.]|живы[\\s\\.]|погиб[\\s\\.]|" +
            "погибла[\\s\\.]|погибли[\\s\\.]", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern TOPIC_ID_PATTERN = Pattern.compile("t=(\\d+)");

    public static Integer getTopicId(String text) {
        Matcher matcher = TOPIC_ID_PATTERN.matcher(text);
        String topicId = null;
        while (matcher.find()) {
            topicId = matcher.group(1);
        }
        return topicId != null ? Integer.valueOf(topicId) : null;
    }

    public static boolean isActive(String text) {
        Matcher matcher = INACTIVE_PATTERN.matcher(text);
        return !matcher.find();
    }


}
