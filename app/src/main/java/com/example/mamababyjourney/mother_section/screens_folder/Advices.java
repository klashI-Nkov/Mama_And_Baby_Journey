package com.example.mamababyjourney.mother_section.screens_folder;

import com.example.mamababyjourney.databinding.FragmentMotherSectionAdvicesFragmentBinding;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import java.util.Objects;


@SuppressWarnings ( { "deprecation" } )
public class Advices extends Fragment
{

    private FragmentMotherSectionAdvicesFragmentBinding binding ;

    private boolean flag ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionAdvicesFragmentBinding . inflate (inflater ,container ,false ) ;

        Theme ( ) ;

        binding . MothersAdviceBTN . setOnClickListener ( V -> Mothers_Advice_BTN ( ) ) ;

        binding . BabyAdviceBTN . setOnClickListener ( V -> Baby_Advice_BTN ( ) ) ;

        return binding . getRoot ( ) ;
    }

    public void Theme ( )
    {

        Configuration configuration = getResources ( ) . getConfiguration ( ) ;

        int currentUiMode = configuration . uiMode & Configuration . UI_MODE_NIGHT_MASK ;

        if ( currentUiMode == Configuration . UI_MODE_NIGHT_YES )
        {
            flag = true ;
            Color (Color . parseColor ("#3CFFFFFF" ) ,"Mothers_Advice_BTN" ) ;
        }
        else
        {
            flag = false ;
            Color (Color . parseColor ("#140E0E0E" ) ,"Mothers_Advice_BTN" ) ;
        }
    }

    @Override
    public void onConfigurationChanged ( @NonNull Configuration newConfig )
    {
        super . onConfigurationChanged ( newConfig ) ;

        if ( ( getResources ( ) . getConfiguration ( ) . uiMode & Configuration . UI_MODE_NIGHT_MASK ) == Configuration . UI_MODE_NIGHT_YES )
        {
            flag = true ;
            Color (Color . parseColor ("#3CFFFFFF" ) ,"Mothers_Advice_BTN" ) ;
        }
        else
        {
            flag = false ;
            Color (Color . parseColor ("#140E0E0E" ) ,"Mothers_Advice_BTN" ) ;
        }
    }

    private void Mothers_Advice_BTN ( )
    {
        if ( flag )
            Color (Color . parseColor ("#3CFFFFFF" ) ,"Mothers_Advice_BTN" ) ;
        else
            Color (Color . parseColor ("#140E0E0E" ) ,"Mothers_Advice_BTN" ) ;
    }

    private void Baby_Advice_BTN ( )
    {
        if ( flag )
            Color (Color . parseColor ("#3CFFFFFF" ) ,"Baby_Advice_BTN" ) ;
        else
            Color (Color . parseColor ("#140E0E0E" ) ,"Baby_Advice_BTN" ) ;
    }

    private void Color ( int color , String button_Name )
    {
        if (  button_Name . equals ( "Mothers_Advice_BTN"  ) )
        {
            binding . MothersAdviceBTN . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;
            binding . BabyAdviceBTN    . setBackgroundTintList ( ColorStateList . valueOf (getResources ( ) . getColor (android . R . color . transparent ) ) ) ;
        }
        else
        {
            binding . MothersAdviceBTN . setBackgroundTintList ( ColorStateList . valueOf (getResources ( ) . getColor (android . R . color . transparent ) ) ) ;
            binding . BabyAdviceBTN    . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;
        }
    }
}