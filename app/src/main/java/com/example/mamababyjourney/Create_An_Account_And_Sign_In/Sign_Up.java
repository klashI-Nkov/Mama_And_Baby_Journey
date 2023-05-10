package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.ActivitySignUpBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
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

    @Override
    public void onConfigurationChanged ( @NonNull Configuration newConfig )
    {
        super.onConfigurationChanged ( newConfig );

        if ( ( getResources ( ).getConfiguration ( ).uiMode & Configuration.UI_MODE_NIGHT_MASK ) == Configuration.UI_MODE_NIGHT_YES )
        {
            binding.DoctorRBTN2.setTextColor ( getResources ( ).getColor ( R.color.R_B_T_C_N ) );
            binding.MomRBTN2.setTextColor ( getResources ( ).getColor ( R.color.R_B_T_C_N ) );
        }
        else
        {
            binding.DoctorRBTN2.setTextColor ( getResources ( ).getColor ( R.color.R_B_T_C_L ) );
            binding.MomRBTN2.setTextColor ( getResources ( ).getColor ( R.color.R_B_T_C_L ) );
        }
    }
}