package com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderInfoFolderDoctorDataActivityBinding;
import com.example.mamababyjourney.doctor_section.Doctor_Activity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.CompletableFuture;
import com.google.firebase.auth.FirebaseAuth;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;
import android.view.View;
import android.os.Bundle;
import java.util.List;
import java.util.Map;

@SuppressWarnings ( { "deprecation" , "ConstantConditions" , "SameParameterValue" , "SpellCheckingInspection" , "RedundantSuppression" } )
@SuppressLint ( {"InflateParams" , "SetTextI18n" } )
public class Doctor_Data_Activity extends AppCompatActivity
{
    private ActivitySignUpAndSignInFolderInfoFolderDoctorDataActivityBinding binding;

    public static int id = 1 ;

    private List < View > views ;

    public static ProgressDialog progressDialog ;

    public static CompletableFuture < Void > get_workPlace_Data_Object_Task = new CompletableFuture < > ( ) ;


    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderInfoFolderDoctorDataActivityBinding . inflate ( getLayoutInflater ( ) ) ;

        setContentView ( binding . getRoot ( ) ) ;

        progressDialog = new ProgressDialog ( this );
        progressDialog . setMessage ( "يرجى الانتظار" ) ;

        views = new ArrayList < > ( ) ;
    }

    public void Add_Workplace_BTN ( View view )
    {
        Intent intent = new Intent (this ,Workplace_Data_Activity . class ) ;
        intent . putExtra ("Action" ,"Add" ) ;
        startActivityForResult ( intent ,1 ) ;
    }

    @Override
    public void onBackPressed ( )
    {

    }

    public void Save_Doctor_Data_BTN ( View view )
    {
        if ( binding . AddWorkplaceBTN . getText ( ) . toString ( ) . contains ( "اخر" ) )
        {
            Intent intent = new Intent (this ,Doctor_Activity. class );
            startActivity (intent ) ;
        }
        else
            Snack_Bar ("يجب اضافة مكان عمل واحد على الاقل قبل المتابعة" );
    }




    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super . onActivityResult (requestCode ,resultCode ,data ) ;

        if ( resultCode == RESULT_OK )
        {
            switch ( requestCode )
            {
                // هاد ال case بتنفذ لما نضيف مكان عمل جديد
                case 1 :
                {
                    AppCompatButton save_Doctor_Data_BTN = findViewById (R . id . Save_Doctor_Data_BTN ) ;
                    AppCompatButton add_Workplace_BTN    = findViewById (R . id . Add_Workplace_BTN    ) ;

                    LinearLayout parlLayout = findViewById (R . id . par ) ;

                    View childLayout = getLayoutInflater ( )
                    .inflate (R . layout .layouts_sign_up_and_sign_in_folder_info_folder_workplace_preview_panel_layout ,null ) ;

                    parlLayout . removeView (save_Doctor_Data_BTN ) ;
                    parlLayout . removeView (add_Workplace_BTN    ) ;

                    WorkPlace_Preview_panel_Initialization (childLayout ,data ,id ,"Add" ) ;

                    childLayout . setTag ("WorkPlace Number " + id ) ;

                    views . add ( childLayout ) ;

                    if ( views . size ( ) == 1 )
                        parlLayout . addView ( childLayout ) ;
                    else
                    {
                        /*
                            هسه الي بصير هون داخل ال else هو انه عنا for each بتلف على ال list الي فيها كل اللوحات والي بصر داخل

                            هاي ال for each هو انه بتحذف اللوحه الي بتكون ال for each ماشره عليها بعدها بترجع تضيفها وهاد ليه بصير

                            بصير عشان ي منار يجو مرتبين يعني اول شي بجي عندك لوحة مكان العمل 1 بعدها لوحة مكان العمل 2 و هيك للاخر
                         */
                        for ( View element : views )
                        {
                            parlLayout . removeView (element ) ;
                            parlLayout . addView    (element ) ;
                        }
                    }

                    parlLayout . addView (add_Workplace_BTN    ) ;
                    parlLayout . addView (save_Doctor_Data_BTN ) ;

                    binding . AddWorkplaceBTN . setText ( "اضافة مكان عمل اخر" ) ;

                    Firebase_Functions_Class . Add_workPlace_Data ( ) ;

                    break ;
                }

                // هاد بتنفذ لما نعدل على بيانات مكان عمل ضفناه
                case 2 :
                {
                    int id = data . getExtras ( ) . getInt ( "Id" ) ;

                    View childLayout = binding . par . findViewWithTag ("WorkPlace Number " +  id  ) ;

                    WorkPlace_Preview_panel_Initialization (childLayout ,data ,id ,"Edit" ) ;

                    Firebase_Functions_Class . Update_workPlace_Data (id ) ;

                    break ;
                }
            }
        }
    }

    private void WorkPlace_Preview_panel_Initialization (View childLayout ,Intent data , int id ,String action )
    {
        WorkPlace_Data workPlace_Data_Object = WorkPlace_Data . workPlace_Data_Object ;

        String workPlace_Type = workPlace_Data_Object . workPlace_Type ;

        TextView textView ;

        textView = childLayout . findViewWithTag ("Workplace Num" ) ;
        textView . setText ( "مكان العمل " + id ) ;

        textView = childLayout . findViewWithTag ("Name Text View" ) ;
        textView . setText ( "اسم " + workPlace_Type + " : " + workPlace_Data_Object . workPlace_Name ) ;

        textView = childLayout . findViewWithTag ("Address Text View" ) ;
        textView . setText ( "عنوان " + workPlace_Type + " : " + workPlace_Data_Object . workPlace_Address ) ;

        textView = childLayout . findViewWithTag ("Phone Number Text View" ) ;
        textView . setText
        (
            workPlace_Type . equals ( "العيادة" ) ?

            "رقم هاتف العيادة" + " : " + workPlace_Data_Object . phoneNumber
            :
            "رقم الهاتف"       + " : " + workPlace_Data_Object . phoneNumber
        );


        textView = childLayout . findViewWithTag ("WorkPlace Location coordinates Text View" ) ;
        textView . setText
        ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + workPlace_Data_Object . latitude + " , " + workPlace_Data_Object . longitude ) ;


        textView = childLayout . findViewWithTag ("Days And Working Hours Text View" ) ;
        textView . setText ( data . getExtras ( ) . getString ("days And Working Hours Text" ) ) ;


        if ( action . equals ( "Add" ) )
        {
            AppCompatButton Edit_Workplace_Data_BTN = childLayout . findViewWithTag ("Edit Workplace Data BTN" ) ;
            Edit_Workplace_Data_BTN . setTag ( "Edit Workplace Data BTN " + id ) ;
            Edit_Workplace_Data_BTN . setText ( "تعديل بيانات " + workPlace_Type ) ;


            AppCompatButton delete_Workplace_Data_BTN = childLayout . findViewWithTag ("Delete Workplace BTN" ) ;
            delete_Workplace_Data_BTN . setTag ( "Delete Workplace BTN " + id ) ;
            delete_Workplace_Data_BTN . setText ( "حذف مكان العمل" ) ;


            Edit_Workplace_Data_BTN . setOnClickListener ( V ->
            {
                progressDialog . show ( ) ;

                int Id = Integer . parseInt (Edit_Workplace_Data_BTN . getTag ( ) . toString ( )
                .substring (Edit_Workplace_Data_BTN . getTag ( ) . toString ( ) . length ( ) - 1 ) ) ;

                Firebase_Functions_Class . Get_workPlace_Data (Id ) ;


                get_workPlace_Data_Object_Task . thenAccept (result ->
                {
                    if ( get_workPlace_Data_Object_Task . isDone ( ) )
                    {
                        Intent intent = new Intent (this , Workplace_Data_Activity . class ) ;

                        intent . putExtra ("Action" ,"Edit" ) ;
                        intent . putExtra ("Id"     ,id     ) ;

                        progressDialog . dismiss ( ) ;

                        startActivityForResult ( intent ,2 ) ;
                    }
                });
            });

            delete_Workplace_Data_BTN . setOnClickListener ( v ->
            {
                progressDialog . show ( ) ;

                int Id = Integer . parseInt (delete_Workplace_Data_BTN . getTag ( ) . toString ( )
                .substring (delete_Workplace_Data_BTN . getTag ( ) . toString ( ) . length ( ) - 1 ) ) ;

                View Layout = binding . par . findViewWithTag ("WorkPlace Number " +  id  ) ;

                Delete_Workplace (Id ,Layout ) ;
            } );
        }

    }

    private void Delete_Workplace ( int id , View childLayout)
    {

        CompletableFuture < Void > delete_Workplace_Task = new CompletableFuture < > ( ) ;

        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        FirebaseFirestore . getInstance ( )
        .collection ("Doctors/" + doctor_Document_Id + "/workplaces" )
        .document   ("workPlace Number " + id )
        . delete ( ) . addOnCompleteListener (task ->
        {
            if ( task . isComplete ( ) )
                delete_Workplace_Task . complete (null ) ;
        });

        delete_Workplace_Task . thenAccept ( result ->
        {
           if ( delete_Workplace_Task . isDone ( ) )
           {
               LinearLayout parlLayout = findViewById (R . id . par ) ;

                parlLayout . removeView (childLayout ) ;

                views . remove (childLayout ) ;

                for ( int i = 0 ; i < views . size ( ) ; i++ )
                {
                    TextView text = views . get ( i ) . findViewWithTag ("Workplace Num" ) ;
                    text . setText ( "مكان العمل " + ( i + 1 ) ) ;
                }

               Firebase_Functions_Class . Update_Id ("Delete" ) ;

           }
        });
    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make ( binding . Constraint , Message , 7000 ) ;

        int color = Color.parseColor ( "#292929" ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList. valueOf (color ) ) ;

        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById ( com . google . android . material . R . id . snackbar_text ) ;

        textView . setSingleLine ( false ) ;

        textView . setTextColor ( ContextCompat. getColor ( this , R . color . white ) ) ;

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

}

