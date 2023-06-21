package com.example.mamababyjourney.Create_An_Account_And_Sign_In.Info;

import com.example.mamababyjourney.databinding.ActivityWorkplaceDataBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import java.util.Objects;
import java.util.List;
import java.util.Map;

@SuppressLint ( { "SetTextI18n" , "SuspiciousIndentation" } )
@SuppressWarnings ( { "deprecation" , "ConstantConditions" , "unchecked" , "SpellCheckingInspection" } )

public class Workplace_Data_Activity extends AppCompatActivity implements AdapterView . OnItemSelectedListener
{

    ActivityWorkplaceDataBinding binding ;

    // هذول بستقبلو قيمة احداثيات مكان العمل من الخارطه بس الدكتور يحدد مكان العمل على الخارطه
    private double longitude, latitude ;

    // هاد المتغير انا مستعمله عشان اخزن فيه نوع مكان العمل الي بختاره المستخدم ان كان مشفى او عياده او مركز صحي
    private String workPlace_Type ;

    // هاد بخزن فيه index العنصر العنصر الي بختاره من ال spinner في فنكشن ال WorkPlace_Page_Initialization بتفهمي ليه بعمل هيك وفي فنكشن ال onItemSelected بتفهمي شو بقصد بال index تبع العنصر الي بختاره من ال spinner
    private int workPlace_Index_In_Spinner ;

    // هاد مستعمله عشان اخزن فيه قيمة ال id الي بتجي من الشاشه الي قبل هاي
    private int id ;

    // هاد مستعمله لحتى اخزن فيه السبب الي انا عشانه داخل هون ان كان عشان اعدل او عشان اضيف وعامل هاد الشي لانه في جزء من الاكواد لازم تتنفذ لما بدي اضيف و الجزء الثاني بس بدي اعدل و من دونه رح يضرب خطا البرنامج لانه رح ينفذ اكواد مش لازم ينفذها
    private String action ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityWorkplaceDataBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;



        /*
            هسه ركزي منيح في الكلام الي رح احكيه هون لانه الي رح اشرح عنه رح تشوفيه في اماكن كثيره منها في كلاس ال Map و في كلاس ال Days_And_Working_Hours_Activity وكلاسات ثانيه كمان

             هسه الي بصير هون getIntent ( ) . getExtras ( ) . getString ( "Action" )

              هو انه ال intent الي جابنا لهاي الشاشه بكون في جواته داتا بعض الاحيان احنا بنحط هاي الداتا فيه

             عشان بنحتاجها لعمل شيء معين في الشاشه الي ال intent رح يودينا الها وعشان اجيب الداتا من هاد ال intent اول شي بحكي اله

             ؛getIntent يعني جيب الي ال intent الي جابني لهاي الشاشة بعدها بقول اله getExtras يعني جيب الداتا الي في هاد ال intent

             بعدها بقول اله get ونوع الداتا الي انا باعته مع ال intent يعني انا هون باعت داتا من نوع string فقلت اله getString وان كنت

             باعث داتا من نوع double بقله getDouble يعني انتي تعالي امسحي getString و اكتبي get لحالها رح تلاحظي انه بعطيكي اكثر من نوع للداتا

             وداخل القوسين بحط ال name الي انا حددته باستعمال فنشكن ال putExtra لما بعتت الداتا لهاد الكلاس
        */
        action = getIntent ( ) . getExtras ( ) . getString ("Action" ) ;


        // هسه هون بشيك ان كنت داخل عشان اضيف نفذ الي جوا الاف غير هيك بكون داخل عشان اعدل و نفذ الي جوا ال else
        if ( action . equals ( "Add" ) )
        {

            /*
                هسه في حالة كنت داخل عشان اضيف بينات رح اخلي ال id يساوي ال id الي في كلاس ال Doctor_Data_Activity والي هو ترتيب مكان العمل بين امكان العمل الي انضافت او رح تنضاف

                بعدها بستدعي الفنكشن الي بجهز الي الشاشه عشان اضيف بيانات مكان العمل الجديد
            */
            id =  Doctor_Data_Activity . id ;
            WorkPlace_Page_Initialization ( ) ;
        }
        else
        {
            /*
               في حالة كنت داخل عشان اعدل اول شي رح اخلي ال id يساوي id المكان الي بدي اعدل على بياناته والي هو جاي الي من كلاس Doctor_Data_Activity مع ال intent

                بعدها بستدعي الفنكشن الي بجيب الي بيانات مكان العمل الي بدي اعدل في بياناته من الفايربيس
            */
            id = getIntent ( ) . getExtras ( ) . getInt ( "Id" ) ;
            Get_workPlace_Data_Object (  id );
        }

