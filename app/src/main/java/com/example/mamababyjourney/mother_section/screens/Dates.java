package com.example.mamababyjourney.mother_section.screens;

import com.example.mamababyjourney.databinding.FragmentDatesBinding;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;


public class Dates extends Fragment
{

    private FragmentDatesBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentDatesBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}