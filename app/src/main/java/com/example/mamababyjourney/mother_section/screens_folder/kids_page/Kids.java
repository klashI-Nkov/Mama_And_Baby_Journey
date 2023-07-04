package com.example.mamababyjourney.mother_section.screens_folder.kids_page;

import com.example.mamababyjourney.mother_section.screens_folder.vaccines_page.Vaccines_Program_Activity;
import com.example.mamababyjourney.databinding.FragmentMotherSectionKidsPageKidsFragmentBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import androidx.gridlayout.widget.GridLayout;
import static android.app.Activity.RESULT_OK;
import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;

@SuppressLint ( { "InflateParams" , "IntentReset" } )
@SuppressWarnings ( { "SpellCheckingInspection" , "deprecation" , "DataFlowIssue" , "RedundantSuppression" } )
public class Kids extends Fragment
{
    FragmentMotherSectionKidsPageKidsFragmentBinding binding ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionKidsPageKidsFragmentBinding.inflate ( inflater , container , false );

        Add_Treatments_Program_Button ( );

        binding.AddChildBTN.setOnClickListener ( view ->
        {
            Intent intent = new Intent (requireContext ( ) ,Add_Child_Activity . class ) ;
            startActivityForResult (intent ,1 ) ;
        } ) ;

        return binding . getRoot ( ) ;
    }

    // هاد الفنكشن بتنفذ اول ما تشتغل الشاشه و الي بعمله هو انه بضيف الي في الشاشة زر لبرنامج التطعيم
    private void Add_Treatments_Program_Button ( )
    {
        // هون انا بجي ال layout الي مستعمله كنموذج وبخزنها في المتغير الي عنا هون
        View childLayout = getLayoutInflater ( ) . inflate (R . layout . layouts_mother_section_grid_layout_item_layout ,null ) ;

        // هسه ال layout الي انا مستعملها كنمذوج لصور الاطفال فيها نوعين من الصور وحده بتجي دائريه و الثانيه عادي هون انافي اول سطرين مظهر الدائريه عشان استعملها و السطرين الثانين مخفي العاديه عشان ما بدي اياها
        CircleImageView circleImageView = childLayout . findViewById (R . id . circle_image_view ) ;
        circleImageView . setVisibility ( View . VISIBLE ) ;

        ImageView image_view = childLayout . findViewById (R . id . image_view ) ;
        image_view . setVisibility ( View . GONE ) ;

        // هون انا جايب من ال layout ال textview الي بدي اعرض فيه اسم الطفل
        TextView textView = childLayout . findViewById (R . id . Title ) ;

        // هون بجيب  ال GridLayout الي بدي اضيف فيها الزر وبخزنها في هاد المتغير الي عنا تحت
        GridLayout . LayoutParams params = new GridLayout . LayoutParams ( ) ;

        // هون بحدد الصوره الي بدي اياها و النص الي بدي اياه للزر
        circleImageView . setImageResource ( R . drawable . images_treatments_program ) ;
        textView . setText ( "برنامج التطعيم" ) ;

        // هون بحدد قياسات و ابعاد الزر
        params . columnSpec = GridLayout . spec (GridLayout . UNDEFINED ,GridLayout . FILL ,1 ) ;
        params . setMargins ( 0 ,0 , 0 ,21 ) ;

        // هون بجهز حدث الضغط على الزر بحيث انه بس نضغط عليه ينقلنا على شاشة برنامج التطعيم
        childLayout . setOnClickListener ( view ->
        {
            Intent intent = new Intent (requireContext ( ) , Vaccines_Program_Activity. class ) ;
            startActivityForResult (intent , 1 ) ;
        });

        // وهون اخر سطر انا بضيف الزر على ال GridLayout الي حددتها فوق
        binding . GirdLayout . addView (childLayout ,params ) ;
    }

    public void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super . onActivityResult (requestCode ,resultCode ,data ) ;

        if ( resultCode == RESULT_OK && data != null )
        {
            // هون بصير تفس الي بصير في هاد الفنكشن Add_Treatments_Program_Button لكن الفرق انه لما نضغط على الزر رح يودينا على الصفحه الي فيها بينات الطفل الي ضغطنا على الزر تبعه
            View childLayout = getLayoutInflater ( ) . inflate ( R . layout .layouts_mother_section_grid_layout_item_layout , null ) ;

            CircleImageView circleImageView = childLayout . findViewById (R . id . circle_image_view ) ;
            circleImageView . setVisibility ( View . VISIBLE ) ;
            ImageView image_view = childLayout . findViewById (R . id . image_view ) ;
            image_view . setVisibility ( View . GONE ) ;

            TextView textView = childLayout . findViewById ( R . id . Title ) ;

            GridLayout . LayoutParams params = new GridLayout . LayoutParams ( ) ;
            Uri selectedImageUri = data . getData ( ) ;

            circleImageView . setImageURI ( selectedImageUri ) ;
            textView . setText ( data . getExtras ( ) . getString ( "Name" ) ) ;

            params . columnSpec = GridLayout . spec ( GridLayout . UNDEFINED , GridLayout . FILL , 1 ) ;
            params . setMargins (0 , 0 , 0 , 21 ) ;

            childLayout . setOnClickListener ( V ->
            {
                Intent intent = new Intent ( requireContext ( ) , Child_Activity . class ) ;
                startActivity ( intent ) ;
            });

            binding . GirdLayout . addView ( childLayout ,params ) ;
        }
    }
}