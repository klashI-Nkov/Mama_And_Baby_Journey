package com.example.mamababyjourney.mother_section.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mamababyjourney.databinding.FragmentMotherSectionAdvicesFragmentBinding;


public class Advices extends Fragment
{

    private FragmentMotherSectionAdvicesFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionAdvicesFragmentBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }

}