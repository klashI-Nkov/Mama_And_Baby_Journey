package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.ActivityInfoBinding;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.Objects;

@SuppressWarnings ( { "unused" , "CommentedOutCode" } )
@SuppressLint ( "ClickableViewAccessibility" )
public class Info_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ActivityInfoBinding binding;
    EditText Name_editText;


    protected void onCreate ( Bundle savedInstanceState )
    {

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide ( );

        binding = ActivityInfoBinding.inflate ( getLayoutInflater ( ) );
        super.onCreate ( savedInstanceState );
        setContentView ( binding.getRoot ( ) );


        // for Auto Complete Text View hints
        adapter_initialization ( );

        binding.MapBTN.setOnClickListener ( v ->
        {

            Intent intent = new Intent ( this, Map.class );
            startActivity ( intent );

        } );

    }



    public void Which_Radio_BTN_Checked ( View view )
    {
        if ( binding.DoctorRBTN.isChecked ( ) || binding.DoctorRBTN2.isChecked ( ) )
        {
            binding.MomRBTN.setChecked ( false );
            binding.MomRBTN2.setChecked ( false );

            binding.DoctorRBTN.setChecked ( true );
            binding.DoctorRBTN2.setChecked ( true );

            binding.MomData.setVisibility ( View.INVISIBLE );
            binding.DoctorData.setVisibility ( View.VISIBLE );
        }
        else if ( binding.MomRBTN.isChecked ( ) || binding.MomRBTN2.isChecked ( ) )
        {
            binding.DoctorRBTN.setChecked ( false );
            binding.DoctorRBTN2.setChecked ( false );

            binding.MomRBTN.setChecked ( true );
            binding.MomRBTN2.setChecked ( true );

            binding.DoctorData.setVisibility ( View.INVISIBLE );
            binding.MomData.setVisibility ( View.VISIBLE );
        }
    }

    public void Add_Workplace_Dialog_Function ( View view )
    {
        binding.DoctorData.setVisibility ( View.INVISIBLE );
        binding.WorkplaceData.setVisibility ( View.VISIBLE );

        binding.SaveWorkplaceData.setOnClickListener ( v ->
        {
            binding.WorkplaceData.setVisibility ( View.INVISIBLE );
            binding.DoctorData.setVisibility ( View.VISIBLE );

            binding.DoctorName.setText ( Name_editText.getText() );
        } );

    }

    private void adapter_initialization ( )
    {
        ArrayAdapter < CharSequence > adapter = ArrayAdapter.createFromResource ( this , R.array.Workplace_Locations , R.layout.spinner_drop_down_items_text );

        adapter.setDropDownViewResource ( R.layout.spinner_drop_down_items_text );

        binding.Workplace.setAdapter ( adapter );
        binding.Workplace.setOnItemSelectedListener ( this );

    }

    public void onItemSelected ( AdapterView < ? > parent , View view , int position , long id )
    {
        /*if ( position==2 )
        {
            AppCompatButton Save_Workplace_Data = binding.SaveWorkplaceData;

            Name_editText.setHint("Enter your name");
            Name_editText.setVisibility ( View.VISIBLE );
            Name_editText.setId ( 10 );


            binding.WorkplaceLayout.removeView(binding.SaveWorkplaceData);
            binding.WorkplaceLayout.removeView(binding.Doctor);
            binding.WorkplaceLayout.addView(Name_editText);
            binding.WorkplaceLayout.addView(Save_Workplace_Data);

            Toast.makeText ( this , String.valueOf ( Name_editText.getId () ) , Toast.LENGTH_LONG ).show ();
        }*/


    }

    @Override
    public void onNothingSelected ( AdapterView < ? > parent )
    {

    }

    private void Workplace_fields ( )
    {
        // create a new instance of EditText
        EditText editText = new EditText(this);

        // set the layout parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);

        // set additional attributes
        editText.setHint("Enter your name");
        editText.setInputType( InputType.TYPE_CLASS_TEXT);

        // add the EditText to your parent layout
        LinearLayout linearLayout = findViewById(R.id.Workplace_Layout);
        linearLayout.addView(editText);

    }

}