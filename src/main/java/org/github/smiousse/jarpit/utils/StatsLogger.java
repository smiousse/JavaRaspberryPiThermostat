package org.github.smiousse.jarpit.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class StatsLogger {

    public enum StatsType {
        TEMPERATURE, HUMIDITY, FAN, HEAT_COMPRESSOR, HEAT_ELEMENT, COOL_COMPRESSOR
    };

    private CloseableHttpClient httpClient;
    private String baseUrl;
    private int count = 0;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * @param logFile
     */
    public StatsLogger(String baseUrl) {
        this.baseUrl = baseUrl;
        this.init();
    }

    /**
     * 
     */
    protected void init() {
        if (httpClient == null) {
            HttpClientBuilder builder = HttpClientBuilder.create();
            this.initHttpConfig(builder);
            httpClient = builder.build();
        }
    }

    /**
     * @param builder
     */
    protected void initHttpConfig(HttpClientBuilder builder) {

        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(60000).build();
        builder.setDefaultSocketConfig(socketConfig);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setMaxRedirects(10).setSocketTimeout(60000)
                .setCircularRedirectsAllowed(true).setRedirectsEnabled(true).setRelativeRedirectsAllowed(true).setRedirectsEnabled(true)
                .build();
        builder.setDefaultRequestConfig(requestConfig);

    }

    /**
     * @param statsType
     * @param value
     * @param info
     */
    public void log(StatsType statsType, Object value, String info) {
        this.log(statsType, value, info, null, new Date(System.currentTimeMillis()));
    }

    /**
     * @param statsType
     * @param value
     * @param info
     * @param extras
     */
    public void log(StatsType statsType, Object value, String info, String extras) {
        this.log(statsType, value, extras, null, new Date(System.currentTimeMillis()));
    }

    /**
     * @param statsType
     * @param value
     * @param info
     * @param extras
     * @param date
     */
    public void log(StatsType statsType, Object value, String info, String extras, Date date) {
        if (statsType != null && value != null) {
            Map<String, String> postParams = new HashMap<>();
            postParams.put("value", value.toString());
            postParams.put("info", info);
            postParams.put("extras", extras);

            try {
                this.doPost(this.baseUrl + statsType.toString(), postParams, null);
            }
            catch (Exception e) {
                // TSLT: handle exception
            }
        }
    }

    /**
     * @param serverUrl
     * @param postParams
     * @param queryParams
     * @return
     * @throws Exception
     */
    protected CloseableHttpResponse doPost(String serverUrl, Map<String, String> postParams, Map<String, String> queryParams)
            throws Exception {
        // Build the server URI together with the parameters you wish to pass
        URIBuilder uriBuilder = new URIBuilder(serverUrl);
        addQueryParams(queryParams, uriBuilder);

        HttpPost post = new HttpPost(uriBuilder.build());
        post.addHeader("Accept", "application/json");

        if (postParams != null && postParams.size() > 0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (String paramName : postParams.keySet()) {
                if (postParams.get(paramName) != null && !postParams.get(paramName).isEmpty()) {
                    params.add(new BasicNameValuePair(paramName, postParams.get(paramName)));
                }
            }
            post.setEntity(new UrlEncodedFormEntity(params));
        }

        CloseableHttpResponse response = httpClient.execute(post);

        // System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());

        return response;
    }

    /**
     * @param queryParams
     * @param uriBuilder
     */
    protected void addQueryParams(Map<String, String> queryParams, URIBuilder uriBuilder) {
        if (queryParams != null && queryParams.size() > 0) {
            for (String paramName : queryParams.keySet()) {
                if (queryParams.get(paramName) != null && !queryParams.get(paramName).isEmpty()) {
                    uriBuilder.addParameter(paramName, queryParams.get(paramName));
                }
            }
        }
    }

    /**
     * 
     */
    public void dispose() {
        if (httpClient != null) {
            try {
                httpClient.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
