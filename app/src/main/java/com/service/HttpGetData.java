package com.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.service.HttpGetListener;

public class HttpGetData extends AsyncTask<String, Void, String>{

    private HttpClient mhttpclient;
    private HttpGet mhttpget;
    private HttpResponse mhttpResponse;
    private HttpEntity mHttpEntity;
    private InputStream in;
    private StringBuffer sb;

    //声明url变量
    private String url;
    //声明接口
    private HttpGetListener listener;

    public HttpGetData(){
    }

    public HttpGetData(String url){
        this.url = url;
    }

    public HttpGetData(String url,HttpGetListener listener){
        this.url = url;
        this.listener = listener;
    }

    /**
     * 写后台需要执行的程序
     */
    @Override
    protected String doInBackground(String... params) {

        try{
            //首先创建一个客户端实例
            mhttpclient = new DefaultHttpClient();
            //设置传递的方法
            mhttpget = new HttpGet(url);
            //通过客户端进行发送
            mhttpResponse = mhttpclient.execute(mhttpget);
            //通过HttpResponse获取方法体
            mHttpEntity = mhttpResponse.getEntity();
            //通过流获取具体的内容
            in = mHttpEntity.getContent();
            //创建缓冲区
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine())!= null){
                sb.append(line);
            }
            return sb.toString();


        }catch(Exception e){
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