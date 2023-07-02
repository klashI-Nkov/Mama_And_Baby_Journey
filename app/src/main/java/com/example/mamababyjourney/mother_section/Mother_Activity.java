package com.example.mamababyjourney.mother_section;

import com.example.mamababyjourney.databinding.ActivityMotherSectionMotherActivityBinding;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.mamababyjourney.R;
import android.view.WindowManager;
import android.os.Bundle;

@SuppressWarnings ( { "DataFlowIssue" , "SpellCheckingInspection" , "RedundantSuppression" } )
public class Mother_Activity extends AppCompatActivity
{
    // هاد المتغير انا مستعمله عشان اعمل action bar مخصص للشاشات بدل ال action bar الي بحطه النظام
    private AppBarConfiguration mAppBarConfiguration ;
    ActivityMotherSectionMotherActivityBinding binding ;

    // هاد بتخزن فيه ال id الي بجيني من شاشة ال sign up
    public static String id ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;
        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityMotherSectionMotherActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        id = "1";

        setSupportActionBar ( binding . MotherActivityAppBar . toolbar ) ;

        // شايفه هاد الكود الي تحت كله هاد عشان اجهز ال action bar و احدد الشاشات الي اسمائها موجوده في ال Navigation Drawer لحتى بس اضغط على اسمهم ننتقل الهم

        mAppBarConfiguration = new AppBarConfiguration . Builder
        (R . id . Mother , R . id . Doctors_And_Clinics , R . id . Advices , R . id . Dates , R . id . Kids )
        . setOpenableLayout ( binding . drawerLayout ) . build ( ) ;

        // هاد الكود الي تحت بجهز الي المتحكم الي وظفيته ينقلني على الشاشة الي بضغط على اسمها في ال Navigation Drawer والكود الي قوق هو الي بجهز ال action bar عشان بس اضغط على اسم وحده من الشاشات الي في ال Navigation Drawer ينعرض اسمها في ال action bar
        NavController navController = Navigation . findNavController (this ,R . id .mother_activity_nav_host ) ;
        NavigationUI . setupActionBarWithNavController (this ,navController ,mAppBarConfiguration ) ;
        NavigationUI . setupWithNavController (binding . navView ,navController ) ;
    }


    // هاد الفنكشن حاولت افهم من chat GPT متى بتنفذ و شو بعمل الكود الي جواته ما فهمت و لا عرفت لهيك انسيكي منه
    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation . findNavController (this ,R . id .mother_activity_nav_host ) ;
        return NavigationUI . navigateUp (navController ,mAppBarConfiguration ) || super  .onSupportNavigateUp ( ) ;
    }
}