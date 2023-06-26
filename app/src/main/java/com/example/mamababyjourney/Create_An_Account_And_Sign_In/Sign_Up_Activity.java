package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.Create_An_Account_And_Sign_In.Info.Doctor_Data_Activity;
import com.example.mamababyjourney.databinding.ActivitySignUpBinding;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.NonNull;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.view.View;
import java.util.Objects;
import android.os.Bundle;

@SuppressWarnings ( { "RedundantSuppression" , "SpellCheckingInspection" , "deprecation" } )
@SuppressLint ( "ClickableViewAccessibility" )
public class Sign_Up_Activity extends AppCompatActivity
{

    ActivitySignUpBinding binding ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Buttons ( ) ;

    }

    // هاد الفنكشن عمله عشان اغير لون التكست لل Radio buttons حسب الثيم لانه حاولت اطبق عليهم ستايل يغيير لون الخط حسب الثيم ما زبط فانجبرت استعمل هاي الطريقه
    @Override
    public void onConfigurationChanged ( @NonNull Configuration newConfig )
    {
        super . onConfigurationChanged ( newConfig ) ;

        // الشرط الي جوا الاف معناه انه روح جيب الثيم الحالي و شوف اذا هو دارك ادخل و نفذ الي جوا الاف واعطي الخط تاع ال Radio buttons الوان الثيم الدارك اذا الثيم الحالي مش دارك اعطيهم الوان الثيم الفاتح
        if ( ( getResources ( ) . getConfiguration ( ) . uiMode & Configuration . UI_MODE_NIGHT_MASK ) == Configuration . UI_MODE_NIGHT_YES )
        {
            binding . DoctorRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_N ) ) ;
            binding . MomRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_N ) ) ;
        }
        else
        {
            binding . DoctorRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_L ) ) ;
            binding . MomRBTN . setTextColor ( getResources ( ) . getColor ( R . color . R_B_T_C_L ) ) ;
        }
    }

    /*
        هاد الفنكشن صح انه جديد لكن هو وحده الجديد اما الاكواد الي جواته قديمه والجديد في الموضوع انه الاكواد الي داخله
        كانت تتنفذ عند حدث الضغط الخاص بالشي الي انا كاتب اله الكود

        والي عملته هلا اني خليتها تتنفذ عند حدث اللمس للشي الي انا كاتب الكود عشانه

        اول شي اسرع في الاستجابه و تاني شي اقل في الكود

        ثالث شي الخاصيه الي سالتي عنها الصبح كانت المشكله لما كانت تتنفذ عند الضغط على ال Radio Buttons تاعين الام و الدكتور

        الي كان يصير مرات ما كانت تشتغل و ما تتنفذ وبس استعملت حدث اللمس زبطت مباشره
     */
    private void Buttons ( )
    {

        // هاد مربوط مع ايقونة التسجيل بواسطة فيسبوك و ظيفته انه يفعل زر انشاء الحساب بعد ما تعطل لما الام او الدكتور كبسو عليه بدون ما يحددو الصفه
        binding . FacebookIcon . setOnTouchListener ( ( v , event ) ->
        {
            if ( event . getAction ( ) == MotionEvent.ACTION_DOWN )
            {
                // هون بقله اذا المسخدم مش محدد شو صفته ادخل الاف و عطل انشاء الحساب باستخدام قوقل اما اذا حدد فعادي خليه يستعمله
                if ( !binding.MomRBTN.isChecked ( ) && !binding.DoctorRBTN.isChecked ( ) )
                 Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;
            }
            return false ;
        } );

        // هاد مربوط مع ايقونة التسجيل بواسطة قوقل و ظيفته انه يفعل زر انشاء الحساب بعد ما تعطل لما الام او الدكتور كبسو عليه بدون ما يحددو الصفه
        binding . GoogleIcon . setOnTouchListener ( ( v , event ) ->
        {
            if ( event . getAction ( ) == MotionEvent.ACTION_DOWN )
            {
                // هون بقله اذا المسخدم مش محدد شو صفته ادخل الاف و عطل انشاء الحساب باستخدام قوقل اما اذا حدد فعادي خليه يستعمله
                if ( ! binding . MomRBTN . isChecked ( ) && ! binding . DoctorRBTN . isChecked ( ) )
                    Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;
                else
                {

                }
            }
            return false;
        } );

    }

    // هاد بتنفذ عند لما نكبس على زر انشاء الحساب و وظفيته انه ينقل المستخدم للشاشه الي بعد شاشة انشاء الحساب
    public void Sing_Up_BTN ( View view )
    {
        /*
            هون في حالة المستخدم لما يسجل باستعمال الايميل كان ام او طبيب لو ما حدد صفته رح بعرض اله

            مسج انه لازم يحدد صفته قبل ما يكمل اما اذا كان محدد فعادي بخليه يكمل من دون اي مشاكل
        */
        if ( ! binding . MomRBTN . isChecked ( ) && ! binding . DoctorRBTN . isChecked ( ) )
            Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;

        // هون كونه بس الام مطلوب منها الاسم بس فما رح يكون في الها بيانات تعبيها لهيك حاكي اله اذا المستخدم كان دكتور انقله لشاشة البيانات عشان يعبي البيانات اللازمه
        if ( binding . DoctorRBTN . isChecked ( ) )
        {
            Intent intent = new Intent ( this , Doctor_Data_Activity . class ) ;
            startActivity ( intent ) ;
        }

        else if ( binding . MomRBTN . isChecked ( ) )
        {
            Intent intent = new Intent (this , Mother_Activity. class ) ;

            startActivity ( intent ) ;
        }
    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make ( binding . Constraint , Message , 7000 ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf ( ContextCompat. getColor ( this , R . color . Snack_bar_BG_Color ) ) ) ;

        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById ( com . google . android . material . R . id . snackbar_text ) ;

        textView . setSingleLine ( false ) ;

        textView . setTextColor ( ContextCompat . getColor ( this , R . color . white ) ) ;

        textView . setTextSize ( 15 ) ;

        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        ViewGroup . MarginLayoutParams marginLayoutParams = ( ViewGroup.MarginLayoutParams ) snackbarView . getLayoutParams ( ) ;

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