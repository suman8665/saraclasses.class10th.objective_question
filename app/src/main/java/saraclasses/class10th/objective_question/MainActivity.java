package saraclasses.class10th.objective_question;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    String url = "false";
    String pdf = "false";
    String tab = "false";
    String videoads_tab = "false";
    String chrome = "false";
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;
    boolean doubleBackToExitPressedOnce = false;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        sidemenu();

        webView = findViewById(R.id.webview);
        ads_manager.intads_load(MainActivity.this);


        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();

            }
        });


        webView.loadUrl("https://12tharts.com/All-Apps/10th-web-app/layout/objective.php");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setClickable(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);


        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(MainActivity.this, "Thanks For Using This Application", Toast.LENGTH_SHORT).show();

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
                    startActivity(intent);
                    url = "false";


                }

                if (pdf.equals("true")) {

                    Intent intent = new Intent(getApplicationContext(), PdfActivity.class);
                    intent.putExtra("url", consoleMessage.message());

                    startActivity(intent);pdf = "false";


                }

                if (tab.equals("true")) {

                    tab = "false";
                    customtab_manager.open_tab(MainActivity.this, consoleMessage.message());

                }

                if (videoads_tab.equals("true")) {

                    videoads_tab = "false";
                    customtab_manager.open_tab(MainActivity.this, consoleMessage.message());

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

                    ads_manager.show_intads_with_callback(MainActivity.this);

                }

                if (consoleMessage.message().equals("videoads")) {


                    ads_manager.show_intads_with_callback(MainActivity.this);


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

    private void sidemenu() {

        toolbar = findViewById(R.id.webtoolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_menu);
        navigationView.setItemIconTintList(null);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int menuitemid = menuItem.getItemId();

                if (menuitemid==R.id.share){

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                }


                if (menuitemid==R.id.rate){

                    Intent rateus = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(rateus);

                }


                if (menuitemid==R.id.email){

                    customtab_manager.open_tab(MainActivity.this, "https://pujaclasses.com/whatsapp-message");

                }


                if (menuitemid==R.id.objective){

                    Intent intent = new Intent(getApplicationContext(), UrlActivity.class);

                    intent.putExtra("url", "https://12tharts.com/All-Apps/10th-web-app/layout/objective.php");
                    startActivity(intent);

                }


                if (menuitemid==R.id.md){

                    Intent intent = new Intent(getApplicationContext(), UrlActivity.class);

                    intent.putExtra("url", "https://12tharts.com/All-Apps/10th-web-app/layout/model-paper.php");
                    startActivity(intent);

                }


                if (menuitemid==R.id.qb){

                    Intent intent = new Intent(getApplicationContext(), UrlActivity.class);

                    intent.putExtra("url", "https://12tharts.com/All-Apps/10th-web-app/layout/question-bank.php");
                    startActivity(intent);

                }


                if (menuitemid==R.id.test){

                    Intent intent = new Intent(getApplicationContext(), UrlActivity.class);

                    intent.putExtra("url", "https://12tharts.com/All-Apps/10th-web-app/layout/objective-test.php");
                    startActivity(intent);

                }



                layouthide();



                return true;

            }


        });


    }

    private void layouthide() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }

    }
}