package com.example.mamababyjourney.mother_section;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;

import com.example.mamababyjourney.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mamababyjourney.databinding.ActivityMotherBinding;

import java.util.Objects;

public class Mother_Activity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMotherBinding binding;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        getWindow ( ) . setFlags( WindowManager. LayoutParams . FLAG_LAYOUT_NO_LIMITS , WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS) ;

        binding = ActivityMotherBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );

        setSupportActionBar ( binding.appBarMother.toolbar ) ;

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration
        .Builder ( R.id.nav_home , R.id.nav_gallery , R.id.nav_slideshow ).setOpenableLayout ( drawer ).build ( );
        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_content_mother );
        NavigationUI.setupActionBarWithNavController ( this , navController , mAppBarConfiguration );
        NavigationUI.setupWithNavController ( navigationView , navController );
    }

    // هاد الفنكشن عمله عشان اغير لون التكست لل Radio buttons حسب الثيم لانه حاولت اطبق عليهم ستايل يغيير لون الخط حسب الثيم ما زبط فانجبرت استعمل هاي الطريقه
    @Override
    public void onConfigurationChanged ( @NonNull Configuration newConfig )
    {
        super . onConfigurationChanged ( newConfig ) ;

        // الشرط الي جوا الاف معناه انه روح جيب الثيم الحالي و شوف اذا هو دارك ادخل و نفذ الي جوا الاف واعطي الخط تاع ال Radio buttons الوان الثيم الدارك اذا الثيم الحالي مش دارك اعطيهم الوان الثيم الفاتح
        if ( ( getResources ( ) . getConfiguration ( ) . uiMode & Configuration . UI_MODE_NIGHT_MASK ) == Configuration . UI_MODE_NIGHT_YES )
        {
            binding . navView . setBackgroundColor ( getResources ( ) . getColor (R . color . Drawer_Background_Color_N ) );
        }
        else
        {
            binding . navView . setBackgroundColor ( getResources ( ) . getColor (R . color . Drawer_Background_Color_L ) );
        }
    }

    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation.findNavController ( this , R.id.nav_host_fragment_content_mother );
        return NavigationUI.navigateUp ( navController , mAppBarConfiguration ) || super.onSupportNavigateUp ( );
    }
}