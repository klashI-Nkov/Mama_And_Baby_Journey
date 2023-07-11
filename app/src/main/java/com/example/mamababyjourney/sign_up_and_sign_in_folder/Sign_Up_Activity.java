package com.example.mamababyjourney.sign_up_and_sign_in_folder;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderSignUpActivityBinding;
import com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page.Doctor_Data_Activity;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.SetOptions;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import com.google.android.gms.tasks.Task;
import android.content.res.Configuration;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import android.provider.MediaStore;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.TextView;
import android.graphics.Color;
import android.view.ViewGroup;
import android.content.Intent;
import java.util.Objects;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import android.net.Uri;

@SuppressWarnings ( { "FieldMayBeFinal" , "ConstantConditions" , "IfStatementWithIdenticalBranches" , "deprecation" } )
@SuppressLint ( { "ClickableViewAccessibility" , "IntentReset" , "SuspiciousIndentation" } )

public class Sign_Up_Activity extends AppCompatActivity
{

    private ActivitySignUpAndSignInFolderSignUpActivityBinding binding ;

    // هاد المتغير بخزن فيه الصوره الي بختارها من الاستديو
    private Uri uri = null ;

    // هون انا بمعل زي ما تقولي مسج بتظهر للمستخدم بتقله الرجاء الانتظار يعني يستنى لحد ما تتخزن الداتا في الفاير بيس
    ProgressDialog progressDialog ;

    Intent intent ;

