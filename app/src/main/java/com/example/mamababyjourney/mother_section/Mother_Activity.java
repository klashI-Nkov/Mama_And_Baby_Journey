package com.example.mamababyjourney.mother_section;

import com.example.mamababyjourney.databinding.ActivityMotherBinding;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import com.example.mamababyjourney.R;
import android.view.WindowManager;
import android.os.Bundle;

public class Mother_Activity extends AppCompatActivity
{
    private ActivityMotherBinding binding;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityMotherBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding.getRoot ( ) );

        setSupportActionBar( binding . toolbar ) ;

        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration
        .Builder ( R.id.nav_home , R.id.nav_gallery , R.id.f ).build ( );

        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_activity_main );
        NavigationUI.setupActionBarWithNavController ( this , navController , mAppBarConfiguration );
        NavigationUI.setupWithNavController ( binding.navView , navController );
    }

}