package com.example.mamababyjourney.Create_An_Account_And_Sign_In.Info;

import com.example.mamababyjourney.databinding.ActivityDaysAndWorkingHoursBinding;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.res.ColorStateList;
import android.annotation.SuppressLint;
import android.view.ViewTreeObserver;
import com.example.mamababyjourney.R;
import android.widget.ArrayAdapter;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.content.Intent;
import android.os.Bundle;
import java.util.Objects;
import android.view.View;

@SuppressWarnings ( { "ConstantConditions"} )
@SuppressLint ( "ClickableViewAccessibility" )

public class Days_And_Working_Hours_Activity extends AppCompatActivity implements AdapterView . OnItemSelectedListener
{

    ActivityDaysAndWorkingHoursBinding binding ;

    // هاد المتغير انا مستعمله عشان اخزن فيه اسم ال spinner الي بنضعط عليه عشان نختار منه لقدام بتعرفي شو لازمته
    public static String spinner_Name = "" ;

    // هاد المتغير انا مستعمله عشان امنع حدوث خطا معين
    public static boolean flag = false;

    /*
        هون عنا array من نوع int وبتحتوي على كل ال id الخاصين بال check boxes

        و ليه من نوع int لانه ال id بكون عباره عن قيمه نوعها int وبرضو لتحت ببين لازمتها
     */
    int [ ] days_Check_Boxes_Ids =
    {
        R . id . Wednesday_Check_Box ,
        R . id . Saturday_Check_Box  ,
        R . id . Thursday_Check_Box  ,
        R . id . Tuesday_Check_Box   ,
        R . id . Sunday_Check_Box    ,
        R . id . Monday_Check_Box    ,
        R . id . Friday_Check_Box
    };

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivityDaysAndWorkingHoursBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Adapters_Initialization ( ) ;

        Initialization_Days_Check_Boxes_For_Edit ( ) ;

