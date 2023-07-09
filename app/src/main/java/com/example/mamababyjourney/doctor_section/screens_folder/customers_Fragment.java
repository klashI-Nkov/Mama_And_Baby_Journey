package com.example.mamababyjourney.doctor_section.screens_folder;

import com.example.mamababyjourney.databinding.FragmentDoctorSectionCustomersFragmentBinding;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

public class customers_Fragment extends Fragment
{
    private FragmentDoctorSectionCustomersFragmentBinding binding ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentDoctorSectionCustomersFragmentBinding . inflate (inflater ,container ,false ) ;
        return binding . getRoot ( ) ;
    }
}