package com.example.mamababyjourney.mother_section.screens_folder.advices_page;

import com.example.mamababyjourney.databinding.FragmentMotherSectionAdvicesFragmentBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import de.hdodenhof.circleimageview.CircleImageView;
import androidx.gridlayout.widget.GridLayout;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import androidx.fragment.app.Fragment;
import com.example.mamababyjourney.R;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.app.ProgressDialog;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.content.Intent;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;


@SuppressWarnings ( { "deprecation" , "InflateParams" } )
public class Advices extends Fragment
{

    private FragmentMotherSectionAdvicesFragmentBinding binding ;

    ProgressDialog progressDialog ;

    // هاد المتغير انا مستعمله عشان من خلاله احدد اي ثيم الي شغال وبكون true اذا كان ثيم الدارك شغال و false اذا كان ثيم اللايت شغال
    private boolean flag ;

    // هاد المتغير انا مستعمله عشان اخزن فيه اسم اخر button انضغط عليه
    private String button_Name  ;

    // هاد الفنكشن هو بالزبط نفس فنشكن ال oncreate لكن في ال fragment بجي هيك شكله و اسمه لانه ال fragment تعتبر جزء من شاشه وليس شاشة كامله لهيك شكله و اسمه بختلف
    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionAdvicesFragmentBinding . inflate (inflater ,container ,false ) ;

        progressDialog = new ProgressDialog ( requireContext ( ) ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;

        Get_Button_Name ( ) ;

        // هاد بتنفذ لما نضغط غلى زر نصائح الام
        binding . MotherAdviceBTN . setOnClickListener ( V ->
        {
            button_Name = "Mothers Advice BTN" ;
            Update_Button_Name ( ) ;
            Color (Color . parseColor (flag ? "#3CFFFFFF" : "#140E0E0E" ) ,button_Name ) ;
            Add_Items ( ) ;
        });

        // هاد بتنفذ لما نضغط غلى زر نصائح الطفل
        binding . BabyAdviceBTN   . setOnClickListener ( V ->
        {
            button_Name = "Baby Advice BTN" ;
            Update_Button_Name ( ) ;
            Color (Color . parseColor (flag ? "#3CFFFFFF" : "#140E0E0E" ) ,button_Name ) ;
            Add_Items ( ) ;
        });

        return binding . getRoot ( ) ;
    }

    // هدول الفنكشنين الي تحت عشان حسب الثيم اغير الوان ال buttons تبعين نصائح الام و الطفل الثاني الي هو هاد Color هو الي بشتغل كل الشغل
    public void Theme ( )
    {
        Configuration configuration = getResources ( ) . getConfiguration ( ) ;

        int currentUiMode = configuration . uiMode & Configuration . UI_MODE_NIGHT_MASK ;

        if ( currentUiMode == Configuration . UI_MODE_NIGHT_YES )
        {
            flag = true ;
            Color (Color . parseColor ("#3CFFFFFF" ) ,button_Name ) ;
        }
        else
        {
            flag = false ;
            Color (Color . parseColor ("#140E0E0E" ) ,button_Name ) ;
        }
    }

    private void Color ( int color ,String button_Name )
    {
        // هون بشيك ايش اخر button انضغط عليه و الي اسمه مخزن في المغير الي اسمه button_Name
        if ( button_Name . equals ( "Mothers Advice BTN"  ) )
        {
            binding . MotherAdviceBTN . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;
            binding . BabyAdviceBTN   . setBackgroundTintList ( ColorStateList . valueOf (getResources ( ) . getColor (android . R . color . transparent ) ) ) ;
        }
        else
        {
            binding . MotherAdviceBTN . setBackgroundTintList ( ColorStateList . valueOf (getResources ( ) . getColor (android . R . color . transparent ) ) ) ;
            binding . BabyAdviceBTN   . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;
        }
    }

    // هاد بجيب الي اسم اخر button انضغط عليه من الفايرستور
    private void Get_Button_Name ( )
    {
        progressDialog . show ( ) ;

        /*
            هسه انا مخزن اسم اخر button انضغط عليه في الفايرستور عشان ي منار لو بدي اخزن قيمة هاد button_Name المتغير في التطبيق الي رح يصير انه في كل مره بغير الثيم

            بتنفذ عندي فنكشن ال oncreate وبرجع قيمة المتغير للقيمه الافتراضيه تبعته و الي هي null يعني لا شيء وبس يتنفذ عندي فنكشن الي Theme رح يضرب التطبيق خطا و يسكر

            لهيك خزنت قيمة هاد button_Name المتغير في الفايرستور و خليت الكود بس اكبس على واحد من ال buttons يخزن اسمه في الفايرستور وبس يتغير الثيم بشتغل عندي فنكشن

            ال oncreate و بجيب الي القيمه الي في الفايرستور و بخزنها في هاد button_Name المتغير



            ولو ي منار عدلت القيمه الابتدائيه لهاد button_Name المتغير في التطبيق بحيت تكون اسم واحد من ال buttons وخلينا نحكي مثلا تكون اسم button نصائح الام الي رح يصير

            لو كنت انا قبل ما اغير الثيم كبست على button نصائح الطفل فرح يتخزن اسمه في هاد button_Name المتغير

            وبس اغير الثيم رح يتنفذ فنكشن ال oncreate رح يرجع هاد button_Name المتغير لقيمته الابتدائيه والي هي اسم button نصائح الام

            وقتها رح يغير لون button نصائح الام وما رح يغير لون button نصائح الطفل الي المفروض انه هو الي يتغير مش button نصائح الام وكمان رح يضل عارض نصائح الطفل وهاد شيئ مش منطقي انه يصير

            وهاد كمان سبب تاني لاني اخزن اسم هاد button_Name المتغير في الفايرستور
        */
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        db . collection ( "A" ) . document ( "D" ) . get ( ) . addOnCompleteListener ( task ->
        {
            if ( task.isSuccessful ( ) )
            {
                DocumentSnapshot document = task.getResult ( );

                if ( document.exists ( ) )
                {
                    // هون بجيب القيمه من الفايرستور و بخزنها في هاد button_Name المتغير
                    button_Name = document . get ( "button name" ) + "" ;

                    // بعدها بستدعي الفنكشن الي بحدد الي شو الثيم الشغال وبغير لون ال button الي جاب اسمه من الفايرستور حسب الثيم
                    Theme ( ) ;

                    // بعدها هون بستدعي الفنكشن الي بضيف ازرار النصائح

                    Add_Items ( ) ;

                    progressDialog . dismiss ( ) ;
                }
            }
        });
    }

