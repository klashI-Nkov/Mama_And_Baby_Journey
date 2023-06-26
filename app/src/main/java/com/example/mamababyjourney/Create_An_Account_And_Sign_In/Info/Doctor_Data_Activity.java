package com.example.mamababyjourney.Create_An_Account_And_Sign_In.Info;

import com.example.mamababyjourney.databinding.ActivityDoctorDataBinding;
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

    ActivityDoctorDataBinding binding;

    /*
        هاد المتغير انا مستعمله عشان اعطي id للوحات اماكن العمل الي بتنضاف عشان

        بس اعدل في بيانات مكان عمل معين اعرف اي لوحه الي لازم اعدل فيها و كمان

        بنفس الوقت مستعمله عشان اعطي بيانات مكان العمل الي بتنضاف في الفاير بيس id

        لحتى اقدر اوصل الها لما بدي اجيبها عشان اعدل عليها

        و هاد المتغير داينمك من حيث الزياده يعني كل ما اضيف مكان عمل جديد رح يزيد

        و قيمته مش مخزنه في التطبيق هي مخزنه في الفاير بيس يعني كل ما اضيف مكان

        عمل جديد رح تزيد قيمته في الفاير بيس بعدها تزيد قيمته هون في التطبيق
    */
    public static int id = 1 ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityDoctorDataBinding . inflate ( getLayoutInflater ( ) ) ;

        setContentView ( binding . getRoot ( ) ) ;

        Firebase_Functions_Class . Clear ( ) ;
    }


    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو الازرار في هاي الشاشه ---------------

    // هاد الفنشكن بتنفذ لما نظغط على زر اضافة مكان العمل
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

    // --------------- نهاية الجزء الي فيه الفنكشن الي بخصو الازرار في هاي الشاشه ---------------



    /* */



    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو هاد الكلاس ---------------


    /*
      قبل ما تخشي قراية الكومنتات روحي شوفي شو مكتوب داخل جملة الاف في هاد Save_WorkPlace_Data_BTN

      الفنكشن في هاد Workplace_Data_Activity الكلاس و شوفي شو مكتوب كومنتات عن ال 3 لسطر هدول
      Intent intent = getIntent ( ) ;
      setResult ( RESULT_OK , intent ) ;
      finish ( ) ;

      وبنصحك قبل ما تخشي تفهمي شو المكتوب جوا هاد الفنكشن تروحي على الفنشكن الي ذكرته فوق تفهميه بعدها تجي هون تفهمي شو الي مكتوب جواته



      هاد الفنشكن بستدعى اول ما نرجع لهاي الشاشه بعد ما نكبس على زر الحفظ في كلاس ال Workplace_Data_Activity
    */
    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {

        /*
          هسه مثل ما انتي شايفه طبعا الفنكشن بستقبل 3 متغيرات الاول requestCode ومعناه كود الطلب الثاني هو resultCode معناه كود النتيجه

          وال resultCode وال requestCode بجو النا داخل ال intent الي بنستعمله لحتى نرجع من شاشة تعبئة بيانات مكان العمل لهاي الشاشة

          و بكون داخله الداتا الي جايه من شاشة تعبئة بيانات مكان العمل وهاد ال intent هو نفسه المتغير الثالث الي بستقبله هاد الفنكشن و الي اسمه data

           و ملاحظه صغير بس

           هسه ال request code ما نحدد قيمته لما نرجع من شاشة تعبئة بيانات مكان العمل

           احنا بنحدده هون في الشاشه الي بدنا ننتقل منها للشاشه الي رح ترجع النا النتيجه

           وفي حالتنا هون احنا حددنا قيمة ال request code لشاشة تعبئة البيانات
           بالرقم 1 وهاد الشي انعمل في فنكشن ال Add_Workplace_BTN

           وتحديدا في هاد السطر  startActivityForResult ( intent , 1 )
        */
        super . onActivityResult (requestCode ,resultCode ,data ) ;

        /*
            هون بنحكي اله اذا  كان ال resultCode الي جاي مع ال intent قيمته تساوي RESULT_OK ادخل و نفذ الي جوا جملة الاف
        */
        if ( resultCode == RESULT_OK )
        {
            /*
                هون كونه بتعامل مع حالتين للرجوع لهاي الشاشه هم حالة تعديل بيانات مكان عمل موجود و حالة اضافة
                بيانات مكان عمل جديد فرح يكون عندي 2 request code لهيك لازم استحدم switch

                عشان احط الكود الي بخص كل حالة في case مختلف وحسب ال request code الي جاي لهاد الفنكشن بنفذ ال case المطلوبه
            */
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
                    View childLayout = getLayoutInflater ( ) . inflate (R . layout . work_place_layout ,null ) ;

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

                    Firebase_Functions_Class . Add_workPlace_Data_Object ( WorkPlace_Data . workPlace_Data_Object ,id ) ;

                    break ;
                }

                // هاد بتنفذ لما نعدل على بيانات مكان عمل ضفناه يعني ال requestCode الي بروح لشاشة تعبئة بيانات مكان العمل و برجع لهاي الشاشه هو 2
                case 2 :
                {
                    int id = data . getExtras ( ) . getInt ( "Id" ) ;

                    /*
                        هون بعرف اوبجكت من هاي WorkPlace_Data الكلاس و بخليه يساوي قيمة الاوبجكت الي في هاي WorkPlace_Data الكلاس و الي اسمه

                        ؛workPlace_data_Object عشان لما بدي اوصل لواحد من متغيرات الاوبجكت اضل ال قله WorkPlace_Data . workPlace_Data_Object
                    */
                    WorkPlace_Data workPlace_data_Object = WorkPlace_Data . workPlace_Data_Object ;

                    // هون بعرف هاد workPlace_Type المتغير و بخيله يساوي هاد workPlace_Type المتغير الخاص بهاد workPlace_data_Object الاوبجكت الي في هاي الكلاس
                    String workPlace_Type = workPlace_data_Object . workPlace_Type ;

                    TextView textView ;

                    View childLayout = binding . par . findViewWithTag ("WorkPlace Number " +  id  ) ;

                    // هون بحط اسم العياده او المشفى الخ...
                    textView = childLayout . findViewWithTag ("Name Text View" ) ;
                    textView . setText ( "اسم " + workPlace_Type + " : " + workPlace_data_Object . workPlace_Name ) ;

                    // هون بحط عنوان العياده او المشفى الخ...
                    textView = childLayout . findViewWithTag ("Address Text View" ) ;
                    textView . setText ( "عنوان " + workPlace_Type + " : " + workPlace_data_Object . workPlace_Address ) ;

                    // هون بحط رقم الهاتف
                    textView = childLayout . findViewWithTag ("Phone Number Text View" ) ;
                    textView . setText
                    (
                        /*
                            هون ? ( "العيادة" ) workPlace_Type . equals بشتغل نفس شغل الاف لكن بشكل

                            مختصر وبقله هون اذا كان مكان العمل الي اختاره عيادة اكتب في ال textView "رقم هاتف العيادة" بعدها رقم الهاتف غير هيك اكتب "رفم الهاتف" بعدها رقم الهاتف


                            يعني هاي ? ( "العيادة" ) workPlace_Type . equals عباره عن جملة اف

                            وهاي : الي بين هاي "رقم هاتف العيادة" وبين هاي "رقم الهاتف" عباره عن كلمة else
                        */
                        workPlace_Type . equals ( "العيادة" ) ?

                        "رقم هاتف العيادة" + " : " +  workPlace_data_Object . phoneNumber
                        :
                        "رقم الهاتف" + " : " + workPlace_data_Object . phoneNumber
                    ) ;


                    // هون بحط احداثيات مكان العمل
                    textView = childLayout . findViewWithTag ("WorkPlace Location coordinates Text View" ) ;
                    textView . setText ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + workPlace_data_Object . latitude + " , " + workPlace_data_Object . longitude ) ;

                    // هون بحط ايام و ساعات العمل
                    textView = childLayout . findViewWithTag ("Days And Working Hours Text View" ) ;
                    textView . setText ( data . getExtras ( ) . getString ( "days And Working Hours Text" ) ) ;

                    AppCompatButton button = childLayout . findViewWithTag ("Edit Workplace Data BTN " + id ) ;
                    button . setText ( "تعديل بيانات " + workPlace_Type ) ;

                    Firebase_Functions_Class . Update_workPlace_Data_Objects_List (workPlace_data_Object ,id ) ;

                    break ;
                }
            }
        }
    }

    // هاد الفنكشن الي بعبي بيانات مكان العمل في لوحة العرض الي بنعرض فيها بيانات مكان العمل في هاي الشاشه
    private void WorkPlace_Preview_panel_Initialization (View childLayout ,Intent data )
    {

        WorkPlace_Data workPlace_Data_Object = WorkPlace_Data . workPlace_Data_Object ;

        String workPlace_Type = workPlace_Data_Object . workPlace_Type ;


        /*
             هاد الفنكشن بستقبل نسخة من لوحة عرض بيانات مكان العمل عشان اعدل على المكونات الي فيها

            هسه اللوحه كل الي جواتها textView و فيها بس button واحد لهيك معرف textView واحد و مستعمله اكثر من مره عشان اعدل ال text لكل ال textView الي موجودين بهاي الشاشه
        */

        TextView textView ;

        // هون انا بحط رقم مكان العمل
        textView = childLayout . findViewWithTag ("Workplace Num" ) ;
        textView . setText ( "مكان العمل " + id ) ;

        // هون بحط اسم العياده او المشفى الخ...
        textView = childLayout . findViewWithTag ("Name Text View" ) ;
        textView . setText ( "اسم " + workPlace_Type + " : " + workPlace_Data_Object . workPlace_Name ) ;

        // هون بحط عنوان العياده او المشفى الخ...
        textView = childLayout . findViewWithTag ("Address Text View" ) ;
        textView . setText ( "عنوان " + workPlace_Type + " : " + workPlace_Data_Object . workPlace_Address ) ;

        // هون بحط رقم الهاتف
        textView = childLayout . findViewWithTag ("Phone Number Text View" ) ;
        textView . setText
        (
            // هون بصير نفس الي بصير في ال Case رقم ال 2 في فنكشن ال onActivityResult
            workPlace_Type . equals ( "العيادة" ) ?

            "رقم هاتف العيادة" + " : " + workPlace_Data_Object . phoneNumber
            :
            "رقم الهاتف"       + " : " + workPlace_Data_Object . phoneNumber
        );


        // هون بحط احداثيات مكان العمل
        textView = childLayout . findViewWithTag ("WorkPlace Location coordinates Text View" ) ;
        textView . setText ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + workPlace_Data_Object . latitude + " , " + workPlace_Data_Object . longitude ) ;


        // هون بحط ايام و ساعات العمل
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


    // --------------- نهاية الجزء الي فيه الفنكشن الي بخصو هاد الكلاس ---------------

}

