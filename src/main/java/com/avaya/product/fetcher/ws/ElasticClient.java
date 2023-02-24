package com.avaya.product.fetcher.ws;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class ElasticClient {
    private CredentialsProvider credentialsProvider;
    private static final boolean readonly = !(true);

    private static ElasticClient instance = null;

    protected ElasticClient() {
        credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("elastic", "elasticpw");
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
    }

    public static ElasticClient getInstance() {
        if (instance == null) {
            instance = new ElasticClient();
        }
        return instance;
    }

    public JSONArray GetDocs(String index, String query) {

        JSONArray result = new JSONArray();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider)
                .build();
        String URL = "http://10.133.88.100:9200/" + index + "/_doc/_search";
        HttpPost httpReq = new HttpPost(URL);
        try {
            StringEntity jsonBody = new StringEntity(query);

            httpReq.setHeader("Content-Type", "application/json");
            httpReq.setEntity(jsonBody);



            // Execute HTTP request
            HttpResponse httpResponse = httpClient.execute(httpReq);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
            } else {
            }

            // Get hold of the response entity
            HttpEntity entity = httpResponse.getEntity();

            JSONObject obj = new JSONObject(new JSONTokener(entity.getContent()));
            result = obj.getJSONObject("hits").getJSONArray("hits");

        } catch (RuntimeException runtimeException) {
            // In case of an unexpected exception you may want to abort
            // the HTTP request in order to shut down the underlying
            // connection immediately.
            httpReq.abort();
            runtimeException.printStackTrace();
        } catch (ClientProtocolException e) {
            // thrown by httpClient.execute(httpGetRequest)
            e.printStackTrace();
        } catch (IOException e) {
            // thrown by entity.getContent();
            e.printStackTrace();
        }
        return result;
    }
    public JSONObject GetElasticRecordsSQL(String query) {

        JSONObject result = new JSONObject();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider)
                .build();
        String URL = "http://10.133.88.100:9200/_sql?format=json";
        HttpPost httpReq = new HttpPost(URL);
        try {
            StringEntity jsonBody = new StringEntity("{\"query\":\""+query+"\"}", ContentType.APPLICATION_JSON);
            System.out.println(jsonBody);
            httpReq.setHeader("Content-Type", "application/json");
            httpReq.setEntity(jsonBody);

            // Execute HTTP request
            HttpResponse httpResponse = httpClient.execute(httpReq);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
            } else {
            }

            // Get hold of the response entity
            HttpEntity entity = httpResponse.getEntity();

            result = new JSONObject(new JSONTokener(entity.getContent()));
            System.out.println(result);
//			result = obj.getJSONObject("hits").getJSONArray("hits");

        } catch (RuntimeException runtimeException) {
            // In case of an unexpected exception you may want to abort
            // the HTTP request in order to shut down the underlying
            // connection immediately.
            httpReq.abort();
            runtimeException.printStackTrace();
        } catch (ClientProtocolException e) {
            // thrown by httpClient.execute(httpGetRequest)
            e.printStackTrace();
        } catch (IOException e) {
            // thrown by entity.getContent();
            e.printStackTrace();
        }
        return result;
    }

}
