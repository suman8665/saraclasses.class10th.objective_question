package saraclasses.class10th.objective_question;













import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.interstitial.InterstitialAd;

public class PdfActivity extends AppCompatActivity {

    LinearLayout layout;
    WebView webView;
    String url = "false";
    String pdf = "false";
    String tab = "false";
    String videoads_tab = "false";
    String chrome = "false";
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    InterstitialAd ads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = findViewById(R.id.banner_ads_layout);
        ads_manager.loadbannerads(layout, PdfActivity.this);
        webView = findViewById(R.id.webview);
        toolbar = findViewById(R.id.webtoolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();

            }
        });



        webView.loadUrl(getIntent().getExtras().getString("url"));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setClickable(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(PdfActivity.this, "Thanks For Using This Application", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                webView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
                startActivity(intent);
                finish();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);

                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {

                    swipeRefreshLayout.setRefreshing(false);

                }


                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

                if (url.equals("true")) {

                    Intent intent = new Intent(getApplicationContext(), UrlActivity.class);
                    intent.putExtra("url", consoleMessage.message());
                    url = "false";
                    startActivity(intent);


                }

                if (pdf.equals("true")) {

                    Intent intent = new Intent(getApplicationContext(), PdfActivity.class);
                    intent.putExtra("url", consoleMessage.message());
                    pdf = "false";
                    startActivity(intent);

                }

                if (tab.equals("true")) {

                    tab = "false";
                    customtab_manager.open_tab(PdfActivity.this, consoleMessage.message());

                }

                if (videoads_tab.equals("true")) {

                    videoads_tab = "false";
                    customtab_manager.open_tab(PdfActivity.this, consoleMessage.message());

                }


                if (consoleMessage.message().equals("url")) {

                    url = "true";
                }
                if (consoleMessage.message().equals("pdf")) {

                    pdf = "true";
                }
                if (consoleMessage.message().equals("chrome")) {

                    chrome = "true";
                }
                if (consoleMessage.message().equals("tab")) {

                    tab = "true";
                }
                if (consoleMessage.message().equals("videoads_tab")) {

                    videoads_tab = "true";
                }

                if (consoleMessage.message().equals("ads")) {

                    ads_manager.show_intads_with_callback(PdfActivity.this);

                }

                if (consoleMessage.message().equals("videoads")) {

                    ads_manager.show_intads_with_callback(PdfActivity.this);


                }

                if (consoleMessage.message().equals("share")){

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }

                if (consoleMessage.message().equals("rate")){

                    Intent rateus = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(rateus);
                }


                return super.onConsoleMessage(consoleMessage);
            }
        });


    }




    @Override
    public void onBackPressed() {


        if (webView.canGoBack()) {

            webView.goBack();

        } else {

            super.onBackPressed();

        }


    }

}