        Adapter_Initialization ( ) ;

    }




    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو الازرار في هاي الشاشه ---------------

    // هاد الكود بتنفذ لما نضغط زر الذهاب الى الخارطة
    public void Go_To_Map_BTN ( View view )
    {

        /*
            الي بصير هون هو نفس الي بصير في فنكشن ال Add_Workplace_BTN في كلاس ال Doctor_Data_Activity

            لكن الفرق هون انه احنا بدنا من خلال هاد الكود ننتقل للخارطه و نبعث 4 متغيرات
        */
        Intent intent = new Intent (this , Map_Activity . class ) ;

        intent . putExtra ("workPlace_Type" ,workPlace_Type ) ;
        intent . putExtra ("longitude"      ,longitude      ) ;
        intent . putExtra ("latitude"       ,latitude       ) ;

        // هون قبل ما ابعث بشيك هل بدي اروح على الخارطه عشان اعدل مكان العمل على الخارطه ولا عشان اضيف مكان العمل على الخارطة
        if ( binding . GoToMapBTN . getText ( ) . toString ( ) . contains ( "تعديل" ) )
            intent . putExtra ("Action" , "Edit" ) ;
        else
            intent . putExtra ("Action" , "Add" ) ;

        startActivityForResult (intent , 1 ) ;
    }

    // هاد الفنكشن بتنفذ لما نضغط على زر اضافة ايام و ساعات العمل
    public void Add_Days_And_Working_Hours_BTN ( View view )
    {

        // هسه هون نفس الي بصير في فنكشن ال Go_To_Map_BTN لكن ببعث متغير واحد مش 4 وهاد الفنكشن بوجهنا لشاشة اضافة ايام و ساعات العمل
        Intent intent = new Intent (this , Days_And_Working_Hours_Activity . class ) ;

        // هون برضو نفس الي بصير في فنكشن ال Go_To_Map_BTN
        if ( binding . AddDaysAndWorkingHoursBTN . getText ( ) . toString ( ) . contains ( "تعديل" ) )
            intent . putExtra ( "Action" , "Edit" ) ;
        else
        {
            // هون بعمل تهيئه لهاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس مشان ما يعطيني خطا لما بدي اعمل اي عمليه عليها
            WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = new ArrayList < > () ;

            intent . putExtra ( "Action" , "Add" );
            /*
                هاد السطر الي تحت الي بعمله هو انه لما بدي اضيف ايام و ساعات العمل لمكان عمل جديد الي بعمله انه بفضي ال list الي بتخزن فيها ايام و ساعات

                العمل على الكامل عشان ما يظهر الي ايام و ساعات العمل الي اخترتها لمكان العمل الي ضفته قبل مكان العمل الجديد الي بدي اضيف اله ايام و ساعات العمل
            */
            WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . clear ( ) ;
        }

        startActivityForResult ( intent , 2 ) ;
    }

    // هاد الفنكشن بتنفذ لما نضغط على زر الحفظ
    public void Save_Workplace_Data_BTN ( View view )
    {
        // هسه هاي الاف بتشيك انه هل الدكتور عبى كل البيانات المطلوبه اذا عبى كل البيانات رح يحفظها و يرجعه للشاشه الي قبل هاي غير هيك رح يعرض اله مسج انه لازم يعبي كل البيانات
        if
        (
            // كل هدول الي تحت هاد الكومنت و داخل قوسين الاف عشان اتاكد انه عبى كل البيانات المطلوبه
            ! binding . WorkPlaceLocationCoordinates . getText ( ) . toString ( ) . contains ( "على" ) &&
            ! binding . DaysAndWorkingHoursTextView  . getText ( ) . toString ( ) . contains ( "قم"  ) &&
            ! binding . WorkPlaceAddress             . getText ( ) . toString ( ) . isEmpty  (       ) &&
            ! binding . WorkPlaceName                . getText ( ) . toString ( ) . isEmpty  (       ) &&
            ! binding . PhoneNumber                  . getText ( ) . toString ( ) . isEmpty  (       )
        )
        {

            /*
                هون احنا بنعرف اوجبكت من الكلاس Intent و بخليه يساوي intent الشاشة الي قبلها و الي هي شاشة بيانات مكان العمل عشان لما نضغك على زر الحفظ يرجعنا لشاشة بيانات مكان العمل ولما نعرف intent بهاي الطريقه احنا هيك بنجيب ال intent الي جابنا لهاي الشاش
            */
            Intent intent = getIntent ( ) ;

            // هون بشيك اذا كنت داخل هاي الشاشه عشان اضيف الداتا و الا اعدل الداتا
            if ( action . equals ( "Edit" ))
            {
                // هون لما اكون داخل الشاشه عشان اعدل باخد قيم متغيرات الاوبجكت من العناصر الي في الشاشه وهاي المتغيرات هي احداثيات مكان العمل و العنوان و اسم مكان العمل و رقم الهاتف
                WorkPlace_Data . workPlace_Data_Object . longitude         = longitude ;
                WorkPlace_Data . workPlace_Data_Object . latitude          = latitude  ;

                WorkPlace_Data . workPlace_Data_Object . workPlace_Address = binding . WorkPlaceAddress . getText ( ) . toString ( ) ;
                WorkPlace_Data . workPlace_Data_Object . workPlace_Name    = binding . WorkPlaceName    . getText ( ) . toString ( ) ;
                WorkPlace_Data . workPlace_Data_Object . phoneNumber       = binding . PhoneNumber      . getText ( ) . toString ( ) ;


                // هون برجع id مكان العمل الي عدلت على بياناته للشاشة الي قبل هاي عشان يعرف الكود على اي لوحة عرض يعدل
                intent . putExtra ( "Id" , id ) ;
            }
            else
            {
                // هون نفس الي بصير في حالة التعديل لكن الفرق انه في متغيرين زياده بعطيهم قيمه و هم ترتيب نوع مكان العمل الي اخترته من ال spinner في قائمة العناصر الي في ال spinner و نوع مكان العمل
                WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner = workPlace_Index_In_Spinner                                          ;
                WorkPlace_Data . workPlace_Data_Object . workPlace_Type                  = workPlace_Type                                                      ;

                WorkPlace_Data . workPlace_Data_Object . longitude         = longitude ;
                WorkPlace_Data . workPlace_Data_Object . latitude          = latitude  ;

                WorkPlace_Data . workPlace_Data_Object . workPlace_Address = binding . WorkPlaceAddress . getText ( ) . toString ( ) ;
                WorkPlace_Data . workPlace_Data_Object . workPlace_Name    = binding . WorkPlaceName    . getText ( ) . toString ( ) ;
                WorkPlace_Data . workPlace_Data_Object . phoneNumber       = binding . PhoneNumber      . getText ( ) . toString ( ) ;
            }
            intent . putExtra ( "days And Working Hours Text" , binding . DaysAndWorkingHoursTextView . getText ( ) . toString ( ) ) ;

            /*
                ال setResult هي فنكشن تابعه للكلاس Activity بتاخد متغيرين الاول هو ال resultCode و الثاني هو الداتا او ال

                ؛intent الي حطينا فيه البيانات الي بدنا نرجعها للشاشة الي قبل هاي الشاشه هسه ال resultCode هو عبارة عن متغير بدل على انه العمليه

                كانت ناجحه ( هسه لتحت بقلك شو العمليه الي بقصدها ) وعشان نحكي اله هاد الشي انه العمليه كانت ناجحه لازم نستدعي فنكشن

                ال setResult عشان نحكي من خلاله انه العمليه كانت ناجحه بنخلي قيمة ال resultCode تساوي RESULT_OK وهاي ال

                ؛RESULT_OK هي عبارة عن قيمه ثابته موجوده في كلاس ال Activity وتساوي -1 وبتدل على انه العملية كانت ناجحه

                فلو جيتي حطيتي الماوس على ال RESULT_OK و كبستي ctrl مع ضغطه على الماوس رح يدخلك على كلاس ال Activity

                 و يعرض الك تعريف ال RESULT_OK ويفرجيكي قيمتها الي هي -1


                هسه العمليه الي بقصدها هي عملية شرح شو هي ال RESULT_OK بواسطة ال Activity الحالية و الي هي شاشة تعبئة بيانات مكان العمل

                 وفي فنكشن ال setResult لما بدنا نرجع قيمه للشاشه الي قبل هاي الشاشه لازم نستعمل ال RESULT_OK كقيمه لل resultCode عشان

                 في الشاشة الي قبل هاي الشاشه نقدر نحصل القيمه الي جايه من

                 الشاشه الحالية والي هي شاشة تعبئة بيانات العمل في حالتنا هون
            */
            setResult ( RESULT_OK , intent ) ;

            /*
                هاد الفنكشن finish تابع لكلاس ال Activity و وظيفته انه بعد ما نبكس على زر الحفظ يحرر

                شاشة تعبئة بيانات العمل من الرام عشان ما يصير اشي اسمه memory leaks او resource leaks والي هم باختصار

                بسببو انه الشاشه الي خلصنا منها تضل في الرام تسحب فيها طول استخدام التطبيق و تؤدي بالنهايه

                انه الجهاز يصير يعلق

                و فنكشن ال finish كانه بتحكي للنظام انا خلص طلعت من شاشة تعبئة بيانات مكان العمل ما تخلي اي شي بخصها في الرام
            */
            finish ( ) ;
        }
        else
        Snack_Bar
        (
            "يرجى التاكد بانك قمت بتعبئة جميع البيانات المطلوبه ومن ضمنها ايام و ساعات العمل و مكان " + workPlace_Type + " على خرائط جوجل"
        );

    }

    // --------------- نهاية الجزء الي فيه الفنكشن الي بخصو الازرار في هاي الشاشه ---------------


    /**/


    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو هاد الكلاس ---------------
    // هاد الفنشكن بتنفذ لما نضغط على زر الرجوع الي موجود في الشاشه من تحت الي هو تاع النظام
    @Override
    public void onBackPressed ( )
    {

        Snack_Bar
        (
            getIntent ( ) . getExtras ( ) . getString ( "Action" ) . equals ( "Add" ) ?

            "لايمكنك الرجوع للخلف\n\n" +
            "يجب تعبئة بيانات مكان العمل جميعها بما فيها مكان العمل على خرائط قوقل و ايام و ساعات العمل "
            :
            "لايمكنك الرجوع للخلف\n\n اذا كنت لم تقم باي تعديل يمكنك الضغط على زر الحفظ للرجوع الى الشاشة السابقه \n\n" +
            "لكن اذا قمت باي تعديل يجيب ان تقوم بتعديل اوقات العمل بالشكل الصحيح ثم قم بالضغط على زر الحفظ"
        );
    }


    /*
        هون هاد الفنكشن وظيفته نفس وظيفة فنكشن ال onActivityResult الي موجود في كلاس

        ال Doctor_Data_Activity لكن هون بتعامل مع شاشة ايام و ساعات العمل و شاشة الخارطه
    */

    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {

        super . onActivityResult ( requestCode , resultCode , data ) ;

        if ( resultCode == RESULT_OK )
        {
            /*
                 هون كونه بتعامل مع شاشتين الي هم شاشة الخارطه و شاشة ايام و ساعات العمل فرح يكون عندي 2 request code لهيك لازم استحدم switch

                 عشان احط الكود الي بخص كل شاشه في case مختلف وحسب ال request code الي جاي لهاد الفنكشن بنفذ ال case المطلوبه

                 واذا حابه تعرفي شو ال request code الي حددتهم الهم ارجعي لفنكشن ال Go_To_Map_BTN و فنكشن ال Add_Days_And_Working_Hours_BTN

                 على سطر فنكشن ال startActivityForResult لحتى تعرفي انا شو معطيهم قيمه لل request code الي بخص كل وحده منهم
             */
            switch ( requestCode )
            {
                // هاد ال case بخص الخارطه
                case 1 :
                {
                    /*
                        هون عنا متغيرين معرفين في الكلاس الي احنا فيه هلا والي هو كلاس تعبئة بيانات مكان العمل

                        المتغيرين بمثلو مكان موقع العمل الي حدده المستخدم على خط الطول و على خط العرض

                        هاد latitude احداثيات خط العرض الخاصه بموقع العمل

                        والتاني هاد longitude في احداثيات خط الطول الخاصه بموقع العمل

                        و ال intent الي بنستخرج منه الداتا في حالتنا هون هو المتغير الي اسمه data و هو بستقبل ال intent الي رجعنا لهاي الشاشه
                     */
                    longitude = data . getExtras ( ) . getDouble ( "longitude"  ) ;
                    latitude  = data . getExtras ( ) . getDouble ( "latitude"   ) ;

                    /*
                        هسه هدول السطرين الي تحت و السطرين الي فوق مثل ما قلت وشرحت عن هاد الفنكشن في كلاس Doctor_Data_Activity انه كل شي داخله بتنفذ

                        لما نرجع من الشاشه الي انتقلنا الها عن طريق الشاشه الحاليه لذلك كل شي داخل هاي ال case و داخل ال case الي تحتها رح يتنفذ بس

                        نرجع لهاي الشاشه من الشاشه الي انتقلنا الها من عن طريق هاي الشاشه
                    */

                    // هاد السطر بعدل ال text الي بكون مكتوب فيه "حدد مكان العمل على خرائط قوقل" وبحد بداله احداثيات مكان العمل الي اختاراها
                    binding . WorkPlaceLocationCoordinates . setText
                    ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + latitude + " , " + longitude ) ;

                    // هاد بغير النص الي مكتوب في زر الذهاب الى الخارطه وبحط بداله النص الي مكتوب عندك تحت
                    binding . GoToMapBTN . setText ( "تعديل احداثيات مكان " + workPlace_Type ) ;
                    break ;
                }

                // هاد ال case بخص شاشة اضافة ايام و ساعات العمل
                case 2 :
                {

                    /*
                        هون بعرف اوبجكت من هاي WorkPlace_Data الكلاس و بخليه يساوي قيمة الاوبجكت الي في هاي WorkPlace_Data الكلاس و الي اسمه
                        workPlace_data_Object عشان لما بدي اوصل لواحد من متغيرات الاوبجكت اضل ال قله WorkPlace_Data . workPlace_Data_Object
                    */
                    WorkPlace_Data workPlace_data_Object = WorkPlace_Data . workPlace_Data_Object ;

                    // هون اول شي بعمله بغير ال النص الي معروض في ال text view الي بنعرض فيه ايام و ساعات العمل من "قم باضافة ايام و ساعات العمل" ل "ايام و ساعات العمل"
                    binding . DaysAndWorkingHoursTextView . setText
                    (
                        // هاد اول شي حيكون في النص الي بعرض في المكان الي رح يعرض لدكتور ايام و ساعات العمل الي حددها
                        "ايام و ساعات العمل\n\n"
                    ) ;


                    /*
                        عنا هون for each ال element فيها عباره عن اوبجكت من الكلاس Days_And_Working_Hours

                        وهاي ال for each بتلف على List ال days_And_Working_Hours_Objects_List الي فيها

                        مجموعة الاوبجكت الخاصين بكل يوم اخترناه

                        هسه هاي foreach بتلف على كل عناصر ال list الخاصه بالاوبجكت الي في هاي WorkPlace_Data الكلاس والي هو

                        workPlace_data_Object و الي اتخزن فيها ايام و ساعات العمل و بتحطهم في ال text view الي بنعرض فيه ايام و ساعات العمل

                        مع النص الي حطيناه فوق و الي هو "ايام و ساعات العمل"
                    */
                    for ( Days_And_Working_Hours element : workPlace_data_Object . days_And_Working_Hours_Objects_List )
                    {
                        binding . DaysAndWorkingHoursTextView . setText
                        (
                            /*
                               هسه هاد السطر بجيب الي ال text الي مخزن في ال textView الي بنعرض فيه ايام و ساعات العمل و الي بكون هو هاد

                               ال text "ايام و ساعات العمل" في حالة كانت ال for each في اول لفه الها وفي اللفه الثانيه بكون ال text الي مخزن

                               في ال textView الي بنعرض فيه ايام و ساعات العمل هو عباره عن هاد ال text "ايام و ساعات العمل" + اليوم و ساعات العمل الي مخزنه في اول اوبجكت

                               في هاي days_And_Working_Hours_Objects_List ال list
                            */
                            binding . DaysAndWorkingHoursTextView . getText ( ) +

                            // بعدها هون قيمة اليوم و الي مخزنه في متغير day في الاوبجكت الي for each ماشره على ال index تبعه
                            element . day + "\n" +

                            // بعدها هون قيمة من الساعه و الي مخزنه في متغير from_Hour في الاوبجكت الي for each ماشره على ال index تبعه
                            " من الساعة : " + element . from_Hour + " " +

                            // بعدها هون القيمه الي بتحدد من الساعه صباحا او مساءا و الي مخزنه في متغير from_AM_Or_PM في الاوبجكت الي for each ماشره على ال index تبعه
                            element . from_AM_Or_PM +

                            // بعدها هون قيمة الى الساعه و الي مخزنه في متغير to_Hour في الاوبجكت الي for each ماشره على ال index تبعه
                            " الى الساعة : " + element . to_Hour + " " +

                            // بعدها هون القيمه الي بتحدد من الساعه صباحا او مساءا و الي مخزنه في متغير to_AM_Or_PM في الاوبجكت الي for each ماشره على ال index تبعه
                            element . to_AM_Or_PM
                        ) ;

                        // هون بقول اله اذا كان الاوبجكت الي ال for each ماشره على ال index تبعه مش اخر عنصر في هاي days_And_Working_Hours_Objects_List ال list ضيف هاد الفاصل على ال text تبع ال textView الي بنعرض فيه ايام و ساعات العمل
                        if
                        (
                            workPlace_data_Object . days_And_Working_Hours_Objects_List . indexOf ( element )
                            !=
                            workPlace_data_Object . days_And_Working_Hours_Objects_List . size ( ) -1
                        )
                            binding . DaysAndWorkingHoursTextView . setText
                            (
                                binding . DaysAndWorkingHoursTextView . getText ( ) . toString ( ) +
                                "\nـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ\n"
                            ) ;
                    }

                    // هون بغير النص الي في زر اضافة ايام و ساعات العمل للنص الي موجود عندك تحت
                    binding . AddDaysAndWorkingHoursBTN . setText ( "تعديل ايام و ساعات العمل" ) ;

                    break ;
                }
            }
        }

    }


    // هاد الفنكشن بتنفذ لما ندخل هاي الشاشه عشان تضيف بيانات مكان عمل جديد او نعدل على بيانات واحد من اماكن العمل
    private void WorkPlace_Page_Initialization ( )
    {

        // هاي ال array انا مخزن فيها ال id الخاصين بمكونات الشاشه عشان اتحكم في ظهورها و اخفاؤها حسب ما انا بدي و حسب حالة الشاشه
        View [ ] screen_Components =
        {
            findViewById (R . id . Days_And_Working_Hours_Text_View ) ,
            findViewById (R . id . Add_Days_And_Working_Hours_BTN   ) ,
            findViewById (R . id . WorkPlace_location_coordinates   ) ,
            findViewById (R . id . WorkPlace_Address                ) ,
            findViewById (R . id . WorkPlace_Name                   ) ,
            findViewById (R . id . Phone_Number                     ) ,
            findViewById (R . id . Go_to_Map_BTN                    )
        };


        /*
            هون بشيك اذا كنت داخل هاي الشاشة عشان اعدل ولا اعشان اضيف الداتا واذا كنت داخل عشان اضيف داتا ادخل و نفذ الي جوا الاف غير هيك يعني لو كنت

            داخل هاي الشاشه عشان اعدل الداتا لا تنفذ الي جوا الاف لانه هاد الفنكشن المفروض يشتعل بس لما ادخل هاي الشاشه عشان اضيف داتا مش عشان اعدل الداتا
        */
        if ( action . equals ( "Add" ) )
        {

            // هدول الي تحت الي بعملوه و من دون شرح تفصيلي هو انه بمسح اي قيم ممكن يكون الدكتور اختارها قبل

            binding . WorkplaceTypeSp . setSelection ( 0 ) ;

            binding . DaysAndWorkingHoursTextView . setText ( "قم باضافة ايام و ساعات العمل" ) ;
            binding . GoToMapBTN                  . setText ( "الذهاب الى الخارطه"           ) ;

            binding . WorkPlaceAddress . setText ( "" ) ;
            binding . WorkPlaceName    . setText ( "" ) ;
            binding . PhoneNumber      . setText ( "" ) ;

            longitude = 0 ;
            latitude  = 0 ;

            // هاي ال for each بتلف على عناصر هاي screen_Components ال array و بتخفي كل عنصر فيها لانه ما بدي يظهر للدكتور اشي الا بعد ما يختار نوع مكان العمل من spinner اختيار نوع مكان العمل
            for ( View element : screen_Components )
            {
                element . setVisibility ( View . GONE ) ;
            }
        }
        else
        {

            // هاد نفس الي في فنكشن ال onActivityResult في ال case رقم 2
            WorkPlace_Data workPlace_data_Object = WorkPlace_Data . workPlace_Data_Object ;

            // هون بخلي هاد workPlace_Type المتغير يساوي قيمة هاد workPlace_Type المتغير الخاص ب اوبجكت مكان العمل الي بدي اعدل على بياناته
            workPlace_Type = workPlace_data_Object . workPlace_Type ;

            // هاي ال for each بتلف على عناصر هاي screen_Components ال array و بتظهر كل عنصر فيها عشان اعرض الداتا  فيها
            for ( View element : screen_Components )
            {
                element . setVisibility ( View . VISIBLE ) ;
            }

            // هون انا بحط ال hint لكل ال editText في هدول ال 3 اسطر الي تحت
            binding . WorkPlaceAddress . setHint ( "عنوان "    + workPlace_Type ) ;
            binding . WorkPlaceName    . setHint ( "اسم "      + workPlace_Type ) ;
            binding . PhoneNumber      . setHint
            (
                // هون بشيك اذا كان نوع مكان العمل يساوي "العيادة" بخلي ال hint يساوي "رقم هاتف العيادة" غير هيك بخليه يساوي "رقم الهاتف"
                workPlace_Type . equals ( "العيادة" ) ?
                "رقم هاتف " + workPlace_Type :
                "رقم الهاتف"
            ) ;

            // هون انا بخلي ال spinner ياشر على نوع مكان العمل الي اخترته لما ضفت بيانات مكان العمل
            binding . WorkplaceTypeSp . setSelection ( workPlace_data_Object . workPlace_Type_Index_In_Spinner ) ;

            // هون انا بحط عنوان مكان العمل الي كتبته لما ضفت بيانات مكان العمل في ال editText
            binding . WorkPlaceAddress . setText ( workPlace_data_Object . workPlace_Address ) ;

            // هون انا بحط اسم مكان العمل الي كتبته لما ضفت بيانات مكان العمل في ال editText
            binding . WorkPlaceName    . setText ( workPlace_data_Object . workPlace_Name    ) ;

            // هون انا بحط رقم الهاتف الي كتبته لما ضفت بيانات مكان العمل في ال editText
            binding . PhoneNumber      . setText ( workPlace_data_Object . phoneNumber       ) ;

            /*
                الي بصير في هدول السطرين
                longitude = workPlace_data_Object . longitude ;
                latitude  = workPlace_data_Object . latitude  ;

                هو اني بجيب احداثيات مكان العمل الي موجود في المتغيرات الخاصه باحداثيات مكان العمل في الاوبجكت الي بدي اعدل على بياناته

                و بخزنها في متغيرات الخاصه باحداثيات مكان العمل في هاد الكلاس عشان بس بدي انتقل للخارطه لحتى اعدل مكان العمل استعمل هدول المتغيرين

                عشان اعرض للدكتور مكان العمل الي اختاره اول مره
            */
            longitude = workPlace_data_Object . longitude ;
            latitude  = workPlace_data_Object . latitude  ;

            // هون انا بحط احداثيات مكان العمل الي حددتها لما ضفت بيانات مكان العمل
            binding . WorkPlaceLocationCoordinates . setText ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + latitude + " , " + longitude ) ;

            // هون انا بعدل من ال text تبع زر الذهاب الى الخارطه من "الذهاب الى الخارطة" ل "تعديل احداثيات مكان العمل"
            binding . GoToMapBTN . setText ( "تعديل احداثيات مكان " + workPlace_Type ) ;


            // هون بعدل ال text تبع ال textView الي بنعرض فيه ساعات العمل من "قم باضافة ايام و ساعات العمل" وبحط بداله ايام ساعات العمل الي حددتها لما ضفت بيانات مكان العمل
            binding . DaysAndWorkingHoursTextView . setText
            (
                // هاد اول شي حيكون في النص الي بعرض في المكان الي رح يعرض لدكتور ايام و ساعات العمل الي حددها
                "ايام و ساعات العمل\n\n"
            ) ;

            for ( Days_And_Working_Hours element : workPlace_data_Object . days_And_Working_Hours_Objects_List )
            {
                binding . DaysAndWorkingHoursTextView . setText
                (
                    /*
                       هسه هاد السطر بجيب الي ال text الي مخزن في ال textView الي بنعرض فيه ايام و ساعات العمل و الي بكون هو هاد

                       ال text "ايام و ساعات العمل" في حالة كانت ال for each في اول لفه الها وفي اللفه الثانيه بكون ال text الي مخزن

                       في ال textView الي بنعرض فيه ايام و ساعات العمل هو عباره عن هاد ال text "ايام و ساعات العمل" + اول اوبجكت

                       في هاي days_And_Working_Hours_Objects_List ال list
                    */
                    binding . DaysAndWorkingHoursTextView . getText ( ) +

                    // بعدها هون قيمة اليوم و الي مخزنه في متغير day في الاوبجكت الي for each ماشره على ال index تبعه
                    element . day +

                    // بعدها هون قيمة من الساعه و الي مخزنه في متغير from_Hour في الاوبجكت الي for each ماشره على ال index تبعه
                    " من الساعة : " + element . from_Hour + " " +

                    // بعدها هون القيمه الي بتحدد من الساعه صباحا او مساءا و الي مخزنه في متغير from_AM_Or_PM في الاوبجكت الي for each ماشره على ال index تبعه
                    element . from_AM_Or_PM +

                    // بعدها هون قيمة الى الساعه و الي مخزنه في متغير to_Hour في الاوبجكت الي for each ماشره على ال index تبعه
                    " الى الساعة : " + element . to_Hour + " " +

                    // بعدها هون القيمه الي بتحدد من الساعه صباحا او مساءا و الي مخزنه في متغير to_AM_Or_PM في الاوبجكت الي for each ماشره على ال index تبعه
                    element . to_AM_Or_PM
                ) ;

                // هون بقول اله اذا كان الاوبجكت الي ال for each ماشره على ال index تبعه مش اخر عنصر في هاي days_And_Working_Hours_Objects_List ال list ضيف هاد الفاصل على ال text تبع ال textView الي بنعرض فيه ايام و ساعات العمل
                if ( workPlace_data_Object . days_And_Working_Hours_Objects_List . indexOf ( element ) != workPlace_data_Object . days_And_Working_Hours_Objects_List . size ( ) -1 )
                    binding . DaysAndWorkingHoursTextView . setText
                    (
                        binding . DaysAndWorkingHoursTextView . getText ( ) . toString ( )+
                        "\nـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ\n"
                    ) ;
            }

            // هون انا بعدل من ال text تبع زر الذهاب الى الخارطه من "اضافة ايام و ساعات العمل" ل "تعديل ايام و ساعات العمل"
            binding . AddDaysAndWorkingHoursBTN . setText ( "تعديل ايام و ساعات العمل" ) ;

        }
    }

    // هاد الفنشكن هو الي بجيب النا داتا مكان العمل الي بدي اعدل علي بياناته ومستدعيه في ال onCreate و بتنفذ لما بدي اعدل بس
    private void Get_workPlace_Data_Object ( int id )
    {
        // هون معرفين اوبجكت من هاي FirebaseFirestore الكلاس و اسمه dp و هاي الكلاس هي المسؤوله عن اي شي بصير في قاعدة البيانات في الفايربيس
        FirebaseFirestore db = FirebaseFirestore . getInstance ( ) ;

        // هون انا من خلال المتغير الي اسمه db بصل ل ال collection الي فيها ال document الي فيه الاوبجكت الي بدي اعدل على بياناته بعدها حاكي اله get يعمي جيب الي هاد الاوبجكت الي بدي اعدل علي بياناته
        db . collection ( "A" ) . document ( "C" ) . get ( ) . addOnCompleteListener ( task ->
        {
            // هون بشيك انه عملية جلب بيانات هاد الاوبجكت كانت ناجحه ادخل و نفذ الي جوا الاف
            if ( task . isSuccessful ( ) )
            {
                /*
                    هون بعرف اوبحكت اسمه document من الكلاس DocumentSnapshot و بعطيه قيمة النتيجه الي رجعت الي من هاد الفنكشن getResult باستعمال هاد task المتغير

                    هسه كلاس ال DocumentSnapshot صراحه مش عارف كيف بدي اشرح الك عنها ولا عارف شو بدي اشرح الك عنها لانه ما لقيت شي منيح عنها لحتى اشرحه

                    بس عكل حال هي عباره عن كلاس مسؤوله عن التعامل مع اي عملية بتخص ال document في الفايرستور في الفايربيس
                 */
                DocumentSnapshot document = task . getResult ( ) ;

                // هون بشيك هل ال document الي بحثت عنه انا موجود اذا موجود ادخل و نفذ الي جوا الاف
                if ( document . exists ( ) )
                {

                    /*
                        هون معرف Map اسمها workPlace_Data_Object وهي الي رح يتخزن فيها بيانات اوبجكت مكان العمل الي بدي اعدل على بياناته

                        و بتقدري تحكي عنها انها اقرب ما تكون لشي بشبه الجدول بالزبط

                        وعشان اجيب بيانات الاوبجكت واحطها في هاي ال map بحكي اله document . get بعدها داخل القوسين بعطيه اسم الاوبجكت مع ال id

                        تبع هاد الاوبجكت لانه انا عامل اسم الاوبجكت يكون هو نفسه ال id تبع الاوبجكت بحيث يكون في رقم بنهاية الاسم يعتبر ال id

                        الخاص بهاد الاوبجكت
                    */
                    Map < String , Object > workPlace_Data_Object = ( Map < String , Object > ) document . get ( "workPlace Data Object Number " + id ) ;


                    /*
                        هسه شايفه من حد هاد الكومنت
                       //--------------------------

                       لحد هاد الكومنت

                       //-------------------------------------------------

                       هاد كله بعمل شي هو تقريبا نفس الي بصير عشان اجيب بيانات الاوبجكت

                       وهاد الشي هو اني اجيب ايام و ساعات العمل

                    هسه كل واحد من ايام و ساعات العمل مخزن في map مخزنه في داخل ArrayList او بعبارة اخرى : انه اي يوم احنا قمنا باضافته تخزن في داخل map في الفايربيس فلو

                     اخترنا 3 ايام مثلا رح يكون عنا في الفايربيس موجود 3 map كل وحده منهم بتخص واحد من ال 3 ايام الي اخترناها وهدول ال 3 map كلهم مخزنين داخل ArrayList

                    عشان هيك لازم اعمل ArrayList وهي هاي days_And_Working_Hours_Objects_List_From_Firebase و ال ArrayList هي نفس ال List بالزبط ما بتفرق عنها شي

                    بعده باستخدام هاي workPlace_Data_Object ال map الي عرفناها فوق و الي بتحتوي بيانات مكان العمل الي بدنا نعدل بياناته ومن ضمنها ال list او الي هي نفسها ArrayList و الي مخزن فيها كل ال map الي بخصو ايام و ساعات العمل الي اخترناهم

                     بجيب هاي days_And_Working_Hours_Objects_List ال list الي مخزن فيها ايام و ساعات العمل من الفايربيس و بخزنها في ال ArrayList الي عملناها و الي هي هاي days_And_Working_Hours_Objects_List_From_Firebase

                     وكل الحكي الي قلته هاد بصير باستعمال هاد السطر ↓↓↓↓↓

                     ArrayList < Map < String , Object > > days_And_Working_Hours_Objects_List_From_Firebase
                     = ( ArrayList < Map < String , Object > > ) workPlace_Data_Object . get ( "days_And_Working_Hours_Objects_List" ) ;

                        بعدها اعرف هاي days_And_Working_Hours_List_Objects ال List باستخدام هاد السطر ↓↓↓↓↓ عشان اخزن فيه كل map الخاصين بايام و ساعات العمل

                        List < Days_And_Working_Hours > days_And_Working_Hours_List_Objects = new ArrayList < > (  ) ;

                        بعدها بعمل for loop تلف على كل ال map الخاصين بايام و ساعات العمل و الي مخزنين  في هاي
                         ؛days_And_Working_Hours_Objects_List_From_Firebase ال ArrayList

                        وتمسك كل map فيها و تخزنها في هاي days_And_Working_Hours_Object_From_Firebase ال map الي داخل ال for loop وهاد الشي بعمله هاد السطر ↓↓↓↓↓

                        Map < String, Object > days_And_Working_Hours_Object_From_Firebase = days_And_Working_Hours_Objects_List_From_Firebase . get ( i ) ;

                        و في كل لفه بتخزن في هاي days_And_Working_Hours_Object_From_Firebase ال map وحده من ال map الخاصين بايام و ساعات العمل والي مخزنين داخل هاي days_And_Working_Hours_Objects_List_From_Firebase ال ArrayList في الفايربيس وهيك لحد ما اخلص كل ال map الخاصين بايام و ساعات العمل

                        والي مخزنين داخل هاي days_And_Working_Hours_Objects_List_From_Firebase ال ArrayList في الفايربيس

                        بعدها بعمل اوبجكت من هاي Days_And_Working_Hours الكلاس اسمه days_And_Working_Hours_Object عشان اخزن فيه ايام و ساعات العمل الي مخزنين في ال map الي ال for loop ماشره على ال index تبعها وهاد السطر ↓↓↓↓↓↓ هو الي بعرف هاد الاوبجكت

                         Days_And_Working_Hours days_And_Working_Hours_Object = new Days_And_Working_Hours ( ) ;

                         بعدها باستعمال هاي days_And_Working_Hours_Object_From_Firebase ال map بجيب كل القيم الي فيها و بحطهم في هاد days_And_Working_Hours_Object الاوبجكت

                         والي بعمل هاد الشي هو هاد الكود ↓↓↓↓↓↓

                        days_And_Working_Hours_Object . from_AM_Or_PM = days_And_Working_Hours_Object_From_Firebase . get ( "from_AM_Or_PM" ) + "" ;
                        days_And_Working_Hours_Object . to_AM_Or_PM   = days_And_Working_Hours_Object_From_Firebase . get ( "to_AM_Or_PM"   ) + "" ;
                        days_And_Working_Hours_Object . from_Hour     = days_And_Working_Hours_Object_From_Firebase . get ( "from_Hour"     ) + "" ;
                        days_And_Working_Hours_Object . to_Hour       = days_And_Working_Hours_Object_From_Firebase . get ( "to_Hour"       ) + "" ;
                        days_And_Working_Hours_Object . day           = days_And_Working_Hours_Object_From_Firebase . get ( "day"           ) + "" ;

                        بعدها بضيف هاد الاوبجكت على هاي days_And_Working_Hours_List_Objects ال list والي بعمل هاد الشي هو هاد السطر ↓↓↓↓↓↓

                        days_And_Working_Hours_List_Objects . add ( days_And_Working_Hours_Object ) ;



                        وبس تخلص ال for loop بصير عنا نسخه عن ايام و ساعات العمل الخاصه بالمكان الي بدنا نعدل على بياناته في هاي ال days_And_Working_Hours_List_Objects

                        بعدها لما نجي تعبي ببانات مكان العمل في الاوجكت الي في هاي Days_And_Working_Hours الكلاس و الي اسمه workPlace_Data_Object بعطي هاي days_And_Working_Hours_List_Objects ال list الخاصه فيه هاي days_And_Working_Hours_List_Objects ال list الي هي فيها نسخة من ايام و ساعات العمل الخاصين بمكان العمل الي بدنا نعدل بياناته الي في الفاير بيس

                        ولي بعمل هاد الشي هو هاد السطر ↓↓↓↓↓↓↓

                        WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = days_And_Working_Hours_List_Objects ;

                    */
                    //--------------------------

                    ArrayList < Map < String , Object > > days_And_Working_Hours_Objects_List_From_Firebase
                    = ( ArrayList < Map < String , Object > > ) workPlace_Data_Object . get ( "days_And_Working_Hours_Objects_List" ) ;

                    List < Days_And_Working_Hours > days_And_Working_Hours_List_Objects = new ArrayList < > (  ) ;

                    for ( int i = 0 ; i < days_And_Working_Hours_Objects_List_From_Firebase . size ( ) ; i++ )
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

                    //-------------------------------------------------

                    // هسه هاد الي تحت ما هو الا عباره عن جلب بيانات مكان العمل الي بدي اعدل عليها و وضعها في هاد workPlace_Data_Object الاوبجكت الي في هاي ال WorkPlace_Data الكلاس
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = days_And_Working_Hours_List_Objects                                                              ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner
                    = Integer . parseInt ( workPlace_Data_Object . get ( "workPlace_Type_Index_In_Spinner" ) + "" ) ;

                    WorkPlace_Data . workPlace_Data_Object . longitude = Double . parseDouble ( workPlace_Data_Object . get ( "longitude" ) + "" ) ;
                    WorkPlace_Data . workPlace_Data_Object . latitude  = Double . parseDouble ( workPlace_Data_Object . get ( "latitude" ) + "" ) ;

                    WorkPlace_Data . workPlace_Data_Object . workPlace_Address              = workPlace_Data_Object . get ( "workPlace_Address"              ) + "" ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Name                 = workPlace_Data_Object . get ( "workPlace_Name"                 ) + "" ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type                 = workPlace_Data_Object . get ( "workPlace_Type"                 ) + "" ;
                    WorkPlace_Data . workPlace_Data_Object . phoneNumber                    = workPlace_Data_Object . get ( "phoneNumber"                    ) + "" ;

                }
            }
        })
        /*
             و هاد السطر
             addOnSuccessListener ( aVoid -> WorkPlace_Page_Initialization ( ) );

             بقول بس تجيب كل البيانات و تحطها في هاد workPlace_Data_Object الاوبجكت الي في هاي ال WorkPlace_Data الكلاس

             استدعي هاد WorkPlace_Page_Initialization الفنكشن الي بعرض الي الداتا الي جبتها و حطيتها في هاد workPlace_Data_Object الاوبجكت الي في هاي ال WorkPlace_Data الكلاس
         */
        . addOnSuccessListener ( aVoid -> WorkPlace_Page_Initialization ( ) );
    }

    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make ( binding . Constraint , Message , 7000 ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf ( ContextCompat. getColor ( this , R . color . Snack_bar_BG_Color ) ) ) ;

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

    // --------------- نهاية الجزء الي فيه الفنكشن الي بخصو هاد الكلاس ---------------


    /**/


    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو ال Spinner تبع مكان العمل ---------------

    /**/

    //هاد الفنكشن مستدعيه في ال onCreate و وظيفته هي انه يجهز القائمه الي الدكتور بختار منها مكان العمل
    private void Adapter_Initialization ( )
    {
        // هون احنا بنحدد العناصر الي رح تنعرض النا لما نضغط على ال spinner
        ArrayAdapter < CharSequence > adapter = ArrayAdapter . createFromResource
        ( this , R . array . Workplace_Locations , R . layout . spinner_drop_down_items_text ) ;

        // فيي هاد السطر بعطيه شكل النص تبع القائمه الي رح تظهر النا لما نضغط على ال spinner
        adapter . setDropDownViewResource ( R . layout . spinner_drop_down_items_text ) ;

        // في هاد السطر بنربط ال array الي فيها الداتا الي رح تظهر لما نضغط على ال spinner
        binding . WorkplaceTypeSp . setAdapter ( adapter ) ;

        // في هاد السطر بنعمل تهيئه لحدث اختيار عنصر من ال spinner
        binding . WorkplaceTypeSp . setOnItemSelectedListener ( this ) ;
    }

    // هاد بخص ال spinner الي فيه اماكن العمل بتنفذ او بتسدعى لما نتخار اي شغله من القائمه الي فيه
    @Override
    public void onItemSelected ( AdapterView < ? > parent , View view , int position , long id )
    {

        View [ ] screen_Components =
        {
            findViewById (R . id . Days_And_Working_Hours_Text_View ) ,
            findViewById (R . id . Add_Days_And_Working_Hours_BTN   ) ,
            findViewById (R . id . WorkPlace_location_coordinates   ) ,
            findViewById (R . id . WorkPlace_Address                ) ,
            findViewById (R . id . WorkPlace_Name                   ) ,
            findViewById (R . id . Phone_Number                     ) ,
            findViewById (R . id . Go_to_Map_BTN                    )
        };

        /*
           هون بحكي اله اذا كان العنصر الي اختاره الدكتور من القائمه ترتبيبه مش الاول في قائمة العناصر الي اختار منها الدكتور

           مكان العمل ادخل و نغذ الي جوا الاف و حاكي اله هيك لانه ي منار اول عنصر في القائمه مكتوب فيه "اختر مكان العمل"

           و انا ما بدي هاي القيمه بدي اي قيمه ثانيه غيرها

            و هون
            action . equals ( "Add" )

            بشيك اذا كنت داخل هاي الشاشه عشان اعدل لانه ي منار بس ادحل عشان اعدل بخلي ال spinner ياشر على

            نوع مكان العمل الي اخترته لما ضفت بيانات مكان العمل ولما اعمل هاي الحركه بستدعي هاد الفنكشن و بنفذ هاي

            ال case الي بتخص نوع مكان العمل الي اخترته لما ضفت بيانات مكان العمل لهيك لازم اشيك انا داخل اعدل ولا

            اضيف داتا عشان اذا داخل اعدل ما اخليه يستدعي هاد الفنكشن و ينفذ ال case الخاص بنوع مكان العمل الي اخترته
        */
        if ( position != 0 && action . equals ( "Add" ) )
        {

            // هون بجيب نوع مكان العمل الي اختاره و بخزنه في هاد المتغير عشان استعمله في ال switch عشان اقدر احدد اي case لازم انفذ لما اختار شي من ال spinner
            workPlace_Type =  parent . getItemAtPosition ( position ) . toString ( ) ;

            // طبعا ال 3 cases بشتغلو نفس الشغل لكن بتختلف القيم الي داخلهم
            switch ( workPlace_Type )
            {

                // هاي بتتنفذ اذا كان مكان العمل الي اختاره عيادة خاصه
                case "عيادة خاصة" :
                {
                    // هون انا بخزن ترتيب العنصر الي اخترته من ال spinner في قائمة العناصر االي في ال spinner يعني ال index تبعه كونه العناصر الي في ال spinner عباره عن array و كل عنصر في ال array اله index
                    workPlace_Index_In_Spinner = 3 ;

                    // هون انا بعدل قيمة هاد workPlace_Type المتغير من "عيادة خاصه" ل "العيادة" وعملت هيك عشان ي منار بدي اسعمله في لوحة عرض بيانات مكان العمل
                    workPlace_Type = "العيادة" ;

                    // هسه هون بضيف اوبجكت لمكان العمل الي بدنا نعبي بياناته و هون ما بعطيه كل البيانات بعطيه بس البيانات الي الها قيم وباقي القيم بعطيها اله في فنكشن ال Save_Workplace_Data_BTN
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = null ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner     = workPlace_Index_In_Spinner                                                   ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type                      = workPlace_Type                                                               ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Address                   = null                                                                         ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Name                      = null                                                                         ;
                    WorkPlace_Data . workPlace_Data_Object . phoneNumber                         = null                                                                         ;

                    // هون انا بعدل ال hint الي بكون مكتوب في ال edit text نبع اسم و عنوان ورقم هاتف العياده
                    binding . WorkPlaceLocationCoordinates . setText ( "حدد مكان العيادة على خرائط قوقل" ) ;
                    binding . WorkPlaceAddress             . setHint ( "عنوان العيادة"                   ) ;
                    binding . WorkPlaceName                . setHint ( "اسم العيادة"                     ) ;
                    binding . PhoneNumber                  . setHint ( "رقم هاتف العيادة"                ) ;

                    break ;
                }

                // هاي بتتنفذ اذا كان مكان العمل الي اختاره مشفى
                case "مشفى" :
                {

                    workPlace_Index_In_Spinner = 4 ;

                    workPlace_Type = "المشفى" ;

                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = null ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner     = workPlace_Index_In_Spinner                                                   ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type                      = workPlace_Type                                                               ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Address                   = null                                                                         ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Name                      = null                                                                         ;
                    WorkPlace_Data . workPlace_Data_Object . phoneNumber                         = null                                                                         ;

                    binding . WorkPlaceLocationCoordinates . setText ( "حدد مكان المشفى على خرائط قوقل" ) ;
                    binding . WorkPlaceAddress             . setHint ( "عنوان المشفى"                   ) ;
                    binding . WorkPlaceName                . setHint ( "اسم المشفى"                     ) ;
                    binding . PhoneNumber                  . setHint ( "رقم الهاتف"                     ) ;

                    break ;
                }

                // هاي بتتنفذ اذا كان مكان العمل الي اختاره مركز صحي او مركو تابع للانروا
                default :
                {
                    // هون بشيك انه اختار مركز تابع للانروا ولا اختار مركز صحي
                    workPlace_Index_In_Spinner = workPlace_Type . contains ( "تابع" ) ? 1 : 2 ;

                    workPlace_Type = "المركز" ;

                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = null ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner     = workPlace_Index_In_Spinner                                                   ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type                      = workPlace_Type                                                               ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Address                   = null                                                                         ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Name                      = null                                                                         ;
                    WorkPlace_Data . workPlace_Data_Object . phoneNumber                         = null                                                                         ;

                    binding . WorkPlaceLocationCoordinates . setText ( "حدد مكان المركز على خرائط قوقل" ) ;
                    binding . WorkPlaceAddress             . setHint ( "عنوان المركز"                   ) ;
                    binding . WorkPlaceName                . setHint ( "اسم المركز"                     ) ;
                    binding . PhoneNumber                  . setHint ( "رقم الهاتف"                     ) ;

                    break ;
                }

            }

            // هاي الفور الي بتعمله هو انه بس اختار شي من ال spinner بتظهر الي كل العناصر المخفيه في الشاش
            for ( View element : screen_Components )
            {
                element . setVisibility ( View . VISIBLE ) ;
            }
        }
    }

    // هاد كمان تابع اله لكن هاد بتنفذ لما ما نختار اشي
    @Override
    public void onNothingSelected ( AdapterView < ? > parent )
    {

    }

    // --------------- نهاية الجزء الي فيه الفنكشن الي بخصو ال Spinner تبع مكان العمل ---------------

}


