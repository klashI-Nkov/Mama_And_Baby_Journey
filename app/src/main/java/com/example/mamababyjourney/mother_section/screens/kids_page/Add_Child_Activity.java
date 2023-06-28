package com.example.mamababyjourney.mother_section.screens.kids_page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.ActivityMotherSectionKidsAddChildActivityBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

@SuppressLint ( { "InflateParams" , "IntentReset" } )
@SuppressWarnings ( { "deprecation" } )
public class Add_Child_Activity extends AppCompatActivity
{
    ActivityMotherSectionKidsAddChildActivityBinding binding;
    Uri selectedImageUri = null ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );

        getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide ( );

        binding = ActivityMotherSectionKidsAddChildActivityBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );

        binding.PickImageBTN.setOnClickListener ( view ->
        {
            Intent intent = new Intent ( Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            intent.setType ( "image/*" );
            startActivityForResult ( intent , 1 );
        } );

    }

    public void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super.onActivityResult ( requestCode , resultCode , data );

        if ( resultCode == RESULT_OK && requestCode == 1 && data != null )
        {
            selectedImageUri = data.getData ( );
            binding.imageView.setImageURI ( selectedImageUri );
        }
    }

    public void Save_BTN ( View view)
    {
        if ( selectedImageUri != null && !binding . NameEditText . getText ().toString ().isEmpty () )
        {
            // Create a URI object representing the desired URI
            //Uri uri = Uri.parse("http://www.example.com");

            Intent intent = new Intent ( this , Kids.class );

            // Set the action for the intent (e.g., ACTION_VIEW to open the URI)
            //intent.setAction(Intent.ACTION_VIEW);

            // Set the data (URI) for the intent
            intent.setData ( selectedImageUri );

            intent.putExtra ( "Name" , binding.NameEditText.getText ( ).toString ( ) );

            setResult ( RESULT_OK , intent );

            finish ( );
        }
        else
            Snack_Bar ( "يجب كتابة الاسم و اختيار صوره قبل الضغظ على زر الحفظ" );
    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make ( binding . Constraint , Message , 7000 ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList. valueOf ( ContextCompat. getColor ( this , R. color . Snack_bar_BG_Color ) ) ) ;

        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById ( com . google . android . material . R . id . snackbar_text ) ;

        textView . setSingleLine ( false ) ;

        textView . setTextColor ( ContextCompat . getColor ( this , R . color . white ) ) ;

        textView . setTextSize ( 15 ) ;

        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        ViewGroup. MarginLayoutParams marginLayoutParams = ( ViewGroup.MarginLayoutParams ) snackbarView . getLayoutParams ( ) ;

        marginLayoutParams . setMargins
                (
                        marginLayoutParams  . leftMargin  ,
                        marginLayoutParams  . topMargin   ,
                        marginLayoutParams . rightMargin ,
                        65
                ) ;

        snackbarView . setLayoutParams ( marginLayoutParams ) ;

        snackbar . show ( ) ;
    }

}