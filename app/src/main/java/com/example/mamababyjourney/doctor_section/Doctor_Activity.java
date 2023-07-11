package com.example.mamababyjourney.doctor_section;

import com.example.mamababyjourney.databinding.ActivityDoctorSectionDoctorActivityBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.example.mamababyjourney.Splash_Activity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.mamababyjourney.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings ( "ConstantConditions" )
@SuppressLint ( { "InflateParams" , "SetTextI18n" } )

public class Doctor_Activity extends AppCompatActivity
{
    private ActivityDoctorSectionDoctorActivityBinding binding ;

    // هاد المتغير انا مستعمله عشان اعمل action bar مخصص للشاشات بدل ال action bar الي بحطه النظام
    private AppBarConfiguration mAppBarConfiguration ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );


        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityDoctorSectionDoctorActivityBinding . inflate ( getLayoutInflater ( ) ) ;
        setContentView ( binding . getRoot ( ) );

        Mother_section_Initialization ( ) ;
    }


    // هاد الفنكشن وظفيته يجهز الي الشاشة عشان اعرض فيها ال fragment الخاصين بقسم الام
    private void Mother_section_Initialization ( )
    {
        setSupportActionBar ( binding . DoctorActivityAppBar . DoctorToolbar ) ;

        // شايفه هاد الكود الي تحت كله هاد عشان اجهز ال action bar و احدد الشاشات الي اسمائها موجوده في ال Navigation Drawer لحتى بس اضغط على اسمهم ننتقل الهم

        mAppBarConfiguration = new AppBarConfiguration . Builder (R . id . Today_Appointments , R . id . customers  )
        . setOpenableLayout ( binding . DoctorDrawerLayout ) . build ( ) ;

        // هاد الكود الي تحت بجهز الي المتحكم الي وظفيته ينقلني على الشاشة الي بضغط على اسمها في ال Navigation Drawer والكود الي قوق هو الي بجهز ال action bar عشان بس اضغط على اسم وحده من الشاشات الي في ال Navigation Drawer ينعرض اسمها في ال action bar
        NavController navController = Navigation. findNavController (this ,R . id . doctor_activity_nav_host ) ;
        NavigationUI. setupActionBarWithNavController (this ,navController ,mAppBarConfiguration ) ;
        NavigationUI . setupWithNavController ( binding . DoctorNavView ,navController ) ;

        Menu menu = binding . DoctorNavView . getMenu ( ) ;
        menu . findItem (R . id . Doctor_Sign_Out_BTN ) . setOnMenuItemClickListener ( menuItem ->
        {
            FirebaseAuth. getInstance ( ) . signOut ( ) ;

            GoogleSignInOptions gso = new GoogleSignInOptions . Builder (GoogleSignInOptions . DEFAULT_SIGN_IN ) . requestEmail ( ) . build ( ) ;

            GoogleSignInClient mGoogleApiClient = GoogleSignIn. getClient(this,gso) ;
            mGoogleApiClient . signOut (  ) ;

            Intent intent = new Intent (this , Splash_Activity. class ) ;
            startActivity (intent ) ;
            return true ;
        });
    }

    // هاد الفنكشن حاولت افهم من chat GPT متى بتنفذ و شو بعمل الكود الي جواته ما فهمت و لا عرفت لهيك انسيكي منه
    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation . findNavController (this ,R . id . doctor_activity_nav_host ) ;
        return NavigationUI . navigateUp (navController ,mAppBarConfiguration ) || super . onSupportNavigateUp ( ) ;
    }

    // هاد الفنكشن الي بجيب الي بيانات الام من الفايرستور
    private void Get_Doctor_Data ( )
    {
        // هون انا بجيب الهيدر تاع ال negation drawer عشان اعدل على قيم مكوناته
        View childLayout = getLayoutInflater ( ) . inflate (R . layout . layouts_mother_section_mother_activity_nav_header_layout ,null ) ;
        CircleImageView user_Profile_Image = childLayout . findViewById (R . id . User_Profile_Image ) ;

        TextView User_Email_Tv = childLayout . findViewById (R . id . User_Email_Tv ) ;
        TextView username_Tv   = childLayout . findViewById (R . id . Username_Tv ) ;

            // هسه هون انا بروح على الفايرستور و بجيب المعلومات الي في document الام وبستعملها
            FirebaseFirestore. getInstance ( )
            .collection ("Mothers" )
            .document   (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) )
            .get ( ) . addOnCompleteListener ( task ->
            {
                if ( task . isSuccessful ( ) )
                {
                    DocumentSnapshot document = task . getResult ( ) ;

                    if ( document . exists ( ) )
                    {
                        // هون انا بجيب القيم الي اجتني من الفايرستور و بحطها في مكونات الشاشه
                        username_Tv   . setText ( "الاسم\u0020 : \u0020" + document . get ( "name" ) ) ;
                        User_Email_Tv . setText ( document . get ( "email" ) + "" ) ;

                        // هون بخزن في هاد image_url المتخير قيمته الي جبتها من الفايرستور والي هو رابط الصوره الي اختارها المستخدم من الاستديو
                        String image_url = document.get ( "image Url" ) + "";

                        // هون بشيك اذا كان هاد image_url المتغير فاضي يعني ما تم اختيار صوره من الاستديو وقتها بنفذ الي جوا الاف عير هيك رح ينفذ الي جوا ال else
                        if ( image_url . isEmpty ( ) || image_url . equals ( null )  )
                        {
                    /*
                       هون بشيك اذا كانت الطريقه الي انعمل فيها الحساب في التطبيق باستخدام قوقل بقله جيب الي صورة الايميل

                        الي تم استخدامه لعمل الحساب في التطبيق و حطه كصور للمستخدم غير هيك حط الصوره الافتراضيه
                    */
                            if ( document . get ( "signUp Method" ) . toString ( ) . equals ( "Sing Up By Google" ) )
                                Picasso . get ( ) . load (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getPhotoUrl ( ) ) . into ( user_Profile_Image ) ;
                            else
                            {
                                if ( document . get ( "صفة المستخدم" ) . equals ( "'طبيب" ) )
                                    user_Profile_Image . setImageResource ( R . drawable . images_male_user_image ) ;
                                else
                                    user_Profile_Image . setImageResource ( R . drawable . images_female_user_image ) ;

                            }
                        }
                        else
                            // هون اذا ما كان هاد image_url المتغير فاضي يعني مخزن فيه رابط الصوره الي تم اختياره من الاستديو عند انشاء الحساب بقله جيبها من الفايرستورج و حطها كصوره للسمتخدم
                            FirebaseStorage. getInstance ( ) . getReferenceFromUrl ( image_url ) . getDownloadUrl ( ) . addOnSuccessListener ( uri -> Picasso . get ( ) . load ( uri ) . into ( user_Profile_Image ) ) ;

                        binding . DoctorNavView . addHeaderView ( childLayout ) ;
                    }
                }
            });

    }

    @Override
    public void onBackPressed ( )
    {

    }

}