@SuppressLint ( "SuspiciousIndentation" )
@SuppressWarnings ( "unused" )
class WorkPlace_Data
{

    // هسه ي منار هاي الكلاس انا عاعملها عشان بس اعمل منها اوبجكت بستقبل مني بيانات مكان العمل و بخزنها في المتغيرات الخاصه فيه و الي هي تحت

    // وهاد هو الاوبجكت الي انا بحكي عنه
    public static WorkPlace_Data workPlace_Data_Object = new WorkPlace_Data ( ) ;



    // هدول المتغيرات الي بمتلكهم الاوبحكت الي فوق


    // بتخزن فيه اسم مكان العمل الي بناخده من ال edit text
    public String workPlace_Name ;

    // هاد بتخزن فيه عنوان مكان العمل لي بناخده من ال edit text
    public String workPlace_Address ;

    // هاد بتخزن فيه رقم الهاتف لي بناخده من ال edit text
    public String phoneNumber ;

    // هاد بتخزن فيه نوع مكان العمل الي بناخده من ال spinner بنختار منه مكان العمل
    public String workPlace_Type ;

    // هاد بخزن فيه index العنصر الي بختاره من ال spinner
    public int workPlace_Type_Index_In_Spinner ;

    // هدول الي بخزن فيهم احداثيات مكان العمل على الخارطه
    public double longitude, latitude ;

