package com.jqsd.common.util;

import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by sam on 16-8-29.
 */
public class HttpUtil {
    public static final Logger LOG = LoggerFactory
            .getLogger(HttpUtil.class);

    public static final String CHARSET_NAME = "UTF-8";
    public static final String REFERER = "referer";
    public static final String USER_AGENT = "user-agent";

    private static RequestConfig getConfig() {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setSocketTimeout(15000).setConnectionRequestTimeout(15000).setConnectTimeout(15000);
        return builder.build();
    }

    public static String get(String url) throws IOException {
        return get(url, null, null);
    }

    public static String getbyCharset(String url,String charset) throws IOException {
        return get(url, null, null,charset);
    }

    public static BasicHeader getReferer(String url) {
        return new BasicHeader(REFERER, url);
    }

    public static BasicHeader getUserAgent() {
        return new BasicHeader(USER_AGENT, getRandomUserAgent());
    }

    /**
     * 获取随机伪浏览器userAgent
     *
     * @return
     */
    public static String getRandomUserAgent() {
        String userAgent = "";
        String[] userAgentArr = {
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:36.0) Gecko/20100101 Firefox/36.0",
                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0b13pre) Gecko/20110307 Firefox/4.0b13pre",
                "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:16.0) Gecko/20100101 Firefox/16.0",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.15) Gecko/20110303 Firefox/3.6.15",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36",
                "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10"};
        int num = getRandomNum(0, userAgentArr.length - 1);
        userAgent = userAgentArr[num];
        return userAgent;
    }

    public static int getRandomNum(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }


    public static String get(String url, BasicCookieStore cookie, BasicHeader header) throws IOException {
        return get(url,null,cookie,header);
    }
    public static String get(String url, BasicCookieStore cookie, BasicHeader header,String charset) throws IOException {
        return get(url,null,cookie,header,charset);
    }

    public static String get(String url, BasicHeader header) throws IOException {
        return get(url,null,null,header);
    }

    public static String get(String url, String ip) throws IOException {
        return get(url,ip,null,null);
    }

    public static String get(String url,String ip, BasicCookieStore cookie, BasicHeader header) throws IOException {
        return get(url,ip,cookie,header,CHARSET_NAME);
    }

    public static String get(String url,String ip, BasicCookieStore cookie, BasicHeader header,String charset) throws IOException {
        // LOG.info("get-->" + url);
        String strResult = "";

        HttpClientBuilder hcb = HttpClients.custom();
        if (cookie != null) {
            //
            hcb.setDefaultCookieStore(cookie);
        }
        if(StrUtil.isNotBlank(ip)){
            String[] addr = ip.split(":");
            int post = Integer.parseInt(addr[1]);
            HttpHost proxy = new HttpHost(addr[0],post);
            hcb.setProxy(proxy);
        }
        hcb.setUserAgent(getRandomUserAgent());
        CloseableHttpClient client = hcb.build();


        HttpGet get = new HttpGet(url);
        if (header != null) {
            get.addHeader(header);
        }
        get.addHeader(getUserAgent());
        get.setConfig(getConfig());
        HttpResponse httpResponse = client.execute(get);
        Header[] content_type = httpResponse.getHeaders("Content-Type");
        if (null != content_type && content_type.length > 0) {
            String encode = content_type[0].getValue();
            String[] sp = encode.split(";");
            String defaultEncode = charset;
            if (null != sp && sp.length > 1) {
                defaultEncode = sp[1].split("=")[1];
            }
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity(), defaultEncode);
            }
        }
        return strResult;
    }

    /**
     * 返回url里的cookie信息
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static BasicCookieStore getCookieStore(String url) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(url);
            get.addHeader(HttpUtil.getUserAgent());
            HttpResponse httpResponse = client.execute(get);
            Header[] cook = httpResponse.getHeaders("Set-Cookie");

            for (int i = 0; i < cook.length; i++) {
                String ck = cook[i].getValue();
                //System.out.println(ck);
                String[] c1 = ck.split(";");
                String name = c1[0].split("=")[0];
                String val = c1[0].split("=")[1];

                BasicClientCookie cookie = new BasicClientCookie(name, val);
                String domain = c1[1].split("=")[1];
                cookie.setDomain(domain);
                cookie.setPath("/");
                if (ck.indexOf("Expires") > 0) {
                    String tt = c1[2].split("=")[1];
                    cookie.setExpiryDate(DateUtil.parseDateGMT(tt));
                }
                cookieStore.addCookie(cookie);
            }
        } catch (Exception e) {

        }
        return cookieStore;
    }


    public static String post(String url, List<NameValuePair> formParams) throws IOException {
        //LOG.info("post-->" + url);
        CloseableHttpClient client = HttpClients.custom().setUserAgent(getRandomUserAgent()).build();
        HttpPost post = new HttpPost(url);
        post.setConfig(getConfig());
        if(formParams!=null){
            post.setEntity(new UrlEncodedFormEntity(formParams, CHARSET_NAME));
        }
        HttpResponse httpResponse = client.execute(post);
        String strResult = "";
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            strResult = EntityUtils.toString(httpResponse.getEntity(), CHARSET_NAME);
        }
        return strResult;
    }

    public static String post(String url,Map<String,String> map) throws IOException {
        return post(url,getParams(map));
    }

    private static List<NameValuePair> getParams(Map<String,String> map){
        List<NameValuePair> list = Lists.newArrayList();
        map.keySet().stream().sorted().forEach(key->list.add(new BasicNameValuePair(key,map.get(key))));
        return list;
    }

    /**
     * 获取url内容
     * @param urlStr
     * @param charset
     * @return
     */
    public static String getURLContent(String urlStr,String charset){
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setInstanceFollowRedirects(false);
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.connect();
            String line = "";
            BufferedReader URLinput = new BufferedReader(new InputStreamReader(con.getInputStream(),charset));
            while ((line = URLinput.readLine()) != null) {
                sb.append(line);
            }
            con.disconnect();
            return sb.toString();
        }catch(Exception e){
            return null;
        }
    }
}
