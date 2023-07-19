package com.example.mamababyjourney.sign_up_and_sign_in_folder;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderSingInActivityBinding;
import com.example.mamababyjourney.doctor_section.Doctor_Activity;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.GoogleAuthProvider;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import com.google.android.gms.tasks.Task;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import android.view.WindowManager;
import android.app.ProgressDialog;
import android.widget.TextView;
import android.content.Intent;
import android.view.ViewGroup;
import android.graphics.Color;
import java.util.Objects;
import android.view.View;
import android.os.Bundle;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings ( { "ConstantConditions" , "FieldMayBeFinal" , "deprecation" , "SpellCheckingInspection" , "RedundantSuppression"  } )
@SuppressLint ( "ClickableViewAccessibility" )
public class Sing_in_Activity extends AppCompatActivity
{
    ActivitySignUpAndSignInFolderSingInActivityBinding binding ;

    String password , email ;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderSingInActivityBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        progressDialog = new ProgressDialog (this ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;
    }

    public void sing_In_BTN ( View view )
    {
        password = binding . passwordEditText . getText ( ) + "" ;
        email    = binding . EmailEditText    . getText ( ) + "" ;

        progressDialog . show ( ) ;

        FirebaseAuth . getInstance ( )
        .fetchSignInMethodsForEmail (email )
        .addOnCompleteListener (task ->
        {
            if ( ! task . getResult ( ) . getSignInMethods ( ) . isEmpty ( ) )
            {
                FirebaseAuth . getInstance ( )
                .signInWithEmailAndPassword (email ,password )
                .addOnCompleteListener (this ,Task ->
                {
                    if ( Task . isSuccessful ( ) )
                        Get_User_Data ( ) ;
                })
                .addOnFailureListener ( e ->
                {
                    progressDialog . dismiss ( ) ;

                    if ( e . getMessage ( ) . equals ( "The password is invalid or the user does not have a password." ) )
                        Snack_Bar ("كلمة المرور خاطئه يرجى التاكد من كلمة المرور و المحاوله مره اخرى" ) ;
                });
            }
            else
            {
                progressDialog . dismiss ( ) ;
                Snack_Bar ("لايوجد حساب مرتبط بهذا الايميل" );
            }
        });
    }

    public void Sing_In_By_Google ( View view)
    {
        progressDialog . show ( ) ;

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN )
        .requestIdToken ( getString (R . string . default_web_client_id ) ) . requestEmail ( ) . build ( ) ;

        GoogleSignInClient mClient = GoogleSignIn . getClient (this ,googleSignInOptions ) ;

        // login
        Intent intent = mClient . getSignInIntent ( ) ;
        startActivityForResult ( intent , 2 );
    }

    public void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super . onActivityResult (requestCode ,resultCode ,data ) ;

        if ( resultCode == RESULT_OK  )
        {
            Task < GoogleSignInAccount > task = GoogleSignIn . getSignedInAccountFromIntent (data ) ;

            try
            {
                GoogleSignInAccount account = task . getResult ( ApiException . class ) ;

                // auth
                AuthCredential credential = GoogleAuthProvider . getCredential (account . getIdToken ( ) ,null ) ;

                FirebaseAuth . getInstance ( )
                .signInWithCredential (credential )
                .addOnCompleteListener (this ,Task ->
                {
                    if ( Task . isSuccessful ( ) )
                    {
                        email = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

                        Get_User_Data ( ) ;
                    }
                });
            }
            catch ( ApiException a )
            {
                throw new RuntimeException ( a ) ;
            }
        }
    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make (binding . Constraint ,Message ,7000 ) ;

        int color = Color.parseColor ("#292929" ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;

        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById (com . google . android . material . R . id . snackbar_text ) ;

        textView . setSingleLine ( false ) ;

        textView . setTextColor ( ContextCompat . getColor (this ,R . color . white ) ) ;

        textView . setTextSize ( 15 ) ;

        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        ViewGroup. MarginLayoutParams marginLayoutParams = ( ViewGroup.MarginLayoutParams ) snackbarView . getLayoutParams ( ) ;

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

    private void Get_User_Data ( )
    {
        List < String > collection_Names = Arrays  . asList ( "Doctors" , "Mothers" );

        for ( String collection_Name : collection_Names )
        {
            FirebaseFirestore . getInstance ( )
            .collection (collection_Name )
            .get ( ) . addOnCompleteListener (task ->
            {
                if ( task . isSuccessful ( ) )
                {
                    FirebaseFirestore . getInstance ( )
                    .collection (collection_Name )
                    .document   (email )
                    .get ( ) . addOnSuccessListener (documentSnapshot ->
                    {
                        if ( documentSnapshot . exists ( ) )
                        {
                            if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيب" ) || documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيبة" ) )
                            {
                                Intent intent = new Intent (Sing_in_Activity . this , Doctor_Activity . class ) ;
                                progressDialog . dismiss ( ) ;
                                startActivity (intent ) ;
                                finish ( ) ;
                            }

                            if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "أم" ) )
                            {
                                Intent intent = new Intent (Sing_in_Activity . this ,Mother_Activity . class ) ;
                                intent . putExtra ("action" ,"signing" ) ;
                                progressDialog . dismiss ( ) ;
                                startActivity (intent ) ;
                                finish ( ) ;
                            }
                        }
                    });
                }
            });
        }
    }
}