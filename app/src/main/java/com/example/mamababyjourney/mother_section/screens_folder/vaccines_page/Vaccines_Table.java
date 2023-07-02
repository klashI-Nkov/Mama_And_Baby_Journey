package com.example.mamababyjourney.mother_section.screens_folder.vaccines_page;

import com.example.mamababyjourney.databinding.FragmentMotherSectionVaccinesPageVaccinesTableFragmentBinding;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;

public class Vaccines_Table extends Fragment
{
    private FragmentMotherSectionVaccinesPageVaccinesTableFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionVaccinesPageVaccinesTableFragmentBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}