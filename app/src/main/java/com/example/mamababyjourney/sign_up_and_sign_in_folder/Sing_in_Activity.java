package com.example.mamababyjourney.sign_up_and_sign_in_folder;

import com.example.mamababyjourney.databinding.ActivitySignUpAndSignInFolderSingInActivityBinding;
import com.example.mamababyjourney.sign_up_and_sign_in_folder.Info_page.Doctor_Data_Activity;
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

@SuppressWarnings ( { "ConstantConditions" , "FieldMayBeFinal" , "deprecation" } )
@SuppressLint ( "ClickableViewAccessibility" )
public class Sing_in_Activity extends AppCompatActivity
{
    ActivitySignUpAndSignInFolderSingInActivityBinding binding ;

    String password , email ;

    // هون انا بمعل زي ما تقولي مسج بتظهر للمستخدم بتقله الرجاء الانتظار يعني يستنى لحد ما تتخزن الداتا في الفاير بيس
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;

        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;
        Objects . requireNonNull (getSupportActionBar ( ) ) . hide ( ) ;

        binding = ActivitySignUpAndSignInFolderSingInActivityBinding . inflate (getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) ) ;

        // هون انا بحدد شو المسج الي رح تظهر
        progressDialog = new ProgressDialog (this ) ;
        progressDialog . setMessage ( "يرجى الانتظار" ) ;
    }

    // هاد بتنفذ لما اضغط على زر تسجيل الدخول
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
                        Get_Data ( ) ;
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

    // هاد بتنفذ لما اضغط على تسجيل الدخول باستخدام جوجل
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

                        Get_Data ( ) ;
                    }
                });
            }
            catch ( ApiException a )
            {
                throw new RuntimeException ( a ) ;
            }
        }
    }




    /*
      هاد الفنكشن مستعمله عشان اعرض مسج معين و مستعمله في معظم كلاسات التطبيق

      لكن لو تروحي على هاد الفنكشن في كل الكلاسات رح تلاقي الي مكتوب جواته نفس الشي

      الا لو رحتي لكلاس ال map في شي مكتوب في هاد الفنكشن في كل الكلاسات ومش مكتوب في هاد الفنكشن الي في كلاس ال map

       لحتى تعرفي شو هو الشي الي مكتوب في هاد الفنكشن في كل الكلاسات ومش مكتوب في هاد الفنكشن في كلاس ال map ادخلي جواته رح تلاقي في كومنت مكتوب قبله

   */
    private void Snack_Bar ( String Message )
    {

        /*
            هون عرفنا اوبجكت من كلاس ال Snackbar

            و من خلال فنكشن ال make بنشء Snackbar وباخد 3 متيغرات
            الاول هو الشاشه او المكان الي رح يظهر فيها المسج

            الثاني هي المسج الي بدي اعرضها للمستخدم وهاي االمسج جايه من االمتغير الي اسمه message الي فوق وهاي بعطيها القيمه الي
            جواتها لما استدعي الفنكشن

            المتغير الثالث هو المده الي رح يضل فيها المسج ظاهر يعني يا اما بظهر لمدة طويلة او لمده قصيره وال LENGTH_LONG يعني
            مده طويله و LENGTH_SHORT يعني بظهر لفتره قصيرة

            لكن انا هو مش مستعمل لا LENGTH_LONG ولا مستعمل LENGTH_SHORT حاكي اليه مباشره المده هي 7000 ملي ثانيه يعني 5
            ثواني لانه كل 1000 ملي ثانيه بتساوي ثانيه وحده
        */
        Snackbar snackbar = Snackbar . make (binding . Constraint ,Message ,7000 ) ;

        // السطر الي تحت عشان اغير لون خلفية ال Snackbar

        int color = Color.parseColor ("#292929" ) ;

        snackbar . getView ( ) . setBackgroundTintList ( ColorStateList . valueOf (color ) ) ;

        /*
            هدول السطرين الي تحت عملناهم عشان نقدر نوصل للتكتست تاع ال Snackbar و عشان نعدل في لون وحجم الخط الخاص فيه

             وكونه ال Snackbar اداة مثل اي اداة اخرى فيعتبر view فبنعرف اوبجكت من الكلاس view و بعدها بنستعمل الاوبجكت الي
             عرفناه فوق من كلاس snackbar لحتى تقله getview ونصل ل اداة التكست الي مستعملها ليعرض فيها المسج ونعدل
             في حجم و لون الخط تبعها
         */
        View snackbarView = snackbar . getView ( ) ;
        TextView textView = snackbarView . findViewById (com . google . android . material . R . id . snackbar_text ) ;

        // هاد السطر قلنا اله انه ما بدي كل المسج تظهر في سطر واحد بدي لو كانت اكثر من سطر تنعرض مثل ما هي مش كلها في سطر واحد
        textView . setSingleLine ( false ) ;

        // هون غيرنا لون الخط للتكست
        textView . setTextColor ( ContextCompat . getColor (this ,R . color . white ) ) ;

        // هون غيرنا حجم الخط
        textView . setTextSize ( 15 ) ;

        // هون غيرنا محاذاة النص و خلينا النص يصير في النص بدل ما هو على اليمين
        textView . setTextAlignment ( View . TEXT_ALIGNMENT_CENTER ) ;

        /*
            هسه انسي هاد السطر ( ) snackbar . show الي هو اخر سطر في الفنكشن الي بهمنا ال 3 الي قبله و الي هم مش موجودين في نفس الفنكشن في كلاس ال Map

            هدول ال 3 اسطر انا مستعملهم عشان احدد بعد ال snackbar عن اسفل الشاشه ولحتى تفهمي شو بحكي امسحي اول 3 اسطر من تحت و خلي اخر سطر الي هو
            هاد ( ) snackbar . show رح تفهمي شو بعملو هدول ال 3 اسطر
        */

        /*
            في هاد السطر الي تحت بعرف اوبجكت اسمه marginLayoutParams من الكلاس MarginLayoutParams

            و كلاس ال MarginLayoutParams هي عبارة عن كلاس موجوده داخل كلاس ال ViewGroup

            و وظيفة كلاس ال MarginLayoutParams هي انها تتعامل مع ال margin الخاصين بالاداة

            ولما قلت اله هيك ( ) snackbarView . getLayoutParams انا هيك باستعمال هاد الفنكشن  getLayoutParams

            قدرت اوصل ال width و ال height و ال margin الخاصين بال snackbar واخدت منهم ال margin وحطيتها في هاد marginLayoutParams الاوبجكت
        */
        ViewGroup. MarginLayoutParams marginLayoutParams = ( ViewGroup.MarginLayoutParams ) snackbarView . getLayoutParams ( ) ;

        /*
            في هاد السطر احنا بنحدد بعد ال Snackbar عن اسفل الشاشة بقيمة 65

            والقيم هاي marginLayoutParams . leftMargin و هاي marginLayoutParams . rightMargin ة هاي marginLayoutParams  . topMargin

            هم القيم الاصليه لبعد ال snackbar عن الحفه اليمين و اليسار و العلويه للشاشة
        */
        marginLayoutParams . setMargins
        (
            marginLayoutParams  . leftMargin  ,
            marginLayoutParams  . topMargin   ,
            marginLayoutParams . rightMargin ,
            65
        ) ;

        // وفي هاد السطر احنا بنعطي ال Snackbar فيم ال margin الجديده
        snackbarView . setLayoutParams ( marginLayoutParams ) ;

        // و هاد السطر الي بعمله هو انه بظهر الي ال snackbar
        snackbar . show ( ) ;

    }

    // هاد الفنكشن الي بجيب داتا المستخدم بس يعمل تسجيل دخول
    private void Get_Data ( )
    {
        // هون عندي list مخزن فيها اسماء ال collections الي فيها بيانات الدكاتره و الامهات عشان باستعمال ال for each الي تحت الف عليهم لحتى ادور على الدكيومنت تبع المستخدم
        List < String > collection_Names = Arrays  . asList ( "Doctors" , "Mothers" );

        for ( String collection_Name : collection_Names )
        {
            // هون اول شي بقله دور الي على ال collection الي فيها ال document تبع المستخدم
            FirebaseFirestore . getInstance ( )
            .collection (collection_Name )
            .get ( ) . addOnCompleteListener (task ->
            {
                // هون اذا كانت ال collection الي بدور عليها موجوده بقله خش ابحث عن document المستخدم فيها
                if ( task . isSuccessful ( ) )
                {
                    FirebaseFirestore . getInstance ( )
                    .collection (collection_Name )
                    .document   (email )
                    .get ( ) . addOnSuccessListener (documentSnapshot ->
                    {
                        if ( documentSnapshot . exists ( ) )
                        {
                            // هون اذا كان المستخدم طبيب او طبيبه بخلي التطبيق يوديه على قسم الدكتور
                            if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيب" ) || documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "طبيبة" ) )
                            {
                                // هون بوجهو لشاشة الدكتور
                                Intent intent = new Intent (Sing_in_Activity . this ,Doctor_Data_Activity . class ) ;

                                // هون بفهم شاشة الدكتور اني جاي من شاشة تسجيل الدخول
                                intent . putExtra ("action" ,"signing" ) ;

                                // هون بخفي الاشعار الي بحكي يرجى الانتظار
                                progressDialog . dismiss ( ) ;

                                // هون بنتقل لشاشة الدكتور
                                startActivity (intent ) ;
                                finish ( ) ;
                            }

                            // هون اذا كان المستخدم طبيب او طبيبه بخلي التطبيق يوديه على قسم الام
                            if ( documentSnapshot . get ( "صفة المستخدم" ) . toString ( ) . equals ( "أم" ) )
                            {
                                // هون بوجهو لشاشة الام
                                Intent intent = new Intent (Sing_in_Activity . this ,Mother_Activity . class ) ;

                                // هون بفهم شاشة الام اني جاي من شاشة تسجيل الدخول
                                intent . putExtra ("action" ,"signing" ) ;

                                // هون بخفي الاشعار الي بحكي يرجى الانتظار
                                progressDialog . dismiss ( ) ;

                                // هون بنتقل لشاشة الام
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