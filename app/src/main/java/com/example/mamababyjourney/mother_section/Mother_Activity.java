package com.example.mamababyjourney.mother_section;

import android.os.Bundle;
import android.view.WindowManager;
import com.example.mamababyjourney.R;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mamababyjourney.databinding.ActivityMotherBinding;


public class Mother_Activity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMotherBinding binding;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityMotherBinding . inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );
        Mother_Activity_Initialization ( ) ;
    }

    private void Mother_Activity_Initialization ( )
    {
        setSupportActionBar ( binding.appBarMother.toolbar ) ;

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration
        .Builder ( R.id.nav_home , R.id.nav_gallery ).setOpenableLayout ( drawer ).build ( );



        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_content_mother );
        NavigationUI.setupActionBarWithNavController ( this , navController , mAppBarConfiguration );
        NavigationUI.setupWithNavController ( navigationView , navController );
    }

    @Override
    protected void onResume ( )
    {
        super . onResume ( ) ;
        Mother_Activity_Initialization ( ) ;
    }

}