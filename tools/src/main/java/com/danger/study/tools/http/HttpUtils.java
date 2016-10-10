package com.danger.study.tools.http;

import com.danger.study.tools.common.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用于http请求的工具类
 * Created by danger on 2016/6/21.
 */
public final class HttpUtils {

    private static final int CONNECT_SOKET_TIME_OUT_LONG = 4000;
    private static final int CONNECT_TIME_OUT_LONG = 4000;

    // 禁止实例化和继承
    private HttpUtils() {
    }

    public static String doHttpGet(String url) {
        return httpRequest(false, "GET", "", url, null, 0, 0, 0);
    }

    public static String doHttpGet(String url, Map<String, String> paramMap) {
        return httpRequest(false, "GET", "", getUrl(url, paramMap), null, 0, 0, 0);
    }

    public static String doHttpPost(String url, Map<String, String> paramMap) {
        return httpRequest(false, "POST", "", url, paramMap, 0, 0, 0);
    }

    public static String doHttpPost(String url, Map<String, String> paramMap, int retry) {
        return httpRequest(false, "POST", "", url, paramMap, 0, 0, retry);
    }

    public static String doHttpPost(String url, String jsonString) {
        return httpRequest(false, "POST", jsonString, url, null, 0, 0, 0);
    }

    public static String doHttpPost(String url, String jsonString, int retry) {
        return httpRequest(false, "POST", jsonString, url, null, 0, 0, retry);
    }

    private static String httpRequest(Boolean isHttps, String method, String params, String strUrl, Map<String, String> paramMap, int socketTimeout, int connectTimeout, int retry) {
        return httpRequest(isHttps, method, params, strUrl, paramMap, socketTimeout, connectTimeout, null, retry);
    }

    public static String httpRequest(Boolean isHttps, String method, String jsonParam, String strUrl, Map<String, String> paramMap, int socketTimeout, int connectTimeout, Map<String, String> headers, int retry) {
        String params1 = jsonParam;
        try {
            long st = System.currentTimeMillis();
            if (socketTimeout == 0) {
                socketTimeout = CONNECT_SOKET_TIME_OUT_LONG;
            }
            if (connectTimeout == 0) {
                connectTimeout = CONNECT_TIME_OUT_LONG;
            }
            CloseableHttpClient client = getClient(isHttps);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout).build();
            CloseableHttpResponse response;
            if ("GET".equals(method)) {
                HttpGet get = new HttpGet(strUrl);
                get.setConfig(requestConfig);
                get.setHeader("Accept", "application/json");
                get.setHeader("Content-type", "application/json;charset=utf-8");
                setHeaders(headers, get);
                response = client.execute(get);
            } else if ("POST".equals(method)) {
                HttpPost post = new HttpPost(strUrl);
                post.setConfig(requestConfig);
                HttpEntity postEntity;
                if (StringUtils.isEmpty(jsonParam)) {
                    //request body 为空 进行 request parameter map 的判断
                    List<BasicNameValuePair> nvps = new ArrayList<>();
                    if (null != paramMap && (!paramMap.isEmpty())) {
                        nvps.addAll(paramMap.keySet().stream().map(name -> new BasicNameValuePair(name, paramMap.get(name))).collect(Collectors.toList()));
                        params1 = getUrl(strUrl, paramMap);
                    }
                    postEntity = new UrlEncodedFormEntity(nvps);
                } else {
                    //request body 进行 json 请求
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                    postEntity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
                }
                post.setEntity(postEntity);
                setHeaders(headers, post);
                response = client.execute(post);
            } else {
                return null;
            }
            //进行请求后 响应消息的处理
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resultMsg = EntityUtils.toString(entity, "UTF-8");
                    if (StringUtils.isNotEmpty(resultMsg)) {
                        long ha = System.currentTimeMillis() - st;
                        //------------ 请求超出2500ms 日志信息记录 提升为 warn
                        String logStr = "Method:" + method + " | isHttps: " + isHttps
                                + " | params: " + params1 + " | url: " + strUrl
                                + " | result: " + resultMsg + " ================ millSeconds:" + ha;
                        if (ha > 2500) {
                            Logger.getLogger(HttpUtils.class).warn(logStr);
                        } else {
                            Logger.getLogger(HttpUtils.class).info(logStr);
                        }
                        //------------ 请求结果信息返回
                        return resultMsg;
                    } else {
                        if (retry > 0) {
                            return httpRequest(isHttps, method, jsonParam, strUrl, paramMap, socketTimeout, connectTimeout, headers, retry - 1);
                        }
                    }
                }
                String msg = "Method:" + method + " isHttps: " + isHttps + " params: " + params1 + " url: " + strUrl + " response is null";
                Logger.getLogger(HttpUtils.class).warn(msg);
            } catch (Exception e) {
                String msg = "Method:" + method + " isHttps: " + isHttps + " params: " + params1 + " url: " + strUrl;
                Logger.getLogger(HttpUtils.class).warn(msg, e);
            } finally {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            String msg = "Method:" + method + " isHttps: " + isHttps + " params: " + params1 + " url: " + strUrl;
            Logger.getLogger(HttpUtils.class).warn(msg, e);
        }
        return null;
    }

    public static Map<String, String> getNameValuePair(String params) {
        Map<String, String> nvps = new HashMap<>();
        String[] paramArray = params.split("&");
        for (String param : paramArray) {
            String[] temp = param.split("=");
            nvps.put(temp[0], temp[1]);
        }
        return nvps;
    }

    private static void setHeaders(Map<String, String> headers, HttpMessage httpMessage) {
        if (headers != null && headers.entrySet().size() > 0) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpMessage.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static CloseableHttpClient getClient(boolean isHttps) {
        if (isHttps) {
            return Objects.requireNonNull(createSSLInsecureClient());
        }
        return HttpClients.createDefault();
    }

    private static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getUrl(String url, Map<String, String> params) {
        StringBuilder requestUrl = new StringBuilder(url);
        int i = 0;
        for (Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isEmpty(entry.getValue())) continue;
            if (i == 0) {
                if (url.matches(".*\\?.*")) {
                    requestUrl.append("&");
                } else {
                    requestUrl.append("?");
                }
                requestUrl.append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                requestUrl.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            i++;
        }
        return requestUrl.toString();
    }

    public static boolean IsUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static boolean IsIp(String str) {
        if ("0:0:0:0:0:0:0:1".equals(str)) {
            return true;
        }
        String regex = "(\\d{1,3}\\.){3}\\d{1,3}";
        return match(regex, str);
    }

    public static long toIpLong(String strIp) {
        if ("0:0:0:0:0:0:0:1".equals(strIp)) {
            strIp = "127.0.0.1";
        }
        if (!IsIp(strIp)) throw new IllegalArgumentException("[" + strIp + "]不是有效的ip地址");
        long[] ip = new long[4];
        String[] items = strIp.split("\\.");
        for (int i = 0; i < 4; i++) {
            ip[i] = Long.parseLong(items[i]);
        }
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String toIpString(long longIp) {
        return String.valueOf((longIp >> 24) & 0xFF) + "." +
                ((longIp >> 16) & 0xFF) + "." +
                ((longIp >> 8) & 0xFF) + "." +
                (longIp & 0xFF);
    }

}