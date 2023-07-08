package com.example.mamababyjourney.mother_section.screens_folder.vaccines_page;

import com.example.mamababyjourney.databinding.ActivityMotherSectionVaccinesPageVaccinesProgramActivityBinding;
import com.example.mamababyjourney.classes.Recycler_View_Adapter;
import com.example.mamababyjourney.classes.Recycler_View_Class;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mamababyjourney.R;
import android.view.WindowManager;
import java.util.Objects;
import android.os.Bundle;

public class Vaccines_Program_Activity extends AppCompatActivity
{
    ActivityMotherSectionVaccinesPageVaccinesProgramActivityBinding binding ;

    Recycler_View_Adapter adapter ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects. requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityMotherSectionVaccinesPageVaccinesProgramActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Recycler_View_Initialization ( ) ;

        adapter = new Recycler_View_Adapter (Recycler_View_Class . recycler_View_Class_Object_List ,this ) ;

        binding . RecyclerView . setAdapter ( adapter ) ;
        binding . RecyclerView . setLayoutManager ( new LinearLayoutManager (this ) ) ;
    }

    // هاد الفنكشن وظيفته انه يجيب الي كل مطعوم مع العمر تبعه و يحطهم في كارد لحالهم
    private void Recycler_View_Initialization ( )
    {
        String [ ] treatments = getResources ( ) . getStringArray ( R . array . treatments ) ;
        String [ ] age        = getResources ( ) . getStringArray ( R . array . age        ) ;

        for ( int i = 0  ; i < treatments . length  ; i++ )
        {
            Recycler_View_Class . recycler_View_Class_Object_List . add ( new Recycler_View_Class (age [ i ] , treatments [ i ] ) ) ;
        }
    }

    @Override
    protected void onPause ( )
    {
        super.onPause ( );
        adapter .ClearData ( ) ;
    }
}
