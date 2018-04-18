package example.domain;

public class TelegramBotInfo {
    private final String apiUrl;
    private final String chatId;

    public TelegramBotInfo(String apiUrl, String chatId) {
        this.apiUrl = apiUrl;
        this.chatId = chatId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getChatId() {
        return chatId;
    }

    @Override
    public String toString() {
        return "MonitoringBotInfo{" +
                "apiUrl='" + apiUrl + '\'' +
                ", chatId='" + chatId + '\'' +
                '}';
    }
}

