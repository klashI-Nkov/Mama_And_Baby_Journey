package com.example.mamababyjourney.doctor_section;

import com.example.mamababyjourney.databinding.ActivityDoctorSectionDoctorActivityBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.example.mamababyjourney.Splash_Activity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.mamababyjourney.R;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class Doctor_Activity extends AppCompatActivity
{
    private ActivityDoctorSectionDoctorActivityBinding binding ;

    // هاد المتغير انا مستعمله عشان اعمل action bar مخصص للشاشات بدل ال action bar الي بحطه النظام
    private AppBarConfiguration mAppBarConfiguration ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );


        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityDoctorSectionDoctorActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) );

        Mother_section_Initialization ( ) ;
    }


    // هاد الفنكشن وظفيته يجهز الي الشاشة عشان اعرض فيها ال fragment الخاصين بقسم الام
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

        Menu menu = binding . DoctorNavView . getMenu ( ) ;
        menu . findItem (R . id . Doctor_Sign_Out_BTN ) . setOnMenuItemClickListener ( menuItem ->
        {
            FirebaseAuth. getInstance ( ) . signOut ( ) ;

            GoogleSignInOptions gso = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestEmail ( ) . build ( ) ;

            GoogleSignInClient mGoogleApiClient = GoogleSignIn. getClient(this,gso) ;
            mGoogleApiClient . signOut (  ) ;

            Intent intent = new Intent (this , Splash_Activity. class ) ;
            startActivity (intent ) ;
            return true ;
        });
    }

    // هاد الفنكشن حاولت افهم من chat GPT متى بتنفذ و شو بعمل الكود الي جواته ما فهمت و لا عرفت لهيك انسيكي منه
    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation . findNavController (this ,R . id . doctor_activity_nav_host ) ;
        return NavigationUI . navigateUp (navController ,mAppBarConfiguration ) || super . onSupportNavigateUp ( ) ;
    }

}