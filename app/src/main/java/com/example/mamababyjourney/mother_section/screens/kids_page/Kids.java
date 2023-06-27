package com.example.mamababyjourney.mother_section.screens.kids_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mamababyjourney.databinding.FragmentMotherSectionKidsKidsFragmentBinding;


public class Kids extends Fragment
{
    private FragmentMotherSectionKidsKidsFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionKidsKidsFragmentBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}