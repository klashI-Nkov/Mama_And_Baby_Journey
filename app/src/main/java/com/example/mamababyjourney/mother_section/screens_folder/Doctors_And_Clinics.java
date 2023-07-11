package com.example.mamababyjourney.mother_section.screens_folder;

import com.example.mamababyjourney.Splash_Activity;
import com.example.mamababyjourney.classes.Recycler_View_Adapter;
import com.example.mamababyjourney.classes.Recycler_View_Class;
import com.example.mamababyjourney.databinding.FragmentMotherSectionDoctorsAndClinicsFragmentBinding;
import com.example.mamababyjourney.doctor_section.Doctor_Activity;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class Doctors_And_Clinics extends Fragment
{
    Recycler_View_Adapter adapter ;
    private FragmentMotherSectionDoctorsAndClinicsFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionDoctorsAndClinicsFragmentBinding . inflate (inflater ,container ,false ) ;


        String [ ] advice_title   = {""};
        String [ ] advice_content = {""};

        List < String > doc_Names = Arrays. asList ( "Doctors" , "clinics" );

        for ( String name : doc_Names )
        {
            // هون اذا كانت ال collection الي بدور عليها موجوده بقله خش ابحث عن document المستخدم فيها
            FirebaseFirestore . getInstance ( )
            .collection ("Doctors And clinics" )
            .document   ( name )
            .get ( ) . addOnSuccessListener (documentSnapshot ->
            {
                if ( documentSnapshot . exists ( ) )
                {
                    advice_content [0] = "الاسم : " + documentSnapshot.get("workPlace_Name");

                    Recycler_View_Class. recycler_View_Class_Object_List .
                            add ( new Recycler_View_Class (advice_title [ 0 ] ,advice_content [ 0 ] ) ) ;

                    adapter = new Recycler_View_Adapter
                            ( Recycler_View_Class. recycler_View_Class_Object_List ,requireContext ( ) ) ;

                    binding . DoctorsAndClinicsRecyclerView . setAdapter ( adapter ) ;
                    binding . DoctorsAndClinicsRecyclerView . setLayoutManager ( new LinearLayoutManager (requireContext ( ) ) ) ;
                }
            });
        }


        return binding . getRoot ( ) ;
    }
}