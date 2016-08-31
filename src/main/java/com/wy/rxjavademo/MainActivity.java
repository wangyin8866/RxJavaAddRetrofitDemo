package com.wy.rxjavademo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mTextView;
    private String mUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    private String mUrl1 = "http://japi.juhe.cn/joke/content/";
    private ImageView mImageView;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.id_iv);
        mTextView = (TextView) findViewById(R.id.id_tv);
        rxAddretro();


//        retroTest();

//        obser();

//        retrofitAddRX();

    }

    private void retrofitAddRX() {
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(final Subscriber<? super List<String>> subscriber) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(mUrl1).addConverterFactory(ScalarsConverterFactory.create()).build();
                RequestSerives requestSerives = retrofit.create(RequestSerives.class);
                Call<String> call = requestSerives.getNewInfo("850f42b73e718c5ff58bac6219a06f13", "1", "10", "asc", "1418745237");
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
//                        mTextView.setText(response.body());
                        Log.e(TAG, "onResponse: " + response.body());
                        list.add(response.body());
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.toString());
                }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> o) {
                        Log.e(TAG, "onNext: " + o.toString());
                        mTextView.setText(o.toString());
                    }
                });
    }

    private void rxAddretro() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(3000, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())  //retrofit基于okhttp网络框架，必须加这个，不然会报错
                .baseUrl(mUrl1)
                .build();
        RequestSerives requestSerives = retrofit.create(RequestSerives.class);
        requestSerives.getTopMovie("850f42b73e718c5ff58bac6219a06f13", "1", "10", "asc", "1418745237")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable.empty())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText(e.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        mTextView.setText(o.toString());
                        Log.e(TAG, "onNext: " + o.toString());
                    }
                });
    }


    private void retroTest() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mUrl1).addConverterFactory(ScalarsConverterFactory.create()).build();
        RequestSerives requestSerives = retrofit.create(RequestSerives.class);
        Call<String> call = requestSerives.getNewInfo("850f42b73e718c5ff58bac6219a06f13", "1", "10", "asc", "1418745237");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mTextView.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void obser() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    URL url = new URL(mUrl);
                    InputStream is = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    subscriber.onNext(bitmap);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mImageView.setImageBitmap((Bitmap) o);
                    }
                });
    }

}
