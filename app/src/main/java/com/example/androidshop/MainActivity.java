package com.example.androidshop;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareImageSlider();
    }

    public void logout(View view) {
        firebaseAuth.signOut();
    }

    public void prepareImageSlider() {
        ImageSlider imageSlider = findViewById(R.id.image_slider);
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.banner1, getString(R.string.shoesDiscount), ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner2, getString(R.string.womenDiscount), ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner3, getString(R.string.christmasDiscount), ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModelList);
    }
}