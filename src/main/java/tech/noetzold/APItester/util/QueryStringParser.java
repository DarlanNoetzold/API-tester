package tech.noetzold.APItester.util;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class QueryStringParser {
    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> parameters = new HashMap<>();

        if (queryString != null && !queryString.isEmpty()) {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                try {
                    String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                    String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                    parameters.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle exception
                }
            }
        }

        return parameters;
    }
}