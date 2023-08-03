package com.ing.custommaterials;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UpdateDialog extends Dialog {

    private final List<String> updateDetailsList= new ArrayList<>();

    public UpdateDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_dialog_layout);

        final RecyclerView updateDetails = findViewById(R.id.updateDetailsRV);
        final AppCompatButton updateNowBtn = findViewById(R.id.updateNowBtn);
        final TextView version = findViewById(R.id.updateVersionTxt);

        //setting update version to the textview
        version.setText("V2.10");

        //configure recyclerview
        updateDetails.setHasFixedSize(true);
        updateDetails.setLayoutManager(new LinearLayoutManager(getContext()));

        //add your own update details one by one
        updateDetailsList.add("1. Bug Fixed");
        updateDetailsList.add("2. Design Improvoments");
        updateDetailsList.add("3. New Functionalities");
        updateDetailsList.add("4. Fast Loading Speed");

        //set adapter to recyclerview
        updateDetails.setAdapter(new UpdateDetailsAdapter(updateDetailsList));

        updateNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
