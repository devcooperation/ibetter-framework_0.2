package com.ibetter.http.request.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibetter.http.request.result.CrawlHttpStream;
 

public class HttpUtils {
	
	protected static final Logger logger=LoggerFactory.getLogger(HttpUtils.class);
	
	protected static final Charset defaultCharset = Charset.forName("utf-8");
	
	protected static Map<String,String> defaultContentType=Maps.newHashMap();
	
	protected static Gson gson = new Gson();
	
	protected static RequestConfig config = null;
	static {
		initDefaultContentType();
		initDefaultHttpConfig();
	}
	
	public static void initDefaultHttpConfig(){
		
		Builder builder = RequestConfig.custom();
		builder.setSocketTimeout(1000 * 15);
		config = builder.build();
		
	}
	public static void initDefaultContentType(){
		defaultContentType.put("image/jpeg", ".jpg");
		defaultContentType.put("image/jpg", ".jpg");
		defaultContentType.put("image/png", ".png");
		defaultContentType.put("image/gif", ".gif");
	}
	
	
	public static String getTypeValue(String type){
		return defaultContentType.get(StringUtils.lowerCase(type));
	}
	
	
	/**
	 * 压缩功能支持
	 * @param url
	 * @param paramMap
	 * @param type
	 * @param isSupportCompress
	 * @return
	 */
	
	public static <T> T post(String url, Map<String, String> paramMap, TypeToken<T> type,boolean isSupportCompress){
		
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
		
		httpClientBuilder.setConnectionManager(connManager);
		httpClientBuilder.setDefaultRequestConfig(config);
	
		CloseableHttpClient httpclient = httpClientBuilder.build();
		HttpPost request = new HttpPost(url);
		List<NameValuePair> parameters = createParam(paramMap);
		HttpEntity entity = new UrlEncodedFormEntity(parameters, Charset.forName("utf-8"));
		request.setEntity(entity);
		
		try {
			CloseableHttpResponse response = httpclient.execute(request);
			StatusLine statusline = response.getStatusLine();
			int statuscode = statusline.getStatusCode();
			if (statuscode == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String result =null;
				if(isSupportCompress){
					InputStream input = responseEntity.getContent();
					InflaterInputStream inflaterInputStream = new InflaterInputStream(input);
					result = IOUtils.toString(inflaterInputStream, defaultCharset);
				}else{
					result=EntityUtils.toString(responseEntity, defaultCharset);
				}
				if(type!=null&&StringUtils.isNotBlank(result)){
					Type generic = type.getType();
					String typeName=generic.getTypeName();
					if(StringUtils.equals(typeName,"java.lang.String")){
						result=StringUtils.join("\"",result,"\"");
					}
					return gson.fromJson(result, generic);
				}
				return  null;
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			connManager.shutdown();
		}

		return null;
		
	}
	public static <T> T post(String url, Map<String, String> paramMap, TypeToken<T> type) {
		return post(url, paramMap, type, false);
	}

	private static List<NameValuePair> createParam(Map<String, String> paramMap) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (MapUtils.isNotEmpty(paramMap)) {
			Set<Entry<String, String>> entrySet = paramMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				list.add(pair);
			}
		}
		return list;
	}

	public static  CrawlHttpStream getHttpStream(String url){
		return getHttpStream(url, null);
	}
	/**
	 * 爬取地址HTTP流
	 * @param url
	 * @param requestConfig
	 * @return
	 */
	public static CrawlHttpStream getHttpStream(String url,RequestConfig requestConfig){
		
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
		httpClientBuilder.setConnectionManager(connManager);
		
		if(requestConfig!=null){
			httpClientBuilder.setDefaultRequestConfig(requestConfig);
		}else{
			httpClientBuilder.setDefaultRequestConfig(config);
		}
		HttpGet httpGet=new HttpGet(url);
		CloseableHttpClient httpClient = httpClientBuilder.build();
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			StatusLine statusLine=resp.getStatusLine();
			int status = statusLine.getStatusCode();
			if(status==HttpStatus.SC_OK){
				HttpEntity respEntity = resp.getEntity();
				Header header = respEntity.getContentType();
				String value=header.getValue();
				String typeValue=getTypeValue(value);
				InputStream inputStream = respEntity.getContent();
				return new CrawlHttpStream(inputStream, typeValue);
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static <T> T post(String url, HttpEntity entity, TypeToken<T> type) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
		httpClientBuilder.setConnectionManager(connManager);
		httpClientBuilder.setDefaultRequestConfig(config);
		HttpPost request = new HttpPost(url);
		request.setEntity(entity);
		CloseableHttpClient httpclient = httpClientBuilder.build();
		try {
			CloseableHttpResponse response = httpclient.execute(request);
			StatusLine statusline = response.getStatusLine();
			int statuscode = statusline.getStatusCode();
			if (statuscode == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String result = EntityUtils.toString(responseEntity, defaultCharset);
				if(StringUtils.isNotBlank(result)){
					Type genericType = type.getType();
					String typeName=genericType.getTypeName();
					if(StringUtils.equals(typeName,"java.lang.String")){
						result=StringUtils.join("\"",result,"\"");
					}
					return gson.fromJson(result, genericType);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			connManager.shutdown();
		}
		return null;
	}

	public String postSSL(String url,HttpEntity entity,Header... headers){
		
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
		httpClientBuilder.setConnectionManager(connManager);
		
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			
			httpClientBuilder.setSSLSocketFactory(sslsf);
			CloseableHttpClient httpClient = httpClientBuilder.build();
			HttpPost httpPost=new HttpPost(url);
			if(ArrayUtils.isNotEmpty(headers)){
				for (int i = 0; i < headers.length; i++) {
					httpPost.addHeader(headers[i]);
				}
			}
			httpPost.setEntity(entity);
			 
			CloseableHttpResponse reponse = httpClient.execute(httpPost);
			StatusLine statusLine = reponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(HttpStatus.SC_OK==statusCode){
				HttpEntity respEntity = reponse.getEntity();
				String result = EntityUtils.toString(respEntity, defaultCharset);
				return result;
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			connManager.shutdown();
		}
		return null;
	}
	
	public static MultipartEntityBuilder createMultipartEntityBuilder() {
		MultipartEntityBuilder entitybuilder = MultipartEntityBuilder.create();
		entitybuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		return entitybuilder;
	}

	public static EntityBuilder createEntityBuilder() {
		EntityBuilder entityBuilder = EntityBuilder.create();
		return entityBuilder;
	}

	public static void main(String[] args) {
		TypeToken<Integer> type=new TypeToken<Integer>(){};
		Integer string= gson.fromJson("\"1\"", type.getType());
		System.out.println(string);
	}
}