@SuppressWarnings ( { "ConstantConditions" , "unchecked" } )
class Firebase_Functions_Class
{
    public static void Add_workPlace_Data ( )
    {
        Doctor_Data_Activity . progressDialog . show ( ) ;

        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        WorkPlace_Data workPlace_Data_Object = WorkPlace_Data . workPlace_Data_Object ;

        HashMap < String , Object > workPlace_Data = new HashMap < > ( ) ;

        workPlace_Data . put ( "days And Working Hours Objects List" , workPlace_Data_Object . days_And_Working_Hours_Objects_List ) ;
        workPlace_Data . put ( "workPlace Type Index In Spinner"     , workPlace_Data_Object . workPlace_Type_Index_In_Spinner     ) ;

        workPlace_Data . put ( "longitude" , workPlace_Data_Object . longitude  ) ;
        workPlace_Data . put ( "latitude"  , workPlace_Data_Object . latitude   ) ;

        workPlace_Data . put ( "workPlace Address" ,  workPlace_Data_Object . workPlace_Address ) ;
        workPlace_Data . put ( "workPlace Name"    ,  workPlace_Data_Object . workPlace_Name    ) ;
        workPlace_Data . put ( "workPlace Type"    ,  workPlace_Data_Object . workPlace_Type    ) ;
        workPlace_Data . put ( "phoneNumber"       ,  workPlace_Data_Object . phoneNumber       ) ;

        FirebaseFirestore . getInstance ( )
        .collection ("Doctors/" + doctor_Document_Id + "/workplaces" )
        .document   ("workPlace Number " + Doctor_Data_Activity . id )
        .set ( workPlace_Data ) . addOnCompleteListener (task ->
        {
            if ( task . isComplete ( ) )
                Update_Id ("Add" ) ;
        });
    }

