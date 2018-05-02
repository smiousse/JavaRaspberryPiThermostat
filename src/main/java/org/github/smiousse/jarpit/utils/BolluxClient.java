package org.github.smiousse.jarpit.utils;

import java.util.ArrayList;
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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.github.smiousse.jarpit.model.JarpitStatus;
import org.github.smiousse.jarpit.utils.StatsLogger.StatsType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BolluxClient {

    private String baseUrl;

    /**
     * @param logFile
     */
    public BolluxClient(String baseUrl) {
        this.baseUrl = baseUrl;

    }

    /**
     * @param statsType
     * @param value
     * @param info
     * @param extras
     * @param date
     */
    public void log(StatsType statsType, Object value, String deviceIdentifier, String extras, String date) {
        if (statsType != null && value != null) {

            Map<String, String> postParams = new HashMap<>();
            postParams.put("value", value.toString());
            postParams.put("deviceIdentifier", deviceIdentifier);
            postParams.put("extras", extras);
            postParams.put("date", date);

            try {
                this.doPost(this.baseUrl + "/rest/stats/add/" + statsType.toString(), postParams, null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param jarpitStatus
     */
    public void updateJarpitStatus(JarpitStatus jarpitStatus) {
        if (jarpitStatus != null) {

            try {
                this.doPostWithBody(this.baseUrl + "/rest/settings/update/status/jarpit", null,
                        new String(new ObjectMapper().writeValueAsBytes(jarpitStatus)));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     */
    private CloseableHttpClient getHttpClient() {

        HttpClientBuilder builder = HttpClientBuilder.create();
        this.initHttpConfig(builder);
        return builder.build();

    }

    /**
     * @param builder
     */
    private void initHttpConfig(HttpClientBuilder builder) {

        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(60000).build();
        builder.setDefaultSocketConfig(socketConfig);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setMaxRedirects(10).setSocketTimeout(60000)
                .setCircularRedirectsAllowed(true).setRedirectsEnabled(true).setRelativeRedirectsAllowed(true).setRedirectsEnabled(true)
                .build();
        builder.setDefaultRequestConfig(requestConfig);

    }

    /**
     * @param serverUrl
     * @param postParams
     * @param queryParams
     * @return
     * @throws Exception
     */
    private CloseableHttpResponse doPostWithBody(String serverUrl, Map<String, String> queryParams, String body) throws Exception {

        CloseableHttpClient httpClient = null;
        try {
            // Build the server URI together with the parameters you wish to pass
            URIBuilder uriBuilder = new URIBuilder(serverUrl);
            addQueryParams(queryParams, uriBuilder);

            HttpPost post = new HttpPost(uriBuilder.build());
            post.addHeader("Accept", "application/json");

            if (body != null && !body.isEmpty()) {
                post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
            }

            httpClient = getHttpClient();

            CloseableHttpResponse response = httpClient.execute(post);

            // System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());

            return response;
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                }
                catch (Exception e) {
                    // TSLT: handle exception
                }
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
    private CloseableHttpResponse doPost(String serverUrl, Map<String, String> postParams, Map<String, String> queryParams)
            throws Exception {

        CloseableHttpClient httpClient = null;
        try {
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

            httpClient = getHttpClient();

            CloseableHttpResponse response = httpClient.execute(post);

            // System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());

            return response;
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                }
                catch (Exception e) {
                    // TSLT: handle exception
                }
            }
        }
    }

    /**
     * @param queryParams
     * @param uriBuilder
     */
    private void addQueryParams(Map<String, String> queryParams, URIBuilder uriBuilder) {
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
    }

}
