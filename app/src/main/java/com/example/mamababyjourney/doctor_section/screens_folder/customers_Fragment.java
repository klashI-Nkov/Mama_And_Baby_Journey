package com.example.mamababyjourney.doctor_section.screens_folder;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.classes.Recycler_View_Adapter;
import com.example.mamababyjourney.classes.Recycler_View_Class;
import com.example.mamababyjourney.databinding.FragmentDoctorSectionCustomersFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

public class customers_Fragment extends Fragment
{
    private FragmentDoctorSectionCustomersFragmentBinding binding ;

    Recycler_View_Adapter adapter ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentDoctorSectionCustomersFragmentBinding . inflate (inflater ,container ,false ) ;
        binding . AddCustomerBTN . setOnClickListener ( view ->
        {
            if ( !binding.MotherEmailEditText . getText ().toString ().isEmpty () && !binding.MotherEmailEditText . getText ().toString ().equals ( null ) )
            {
                String [ ] advice_title   = {""};
                String [ ] advice_content = {""};

                // هسه هون انا بروح على الفايرستور و بجيب المعلومات الي في documnet الام وبستعملها
                FirebaseFirestore. getInstance ( )
                        .collection ("Mothers" )
                        .document   ( binding . MotherEmailEditText . getText () .toString () )
                        .get ( ) . addOnCompleteListener ( task ->
                        {
                            if ( task . isSuccessful ( ) )
                            {
                                DocumentSnapshot document = task . getResult ( ) ;

                                if ( document . exists ( ) )
                                {
                                    advice_content [0] = "الاسم : " + document.get("name");

                                    Recycler_View_Class. recycler_View_Class_Object_List .
                                            add ( new Recycler_View_Class (advice_title [ 0 ] ,advice_content [ 0 ] ) ) ;

                                    adapter = new Recycler_View_Adapter
                                            ( Recycler_View_Class. recycler_View_Class_Object_List ,requireContext ( ) ) ;

                                    binding . CustomersRecyclerView . setAdapter ( adapter ) ;
                                    binding . CustomersRecyclerView . setLayoutManager ( new LinearLayoutManager (requireContext ( ) ) ) ;
                                    binding . NoCustomersYetTv . setVisibility ( View.GONE );
                                    binding . CustomersRecyclerView . setVisibility ( View.VISIBLE );
                                }
                            }
                        });
            }
        });
        return binding . getRoot ( ) ;
    }

    @Override
    public void onPause ( )
    {
        super.onPause ( );
        if ( adapter != null )
            adapter . ClearData ( ) ;
    }
}