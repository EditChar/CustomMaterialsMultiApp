package com.ing.custommaterials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;



public class TabsActivity extends AppCompatActivity implements View.OnClickListener {
    ColorStateList def;
    private TextView tab1, tab2 , tab3, select;
    TextView selectedTextView, nonSelectedTextView1, nonSelectedTextView2;

    private int selectedTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
    //View.OnClickListener implements ettik böylelikle her buton için ayrı ayrı setOnClick etmemize gerek kalmadan aşağıda onClick içine çağıracaz.
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);

        select = findViewById(R.id.select);
        def=tab2.getTextColors();

        //select FragmentBir by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, FragmentBir.class, null)
                .commit();


    }

    private void selectTab(int tabNumber){
        if(tabNumber == 1 ){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, FragmentBir.class, null)
                    .commit();
        }
        else if(tabNumber == 2 ){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, FragmentIki.class, null)
                    .commit();
        }
        else if(tabNumber == 3 ){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, FragmentUc.class, null)
                    .commit();
        }
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.tab1){
            select.animate().x(0).setDuration(100);
            tab1.setTextColor(Color.WHITE);
            tab2.setTextColor(def);
            tab3.setTextColor(def);
            selectTab(1);
        }
        else if(view.getId() == R.id.tab2){
            int size = tab2.getWidth();
            select.animate().x(size).setDuration(100);
            tab2.setTextColor(Color.WHITE);
            tab1.setTextColor(def);
            tab3.setTextColor(def);
            selectTab(2);
        }
        else if(view.getId() == R.id.tab3){
            int size = tab2.getWidth() * 2;
            select.animate().x(size).setDuration(100);
            tab3.setTextColor(Color.WHITE);
            tab1.setTextColor(def);
            tab2.setTextColor(def);
            selectTab(3);
        }
    }

}