package example.util;

import example.domain.TelegramBotInfo;

import java.util.HashMap;
import java.util.Map;

public class AlarmUtil {
    public static HttpResponseData alertTelegram(TelegramBotInfo telegramBotInfo, String message) throws Exception {
        Map<String, Object> mapOfParameter = new HashMap<>();
        mapOfParameter.put("text", message);
        mapOfParameter.put("chat_id", telegramBotInfo.getChatId());
        return HttpUtil.requestPostWithMap(telegramBotInfo.getApiUrl(), mapOfParameter);
    }
}
