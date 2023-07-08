package com.example.mamababyjourney.mother_section.screens_folder;

import com.example.mamababyjourney.databinding.FragmentMotherSectionDatesFragmentBinding;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;


public class Dates extends Fragment
{
    private FragmentMotherSectionDatesFragmentBinding binding ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionDatesFragmentBinding . inflate (inflater ,container ,false ) ;
        return binding . getRoot ( ) ;
    }
}