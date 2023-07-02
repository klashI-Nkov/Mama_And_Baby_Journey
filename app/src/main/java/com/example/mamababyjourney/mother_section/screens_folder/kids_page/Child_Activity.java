package com.example.mamababyjourney.mother_section.screens_folder.kids_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.ActivityMotherSectionKidsPageChildActivityBinding;

import java.util.Objects;

public class Child_Activity extends AppCompatActivity
{
    ActivityMotherSectionKidsPageChildActivityBinding binding ;
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );

        getWindow ( ) . setFlags (WindowManager. LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects. requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityMotherSectionKidsPageChildActivityBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration
        .Builder ( R.id.Treatments_Table , R.id.Child_Treatment_Record ).build ( );

        NavController navController = Navigation.findNavController ( this , R.id.child_activity_nav_host_fragment );
        NavigationUI.setupActionBarWithNavController ( this , navController , mAppBarConfiguration );
        NavigationUI.setupWithNavController ( binding.navView , navController );


    }
}