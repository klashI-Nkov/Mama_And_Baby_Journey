package com.example.mamababyjourney.mother_section.screens_folder.kids_page;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mamababyjourney.databinding.FragmentMotherSectionKidsPageChildTreatmentRecordFragmentBinding;

public class Child_Treatment_Record extends Fragment
{

    private FragmentMotherSectionKidsPageChildTreatmentRecordFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionKidsPageChildTreatmentRecordFragmentBinding.inflate ( inflater , container , false );
        return binding.getRoot ( );
    }
}