    /*
        هدول ال 3 متغيرات
        الاول بخزن فيه الباس الي بتنكتب في ال edit text تبع الباس
        الثاني بخزن فيه الايميل الي بنكتب في ال edit text تبع الايميل
        الثالث بخزن فيه الاسم الي بنكتب في ال edit text تبع الاسم
        الرابع بحط فيه رابط الصوره الي بحطها المستخدم
    */
    private String password , email , name;


    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderSignUpActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        // هون انا بحدد شو المسج الي رح تظهر
        progressDialog = new ProgressDialog (this ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;

        // هاد بتنفذ لما اضغط على ال Radio button الي مكتوب فيه ام
        binding . MomRBTN . setOnTouchListener ( ( view , motionEvent ) ->
        {
            binding . userImageView . setImageResource ( R . drawable . images_female_user_image );
            binding . s . setVisibility ( View . VISIBLE ) ;
            return false;
        } );

        // هاد بتنفذ لما اضغط على ال Radio button الي مكتوب فيه طبيب
        binding . DoctorRBTN . setOnTouchListener ( ( view , motionEvent ) ->
        {
            binding . userImageView . setImageResource ( R . drawable . images_male_user_image );
            binding . s . setVisibility ( View . VISIBLE ) ;
            return false;
        });

        // هاد بتنفذ لما اضغط على ال Radio button الي مكتوب فيه طبيبه
        binding . FDoctorRBTN . setOnTouchListener ( ( view , motionEvent ) ->
        {
            binding . userImageView . setImageResource ( R . drawable . images_female_user_image );
            binding . s . setVisibility ( View . VISIBLE ) ;
            return false;
        });

        // هاد بتنفذ لما اضغط على صورة الكاميرا الي جنب الصوره الشخصيه
        binding . PickUserImageBTN . setOnClickListener ( view ->
        {
            @SuppressLint ( "IntentReset" ) Intent intent = new Intent (Intent . ACTION_PICK ,MediaStore. Images . Media . EXTERNAL_CONTENT_URI ) ;
            intent . setType ( "image/*" ) ;
            startActivityForResult (intent ,1 ) ;
        });

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


    // هاد بتنفذ عند لما نكبس على زر انشاء الحساب و وظفيته انه ينقل المستخدم للشاشه الي بعد شاشة انشاء الحساب
    public void Sing_Up_BTN ( View view )
    {

        password = binding . UPasswordEditText . getText ( ) . toString ( ) ;
        email    = binding . UEmailEditText    . getText ( ) . toString ( ) ;
        name     = binding . NameEditText      . getText ( ) . toString ( ) ;

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
        if ( binding . MomRBTN . isChecked ( ) || binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( ) )
        {
            // هون اذا كل الحقول معبية رح يدخل ينفذ الي جوا الاف ولو كان واحد من الحقول فاضي رح يعرض اله المسج الي تحت
            if ( all_Fields_Are_Filled_In )
            {

                // هون في حالة كان طول الباس اقل من 6 خانات رح يعرض اله المسج الي تحت
                if ( ! password_Length_Is_Correct )
                    Snack_Bar ( "يجب ان لا يقل طول كلمة المرور عن \u00206\u0020 خانات" ) ;


                // هون في حالة كان الايميل مش مكتوب صح رح يعرض اله المسج الي تحت
                if ( ! email_Format_Is_Correct )
                    Snack_Bar ( "صيغية الايميل الذي ادخلته غير صحيحه يرجى كتابة الايميل بشكل صحيح" ) ;


                /*
                   هون اذا كل شي اموره تمام انه عبى كل الحقول و كتب الايميل صح و طول الباس مش اقل من

                   6 بستدعي الفنكشن الي بعمل الي حساب وبضيف البيانات في الفايرستور وببعثله الايميل و الباس
                */
                if ( is_All_Done )
                    Create_Account_With_Email ( ) ;
            }
            else
                Snack_Bar ( "يرجى تعبئة جميع الحقول قبل المتابعة" ) ;
        }
        else
            Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;

    }

    // هاد بتنفذ لما اضغط على انشاء حساب باستخدام جوجل
    public void Sing_Up_By_Google ( View view)
    {
        // هون بقله اذا المسخدم حدد الصفه نفذ الي جوا الاف لو ما حدد اعرض اله المسج الي تحت
        if ( binding . MomRBTN . isChecked ( ) || binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( )  )
        {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestIdToken ( getString (R . string . default_web_client_id ) ) . requestEmail ( ) . build ( ) ;

            GoogleSignInClient mClient = GoogleSignIn . getClient (this ,googleSignInOptions ) ;

            // هون انا بظهر المربع الي بختار منه الايميل الي بدي استعمله عشان اعمل حساب في التطبيق
            Intent intent = mClient . getSignInIntent ( ) ;
            startActivityForResult ( intent , 2 );
        }
        else
            Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;
    }

    public void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super . onActivityResult (requestCode ,resultCode ,data ) ;

        if ( resultCode == RESULT_OK  )
        {
            switch ( requestCode )
            {
                // هاد بتنفذ لما نختار صوره من الاستديو
                case 1 :
                {
                    if ( data != null )
                    {
                        // هون بجيب الصوره الي اختارها من الاستديو و بخزنها في المتغير الي اسمه uri وبعدها بحطها بدل الصوره الي بتكون قبل ما نختار الصوره
                        uri = data . getData ( ) ;

                        binding . userImageView . setImageURI ( uri ) ;
                    }
                    break ;
                }

                // هاد بتنفذ لما نعمل حساب باستخدام جوجل
                case 2 :
                {

                    progressDialog . show ( ) ;

                    Task < GoogleSignInAccount > task = GoogleSignIn . getSignedInAccountFromIntent (data ) ;

                    try
                    {
                        GoogleSignInAccount account = task . getResult ( ApiException . class ) ;

                        AuthCredential credential = GoogleAuthProvider . getCredential (account . getIdToken ( ) ,null ) ;

                        FirebaseAuth . getInstance ( )
                        .signInWithCredential (credential )
                        .addOnCompleteListener (this ,authResultTask ->
                        {
                            if ( authResultTask . isSuccessful ( ) )
                            {
                                email = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;
                                name  = FirebaseAuth  . getInstance ( ) . getCurrentUser ( ) . getDisplayName ( ) ;

                                // هاد بتنفذ في حالة كان الي بعمل الحساب دكتور
                                if ( binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( ) )
                                {
                                    // هون احنا بنجهز ال intent لحتى ينقلنا لشاشة الدكتور
                                    intent = new Intent (Sign_Up_Activity . this ,Doctor_Data_Activity . class ) ;

                                    // هون بشيك هل هي طبيب او طبيبه اذا كان طبيب رح يخزن في هاد user_Kind المتغير طبيب اذا طبيبه رح يخزن طبيبه
                                    String user_Kind = binding . DoctorRBTN . isChecked ( ) ?
                                           binding . DoctorRBTN . getText ( ) + "" : binding . FDoctorRBTN . getText ( ) + "" ;

                                    // هون اذا ما تم اختيار صوره من الاستديو مباشره بستعمل الصوره الي على الايميل
                                    if ( uri == null )
                                    uri = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getPhotoUrl ( ) ;

                                    /*
                                        هون انا بستدعي الفنكشن الي بضيف الي في الفايرستور بيانات الدكتور الي انعمل اله حساب وببعث اله

                                         1- اسم ال id الي بدي اضيفه لبيانات االمستخدم
                                         2- اسم ال collection الي بدي اضيف فيها بيانات المستخدم
                                         3- صفة المستخدم

                                         4- نوع طريقة انشاء الحساب عشان اخزنها مع بيانات الدكتور مشان ي منار لو ما كان مختار صوره من الاستديو وعامل
                                         حساب باستخدام جوجل من خلال هاد المتغير اعرف انه مستخدم حساب جوجل لعمل حساب و مش مختار صوره من
                                         الاستديو عشان بس يعمل تسجيل دخول المره الجاي استعمل صورة ايميله بدل الصوره الافتراضيه
                                    */
                                    Add_Doctor_Or_Mother_Data_To_firestore ("Doctors" ,user_Kind , "Sing Up By Google" ) ;
                                }

                                // هاد بتنفذ في حالة كان الي بعمل الحساب ام والي بصير جواته نفس الي بصير في حالة كان الي بعمل الحساب الام
                                if ( binding . MomRBTN    . isChecked ( ) )
                                {
                                    intent = new Intent (Sign_Up_Activity . this ,Mother_Activity . class ) ;

                                    // هون اذا ما تم اختيار صوره من الاستديو مباشره بستعمل الصوره الي على الايميل
                                    if ( uri == null )
                                    uri = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getPhotoUrl ( ) ;

                                    Add_Doctor_Or_Mother_Data_To_firestore ("Mothers" ,binding . MomRBTN . getText ( ) + "" , "Sing Up By Google" ) ;
                                }
                            }
                        });
                    }
                    catch ( ApiException a )
                    {
                        throw new RuntimeException ( a ) ;
                    }

                    break ;
                }
            }
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

    // هاد الفنكشن بتنفذ لما اضغط على زر انشاء الحساب
    private void Create_Account_With_Email ( )
    {
        progressDialog . show ( ) ;

        // اول شي بروح على الفايربيس بتاكد انه الايميل الي بده يسجل فيه المستخدم مش موجود في الفايربيس
        FirebaseAuth . getInstance ( )
        .fetchSignInMethodsForEmail (email )
        .addOnCompleteListener (task ->
        {
            // هون بشيك اذا الايميل مش موجود ادخل ونفذ الي جوا الاف غير هيك اعرض اله المسج الي تحت الي جوا ال else
            if ( task . getResult ( ) . getSignInMethods ( ) . isEmpty ( ) )
            {
                // هون اذا الايميل مش موجود مباشره بقله اعمل الي حساب باستخدام الباس و الايميل الي اعطاك اياهم اليزور
                FirebaseAuth . getInstance ( ) . createUserWithEmailAndPassword (email ,password ) . addOnCompleteListener (this ,Task ->
                {
                    if ( Task . isComplete ( ) )
                    {
                        // هاد بتنفذ في حالة كان الي بعمل الحساب دكتور
                        if ( binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( ) )
                        {
                            // هون احنا بنجهز ال intent لحتى ينقلنا لشاشة الدكتور
                            intent = new Intent (Sign_Up_Activity . this ,Doctor_Data_Activity . class ) ;

                            // هون بشيك هل هي طبيب او طبيبه اذا كان طبيب رح يخزن في هاد user_Kind المتغير طبيب اذا طبيبه رح يخزن طبيبه
                            String user_Kind = binding . DoctorRBTN . isChecked ( ) ?
                                    binding . DoctorRBTN . getText ( ) + "" : binding . FDoctorRBTN . getText ( ) + "" ;

                            /*
                                هون انا بستدعي الفنكشن الي بضيف الي في الفايرستور بيانات الدكتور الي انعمل اله حساب وببعث اله

                                 1- اسم ال id الي بدي اضيفه لبيانات االمستخدم
                                 2- اسم ال collection الي بدي اضيف فيها بيانات المستخدم
                                 3- صفة المستخدم
                            */
                            Add_Doctor_Or_Mother_Data_To_firestore ("Doctors" ,user_Kind ,"Sing Up By Email" ) ;
                        }

                        // هاد بتنفذ في حالة كان الي بعمل الحساب ام والي بصير جواته نفس الي بصير في حالة كان الي بعمل الحساب الام
                        if ( binding . MomRBTN    . isChecked ( ) )
                        {
                            intent = new Intent (Sign_Up_Activity . this ,Mother_Activity . class ) ;

                            Add_Doctor_Or_Mother_Data_To_firestore ("Mothers" ,binding . MomRBTN . getText ( ) + "" ,"Sing Up By Email" ) ;
                        }
                    }
                });
            }
            else
            {
                progressDialog . dismiss ( ) ;
                Snack_Bar ("هذا الايميل مستخدم يرجى اختيار ايميل اخر" ) ;
            }
        });
    }

    // هاد الفنكشن الي بضيف الي داتا السمتخدم في الفاير ستور
    private void Add_Doctor_Or_Mother_Data_To_firestore ( String collection_Name , String user_Kind , String signUp_Method )
    {
        // هون انا بشيك اذا المستخدم اختار صوره بقله ادخل جوا الاف و ابعت الصوره للشاشة الي رح توديني الها شاشة انشاء الحسا وقله انه الصوره تم اختيارها من الاستديو
        if ( uri != null )
        {
            // هون ببعت الصوره
            intent . setData ( uri ) ;

            // هون بستدعي الفنكشن الي بضيف الي الصوره على الفايرستورج
            Add_Image_To_Firestorage (collection_Name ) ;

            // هون بقله تم اختيار الصوره من الاستديو
            intent . putExtra ("image form ?" ,"uri" ) ;
        }
        else
            intent . putExtra ("image form ?" ,"Drawable" ) ;

        // هون انا ببعت مع ال intent مسج بوضح انا في اي شاشة كنت قبل ما انتقل للشاشة الي رح توديني الها شاشة انشاء الحساب
        intent . putExtra ( "action" , "signup" ) ;

        // هون انا ببعت اسم المستخدم للشاشه الي رح توديني الها شاشة انشاء الحساب
        intent . putExtra ( "name" , name ) ;

        // هون انا ببعت ايميل المستخدم للشاشه الي رح توديني الها شاشة انشاء الحساب
        intent . putExtra ( "email" , email ) ;

        // هون انا بعمل HashMap عشان احط فيها داتا المستخدم الي انعمل اله حساب
        HashMap < String , Object > data = new HashMap < > ( ) ;

        // هون بحط باقي الداتا الخاصه بالنستخدم الي انعمل اله حساب في هاي data ال HashMap
        data . put ( "name" , name ) ;
        data . put ( "صفة المستخدم" , user_Kind ) ;
        data . put ( "image Url" , "" ) ;
        data . put ( "signUp Method" , signUp_Method ) ;
        data . put ( "email" , email ) ;


        // هون لما استعدينا الفنكشن الي احنا فيه حسب صفة المستخدم حددنا اسم ال collection الي بدي اضيف فيها الداتا و الي بعثنها اسمها لهاد الفنكشن في هاد collection_Name المتغير
        FirebaseFirestore . getInstance ( ) . collection (collection_Name ) . document (email ) . set ( data ) ;

        // هون انا بلغي الاشعار الي بحكي يرجى الانتظار
        progressDialog . dismiss ( ) ;

        // وهون اخر شي انا بوجهه للشاشه المطلوبه حسب صفته
        startActivity (intent ) ;
    }

    // هاد الي بخزن الي الصوره الي اختاره المستخدم في الفايرستورج
    private void Add_Image_To_Firestorage ( String collection_Name )
    {
        // هون انا بروح على الفايرستورج و بعمل مجلد اسمه images واسم الصوره بكون user number + اال id تبع المستخدم
        StorageReference imageRef = FirebaseStorage . getInstance ( ) . getReference ( ) . child ( "images" + "/user image" ) ;

        // هون بعد ما تنرفع الصوره على الفايرستورج بخزن الرابط تبعها في هاد image_Url المتغير
        imageRef . putFile (uri ).addOnSuccessListener (taskSnapshot ->

        imageRef . getDownloadUrl ( ) . addOnSuccessListener (downloadUri ->
        {
            // هون انا بعمل HashMap وبحط فيها داتا المستخدم الي انعمل اله حساب
            HashMap < String , Object > data = new HashMap < > ( ) ;
            data . put ( "image Url" , downloadUri . toString ( ) ) ;

            // هون لما استعدينا الفنكشن الي احنا فيه حسب صفة المستخدم حددنا اسم ال collection الي بدي اضيف فيها الداتا و الي بعثنها اسمها لهاد الفنكشن في هاد collection_Name المتغير
            FirebaseFirestore . getInstance ( ) . collection (collection_Name ) . document (email ) . set ( data , SetOptions . merge ( ) ) ;
        } ));
    }
}