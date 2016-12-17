package com.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.service.HttpGetListener;

public class HttpPostData extends AsyncTask<String, Void, String> {

    private HttpClient mhttpclient;
    private HttpPost mhttppost;
    private HttpResponse mhttpResponse;
    private HttpEntity mHttpEntity;
    private InputStream in;
    private StringBuffer sb;

    //声明url变量
    private String url;
    //声明接口
    private HttpGetListener listener;

    private JSONObject param_data = new JSONObject();

    public HttpPostData() {
    }

    public HttpPostData(String url) {
        this.url = url;
    }

    public HttpPostData(String url, HttpGetListener listener) {
        this.url = url;
        this.listener = listener;
    }

    public void push(String key, String value) {
        try {
            param_data.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * 写后台需要执行的程序
     */
    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        Integer retCode = null;

        try {

            //首先创建一个客户端实例
            mhttpclient = new DefaultHttpClient();
            mhttppost = new HttpPost(url);

            // 为Post设置一些头信息
            mhttppost.setHeader("Content-type", "application/json");
            mhttppost.setHeader("Accept", "application/json");

            // 利用JSON，构造需要传入的参数
            String para_str = param_data.toString();
            System.out.println("para posted is " + para_str);
            mhttppost.setEntity(new StringEntity(para_str, HTTP.UTF_8));

            //通过客户端进行发送
            mhttpResponse = mhttpclient.execute(mhttppost);
            //通过HttpResponse获取方法体
            mHttpEntity = mhttpResponse.getEntity();
            //通过流获取具体的内容
            in = mHttpEntity.getContent();
            //创建缓冲区
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        listener.GetDataUrl(result);
        super.onPostExecute(result);
    }
}