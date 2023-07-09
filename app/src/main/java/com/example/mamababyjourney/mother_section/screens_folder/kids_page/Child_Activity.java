package com.example.mamababyjourney.mother_section.screens_folder.kids_page;

import com.example.mamababyjourney.databinding.ActivityMotherSectionKidsPageChildActivityBinding;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.mamababyjourney.R;
import android.view.WindowManager;
import android.os.Bundle;
import android.view.View;
import java.util.Objects;

@SuppressWarnings ( "FieldCanBeLocal" )

public class Child_Activity extends AppCompatActivity
{
    private ActivityMotherSectionKidsPageChildActivityBinding binding ;
    public static String name ;
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate (savedInstanceState ) ;

        name = getIntent ( ) . getExtras ( ) . getString ("name" ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects. requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityMotherSectionKidsPageChildActivityBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration
        .Builder (R.id.Treatments_Table , R.id.Child_Treatment_Record ) . build ( );

        if ( getIntent ( ) . getExtras ( ) . getString ("gender" ) . equals ( "ذكر" ) )
        {
            binding . FemaleLayout . setVisibility ( View . GONE    ) ;
            binding . MaleLayout   . setVisibility ( View . VISIBLE ) ;
        }
        else
        {
            binding . MaleLayout   . setVisibility ( View . GONE    ) ;
            binding . FemaleLayout . setVisibility ( View . VISIBLE ) ;
        }

        NavController navController = Navigation . findNavController (this ,R . id . child_Activity_nav_host_fragment );
        NavigationUI . setupActionBarWithNavController (this ,navController ,mAppBarConfiguration );
        NavigationUI . setupWithNavController (binding.navView ,navController );

    }
}