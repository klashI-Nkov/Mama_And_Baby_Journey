package com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderInfoFolderWorkplaceDataActivityBinding;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.annotation.SuppressLint;
import com.example.mamababyjourney.R;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.Intent;
import android.graphics.Color;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import java.util.Objects;
import java.util.List;

@SuppressLint ( { "SetTextI18n" , "SuspiciousIndentation" } )
@SuppressWarnings ( { "deprecation" , "ConstantConditions"  , "SpellCheckingInspection" } )

public class Workplace_Data_Activity extends AppCompatActivity implements AdapterView . OnItemSelectedListener
{

    private ActivitySignUpAndSignInFolderInfoFolderWorkplaceDataActivityBinding binding ;

    private double longitude , latitude ;

    private String workPlace_Type ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate ( savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderInfoFolderWorkplaceDataActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        Adapter_Initialization ( ) ;

        if ( getIntent ( ) . getExtras ( ) . getString ("Action" ) . equals ( "Edit" ) )
            Get_workPlace_Data_Object ( ) ;
    }





    public void Go_To_Map_BTN ( View view )
    {
        Intent intent = new Intent (this , Map_Activity . class ) ;

        intent . putExtra ("workPlace_Type" ,workPlace_Type ) ;
        intent . putExtra ("longitude"      ,longitude      ) ;
        intent . putExtra ("latitude"       ,latitude       ) ;

        if ( binding . GoToMapBTN . getText ( ) . toString ( ) . contains ( "تعديل" ) )
            intent . putExtra ("Action" , "Edit" ) ;
        else
            intent . putExtra ("Action" , "Add" ) ;

        startActivityForResult (intent , 1 ) ;
    }

    public void Add_Days_And_Working_Hours_BTN ( View view )
    {
        Intent intent = new Intent (this ,Days_And_Working_Hours_Activity . class ) ;

        if ( binding . AddDaysAndWorkingHoursBTN . getText ( ) . toString ( ) . contains ( "تعديل" ) )
            intent . putExtra ("Action" ,"Edit" ) ;
        else
        {
            WorkPlace_Data . workPlace_Data_Object . days_And_Working_Hours_Objects_List = new ArrayList < > () ;
            intent . putExtra ( "Action" ,"Add" ) ;
        }

        startActivityForResult (intent ,2 ) ;
    }

