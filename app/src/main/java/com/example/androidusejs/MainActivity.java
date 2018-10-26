package com.example.androidusejs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class MainActivity extends AppCompatActivity {

    private BridgeWebView webview;
    private TextView tvJs;
    private TextView tvShowmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        registerInJs();
        callJs();
    }

    private void initView() {
        tvJs = (TextView) findViewById(R.id.tv_androidcalljs);
        tvShowmsg = (TextView) findViewById(R.id.tv_showmsg);

        webview = (BridgeWebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //与js交互必须设置
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/html.html");
    }

    private void callJs(){
        tvJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.callHandler("functionInJs", "Android传递给js的数据", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        tvShowmsg.setText(data);
                    }
                });
            }
        });
    }

    private void registerInJs() {
        webview.registerHandler("functionInAndroid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                tvShowmsg.setText("js调用了Android");
                //返回给html
                function.onCallBack("Android回传给js的数据");
            }
        });
    }

}