        Initialization_Days_Check_Boxes_For_Add ( ) ;
    }



    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو هاد الكلاس ---------------

    // هاد الفنكشن المسؤول عن تجهيز حدث اختيار او الغاء اختيار اليوم لحتى يصير اكم شغله محدده ( لقدام بتعرفي شو هم الاكم شغله هدول ) في حالة اختيار او الغاء اختيار اليوم
    private void Initialization_Days_Check_Boxes_For_Add ( )
    {
        /*
             طيب هاد الفنكشن بستدعى اول ما تفتح الشاشه لانه ببساطه مستدعيه في ال onCreate

             والي بعمله هو انه بقوم بتجهيز حدث اختيار و الغاء اختيار اليوم

             بحيث انه بس يختار واحد من الايام يضيف اوبجكت خاص بهاد اليوم في List of objects انا معرفها من هاي Days_And_Working_Hours الكلاس

              و اسمها days_And_Working_Hours_Objects_List و هي خاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس و هاد الاوبجكت بحتوي اسم اليوم الي تم اختياره بالاضافه الى ساعات العمل في هاد اليوم الي تم اختياره

             والي بصير في حالة اختيار واحد من الايام بجي بجيب التكست تاع ال check box الخاص باليوم الي تم اختياره

             وبخزنه في اوبجكت اليوم الي تم اختياره و تحديدا في المتغير الخاص باليوم لهاد الاوبجكت تبع اليوم

             ولما يشيل الاختيار عن اليوم الي حدده برضو هاد الفنكشن بقوم بمسح اوبجكت اليوم الي تم الغاء اختياره من ال List الي ذكرتها قبل مع ساعات العمل الخاصه فيه

             وكل هاد الحكي بيعمله من خلال استدعاء فنكشن ال Initialization_Days_Check_Boxes

             وهاد الفنكشن كل الي عليه انه يمسك ال array الخاصه بال id's تبعين ال check boxes و يلف عليهم واحد واحد من خلال هاي
              ؛for (  int element : days_Check_Boxes_Ids ) ال for each الي موجوده عندك في هاد الفنكشن و في كل لفه
              هاد element المتغير بتخزن فيه id ال check box الي ال for each ماشره على ال index الي بحتوي ال id تبعه
         */

        /*
            عنا هاي spinners ال array من نوع Spinner بتخزن فيها spinners ساعات العمل الخاصين باليوم الي تم اختياره

            عشان اظهرهم لما احدد اليوم تبعهم و عشان اخفيهم لما اشيل التحديد عن اليوم الخاص فيهم و اظهرهم لما بدي احدد ساعات العمل
         */
        Spinner [ ] spinners ;

        for (  int element : days_Check_Boxes_Ids )
        {
            /*
                هسه الي داخل قوسين ال switch

                من خلال فنكشن ال getResources بنستدعي فنكشن ال getResourceEntryName والي بتاخد منا id

                معين و بترجع النا اسم الاداة الي اعطيناها ال id تبعها وفي حالتنا هون ال id مخزن في المتغير الي

                اسمه element و الي ماخد قيمته من محتويات ال index الي واقفه عنده ال for each

                و ال getResources هي فنكشن تم توارثها من الكلاس AppCompatActivity

                عند تعريف الكلاس فوق في هاد السطر
                public class Days_And_Working_Hours_Activity extends AppCompatActivity

                و تحديدا لم قلنا اله extends AppCompatActivity

                وهاي الفنكشن عباره عن فنكشن بتخيلنا قادرين نصل لموراد التطبيق مثل الالوان و القيم النصيه (strings)
             */

            switch ( getResources ( ) . getResourceEntryName ( element ) )
            {
                /*
                    داخل ال switch عنا 7 Cases كل واحد بتنفذ حسب اسم check box الي برجعه النا هاد السطر

                     getResources ( ) . getResourceEntryName ( element )

                     يعني مثلا لو جينا على هاي days_Check_Boxes_Ids ال array اخذنا اول عنصر فيها الي هو هاد

                     ؛ R . id . Saturday_Check_Box والي هو نفسه id ال check box تبع يوم السبت

                     والي ال index تبعه 0 وهو نفس ال index الي بتبلش ال for each تشتغل من عنده في الحاله هاي رح يتخزن في

                     ال element الي في ال for each ال id تبع ال check box الخاص بيوم السبت

                     وبس ننفذ هاد السطر
                     getResources ( ) . getResourceEntryName ( element ) ;

                     الي بين قوسين ال switch بعطينا هاي القيمه "Saturday_Check_Box"

                     وبهيك بجي بنفذ اول case داخل ال switch والي هي بتخص يوم السبت

                     وفي كل مره بتلف ال for each بصير داخل اقواس ال switch اسم ال check box الي id

                     الموجود في ال index الجاي مرتبط فيه وبنفذ ال case  الي بتطابق مع اسمه

                     ان شاء الله تكون وصلت
                 */

                /*
                    طبعا كل ال cases ال 7 بعملو نفس الشي لكن الي بختلف فيهم هي القيم الي داخلهم

                    يعني اذا كان ال index ألحالي بحتوي ال id الخاص بال check box تبع يوم السبت

                    في ال case الخاص بال check box تبع يوم السبت بحط القيم الخاصه بيوم السبت
                 */
                case "Saturday_Check_Box":
                {

                    // هون بنخزن sinners ساعات العمل باليوم الي حددناه في هاي spinners ال array
                    spinners = new Spinner [ ]
                    {
                        findViewById ( R . id . Saturday_From_Am_Or_Pm ) ,
                        findViewById ( R . id . Saturday_To_Am_Or_Pm   ) ,
                        findViewById ( R . id . Saturday_From_Hour     ) ,
                        findViewById ( R . id . Saturday_To_Hour       )
                    };

                    /*
                        هون بنستدعي فنكشن ال Initialization_Days_Check_Boxes و بنعطيه القيم التاليه

                        1- ال Text view الحاص باليوم الي اتحدد و الي بكون مكتوب فيه ساعات العمل والي بكون فوق spinners الساعات وتحت
                        ال check box تبع اليوم

                        2- ال check box الخاص باليوم الي بندنا نتاكد انه تم اختياره او الغاء اختياره

                        3- ال array الي فيها ال spinners تبعين ساعات العمل الخاصين باليوم الي اخترناه عشان اظهرهم عند اختيار اليوم او
                        اخفيهم عند الغاء تحديد اليوم

                        4- اسم اليوم الي تحدد ال check box تبعه وهاد ضروري عشان الحذف لما نعمل الغاء تحديد لليوم

                        واخر شي ال action وهاد كمان ضروري لانه فنكشن ال Initialization_Days_Check_Boxes مشترك بين هاد الفنكشن و بين هاد

                        ؛Initialization_Days_Check_Boxes_For_Edit الفنكشن والاثنين بشتغلو نفس الشغل تقريبا مع وجود بعض الاختلافات البسيطه

                       تقريبا و هاد ال action هو الي بحدد اي فنكشن الي استدعى فنكشن ال

                        ؛Check_BoxInitialization_Days_Check_Boxes عشان يعمل الي بده اياه

                        وما يعمل الشي الي لازم يعمله لفنكشن تاني
                     */

                    Initialization_Days_Check_Boxes
                    (
                        findViewById ( R . id . Saturday_Work_Hours_Text_view ) ,
                                 findViewById ( element                                ) ,
                        spinners ,
                        "السبت",
                        "Select or deselect"
                    );
                    break ;
                }

                case "Sunday_Check_Box":
                {


                    spinners = new Spinner [ ]
                    {
                        findViewById ( R . id . Sunday_From_Am_Or_Pm ) ,
                        findViewById ( R . id . Sunday_To_Am_Or_Pm   ) ,
                        findViewById ( R . id . Sunday_From_Hour     ) ,
                        findViewById ( R . id . Sunday_To_Hour       )
                    };

                    Initialization_Days_Check_Boxes
                    (
                        findViewById ( R . id . Sunday_Work_Hours_Text_view ) ,
                                 findViewById ( element                              ) ,
                        spinners ,
                        "الاحد" ,
                        "Select or deselect"
                    );
                    break ;
                }

                case "Monday_Check_Box":
                {

                    spinners = new Spinner [ ]
                    {
                        findViewById ( R . id . Monday_From_Am_Or_Pm ) ,
                        findViewById ( R . id . Monday_To_Am_Or_Pm   ) ,
                        findViewById ( R . id . Monday_From_Hour     ) ,
                        findViewById ( R . id . Monday_To_Hour       )
                    };

                    Initialization_Days_Check_Boxes
                    (
                        findViewById ( R . id . Monday_Work_Hours_Text_view ) ,
                                 findViewById ( element                              ) ,
                        spinners ,
                        "الاثنين" ,
                        "Select or deselect"
                    );
                    break ;
                }

                case "Tuesday_Check_Box":
                {

                    spinners = new Spinner [ ]
                    {
                        findViewById ( R . id . Tuesday_From_Am_Or_Pm ) ,
                        findViewById ( R . id . Tuesday_To_Am_Or_Pm   ) ,
                        findViewById ( R . id . Tuesday_From_Hour     ) ,
                        findViewById ( R . id . Tuesday_To_Hour       )
                    };

                    Initialization_Days_Check_Boxes
                    (
                        findViewById ( R . id . Tuesday_Work_Hours_Text_view ) ,
                                 findViewById ( element                               ) ,
                        spinners ,
                        "الثلاثاء" ,
                        "Select or deselect"
                    );
                    break ;
                }

                case "Wednesday_Check_Box":
                {

                    spinners = new Spinner [ ]
                    {
                        findViewById ( R . id . Wednesday_From_Am_Or_Pm ) ,
                        findViewById ( R . id . Wednesday_To_Am_Or_Pm   ) ,
                        findViewById ( R . id . Wednesday_From_Hour     ) ,
                        findViewById ( R . id . Wednesday_To_Hour       )
                    };

                    Initialization_Days_Check_Boxes
                    (
                        findViewById ( R . id . Wednesday_Work_Hours_Text_view ) ,
                                 findViewById ( element                                 ) ,
                        spinners ,
                        "الاربعاء" ,
                        "Select or deselect"
                    );
                    break ;
                }

                case "Thursday_Check_Box":
                {

                    spinners = new Spinner [ ]
                    {
                        findViewById ( R . id . Thursday_From_Am_Or_Pm ) ,
                        findViewById ( R . id . Thursday_To_Am_Or_Pm   ) ,
                        findViewById ( R . id . Thursday_From_Hour     ) ,
                        findViewById ( R . id . Thursday_To_Hour       )
                    };

                    Initialization_Days_Check_Boxes
                    (
                        findViewById ( R . id . Thursday_Work_Hours_Text_view ) ,
                                 findViewById ( element                                ) ,
                        spinners ,
                        "الخميس" ,
                        "Select or deselect"
                    );
                    break ;
                }

                case "Friday_Check_Box":
                {

                    spinners = new Spinner [ ]
                    {
                        findViewById ( R . id . Friday_From_Am_Or_Pm ) ,
                        findViewById ( R . id . Friday_To_Am_Or_Pm ) ,
                        findViewById ( R . id . Friday_From_Hour ) ,
                        findViewById ( R . id . Friday_To_Hour )
                    };

                    Initialization_Days_Check_Boxes
                    (
                        findViewById ( R . id . Friday_Work_Hours_Text_view ) ,
                                 findViewById ( element                              ) ,
                        spinners ,
                        "الجمعه" ,
                        "Select or deselect"
                    );
                    break ;
                }
            }
        }
    }

    // هاد الفنكشن مسؤول عن تجهيز ال check boxes و ال spinners في حالة تعديل اوقات العمل
    private void Initialization_Days_Check_Boxes_For_Edit ( )
    {
        /*
            هسه كونه انا مستدعي هاد الفنكشن في ال onCreate وكونه عنا حالتين بوصل الهم لهاي الشاشه ايام و ساعات العمل

            الاولى هي لما اجي بدي اضيف مكان عمل و بدي اضيف اله ايام و ساعات العمل

            والثانيه هي اذا خربطت و بدي اعدل شي في ايام و ساعات العمل

            يعني هاد الفنكشن اول ما ندخل الشاشه رح يتنفذ مباشره سواء دخلنا لنضيف ايام و ساعات العمل للاول مره او عشان نعدل

            بس انا ما بدي اياه يتنفذ الي داخل الفنكشن الا لما ادخل عشان اعدل لانه هاد الفنكشن وظيفته

            انه يعرض الي الخيارات و البيانات الي اخترتها و ادخلتها اول مره لما ضفت ايام و ساعات العمل

            يعني بدي الي داخل هاد الفنكشن يتنفذ في حالة دخلت الشاشه عشان اعدل على ايام و ساعات العمل الي ضفتها اول مره

            لهيك لازم اشيك على المسج الي اجى مع ال intent الي جابنا لهاي الشاشه

           وهاد الشي بصير داخل قوسين جملة ال if والي بصير انه بنشيك اذا المسج الي جاي النا مع ال intent الي جابنا لهاي الشاشه قيمته تساوي "Edit"

            ادخل و نفذ الي داخل جملة ال if والي هو الي بعمله هاد الفنكشن غير هيك لا تنفذ شي من الي داخل الفنكشن
        */
        if ( getIntent ( ) . getExtras ( ) . getString ( "Action" ) . equals ( "Edit" ) )
        {
            /*
                 هون في حالة كنا بدنا نعدل الي بصير كالاتي عنا هاي spinners ال array بتخزن فيها spinners ساعات العمل لليوم الي بدنا
                 نعدل اله ساعات العمل عشان نظهرهم عشان نعدل في قيمهم
            */
            Spinner [ ] spinners ;

            /*
                هاي ال for each بتلف على هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس الي فيها مجموعة الاوبجكت الخاصين بكل يوم اخترناه

                كل واحد من هدول الاوبجكت بخص يوم من الايام الي اخترناها وكل اوبجكت فيه اسم اليوم + من الساعه الفلانيه + صباحا

                او مساءً الخاص ب من الساعه الفلانيه + الى الساعه الفلانيه وبرضو صباحا + مساءً الخاص ب الى الساعه الفلانيه

               وهي تقريبا بتشتغل نفس شغل ال for each و ال switch الي في نكشن

               ال Initialization_Days_Check_Boxes_For_Add لكن هون الفرق

               انه ال case الي داخل ال switch ببتنفذ اذا كنا محددين اليوم بس يعني لو في واحد من ال case انا مش محدد اليوم الخاص فيه

               ما رح يتنفذ الي داخلها

               والي بتعمله هاي ال for each انها لما بدنا نعدل بتجي ال for each بتمسك هاي days_And_Working_Hours_Objects_List ال list

               وبتلف عل كل الاوبجكت الي فيها واحد واحد ليه بتعمل هيك لانه ايام و ساعات العمل الي اخترناهم عند الاضافه لاول مره اتخزنو في هاي ال List

               ولما بدنا نعدل بدنا يعرض النا الاختيارات الي احنا احترناها والي هي مخزنه في هاي ال List

               لهيك بلف على الاوبجكت الي في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس

               على عكس الي بصير في فنكشن ال Initialization_Days_Check_Boxes_For_Add

               انه بلف على كل ال check box تاعين الايام و بعالجهم حسب المطلوب
             */
            for ( Days_And_Working_Hours element : WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List )
            {

                /*
                    و هون بدل ما كنا في فنكشن ال Initialization_Days_Check_Boxes_For_Add
                    بنستعمل الاسم الخاص ب check box تبع اليوم

                    هون احنا بنستعمل القيمه الي مخزنه في متغير ال day الخاص بكل اوجبكت في في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس

                    يعني مثلا لو كان اول اوجبكت في هاي ال List مكتوب في ال day تبعه "السبت" وقتها رح يجي ينفذ ال case الي خاص

                    بيوم السبت وهيك لحد ما نلف على كل عناصر ال List
                 */
                switch ( element . day )
                {

                    case "السبت":
                    {
                        /*
                           مثل ما انتي شايقه الوضع في ال cases ال 7 نفس الوضع في ال cases ال 7 في فنكشن ال

                           ؛Initialization_Days_Check_Boxes_For_Add لكن الي بتخلف هو شي واحد

                           بس و الي هو المتغير action هون معطينه قيمه والي هي "Edit" بينما في فنكشن ال

                           Initialization_Days_Check_Boxes_For_Add؛

                           اعطيناه قيمه و الي هي "Select or deselect"

                            هسه هون بدي اشرح الك شو لازمة المتغير الي اسمه action هسه ي منار فنكشن ال

                            Initialization_Days_Check_Boxes_For_Add

                            وظيفته انه بجهز النا ال check boxes ال 7 الخاصين بالايام

                            بحيث لو حددنا واحد من الايام يضيف النا اوبجكت خاص فيه في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس

                            ويظهر النا spinners ساعات العمل الخاصين فيه وفي حالة شلنا التحديد عنه

                            يمسح الاوبجكت الخاص فيه من هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس

                            ويخفي النا spinners ساعات الخاصين باليوم الي حددناه

                            وعشان فنكشن ال Initialization_Days_Check_Boxes_For_Add

                             شغلته او لازمته متعلقه في تحديد و الغاء تحديد اليوم

                             اعطيت المتغير action هاي القيمه "Select or deselect" عشان افهم فنكشن ال Initialization_Days_Check_Boxes

                             انه بدي  تجهز الي حدث تحديد او الغاء تحديد اليوم لكل ال check boxes تاعين الايام




                            اما هاد الفنكشن وظيفته هي انه بس نجي نعدل يجي على هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس و يلف على الاوبجكت

                            الي داخلها ويحدد اليوم الي بخص كل اوبجكت و يظهر النا spinners ساعات الخاصين باليوم الي حددناه مع القيم الي اخترناها عشان نقدر نعدل فيها

                            لهيك اعطيت المتغير action هاي القيمه "Edit" في هاد الفنكشن
                         */

                        spinners = new Spinner [ ]
                        {
                            findViewById ( R . id . Saturday_From_Am_Or_Pm ) ,
                            findViewById ( R . id . Saturday_To_Am_Or_Pm   ) ,
                            findViewById ( R . id . Saturday_From_Hour     ) ,
                            findViewById ( R . id . Saturday_To_Hour       )
                        };

                        Initialization_Days_Check_Boxes
                        (
                            findViewById ( R . id . Saturday_Work_Hours_Text_view ) ,
                                     findViewById ( R . id . Saturday_Check_Box            ) ,
                            spinners ,
                            "السبت" ,
                            "Edit"
                        );
                        break ;
                    }

                    case "الاحد":
                    {
                        spinners = new Spinner [ ]
                        {
                            findViewById ( R . id . Sunday_From_Am_Or_Pm ) ,
                            findViewById ( R . id . Sunday_To_Am_Or_Pm   ) ,
                            findViewById ( R . id . Sunday_From_Hour     ) ,
                            findViewById ( R . id . Sunday_To_Hour       )
                        };

                        Initialization_Days_Check_Boxes
                        (
                            findViewById ( R . id . Sunday_Work_Hours_Text_view ) ,
                                      findViewById ( R . id . Sunday_Check_Box           ) ,
                            spinners ,
                            "الاحد" ,
                            "Edit"
                        );
                        break ;
                    }

                    case "الاثنين":
                    {
                        spinners = new Spinner [ ]
                        {
                            findViewById ( R . id . Monday_From_Am_Or_Pm ) ,
                            findViewById ( R . id . Monday_To_Am_Or_Pm   ) ,
                            findViewById ( R . id . Monday_From_Hour     ) ,
                            findViewById ( R . id . Monday_To_Hour       )
                        };

                        Initialization_Days_Check_Boxes
                        (
                            findViewById ( R . id . Monday_Work_Hours_Text_view ) ,
                                      findViewById ( R . id . Monday_Check_Box           ) ,
                            spinners ,
                            "الاثنين" ,
                            "Edit"
                        );
                        break ;
                    }

                    case "الثلاثاء":
                    {
                        spinners = new Spinner [ ]
                        {
                            findViewById ( R . id . Tuesday_From_Am_Or_Pm ) ,
                            findViewById ( R . id . Tuesday_To_Am_Or_Pm   ) ,
                            findViewById ( R . id . Tuesday_From_Hour     ) ,
                            findViewById ( R . id . Tuesday_To_Hour       )
                        };

                        Initialization_Days_Check_Boxes
                        (
                            findViewById ( R . id . Tuesday_Work_Hours_Text_view ) ,
                                     findViewById ( R . id .  Tuesday_Check_Box           ) ,
                            spinners ,
                            "الثلاثاء" ,
                            "Edit"
                        );
                        break ;
                    }

                    case "الاربعاء":
                    {
                        spinners = new Spinner [ ]
                        {
                            findViewById ( R . id . Wednesday_From_Am_Or_Pm ) ,
                            findViewById ( R . id . Wednesday_To_Am_Or_Pm   ) ,
                            findViewById ( R . id . Wednesday_From_Hour     ) ,
                            findViewById ( R . id . Wednesday_To_Hour       )
                        };

                        Initialization_Days_Check_Boxes
                        (
                            findViewById ( R . id . Wednesday_Work_Hours_Text_view ) ,
                                     findViewById ( R . id . Wednesday_Check_Box            ) ,
                            spinners ,
                            "الاربعاء" ,
                            "Edit"
                        );
                        break ;
                    }

                    case "الخميس":
                    {
                        spinners = new Spinner [ ]
                        {
                            findViewById ( R . id . Thursday_From_Am_Or_Pm ) ,
                            findViewById ( R . id . Thursday_To_Am_Or_Pm   ) ,
                            findViewById ( R . id . Thursday_From_Hour     ) ,
                            findViewById ( R . id . Thursday_To_Hour       )
                        };

                        Initialization_Days_Check_Boxes
                        (
                            findViewById ( R . id . Thursday_Work_Hours_Text_view ) ,
                                     findViewById ( R . id . Thursday_Check_Box            ) ,
                            spinners ,
                            "الخميس" ,
                            "Edit"
                        );
                        break ;
                    }

                    case "الجمعه":
                    {
                        spinners = new Spinner [ ]
                        {
                            findViewById ( R . id . Friday_From_Am_Or_Pm ) ,
                            findViewById ( R . id . Friday_To_Am_Or_Pm   ) ,
                            findViewById ( R . id . Friday_From_Hour     ) ,
                            findViewById ( R . id . Friday_To_Hour       )
                        };

                        Initialization_Days_Check_Boxes
                        (
                            findViewById ( R . id . Friday_Work_Hours_Text_view ) ,
                                     findViewById ( R . id . Friday_Check_Box            ) ,
                            spinners ,
                            "الجمعه" ,
                            "Edit"
                        );
                        break ;
                    }
                }
            }
        }
    }

    // هاد الفنكشن الي بشتغل كل الحكي الي قلته في الفنكشنين الي فوق وحاطه مش قصة زيادة او شي تاني حاطه عشان اخفف من الكود
    private void Initialization_Days_Check_Boxes ( TextView work_Hours_Text_View , CheckBox checkBox , Spinner [ ] spinners , String day , String action )
    {

        /*
            طيب لو تطلعي على المتغيرات الي بستقبلها الفنكشن بتلاقي انها نفس اسماء المتغيرات الي انا باعتها لهاد الفنكشن داخل ال switch في

            الفنكشنين الي فوق والوحيد الي بدي احكي عنه و بهمني اكتر شي هو متغير ال action الي بحكي لهاد الفنكشن شو يعمل بالزبط

            هلا هاد المتغير في فنكشن ال Initialization_Days_Check_Boxes_For_Add
            بنعطيه هاي القيمة "Select or deselect"

            اما في الفنكشن الثاني الي هو هاد Initialization_Days_Check_Boxes_For_Edit
            بنعطيه هاي القيمة "Edit"

            هسه الي داخل جملة الاف هاد بتنفذ في حالة تم استدعاء هاد الفنكشن الي احنا فيه بواسطة هاد الفنشكن

            Initialization_Days_Check_Boxes_For_Add

           وفي هاي الحاله بتكون قيمة ال action تساوي "Select or deselect" و وقتها الفنكشن بشتغل عشان يجهز النا محتويات الشاشه لاستقبال قيم جديده


            والي داخل ال else بتنفذ في حالة تم استدعاء الفنكشن الي احنا فيه هلا بواسطة هاد الفنكشن

            Initialization_Days_Check_Boxes_For_Edit

            وفي هاي الحاله بتكون قيمة ال action تساوي "Edit" و وقتها الفنكشن هاد بشتغل عشان يعرض النا شو القيم الي اخترناها عند الاضافه لاول مره عشان نعدل قيميتها
         */
        if ( action . equals ( "Select or deselect" ) )
        {
            /*
                طيب هسه هون في حالة تم استدعاء هاد الفنكشن بواسطه هاد

                Initialization_Days_Check_Boxes_For_Add الفنكشن

                بتنفذ الي تحت

                والي بصير في هاي الحاله انه بجهز النا حدث تحديد و الغاء تحديد كل ال check boxes

                بحيث انه ي منار مثلا

                انا بس اجي اختار ال check box تبع يوم السبت في هاي الجزئيه بالذات بيستدعي حدث تحديد ال check box الخاص بها اليوم و

                يضيف اوبجكت لهاد اليوم الي اخترناه في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس

                وكمان بظهر النا spinners ساعات العمل الخاصين فيه

                اما في حالة تم الغاء تحديد يوم معين وقتها بيستدعى حدث الغاء تحديد اليوم الخاص ب check box اليوم الي تم الغاء تحديده

                وبحذف النا اوبجكت اليوم الي تم الغاء تحديده من هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس وبخفي spinners ساعات العمل الخاصين فيه
             */

            checkBox . setOnCheckedChangeListener ( ( buttonView , isChecked ) ->
            {
                /*
                    هون في حالة تم اختيار اي واحد من ال check boxes استدعي حدث تحديد هاد ال check box  و نفذ الي داخل الاف غير هيك استدعي حدث الغاء التحديد و نفذ الي داخل ال else
                 */
                if ( checkBox . isChecked ( ) )
                {
                    /*
                        الي بصير هون ي منار هو انه

                        1- بعطي المتغيرات الي من خلالها بشيك انه حط ساعات العمل بشكل صح قيمة تساوي true ك دليل على انه الدكتور حدد واحد من الايام بس لسه ما حدد ساعات العمل تبعته صح

                        2- بجي على هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس بحط فيها اوبجكت لليوم الي تم اختياره و بعطي متغير ال day
                        لهاد الاوبجكت قيمة متغير ال day الي مبعوث لهاد الفنكشن وبحط قيمة الصباحا او مساء الخاصه ب من الساعه الفلانيه و ب
                        الى الساعه الفلانيه تساوي "ص" يعني صباحا و خليتها تساوي "ص" عشان هي القيمه الي بتكون معروضه في هدول ال
                        ؛spinner من دون دون ما نضغط على واحد منهم

                        3- بظهر ال text view الي بكون مكتوب فيه ساعات العمل الي بين ال check box تبع اليوم و بين spinners ساعات
                        العمل تبعين اليوم

                        4- بظهر النا spinners تاعين ساعات العمل الخاصين باليوم الي اخترناه
                     */

                    Days_And_Working_Hours . is_From_Hour_Empty = true;

                    Days_And_Working_Hours . is_To_Hour_Empty = true ;

                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . add ( new Days_And_Working_Hours ( day , "" , "ص" , "" , "ص" ) ) ;

                    //هون بظهر ال text view الي بكون مكتوب فيه ساعات العمل الي بين ال check box تبع اليوم و بين spinners ساعات العمل تبعين اليوم
                    work_Hours_Text_View . setVisibility ( View . VISIBLE ) ;

                    //  هون بخفي spinners ساعات العمل الخاصين باليوم الي تم تحديده وفي كل لفه ال element بكون عباره عن ال spinner اي بدنا نظهره
                    for ( Spinner element : spinners )
                    {
                        element . setVisibility ( View . VISIBLE ) ;
                    }
                }
                else
                {

                    /*
                      هون بصير عكس الي بصير داخل الاف و هو انه اول بعطي المتغيرات الي من خلالها بشيك انه حط ساعات العمل

                      بشكل صح قيمة تساوي false غشان ااكد انه اليوم الي حدده الدكتور شال عنه التحديد و خلص ما بلزمه ثاني شي

                      بحذف الاوبجكت الخاص باليوم الي تم الغاء تحديده من هاي days_And_Working_Hours_Objects_List ال List

                      بعدها بخفي ال spinners ساعات العمل و ال text view الي بكون مكتوب فيه ساعات العمل الي بين ال check box

                      تبع اليوم و بين spinners ساعات العمل تبعين اليوم الخاصين باليوم الي عملنا الغاء تحديد اله
                    */

                    Days_And_Working_Hours . is_From_Hour_Empty = false;

                    Days_And_Working_Hours . is_To_Hour_Empty = false ;

                    // هون بنستدعي الفنكشن الي بجيب النا ال index الخاص ب اوبجكت اليوم الي بدنا نحذفه من ال List وهاد الفنكشن انا عامله في كلاس ال Days_And_Working_Hours
                    Days_And_Working_Hours . Index_Of ( day ) ;

                    /*
                        بعدها بعطي امر بحذف هاد الاوبجكت وبين قوسين فنكشن ال remove بعطيه index الاوبجكت الي بدي احذفه و برضو

                        هاد المتغير الي هو index موجود في كلاس ال Days_And_Working_Hours ولما نستدعي فنكشن ال Index_Of

                        الي بعمله الفنكشن هو انه بخزن النا index الخاص ب اوبجكت اليوم الي بدنا نحذفه من ال List في هاد المتغير الي اسمه index
                     */
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . remove ( Days_And_Working_Hours.index ) ;

                    //هون بخفي ال text view الي بكون مكتوب فيه ساعات العمل الي بين ال check box تبع اليوم و بين spinners ساعات العمل تبعين اليوم
                    work_Hours_Text_View . setVisibility ( View . GONE ) ;

                    // هون بخفي spinners ساعات العمل الخاصين باليوم الي تم الغاء تحديده
                    for ( Spinner element : spinners )
                    {
                        element . setVisibility ( View . GONE ) ;
                    }
                }
            });
        }
        else
        {
            /*
                طيب هسه لو تم استدعاء هاد الفنكشن بواسطه هاد Initialization_Days_Check_Boxes_For_Edit الفنشكن

                رح يتفذ الي داخل ال else و الي بصير داخل ال else هو انه اول شي

                بعمل تحديد لل check box تبع اليوم الي اله اوبجكت في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس

                ال check box تبع اليوم و بين spinners ساعات العمل تبعين اليوم الخاصين باليوم الي موجود اله اوبجكت في هاي

                ؛days_And_Working_Hours_Objects_List ال List
             */

            // هون بيختار ال check box تبع اليوم الي بدنا نعدل ساعات العمل الخاصين فيه
            checkBox . setChecked ( true ) ;

            work_Hours_Text_View . setVisibility ( View . VISIBLE ) ;

            /*
               هاد حاطه عشان لما بدي اوصل لهاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس ما اضل
                اقله WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List
            */
            WorkPlace_Data workPlace_data_Object = WorkPlace_Data . workPlace_Data_Object ;

            for ( Spinner element : spinners )
            {
                /*
                    هسه هاي ال for each الي بتعمله انه بتلف على ال spinners الي في هاي spinners ال array الي بعثناها لهاد الفنكشن

                    و الي بصير فيها او الي بتعمله هو انه ي منار اول شي بتحدد index الاوبجكت الخاص باليوم الي بدنا نعدل ساعات العمل اله

                    بعدها بظهر النا spinners ساعات العمل تبعين اليوم الي اله اوبجكت في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس

                     بعدها في عنا 4 جمل اف

                    الاول بتشتغل اذا كانت ال for each ماشره على spinner الخاص ب من الساعه الفلانيه

                    الثانيه بتشتغل اذا كانت ال for each ماشره على spinner الخاص ب صباحا او مساء الخاص ب من الساعه الفلانيه

                    الثالثه بتشتغل اذا كانت ال for each ماشره على spinner الخاص ب الى الساعه الفلانيه

                    الرابعه بتشتغل اذا كانت ال for each ماشره على spinner الخاص ب صباحا او مساء الخاص ب الى الساعه الفلانيه
                */
                Days_And_Working_Hours . Index_Of ( day ) ;

                element . setVisibility ( View . VISIBLE ) ;

                if ( getResources ( ) . getResourceEntryName ( element . getId ( ) ) . contains ( "From_Hour" ) )
                {

                    // هاد السطر بخلي ال spinner تبع من الساعه الفلانيه ياشر على الاختيار الي انا اخترته لما ضفت ساعات العمل
                    element . setSelection
                    (
                        Integer . parseInt
                        (
                            workPlace_data_Object . days_And_Working_Hours_Objects_List
                            . get ( Days_And_Working_Hours . index ) . from_Hour
                        )
                    );
                }

                if ( getResources ( ) . getResourceEntryName ( element . getId ( ) ) . contains ( "From_Am_Or_Pm" ) )
                {
                    /*
                        هسه كونه صباحا او مساء ما باخد غير قيمتين ف الي بعمله هون هو اني بشيك اذا كان قيمة صباحا او مساء

                        الخاصه ب من الساعه الفلانيه تساوي "ص" وقتها خلي ال spinner تبع صباحا او مساء الخاص ب من

                        الساعه الفلانيه يعرض الي ص اذا كانت القيمه ما بتساوي "ص" اعرض الي في ال spinner م
                     */
                    if
                    (
                        workPlace_data_Object . days_And_Working_Hours_Objects_List . get ( Days_And_Working_Hours . index )
                        . from_AM_Or_PM . equals ( "ص" )
                    )
                         element . setSelection ( 0 ) ;
                    else
                        element . setSelection ( 1 ) ;
                }

                if ( getResources ( ) . getResourceEntryName ( element . getId ( ) ) . contains ( "To_Hour" ) )
                {
                    // هاد السطر بخلي ال spinner تبع الى الساعه الفلانيه ياشر على الاختيار الي انا اخترته لما ضفت ساعات العمل
                    element . setSelection
                    (
                        Integer . parseInt
                        (
                            workPlace_data_Object . days_And_Working_Hours_Objects_List
                            . get ( Days_And_Working_Hours . index ) . to_Hour
                        )
                    );
                }

                if ( getResources ( ) . getResourceEntryName ( element . getId ( ) ) . contains ( "To_Am_Or_Pm" ) )
                {

                    // هون نفس الي بصير في حالة كانت ال for each ماشره على spinner الصباحا او مساء الخاص ب من الساعه الفلانيه
                    if
                    (
                        workPlace_data_Object . days_And_Working_Hours_Objects_List . get ( Days_And_Working_Hours . index )
                        . to_AM_Or_PM . equals ( "ص" )
                    )
                     element . setSelection ( 0 ) ;
                    else
                     element . setSelection ( 1 ) ;
                }
            }
        }
    }


    private void Snack_Bar ( String Message )
    {

        Snackbar snackbar = Snackbar . make ( binding . Constraint , Message , 7000 ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList. valueOf ( ContextCompat. getColor ( this , R . color . Snack_bar_BG_Color ) ) ) ;

        View snackbarView = snackbar . getView ( ) ;

        TextView textView = snackbarView . findViewById ( com . google . android . material . R . id . snackbar_text ) ;

        textView . setSingleLine ( false ) ;

        textView . setTextColor ( ContextCompat . getColor ( this , R . color . white ) ) ;

        textView . setTextSize ( 15 ) ;

        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        ViewGroup . MarginLayoutParams marginLayoutParams = ( ViewGroup .MarginLayoutParams ) snackbarView . getLayoutParams ( ) ;

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

    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو الازرار في هاي الشاشه ---------------

    // هاد بتفذ لما نضغط على زر الغاء تحديد الكل
    public void DeSelect_All_Days_BTN ( View view )
    {
        /*
            هون ال for each بتلف على ال id الخاصين بال check boxes الخاصين بالايام وبعمل الغاء تحديد لاي

            يوم تم اختياره و يخفي ال spinners الخاصين فيه ويجهزهم لاستقبال قيمه جديده غير القيمه الي كانت فيهم و

            في كل لفه ال element بتخزن فيه id الي check box الي ال for each ماشره على ال id تبعه
        */
        for ( int element : days_Check_Boxes_Ids )
        {
            /*
                هون معرفين متغير من نوع check box و في كل لفه

                بنخليه يصير نسخه عن ال check box الي ال for each ماشر على ال id تبعه
             */
            CheckBox checkBox = findViewById ( element ) ;
            /*
                هون بعد ما صار عنا نسخه من ال check box الي ال for each ماشره على ال id الخاص فيه

                بنعمل اله الغاء تحديد من خلال هاد السطر الي تحت
             */
            checkBox . setChecked ( false ) ;
        }

        // هسه لما نعمل الغاء تحديد للكل مش من المنطقي انه الاوبجكت الخاصين بالايام الي شلنا التحديد عنها تضل في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوبجكت الي في هاي WorkPlace_Data الكلاس
        WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . clear ( ) ;
    }

    // هاد الفنكشن بتنفذ لما نضغط على زر تحديد الكل
    public void SelectAllDaysBTN ( View view )
    {
        // هون نفس الي بصير لما نكبس على زر الغاء تحديد الكل لكن الفرق انه بحدد الكل
        for ( int element : days_Check_Boxes_Ids )
        {
            CheckBox checkBox = findViewById ( element ) ;
            checkBox . setChecked ( true ) ;
        }

    }

    // هاد الفنكشن بيستدعى لما نضغط على زر الحفظ الخاص بحفظ ايام و ساعات العمل
    public void Save_Days_And_Working_Hours_BTN ( View view )
    {

        // هاي ال for each وظيفتها انه تلف على كل الاوجكت الي في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوجكت الي في هاي WorkPlace_Data الكلاس و تشيك انه ساعات العمل لكل الايام الي معمول الها اوجكت في ال list محطوطه صح
        for ( Days_And_Working_Hours element : WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List )
        {
            // في كل لفه ال element بتكون قيمته تساوي الاوبجكت الي في هاي days_And_Working_Hours_Objects_List ال list الخاصه بهاد workPlace_Data_Object الاوجكت الي في هاي WorkPlace_Data الكلاس والي ال for each ماشره على ال index تبعه
            Days_And_Working_Hours . is_From_Hour_Empty = element . from_Hour . isEmpty ( ) ;
            Days_And_Working_Hours . is_To_Hour_Empty   = element . to_Hour   . isEmpty ( ) ;
        }

        // هاد  الشرط عشان نتاكد انه اختار اوقات العمل ليوم واحد على الاقل و عبى كل البيانات اللازمه اله غير هيك رح يظهر اله المسج الي في ال else
        if
        (
            /*
                والشرط بقول انه اذا
                ما كانت هاي days_And_Working_Hours_Objects_List ال List الخاصه بهاد workPlace_Data_Object الاوجكت الي في هاي WorkPlace_Data الكلاس فاضيه
                و ما كانت قيمة هاد is_From_Hour_Empty المتغير true
                و ما كانت قيمة هاد is_To_Hour_Empty المتغير true

                ادخل و نفذ الي داخل جملة الاف

                واذا وحده منهم ما كانت مثل ما انا حاكي نفذ الي داخل ال else
             */
            ! WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . isEmpty ( ) &&
            ! Days_And_Working_Hours . is_From_Hour_Empty &&
            ! Days_And_Working_Hours . is_To_Hour_Empty
        )
        {
            // هدول ال 3 اسطر هم نفسهم الي موجودين في فنكشن ال Save_Workplace_Data_BTN في كلاس ال Workplace_Data_Activity
            Intent intent = getIntent ( ) ;
            setResult ( RESULT_OK , intent ) ;
            finish ( ) ;
        }
        else
        {
            /*
                هسه هون داخل قوسين فنكشن ال Snack_Bar

                بشيك على المسج الي جاي مع ال intent الي جابنا لهاي الشاشه اذا كان دخل

                هاي الشاشه عشان يضيف ايام و ساعات العمل لاول مره رح تكون قيمة المسج

                تساوي "Add" ورح يطبع اله اول مسج

                غير هيك رح تكون قيمة المسج تساوي "Edit" و رح يطبع اله الجمله الثانيه
             */
            Snack_Bar
            (
                getIntent ( ) . getExtras ( ) . getString ( "Action" ) . equals ( "Add" ) ?
                "يرجى تحديد اوقات العمل في يوم واحد على الاقل وتحديد اوقات العمل بشكل صحيح" :
                "يرجى تعديل اوقات العمل بشكل صحيح"
            );
        }
    }


    // هاد الفنشكن بتنفذ لما نضغط على زر الرجوع الي موجود في الشاشه من تحت الي هو تاع النظام
    @Override
    public void onBackPressed ( )
    {

        // هون بصير نفس الي بصير داخل ال else الي في فنكشن ال Save_Days_And_Working_Hours_BTN

        Snack_Bar
        (
            getIntent ( ) . getExtras ( ) . getString ( "Action" ) . equals ( "Add" ) ?

            "لايمكنك الرجوع للخلف\n\n" +
            "يجب تحديد اوقات العمل في يوم واحد على الاقل وتحديد اوقات العمل بشكل صحيح ثم الضغط على زر الحفظ"
            :
            "لايمكنك الرجوع للخلف\n\n اذا كنت لم تقم باي تعديل يمكنك الضغط على زر الحفظ للرجوع الى الشاشة السابقه \n\n" +
            "لكن اذا قمت باي تعديل يجيب ان تقوم بتعديل اوقات العمل بالشكل الصحيح ثم قم بالضغط على زر الحفظ"
        );
    }

    // --------------- نهاية الجزء الي فيه الفنكشن الي بخصو الازرار في هاي الشاشه ---------------


    /* */



    // --------------- بداية الجزء الي فيه الفنكشن الي بخصو ال Spinners تبعين ساعات العمل ---------------

    /**/

    // هدول ال 3 فنكشن هم نفسهم الي في كلاس ال Workplace_Data_Activity لكن في فرق كبير بينهم و بين الي هون ادخلي و وشوفي شو الفرق بس يجي دورهم لتشوفي شو جواتهم

    /**/

    // هاد الفنكشن وظيفته انه يجهز الي spinners ساعات العمل كلهم مع كل الاحداث الي بتخصهم
    private void Adapters_Initialization ( )
    {
        // هاد الاوبجكت الي بخزن فيه ال spinner الي بدي اجهزه
        Spinner spinner  ;

        // هاد المتغير الي بنحط فيه العناصر الي رح تنعرض الي لما اضعط على واحد من ال spinners
        ArrayAdapter < CharSequence > adapter = null;

        // هاي ال array فيها كل ال id's تبعين ساعات العمل والي ال for each رح تلف عليهم كلهم عشان تجهزهم
        int [ ] sp_ID =
        {
            R . id . Saturday_From_Hour  , R . id . Saturday_From_Am_Or_Pm  , R . id . Saturday_To_Hour  , R . id . Saturday_To_Am_Or_Pm  ,

            R . id . Sunday_From_Hour    , R . id . Sunday_From_Am_Or_Pm    , R . id . Sunday_To_Hour    , R . id . Sunday_To_Am_Or_Pm    ,

            R . id . Monday_From_Hour    , R . id . Monday_From_Am_Or_Pm    , R . id . Monday_To_Hour    , R . id . Monday_To_Am_Or_Pm    ,

            R . id . Tuesday_From_Hour   , R . id . Tuesday_From_Am_Or_Pm   , R . id . Tuesday_To_Hour   , R . id . Tuesday_To_Am_Or_Pm   ,

            R . id . Wednesday_From_Hour , R . id . Wednesday_From_Am_Or_Pm , R . id . Wednesday_To_Hour , R . id . Wednesday_To_Am_Or_Pm ,

            R . id . Thursday_From_Hour  , R . id . Thursday_From_Am_Or_Pm  , R . id . Thursday_To_Hour  , R . id . Thursday_To_Am_Or_Pm  ,

            R . id . Friday_From_Hour    , R . id . Friday_From_Am_Or_Pm    , R . id . Friday_To_Hour    , R . id . Friday_To_Am_Or_Pm
        };

        // في هاي ال for each في كل لفه ال element بتكون قيمةه تساوي ال id تبع ال spinner الي for each ماشره عليه
        for ( int element : sp_ID )
        {
            // هسه اول شي بصير داخل ال for each انه بنخزن ال spinner الي ال for each ماشره على ال id تبعه في المتغير الي اسمه spinner
            spinner = findViewById ( element ) ;

            /*
                بعدها عنا 3 جمل if

                الاولى بتتنفذ اذا ال spinner الي ال for each ماشره على ال id تبعه هو spinner بخص من الساعه الفلانيه و مش مهم لاي يوم تابع
                الثانيه بتتنفذ اذا ال spinner الي ال for each ماشره على ال id تبعه هو spinner بخص الى الساعه الفلانيه و مش مهم لاي يوم تابع
                الثالثه بتتنفذ اذا ال spinner الي ال for each ماشره على ال id تبعه هو spinner بخص صباحا او مساء و مش مهم لاي يوم تابع
             */
            if ( getResources ( ) . getResourceEntryName ( element ) . contains ( "From_Hour" ) )
            {

                // هون احنا بنحدد العناصر الي رح تنعرض النا لما نضغط على اي Spinner بخص من الساعه الفلانيه ومش مهم تابع لاي يوم

                adapter = ArrayAdapter . createFromResource
                (
                    this ,
                    R . array . work_Hours_From ,
                    R . layout . spinner_drop_down_items_text
                ) ;
            }

            if ( getResources ( ) . getResourceEntryName ( element ) . contains ( "To_Hour"   ) )
            {
                // هون احنا بنحدد العناصر الي رح تنعرض النا لما نضغط على اي Spinner بخص الى الساعه الفلانيه ومش مهم تابع لاي يوم
                adapter = ArrayAdapter . createFromResource
                (
                    this ,
                    R . array . work_Hours_To ,
                    R . layout . spinner_drop_down_items_text
                ) ;
            }

            if ( getResources ( ) . getResourceEntryName ( element ) . contains ( "Am_Or_Pm"  ) )
            {
                // هون احنا بنحدد العناصر الي رح تنعرض النا لما نضغط على اي Spinner بخص صباحا او مساء ومش مهم اذا كان تبع من الساعه الفلانيه او تبع الى الساعه الفلانيه ومش مهم تابع لاي يوم
                adapter = ArrayAdapter . createFromResource
                (
                    this ,
                    R . array . AM_Or_PM ,
                    R . layout . spinner_drop_down_items_text
                ) ;
            }

            // اذا في اسطر ما في شرح قبلها معناتها انا بكون شارحها في فنكشن ال Adapter_Initialization في كلاس ال Workplace_Data_Activity


            // فيي هاد السطر بعطيه شكل النص تبع القائمه الي رح تظهر النا لما نضغط على واحد من ال spinners بغض النظر شو هو ال spinner
            adapter . setDropDownViewResource ( R . layout . spinner_drop_down_items_text ) ;

            spinner . setAdapter ( adapter ) ;

            spinner . setOnItemSelectedListener ( this ) ;

            // في هاد السطر احنا بنجهز حدث لمس اي واحد من ال spinners
            spinner . setOnTouchListener ( ( View v ,  MotionEvent event ) ->
            {
                /*
                    الي بصير داخل هاد الفنكشن انه ي منار اول شي

                    شفتي هاد spinner_Name المتغير هاد هو نفسه الي عرفناه قبل ال onCreate هاد هو نفسه

                    في حالة لمس واحد من ال spinners شو ما كان و لمين ما كان تابع او بخص مين رح يتخزن اسمه في هاد المتغير

                    في فنكشن ال onItemSelected رح اقلك ليه عامل هاي الحركه و شو بدي فيها

                    بعدها عنا متغير اسمه flag من نوع boolean بنخلي قيمته تساوي true

                    واخر سطر هاد مش مهم و ما بعرف شو بعمل لكنه بخص الفنكشن نفسه
                 */
                spinner_Name = getResources ( ) . getResourceEntryName ( element ) ;
                flag = true ;
                return false ;
            });

            /*
                هون بنعرف كمان متغير من نوع Spinner و بنخليه يساوي المتغير الي من نوع Spinner الي عرفناه اول شي

                وعملت هيك لانه اخو المحترمه ما قبل يستعمل المتغير الي عرفناه قبل الا بده متغير لحاله خاص فيه ليه ما بعرف
             */
            Spinner finalSpinner = spinner ;

            // هاد الفنكنشن و الي جواته بتفذ لما يختفي واحد من ال spinners
            ViewTreeObserver . OnGlobalLayoutListener layoutListener = ( ) ->
            {
                // هون بين قوسين الاف بنشيك انه هل ال spinner الي ال for each ماشره على ال id تبعه مخفي ولا ظاهر
                if ( finalSpinner . getVisibility ( ) == View . GONE )
                {
                    // اذا كان ظاهر و بعدها اختفى مباشره بس يختفي تعال و امسح الي اي اختيار تم من خلال هاد ال spinner
                    finalSpinner . setSelection ( 0 ) ;
                }
            };

            spinner . getViewTreeObserver ( ) . addOnGlobalLayoutListener ( layoutListener ) ;
        }
    }

    // هاد بخص ال spinners الي فيه اماكن العمل بتنفذ او بتسدعى لما نختار اي شغله من القائمه الي فيه
    @Override
    public void onItemSelected ( AdapterView < ? > parent , View view , int position , long id )
    {

        /*
          طيب هسه هون بدي اشرح الك شو لازمة هاد spinner_Name المتغير

          هاد المتغير ي منار سبق و قلنا في هاد Adapters_Initialization الفنكشن وتحديدا في هاي الجزئيه

          spinner . setOnTouchListener ( ( View v ,  MotionEvent event ) ->
          {
             spinner_Name = getResources ( ) . getResourceEntryName ( element ) ;
             flag = true ;
             return false ;
           });

           انه بس نضغط على واحد من ال spinners بتخزن فيه اسم ال spinner الي ضغطنا عليه

           وهاد بصير ي منار عشان انا هون في هاد الفنكشن ما بعرف اي spinner الي ضغطت عليه

           ولازم اعرف اي spinner الي ضغطت عليه فكان هاد الحل

           وبحتاج اسم ال spinner الي تم الضغط عليه لانه ي منار

           قلنا قبل انه كل يوم بتم اختياره  بنعمل اله اوبجكت في هاي days_And_Working_Hours_Objects_List ال List

           وقلنا كل اوبجكت في هاي ال List في اله 5 متغيرات والي هن

           اليوم + من الساعه الفلانيه + صباحا او مساء تبع الساعه الفلانيه + الى الساعه الفلانيه + صباحا او مساء تبع الى الساعه الفلانيه

           ودام هيك انا لازم اعرف ال spinner الي تم الضغط عليه بخص اي يوم عشان اعرف ساعات العمل الي ضفتها

           تابعه لاي يوم عشان اضيفها مع الاوبجكت الخاص بهاد اليوم

           واذا ما عرفت اسم ال spinner الي تم الضغط عليه ما رح اعرف في اي اوبجكت و في اي متغير في الاوبجكت اضيف القيمه الي رح اخدها من هاد ال spinner و لاي يوم تابعه

          غير هيك من دون هاد المتغير ما رح يكون في عندي متغير day حتى الي موجود تحت والي لازمته انه هو الي يحدد الي ال spinner الي
           ضغطت عليه بخص اي يوم عشان انفذ case أليوم الي بخصه من داخل ال switch
         */

        /*

            هسه هون عنا متغير اسمه day هاد المتغير بخزن فيه اسم اليوم الي تابع اله ال spinner وبجيب اسم اليوم

            الخاص فيه من خلال اني اجي على ال spinner_Name و اخد اول جزء منه والي هو اسم اليوم الخاص فيه

            بعدها عنا متغير ال flag هاد المتغير انا مستعمله ي منار عشان امنع حدوث خطا محدد

            والخطا الي بصير هو انه لما احدد واحد من الايام و اختار قيم ساعات العمل واخلص بعدها اجي واختار يوم ثاني

            مباشره بجي على اوجبكت اليوم الي اخترته قبل و بروح على قيمة spinner تبع صباحا او مساء الخاص ب الى الساعه الفلانيه

            و بعدل قيمته على مزاجه فكان الحل هو انه اعمل هاد المتغير و اعطيه قيمه ابتدائي تكون false


            وقيمته بتصير صح ي منار لما اضغط على اي واحد من ال spinner

            وهاد الحكي بصير ( انه قيمته بتصير صح ) في فنكشن ال Adapters_Initialization وتحديدا في هاي الجزئيه

            spinner . setOnTouchListener ( ( View v ,  MotionEvent event ) ->
            {
               spinner_Name = getResources ( ) . getResourceEntryName ( element ) ;
               flag = true ;
               return false ;
             });

             لما قلت اله flag = true
         */

        String day = ! spinner_Name . isEmpty ( ) ? spinner_Name . substring ( 0 , spinner_Name . indexOf ( "_" ) ) : "" ;

        if ( flag )
        {
            // طيب هسه عنا هون ال Switch داخله 7 cases كل واحد منهم بخص واحد من الايام
            switch ( day )
            {
                // هسه اذا كانت قيمة ال day تساوي "Saturday" رح ينفذ اول case واسم ال spinner هو الي بحدد النا اي case رح يتنفذ لانه احنا بناخذ قيمة ال day من اسم ال spinner و اسم ال spinner بتخزن فيه اسم ال spinner الي بنضغط عليه

                case "Saturday" :
                {
                    // هون اول شي بنحدد ال index الخاص باليوم الي بدنا نضيف اله ساعات العمل
                    Days_And_Working_Hours . Index_Of ( "السبت" ) ;

                    /*
                        بعدها بنستدعي هاد الفنكشن وبياخد منا متغيرين الاول هو ترتيب العنصرالي اخترناه من ال spinner في قائمة العناصر
                         الي في ال spinner

                         المتغير الثاني هو عباره عن القيمه الي اخترناها من ال spinner الي ضغطنا عليه
                     */
                    Days_And_Working_Hours . Add_Work_Hours ( position , parent . getItemAtPosition ( position ) . toString ( ) ) ;
                    break ;
                }

                case "Sunday" :
                {
                    Days_And_Working_Hours . Index_Of ( "الاحد" ) ;
                    Days_And_Working_Hours . Add_Work_Hours ( position , parent . getItemAtPosition ( position ) . toString ( ) ) ;
                    break ;
                }

                case "Monday" :
                {
                    Days_And_Working_Hours . Index_Of ( "الاثنين" ) ;
                    Days_And_Working_Hours . Add_Work_Hours ( position , parent . getItemAtPosition ( position ) . toString ( ) ) ;
                    break ;
                }

                case "Tuesday" :
                {
                    Days_And_Working_Hours . Index_Of ( "الثلاثاء" ) ;
                    Days_And_Working_Hours . Add_Work_Hours ( position , parent . getItemAtPosition ( position ) . toString ( ) ) ;
                    break ;
                }

                case "Wednesday" :
                {
                    Days_And_Working_Hours . Index_Of ( "الاربعاء" ) ;
                    Days_And_Working_Hours . Add_Work_Hours ( position , parent . getItemAtPosition ( position ) . toString ( ) ) ;
                    break ;
                }

                case "Thursday" :
                {
                    Days_And_Working_Hours . Index_Of ( "الخميس" ) ;
                    Days_And_Working_Hours . Add_Work_Hours ( position , parent . getItemAtPosition ( position ) . toString ( ) ) ;
                    break ;
                }

                case "Friday" :
                {
                    Days_And_Working_Hours . Index_Of ( "الجمعه" ) ;
                    Days_And_Working_Hours . Add_Work_Hours ( position , parent . getItemAtPosition ( position ) . toString ( ) ) ;
                    break ;
                }
            }
        }
    }

    // هاد كمان تابع اله لكن هاد بتنفذ لما ما نختار اشي
    @Override
    public void onNothingSelected ( AdapterView < ? > parent )
    {

    }


    // --------------- نهاية الجزء الي فيه الفنكشن الي بخصو ال Spinners تبعين ساعات العمل ---------------

}