    public static void Get_workPlace_Data ( int id )
    {

        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;


        FirebaseFirestore . getInstance ( )
        .collection ( "Doctors/" + doctor_Document_Id + "/workplaces" )
        .document ( "workPlace Number " + id )
        .get ( ) . addOnCompleteListener ( task ->
        {
            if ( task . isSuccessful ( ) )
                if ( task . getResult ( ) . exists ( ) )
                {

                    ArrayList < Map < String, Object > > days_And_Working_Hours_Objects_List_From_Firebase
                    = ( ArrayList < Map < String, Object > > ) task . getResult ( ) . get ( "days And Working Hours Objects List" ) ;

                    List < Days_And_Working_Hours > days_And_Working_Hours_List_Objects = new ArrayList <> ( ) ;

                    for ( int i = 0  ; i < days_And_Working_Hours_Objects_List_From_Firebase . size ( )  ; i++ )
                    {
                        Map < String, Object > days_And_Working_Hours_Object_From_Firebase = days_And_Working_Hours_Objects_List_From_Firebase . get ( i ) ;

                        Days_And_Working_Hours days_And_Working_Hours_Object = new Days_And_Working_Hours ( ) ;

                        days_And_Working_Hours_Object . from_AM_Or_PM = days_And_Working_Hours_Object_From_Firebase . get ( "from_AM_Or_PM" ) + "" ;
                        days_And_Working_Hours_Object . to_AM_Or_PM   = days_And_Working_Hours_Object_From_Firebase . get ( "to_AM_Or_PM"   ) + "" ;
                        days_And_Working_Hours_Object . from_Hour     = days_And_Working_Hours_Object_From_Firebase . get ( "from_Hour"     ) + "" ;
                        days_And_Working_Hours_Object . to_Hour       = days_And_Working_Hours_Object_From_Firebase . get ( "to_Hour"       ) + "" ;
                        days_And_Working_Hours_Object . day           = days_And_Working_Hours_Object_From_Firebase . get ( "day"           ) + "" ;

                        days_And_Working_Hours_List_Objects . add ( days_And_Working_Hours_Object ) ;
                    }


                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = days_And_Working_Hours_List_Objects ;

                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner = Integer . parseInt
                    (task . getResult ( ) . get ( "workPlace Type Index In Spinner" ) + "" ) ;

                    WorkPlace_Data . workPlace_Data_Object . longitude = Double . parseDouble (task . getResult ( ) . get ( "longitude" ) + "" ) ;
                    WorkPlace_Data . workPlace_Data_Object . latitude  = Double . parseDouble (task . getResult ( ) . get ( "latitude"  ) + "" ) ;

                    WorkPlace_Data . workPlace_Data_Object . workPlace_Address = task . getResult ( ) . get ( "workPlace Address" ) + "" ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Name    = task . getResult ( ) . get ( "workPlace Name"    ) + "" ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type    = task . getResult ( ) . get ( "workPlace Type"    ) + "" ;
                    WorkPlace_Data . workPlace_Data_Object . phoneNumber       = task . getResult ( ) . get ( "phoneNumber"       ) + "" ;

                    Doctor_Data_Activity . get_workPlace_Data_Object_Task . complete (null ) ;
                }
        } ) ;
    }

