package com.example.mamababyjourney.doctor_section.screens_folder;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.classes.Recycler_View_Adapter;
import com.example.mamababyjourney.classes.Recycler_View_Class;
import com.example.mamababyjourney.databinding.FragmentDoctorSectionTodayAppointmentsFragmentBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

public class Today_Appointments extends Fragment
{
    private FragmentDoctorSectionTodayAppointmentsFragmentBinding binding ;

    Recycler_View_Adapter adapter ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentDoctorSectionTodayAppointmentsFragmentBinding . inflate (inflater ,container ,false ) ;
        Add_Sub_Advice ( ) ;
        return binding . getRoot ( ) ;
    }

        // هاد الفنكشن الي بعرض الي النصائح الي بكبس على الزر تبعها
    private void Add_Sub_Advice ( )
    {
        String [ ] advice_title   = getResources ( ) . getStringArray ( R. array . prepare_yourself_for_breastfeeding_titles  ) ;
        String [ ] advice_content = getResources ( ) . getStringArray ( R . array . prepare_yourself_for_breastfeeding_content ) ;


        for ( int i = 0  ; i < advice_title . length  ; i++ )
        {
            Recycler_View_Class. recycler_View_Class_Object_List .
                    add ( new Recycler_View_Class (advice_title [ i ] ,advice_content [ i ] ) ) ;
        }

        adapter = new Recycler_View_Adapter
                ( Recycler_View_Class. recycler_View_Class_Object_List ,requireContext ( ) ) ;

        binding . TodayAppointmentsRecyclerView . setAdapter ( adapter ) ;
        binding . TodayAppointmentsRecyclerView . setLayoutManager ( new LinearLayoutManager (requireContext ( ) ) ) ;
    }

    @Override
    public void onPause ( )
    {
        super.onPause ( );
        if ( adapter != null )
            adapter . ClearData ( ) ;
    }
}