    // وهاد الي بخزن في الفايرستور اسم اخر button انضظ عليه
    private void Update_Button_Name ( )
    {
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        HashMap < String , Object > Button_Name = new HashMap < > ( ) ;
        Button_Name . put ( "button name" , button_Name ) ;

        db . collection ("A" ) . document ("D" ) . update (Button_Name ) ;
    }

    /*
        هاد الفنكشن بعمل نفس الي بصير في ال kids fragment وتحديدا في فنكشن ال Add_Treatments_Program_Button و فنكشن ال onActivityResult

        لكن في فرق بسيط عنهم هسه فنشكن ال onActivityResult هاد بتنفذ لما نضيف طفل جديد و بتنفذ مره وحده

        وفنكشن ال Add_Treatments_Program_Button بتفذ لما نفتح الشاشه عشان يضيف النا زر برنامج التطعيم و بتنفذ مره وحده بس

        اما هاد بتنفذ اول ما تفتح الشاشه و كمان لما نضغط على زر نصائح الام او زر نصائح الطفل

        واول شي بعمله بس يتنفذ يعني انه بمسح كل شي في الشاشه عشان ي منار لو ما مسحنا كل شي لما نكبس على زر نصائح الام بعد ما كنا
        كابسين على زر نصائح الطفل رح يعرض نصائح الام مع نصائح الطفل لهيك لازم نفضي الشاشه عشان نعرض بس نصائح الام لما نضغط على الزر تبعها

        بعدها بجيب ال array الي فيها عنواين النصائح من ملف ال strings و بخزنها في هاي titles ال array

        بعدها بدخل في for each بتلف على كل عناصر هاي titles ال array وبتبلش تضيفهم في الشاشه
    */
    private void Add_Items ( )
    {

        progressDialog . show ( ) ;

        // هون بحذف كل شي في الشاشة
        binding . GirdLayout . removeAllViews ( ) ;

        // هون انا بشيك على اسم ال button الي مخزن في هاد button_Name المتغير فاذا كان مخزن فيه اسم button نصائح الام وقتها رح يجب ال Array الي فيها عناوين نصائح الام غير هيك رح يجيب ال array الي فيها عناوين نصائح الطفل
        String [ ] titles = getResources ( ) . getStringArray ( button_Name . equals ( "Mothers Advice BTN" ) ? R . array . mother_advices_titles : R . array . kids_advices_titles ) ;

        for ( String title : titles )
        {
            // هون انا بجيب ال layout الي مستعمله كنموذج وبخزنها في هاد childLayout المتغير

            View childLayout = getLayoutInflater ( ) . inflate ( R . layout . layouts_mother_section_grid_layout_item_layout ,null ) ;

            /*
               هسه هون بدل ما انا كنت في فنكشن ال onActivityResult وفنكشن ال Add_Treatments_Program_Button في
                ال kids fragment مستعمل الصوره الدائريه هون مستعمل الصوره العاديه فمخفي الصوره الدائري و مظهر الصوره العاديه
            */
            CircleImageView circleImageView = childLayout . findViewById (R . id . circle_image_view ) ;
            ImageView image_view = childLayout . findViewById (R . id . image_view ) ;

            circleImageView . setVisibility ( View . GONE ) ;
            image_view      . setVisibility ( View . VISIBLE ) ;


            // هون بجيب ابعاد ال GridLayout و بخزنها في هاد params المتغير
            GridLayout . LayoutParams params = new GridLayout . LayoutParams ( ) ;

            // هون نفس الي بصير لما اجيب ال array من ملف ال strings بشيك على اسم ال button الي مخزن في هاد button_Name المتغير اذا كان مخزن فيه اسم زر نصائح الام فحط الصوره الخاصة بنصائح الام غير هيك حط الصوره الخاصه بنصائح الطفل
            image_view . setImageResource ( button_Name . equals ( "Mothers Advice BTN" ) ? R . drawable . images_mother_image :  R . drawable . images_baby ) ;


            TextView textView = childLayout . findViewById (R . id . Title ) ;
            textView . setText ( title ) ;

            params . columnSpec = GridLayout . spec (GridLayout . UNDEFINED ,GridLayout . FILL ,1 ) ;
            params . setMargins ( 0 ,0 ,0 ,21 ) ;

            // هون احنا بنجهز حدث الضغط على وحده من النصائح
            childLayout . setOnClickListener ( view ->
            {
                Intent intent = new Intent (requireContext ( ) ,Sub_Advices_Activity . class ) ;
                intent . putExtra ("title" , title ) ;
                startActivity (intent ) ;
            });

            binding . GirdLayout . addView (childLayout ,params ) ;

            progressDialog . dismiss ( ) ;
        }
    }
}