    public static void Update_workPlace_Data ( int id )
    {
        WorkPlace_Data workPlace_Data_Object = WorkPlace_Data . workPlace_Data_Object ;

        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        HashMap < String , Object > workPlace_Data = new HashMap < > ( ) ;
        workPlace_Data . put ( "days And Working Hours Objects List" , workPlace_Data_Object . days_And_Working_Hours_Objects_List ) ;
        workPlace_Data . put ( "workPlace Type Index In Spinner"     , workPlace_Data_Object . workPlace_Type_Index_In_Spinner     ) ;

        workPlace_Data . put ( "longitude" , workPlace_Data_Object . longitude  ) ;
        workPlace_Data . put ( "latitude"  , workPlace_Data_Object . latitude   ) ;

        workPlace_Data . put ( "workPlace Address" ,  workPlace_Data_Object . workPlace_Address ) ;
        workPlace_Data . put ( "workPlace Name"    ,  workPlace_Data_Object . workPlace_Name    ) ;
        workPlace_Data . put ( "workPlace Type"    ,  workPlace_Data_Object . workPlace_Type    ) ;
        workPlace_Data . put ( "phoneNumber"       ,  workPlace_Data_Object . phoneNumber       ) ;

        FirebaseFirestore . getInstance ( )
        .collection ("Doctors/" + doctor_Document_Id + "/workplaces" )
        .document   ("workPlace Number " + id ) . update ( workPlace_Data ) ;
    }

    public static void Update_Id (String action )
    {
        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        HashMap < String , Object > id = new HashMap < > ( ) ;

        if ( action . equals ( "Add" ) )
            id . put ( "workPlace ID" , ++ Doctor_Data_Activity . id  ) ;
        else
            id . put ( "workPlace ID" , -- Doctor_Data_Activity . id ) ;


        FirebaseFirestore . getInstance ( ) . collection ("Doctors" ) . document (doctor_Document_Id ) . update (id ) ;

        if ( action . equals ( "Delete" ) )
        {
            id . put ( "workPlace ID" , Doctor_Data_Activity . id + 1  ) ;
            FirebaseFirestore . getInstance ( ) . collection ("Doctors" ) . document (doctor_Document_Id ) . update (id ) ;
        }

        Doctor_Data_Activity . progressDialog . dismiss ( ) ;
    }
}