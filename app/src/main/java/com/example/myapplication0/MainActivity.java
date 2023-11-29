package com.example.myapplication0;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication0.databinding.ActivityMainBinding;
//import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
//import com.yandex.mobile.ads.common.AdSize;
import com.yandex.mobile.ads.common.MobileAds;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;

public class MainActivity extends AppCompatActivity {

    private BannerAdView bannerAd;
    private String group = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(v -> {

        });

        LinkExtractorTask task = new LinkExtractorTask();
        task.execute("https://www.altspu.ru/schedule/students/s-437/?year=2023&week=49");

        // Запуск AsyncTask
//        new ParserWebTask().execute();

        createBanner();
        loadBanner();




    }
    private static class ParserWebTask extends AsyncTask<Void, Void, ParserWeb> {
        protected ParserWeb doInBackground(Void... params) {
            try {
                return new ParserWeb("s");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        protected void onPostExecute(ParserWeb parserWeb) {
            // Здесь вы можете использовать инициализированный объект `parserWeb`
        }
    }

    private void createBanner() {
        String adUnitId = "demo-banner-yandex";
        BannerAdSize bannerAdSize = BannerAdSize.stickySize(this,900);

        bannerAd = new BannerAdView(this);
        bannerAd.setAdUnitId(adUnitId);
        bannerAd.setAdSize(bannerAdSize);
        bannerAd.setBannerAdEventListener(new BannerAdEventLogger());

        // Добавьте bannerAd в ваш макет
        // Например: constraintLayout.addView(bannerAd);

        LinearLayout main = findViewById(R.id.banner_content);
        main.addView(bannerAd);
    }

    private void loadBanner() {
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);
    }

    private class BannerAdEventLogger implements BannerAdEventListener {

        @Override
        public void onAdLoaded() {
            // Баннер успешно загружен
        }

        @Override
        public void onAdFailedToLoad(AdRequestError error) {
            // Ошибка при загрузке баннера
        }

        @Override
        public void onAdClicked() {
            // Пользователь кликнул по баннеру
            System.out.println("$#$#$#$#$#$##$$#$#$#$##$$##$#$#$#$#$$##$#$#$#$#$#$#$#");
        }

        @Override
        public void onLeftApplication() {
            // Пользователь покинул приложение после клика по баннеру
        }

        @Override
        public void onReturnedToApplication() {
            // Пользователь вернулся в приложение
        }

        @Override
        public void onImpression(ImpressionData data) {
            // Отслеживание показа баннера
        }
    }
}

