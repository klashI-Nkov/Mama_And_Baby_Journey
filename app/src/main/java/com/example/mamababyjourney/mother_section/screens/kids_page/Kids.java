package com.example.mamababyjourney.mother_section.screens.kids_page;

import static android.app.Activity.RESULT_OK;

import com.example.mamababyjourney.databinding.FragmentMotherSectionKidsKidsFragmentBinding;
import com.example.mamababyjourney.mother_section.screens.treatments.Treatments_Program_Activity;

import de.hdodenhof.circleimageview.CircleImageView;

import androidx.annotation.Nullable;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import com.example.mamababyjourney.R;

import android.net.Uri;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/** @noinspection DataFlowIssue*/
@SuppressLint ( { "InflateParams" , "IntentReset" } )
@SuppressWarnings ( { "SpellCheckingInspection" , "deprecation" } )
public class Kids extends Fragment
{
    FragmentMotherSectionKidsKidsFragmentBinding binding;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionKidsKidsFragmentBinding.inflate ( inflater , container , false );

        Add_Treatments_Program_Button ( );

        binding.AddChildBTN.setOnClickListener ( view ->
        {
            Intent intent = new Intent ( requireContext ( ) , Add_Child_Activity.class );
            startActivityForResult ( intent , 1 );

        } );

        return binding.getRoot ( );
    }

    private void Add_Treatments_Program_Button ( )
    {
        View childLayout = getLayoutInflater ( ).inflate ( R.layout.layouts_mother_section_treatments_treatments_program_grid_layout_item_layout , null );

        CircleImageView circleImageView = childLayout.findViewById ( R.id.image_view );

        TextView textView = childLayout.findViewById ( R.id.Title );

        GridLayout.LayoutParams params = new GridLayout.LayoutParams ( );


        circleImageView.setImageResource ( R.drawable.treatments_program_pic );
        textView.setText ( "برنامج التطعيم" );

        params.columnSpec = GridLayout.spec ( GridLayout.UNDEFINED , GridLayout.FILL , 1 );
        params.setMargins ( 0 , 0 , 0 , 21 );
        childLayout.setOnClickListener ( view ->
        {
            Intent intent = new Intent ( requireContext ( ) , Treatments_Program_Activity.class );
            startActivityForResult ( intent , 1 );
        } );
        binding.GirdLayout.addView ( childLayout , params );
    }

    public void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super.onActivityResult ( requestCode , resultCode , data );

        if ( resultCode == RESULT_OK && data!=null)
        {
            View childLayout = getLayoutInflater ( ).inflate ( R.layout.layouts_mother_section_treatments_treatments_program_grid_layout_item_layout , null );

            CircleImageView circleImageView = childLayout.findViewById ( R.id.image_view );

            TextView textView = childLayout.findViewById ( R.id.Title );

            GridLayout.LayoutParams params = new GridLayout.LayoutParams ( );
            Uri selectedImageUri = data.getData ( );

            circleImageView.setImageURI ( selectedImageUri );
            textView.setText ( data . getExtras () . getString ( "Name" ) );

            params.columnSpec = GridLayout.spec ( GridLayout.UNDEFINED , GridLayout.FILL , 1 );
            params.setMargins ( 0 , 0 , 0 , 21 );
            binding.GirdLayout.addView ( childLayout , params );
        }
        else
            Toast . makeText ( requireContext () , "null" , Toast.LENGTH_LONG ) ;
    }

}