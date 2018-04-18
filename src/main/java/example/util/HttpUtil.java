package example.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtil.class);

    private static PoolingHttpClientConnectionManager connectionManager;
    private static HttpClientBuilder httpClientBuilder;

    static {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory(SSLContexts.createDefault(), NoopHostnameVerifier.INSTANCE))
                .build();

        HttpUtil.connectionManager = new PoolingHttpClientConnectionManager(registry);
        try {
            logger.info("load http config success for http util");
            HttpUtil.connectionManager.setMaxTotal(200);
            HttpUtil.connectionManager.setDefaultMaxPerRoute(20);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(20000)
                    .setSocketTimeout(30000)
                    .setConnectTimeout(3000)
                    .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                    .build();
            SocketConfig socketConfig = SocketConfig.custom()
                    .setSoKeepAlive(false)
                    .build();
//            HttpRetryHandler retryHandler = new HttpRetryHandler(3;
            HttpUtil.httpClientBuilder = HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig)
                    .setDefaultSocketConfig(socketConfig)
                    .setConnectionManager(HttpUtil.connectionManager);
//                    .setRetryHandler(retryHandler);
            logger.info("http util initialize success");
        } catch(Exception e) {
            logger.error("http util initialize fail", e);
        }
    }

    private static HttpUtil ourInstance;

    public synchronized static HttpUtil getInstance() {
        if(ourInstance == null) {
            ourInstance = new HttpUtil();
        }
        return ourInstance;
    }

    public static HttpResponseData requestPostWithMap(String apiUrl, Map<String, Object> mapOfParameter) throws Exception {
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        for (String key : mapOfParameter.keySet()) {
            nameValuePairList.add(new BasicNameValuePair(key, mapOfParameter.get(key).toString()));
        }
        return requestPostWithNameValuePairList(apiUrl, nameValuePairList);
    }

    private static HttpResponseData requestPostWithNameValuePairList(String apiUrl, List<NameValuePair> nameValuePairList) throws Exception {
        HttpEntity requestEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");

        return requestPost(apiUrl, requestEntity);
    }

//    public static HttpResponseData requestPostWithMultipartProtocol(HttpProtocolInfo httpProtocolInfo, String data) throws Exception {
//        String tmpFileName = "tmp_" + Thread.currentThread().getId() + "_" + System.currentTimeMillis();
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
//                .setCharset(Charset.forName("UTF-8"))
//                .addBinaryBody(httpProtocolInfo.getDataParameterName(), data.getBytes(), ContentType.create("multipart/form-data", Charset.forName("UTF-8")), tmpFileName);
//        HttpEntity multipartEntity = builder.build();
//
//        return requestPost(httpProtocolInfo, multipartEntity);
//    }

    private static HttpResponseData requestPost(String apiUrl, HttpEntity requestEntity) throws Exception {
        int statusCode = Integer.MIN_VALUE;
        String responseBody = null;
        HttpPost httpPost = null;
        HttpEntity httpEntity = null;
        try {
            CloseableHttpClient httpClient  = httpClientBuilder.build();
            httpPost = new HttpPost(apiUrl);
            httpPost.setEntity(requestEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            statusCode = httpResponse.getStatusLine().getStatusCode();
            if(httpEntity != null) {
                responseBody = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
        } finally {
            EntityUtils.consumeQuietly(httpEntity);
            if(httpPost != null) httpPost.releaseConnection();
        }
        return new HttpResponseData(responseBody, statusCode);
    }

    public void closeConnection() {
        HttpUtil.connectionManager.closeExpiredConnections();
        HttpUtil.connectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
    }

    public PoolingHttpClientConnectionManager getHttpConnManager() {
        return HttpUtil.connectionManager;
    }

    public void setHttpConnManager(PoolingHttpClientConnectionManager connectionManager) {
        HttpUtil.connectionManager = connectionManager;
    }
}