class Days_And_Working_Hours
{

    /*
        طيب هاد الكلاس ي منار انا عامله خص نص عشان اخزن في اليوم و الساعات العمل فيه

        ولحتى يكون كلامي اوضح عملته لحتى اقدر اعرف List of object كل اوبجكت فيه بعبر عن واحد من الايام الي اخترتها

        وكل يوم بنعمل اله اوبجكت في هاي ال List بكون اله 5 متغيرات و هم



        1- اسم اليوم الي اخترناه

        2- قيمة من الساعه الفلانيه الي بنختارها من spinner تبع من الساعه الفلانيه

        3- قيمة الصباحا او مساء الخاصه ب من الساعه الفلانيه الي بنختارها من spinner تبع الصباحا او مساء الخاص ب من الساعه الفلانيه

        4- قيمة الى الساعه الفلانيه الي بنختارها من spinner تبع من الساعه الفلانيه

        5- قيمة الصباحا او مساء الخاصه ب الى الساعه الفلانيه الي بنختارها من spinner تبع الصباحا او مساء الخاص ب الى الساعه الفلانيه
     */

    // هاد المتغير بنحتاجه عشان نخزن في index اليوم بدنا نعرف ال index تبعه
    public static int index ;

    // هدول المتغييرن بنحتاجهم عشان ناتكد انه اختار ساعات العمل بشكل صحيح
    public static boolean is_From_Hour_Empty , is_To_Hour_Empty ;


