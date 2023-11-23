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

        // Чтение значения из кэша
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        group = preferences.getString("group", null); // "null" - значение по умолчанию, если такого значения нет в кэше
        // тут ты проверяешь естьли что то в кэше если да то группе даем оттуда значение
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Введите номер группы например 3115д");

            // Добавление компонента ввода текста
            final EditText input = new EditText(MainActivity.this);
            builder.setView(input);

            // Добавление кнопки "Ок"
            builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Код, выполняющийся при нажатии на кнопку "Ок"
                    //                    Toast.makeText(MainActivity.this, "Введенный текст: " + enteredText, Toast.LENGTH_SHORT).show();
                    group = input.getText().toString();
                    button.setText(group);

                    // тут ты записываешь в кэш
                    SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("group", group);
                    editor.apply();

                    @SuppressLint("ResourceType") LinearLayout layout = findViewById(R.string.my_layout);

                    if (layout != null) {
                        ViewGroup parent = (ViewGroup) layout.getParent();
                        if (parent != null) {
                            parent.removeView(layout);
                        }
                    }

                    new LoadDataAsync(group).execute(getApplicationContext());
                }
            });

            // Добавление кнопки "Отмена"
            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Код, выполняющийся при нажатии на кнопку "Отмена"
                    dialog.cancel();
                }
            });

            // Создание и отображение диалогового окна
            AlertDialog dialog = builder.create();
            dialog.show();

            // Здесь выполняется код при нажатии на кнопку
//            Toast.makeText(MainActivity.this, "Кнопка нажата", Toast.LENGTH_SHORT).show();
        });

//        bannerAd = findViewById(R.id.banner);
        createBanner();

        loadBanner();

        if (group != null){
            button.setText(group);
            new LoadDataAsync(group).execute(getApplicationContext());
        }

    }

    public static int getIntegerFromString(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private class LoadDataAsync extends AsyncTask<Context, Void, LinearLayout> {

        private String myGroup;

        public LoadDataAsync(String myGroup) {
            if (myGroup!=null){
                this.myGroup = myGroup;
            } else {
                this.myGroup = "null!";
            }
//            System.out.println("MY GROUP ------------------------------------------------------------------------------------------------------------------------ "+this.myGroup);
        }
        @SuppressLint("ResourceType")
        @Override
        protected LinearLayout doInBackground(Context... contexts) {
            Context context = contexts[0];
            LinearLayout newLayout = new LinearLayout(context);
            newLayout.setId(R.string.my_layout);
            newLayout.setOrientation(LinearLayout.VERTICAL);

            try {



                Sheet table = TableExcel.getSheet();
//                TextView dayOfTheWeek = new TextView(context);
//                dayOfTheWeek.setText("Label 1");
//                dayOfTheWeek.setTextColor(Color.YELLOW);
//                newLayout.addView(dayOfTheWeek);
                String oldMonDay = "";
                int rowsKill = 0;
                String myGroup = this.myGroup;//"3115д";
                int thisColumnIndex = -1;
                Date date = new Date();
                int counter = 0;
                for (Row row : table) {
                    if (rowsKill<=0){
                        LinearLayout itemBlock = new LinearLayout(context);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        int marginBottom = 0; // in pixels
                        layoutParams.setMargins(0, 0, 0, marginBottom);

                        itemBlock.setLayoutParams(layoutParams);

                        itemBlock.setBackgroundColor(Color.BLUE);

//                        itemBlock.setText(cell.getStringCellValue());

                        for (Cell cell : row) {
                            // если номер группы есть в ячейке (U3 - столбец строка)
                            if (cell.getStringCellValue().contains(myGroup)){
//                                System.out.println(counter+"((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((");
                                System.out.println(cell.getColumnIndex());
                                counter+=1;
                                thisColumnIndex = cell.getColumnIndex();
                            }
                            if (thisColumnIndex == cell.getColumnIndex()){
                                TextView item = new TextView(context);
                                item.setTextColor(Color.WHITE);
//                                item.setText(cell.getStringCellValue());

                                TextView itemTime = new TextView(context);
                                itemTime.setTextColor(Color.GRAY);

//                                System.out.println("MY GROUP ------------------------------------------------------------------------------------------------------------------------ "
//                                        +table.getRow(cell.getRowIndex()).getCell(0).getStringCellValue());
                                String monDay;
                                if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.BLANK && table.getRow(cell.getRowIndex()).getCell(0) != null){
                                System.out.println(cell.getCellType()+"((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((");
                                    System.out.println(table.getRow(cell.getRowIndex()).getCell(0));
                                    System.out.println(cell+"((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((");
                                    monDay = table.getRow(cell.getRowIndex()).getCell(0).getStringCellValue();
                                } else{
                                    monDay = "";
                                }


//                                TextView itemMonDay = null;
                                CardView itemMonDay = null;
                                if (monDay!=oldMonDay){

                                    itemMonDay = new CardView(context);
//                                    itemMonDay.set
                                    itemMonDay.setRadius(20);
//                                    itemMonDay.set;
                                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    );
                                    layoutParams1.setMargins(0, 40, 0, 0);
                                    itemMonDay.setLayoutParams(layoutParams1);
                                    itemMonDay.setPadding(100,100,100,100);

//                                    int thisDay = getIntegerFromString(monDay);
                                    String result = "checkThisDate(thisDay)";


//                                    TableExcel.checkStringContains(monDay, date.getThisStringRusDayWeek(), false);


                                    if (result == "old_day"){
                                        itemMonDay.setCardBackgroundColor(Color.DKGRAY);
                                    }else {

                                        itemMonDay.setCardBackgroundColor(Color.RED);
                                    }

                                    TextView textView = new TextView(context);
                                    textView.setText(monDay);
                                    itemMonDay.addView(textView);
                                }

//                                String time = table.getRow(cell.getRowIndex()).getCell(1).getStringCellValue();

                                String time;
                                if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.BLANK && table.getRow(cell.getRowIndex()).getCell(1) != null){
                                    time = table.getRow(cell.getRowIndex()).getCell(1).getStringCellValue();
                                } else{
                                    time = "";
                                }

                                itemTime.setText(time);

                                if (itemMonDay!=null){
//                                    TextView textView = new TextView(context);
//                                    textView.setText("");
                                    newLayout.addView(itemMonDay);
//                                    itemBlock.addView(textView);
                                }
                                if(item.length()>0 || itemTime.length()>0){
                                    item.setText(cell.getStringCellValue());
                                    itemBlock.addView(itemTime);
                                    itemBlock.addView(item);
                                    newLayout.addView(itemBlock);
                                }

//                                newLayout.addView(item);
                            }
                        }
                    } else{
                        rowsKill -= 1;
                    }
                }
                        if (counter==0){
                            TextView textView = new TextView(context);
                            textView.setTextColor(Color.RED);
                            textView.setText("ПУСТО");
                            newLayout.addView(textView);

                            TextView textView1 = new TextView(context);
                            textView1.setTextColor(Color.RED);
                            textView1.setText("");
                            newLayout.addView(textView1);
                        }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return newLayout;
        }

        @Override
        protected void onPostExecute(LinearLayout result) {
            LinearLayout masterBox = findViewById(R.id.master_box);
            masterBox.addView(result);
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