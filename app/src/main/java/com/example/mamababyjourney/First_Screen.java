package com.example.mamababyjourney;

import com.example.mamababyjourney.sign_up_and_sign_in_folder.Sign_Up_Activity;
import com.example.mamababyjourney.sign_up_and_sign_in_folder.Sing_in_Activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.view.WindowManager;
import android.content.Intent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import android.os.Bundle;
import android.view.View;

@SuppressWarnings ( "ConstantConditions" )
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

        if ( FirebaseAuth . getInstance ( ) . getCurrentUser () != null )
            Get_User_Data () ;

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

    private void Get_User_Data ( )
    {
        List < String > collectionNames = Arrays  . asList ( "Doctors" , "Mothers" );

        String documentId = FirebaseAuth  . getInstance ( )  . getCurrentUser ( )  . getEmail ( );

        for ( String collectionName : collectionNames )
        {
            FirebaseFirestore . getInstance ( ) . collection ( collectionName ) . document ( documentId ) . get ( ) . addOnSuccessListener ( documentSnapshot ->
            {
                if ( documentSnapshot . exists ( ) )
                {
                    // Document found in this collection
                    // Handle the document here
                    String collection = documentSnapshot . getReference ( ) . getParent ( ) . getId ( );
                    //Log  .  d ( "Firestore" , "Document found in collection: " + collection ) ;
                    // You can access the document data using documentSnapshot  .  getData() method
                }
            } );
        }
    }
}