    // هاي ال list الي بتخزن فيها ايام و ساعات العمل لكل مكان بنضاف ومستعملها في خاي Days_And_Working_Hours_Activity الكلاس و هاي Days_And_Working_Hours الكلاس
    public List < Days_And_Working_Hours > days_And_Working_Hours_Objects_List ;


    // شايفه كل هدول الفنكشن الي تحت انا مش مستعمل اي واحد منهم في الكود هدول محطوطين عشان الفايربيس تقدر توصل لقيم متغيرات الاوبجكت لحتى يتخزنو في الفايربيس و من دونهم رح يعطي خطا

    public String getworkPlace_Name ( )
    {
        return workPlace_Name ;
    }

    public String getworkPlace_Address ( )
    {
        return workPlace_Address ;
    }

    public String getphoneNumber ( )
    {
        return phoneNumber ;
    }

    public String getworkPlace_Type ( )
    {
        return workPlace_Type ;
    }

    public int getworkPlace_Type_Index_In_Spinner ( )
    {
        return workPlace_Type_Index_In_Spinner ;
    }

    public double getLongitude ( )
    {
        return longitude ;
    }

    public double getLatitude ( )
    {
        return latitude ;
    }
    public List < Days_And_Working_Hours > getdays_And_Working_Hours_Objects_List ( )
    {
        return days_And_Working_Hours_Objects_List ;
    }
}


