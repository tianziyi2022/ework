package com.hebutgo.ework.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author mazhe
 */
public class HttpTool {
    public static String get(String path) throws Exception {
        URL url = new URL(path);
        SslUtils.ignoreSsl();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        byte[] data = toByteArray(inStream);
        String result = new String(data, "UTF-8");
        return result;
    }

    public String post(String path,String params) throws Exception {
        String encoding = "UTF-8";
        byte[] data = params.getBytes(encoding);
        URL url = new URL(path);
        SslUtils.ignoreSsl();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        conn.setRequestProperty("Content-Type", "application/x-javascript; charset=" + encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        conn.setConnectTimeout(5 * 1000);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        // 响应代码 200表示成功
        System.out.println(conn.getResponseCode());
        String result = "";
        if (conn.getResponseCode() == 200) {
            InputStream inStream = conn.getInputStream();
            result = new String(toByteArray(inStream), "UTF-8");
            // 响应代码 200表示成功
            System.out.println(result);
        }
        return result;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}