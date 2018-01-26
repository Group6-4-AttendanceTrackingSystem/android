package com.example.android.attendancetrackingsystem6_4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.attendancetrackingsystem6_4.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String STR = "";
    private final int HEIGHT = 1024;
    private final int WIDTH = 1024;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Please show the QR code to your lecturer");
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.week);
        String week = getIntent().getStringExtra("week");
        String sessionKey = getIntent().getStringExtra("sessionKey");
        String email = getIntent().getStringExtra("email");
        textView.setText("Attendance token of " + email + " for week " + String.valueOf(Integer.parseInt(week)+1) + ".");
        try {
            final TextView mTextView = (TextView) findViewById(R.id.temptext);
// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url ="http://ase2017-group6-4.appspot.com/rest/attendance/qr/student/" + email + "/week/" + week + "/session/" + sessionKey;

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            STR = response;
                            try {
                                Bitmap bitmap = encodeAsBitmap(STR);
                                ImageView imageView = (ImageView) findViewById(R.id.qrCode);
                                imageView.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                            Log.v("response",response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("response","bad response");
                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {

            return;
        }

    }
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            System.out.println("Unsupported format");
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;//black or white
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }
}