    // هدول ال 5 هم نفسهم الي ذكرتهم لما حكيت انه كل اوبجكت في هاي days_And_Working_Hours_Objects_List ال List اله 5 متغيرات
    public String from_AM_Or_PM ;
    public String to_AM_Or_PM   ;
    public String from_Hour     ;
    public String to_Hour       ;
    public String day           ;

    // هاد ال constructor الخاص بالكلاس وظيفته انه لما اضيف اوبجكت ليوم معين يستقبل مني القيم الخاص بهاد الاوبجكت
    public Days_And_Working_Hours ( String day , String from_Hour , String from_AM_Or_PM , String to_Hour , String to_AM_Or_PM )
    {
        this . from_AM_Or_PM = from_AM_Or_PM ;
        this . to_AM_Or_PM   = to_AM_Or_PM   ;
        this . from_Hour     = from_Hour     ;
        this . to_Hour       = to_Hour       ;
        this . day           = day           ;
    }

    // هاد الفنكشن هو الي بخزن index اليوم الي بدنا نعرف ال index تبعه في المتغير الي اسمه index فيي هاي الكلاس
    public static void Index_Of ( String day )
    {
        /*
            هاد الفنكشن بستقبل منا متغير من نوع string اسمه day

            جوا هاد الفنكشن عنا for each بتلف على كل الاوبجكت في هاي days_And_Working_Hours_Objects_List ال List
         */
        for ( Days_And_Working_Hours element : WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List )
        {
            /*
                هون بنشيك على قيمة متغير ال day الخاص بالاوبجكت الي ال for each ماشره على ال index تبعه

                اذا كانت قيمته تساوي قيمة متغير ال day الي مبعوث لهاد الفنكشن وقتها رح يدخل و ينفذ الي جوا الاف
             */
            if ( element . day . equals ( day ) )
            {
                // كل الي بعمله هون هو انه بجيب index الاوبجكت الي قيمة متغير ال day تبعه تساوي قيمة متغير ال day الي مبعوث للفنكشن و بخزنه في المتغير الي في هاي الكلاس و الي اسمه index
                index = WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . indexOf ( element ) ;
                break ;
            }
        }
    }



