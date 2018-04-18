package example.util;

public class HttpResponseData {
    private final String body;
    private final int code;

    public HttpResponseData(String body, int code) {
        this.body = body;
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "HttpResponseData{" +
                "body='" + body + '\'' +
                ", code=" + code +
                '}';
    }
}
