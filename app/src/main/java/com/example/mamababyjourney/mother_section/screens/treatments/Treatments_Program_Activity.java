package com.example.mamababyjourney.mother_section.screens.treatments;

import com.example.mamababyjourney.databinding.ActivityMotherSectionTreatmentsTreatmentsProgramActivityBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mamababyjourney.R;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Context;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Objects;
import android.os.Bundle;
import android.view.View;
import java.util.List;

public class Treatments_Program_Activity extends AppCompatActivity
{
    ActivityMotherSectionTreatmentsTreatmentsProgramActivityBinding binding ;

    Recycler_View_Adapter adapter ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects. requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityMotherSectionTreatmentsTreatmentsProgramActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Recycler_View_Initialization ( ) ;

        adapter = new Recycler_View_Adapter ( Recycler_View_Class . recycler_View_Class_Object_List ,this ) ;

        binding . RecyclerView . setAdapter ( adapter ) ;
        binding . RecyclerView . setLayoutManager ( new LinearLayoutManager (this ) ) ;
    }

    private void Recycler_View_Initialization ( )
    {

        String [ ] treatments = getResources ( ) . getStringArray ( R . array . treatments ) ;
        String [ ] age        = getResources ( ) . getStringArray ( R . array . age        ) ;

        for ( int i = 0  ; i < treatments . length  ; i++ )
        {
            Recycler_View_Class . recycler_View_Class_Object_List . add ( new Recycler_View_Class (treatments[ i ] ,age[ i ] ) ) ;
        }
    }

    @Override
    protected void onPause ( )
    {
        super.onPause ( );
        adapter .ClearData ( ) ;
    }
}

@SuppressWarnings ( "unused" )
class Recycler_View_Class
{
    static List < Recycler_View_Class > recycler_View_Class_Object_List = new ArrayList < > ( ) ;

    String age , vaccination ;

    public Recycler_View_Class ( String vaccination , String age )
    {
        this . vaccination = vaccination ;
        this . age         = age         ;
    }

    public String getAge ( ) { return age ; }

    public String getVaccination ( ) { return vaccination ; }
}

class Recycler_View_Adapter extends RecyclerView . Adapter < Recycler_View_Adapter . View_Holder >
{
    List < Recycler_View_Class > recycler_View_Class_Object_List ;
    Context context ;

    public Recycler_View_Adapter ( List < Recycler_View_Class > recycler_View_Class_Object_List , Context context )
    {
        this . recycler_View_Class_Object_List = recycler_View_Class_Object_List ;
        this . context = context ;
    }

    @NonNull
    @Override
    public Recycler_View_Adapter . View_Holder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType )
    {
        LayoutInflater inflater = LayoutInflater . from ( context ) ;
        View view = inflater . inflate (R . layout .layouts_mother_section_treatments_treatments_program_recycler_view_row_layout ,parent ,false ) ;
        return new Recycler_View_Adapter . View_Holder ( view ) ;
    }
    public void ClearData ( )
    {
        recycler_View_Class_Object_List . clear ( ) ;
    }
    @Override
    public void onBindViewHolder ( @NonNull View_Holder holder , int index )
    {
        holder . vaccination_Tv . setText ( recycler_View_Class_Object_List . get ( index ) . vaccination ) ;
        holder . age_Tv         . setText ( recycler_View_Class_Object_List . get ( index ) . age         ) ;
    }

    @Override
    public int getItemCount ( ) { return recycler_View_Class_Object_List . size ( ) ; }

    public static class View_Holder extends RecyclerView . ViewHolder
    {
        TextView vaccination_Tv , age_Tv ;

        public View_Holder ( @NonNull View view )
        {
            super ( view ) ;
            vaccination_Tv = view . findViewById ( R . id . Vaccination ) ;
            age_Tv         = view . findViewById ( R . id . Age         ) ;
        }
    }

}