    // هسه عنا 5 فنكشن تحت اول 4 بشتغلو نفس الشغل بالزبط لكن الفرق الوحيد بينهم انه كل واحد فيهم بستقبل قيمة واحد من المتغيرات الي بتخص ساعات العمل الي بتكون موجوده داخل هاي days_And_Working_Hours_Objects_List ال List




    // هاد الفنكشن وظيفته انه يستقبل منا القيمه الي بنحددها من خلال spinner من الساعه الفلانيه ويضيفها في اوبجكت جديد بحتوي بقية القيم الموجوده في الاوبجكت الحالي
    public static void Set_From_Hour ( String from_Hour )
    {

        // هون بنشيك اذا كانت ال from_Hour الي جايه لهاد الفنكشن فاضيه خزن في هاد is_From_Hour_Empty المتغير true اذا مش فاضيه خزن false
        is_From_Hour_Empty = from_Hour . isEmpty ( ) ;

        /*
            هون احنا بنضيف القيمه الي استقبله هاد الفنكشن في هاد from_Hour المتغير على اوبجكت جديد مع القيم الي كانت في الاوبجكت القيدم
            الي موجود في ال index كذا

            بعدها بنحط هاد الاوبجكت مكان الاوبجكت الحالي و الي ال index تبعه كذا

            و فنكشن ال set بياخد منا متغييرن الاول هو ال index الي بدنا نضيف الداتا فيه

            والثاني هو الداتا الي بدنا نضيفها في هاد ال index و في حالتنا الداتا هي عباره عن اوبجكت لهيك قلت اله هيك
            new Days_And_Working_Hours

            لاني انا بتعامل مع اوبجكت من هاي الكلاس ولما اعمل set انا بعدل على محتويات index معين مش بضيف عنصر جديد

            فلما اقول اله هيك new Days_And_Working_Hours زي كانه ي منار

             ال index الفلاني مخزن فيه مثلا 5 و لما جيت انا قلت اله new Days_And_Working_Hours زي كاني قلت اله بدل 5 حط 6
         */
        WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . set
        (
            // هون بنجيب قيمة ال index من المتغير index الي موجود في هاد الكلاس لانه index الاوبجكت الي بدنا نبدله ب اوبجكت ثاني موجود فيه
            index ,

            /*
                هسه هون انا بقوم بنسخ داتا الاوبجكت القديم للاوبجكت الجديد و بحط فوق هاي الداتا القيمه الي جايه لهاد الفنكشن في هاد from_Hour المتغير

                { 1-
                    هاد السطر days_And_Working_Hours_Objects_List . get ( index ) . day

                    بقول جيب الي القيمه المخزنه في متغير ال day الخاص بالاوبجكت الي ال index تبعه كذا و حطها كقيمة
                    لهاد لمتغير ال day الخاص بالاوبجكت الجديد الي بدي احطه محل الاوبجكت الموجود في ال index كذا
                }

                السطر الثاني بحط القيمه الي جايه لهاد الفنكشن في المتغير from_Hour كقيمة لمتغير ال from_Hour الخاص بالاوبجكت الجديد

                { 3-
                    هاد السطر days_And_Working_Hours_Objects_List . get ( index ) . from_AM_Or_PM

                   بقول جيب الي القيمه المخزنه في متغير ال from_AM_Or_PM الخاص بالاوبجكت الي ال index تبعه كذا و حطها كقيمة
                   لهاد لمتغير ال from_AM_Or_PM الخاص بالاوبجكت الجديد الي بدي احطه محل الاوبجكت الموجود في ال index كذا
                }

                { 3-
                    هاد السطر days_And_Working_Hours_Objects_List . get ( index ) . to_Hour

                    بقول جيب الي القيمه المخزنه في متغير ال to_Hour الخاص بالاوبجكت الي ال index تبعه كذا و حطها كقيمة
                    لهاد لمتغير ال to_Hour الخاص بالاوبجكت الجديد الي بدي احطه محل الاوبجكت الموجود في ال index كذا
                }

                { 4-
                     هاد السطر days_And_Working_Hours_Objects_List . get ( index ) . to_AM_Or_PM

                    بقول جيب الي القيمه المخزنه في متغير ال to_AM_Or_PM الخاص بالاوبجكت الي ال index تبعه كذا و حطها كقيمة
                    لهاد لمتغير ال to_AM_Or_PM الخاص بالاوبجكت الجديد الي بدي احطه محل الاوبجكت الموجود في ال index كذا
                }
             */
            new Days_And_Working_Hours
            (
                WorkPlace_Data . workPlace_Data_Object. days_And_Working_Hours_Objects_List . get ( index ) . day ,
                from_Hour ,
                WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . from_AM_Or_PM ,
                WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . to_Hour ,
                WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . to_AM_Or_PM
            )
        ) ;
    }

