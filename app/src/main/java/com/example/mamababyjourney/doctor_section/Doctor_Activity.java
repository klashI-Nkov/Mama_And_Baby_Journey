package com.example.mamababyjourney.doctor_section;

import com.example.mamababyjourney.databinding.ActivityDoctorSectionDoctorActivityBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.firestore.FirebaseFirestore;
import de.hdodenhof.circleimageview.CircleImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.example.mamababyjourney.Splash_Activity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import android.annotation.SuppressLint;
import androidx.navigation.Navigation;
import com.example.mamababyjourney.R;
import com.squareup.picasso.Picasso;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;


@SuppressWarnings ( {"ConstantConditions" , "SpellCheckingInspection" , "RedundantSuppression" , "UnnecessaryUnicodeEscape" } )
@SuppressLint ( { "InflateParams" , "SetTextI18n" } )

public class Doctor_Activity extends AppCompatActivity
{
    private ActivityDoctorSectionDoctorActivityBinding binding ;

    private AppBarConfiguration mAppBarConfiguration ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityDoctorSectionDoctorActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Get_Doctor_Data ( ) ;

        Mother_section_Initialization ( ) ;
    }


    private void Mother_section_Initialization ( )
    {
        setSupportActionBar ( binding . DoctorActivityAppBar . DoctorToolbar ) ;

        // شايفه هاد الكود الي تحت كله هاد عشان اجهز ال action bar و احدد الشاشات الي اسمائها موجوده في ال Navigation Drawer لحتى بس اضغط على اسمهم ننتقل الهم

        mAppBarConfiguration = new AppBarConfiguration . Builder (R . id . Today_Appointments , R . id . customers  )
        . setOpenableLayout ( binding . DoctorDrawerLayout ) . build ( ) ;

        // هاد الكود الي تحت بجهز الي المتحكم الي وظفيته ينقلني على الشاشة الي بضغط على اسمها في ال Navigation Drawer والكود الي قوق هو الي بجهز ال action bar عشان بس اضغط على اسم وحده من الشاشات الي في ال Navigation Drawer ينعرض اسمها في ال action bar
        NavController navController = Navigation. findNavController (this ,R . id . doctor_activity_nav_host ) ;
        NavigationUI. setupActionBarWithNavController (this ,navController ,mAppBarConfiguration ) ;
        NavigationUI . setupWithNavController ( binding . DoctorNavView ,navController ) ;
    }

    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation . findNavController (this ,R . id . doctor_activity_nav_host ) ;
        return NavigationUI . navigateUp (navController ,mAppBarConfiguration ) || super . onSupportNavigateUp ( ) ;
    }

    private void Get_Doctor_Data ( )
    {
        View childLayout = getLayoutInflater ( ) . inflate (R . layout . layouts_mother_section_mother_activity_nav_header_layout ,null ) ;

        CircleImageView user_Profile_Image = childLayout . findViewById (R . id . User_Profile_Image ) ;

        TextView User_Email_Tv = childLayout . findViewById (R . id . User_Email_Tv ) ;
        TextView username_Tv   = childLayout . findViewById (R . id . Username_Tv ) ;

        FirebaseFirestore. getInstance ( )
        .collection ("Doctors" )
        .document   (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) )
        .get ( ) . addOnCompleteListener (task ->
        {
            if ( task . isSuccessful ( ) )
            {
                if ( task . getResult ( ) . exists ( ) )
                {
                    username_Tv   . setText ( "الاسم\u0020 : \u0020" + task . getResult ( ) . get ( "name" ) ) ;
                    User_Email_Tv . setText ( task . getResult ( ) . get ( "email" ) + "" ) ;

                    String image_url = task . getResult ( ).get ( "image Url" ) + "" ;

                    if ( image_url . isEmpty ( ) || image_url . equals ( null )  )
                    {
                        if ( task . getResult ( ) . get ( "signUp Method" ) . toString ( ) . equals ( "Sing Up By Google" ) )

                             Picasso . get ( ) . load (FirebaseAuth . getInstance ( ) . getCurrentUser ( )
                            .getPhotoUrl ( ) ) . into (user_Profile_Image ) ;


                        else if ( task . getResult ( ) . get ( "صفة المستخدم" ) . equals ( "'طبيب" ) )
                                user_Profile_Image . setImageResource ( R . drawable . images_male_user_image ) ;
                            else
                                user_Profile_Image . setImageResource ( R . drawable . images_female_user_image ) ;
                    }
                    else
                        FirebaseStorage. getInstance ( ) . getReferenceFromUrl (image_url ) . getDownloadUrl ( )
                        .addOnSuccessListener (uri -> Picasso . get ( ) . load ( uri ) . into (user_Profile_Image ) ) ;

                    binding . DoctorNavView . addHeaderView (childLayout ) ;
                }
            }
        });
    }

    @Override
    public void onBackPressed ( )
    {

    }

    public void Doctor_Sign_Out_BTN ( MenuItem menuItem )
    {
        FirebaseAuth. getInstance ( ) . signOut ( ) ;

        GoogleSignInOptions gso = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestEmail ( ) . build ( ) ;

        GoogleSignInClient mGoogleApiClient = GoogleSignIn. getClient(this,gso) ;
        mGoogleApiClient . signOut (  ) ;

        Intent intent = new Intent (this , Splash_Activity. class ) ;
        startActivity (intent ) ;

    }

}