package com.example.mamababyjourney.doctor_section.screens_folder;

import com.example.mamababyjourney.databinding.FragmentDoctorSectionTodaysAppointmentsFragmentBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

public class Today_Appointments extends Fragment
{
    private FragmentDoctorSectionTodaysAppointmentsFragmentBinding binding ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentDoctorSectionTodaysAppointmentsFragmentBinding . inflate (inflater ,container ,false ) ;
        return binding . getRoot ( ) ;
    }
}