    // هاد الفنكشن وظيفته انه يستقبل منا القيمه الي بنحددها من خلال spinner صباحا او مساء الخاص ب من الساعه الفلانيه ويضيفها في اوبجكت جديد بحتوي بقية القيم الموجوده في الاوبجكت الحالي
    public static void Set_From_AM_Or_PM ( String from_AM_Or_PM )
    {
        // هون تقريبا نفس الفنكشن الي قبل لكن هاد الفنشكن بستقبل قيمة متغير ال from_AM_Or_PM
        WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . set
        (
            index ,
            new Days_And_Working_Hours
            (
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . day ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . from_Hour ,
                from_AM_Or_PM ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . to_Hour ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . to_AM_Or_PM
            )
        ) ;
    }

    // هاد الفنكشن وظيفته انه يستقبل منا القيمه الي بنحددها من خلال spinner الى الساعه الفلانيه ويضيفها في اوبجكت جديد بحتوي بقية القيم الموجوده في الاوبجكت الحالي
    public static void Set_To_Hour ( String to_Hour )
    {
        // وهاد برضو نفس الشي لكن
        is_To_Hour_Empty = to_Hour . isEmpty ( ) ;

        WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . set
        (
            index ,
            new Days_And_Working_Hours
            (
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . day ,
                    WorkPlace_Data . workPlace_Data_Object. days_And_Working_Hours_Objects_List . get ( index ) . from_Hour ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . from_AM_Or_PM ,
                to_Hour ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . to_AM_Or_PM
            )
        ) ;
    }

