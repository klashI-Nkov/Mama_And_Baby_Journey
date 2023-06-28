package com.example.mamababyjourney.mother_section.screens.treatments;

import com.example.mamababyjourney.databinding.FragmentMotherSectionTreatmentsTreatmentsTableFragmentBinding;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;

public class Treatments_Table extends Fragment
{
    private FragmentMotherSectionTreatmentsTreatmentsTableFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionTreatmentsTreatmentsTableFragmentBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}