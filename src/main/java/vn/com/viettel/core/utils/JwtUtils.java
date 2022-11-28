package vn.com.viettel.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.StringReader;

public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Get data user from Jwt
     *
     * @param jwtToken
     * @return
     */
    public static Object getDataFromJwt(String jwtToken, Class<?> JwtObject) {
        try {
            String[] split_string = jwtToken.split("\\.");
            if (split_string.length == 0) {
                return null;
            }
            String base64EncodedBody = split_string[1];
            Base64 base64Url = new Base64(true);
            String body = new String(base64Url.decode(base64EncodedBody));
            return (Object) convertJsonToObject(body, JwtObject);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param strJsonData
     * @param classOfT
     * @return
     */
    public static Object convertJsonToObject(String strJsonData, Class<?> classOfT) {
        Object result = null;
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonReader reader = new JsonReader(new StringReader(strJsonData));
            reader.setLenient(true);
            result = gson.fromJson(reader, classOfT);
        } catch (JsonIOException | JsonSyntaxException e) {
            LOGGER.error("Loi convertJsonToObject:", e);
        }
        return result;
    }
}
