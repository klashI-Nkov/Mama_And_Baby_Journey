package com.example.mamababyjourney.mother_section.screens_folder.kids_page;

import com.example.mamababyjourney.databinding.ActivityMotherSectionKidsPageAddChildActivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.SetOptions;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import android.provider.MediaStore;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import java.util.Objects;
import android.net.Uri;

@SuppressLint ( { "InflateParams" , "IntentReset" } )
@SuppressWarnings ( { "deprecation" , "SameParameterValue" , "CodeBlock2Expr" , "ConstantConditions" } )
public class Add_Child_Activity extends AppCompatActivity implements AdapterView. OnItemSelectedListener
{
    ActivityMotherSectionKidsPageAddChildActivityBinding binding ;

    // هاد المتغير بخزن فيه الصوره الي بختارها من الاستديو
    Uri uri = null ;

    // هاد الي بخزن فيه جنس الطفل
    String gender ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityMotherSectionKidsPageAddChildActivityBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        // هاد الكود بتنفذ بس تضغظ على صورة الكاميرا الي موجوده جنب الصوره والي بعمله هاد الكود الي جوا انه بفتح النا الاستديو عشان نختار صوره منه
        binding . PickChildImageBTN . setOnClickListener ( view ->
        {
            Intent intent = new Intent (Intent . ACTION_PICK ,MediaStore . Images . Media . EXTERNAL_CONTENT_URI ) ;
            intent . setType ( "image/*" ) ;
            startActivityForResult (intent ,1 ) ;
        });

        Adapter_Initialization ( ) ;
    }


    public void Save_BTN ( View view )
    {
        // هون بشيك اذا كان حط الاسم و اختار الصوره اذا عمل هيك بستدعي فنكشن ال Add_Chile_Data اذا ما عمل بس شي واحد من الي حكيته بظهر اله المسج الي تحت
        if ( !binding . NameEditText . getText ( ) . toString ( ) . isEmpty ( ) && gender != null )
            Add_Chile_Data ( ) ;
        else
            Snack_Bar ( "يجب كتابة الاسم و اختيار الجنس و الصوره قبل الضغظ على زر الحفظ" ) ;
    }

    private void Add_Chile_Data (  )
    {

        final ProgressDialog progressDialog = new ProgressDialog(this ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;
        progressDialog . show ( ) ;

        // هون بس يخزن بيانات الطفل برجع على شاشة الاطفال
        Intent intent = new Intent (this ,Kids . class ) ;

        // هون انا بعمل HashMap و بخزن فيها اسم الطفل ورابط الصوره عشان اخزنهم مع بيانات الام في الفايرستور في دكيزمنت خاص بالطفل وبكون باسمه
        HashMap < String , Object > data = new HashMap < > ( ) ;
        data . put ( "Child Name" , binding . NameEditText . getText ( ) . toString ( ) ) ;
        data . put ( "gender" , gender ) ;

        // هون بشيك اذا المستخدم اختار صوره وثتها بضيف رابطها في الدكيومنت الخاص بالطفل واذا ما اختار ما يضيف اي رابط في دكيومنت الطفل
        if ( uri != null )
        {
            // هون انا بروح على الفايرستورج و بعمل مجلد اسمه images واسم الصوره بكون هو اسم الطفل الي حطيناه
            StorageReference imageRef = FirebaseStorage . getInstance ( ) . getReference ( ) . child
            ("images" + "/" + binding . NameEditText . getText ( ) . toString ( ) ) ;

            imageRef . putFile (uri ) . addOnSuccessListener
            (
                taskSnapshot ->
                {
                    imageRef . getDownloadUrl ( ) . addOnSuccessListener
                    (
                        downloadUri ->
                        {
                            // هون انا بضيف رابط الصوره في هاي data ال HashMap عشان اضيفه مع بقية الداتا في الفايرستور
                            data . put ( "image Url" , downloadUri . toString ( ) ) ;

                            FirebaseFirestore . getInstance ( )
                            . collection ("/Mothers/"+ FirebaseAuth. getInstance ( ) . getCurrentUser ( ) . getEmail ( ) + "/Children's" )
                            . document (binding . NameEditText . getText ( ) . toString ( ) )
                            . set ( data , SetOptions . merge ( ) ) . addOnCompleteListener (task ->
                            {
                                if ( task . isComplete ( ) )
                                {
                                    // هون انا ببعت الصوره الي تم اختيارها من الاستديو لشاشة الاطفال
                                    intent . setData ( uri ) ;

                                    // هون انا ببعت مسج لشاشة الاطفال انه في صورة تم اختيارها من الاستديو
                                    intent . putExtra ("image form ?" ,"uri" ) ;

                                    // هون ببعت اسم الطفل لشاشة الاطفال
                                    intent . putExtra ("Name",binding . NameEditText . getText ( ) . toString ( ) ) ;

                                    setResult (RESULT_OK ,intent ) ;

                                    // هون بلغي الاشعار الي بحكي يرجى الانتظار
                                    progressDialog . dismiss ( ) ;

                                    finish ( ) ;
                                }
                            });
                        }
                    );
                }
            );
        }
        else
        {
            data . put ( "image Url" , "" ) ;

            FirebaseFirestore . getInstance ( )
            . collection ("/Mothers/"+ FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) + "/Children's" )
            . document (binding . NameEditText . getText ( ) . toString ( ) )
            . set ( data , SetOptions . merge ( ) ) . addOnCompleteListener (task ->
            {
                if ( task . isComplete ( ) )
                {
                    // هون انا ببعت مسج لشاشة الاطفال انه مافي صورة تم اختيارها من الاستديو
                    intent . putExtra ("image form ?" ,"Drawable" ) ;

                    // هون ببعت اسم الطفل لشاشة الاطفال
                    intent . putExtra ("Name",binding . NameEditText . getText ( ) . toString ( ) ) ;

                    setResult (RESULT_OK ,intent ) ;

                    // هون بلغي الاشعار الي بحكي يرجى الانتظار
                    progressDialog . dismiss ( ) ;

                    finish ( ) ;
                }
            });
        }
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





    //هاد الفنكشن مستدعيه في ال onCreate و وظيفته هي انه يجهز القائمه الي الدكتور بختار منها مكان العمل
    private void Adapter_Initialization ( )
    {
        // هون احنا بنحدد العناصر الي رح تنعرض النا لما نضغط على ال spinner
        ArrayAdapter < CharSequence > adapter = ArrayAdapter . createFromResource
                ( this , R . array . Gender , R . layout .layouts_spinner_drop_down_items_text_layout ) ;

        // فيي هاد السطر بعطيه شكل النص تبع القائمه الي رح تظهر النا لما نضغط على ال spinner
        adapter . setDropDownViewResource ( R . layout .layouts_spinner_drop_down_items_text_layout ) ;

        // في هاد السطر بنربط ال array الي فيها الداتا الي رح تظهر لما نضغط على ال spinner
        binding . GenderSp . setAdapter ( adapter ) ;

        // في هاد السطر بنعمل تهيئه لحدث اختيار عنصر من ال spinner
        binding . GenderSp . setOnItemSelectedListener ( this ) ;
    }

    @Override
    public void onItemSelected ( AdapterView < ? > parent , View view , int position , long id )
    {
        if ( position != 0 )
            gender = parent . getItemAtPosition (position ) . toString ( ) ;
    }

    @Override
    public void onNothingSelected ( AdapterView < ? > adapterView )
    {

    }
}