package com.example.mamababyjourney.doctor_section.screens_folder;

import com.example.mamababyjourney.databinding.ActivityMotherSectionKidsPageAddChildActivityBinding;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

public class Add_New_Customer_Activity extends AppCompatActivity
{
    private ActivityMotherSectionKidsPageAddChildActivityBinding binding ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = ActivityMotherSectionKidsPageAddChildActivityBinding . inflate (inflater ,container ,false ) ;
        return binding . getRoot ( ) ;
    }
}