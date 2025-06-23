package com.hxl.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP工具类
 * 基于Apache HttpClient封装的HTTP请求工具类，提供GET/POST请求发送功能
 * 支持表单参数和JSON参数格式，包含请求超时配置和资源自动释放功能
 */
public class HttpClientUtil {

    /** 请求超时时间（毫秒），统一设置为20秒 */
    static final int TIMEOUT_MSEC = 20 * 1000;

    /**
     * 发送GET方式请求
     * <p>将请求参数拼接至URL后发送GET请求，适用于获取资源场景</p>
     * @param url 请求URL地址
     * @param paramMap 请求参数键值对，可为null
     * @return 响应内容字符串，请求失败或异常时返回空字符串
     */
    public static String doGet(String url, Map<String, String> paramMap) {
        // 创建HTTP客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        CloseableHttpResponse response = null;

        try {
            // 构建URI对象，支持参数拼接
            URIBuilder builder = new URIBuilder(url);
            if (paramMap != null) {
                // 遍历参数Map并添加到URI查询字符串中
                for (String key : paramMap.keySet()) {
                    builder.addParameter(key, paramMap.get(key));
                }
            }
            URI uri = builder.build();

            // 创建GET请求对象
            HttpGet httpGet = new HttpGet(uri);

            // 执行HTTP请求并获取响应
            response = httpClient.execute(httpGet);

            // 处理响应结果，仅当状态码为200时解析响应体
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            // 捕获所有异常并打印堆栈信息，保证程序不崩溃
            e.printStackTrace();
        } finally {
            // 最终释放资源，关闭响应和客户端连接
            try {
                if (response != null) response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 发送POST方式请求（表单参数格式）
     * <p>将请求参数封装为表单格式（application/x-www-form-urlencoded）发送</p>
     * @param url 请求URL地址
     * @param paramMap 请求参数键值对，可为null
     * @return 响应内容字符串
     * @throws IOException 网络异常或资源关闭异常时抛出
     */
    public static String doPost(String url, Map<String, String> paramMap) throws IOException {
        // 创建HTTP客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";

        try {
            // 创建POST请求对象
            HttpPost httpPost = new HttpPost(url);

            // 处理请求参数，转换为表单参数列表
            if (paramMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
                // 封装为表单实体并设置到请求中
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }

            // 设置请求超时配置
            httpPost.setConfig(builderRequestConfig());

            // 执行HTTP请求并获取响应
            response = httpClient.execute(httpPost);

            // 解析响应体内容
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            // 抛出原始异常，由调用者处理
            throw e;
        } finally {
            // 释放响应资源
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * 发送POST方式请求（JSON参数格式）
     * <p>将请求参数封装为JSON格式（application/json）发送</p>
     * @param url 请求URL地址
     * @param paramMap 请求参数键值对，可为null
     * @return 响应内容字符串
     * @throws IOException 网络异常或资源关闭异常时抛出
     */
    public static String doPost4Json(String url, Map<String, String> paramMap) throws IOException {
        // 创建HTTP客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";

        try {
            // 创建POST请求对象
            HttpPost httpPost = new HttpPost(url);

            // 处理请求参数，转换为JSON格式
            if (paramMap != null) {
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    jsonObject.put(param.getKey(), param.getValue());
                }
                // 封装为JSON实体并设置请求头
                StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
                entity.setContentEncoding("utf-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }

            // 设置请求超时配置
            httpPost.setConfig(builderRequestConfig());

            // 执行HTTP请求并获取响应
            response = httpClient.execute(httpPost);

            // 解析响应体内容
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            // 抛出原始异常，由调用者处理
            throw e;
        } finally {
            // 释放响应资源
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * 构建请求超时配置
     * <p>统一设置连接超时、请求获取超时和套接字超时为相同值</p>
     * @return RequestConfig配置对象
     */
    private static RequestConfig builderRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_MSEC)       // 连接建立超时时间
                .setConnectionRequestTimeout(TIMEOUT_MSEC) // 从连接池获取连接的超时时间
                .setSocketTimeout(TIMEOUT_MSEC)          // 数据传输超时时间
                .build();
    }
}