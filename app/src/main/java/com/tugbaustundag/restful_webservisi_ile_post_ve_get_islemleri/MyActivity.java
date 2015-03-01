/*
 * Copyright (C) 2015 Tuğba Üstündağ <tugba.ust2008@gmail.com><http://tugbaustundag.com>
*/
package com.tugbaustundag.restful_webservisi_ile_post_ve_get_islemleri;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //StrictMode kullanarak,ağ erişiminin güvenli bir şekilde yapılmasını sağlıyoruz...
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String wcfUrl= "http://test.com/getRestfull.php";
        String jsonString="";

        JSONObject obj=new JSONObject();

        try {
            //RESTful Web servisiyle göndermek isteğimiz içerikleri,Json objesine   put methoduyla ekliyoruz..
            obj.put("name_surname","Tuğba Üstündağ");
            obj.put("student_email","tugba@gmail.com");
            obj.put("student_phoneNumber","5355850987");

            HttpClientMy HttpClientMy=new HttpClientMy();
            //RESTful Web servisini çağırıp, içerikleri sunucuya gönderen ve  sunucudan  data çekmeyi sağlayn methodu çağırdık,
            jsonString=HttpClientMy.callWebService(wcfUrl, obj);

            //jsonString değişkenen gelen değer:
            // {"Android":false}{"student_email":"tugba@gmail.com","student_phoneNumber":"5355850987","name_surname":"Tugba Üstündag"}

            //Json string ini parse edicek methodu yazdık..
            ParseResponseJSONData(jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //RESTful Web servis'den gelen json string i parse eden method
    public void ParseResponseJSONData(String jsonString) {

        JSONObject jsonResponse;

        try {
            //Json objesi olusturuyoruz..
            jsonResponse = new JSONObject(jsonString);
            //Olusturdugumuz obje üzerinden  json string deki dataları kullanıyoruz..
            JSONObject sys = jsonResponse.getJSONObject("Android");
            String student_email=sys.getString("student_email");
            String name_surname=sys.getString("name_surname");
            String student_phoneNumber=sys.getString("student_phoneNumber");

            //Sunucudan gelen dataları kullanıcıyı göstermek için, TextView controller'ına değerilerimizi gönderiyoruz
            TextView ad=(TextView)findViewById(R.id.ad);
            TextView email=(TextView)findViewById(R.id.email);
            TextView telefonNo=(TextView)findViewById(R.id.telNo);
            ad.setText(name_surname);
            email.setText(student_email);
            telefonNo.setText(student_phoneNumber);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

