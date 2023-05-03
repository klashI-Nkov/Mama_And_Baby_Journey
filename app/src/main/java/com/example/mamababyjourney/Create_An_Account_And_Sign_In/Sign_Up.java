package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.databinding.ActivitySignUpBinding;
import androidx.appcompat.app.AppCompatActivity;
import android . view    . WindowManager;
import java    . util    . Objects;
import android . content . Intent;
import android . os      . Bundle;
import android . view    . View;

public class Sign_Up extends AppCompatActivity
{

    ActivitySignUpBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow ( ) . setFlags ( WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS , WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects. requireNonNull ( getSupportActionBar ( ) ) . hide ( ) ;

        super . onCreate ( savedInstanceState ) ;
        binding = ActivitySignUpBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;
    }

    public void Go_to_Info ( View view )
    {
        Intent intent = new Intent ( this , Info_Activity . class ) ;
        startActivity ( intent ) ;
    }

}