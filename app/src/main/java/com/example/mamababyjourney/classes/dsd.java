package com.example.mamababyjourney.classes;

import com.example.mamababyjourney.mother_section.screens_folder.kids_page.Add_Child_Activity;
import com.example.mamababyjourney.mother_section.screens_folder.kids_page.Child_Activity;
import com.example.mamababyjourney.mother_section.screens_folder.vaccines_page.Vaccines_Program_Activity;
import com.example.mamababyjourney.databinding.FragmentMotherSectionKidsPageKidsFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import de.hdodenhof.circleimageview.CircleImageView;
import com.google.firebase.storage.FirebaseStorage;
import static android.app.Activity.RESULT_OK;
import androidx.gridlayout.widget.GridLayout;
import androidx.fragment.app.Fragment;
import com.example.mamababyjourney.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;

@SuppressLint ( { "InflateParams" , "IntentReset" , "SetTextI18n" } )
@SuppressWarnings ( { "deprecation" , "DataFlowIssue" , "RedundantSuppression" , "ConstantConditions" } )
public class dsd extends Fragment
{
    FragmentMotherSectionKidsPageKidsFragmentBinding binding ;

    Intent intent ;


    ProgressDialog progressDialog ;


    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionKidsPageKidsFragmentBinding.inflate (inflater ,container ,false );

        progressDialog = new ProgressDialog (requireContext ( ) ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;

        Add_Treatments_Program_Button ( );

        Get_Children ( ) ;

        binding.AddChildBTN.setOnClickListener ( view ->
        {
            Intent intent = new Intent (requireContext ( ) ,Add_Child_Activity . class ) ;
            startActivityForResult (intent ,1 ) ;
        });

        return binding . getRoot ( ) ;
    }

    // هاد الفنكشن بتنفذ اول ما تشتغل الشاشه و الي بعمله هو انه بضيف الي في الشاشة زر لبرنامج التطعيم
    private void Add_Treatments_Program_Button ( )
    {
        intent = new Intent (requireContext ( ) ,Vaccines_Program_Activity . class ) ;
        Add_Items (R . drawable . images_treatments_program ,"برنامج التطعيم" , null ,"" ,null ) ;
    }

    public void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super . onActivityResult (requestCode ,resultCode ,data ) ;

        if ( resultCode == RESULT_OK && data != null )
        {
            intent = new Intent (requireContext ( ) ,Child_Activity . class ) ;
            intent . putExtra ( "gender" , data . getExtras ( ) . getString ("gender" ) ) ;
            Add_Items (R . drawable . images_baby ,data . getExtras ( ) . getString ("Name" ) , data ,"" ,null ) ;
        }
    }

    // هاد الفنكشن الي بجيب الي الداتا تبعت الاطفال من الفاير ستور
    private void Get_Children ( )
    {
        progressDialog . show ( ) ;

        FirebaseFirestore . getInstance ( )
                .collection ( "/Mothers/" + FirebaseAuth. getInstance ( ) . getCurrentUser ( ) . getEmail ( ) + "/Children's" )
                .get ( ) . addOnCompleteListener ( task ->
                {
                    if ( task . isSuccessful ( ) )
                    {
                        if ( !task.getResult ( ).isEmpty ( ) )
                        {
                            FirebaseFirestore . getInstance ( )
                                    .collection ("/Mothers/" + FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) + "/Children's" )
                                    .get ( ) . addOnSuccessListener (querySnapshot ->
                                    {
                                        for ( DocumentSnapshot documentSnapshot : querySnapshot )
                                        {

                                            String image_Url = documentSnapshot . get ( "image Url" ) . toString ( ) ;

                                            if ( image_Url . isEmpty () || image_Url . equals ( null ) )
                                            {
                                                intent = new Intent (requireContext ( ) ,Child_Activity . class ) ;
                                                intent . putExtra ("gender" ,documentSnapshot . get ( "gender" ) + "" ) ;
                                                Add_Items (R . drawable . images_baby ,documentSnapshot . get ( "Child Name" ) + "" ,null ,image_Url ,null ) ;
                                            }
                                            else
                                            {
                                                FirebaseStorage . getInstance ( ) . getReferenceFromUrl (image_Url ) . getDownloadUrl ( ) . addOnSuccessListener (uri ->
                                                {

                                                    intent = new Intent (requireContext ( ) , Child_Activity . class ) ;
                                                    intent . putExtra ("gender" ,documentSnapshot . get ( "gender" ) + "" ) ;

                                                    Add_Items (R . drawable . images_baby ,documentSnapshot . get ( "Child Name" ) + "" , null , image_Url , uri ) ;
                                                });
                                            }
                                        }
                                    });
                        }
                        else
                            progressDialog . dismiss ( ) ;
                    }
                });
    }

    private void Add_Items ( int image_Resource , String title , Intent data , String image_Url , Uri uri )
    {
        // هون انا بجي ال layout الي مستعمله كنموذج وبخزنها في المتغير الي عنا هون
        View childLayout = getLayoutInflater ( ) . inflate (R . layout . layouts_mother_section_grid_layout_item_layout ,null ) ;

        // هسه ال layout الي انا مستعملها كنمذوج لصور الاطفال فيها نوعين من الصور وحده بتجي دائريه و الثانيه عادي هون انافي اول سطرين مظهر الدائريه عشان استعملها و السطرين الثانين مخفي العاديه عشان ما بدي اياها
        CircleImageView circleImageView = childLayout . findViewById (R . id . circle_image_view ) ;
        ImageView image_view = childLayout . findViewById (R . id . image_view ) ;

        circleImageView . setVisibility ( View . VISIBLE ) ;
        image_view      . setVisibility ( View . GONE    ) ;

        // هون انا جايب من ال layout ال textview الي بدي اعرض فيه اسم الطفل
        TextView textView = childLayout . findViewById (R . id . Title ) ;

        // هون بجيب  ال GridLayout الي بدي اضيف فيها الزر وبخزنها في هاد المتغير الي عنا تحت
        GridLayout . LayoutParams params = new GridLayout . LayoutParams ( ) ;

        // هون بحدد الصوره الي بدي اياها و النص الي بدي اياه للزر
        if ( title . equals ( "برنامج التطعيم" )  )
            circleImageView . setImageResource ( image_Resource ) ;
        else
        {
            if (data != null)
                if ( data . getExtras ( ) . getString ( "image form ?" ) . equals ( "Drawable" ) )
                    circleImageView . setImageResource ( image_Resource ) ;
                else
                {
                    Uri imageUri = data . getData ( ) ;
                    circleImageView . setImageURI ( imageUri ) ;
                }
        }

        if ( ! image_Url . isEmpty () && ! image_Url . equals ( null ) )
            Picasso . get ( ) . load (uri ) . into (circleImageView ) ;
        else
            circleImageView . setImageResource ( image_Resource ) ;


        textView . setText ( title ) ;

        // هون بحدد قياسات و ابعاد الزر
        params . columnSpec = GridLayout . spec (GridLayout . UNDEFINED ,GridLayout . FILL ,1 ) ;
        params . setMargins ( 0 ,0 , 0 ,21 ) ;

        // هون بجهز حدث الضغط على الزر بحيث انه بس نضغط عليه ينقلنا على شاشة برنامج التطعيم
        childLayout . setOnClickListener ( view ->
        {
            startActivityForResult (intent , 1 ) ;
        });

        // وهون اخر سطر انا بضيف الزر على ال GridLayout الي حددتها فوق
        binding . GirdLayout . addView (childLayout ,params ) ;

        progressDialog . dismiss ( ) ;
    }
}
