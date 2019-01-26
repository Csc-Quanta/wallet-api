package org.csc.wlt.helper;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

//import org.csc.account.core.BlockChainConfig;
@NActorProvider
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "httpHelp")
@Data
public class HttpHelp implements ActorService {

//    @ActorRequire(name = "BlockChain_Config", scope = "global")
//    BlockChainConfig blockChainConfig;

	/**
	 * get 请求
	 * 
	 * @param url
	 * @param head
	 * @return
	 */
	public String get(String url, Map<String, String> head, boolean needProxy) throws IOException {
		URL reqUrl;
		HttpURLConnection conn;
		reqUrl = new URL(url);
		if (needProxy) {
			conn = (HttpURLConnection) reqUrl
					.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)));
		} else {
			conn = (HttpURLConnection) reqUrl.openConnection();
		}
		for (Map.Entry<String, String> map : head.entrySet()) {
			conn.setRequestProperty(map.getKey(), map.getValue());
		}
		try (InputStreamReader isr = new InputStreamReader(conn.getInputStream()); // 返回从此打开的连接读取的输入流。
				BufferedReader br = new BufferedReader(isr)) {
			// 创建一个使用默认大小输入缓冲区的缓冲字符输入流。
			String temp;
			StringBuffer html = new StringBuffer();
			while ((temp = br.readLine()) != null) {
				// 按行读取输出流
				if (!temp.trim().equals("")) {
					html.append(temp);
				}
			}
			return html.toString();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * post请求
	 * 
	 * @param url       请求的url
	 * @param body      http身体信息
	 * @param headerMap 头信息
	 * @param needProxy 是否需要翻墙
	 * @return
	 */
	public String post(String url, Map<String, Object> body, Map<String, String> headerMap, boolean needProxy)
			throws IOException {
		URL reqUrl;
		HttpURLConnection conn;
		reqUrl = new URL(url);
		if (needProxy) {
			conn = (HttpURLConnection) reqUrl
					.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)));
		} else {
			conn = (HttpURLConnection) reqUrl.openConnection();
		}
		conn.setDoOutput(true);
		conn.setDoInput(true);
		for (Map.Entry<String, String> map : headerMap.entrySet()) {
			conn.setRequestProperty(map.getKey(), map.getValue());
		}
		try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
			// 发送请求参数
			out.print(JSON.toJSONString(body));
			// flush输出流的缓冲
			out.flush();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String line;
				StringBuffer buffer = new StringBuffer();
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				return buffer.toString();
			} catch (IOException e) {
				throw e;
			}
		} catch (IOException e) {
			throw e;
		}
	}
}
