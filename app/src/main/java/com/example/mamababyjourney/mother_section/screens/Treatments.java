package com.example.mamababyjourney.mother_section.screens;

import com.example.mamababyjourney.databinding.FragmentTreatmentsBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

public class Treatments extends Fragment
{

    private FragmentTreatmentsBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentTreatmentsBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}