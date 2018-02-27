package com.geely.mars.sqldemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by Shuai.Li13 on 2018/2/7.
 */

public class ZxingActivity extends Activity {
    private int i = 10;
    private ImageView iv;
    private static final String TAG = "ZxingActivity";
    private Observable<Integer> observable;
    private Observer<Integer> observer;
    private Observable<Object> defer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxing_activity);
        iv = findViewById(R.id.iv);

        init();
    }

    private void init() {
        observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        });
        observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                XLog.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                XLog.e(TAG, "onNext", integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                XLog.e(TAG, "onSubscribe");
            }

            @Override
            public void onComplete() {
                XLog.e(TAG, "onComplete");
            }
        };

        defer = Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return Observable.just(i);
            }
        });

    }

    private void initTimer() {

        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                XLog.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                XLog.e(TAG, "onNext", aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                XLog.e(TAG, "onComplete");
            }
        });
    }

    public void onClick(View view) {
        Bitmap bitmap = generateBitmap("I Love YOU", 1400, 1400);
        Bitmap bitmap1 = addLogo(bitmap, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        iv.setImageBitmap(bitmap1);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("a");
                emitter.onNext("b");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                XLog.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                XLog.e(TAG, "onNext", s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                XLog.e(TAG, "onError", e.getMessage());
            }

            @Override
            public void onComplete() {
                XLog.e(TAG, "onComplete");
            }
        });
        Observable.just("hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                XLog.e(TAG, "accept", s);
            }
        });
        just();
    }

    private void just() {
        Observable.just("1", "3").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                XLog.e(TAG, "just accept", s);
            }
        });
        Observable.fromArray(1, 3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Bitmap generateBitmap(String content, int width, int height) {

        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] colors = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        colors[i * width + j] = 0x00ff0000;
                    } else {
                        colors[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(colors, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }

    public void defer(View view) {
        i = 15;
        defer.subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                XLog.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Object o) {
                XLog.e(TAG, "onNext", o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                XLog.e(TAG, "onComplete");
            }
        });
    }

    public void timer(View view) {
//        initTimer();
        initInterval();
//        request();
    }

    private void request() {

        Observable.interval(5,2,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://fy.iciba.com")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInfer requestInfer = retrofit.create(RequestInfer.class);
                Observable<RequestDao> call = requestInfer.getCall();
                call.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<RequestDao>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RequestDao requestDao) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initInterval() {
        Observable.range(3,9).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
