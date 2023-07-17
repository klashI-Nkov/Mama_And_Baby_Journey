package com.example.mamababyjourney.mother_section.screens_folder;

import com.example.mamababyjourney.databinding.FragmentMotherSectionDoctorsAndClinicsFragmentBinding;
import com.example.mamababyjourney.classes.Recycler_View_Adapter;
import com.example.mamababyjourney.classes.Recycler_View_Class;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings ( { "ConstantConditions" , "unchecked" } )
public class Doctors_And_Clinics extends Fragment
{
    Recycler_View_Adapter adapter ;
    private FragmentMotherSectionDoctorsAndClinicsFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionDoctorsAndClinicsFragmentBinding . inflate (inflater ,container ,false ) ;


        String [ ] advice_title   = {""};
        String [ ] advice_content = {""};

        FirebaseFirestore . getInstance ( ) . collection ("Doctors" ) . get ( ) . addOnSuccessListener ( querySnapshot ->
        {
            for ( DocumentSnapshot documentSnapshot : querySnapshot )
            {
                Log . d ( "Test" , documentSnapshot . getId () + "" ) ;
                FirebaseFirestore . getInstance ( ) . collection ("Doctors/" + documentSnapshot . getId ( ) + "/workplaces" ) . get ( ) . addOnSuccessListener ( task ->
                {

                    for ( DocumentSnapshot document : task )
                    {
                        Log . d ( "Test" , document . getId () + "" ) ;

                        advice_content [ 0 ] = "الاسم : " + document.get("workPlace_Name");


                        Recycler_View_Class . recycler_View_Class_Object_List .
                                add ( new Recycler_View_Class (advice_title [ 0 ] ,advice_content [ 0 ] ) ) ;

                        adapter = new Recycler_View_Adapter
                                ( Recycler_View_Class. recycler_View_Class_Object_List ,requireContext ( ) ) ;

                        binding . DoctorsAndClinicsRecyclerView . setAdapter ( adapter ) ;
                        binding . DoctorsAndClinicsRecyclerView . setLayoutManager ( new LinearLayoutManager (requireContext ( ) ) ) ;
                    }
                });
            }
        });

        return binding . getRoot ( ) ;
    }
}