package com.example.mamababyjourney.mother_section.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mamababyjourney.databinding.FragmentMotherSectionDatesFragmentBinding;


public class Dates extends Fragment
{
    private FragmentMotherSectionDatesFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionDatesFragmentBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}