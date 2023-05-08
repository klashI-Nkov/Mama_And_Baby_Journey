package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.ActivityInfoBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.Objects;

@SuppressWarnings ( { "unused" , "SpellCheckingInspection" , "deprecation" , "FieldCanBeLocal" } )
@SuppressLint ( "ClickableViewAccessibility" )
public class Info_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ActivityInfoBinding binding;
    EditText Name_editText;

     private double longitude , latitude ;

    private final int reqcode = 2;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Objects.requireNonNull ( getSupportActionBar ( ) ).hide ( );

        binding = ActivityInfoBinding.inflate ( getLayoutInflater ( ) );
        super.onCreate ( savedInstanceState );
        setContentView ( binding.getRoot ( ) );


        // for Auto Complete Text View hints
        adapter_initialization ( );

        /*
            قبل ما تبلشي تقري في كومنتات الفنشكن التحت روحي لكود الجافا تاع ال Map و شوفي هاد الفشنكن
            binding.SaveWorkplaceOnMapBTN.setOnClickListener ( v ->
            الي موجود داخل فنكشن ال onCreate لانك لو ما شفتيه ما رح تفهمي شي منه
         */
        // هاد الفنكشن بتنفذ لما نكبس على زر الذهاب الى الخارطة
        binding.MapBTN.setOnClickListener ( v ->
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

                ومعطيها مع ال intent متغير اسمه  reqcode بعبر عن قيمه لمتغير اسمه requestCode

                طيب تعالي هلا اشرح الك بالتفصيل شو الفرق بين startActivityForResult و بين startActivity

                نبلش بالسهل منهم و الي ما بده شرح كثير ال startActivity ببساطه بتنقلنا للشاشه
                الي حددناها في ال intent والي بتاخده startActivity

                اما ال  startActivityForResult هي بتشغل الشاشة الي حددناها في ال intent +
                انها بتستنى الشاشه الي اشتغلت والي هي الخارطه في حالتنا هون ترجع قيمه للشاشه الحالية
                والي هي في حالتنا هون شاشة البيانات

                طيب هسه بتحكي بعقلك فهمت الفرق بينهم شو بالنسبه لهاد المتغير reqcode

                انا بقلك شو هاد المتغير و لشو وبدي اوصلك الفكره عن طريق مثال

                هسه مش في الدوائر الحكوميه او المعاملات في الجامعه بكون الها ارقام عشان يقدرو يرجعو الها بس يحتاجوها

                هون نفس القصه هاد ال reqcode بوديه للشاشه الي رح ينقلنا ال intent الها

                ليه بنوديه الها لانه ي منار مو قلنا ال startActivityForResult بتشغل الشاشه الي حددناها في ال intent و بتستنى منها ترجع نتيجه او قيمه

                هاي القيمه هي مثل المعامله الي حكيت عنها في المثال لازم يكون في الها رقم عشان لما بدنا نستخرج البيانات من النتيجه الي رجعت النا بدنا نكون عارفين شو رقم النتيجه الي بدنا ناخد منها البيانات
             */

            Intent intent = new Intent ( this, Map.class );

            startActivityForResult ( intent , reqcode );

        } );

    }

    /*
       قبل ما تخشي تقري الكومنتات روحي  شوفي هاد
       binding.MapBTN.setOnClickListener ( v -->
        الي في فنكشن ال onCreate في هاد الكلاس

       وبنصحك قبل ما تخشي تفهمي شو المكتوب جوا هاد الفنكشن تروحي على الفنشكن الي ذكرته فوق تفهميهم بعدها تجي هون تفهمي شو الي مكتوب جواته

       هاد الفنشكن بستدعى و بتنفذ في حالة انه احنا دخلنا على كلاس وفي
       حالتنا هون هي كلاس البيانات و منها رحنا للخارطه وبعد هيك رجعنا
       باستعمال intent موجود فيه داتا لكلاس البيانات وقتها رح يتنفذ هاد الفنشكن
    */
    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent data )
    {

        /*
            هسه مثل ما انتي شايفه طبعا الفنكشن بستقبل 3 متغيرات الاول requestCode ومعناه كود الطلب
            الثاني هو resultCode معناه كود النتيجه وهاد بجي النا هو و ال requestCode لما نرجع من الخارطه
            لشاشة البيانات باستعمال intent من خلال ال intent الي رجعنا من خلاله من الخارطه للشاشة البيانات و الي
            بحتوي ال requestCode و resultCode والي في حالتنا هون هو المتغير الثالث والي اسمه data
         */
        super.onActivityResult ( requestCode , resultCode , data );

        /*
            هون بنحكي اله اذا كان ال requestCode الي جاي مع ال intent من شاشة الخارطة يساوي ال reqcode الي في الشاشة الحالية الي
             هي شاشة البيانات ادخل و نفذ الي جوا جملة الاف
         */
        if ( requestCode == reqcode )
        {
            // هون بنحكي اله اذا كان ال resultCode الي جاي مع ال intent قيمته تساوي RESULT_OK ادخل نفذ الي جوا الاف
            if ( resultCode == RESULT_OK )
            {
                /*
                    هون عنا متغيرين معرفين في الكلاس الي احنا فيه هلا والي هو كلاس البيانات المتغيرين بمثلو مكان موقع
                    العمل الي حدده المستخدم على خط الطول و على خط العرض
                    هاد latitude احداثيات خط العرض الخاصه بموقع العمل
                    والتاني هاد longitude في احداثيات خط الطول الخاصه بموقع العمل

                    هون data != null ? بشتغل شغل الاف لكن بشكل مختصر وبقله هون في السطرين الي تحت
                    اذا الداتا الي جايه مع ال intent ما كانت فاضيه
                    جيبها و خزنها في هدول المتغيرين

                    اما اذا ما كان في داتا يعني الدكتور ما حدد موقع العمل على الخارطه خلي المكان قيمتهم تساوي صف

                    يعني هاي data != null ? عباره عن جملة اف
                    وهاي : عباره عن كلمة else

                    هلا كيف بجيب الداتا الي بدي اياها بالزبط من ال intent ألي جاي من الخريطه و محمل بداتا

                    بجيب الداتا الي بدي اياها بالذات من خلال ال key و الي هو نفسه ال name

                    وفنكشن ال getDoubleExtra هو الفنكشن بستقبل قيمه double  ومن خلاله بنستخرج القيمه الي جايه مع ال intent
                 */
                latitude  = data != null ? data.getDoubleExtra ( "latitude"  , 0 ) : 0 ;
                longitude = data != null ? data.getDoubleExtra ( "longitude" , 0 ) : 0 ;
            }
        }
    }

    public void Which_Radio_BTN_Checked ( View view )
    {
        if ( binding.DoctorRBTN.isChecked ( ) || binding.DoctorRBTN2.isChecked ( ) )
        {
            binding.MomRBTN.setChecked ( false );
            binding.MomRBTN2.setChecked ( false );

            binding.DoctorRBTN.setChecked ( true );
            binding.DoctorRBTN2.setChecked ( true );

            binding.MomData.setVisibility ( View.INVISIBLE );
            binding.DoctorData.setVisibility ( View.VISIBLE );
        }
        else if ( binding.MomRBTN.isChecked ( ) || binding.MomRBTN2.isChecked ( ) )
        {
            binding.DoctorRBTN.setChecked ( false );
            binding.DoctorRBTN2.setChecked ( false );

            binding.MomRBTN.setChecked ( true );
            binding.MomRBTN2.setChecked ( true );

            binding.DoctorData.setVisibility ( View.INVISIBLE );
            binding.MomData.setVisibility ( View.VISIBLE );
        }
    }

    public void Add_Workplace_Dialog_Function ( View view )
    {
        binding.DoctorData.setVisibility ( View.INVISIBLE );
        binding.WorkplaceData.setVisibility ( View.VISIBLE );

        binding.SaveWorkplaceData.setOnClickListener ( v ->
        {
            binding.WorkplaceData.setVisibility ( View.INVISIBLE );
            binding.DoctorData.setVisibility ( View.VISIBLE );

            binding.DoctorName.setText ( Name_editText.getText() );
        } );

    }

    private void Workplace_fields ( )
    {
        // create a new instance of EditText
        EditText editText = new EditText(this);

        // set the layout parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);

        // set additional attributes
        editText.setHint("Enter your name");
        editText.setInputType( InputType.TYPE_CLASS_TEXT);

        // add the EditText to your parent layout
        LinearLayout linearLayout = findViewById(R.id.Workplace_Layout);
        linearLayout.addView(editText);

    }

    // هاد الفنكشن مستدعيه في ال onCreate و وظيفته هي انه يجهز القائمه الي الدكتور بختار منها مكان العمل للاستعمال عند انشاء الشاشة
    private void adapter_initialization ( )
    {

        ArrayAdapter < CharSequence > adapter = ArrayAdapter.createFromResource ( this , R.array.Workplace_Locations , R.layout.spinner_drop_down_items_text );

        adapter.setDropDownViewResource ( R.layout.spinner_drop_down_items_text );

        binding.Workplace.setAdapter ( adapter );
        binding.Workplace.setOnItemSelectedListener ( this );

    }

    // هاد بخص ال spneer الي فيه اماكن العمل بتنفذ او بتسدعى لما نتخار اي شغله من القائمه الي فيه

    @Override
    public void onItemSelected ( AdapterView < ? > parent , View view , int position , long id )
    {

    }

    // هاد كمان تابع اله لكن هاد بتنفذ لما ما نختار اشي
    @Override
    public void onNothingSelected ( AdapterView < ? > parent )
    {

    }
}