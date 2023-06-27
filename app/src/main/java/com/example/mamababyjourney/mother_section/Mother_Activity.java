package com.example.mamababyjourney.mother_section;

import com.example.mamababyjourney.databinding.ActivityMotherSectionMotherActivityBinding;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.mamababyjourney.R;
import android.view.WindowManager;
import android.os.Bundle;


public class Mother_Activity extends AppCompatActivity
{
    private AppBarConfiguration mAppBarConfiguration;
     ActivityMotherSectionMotherActivityBinding binding;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;
        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityMotherSectionMotherActivityBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );

        setSupportActionBar ( binding.appBarMother.toolbar ) ;

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration
        .Builder ( R.id.Mother , R.id.Doctors_And_Clinics , R.id.Advices , R.id.Dates , R.id.Kids ).setOpenableLayout ( drawer ).build ( );
        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_content_mother );
        NavigationUI.setupActionBarWithNavController ( this , navController , mAppBarConfiguration );
        NavigationUI.setupWithNavController ( navigationView , navController );
    }

    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_content_mother );
        return NavigationUI.navigateUp ( navController , mAppBarConfiguration ) || super.onSupportNavigateUp ( );
    }
}