package com.mtrsz.dpms.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.mtrsz.dpms.common.core.excpetion.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


/**
 * @author vskendo
 * @description 简易的 Http 交流框架
 * @date 2022/8/4
 */
public final class HttpRequestUtil {
    private HttpRequestUtil() {
    }

   public static String requestOnce(String url, int connectTimeoutMs, int readTimeoutMs) {
       return requestOnce(url, connectTimeoutMs, readTimeoutMs, null);
   }

   public static String requestOnce(String url) {
       return requestOnce(url, NETWORK_TIMEOUT, NETWORK_TIMEOUT, null);
   }

   public static String requestOnce(String url, String data) {
       return requestOnce(url, NETWORK_TIMEOUT, NETWORK_TIMEOUT, data);
   }

   public static String requestOnce(String url, String paramName, String data) {
       return requestOnce(url, NETWORK_TIMEOUT, NETWORK_TIMEOUT, paramName, data);
   }

   public static String requestOnce(String url, int connectTimeoutMs, int readTimeoutMs, String paramName, String data) {
       try {
           HttpResponse httpResponse = gainHttpClient().execute(gainGetOrPost(url, connectTimeoutMs, readTimeoutMs, paramName, data));
           return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8.name());
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
   }
   public static String requestOnce(String url, int connectTimeoutMs, int readTimeoutMs, String data) {
       try {
           HttpResponse httpResponse = gainHttpClient().execute(gainGetOrPost(url, connectTimeoutMs, readTimeoutMs, data));
           return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8.name());
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
   }

   /**
    * 发送POST请求，参数列表以JSON字符串形式发送
    *
    * @param url          请求地址
    * @param content      请求参数的json字符串
    * @param headers      header信息
    * @param responseType 请求返回内容的类型
    * @return 响应内容
    */
   public static <T> ResponseEntity<T> post(String url, String content, HttpHeaders headers, Class<T> responseType) {
       RestTemplate restTemplate = new RestTemplate();
       if (null == headers) {
           headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
       }
       org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>(content, headers);
       return restTemplate.postForEntity(url, request, responseType);
   }

   /**
    * 发送POST请求，参数列表以MAP形式发送
    *
    * @param url     请求地址
    * @param params  请求参数
    * @param headers header信息
    * @return 响应内容
    */
   private static ResponseEntity<String> post(String url, Map<String, String> params, HttpHeaders headers) {
       RestTemplate restTemplate = new RestTemplate();
       MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
       if (!ObjectUtils.isEmpty(params)) {
           setRequestParam(paramMap, params);
       }
       if (null == headers) {
           headers = new HttpHeaders();
       }
       org.springframework.http.HttpEntity<MultiValueMap<String, String>> request = new org.springframework.http.HttpEntity<>(paramMap, headers);
       return restTemplate.postForEntity(url, request, String.class);
   }

   /**
    * 把MAP类型的参数列表转换成MultiValueMap
    *
    * @param paramMap 参数数据目标，post请求参数使用的类型
    * @param params   参数数据来源
    */
   private static void setRequestParam(MultiValueMap<String, String> paramMap, Map<String, String> params) {
       for (String key : params.keySet()) {
           paramMap.add(key, params.get(key));
       }
   }

   private static HttpClient gainHttpClient() {
       BasicHttpClientConnectionManager connManager = connManager = new BasicHttpClientConnectionManager(
               RegistryBuilder.<ConnectionSocketFactory>create()
                       .register("http", PlainConnectionSocketFactory.getSocketFactory())
                       .register("https", SSLConnectionSocketFactory.getSocketFactory())
                       .build(),
               null,
               null,
               null
       );
       return HttpClientBuilder.create().setConnectionManager(connManager).build();
   }

   private static HttpRequestBase gainGetOrPost(String url, int connectTimeoutMs, int readTimeoutMs, String data) {
       HttpRequestBase httpRequestBase;
       if (StringUtils.isBlank(data)) { //get
           httpRequestBase = new HttpGet(url);
       } else { //post
           HttpPost post = new HttpPost(url);
           post.setEntity(new StringEntity(data, WechatConstants.ENCODER_TYPE));
           httpRequestBase = post;
       }
       httpRequestBase.setConfig(RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build());
       httpRequestBase.addHeader(CONTENT_TYPE, ContentType.TEXT_XML.getMimeType());
       return httpRequestBase;
   }

   private static HttpRequestBase gainGetOrPost(String url, int connectTimeoutMs, int readTimeoutMs, String paramName, String data)
           throws JsonProcessingException {
       ObjectMapper objectMapper = getMapper();
       Map<String, Object> map = new HashMap<>();
       if (StringUtils.isNotEmpty(paramName) && StringUtils.isNotEmpty(data)) {
           map.put(paramName, data);
       } else throw new RuntimeException("data is empty but require translate");
       return gainGetOrPost(url, connectTimeoutMs, readTimeoutMs, objectMapper.writeValueAsString(map));
   }

    /**
     * 获取ObjectMapper的工具类
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }

    // public static String parseResult(ResponseEntity<String> redirectResult) {
    //     HttpHeaders headers = redirectResult.getHeaders();
    //     System.out.println("请求结束，收到： " + redirectResult);
    //     System.out.println("header为：");
    //     headers.forEach((k, v) -> System.out.println(k + ": " + v));
    //     String body = redirectResult.getBody();
    //     System.out.println("body 为" + body);
    //     JSONObject jsonBody = Optional.ofNullable(JSON.parseObject(body)).orElseThrow(() -> new ServiceException("返回值没有Body"));
    //     String code = Optional.ofNullable(jsonBody.getString("code")).orElseThrow(() -> new ServiceException("无法解析返回值的code"));
    //     if (!"200".equals(code))
    //         throw new ServiceException("返回值code为: " + code + "，错误信息：" + jsonBody.getString("msg"));
    //     return Optional.ofNullable(jsonBody.getString("data")).orElse("");
    // }

}
