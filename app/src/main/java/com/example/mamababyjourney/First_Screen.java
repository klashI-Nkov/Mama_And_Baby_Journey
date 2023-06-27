package com.example.mamababyjourney;

import com.example.mamababyjourney.Create_An_Account_And_Sign_In.Sign_Up_Activity;
import com.example.mamababyjourney.Create_An_Account_And_Sign_In.Sing_in_Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.view.WindowManager;
import android.content.Intent;
import java.util.Objects;
import android.os.Bundle;
import android.view.View;

public class First_Screen extends AppCompatActivity
{
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;
        setContentView ( R . layout . activity_first_screen ) ;
        Permissions ( ) ;
    }

    public void Go_to_Sing_In ( View view )
    {
        Intent intent = new Intent ( this , Sing_in_Activity. class ) ;
        startActivity ( intent ) ;
    }

    public void Go_to_Sing_Up ( View view )
    {
        Intent intent = new Intent ( this , Sign_Up_Activity. class ) ;
        startActivity ( intent ) ;
    }

    private void Permissions ( )
    {

        if
        (
            ActivityCompat.checkSelfPermission ( this , android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission ( this , android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat . requestPermissions ( this , new String [ ] { android . Manifest . permission . ACCESS_FINE_LOCATION } , 1 ) ;
            ActivityCompat . requestPermissions ( this , new String [ ] { android . Manifest . permission . ACCESS_COARSE_LOCATION } , 1 ) ;
        }

    }
}

