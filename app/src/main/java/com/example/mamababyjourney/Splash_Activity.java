package com.example.mamababyjourney;

import com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page.Doctor_Data_Activity;
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

@SuppressWarnings ( "ConstantConditions" )
public class Splash_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        setContentView ( R . layout . activity_splash ) ;

        // هون انا بحدد مدة شاشة الترحيب
        new Handler ( ) . postDelayed ( ( ) ->
        {
            /*
                هون بشيك اذا في مستخدم عامل تسجيل دخول من قبل وقتها بستدعي الفنشكن الي بجيب الي داتا المستخدم

                اذا ما في مستخدم عامل تسجيل دخول من قبل بروح على الشاشه الي فيها ازرار انشاء الحساب و تسجيل الدخول
            */
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


    // هاد الفنكشن الي بجيب داتا المستخدم بس يفتح التطبيق
    private void Get_User_Data ( )
    {
        // هون عندي list مخزن فيها اسماء ال collections الي فيها بيانات الدكاتره و الامهات عشان باستعمال ال for each الي تحت الف عليهم لحتى ادور على الدكيومنت تبع المستخدم
        List < String > collection_Names = Arrays  . asList ( "Doctors" , "Mothers" );

        for ( String collection_Name : collection_Names )
        {
            // هون اول شي بقله دور الي على ال collection الي فيها ال document تبع المستخدم
            FirebaseFirestore . getInstance ( )
            .collection (collection_Name )
            .get ( ) . addOnCompleteListener (task ->
            {
                if ( task . isSuccessful ( ) )
                {
                    if ( !task . getResult ( ) . isEmpty ( ) )
                    {
                        // هون اذا كانت ال collection الي بدور عليها موجوده بقله خش ابحث عن document المستخدم فيها
                        FirebaseFirestore . getInstance ( )
                        .collection (collection_Name )
                        .document   (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) )
                        .get ( ) . addOnSuccessListener (documentSnapshot ->
                        {
                            if ( documentSnapshot . exists ( ) )
                            {
                                // هون اذا كان المستخدم طبيب او طبيبه بخلي التطبيق يوديه على قسم الدكتور
                                if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيب" ) || documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيبة" ) )
                                {
                                    // هون بوجهو لشاشة الدكتور
                                    Intent intent = new Intent ( Splash_Activity . this ,Doctor_Data_Activity . class ) ;

                                    // هون بفهم شاشة الدكتور اني جاي من شاشة التطبيق الرئيسيه
                                    intent . putExtra ("action" ,"signing" ) ;

                                    // هون بنتقل لشاشة الدكتور
                                    startActivity (intent ) ;
                                    finish ( ) ;
                                }

                                // هون اذا كان المستخدم طبيب او طبيبه بخلي التطبيق يوديه على قسم الام
                                if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "أم" ) )
                                {
                                    // هون بوجهو لشاشة الام
                                    Intent intent = new Intent (Splash_Activity  . this ,Mother_Activity . class ) ;

                                    // هون بفهم شاشة الدكتور اني جاي من شاشة التطبيق الرئيسيه
                                    intent . putExtra ("action" ,"signing" ) ;

                                    // هون بنتقل لشاشة الام
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