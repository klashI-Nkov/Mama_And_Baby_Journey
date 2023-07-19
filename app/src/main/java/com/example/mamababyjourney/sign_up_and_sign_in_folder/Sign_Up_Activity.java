package com.example.mamababyjourney.sign_up_and_sign_in_folder;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderSignUpActivityBinding;
import com.example.mamababyjourney.doctor_section.Doctor_Activity;
import com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page.Doctor_Data_Activity;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.firestore.FirebaseFirestore;
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

@SuppressWarnings ( { "FieldMayBeFinal" , "ConstantConditions" , "IfStatementWithIdenticalBranches" , "deprecation" , "SpellCheckingInspection" , "RedundantSuppression" , "UnnecessaryUnicodeEscape"} )
@SuppressLint ( { "ClickableViewAccessibility" , "IntentReset" , "SuspiciousIndentation" } )

public class Sign_Up_Activity extends AppCompatActivity
{

    private ActivitySignUpAndSignInFolderSignUpActivityBinding binding ;

    private Uri uri = null ;

    ProgressDialog progressDialog ;

    Intent intent ;

    private String password , email , name;


    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderSignUpActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        progressDialog = new ProgressDialog (this ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;

        binding . MomRBTN . setOnTouchListener ( ( view , motionEvent ) ->
        {
            binding . userImageView . setImageResource ( R . drawable . images_female_user_image );
            binding . s . setVisibility ( View . VISIBLE ) ;
            return false;
        } );

        binding . DoctorRBTN . setOnTouchListener ( ( view , motionEvent ) ->
        {
            binding . userImageView . setImageResource ( R . drawable . images_male_user_image );
            binding . s . setVisibility ( View . VISIBLE ) ;
            return false;
        });

        binding . FDoctorRBTN . setOnTouchListener ( ( view , motionEvent ) ->
        {
            binding . userImageView . setImageResource ( R . drawable . images_female_user_image );
            binding . s . setVisibility ( View . VISIBLE ) ;
            return false;
        });

        binding . PickUserImageBTN . setOnClickListener ( view ->
        {
            @SuppressLint ( "IntentReset" ) Intent intent = new Intent (Intent . ACTION_PICK ,MediaStore. Images . Media . EXTERNAL_CONTENT_URI ) ;
            intent . setType ( "image/*" ) ;
            startActivityForResult (intent ,1 ) ;
        });

        Theme ( ) ;
    }

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

    public void Sing_Up_BTN ( View view )
    {

        password = binding . UPasswordEditText . getText ( ) . toString ( ) ;
        email    = binding . UEmailEditText    . getText ( ) . toString ( ) ;
        name     = binding . NameEditText      . getText ( ) . toString ( ) ;

        boolean email_Format_Is_Correct = email . contains ( "@" ) && email . contains ( "." ) ;

        boolean password_Length_Is_Correct = password . length ( ) >= 6 ;

        boolean all_Fields_Are_Filled_In = ! password . isEmpty ( ) && ! email . isEmpty ( ) && ! name . isEmpty ( ) ;

        boolean is_All_Done = all_Fields_Are_Filled_In && email_Format_Is_Correct && password_Length_Is_Correct ;


        if ( binding . MomRBTN . isChecked ( ) || binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( ) )
        {
            if ( all_Fields_Are_Filled_In )
            {
                if ( ! password_Length_Is_Correct )
                    Snack_Bar ( "يجب ان لا يقل طول كلمة المرور عن \u00206\u0020 خانات" ) ;

                if ( ! email_Format_Is_Correct )
                    Snack_Bar ( "صيغية الايميل الذي ادخلته غير صحيحه يرجى كتابة الايميل بشكل صحيح" ) ;

                if ( is_All_Done )
                    Create_Account_With_Email ( ) ;
            }
            else
                Snack_Bar ( "يرجى تعبئة جميع الحقول قبل المتابعة" ) ;
        }
        else
            Snack_Bar ( "يرجى تحديد صفتك قبل المتابعه" ) ;

    }