@SuppressWarnings ( "SpellCheckingInspection" )
class Firebase_Functions_Class
{

    // هاد الفنكشن بتسدعى لما بدي اضيف مكان عمل جديد عشان يضيف بياناته في الفاير بيس
    public static void Add_workPlace_Data_Object ( WorkPlace_Data workPlace_Data_Object , int id )
    {
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        HashMap < String , Object > data = new HashMap < > ( ) ;
        data . put ( "workPlace Data Object Number " + id , workPlace_Data_Object ) ;

        db . collection ( "A" ) . document ( "C" ) . set ( data , SetOptions . merge ( ) )
        . addOnCompleteListener ( task ->
        {
            if ( task . isComplete ( ) )
            {
                Update_Id ( ) ;
            }
        }) ;
    }

    // هاد بستدعى لما اعدل على بيانات مكان العمل عشان يحدثها في الفاير بيس
    public static void Update_workPlace_Data_Objects_List ( WorkPlace_Data workPlace_Data_Object , int id )
    {
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        HashMap < String , Object > data = new HashMap < > ( ) ;
        data . put ( "workPlace Data Object Number " + id , workPlace_Data_Object ) ;
        db . collection ("A" ) . document ("C" ) . update ( data ) ;
    }

    // هاد انا مستدعيه في فنكشن ال onCreate وكل الي بعمله هو انه اول ما تفتح الشاشه الي احنا فيها يفضي الفاير بيس من اي بيانات لاي مكان عمل تمت اضافته
    public static void Clear ( )
    {
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        HashMap < String , Object > id = new HashMap < > ( ) ;

        id . put ( "id" , 1 ) ;

        db . collection ( "A" ) . document ( "C" ) . set ( id ) ;
    }

    // وهاد الفنكشن وظيفته انه يزود الي ال id في كل مره بضيف فيها مكان عمل جديد
    public static void Update_Id ( )
    {
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        HashMap < String , Object > id = new HashMap < > ( ) ;
        id . put ( "id" , ++ Doctor_Data_Activity . id  ) ;
        db . collection ("A" ) . document ("C" ) . update (id ) ;
    }
}