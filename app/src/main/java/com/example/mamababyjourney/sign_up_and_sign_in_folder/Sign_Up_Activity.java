package com.example.mamababyjourney.sign_up_and_sign_in_folder;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderSignUpActivityBinding;
import com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page.Doctor_Data_Activity;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import androidx.activity.result.contract.ActivityResultContracts;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.api.ApiException;
import androidx.activity.result.ActivityResultLauncher;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.GoogleAuthProvider;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import com.google.android.gms.tasks.Task;
import android.content.res.Configuration;
import com.example.mamababyjourney.R;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.widget.TextView;
import android.graphics.Color;
import android.view.ViewGroup;
import android.content.Intent;
import android.app.Activity;
import java.util.Objects;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;

@SuppressWarnings ( { "FieldMayBeFinal" , "ConstantConditions" , "IfStatementWithIdenticalBranches" , "CommentedOutCode" } )
@SuppressLint ( "ClickableViewAccessibility" )
public class Sign_Up_Activity extends AppCompatActivity
{

    ActivitySignUpAndSignInFolderSignUpActivityBinding binding ;

    private int mother_Id = 1 ;

    Intent intent ;

    GoogleSignInClient mClient ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderSignUpActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Theme ( ) ;
    }

    //هاد الفنكشن عملته عشان اغير لون خط ال Radio buttons حسب الثيم
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

    // هاد بتنفذ لما اضغط على انشاء حساب باستخدام جوجل
    public void Sing_Up_By_Google ( View view)
    {
        // هون بقله اذا المسخدم حدد الصفه نفذ الي جوا الاف لو ما حدد اعرض اله المسج الي تحت
        if ( binding.MomRBTN.isChecked ( ) || binding.DoctorRBTN.isChecked ( ) )
        {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestIdToken ( getString (R . string . default_web_client_id ) ) . requestEmail ( ) . build ( ) ;
            mClient = GoogleSignIn . getClient (this ,googleSignInOptions ) ;

            // login
            Intent intent = mClient . getSignInIntent ( ) ;
            activityResultLauncher . launch (intent ) ;
        }
        else
            Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;
    }

    private ActivityResultLauncher < Intent > activityResultLauncher = registerForActivityResult (new ActivityResultContracts . StartActivityForResult ( ) ,result ->
    {
        if ( result . getResultCode ( ) == Activity . RESULT_OK )
        {
            Intent data = result . getData ( ) ;
            Task < GoogleSignInAccount > task = GoogleSignIn . getSignedInAccountFromIntent (data ) ;

            try
            {
                GoogleSignInAccount account = task . getResult ( ApiException . class ) ;

                // auth
                AuthCredential credential = GoogleAuthProvider . getCredential (account . getIdToken ( ) ,null ) ;

                FirebaseAuth . getInstance ( ) . signInWithCredential (credential ) . addOnCompleteListener (this ,Task ->
                {
                    if ( Task . isSuccessful ( ) )
                    {
                        Snack_Bar (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getDisplayName ( ) ) ;
                    }
                });
            }
            catch ( ApiException a )
            {
                throw new RuntimeException ( a ) ;
            }
        }
    });

    // هاد بتنفذ عند لما نكبس على زر انشاء الحساب و وظفيته انه ينقل المستخدم للشاشه الي بعد شاشة انشاء الحساب
    public void Sing_Up_BTN ( View view )
    {
        /*
            هدول ال 3 متغيرات
            الاول بخزن فيه الباس الي بتنكتب في ال edit text تبع الباس
            الثاني بخزن فيه الايميل الي بنكتب في ال edit text تبع الايميل
            الثالث بخزن فيه الاسم الي بنكتب في ال edit text تبع الاسم
        */
        String password = binding . UPasswordEditText . getText ( ) . toString ( ) ;
        String email    = binding . UEmailEditText    . getText ( ) . toString ( ) ;
        String name     = binding . NameEditText      . getText ( ) . toString ( ) ;

        /*
            هدول ال 4 متغيرات عشان اتحقق من اكم شغله و كل واحد مبين من اسمه لشو هو

            الاول في حالة انكتب الايميل صح رح يتخزن فيه true غير هيك بتخزن فيه false

            الثاني في حالة كان طول الباس مش اقل من 6 خانات رح يتخزن فيه true غير هيك بتخزن فيه false

            الثالث في حالة ما كان في اي واحد من الحقول فاضي رح يتخزن فيه true غير هيك بتخزن فيه false

            الرابع في حالة كل المتغيرات ال 3 الي قبله مخزن فيهم true رح يتخزن فيه true لو واحد من ال 3 متغيرات مخزن فيه false رح يتخزن فيه false
        */
        boolean email_Format_Is_Correct = email . contains ( "@" ) && email . contains ( "." ) ;

        boolean password_Length_Is_Correct = password . length ( ) >= 6 ;

        boolean all_Fields_Are_Filled_In = ! password . isEmpty ( ) && ! email . isEmpty ( ) && ! name . isEmpty ( ) ;

        boolean is_All_Done = all_Fields_Are_Filled_In && email_Format_Is_Correct && password_Length_Is_Correct ;


        // هون في حالة المستخدم حدد صفته رح ينفذ الي جوا الاف ولو ما حدد رح بعرض اله مسج انه لازم يحدد صفته قبل ما يكمل قبل ما يكمل
        if ( binding . MomRBTN . isChecked ( ) || binding . DoctorRBTN . isChecked ( ) )
        {
            // هون اذا كل الحقول معبية رح يدخل ينفذ الي جوا الاف ولو كان واحد من الحقول فاضي رح يعرض اله المسج الي تحت
            if ( all_Fields_Are_Filled_In )
            {

                // هون في حالة كان طول الباس اقل من 6 خانات رح يعرض اله المسج الي تحت
                if ( ! password_Length_Is_Correct )
                    Snack_Bar ( "يجب ان لا يقل طول كلمة المرور عن 6 خانات" ) ;


                // هون في حالة كان الايميل مش مكتوب صح رح يعرض اله المسج الي تحت
                if ( ! email_Format_Is_Correct )
                    Snack_Bar ( "صيغية الايميل الذي ادخلته غير صحيحه يرجى كتابة الايميل بشكل صحيح" ) ;


                /*
                   هون اذا كل شي اموره تمام انه عبى كل الحقول و كتب الايميل صح و طول الباس مش اقل من

                   6 بستدعي الفنكشن الي بعمل الي حساب وبضيف البيانات في الفايرستور وببعثله الايميل و الباس
                */
                if ( is_All_Done )
                    Create_Account_With_Email ( binding . UEmailEditText . getText ( ) . toString ( ) , binding . UPasswordEditText . getText ( ) . toString ( ) ) ;
            }
            else
                Snack_Bar ( "يرجى تعبئة جميع الحقول قبل المتابعة" ) ;
        }
        else
            Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;

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
        id . put ( "mother Id" , ++mother_Id  ) ;
        db . collection ("ID's" ) . document ("id's" ) . update (id ) ;
    }

    private void Create_Account_With_Email ( String email , String password )
    {
        // هون انا بمعل زي ما تقولي مسج بتظهر للمستخدم بتقله الرجاء الانتظار يعني يستنى لحد ما تتخزن الداتا في الفاير بيس
        final ProgressDialog progressDialog = new ProgressDialog (this ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;
        progressDialog . show ( ) ;

        FirebaseAuth firebaseAuth = FirebaseAuth . getInstance ( ) ;

        firebaseAuth . createUserWithEmailAndPassword (email ,password ) . addOnCompleteListener (this ,Task ->
        {
            if ( Task . isComplete ( ) )
            {
                // هاد بتنفذ لما يكون الي بعمل الحساب دكتور
                if ( binding . DoctorRBTN . isChecked ( ) )
                {
                    intent = new Intent ( this , Doctor_Data_Activity . class ) ;
                }

                // هاد بتنفذ لما يكون الي بعمل الحساب ام
                if ( binding . MomRBTN . isChecked ( ) )
                {
                     intent = new Intent (this , Mother_Activity . class ) ;

                    FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

                    HashMap < String, Object > data = new HashMap <> ( ) ;
                    data . put ( "Name" , binding . NameEditText . getText ( ) + "" ) ;

                    db . collection ("mothers" ) . document (mother_Id + "" ) . set ( data ) ;

                    intent . putExtra ("Id" ,mother_Id ) ;

                    Update_Id ( ) ;
                }

                progressDialog . dismiss ( ) ;

                Snack_Bar ( "Authentication done and the user name is : " + firebaseAuth . getCurrentUser ( ) . getDisplayName () ) ;

                startActivity (intent ) ;
            }
            else
            {
                progressDialog . dismiss ( ) ;
                Snack_Bar ("Authentication filed" + Task . getException ( ) ) ;
            }
        } ) ;



        /*FirebaseAuth . getInstance ( ) . fetchSignInMethodsForEmail (email ) . addOnCompleteListener (task ->
        {
                if ( task . getResult ( ) . getSignInMethods ( ) . isEmpty ( ) )
                {

                }
                else
                {
                    //progressDialog . dismiss ( ) ;
                    Snack_Bar ("هذا الايميل مستخدم يرجى اختيار ايميل اخر" ) ;
                }
        } ) ;*/
    }
}