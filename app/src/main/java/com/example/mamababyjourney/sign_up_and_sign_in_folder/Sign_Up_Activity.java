package com.example.mamababyjourney.sign_up_and_sign_in_folder;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderSignUpActivityBinding;
import com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page.Doctor_Data_Activity;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.NonNull;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.view.View;
import java.util.HashMap;
import java.util.Objects;
import android.os.Bundle;


@SuppressWarnings ( { "FieldCanBeLocal" , "FieldMayBeFinal" , "SameParameterValue" , "IfStatementWithIdenticalBranches" } )
@SuppressLint ( "ClickableViewAccessibility" )
public class Sign_Up_Activity extends AppCompatActivity
{

    ActivitySignUpAndSignInFolderSignUpActivityBinding binding ;

    private String id = "1" ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderSignUpActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Buttons ( ) ;
        Theme ( ) ;

    }

    // هاد الفنكشن وهاد onConfigurationChanged عملتهم عشان اغير لون التكست لل Radio buttons حسب الثيم لانه حاولت اطبق عليهم ستايل يغيير لون الخط حسب الثيم ما زبط فانجبرت استعمل هاي الطريقه
    public void Theme ( )
    {

        Configuration configuration = getResources ( ) . getConfiguration ( ) ;

        int currentUiMode = configuration . uiMode & Configuration . UI_MODE_NIGHT_MASK ;

        int color ;

        if ( currentUiMode == Configuration . UI_MODE_NIGHT_YES )
        {
            color = Color.parseColor ( "#A2FFFFFF" ) ;
            binding . DoctorRBTN . setTextColor ( color ) ;
            binding . MomRBTN    . setTextColor ( color ) ;
        }
        else
        {
            color = Color.parseColor ( "#73000000" ) ;
            binding . DoctorRBTN . setTextColor ( color ) ;
            binding . MomRBTN    . setTextColor ( color ) ;
        }
    }

    @Override
    public void onConfigurationChanged ( @NonNull Configuration newConfig )
    {
        super . onConfigurationChanged ( newConfig ) ;
        int color ;
        // الشرط الي جوا الاف معناه انه روح جيب الثيم الحالي و شوف اذا هو دارك ادخل و نفذ الي جوا الاف واعطي الخط تاع ال Radio buttons الوان الثيم الدارك اذا الثيم الحالي مش دارك اعطيهم الوان الثيم الفاتح
        if ( ( getResources ( ) . getConfiguration ( ) . uiMode & Configuration . UI_MODE_NIGHT_MASK ) == Configuration . UI_MODE_NIGHT_YES )
        {
            color = Color.parseColor ( "#A2FFFFFF" ) ;
            binding . DoctorRBTN . setTextColor ( color ) ;
            binding . MomRBTN    . setTextColor ( color ) ;
        }
        else
        {
            color = Color.parseColor ( "#73000000" ) ;
            binding . DoctorRBTN . setTextColor ( color ) ;
            binding . MomRBTN    . setTextColor ( color ) ;
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
            if ( ! binding . NameEditText . getText ( ) . toString ( ) . isEmpty ( ) )
            {
                // هون انا بمعل زي ما تقولي مسج بتظهر للمستخدم بتقله الرجاء الانتظار يعني يستنى لحد ما تتخزن الداتا في الفاير بيس
                final ProgressDialog progressDialog = new ProgressDialog (this ) ;
                progressDialog . setMessage ( "يرجى الانتظار" ) ;
                progressDialog . show ( ) ;

                FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

                HashMap < String , Object > data = new HashMap < > ( ) ;
                data . put ( "Name", binding . NameEditText . getText ( ) + "" ) ;

                db . collection ("mothers" ) . document (id ) . set ( data ) . addOnCompleteListener ( task ->
                {
                    if ( task . isComplete ( ) )
                    {
                        Intent intent = new Intent (this ,Mother_Activity . class ) ;
                        intent . putExtra ("Id" ,id ) ;
                        progressDialog . dismiss ( ) ;
                        Update_Id ( ) ;
                        startActivity ( intent ) ;
                    }
                });
            }
            else
                Snack_Bar ( "يرجى كتابة الاسم قبل المتابعة" ) ;
        }
    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make ( binding . Constraint , Message , 7000 ) ;

        int color = Color.parseColor ( "#292929" ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;

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

    public void Update_Id ( )
    {
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        HashMap < String , Object > id = new HashMap < > ( ) ;
        id . put ( "Mother Id" , ++ Doctor_Data_Activity . id  ) ;
        db . collection ("id's" ) . document ("id" ) . update (id ) ;
    }
}