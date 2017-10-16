package com.java.api.apache.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

/**
 * POINTS:
 * 1. basic http actions: get/post/put/delete
 * 2. add proxy
 * 3. *ssl
 *
 * Created by kevintian on 2017/9/29.
 */
public class HttpClientDemo {
    /**
     * 发送 get请求
     */
    public void get(String uri) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(uri);
            System.out.println("executing GET request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length:\n" + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content:\n" + EntityUtils.toString(entity, "utf-8"));
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getWithProxy(String uri, Proxy proxyParams) {
        // set proxy
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        if (proxyParams.getUserName() != null && proxyParams.getPassword() != null) {
            credsProvider.setCredentials(
                    new AuthScope(proxyParams.hostName, proxyParams.port),
                    new UsernamePasswordCredentials(proxyParams.userName, proxyParams.password));
        }
        HttpHost proxy = new HttpHost(proxyParams.hostName, proxyParams.port);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        HttpGet request = new HttpGet(uri);
        request.setConfig(config);
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build()) {
            System.out.println("Executing request " + request.getRequestLine());
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public void post(String uri, Map<String, String> headers, Map<String, Object> body) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(uri);
        // add headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httppost.setHeader(entry.getKey(), entry.getValue());
        }
        // set body
        try {
            StringEntity json = new StringEntity(new ObjectMapper().writeValueAsString(body), "utf-8");
            httppost.setEntity(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("POST request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void postWithProxy(String uri, Map<String, String> headers, Map<String, Object> body, Proxy proxyParams) {
        HttpPost httppost = new HttpPost(uri);
        // add headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httppost.setHeader(entry.getKey(), entry.getValue());
        }
        // set body
        try {
            StringEntity json = new StringEntity(new ObjectMapper().writeValueAsString(body), "utf-8");
            httppost.setEntity(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // set proxy
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        if (proxyParams.getUserName() != null && proxyParams.getPassword() != null) {
            credsProvider.setCredentials(
                    new AuthScope(proxyParams.hostName, proxyParams.port),
                    new UsernamePasswordCredentials(proxyParams.userName, proxyParams.password));
        }
        HttpHost proxy = new HttpHost(proxyParams.hostName, proxyParams.port);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httppost.setConfig(config);
        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build()) {
            System.out.println("POST request " + httppost.getURI());
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO: HttpClient连接SSL
     */
    public void ssl() {
        CloseableHttpClient httpclient = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
            try {
                // 加载keyStore d:\\tomcat.keystore
                trustStore.load(instream, "123456".toCharArray());
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (Exception ignore) {
                }
            }
            // 相信自己的CA和所有自签名的证书
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            // 只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            // 创建http请求(get方式)
            HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");
            System.out.println("executing request" + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    System.out.println(EntityUtils.toString(entity));
                    EntityUtils.consume(entity);
                }
            } finally {
                response.close();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Proxy {
        String hostName;
        Integer port;
        String userName;
        String password;

        public Proxy(String hostName, Integer port) {
            this.hostName = hostName;
            this.port = port;
        }

        public Proxy(String hostName, Integer port, String userName, String password) {
            this.hostName = hostName;
            this.port = port;
            this.userName = userName;
            this.password = password;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
