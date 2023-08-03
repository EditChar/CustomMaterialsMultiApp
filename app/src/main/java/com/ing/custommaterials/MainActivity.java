package com.ing.custommaterials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout drink_layout, pizza_layout, donut_layout;
    TextView btn_continue, btn_cancel;
    Animation fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drink_layout = findViewById(R.id.drink_layout);
        pizza_layout = findViewById(R.id.pizza_layout);
        donut_layout = findViewById(R.id.donut_layout);
        btn_continue = findViewById(R.id.btn_continue);
        btn_cancel = findViewById(R.id.btn_cancel);

        fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        btn_continue.setAlpha(0); // item seçilmeden başlangıçta alpha(0) ile btn_continue gözükmüyor.
        btn_cancel.setAlpha(0);




        donut_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                donut_layout.setBackground(getDrawable(R.drawable.bg_item_selected));
                btn_continue.setAlpha(1); // default olarak item seçilmeden yukarıda alpha(0) verdiğimiz için alpha(1) yapmazsak gözükmez seçilince de.
                btn_cancel.setAlpha(1);
                btn_continue.startAnimation(fade);//drink_layout seçildikten sonra btn_continue animasyonla beliriyor.
                btn_cancel.startAnimation(fade); // animasyon

                selectedLayoutListener(donut_layout);

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,TabsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        drink_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drink_layout.setBackground(getDrawable(R.drawable.bg_item_selected));
                btn_continue.setAlpha(1); // default olarak item seçilmeden yukarıda alpha(0) verdiğimiz için alpha(1) yapmazsak gözükmez seçilince de.
                btn_cancel.setAlpha(1);
                btn_continue.startAnimation(fade);//drink_layout seçildikten sonra btn_continue animasyonla beliriyor.
                btn_cancel.startAnimation(fade); // animasyon

                selectedLayoutListener(drink_layout);

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        pizza_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pizza_layout.setBackground(getDrawable(R.drawable.bg_item_selected));
                btn_continue.setAlpha(1);
                btn_cancel.setAlpha(1);
                btn_continue.startAnimation(fade);
                btn_cancel.startAnimation(fade);

                selectedLayoutListener(pizza_layout);

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,RateUsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });



        // Cancel tıklandığında btn_continue kayboluyor yani alpha(0) oluyor.
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drink_layout.setBackground(getDrawable(R.drawable.bg_item));
                btn_continue.setAlpha(0);
                btn_cancel.setAlpha(0);
            }
        });
    }


   private void selectedLayoutListener(LinearLayout selectedLayout){
        if(selectedLayout == drink_layout){
            drink_layout.setBackground(getDrawable(R.drawable.bg_item_selected));
        }else {
            drink_layout.setBackground(getDrawable(R.drawable.btn_bg_cancel));
        }
        if(selectedLayout == pizza_layout){
            pizza_layout.setBackground(getDrawable(R.drawable.bg_item_selected));

        }else{
            pizza_layout.setBackground(getDrawable(R.drawable.btn_bg_cancel));

        }
       if(selectedLayout == donut_layout){
           donut_layout.setBackground(getDrawable(R.drawable.bg_item_selected));

       }else{
           donut_layout.setBackground(getDrawable(R.drawable.btn_bg_cancel));

       }
   }
}