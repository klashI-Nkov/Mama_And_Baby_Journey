package com.example.mamababyjourney.mother_section.screens_folder.advices_page;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.classes.Recycler_View_Adapter;
import com.example.mamababyjourney.classes.Recycler_View_Class;
import com.example.mamababyjourney.databinding.ActivityMotherSectionAdvicesPageSubAdvicesActivityBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.Objects;

public class Sub_Advices_Activity extends AppCompatActivity
{

    ActivityMotherSectionAdvicesPageSubAdvicesActivityBinding binding ;

    Recycler_View_Adapter adapter ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;
        getWindow ( ) . setFlags ( WindowManager. LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects. requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityMotherSectionAdvicesPageSubAdvicesActivityBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Add_Sub_Advice ( ) ;
    }

    // هاد الفنكشن الي بعرض الي النصائح الي بكبس على الزر تبعها
    private void Add_Sub_Advice ( )
    {
        String [ ] advice_content = { "null" } ;
        String [ ] advice_title   = { "null" } ;

        // هون عنا switch بتشيك على عنوان النصيحه و بتنفذ ال case الخاص فيها
        switch ( getIntent ( ) . getExtras ( ) . getString ( "title" ) )
        {
            case "سلوكيات هامة يجب مراعاتها قبل الحمل" :
            {
                // هون بستدعي الفنكشن الي بضيف الي العناوين الخاصه بالنصائح الي ضغطنا على الزر تبعهم مع محتوى كل نصيحه و بحط الي عنوان النصيحه الي ضغطنا على الزر تبعها وفي باقي ال cases بصير نفس الشي لكن كل case اله array عناوين و array محتويات مختلفين عن الي بخصو ال Case الي قبله

                advice_title   = getResources ( ) . getStringArray ( R . array . Important_behaviors_to_consider_before_pregnancy_title   ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . Important_behaviors_to_consider_before_pregnancy_content ) ;
                break;
            }

            case "امور يجب الانتباه إليها أثناء الحمل" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . things_to_watch_out_for_during_pregnancy_title   ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . things_to_watch_out_for_during_pregnancy_content ) ;
                break;
            }

            case "حضري نفسك للرضاعة الطبيعية" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . prepare_yourself_for_breastfeeding_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . prepare_yourself_for_breastfeeding_content ) ;
                break;
            }

            case "إرشادات للمرأة الحامل" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . instructions_for_pregnant_women_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . instructions_for_pregnant_women_content ) ;
                break;
            }

            case "العناية بالطفل الوليد" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . newborn_baby_care_title   ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . newborn_baby_care_content ) ;
                break;
            }

            case "المشاكل اثناء الحمل" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . problems_during_pregnancy_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . problems_during_pregnancy_content ) ;
                break;
            }

            case "علامات اقتراب الولادة" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . Signs_of_approaching_childbirth_title  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . Signs_of_approaching_childbirth_content ) ;
                break;
            }

            case "العناية بالطفل المريض" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . Taking_care_of_a_sick_child_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . Taking_care_of_a_sick_child_content ) ;
                break;
            }

            case "العناية باسنان الطفل" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . child_dental_care_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . child_dental_care_content ) ;
                break;
            }

            case "الحوادث المنزليه" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . home_accidents_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . home_accidents_content ) ;
                break;
            }

            case "تغذية الاطفال" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . kids_nutrition_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . kids_nutrition_content ) ;
                break;
            }

            case "تطور الطفل" :
            {
                advice_title   = getResources ( ) . getStringArray ( R . array . child_development_titles  ) ;
                advice_content = getResources ( ) . getStringArray ( R . array . child_development_content ) ;
                break;
            }
        }

        binding . SubAdviceTitle . setText ( getIntent ( ) . getExtras ( ) . getString ( "title" ) ) ;

        for ( int i = 0  ; i < advice_title . length  ; i++ )
        {
            Recycler_View_Class . recycler_View_Class_Object_List .
                    add ( new Recycler_View_Class (advice_title [ i ] ,advice_content [ i ] ) ) ;
        }

        adapter = new Recycler_View_Adapter
                ( Recycler_View_Class. recycler_View_Class_Object_List ,this ) ;

        binding . SubAdviceRecyclerView . setAdapter ( adapter ) ;
        binding . SubAdviceRecyclerView . setLayoutManager ( new LinearLayoutManager (this ) ) ;
    }

    @Override
    public void onBackPressed ( )
    {
        super.onBackPressed ( );
        if ( adapter != null )
            adapter . ClearData ( ) ;
    }
}