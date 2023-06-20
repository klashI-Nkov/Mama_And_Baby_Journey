package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.databinding.ActivitySingInBinding;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import java.util.Objects;
import android.os.Bundle;

public class Sing_in_Activity extends AppCompatActivity
{
    ActivitySingInBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags ( WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS , WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySingInBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;
    }
}