    public void Sing_Up_By_Google ( View view)
    {
        if ( binding . MomRBTN . isChecked ( ) || binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( )  )
        {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestIdToken ( getString (R . string . default_web_client_id ) ) . requestEmail ( ) . build ( ) ;

            GoogleSignInClient mClient = GoogleSignIn . getClient (this ,googleSignInOptions ) ;

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
                                name  = FirebaseAuth  . getInstance ( ) . getCurrentUser ( ) . getDisplayName ( ) ;
                                email = FirebaseAuth  . getInstance ( ) . getCurrentUser ( ) . getEmail       ( ) ;

                                if ( binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( ) )
                                {
                                    intent = new Intent (Sign_Up_Activity . this , Doctor_Activity. class ) ;

                                    String user_Kind = binding . DoctorRBTN . isChecked ( ) ?
                                           binding . DoctorRBTN . getText ( ) + "" : binding . FDoctorRBTN . getText ( ) + "" ;

                                    if ( uri == null )
                                    uri = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getPhotoUrl ( ) ;

                                    Add_Doctor_Or_Mother_Data_To_firestore ("Doctors" ,user_Kind , "Sing Up By Google" ) ;
                                }

                                if ( binding . MomRBTN    . isChecked ( ) )
                                {
                                    intent = new Intent (Sign_Up_Activity . this ,Mother_Activity . class ) ;

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

    private void Create_Account_With_Email ( )
    {
        progressDialog . show ( ) ;

        FirebaseAuth . getInstance ( )
        .fetchSignInMethodsForEmail (email )
        .addOnCompleteListener (task ->
        {
            if ( task . getResult ( ) . getSignInMethods ( ) . isEmpty ( ) )
            {
                FirebaseAuth . getInstance ( ) . createUserWithEmailAndPassword (email ,password ) . addOnCompleteListener (this ,Task ->
                {
                    if ( Task . isComplete ( ) )
                    {
                        if ( binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( ) )
                        {
                            intent = new Intent (Sign_Up_Activity . this ,Doctor_Data_Activity . class ) ;

                            String user_Kind = binding . DoctorRBTN . isChecked ( ) ?
                                    binding . DoctorRBTN . getText ( ) + "" : binding . FDoctorRBTN . getText ( ) + "" ;

                            Add_Doctor_Or_Mother_Data_To_firestore ("Doctors" ,user_Kind ,"Sing Up By Email" ) ;
                        }

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

    private void Add_Doctor_Or_Mother_Data_To_firestore ( String collection_Name , String user_Kind , String signUp_Method )
    {
        if ( uri != null )
        {
            intent . setData ( uri ) ;
            Add_Image_To_Firestorage (collection_Name ) ;
            intent . putExtra ("image form ?" ,"uri" ) ;
        }
        else
            intent . putExtra ("image form ?" ,"Drawable" ) ;

        intent . putExtra ( "action" , "signup" ) ;

        intent . putExtra ( "name" , name ) ;

        intent . putExtra ( "email" , email ) ;

        HashMap < String , Object > data = new HashMap < > ( ) ;

        data . put ( "name" , name ) ;
        data . put ( "صفة المستخدم" , user_Kind ) ;
        data . put ( "image Url" , "" ) ;
        data . put ( "signUp Method" , signUp_Method ) ;
        data . put ( "email" , email ) ;

        if ( binding . DoctorRBTN . isChecked ( ) || binding . FDoctorRBTN . isChecked ( ) )
            data . put ( "workPlace ID" , "" ) ;

        FirebaseFirestore . getInstance ( ) . collection (collection_Name ) . document (email ) . set ( data ) ;

        progressDialog . dismiss ( ) ;

        startActivity (intent ) ;
    }

    private void Add_Image_To_Firestorage ( String collection_Name )
    {
        StorageReference imageRef = FirebaseStorage . getInstance ( ) . getReference ( ) . child ( "images" + "/user image" ) ;

        imageRef . putFile (uri ).addOnSuccessListener (taskSnapshot ->

        imageRef . getDownloadUrl ( ) . addOnSuccessListener (downloadUri ->
        {
            HashMap < String , Object > data = new HashMap < > ( ) ;
            data . put ( "image Url" , downloadUri . toString ( ) ) ;

            FirebaseFirestore . getInstance ( ) . collection (collection_Name ) . document (email ) . set ( data , SetOptions . merge ( ) ) ;
        } ));
    }
}