package com.example.mamababyjourney.mother_section.screens_folder;

import com.example.mamababyjourney.databinding.FragmentMotherSectionDoctorsAndClinicsFragmentBinding;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

public class Doctors_And_Clinics extends Fragment
{
    private FragmentMotherSectionDoctorsAndClinicsFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionDoctorsAndClinicsFragmentBinding . inflate (inflater ,container ,false ) ;
        return binding . getRoot ( ) ;
    }
}