    public void Save_Workplace_Data_BTN ( View view )
    {
        if
        (
            /*
            ! binding . WorkPlaceLocationCoordinatesTv . getText ( ) . toString ( ) . contains ( "على" ) &&
            ! binding . DaysAndWorkingHoursTv  . getText ( ) . toString ( ) . contains ( "قم"  ) &&
            ! binding . WorkPlaceAddress             . getText ( ) . toString ( ) . isEmpty  (       ) &&
            ! binding . WorkPlaceName                . getText ( ) . toString ( ) . isEmpty  (       ) &&
            ! binding . PhoneNumber                  . getText ( ) . toString ( ) . isEmpty  (       )*/
            true
        )
        {

            Intent intent = getIntent ( ) ;

            if ( getIntent ( ) . getExtras ( ) . getString ("Action" ) . equals ( "Edit" ))
                intent . putExtra ("Id" ,getIntent ( ) . getExtras ( ) . getInt ("Id" ) ) ;


            WorkPlace_Data . workPlace_Data_Object . workPlace_Address = binding . WorkPlaceAddress . getText ( ) . toString ( ) ;
            WorkPlace_Data . workPlace_Data_Object . workPlace_Name    = binding . WorkPlaceName    . getText ( ) . toString ( ) ;
            WorkPlace_Data . workPlace_Data_Object . phoneNumber       = binding . PhoneNumber      . getText ( ) . toString ( ) ;

            WorkPlace_Data . workPlace_Data_Object . longitude         = longitude ;
            WorkPlace_Data . workPlace_Data_Object . latitude          = latitude  ;

            intent . putExtra ("days And Working Hours Text" ,binding . DaysAndWorkingHoursTv . getText ( ) . toString ( ) ) ;

            setResult (RESULT_OK ,intent ) ;

            finish ( ) ;
        }
        else
        Snack_Bar
        (
            "يرجى التاكد بانك قمت بتعبئة جميع البيانات المطلوبه ومن ضمنها ايام و ساعات العمل و مكان " + workPlace_Type + " على خرائط جوجل"
        );

    }

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





    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {
        super . onActivityResult ( requestCode , resultCode , data ) ;

        if ( resultCode == RESULT_OK )
        {
            switch ( requestCode )
            {
                // هاد ال case بخص الخارطه
                case 1 :
                {
                    longitude = data . getExtras ( ) . getDouble ( "longitude"  ) ;
                    latitude  = data . getExtras ( ) . getDouble ( "latitude"   ) ;

                    binding . WorkPlaceLocationCoordinatesTv . setText
                    ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + latitude + " , " + longitude ) ;

                    binding . GoToMapBTN . setText ( "تعديل احداثيات مكان " + workPlace_Type ) ;
                    break ;
                }

                // هاد ال case بخص شاشة اضافة ايام و ساعات العمل
                case 2 :
                {

                    WorkPlace_Data workPlace_data_Object = WorkPlace_Data . workPlace_Data_Object ;

                    binding . DaysAndWorkingHoursTv . setText ( "ايام و ساعات العمل\n\n" ) ;

                    for ( Days_And_Working_Hours element : workPlace_data_Object . days_And_Working_Hours_Objects_List )
                    {
                        binding . DaysAndWorkingHoursTv . setText
                        (
                            binding . DaysAndWorkingHoursTv . getText ( ) +

                            element . day + "\n" +

                            " من الساعة : " + element . from_Hour + " " +

                            element . from_AM_Or_PM +

                            " الى الساعة : " + element . to_Hour + " " +

                            element . to_AM_Or_PM
                        ) ;

                        // هون بقول اله اذا كان الاوبجكت الي ال for each ماشره على ال index تبعه مش اخر عنصر في هاي days_And_Working_Hours_Objects_List ال list ضيف هاد الفاصل على ال text تبع ال textView الي بنعرض فيه ايام و ساعات العمل
                        if
                        (
                            workPlace_data_Object . days_And_Working_Hours_Objects_List . indexOf ( element )
                            !=
                            workPlace_data_Object . days_And_Working_Hours_Objects_List . size ( ) - 1
                        )
                            binding . DaysAndWorkingHoursTv . setText
                            (
                                binding . DaysAndWorkingHoursTv . getText ( ) . toString ( ) +
                                "\nـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ\n"
                            ) ;
                    }

                    binding . AddDaysAndWorkingHoursBTN . setText ( "تعديل ايام و ساعات العمل" ) ;

                    break ;
                }
            }
        }
    }

    private void Get_workPlace_Data_Object ( )
    {
        ProgressDialog progressDialog = new ProgressDialog ( this );
        progressDialog. setMessage ( "يرجى الانتظار" ) ;
        progressDialog. show ( ) ;

        View [ ] screen_Components =
        {
            findViewById (R . id . WorkPlace_location_coordinates_Tv ) ,
            findViewById (R . id . Add_Days_And_Working_Hours_BTN    ) ,
            findViewById (R . id . Days_And_Working_Hours_Tv         ) ,
            findViewById (R . id . WorkPlace_Address                 ) ,
            findViewById (R . id . WorkPlace_Name                    ) ,
            findViewById (R . id . Phone_Number                      ) ,
            findViewById (R . id . Go_to_Map_BTN                     )
        };

        WorkPlace_Data workPlace_data_Object = WorkPlace_Data . workPlace_Data_Object ;

        workPlace_Type = workPlace_data_Object . workPlace_Type ;

        // show screen components
        for ( View element : screen_Components )
        {
            element . setVisibility ( View . VISIBLE ) ;
        }


        // set the hint for edit text
        binding . WorkPlaceAddress . setHint ( "عنوان "    + workPlace_Type ) ;
        binding . WorkPlaceName    . setHint ( "اسم "      + workPlace_Type ) ;
        binding . PhoneNumber      . setHint
        (
            workPlace_Type . equals ( "العيادة" ) ?
            "رقم هاتف " + workPlace_Type :
            "رقم الهاتف"
        ) ;


        // setSelection for workplace type spinner
        binding . WorkplaceTypeSp . setSelection ( workPlace_data_Object . workPlace_Type_Index_In_Spinner ) ;

        // set the text for edit text
        binding . WorkPlaceAddress . setText ( workPlace_data_Object . workPlace_Address ) ;

        binding . WorkPlaceName    . setText ( workPlace_data_Object . workPlace_Name    ) ;

        binding . PhoneNumber      . setText ( workPlace_data_Object . phoneNumber       ) ;



        // set the text for workPlace location coordinates tv
        longitude = workPlace_data_Object . longitude ;
        latitude  = workPlace_data_Object . latitude  ;

        binding . WorkPlaceLocationCoordinatesTv . setText
        ( "احداثيات مكان " + workPlace_Type + " التي حددتها هي" + "\n\n" + latitude + " , " + longitude ) ;

        // set text for go to map BTN
        binding . GoToMapBTN . setText ( "تعديل احداثيات مكان " + workPlace_Type ) ;



        // set the text for days and working hours tv
        binding . DaysAndWorkingHoursTv . setText ( "ايام و ساعات العمل\n\n" ) ;

        for ( Days_And_Working_Hours element : workPlace_data_Object . days_And_Working_Hours_Objects_List )
        {
            binding . DaysAndWorkingHoursTv . setText
            (
                binding . DaysAndWorkingHoursTv . getText ( ) +

                element . day +

                " من الساعة : " + element . from_Hour + " " +

                element . from_AM_Or_PM +

                " الى الساعة : " + element . to_Hour + " " +

                element . to_AM_Or_PM
            ) ;

            // هون بقول اله اذا كان الاوبجكت الي ال for each ماشره على ال index تبعه مش اخر عنصر في هاي days_And_Working_Hours_Objects_List ال list ضيف هاد الفاصل على ال text تبع ال textView الي بنعرض فيه ايام و ساعات العمل
            if
            (
                workPlace_data_Object . days_And_Working_Hours_Objects_List . indexOf ( element ) !=
                workPlace_data_Object . days_And_Working_Hours_Objects_List . size ( ) - 1
            )
                binding . DaysAndWorkingHoursTv . setText
                (
                    binding . DaysAndWorkingHoursTv . getText ( ) . toString ( ) +
                    "\nـــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ\n"
                ) ;
        }

        // set text for add days and working hours BTN
        binding . AddDaysAndWorkingHoursBTN . setText ( "تعديل ايام و ساعات العمل" ) ;



        progressDialog. dismiss ( ) ;
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






    private void Adapter_Initialization ( )
    {
        // هون احنا بنحدد العناصر الي رح تنعرض النا لما نضغط على ال spinner
        ArrayAdapter < CharSequence > adapter = ArrayAdapter . createFromResource
        ( this , R . array . Workplace_Locations , R . layout .layouts_spinner_drop_down_items_text_layout ) ;

        // فيي هاد السطر بعطيه شكل النص تبع القائمه الي رح تظهر النا لما نضغط على ال spinner
        adapter . setDropDownViewResource ( R . layout .layouts_spinner_drop_down_items_text_layout ) ;

        // في هاد السطر بنربط ال array الي فيها الداتا الي رح تظهر لما نضغط على ال spinner
        binding . WorkplaceTypeSp . setAdapter ( adapter ) ;

        // في هاد السطر بنعمل تهيئه لحدث اختيار عنصر من ال spinner
        binding . WorkplaceTypeSp . setOnItemSelectedListener ( this ) ;
    }

    @Override
    public void onItemSelected ( AdapterView < ? > parent , View view , int position , long id )
    {

        View [ ] screen_Components =
        {
            findViewById (R . id .Days_And_Working_Hours_Tv ) ,
            findViewById (R . id . Add_Days_And_Working_Hours_BTN   ) ,
            findViewById (R . id .WorkPlace_location_coordinates_Tv ) ,
            findViewById (R . id . WorkPlace_Address                ) ,
            findViewById (R . id . WorkPlace_Name                   ) ,
            findViewById (R . id . Phone_Number                     ) ,
            findViewById (R . id . Go_to_Map_BTN                    )
        };

        if ( position != 0 && getIntent ( ) . getExtras ( ) . getString ("Action" ) . equals ( "Add" ) )
        {

            workPlace_Type =  parent . getItemAtPosition ( position ) . toString ( ) ;

            switch ( workPlace_Type )
            {

                case "عيادة خاصة" :
                {
                    workPlace_Type = "العيادة" ;

                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner = 3              ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type                  = workPlace_Type ;

                    binding . WorkPlaceLocationCoordinatesTv . setText ( "حدد مكان العيادة على خرائط قوقل" ) ;
                    binding . WorkPlaceAddress               . setHint ( "عنوان العيادة"                   ) ;
                    binding . WorkPlaceName                  . setHint ( "اسم العيادة"                     ) ;
                    binding . PhoneNumber                    . setHint ( "رقم هاتف العيادة"                ) ;

                    break ;
                }

                case "مشفى" :
                {
                    workPlace_Type = "المشفى" ;

                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner = 4              ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type                  = workPlace_Type ;

                    binding . WorkPlaceLocationCoordinatesTv . setText ( "حدد مكان المشفى على خرائط قوقل" ) ;
                    binding . WorkPlaceAddress               . setHint ( "عنوان المشفى"                   ) ;
                    binding . WorkPlaceName                  . setHint ( "اسم المشفى"                     ) ;
                    binding . PhoneNumber                    . setHint ( "رقم الهاتف"                     ) ;

                    break ;
                }

                // هاي بتتنفذ اذا كان مكان العمل الي اختاره مركز صحي او مركو تابع للانروا
                default :
                {
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type_Index_In_Spinner = workPlace_Type . contains ( "تابع" ) ? 1 : 2 ;

                    workPlace_Type = "المركز" ;
                    WorkPlace_Data . workPlace_Data_Object . workPlace_Type = workPlace_Type ;

                    binding . WorkPlaceLocationCoordinatesTv . setText ( "حدد مكان المركز على خرائط قوقل" ) ;
                    binding . WorkPlaceAddress               . setHint ( "عنوان المركز"                   ) ;
                    binding . WorkPlaceName                  . setHint ( "اسم المركز"                     ) ;
                    binding . PhoneNumber                    . setHint ( "رقم الهاتف"                     ) ;

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

    @Override
    public void onNothingSelected ( AdapterView < ? > parent )
    {

    }
}


@SuppressLint ( "SuspiciousIndentation" )
@SuppressWarnings ( { "unused" , "SpellCheckingInspection" , "RedundantSuppression" } )
class WorkPlace_Data
{

    // هسه ي منار هاي الكلاس انا عاعملها عشان بس اعمل منها اوبجكت بستقبل مني بيانات مكان العمل و بخزنها في المتغيرات الخاصه فيه و الي هي تحت

    // وهاد هو الاوبجكت الي انا بحكي عنه
    public static WorkPlace_Data workPlace_Data_Object = new WorkPlace_Data ( ) ;



    // هدول المتغيرات الي بمتلكهم الاوبحكت الي فوق


    // بتخزن فيه اسم مكان العمل الي بناخده من ال edit text
    public String workPlace_Name = null ;



    // هاد بتخزن فيه عنوان مكان العمل لي بناخده من ال edit text
    public String workPlace_Address = null ;



    // هاد بتخزن فيه رقم الهاتف لي بناخده من ال edit text
    public String phoneNumber = null ;



    // هاد بتخزن فيه نوع مكان العمل الي بناخده من ال spinner بنختار منه مكان العمل
    public String workPlace_Type ;



    // هاد بخزن فيه index العنصر الي بختاره من ال spinner
    public int workPlace_Type_Index_In_Spinner ;



    // هدول الي بخزن فيهم احداثيات مكان العمل على الخارطه
    public double longitude, latitude ;



    // هاي ال list الي بتخزن فيها ايام و ساعات العمل لكل مكان بنضاف ومستعملها في خاي Days_And_Working_Hours_Activity الكلاس و هاي Days_And_Working_Hours الكلاس
    public List < Days_And_Working_Hours > days_And_Working_Hours_Objects_List = null ;






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