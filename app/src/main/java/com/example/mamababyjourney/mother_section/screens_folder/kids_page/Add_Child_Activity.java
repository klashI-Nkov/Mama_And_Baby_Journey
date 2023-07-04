package com.example.mamababyjourney.mother_section.screens_folder.kids_page;

import com.example.mamababyjourney.databinding.ActivityMotherSectionKidsPageAddChildActivityBinding;
import com.example.mamababyjourney.mother_section.Mother_Activity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.UploadTask;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;

import android.graphics.Color;
import android.provider.MediaStore;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import java.util.Objects;
import android.net.Uri;

@SuppressLint ( { "InflateParams" , "IntentReset" } )
@SuppressWarnings ( { "deprecation" , "SameParameterValue" , "SpellCheckingInspection" } )
public class Add_Child_Activity extends AppCompatActivity
{
    ActivityMotherSectionKidsPageAddChildActivityBinding binding ;

    // هاد المتغير بخزن فيه الصوره الي بختارها من الاستديو
    Uri uri = null ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityMotherSectionKidsPageAddChildActivityBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        // هاد الكود بتنفذ بس تضغظ على صورة الكاميرا الي موجوده جنب الصوره والي بعمله هاد الكود الي جوا انه بفتح النا الاستديو عشان نختار صوره منه
        binding . PickImageBTN . setOnClickListener ( view ->
        {
            Intent intent = new Intent (Intent . ACTION_PICK ,MediaStore . Images . Media . EXTERNAL_CONTENT_URI ) ;
            intent . setType ( "image/*" ) ;
            startActivityForResult (intent ,1 ) ;
        });
    }

    public void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super . onActivityResult (requestCode ,resultCode ,data ) ;

        if ( resultCode == RESULT_OK && requestCode == 1 && data != null )
        {
            // هون بجيب الصوره الي اختارها من الاستديو و بخزنها في المتغير الي اسمه uri وبعدها بحطها بدل الصوره الي بتكون قبل ما نختار الصوره
            uri = data . getData ( ) ;
            binding . childImageView . setImageURI ( uri ) ;
        }
    }

    public void Save_BTN ( View view )
    {
        // هون بشيك اذا كان حط الاسم و اختار الصوره اذا عمل هيك بستدعي فنكشن ال Add_Chile_Data اذا ما عمل بس شي واحد من الي حكيته بظهر اله المسج الي تحت
        if ( uri != null && !binding . NameEditText . getText ( ) . toString ( ) . isEmpty ( ) )
            Add_Chile_Data (uri ) ;
        else
            Snack_Bar ( "يجب كتابة الاسم و اختيار صوره قبل الضغظ على زر الحفظ" ) ;
    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make (binding . Constraint ,Message ,7000 ) ;

        int color = Color.parseColor ( "#292929" ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;

        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById (com . google . android . material . R . id . snackbar_text ) ;

        textView . setSingleLine ( false ) ;

        textView . setTextColor ( ContextCompat . getColor (this ,R . color . white ) ) ;

        textView . setTextSize ( 15 ) ;

        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        ViewGroup . MarginLayoutParams marginLayoutParams = ( ViewGroup . MarginLayoutParams ) snackbarView . getLayoutParams ( ) ;

        marginLayoutParams . setMargins
        (
            marginLayoutParams . leftMargin ,
            marginLayoutParams . topMargin ,
            marginLayoutParams . rightMargin ,
            65
        );

        snackbarView . setLayoutParams ( marginLayoutParams ) ;

        snackbar . show ( ) ;
    }

    @SuppressWarnings ( "CodeBlock2Expr" )
    private void Add_Chile_Data ( Uri uri )
    {

        final ProgressDialog progressDialog = new ProgressDialog(this ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;
        progressDialog . show ( ) ;

        // هون انا بروح على الفايرستورج و بعمل مجلد اسمه بكون قيمة ال id تبع الام واسم الصوره بكون هو اسم الطفل الي حطيناه
        StorageReference imageRef = FirebaseStorage . getInstance ( ) . getReference ( ) . child
        (Mother_Activity . id + "/" + binding . NameEditText . getText ( ) . toString ( ) ) ;

        // هون بعمل مهمة رفع ملف وبحط المهمه امر برفع الصوره الي اجت لهاد الفنكشن في المتغير الي اسمه Uri
        UploadTask uploadTask = imageRef . putFile (uri ) ;

        uploadTask . addOnSuccessListener
        (
            taskSnapshot ->
            {
                imageRef . getDownloadUrl ( ) . addOnSuccessListener
                (
                    downloadUri ->
                    {
                        // هون بس ترفع الصوره بدخل هون وبخزن في الفايرستور الرابط الي اتخزن فيه الصوره عشان بس بدي اجيبها اوصل الها بكل سهوله من خلال الرابط تبعها

                        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

                        // هون انا بعمل ماب و بخزن فيها اسم الطفل ورابط الصوره عشان اخزنهم مع بيانات الام في الفايرستور في دكيزمنت خاص بالطفل وبكون باسمه
                        HashMap < String , Object > data = new HashMap < > ( ) ;
                        data . put ( "Child Name" , binding . NameEditText . getText ( ) . toString ( ) );
                        data . put ( "imageUrl" , downloadUri . toString ( ) ) ;

                        db . collection ("/mothers/1/Childrns" ) . document (binding . NameEditText . getText ( ) . toString ( ) ) . set ( data , SetOptions . merge ( ) ) . addOnCompleteListener (task ->
                        {
                            if ( task . isComplete ( ) )
                            {
                                // هون بس يخزن بيانات الطفل برجع على شاشة الاطفال
                                Intent intent = new Intent (this ,Kids . class ) ;

                                intent . setData ( uri ) ;

                                intent . putExtra ("Name",binding . NameEditText . getText ( ) . toString ( ) ) ;

                                setResult (RESULT_OK ,intent ) ;

                                progressDialog . dismiss ( ) ;

                                finish ( ) ;
                            }
                        });
                    }
                );
            }
        );
    }
}