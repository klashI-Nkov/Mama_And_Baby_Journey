package com.example.mamababyjourney.mother_section.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mamababyjourney.databinding.FragmentMotherSectionDoctorsAndClinicsFragmentBinding;

public class Doctors_And_Clinics extends Fragment
{
    private FragmentMotherSectionDoctorsAndClinicsFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionDoctorsAndClinicsFragmentBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}