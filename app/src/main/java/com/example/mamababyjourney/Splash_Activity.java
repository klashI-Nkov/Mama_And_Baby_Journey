package com.example.mamababyjourney;

import com.example.mamababyjourney.doctor_section.Doctor_Activity;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Handler;
import java.util.Objects;
import android.os.Bundle;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings ( {"ConstantConditions"  , "SpellCheckingInspection" , "RedundantSuppression"} )
public class Splash_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        setContentView ( R . layout . activity_splash ) ;

        new Handler ( ) . postDelayed ( ( ) ->
        {
            if ( FirebaseAuth . getInstance ( ) . getCurrentUser ( ) != null )
             Get_User_Data ( ) ;
            else
            {
                Intent intent = new Intent (Splash_Activity . this ,First_Screen . class ) ;
                intent . putExtra ("action" ,"signing" ) ;
                startActivity (intent ) ;
                finish ( ) ;
            }
        } , 5000 ) ;
    }


    private void Get_User_Data ( )
    {
        List < String > collection_Names = Arrays  . asList ( "Doctors" , "Mothers" );

        for ( String collection_Name : collection_Names )
        {
            FirebaseFirestore . getInstance ( )
            .collection (collection_Name )
            .get ( ) . addOnCompleteListener (task ->
            {
                if ( task . isSuccessful ( ) )
                {
                    if ( ! task . getResult ( ) . isEmpty ( ) )
                    {
                        FirebaseFirestore . getInstance ( )
                        .collection (collection_Name )
                        .document   (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) )
                        .get ( ) . addOnSuccessListener (documentSnapshot ->
                        {
                            if ( documentSnapshot . exists ( ) )
                            {
                                if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيب" ) || documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيبة" ) )
                                {
                                    Intent intent = new Intent ( Splash_Activity . this , Doctor_Activity. class ) ;
                                    startActivity (intent ) ;
                                    finish ( ) ;
                                }

                                if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "أم" ) )
                                {
                                    Intent intent = new Intent (Splash_Activity  . this ,Mother_Activity . class ) ;
                                    intent . putExtra ("action" ,"signing" ) ;
                                    startActivity (intent ) ;
                                    finish ( ) ;
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}