package com.cerenerdem.selectdatemask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Maskeleme örneği linki https://github.com/santalu/mask-edittext

    EditText edt_Tarih;
    EditText edt_Tarih_8601;
    EditText edt_MaskTarih;
    Button  btn_SecTarih;
    Context context;
    String gonderilecekTarihFormati;

    Calendar takvim;
    int yil;
    int ay;
    int gun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        edt_Tarih = (EditText) findViewById(R.id.edt_Tarih);
        edt_Tarih_8601 = (EditText) findViewById(R.id.edt_Tarih_8601);
        //edt_MaskTarih = (EditText) findViewById(R.id.edt);
        btn_SecTarih = (Button) findViewById(R.id.btn_SecTarih);


        //takvim bilgilerini almak için getInstance kullanıyoruz.
        takvim = Calendar.getInstance();

        yil = takvim.get(Calendar.YEAR);
        ay = takvim.get(Calendar.MONTH);
        gun = takvim.get(Calendar.DAY_OF_MONTH);

        takvim.setTimeInMillis(0);
        takvim.set(yil, ay, gun, 0, 0, 0);

        SimpleDateFormat format_example1 = new SimpleDateFormat("dd-MM-yyyy");//yazdırılmasını istediğimiz format
        String strDate = format_example1.format(takvim.getTime());//strDate değişkenimize takvimin anlık vereceği değeri istediğimiz formatta yazdırıyoruz.
        edt_Tarih.setText(strDate.toString());//sayfa açıldığında bugünün tarihini istediğimiz formatta edittexe yazar.

    }



    public  String formatDate_8601(int year, int month, int day) {//format değişikliği

        takvim = Calendar.getInstance();
        takvim.setTimeInMillis(0);
        takvim.set(year, month, day);
        Date date = takvim.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        return sdf.format(date);
    }

    public  String formatDate(int year, int month, int day) {//format değişikliği

        takvim = Calendar.getInstance();
        takvim.setTimeInMillis(0);
        takvim.set(year, month, day);
        Date date = takvim.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(date);
    }


   public void click_btn_SecTarih (View view){ //tarih seçildiği anda yapılacak işlemler...

        //dialog olarak açılacak tarih seçme kutusunu tanımlıyoruz ve veri bilgilerini giriyoruz.
       DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {//tarih seçildiği anda bize year değişkeni olarak yıl, month değişkeni olarak ay, dayOfMonth olarak gün bilgisini gönderecek.
               month += 1;//ocak ayı 0 dır bu nedenle 1 arttırıp başlatıyoruz.

                //edt_Tarih_8601.setText(formatDate(year,month,dayOfMonth));//alt kısımda yer alan edittextimize formatdate fonksiyonunda belirttiğim şekilde tarih yazdırılsın istiyorum.
                edt_Tarih_8601.setText(formatDate_8601(year,month,dayOfMonth));//alt kısımda yer alan edittextimize formatdate fonksiyonunda belirttiğim şekilde tarih yazdırılsın istiyorum.
                String gonderilecekTarihFormati = formatDate_8601(year,month,dayOfMonth);//seçilen tarih bilgisini formatdate_8601 deki gibi de gonderilecekMetin değişkenime atıyorum. Eğer bir veritabanına kayıt işlemi olacaksa bu tarihi kullanacağım.

           }


       }, yil, ay, gun); //bugüne dair yıl ay gün bilgilerini açılışta seçili olarak getirtiyoruz

       dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
       dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
       dpd.show();

   }
}
