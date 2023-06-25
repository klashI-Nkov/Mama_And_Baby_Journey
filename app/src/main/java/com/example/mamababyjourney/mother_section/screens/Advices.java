package com.example.mamababyjourney.mother_section.screens;

import com.example.mamababyjourney.databinding.FragmentAdvicesBinding;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;


public class Advices extends Fragment
{

    private FragmentAdvicesBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentAdvicesBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }

}