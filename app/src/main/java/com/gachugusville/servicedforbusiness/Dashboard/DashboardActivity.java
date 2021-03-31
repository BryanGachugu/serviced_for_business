package com.gachugusville.servicedforbusiness.Dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.gachugusville.development.servicedforbusiness.BuildConfig;
import com.gachugusville.development.servicedforbusiness.R;
import com.gachugusville.servicedforbusiness.Registration.LogInActivity;
import com.gachugusville.servicedforbusiness.Utils.Provider;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.review.testing.FakeReviewManager;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final float END_SCALE = 0.7f;
    private DrawerLayout drawer_layout;
    private NavigationView navigation_view;
    private LinearLayout contentView;
    private ReviewManager manager;
    private ReviewInfo reviewInfo;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_dashboard);

        drawer_layout = findViewById(R.id.drawer_layout);
        navigation_view = findViewById(R.id.navigation_view);
        View header = navigation_view.getHeaderView(0);
        TextView txt_user_name = header.findViewById(R.id.txt_user_name);
        TextView txt_greetings = header.findViewById(R.id.txt_greetings);
        txt_user_name.setText(Provider.getInstance().getUser_name());
        ImageView menu_init = findViewById(R.id.menu_init);
        ImageView btn_settings = findViewById(R.id.btn_settings);
        contentView = findViewById(R.id.contentView);

        //Retrieve current user document using his unique ID
        String Uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DocumentReference docRef = db.collection("Providers").document(Uid);
        //Updates all the data to the provider class
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){
                documentSnapshot.toObject(Provider.getInstance().getClass());
                Log.d("NAMEFFS", Provider.getInstance().getUser_name());
                Log.d("WTF", String.valueOf(Provider.getInstance().isRegistrationFinished()));
            }
        }).addOnFailureListener(e ->{
            Toast.makeText(this, "An error occurred retrieving your data", Toast.LENGTH_SHORT).show();
            Log.d("'Retrieval", e.getMessage());
        });

        //Greet user based on time
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour > 12 && hour < 17) {
            greeting = "Good Afternoon";
            txt_greetings.setText(greeting);
        } else if (hour >= 17 && hour < 21) {
            greeting = "Good Evening";
            txt_greetings.setText(greeting);
        } else if (hour >= 21) {
            greeting = "Good Night";
            txt_greetings.setText(greeting);
        } else {
            greeting = "Good Morning";
            txt_greetings.setText(greeting);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.parent_container, new Home()).commit();

        menu_init.setOnClickListener(v -> {
            if (drawer_layout.isDrawerVisible(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START);
            } else drawer_layout.openDrawer(GravityCompat.START);
        });

        // animateNavigationDrawer();
        navigationDrawer();

        btn_settings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));


        //line_chart = findViewById(R.id.line_chart);
        // line_chart.setBackgroundColor(Color.TRANSPARENT);
        //LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(), "Weekly profile views");
        //lineDataSet.setColor(getResources().getColor(R.color.white));
        // lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        //iLineDataSets.add(lineDataSet);

        // LineData lineData = new LineData(iLineDataSets);
        // line_chart.setData(lineData);
        // line_chart.invalidate();

        // lineDataSet.setCircleColor(getResources().getColor(R.color.orange));
        //lineDataSet.setValueTextColor(getResources().getColor(R.color.white));
        // lineDataSet.setLineWidth(2f);
        // lineDataSet.setDrawHighlightIndicators(false);
        // lineDataSet.disableDashedLine();
        // lineDataSet.disableDashedHighlightLine();

    }

    private void animateNavigationDrawer() {
        drawer_layout.setScrimColor(getResources().getColor(R.color.light_blue));
        drawer_layout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //Translate the view accounting for the scaled width
                final float xOffset = drawer_layout.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);


            }
        });
    }

    //Navigation Drawer functions
    private void navigationDrawer() {
        //Navigation Drawer
        navigation_view.bringToFront();
        navigation_view.setNavigationItemSelectedListener(this);
        navigation_view.setCheckedItem(R.id.home_nav);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.parent_container, fragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerVisible(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else if (Objects.requireNonNull(navigation_view.getCheckedItem()).getItemId() == R.id.home_nav) {
            finishAffinity();
            finish();
        } else super.onBackPressed();
    }

    private ArrayList<Entry> lineChartDataSet() {
        ArrayList<Entry> dataset = new ArrayList<>();
        dataset.add(new Entry(0, 10));
        dataset.add(new Entry(1, 5));
        dataset.add(new Entry(2, 8));
        dataset.add(new Entry(3, 1));
        dataset.add(new Entry(4, 2));
        dataset.add(new Entry(5, 12));
        dataset.add(new Entry(6, 7));
        return dataset;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        try {
            switch (item.getItemId()) {
                case R.id.home_nav:
                    if (navigation_view.getMenu().findItem(R.id.home_nav).isChecked())
                        drawer_layout.closeDrawer(GravityCompat.START);
                    else {
                        fragment = new Home();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.work_nav:
                    if (navigation_view.getMenu().findItem(R.id.work_nav).isChecked())
                        drawer_layout.closeDrawer(GravityCompat.START);
                    else {
                        fragment = new JobsFragment();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.msg_nav:
                    if (navigation_view.getMenu().findItem(R.id.msg_nav).isChecked())
                        drawer_layout.closeDrawer(GravityCompat.START);
                    else {
                        fragment = new Messages();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.subscriptions_nav:
                    if (navigation_view.getMenu().findItem(R.id.subscriptions_nav).isChecked())
                        drawer_layout.closeDrawer(GravityCompat.START);
                    else {
                        fragment = new SubscriptionsFragment();
                        drawer_layout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.log_out:
                    logOutUser();
                    break;

                case R.id.share_nav:
                    shareApp();
                    drawer_layout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.rate_nav:
                    drawer_layout.closeDrawer(GravityCompat.START);
                    manager = new FakeReviewManager(this);
                    // rateApp(); //TODO delete fake review line and enable this one
                    break;
                case R.id.contact_dev_nav:
                    sendEmailToDev();
                    drawer_layout.closeDrawer(GravityCompat.START);
                    break;
            }
            loadFragment(fragment);
        } catch (Exception e) {
            Log.d("Navigation Error", e.getMessage());
        }

        return true;
    }

    private void logOutUser() {
        try {
            drawer_layout.closeDrawer(GravityCompat.START);
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
        } catch (Exception e) {
            Toast.makeText(this, "Failed to log out", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmailToDev() {
        Log.i("Send email", "");

        String[] TO = {"gachugusville@gmail.com"};
        String[] CC = {"gitau.spice270@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DashboardActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void rateApp() {
        manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(DashboardActivity.this, reviewInfo);
                flow.addOnSuccessListener(result -> findViewById(R.id.rate_nav).setVisibility(View.GONE)).addOnFailureListener(e -> Toast.makeText(DashboardActivity.this, "An internal error occured", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(DashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(DashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show());
    }

    public void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Serviced for business");
            String shareMessage = "\nHey, ready to earn extra money based on your skills and at the time of your preference? \n Then, try out Serviced for business\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            Log.d("ShareError", e.getMessage());
        }
    }

}