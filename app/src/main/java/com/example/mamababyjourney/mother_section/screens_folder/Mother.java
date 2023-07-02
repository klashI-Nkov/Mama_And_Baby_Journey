package com.example.mamababyjourney.mother_section.screens_folder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mamababyjourney.databinding.FragmentMotherSectionMotherFragmentBinding;

public class Mother extends Fragment
{

    private FragmentMotherSectionMotherFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionMotherFragmentBinding . inflate (inflater ,container,false ) ;
        return binding . getRoot ( ) ;
    }
}