    // هاد الفنكشن وظيفته انه يستقبل منا القيمه الي بنحددها من خلال spinner صباحا او مساء الخاص ب الى الساعه الفلانيه ويضيفها في اوبجكت جديد بحتوي بقية القيم الموجوده في الاوبجكت الحالي
    public static void Set_To_AM_Or_PM ( String to_AM_Or_PM )
    {
        WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . set
        (
            index ,
            new Days_And_Working_Hours
            (
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . day ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . from_Hour ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . from_AM_Or_PM ,
                    WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List . get ( index ) . to_Hour ,
                to_AM_Or_PM
            )
        ) ;
    }

    // هاد الفنكشن المسؤول عن تخزين ساعات العمل و مستدعيه في فنكشن ال onItemSelected في الكلاس الي فوق
    public static void Add_Work_Hours ( int position , String val )
    {

        // ملاحظه متغير ال spinner_Name و متغير ال flag هدول انا معرفهم في الكلاس الي فوق والي هي هاي Days_And_Working_Hours_Activity لهيك اذا انك ملاحظه اني كاتب اسم هاي الكلاس قبلهم

        /*
            طيب ي منار في هاد الفنكشن عنا جملتين if الاولى بتتفذ اذا كان ال spinner الي ضغطت عليه بخص من الساعه الفلانيه او بخص الى الساعه الفلانيه

            بغض النظر تبعين اي يوم لانه حددنا سابقا في هاد onItemSelected الفنكشن في كل case ال spinner لاي يوم بتبع



            وال if الثانيه ببتفذ اذا كان ال spinner تبع صباحا او مساء وبعض النظر اذا كان تبع الى الساعه الفلانيه او تبع من الساعه

            الفلانيه و لاي يوم بتبع لانه حددنا سابقا في هاد onItemSelected الفنكشن في كل case ال spinner لاي يوم بتبع
         */

        /*
            هسه هون داخل قوسين الاف بقله اذا اسم ال spinner كان بحتوي على كلمة Hour معناتها هاد ال spinner يا بخص من الساعه الفلانيه او
            بخص الى الساعه الفلانيه

            في اذا اسم ال spinner كان بحتوي كلمة Hour و بنقس الوقت اذا كان ترتيب العنصر الي خترناه من ال spinner في قائمة عناصر

            ال spinner مش اول عنصر

            ( لانه ي منار اول عنصر في spinners الى الساعه الفلانيه او من الساعه الفلانيه مكتوب فيه "من الساعه" بالنسبه ل spinner من الساعه الفلانيه و "الى الساعه" بالنسبه ل spinner الى الساعه الفلانيه )

            ادحل و نفذ الي داخل ال if
         */
        if ( position != 0 && Days_And_Working_Hours_Activity . spinner_Name . contains ( "Hour" ) )
        {
            /*
                هسه اذا كان اسم ال spinner بحتوي هاد المقطع "From_Hour" معناته ال spinner الي ضغطنا عليه هو spinner خاص ب من الساعه

                الفلانيه وقتها رح يدخل جوا الاف و يستدعي فنكشن Set_From_Hour من كلاس ال Days_And_Working_Hours و الي بضيف

                القيمه الي اخترناها من spinner الخاص ب من الساعه الفلانيه على اوبجكت اليوم الخاص فيه في هاي

                ؛days_And_Working_Hours_Objects_List ال List


               غير هيك رح يكون ال spinner الي ضغطنا عليه تبع الى الساعه الفلانيه و رح يستدعي هاد Set_To_Hour من كلاس ال

               ؛Days_And_Working_Hours و الي بضيف القيمه الي اخترناها من spinner الخاص ب الى الساعه الفلانيه على اوبجكت اليوم الخاص فيه في هاي

                ؛days_And_Working_Hours_Objects_List ال List
             */
            if ( Days_And_Working_Hours_Activity . spinner_Name . contains ( "From_Hour" ) )
                Days_And_Working_Hours . Set_From_Hour ( val ) ;
            else
                Days_And_Working_Hours . Set_To_Hour   ( val   ) ;
        }


        /*
            داخل قوسين هاي الاف بشيك انه ال spinner الي ضغطت عليه مش spinner الى الساعه الفلانيه او spinner من الساعه الفلانيه

            من خلال انه اقله داخل القوسين اذا كان اسم ال spinner ما بحتوي كلمة "Hour" معناتو هاد السبنيير ي اما هو spinner صباحا او مساءا تبع من الساعه الفلانيه

            او هو spinner صباحا او مساء تبع الى الساعه الفلانيه
         */
        if ( ! Days_And_Working_Hours_Activity . spinner_Name . contains ( "Hour" ) )
        {
            /*
                هون داخل قوسين الاف بشيك اذا كان ال spinner الي ضغطنا عليه هو spinner صباحا او مساء تبع من الساعه الفلانيه

                اذا كان هيك رح يستدعي النا فنشكن ال  Set_From_AM_Or_PM من كلاس ال Days_And_Working_Hours والي بضيف

                النا القيمه الي اخترناها من spinner صباحا او مساء تبع من الساعه الفلانيه على اوبجكت اليوم الخاص فيه في هاي

                ؛days_And_Working_Hours_Objects_List ال List



                غير هيك رح يستدعي النا فنشكن ال  Set_To_AM_Or_PM من كلاس ال Days_And_Working_Hours والي بضيف

                النا القيمه الي اخترناها من spinner صباحا او مساء تبع الى الساعه الفلانيه على اوبجكت اليوم الخاص فيه في هاي

                ؛days_And_Working_Hours_Objects_List ال List

             */
            if ( Days_And_Working_Hours_Activity . spinner_Name . contains ( "From_Am_Or_Pm" ) )
                Days_And_Working_Hours . Set_From_AM_Or_PM ( val ) ;
            else
                Days_And_Working_Hours . Set_To_AM_Or_PM   ( val       ) ;
        }

        /*
            واخر شي بخلي قيمة ال flag تساوي false عشان امنع الكود انه ينفذ ال Switch الي داخل فنكشن ال onItemSelected

            لانه ي منار فنكشن ال onItemSelected هاد بتنفذ مش بس نختار شي من اي spinner لا هاد بتفذ اول ما نفتح الشاشه

            وكمان لما ال spinner يظهر او يختفي

            لهيك اذا ما خلينا ال flag يساوي false رح ينفذ الي داخل ال switch بدون ما يكون في حاجه اختار شي من واحد من ال spinners

            وهاد الشي رح يتسبب في خطا
         */
        Days_And_Working_Hours_Activity . flag = false;
    }

}