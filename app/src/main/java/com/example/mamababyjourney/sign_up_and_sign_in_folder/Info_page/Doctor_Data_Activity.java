package com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderInfoFolderDoctorDataActivityBinding;
import com.example.mamababyjourney.doctor_section.Doctor_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.SetOptions;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import android.widget.LinearLayout;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;
import android.view.View;
import android.os.Bundle;
import java.util.List;

@SuppressWarnings ( { "deprecation" , "ConstantConditions" , "SpellCheckingInspection" } )
@SuppressLint ( {"InflateParams" , "SetTextI18n" } )
public class Doctor_Data_Activity extends AppCompatActivity
{

    ActivitySignUpAndSignInFolderInfoFolderDoctorDataActivityBinding binding;

    public static int id = 1 ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderInfoFolderDoctorDataActivityBinding . inflate ( getLayoutInflater ( ) ) ;

        setContentView ( binding . getRoot ( ) ) ;
    }

    public void Add_Workplace_BTN ( View view )
    {
        /*
            طيب مبدئيا هاد الفنشكن بشتغل نفس هدول الفنكشنين
            public void Go_to_Sing_In ( View view )
            public void Go_to_Sing_Up ( View view )

            والي موجودين في كود الجافا ل اول شاشة والي هي ال First_screen

            لكن في اختلاف صغير و كبير بنفس الوقت بينهم وبين طريقة شغلهم

            لو تروحي على كلاس الجافا الخاص بال First_screen

            وتروحي على الفنكشنين الي ذكرتهم بتلاقي نفس الشي الي انا عامله هون لكن لو تدققي

            بتلاقي اني مستعمل startActivity وفي هاد الفنكشن الي احنا فيه هسه مستعمل
             startActivityForResult

            ومعطيها مع ال intent الرقم 1 و بعبر عن قيمه لمتغير اسمه requestCode

            طيب تعالي هلا اشرح الك بالتفصيل شو الفرق بين startActivityForResult و بين startActivity

            نبلش بالسهل منهم و الي ما بده شرح كثير ال startActivity ببساطه بتنقلنا للشاشه
            الي حددناها في ال intent والي بتاخده startActivity

            اما ال  startActivityForResult هي بتشغل الشاشة الي حددناها في ال intent +
            انها بتستنى الشاشه الي اشتغلت والي هي شاشة البيانات في حالتنا هون ترجع قيمه للشاشه الحالية
            والي هي في حالتنا هون شاشة عرض البيانات

            طيب هسه بتحكي بعقلك فهمت الفرق بينهم شو بالنسبه لللرقم 1

            انا بقلك شو بالنسبة للرقم 1 و لشو وبدي اوصلك الفكره عن طريق مثال

            هسه مش في الدوائر الحكوميه او المعاملات في الجامعه بكون الها ارقام عشان يقدرو يرجعو الها بس يحتاجوها

            هون نفس القصه الرقم 1 بوديه للشاشه الي رح ينقلنا ال intent الها

            ليه بنوديه الها لانه ي منار مو قلنا ال startActivityForResult بتشغل الشاشه الي حددناها في ال intent و بتستنى منها ترجع نتيجه او قيمه

            هاي القيمه هي مثل المعامله الي حكيت عنها في المثال لازم يكون في الها رقم عشان لما بدنا نستخرج البيانات من النتيجه الي رجعت النا بدنا نكون عارفين شو رقم النتيجه الي بدنا ناخد منها البيانات
        */
        Intent intent = new Intent (this ,Workplace_Data_Activity . class ) ;

        /*
            هون باستعمال المتغير intent استدعينا فننكشن ال putExtra و الي وظيفته باختصار هي انه
            ينقل الداتا من شاشه لشاشه اخرى و في حالتنا هون احنا بنبعث داتا للشاشة تعبئة بيانات مكان العمل

            وباخد متغيرين الاول الي هو ال name و الثاني الي هو ال value

            وال name بمثل اشي اسمه ال key او المفتاح وهاد بنستعمله في الشاشة الي بدنا نبعث الها الداتا و الي

            هي الخارطه عشان نحصل ال value الي انبعثت عن طريق هاد الفنكشن

            هاد السطر ببعت داتا مع ال intent لشاشة تعبئة بيانات مكان العمل عشان لما ادحلها من خلال هاي المسج او الداتا اعرف انه دخلتها عشان اضيف داتا و انفذ الاكواد الي بدي تتنفذ لما ادخل الشاشه عشان اضيف داتا
         */
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
            Intent intent = new Intent (this , Doctor_Activity. class );
            startActivity ( intent ) ;
        }
    }




    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {

        super . onActivityResult (requestCode ,resultCode ,data ) ;

        if ( resultCode == RESULT_OK )
        {
            switch ( requestCode )
            {
                // هاد ال case بتنفذ لما نضيف مكان عمل جديد يعني ال requestCode الي بروح لشاشة تعبئة بيانات مكان العمل و برجع لهاي الشاشه هو 1
                case 1 :
                {

                    /*
                        هسه الي بصير هون داخل الاف هو انه بجهز الي لوحة عرض بيانات مكان العمل عشان اعرض فيها البيانات الي اختارها الدكتور

                        هسه كل مكان عمل بنضاف بنعمل اله لوحة عرض خاصه فيه بنعرض فيها البيانات تبعته وهاي ال list بخزن فيها هدول اللوحات
                    */
                    List < View > views = new ArrayList < > ( ) ;

                    // هسه ي منار انا لما بضيف لوحة العرض بدي زر الحفظ و اضافة يظهرو تحت اللوحه مش قبلها لهيك عرفت هدول المتغيريين الي تحت عشان اخليهم اخزن فيه زر الحفظ و زر اضافة مكان العمل
                    AppCompatButton save_Doctor_Data_BTN = findViewById (R . id . Save_Doctor_Data_BTN ) ;
                    AppCompatButton add_Workplace_BTN    = findViewById (R . id . Add_Workplace_BTN    ) ;

                    // هسه هاد المتغير انا مخزن فيه ال LinearLayout الي بدي اضيف فيها اللوحه
                    LinearLayout parlLayout = findViewById (R . id . par ) ;

                    /*
                        هسه اللوحه انا ي منار عامل لوحه وحده و هي عباره عن شاشة عاديه لكنها مش مربوطه بشي و كل ما

                        بدي اضيف لوحه بجيب النسخه الي انا عاملها و بعمل منها نسخه جديده وهاد السطر الي بعمل هاد الشي
                    */
                    View childLayout = getLayoutInflater ( ) . inflate (R . layout .layouts_sign_up_and_sign_in_folder_info_folder_workplace_preview_panel_layout ,null ) ;

                    /*
                        هسه قبل ما اضيف اللوحه عشان اخلي زر الحفظ و زر اضافة مكان العمل تحت اللوحه لازم احذفهم من الشاشه عشان اضيفهم

                        بعد ما اضيف اللوحه الي عملتها بحيث يكونو تحتها والي بعمله هدول السطرين الي تحت انهم بحذفو هدول الازرار
                    */
                    parlLayout . removeView (add_Workplace_BTN    ) ;
                    parlLayout . removeView (save_Doctor_Data_BTN ) ;

                    // هون بستدعي الفنكشن الي بعبي الي بيانات مكان العمل في اللوحه
                    WorkPlace_Preview_panel_Initialization ( childLayout , data ) ;

                    // هون بعطي اللوحه tag يعتبر مثل ال id عشان اقدر اوصل الها لما احتاجها
                    childLayout . setTag ( "WorkPlace Number " + id ) ;

                    // هون بضيف اللوحه الي عملتها على ال list الي اسمها views
                    views . add ( childLayout ) ;

                    /*
                         هون داخل الاف بقله اذا كان عندي بس لوحه وحده ضيفها مباشره على الشاشه

                         اما اذا كان في اكثر من لوحه وحده ادخل جوا ال else
                    */
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

                    // هون بعد ما ضفنا اللوحه بنرجع بنضيف الازرار الي مسحناها قبل ما نضيف اللوحه بحيث انهم يكونو تحت اللوحه
                    parlLayout . addView (add_Workplace_BTN    ) ;
                    parlLayout . addView (save_Doctor_Data_BTN ) ;

                    // و اخر شي بصير هاد السطر الي بغير ال text الي في زر اضافة مكان العمل من "اضافة مكان العمل" ل "اضافة مكان عمل اخر" عشان افهم الدكتور انه لسه عنده امكانيه انه يضيف مكان عمل ثاني غير المكان الي اضافه اول مره
                    binding . AddWorkplaceBTN . setText ( "اضافة مكان عمل اخر" ) ;

                    Firebase_Functions_Class . Add_workPlace_Data ( ) ;

                    break ;
                }

                // هاد بتنفذ لما نعدل على بيانات مكان عمل ضفناه يعني ال requestCode الي بروح لشاشة تعبئة بيانات مكان العمل و برجع لهاي الشاشه هو 2
                case 2 :
                {
                    int id = data . getExtras ( ) . getInt ( "Id" ) ;

                    WorkPlace_Data workPlace_Data_Object = WorkPlace_Data . workPlace_Data_Object ;

                    String workPlace_Type = workPlace_Data_Object . workPlace_Type ;

                    TextView textView ;

                    View childLayout = binding . par . findViewWithTag ("WorkPlace Number " +  id  ) ;

                    textView = childLayout . findViewWithTag ("Name Text View" ) ;
                    textView . setText ( "اسم " + workPlace_Type + " : " + workPlace_Data_Object . workPlace_Name ) ;

                    textView = childLayout . findViewWithTag ("Address Text View" ) ;
                    textView . setText ( "عنوان " + workPlace_Type + " : " + workPlace_Data_Object . workPlace_Address ) ;

                    textView = childLayout . findViewWithTag ("Phone Number Text View" ) ;
                    textView . setText
                    (
                        workPlace_Type . equals ( "العيادة" ) ?

                        "رقم هاتف العيادة" + " : " +  workPlace_Data_Object . phoneNumber
                        :
                        "رقم الهاتف" + " : " + workPlace_Data_Object . phoneNumber
                    ) ;

                    textView = childLayout . findViewWithTag ("WorkPlace Location coordinates Text View" ) ;
                    textView . setText ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + workPlace_Data_Object . latitude + " , " + workPlace_Data_Object . longitude ) ;

                    textView = childLayout . findViewWithTag ("Days And Working Hours Text View" ) ;
                    textView . setText ( data . getExtras ( ) . getString ( "days And Working Hours Text" ) ) ;

                    AppCompatButton button = childLayout . findViewWithTag ("Edit Workplace Data BTN " + id ) ;
                    button . setText ( "تعديل بيانات " + workPlace_Type ) ;

                    Firebase_Functions_Class . Update_workPlace_Data (id ) ;

                    break ;
                }
            }
        }
    }

    private void WorkPlace_Preview_panel_Initialization (View childLayout ,Intent data )
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
        textView . setText ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + workPlace_Data_Object . latitude + " , " + workPlace_Data_Object . longitude ) ;


        textView = childLayout . findViewWithTag ("Days And Working Hours Text View" ) ;
        textView . setText ( data . getExtras ( ) . getString ( "days And Working Hours Text" ) ) ;

        AppCompatButton button = childLayout . findViewWithTag ("Edit Workplace Data BTN" ) ;
        button . setTag ( "Edit Workplace Data BTN " + id ) ;

        button . setText ( "تعديل بيانات " + workPlace_Type ) ;

        button . setOnClickListener ( V ->
        {

            int id = Integer . parseInt
            ( button . getTag ( ) . toString ( ) . substring ( button . getTag ( ) . toString ( ) . length ( ) - 1 ) ) ;

            Intent intent = new Intent (this , Workplace_Data_Activity . class ) ;
            intent . putExtra ("Action" ,"Edit" ) ;
            intent . putExtra ("Id"     ,id     ) ;

            startActivityForResult ( intent ,2 ) ;
        });
    }
}

