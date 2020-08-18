package fyp.cms;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;

import android.content.ActivityNotFoundException;

import android.content.Intent;

import android.content.pm.PackageManager;

import android.graphics.Bitmap;

import android.net.Uri;

import android.os.Bundle;

import android.util.Log;

import android.view.KeyEvent;

import android.webkit.WebView;

import android.webkit.WebViewClient;

import android.widget.Toast;



public class Payment_Page extends AppCompatActivity {

    WebView browser;

    ProgressDialog dialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(1);

        getWindow().setFlags(1024,1024);

        setContentView(R.layout.payment_layout);

        browser=findViewById(R.id.web);

        dialog=new ProgressDialog(this);

        dialog.setMessage("Please Wait...");

        browser.getSettings().setJavaScriptEnabled(true);

        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        browser.setWebViewClient(new MyWebViewClient());

        browser.loadUrl("https://www.studyhouse.pk/product/male-sahiwal-cow/");

    }



    class MyWebViewClient extends WebViewClient {

        @Override

        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);

            if(!dialog.isShowing()){

                dialog.show();

            }

        }



        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("https://www.studyhouse.pk/checkout/order-received/")){
                Toast.makeText(Payment_Page.this,"Payment Sucessfully recieved",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Payment_Page.this,buyer_home_page.class));
                finish();
            }else {
                view.loadUrl(url);
            }

            return true;

        }



        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);

            if(dialog.isShowing()){

                dialog.dismiss();

            }

        }

    }

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            switch (keyCode) {

                case KeyEvent.KEYCODE_BACK:

                    if (browser.canGoBack()) {

                        browser.goBack();

                    } else {

                        finish();

                    }

                    return true;

            }



        }

        return super.onKeyDown(keyCode, event);

    }
}


