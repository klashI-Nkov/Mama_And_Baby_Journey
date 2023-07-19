package com.example.mamababyjourney.mother_section;

import com.example.mamababyjourney.databinding.ActivityMotherSectionMotherActivityBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.firestore.FirebaseFirestore;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.mamababyjourney.Splash_Activity;
import com.google.firebase.storage.FirebaseStorage;
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

@SuppressWarnings ( { "DataFlowIssue" , "SpellCheckingInspection" , "RedundantSuppression" , "ConstantConditions" , "deprecation" , "UnnecessaryUnicodeEscape"  } )
@SuppressLint ( { "InflateParams" , "SetTextI18n" } )
public class Mother_Activity extends AppCompatActivity
{
    private AppBarConfiguration mAppBarConfiguration ;

    private ActivityMotherSectionMotherActivityBinding binding ;

    String image_url  ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityMotherSectionMotherActivityBinding . inflate (getLayoutInflater ( ) ) ;

        setContentView ( binding . getRoot ( ) ) ;

        Mother_section_Initialization ( ) ;

        Get_Mother_Data ( ) ;
    }

    private void Mother_section_Initialization ( )
    {

        setSupportActionBar ( binding . MotherActivityAppBar . MotherToolbar ) ;

        // شايفه هاد الكود الي تحت كله هاد عشان اجهز ال action bar و احدد الشاشات الي اسمائها موجوده في ال Navigation Drawer لحتى بس اضغط على اسمهم ننتقل الهم

        mAppBarConfiguration = new AppBarConfiguration .
        Builder (R . id . Mother , R . id . Doctors_And_Clinics , R . id . Advices , R . id . Dates , R . id . Kids )
        . setOpenableLayout ( binding . MotherDrawerLayout ) . build ( ) ;

        // هاد الكود الي تحت بجهز الي المتحكم الي وظفيته ينقلني على الشاشة الي بضغط على اسمها في ال Navigation Drawer والكود الي قوق هو الي بجهز ال action bar عشان بس اضغط على اسم وحده من الشاشات الي في ال Navigation Drawer ينعرض اسمها في ال action bar
        NavController navController = Navigation . findNavController (this ,R . id . mother_activity_nav_host ) ;
        NavigationUI . setupActionBarWithNavController (this ,navController ,mAppBarConfiguration ) ;
        NavigationUI . setupWithNavController (binding . MotherNavView ,navController ) ;
    }

    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation . findNavController (this ,R . id . mother_activity_nav_host ) ;
        return NavigationUI . navigateUp (navController ,mAppBarConfiguration ) || super . onSupportNavigateUp ( ) ;
    }

    private void Get_Mother_Data ( )
    {
        View childLayout = getLayoutInflater ( ) . inflate (R . layout . layouts_mother_section_mother_activity_nav_header_layout ,null ) ;

        CircleImageView user_Profile_Image = childLayout . findViewById (R . id . User_Profile_Image ) ;

        TextView User_Email_Tv = childLayout . findViewById (R . id . User_Email_Tv ) ;
        TextView username_Tv   = childLayout . findViewById (R . id . Username_Tv ) ;

        if ( getIntent ( ) . getExtras ( ) . getString ("action" ) . equals ( "signup" ) )
        {
            username_Tv    . setText ( "الاسم\u0020 : \u0020" + getIntent ( ) . getExtras ( ) . getString ("name" ) ) ;
            User_Email_Tv  . setText ( getIntent ( ) . getExtras ( ) . getString ("email" ) ) ;

            if ( getIntent ( ) . getExtras ( ) . getString ("image form ?" ) . equals ( "uri" ) )
                Picasso . get ( ) . load (getIntent ( ) . getData ( ) ) . into (user_Profile_Image );
            else
                user_Profile_Image.setImageResource ( R . drawable . images_female_user_image ) ;

            binding . MotherNavView . addHeaderView (childLayout ) ;
        }
        else
        {
            FirebaseFirestore . getInstance ( )
            .collection ("Mothers" )
            .document   (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) )
            .get ( ) . addOnCompleteListener (task ->
            {
                if ( task . isSuccessful ( ) )
                {

                    if ( task . getResult ( ) . exists ( ) )
                    {
                        username_Tv   . setText ( "الاسم\u0020 : \u0020" + task . getResult ( ) . get ( "name" ) ) ;
                        User_Email_Tv . setText ( task . getResult ( ) . get ( "email" ) + "" ) ;

                        image_url = task . getResult ( ) . get ( "image Url" ) + "" ;

                        if ( image_url . isEmpty ( ) || image_url . equals ( null )  )
                        {
                            if ( task . getResult ( ) . get ( "signUp Method" ) . toString ( ) . equals ( "Sing Up By Google" ) )

                                 Picasso . get ( ) . load (FirebaseAuth . getInstance ( ) . getCurrentUser ( )
                                .getPhotoUrl ( ) ) . into (user_Profile_Image ) ;

                            else
                                user_Profile_Image . setImageResource ( R . drawable . images_female_user_image ) ;
                        }
                        else
                            FirebaseStorage . getInstance ( ) . getReferenceFromUrl (image_url ) . getDownloadUrl ( )
                            .addOnSuccessListener (uri -> Picasso . get ( ) . load ( uri ) . into (user_Profile_Image ) ) ;

                        binding . MotherNavView . addHeaderView (childLayout ) ;
                    }
                }
            });
        }
    }

    public void Mother_Sign_Out_BTN ( MenuItem menuItem)
    {
        FirebaseAuth . getInstance ( ) . signOut ( ) ;

        GoogleSignInOptions gso = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestEmail ( ) . build ( ) ;

        GoogleSignInClient mGoogleApiClient = GoogleSignIn . getClient(this,gso) ;
        mGoogleApiClient . signOut (  ) ;

        Intent intent = new Intent (this ,Splash_Activity . class ) ;
        startActivity (intent ) ;
    }

    @Override
    public void onBackPressed ( )
    {

    }
}