@SuppressWarnings ( { "ConstantConditions" } )
class Firebase_Functions_Class
{
    public static void Add_workPlace_Data ( )
    {

        // Add workPlace id to doctor data
        HashMap < String , Object > data = new HashMap < > ( ) ;
        data . put ( "workPlace ID" , Doctor_Data_Activity . id ) ;

        FirebaseFirestore . getInstance ( )
        .collection ("Doctors" )
        .document   (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) )
        .set ( data , SetOptions . merge ( ) ) . addOnCompleteListener ( task ->
        {
            // Add workPlace data to doctor data
            if ( task . isComplete ( ) )
                Add_WorkPlace_Data_To_Doctor_Data ( ) ;
        });
    }

    private static void Add_WorkPlace_Data_To_Doctor_Data ( )
    {
        WorkPlace_Data workPlace_Data_Object = WorkPlace_Data . workPlace_Data_Object ;

        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        HashMap < String , Object > workPlace_Data = new HashMap < > ( ) ;

        workPlace_Data . put ( "days_And_Working_Hours_Objects_List" ,   workPlace_Data_Object . days_And_Working_Hours_Objects_List ) ;
        workPlace_Data . put ( "workPlace_Type_Index_In_Spinner"     ,   workPlace_Data_Object . workPlace_Type_Index_In_Spinner     ) ;

        workPlace_Data . put ( "longitude" , workPlace_Data_Object . longitude  ) ;
        workPlace_Data . put ( "latitude"  , workPlace_Data_Object . latitude   ) ;

        workPlace_Data . put ( "workPlace_Address" ,  workPlace_Data_Object . workPlace_Address ) ;
        workPlace_Data . put ( "workPlace_Name"    ,  workPlace_Data_Object . workPlace_Name    ) ;
        workPlace_Data . put ( "workPlace_Type"    ,  workPlace_Data_Object . workPlace_Type    ) ;
        workPlace_Data . put ( "phoneNumber"       ,  workPlace_Data_Object . phoneNumber       ) ;

        FirebaseFirestore . getInstance ( )
        .collection ("Doctors/" + doctor_Document_Id + "/workplaces" )
        .document   ("workPlace Number " + Doctor_Data_Activity . id )
        .set ( workPlace_Data ) . addOnCompleteListener (task ->
        {
            if ( task . isComplete ( ) )
                Update_Id ( ) ;
        });
    }

    public static void Update_workPlace_Data ( int id )
    {
        WorkPlace_Data workPlace_Data_Object = WorkPlace_Data . workPlace_Data_Object ;

        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        HashMap < String , Object > workPlace_Data = new HashMap < > ( ) ;
        workPlace_Data . put ( "days_And_Working_Hours_Objects_List" ,   workPlace_Data_Object . days_And_Working_Hours_Objects_List ) ;
        workPlace_Data . put ( "workPlace_Type_Index_In_Spinner"     ,   workPlace_Data_Object . workPlace_Type_Index_In_Spinner     ) ;

        workPlace_Data . put ( "longitude" , workPlace_Data_Object . longitude  ) ;
        workPlace_Data . put ( "latitude"  , workPlace_Data_Object . latitude   ) ;

        workPlace_Data . put ( "workPlace_Address" ,  workPlace_Data_Object . workPlace_Address ) ;
        workPlace_Data . put ( "workPlace_Name"    ,  workPlace_Data_Object . workPlace_Name    ) ;
        workPlace_Data . put ( "workPlace_Type"    ,  workPlace_Data_Object . workPlace_Type    ) ;
        workPlace_Data . put ( "phoneNumber"       ,  workPlace_Data_Object . phoneNumber       ) ;

        FirebaseFirestore . getInstance ( )
        .collection ("Doctors/" + doctor_Document_Id + "/workplaces" )
        .document   ("workPlace Number " + id ) . update ( workPlace_Data ) ;
    }

    public static void Update_Id ( )
    {
        String doctor_Document_Id = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        HashMap < String , Object > id = new HashMap < > ( ) ;
        id . put ( "workPlace ID" , ++ Doctor_Data_Activity . id  ) ;

        FirebaseFirestore . getInstance ( ) . collection ("Doctors" ) . document (doctor_Document_Id ) . update (id ) ;
    }
}