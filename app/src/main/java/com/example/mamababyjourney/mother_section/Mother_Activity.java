package com.example.mamababyjourney.mother_section;

import com.example.mamababyjourney.First_Screen;
import com.example.mamababyjourney.databinding.ActivityMotherSectionMotherActivityBinding;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mamababyjourney.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.os.Bundle;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings ( { "DataFlowIssue" , "SpellCheckingInspection" , "RedundantSuppression" , "ConstantConditions" , "deprecation" } )
@SuppressLint ( { "InflateParams" , "SetTextI18n" } )
public class Mother_Activity extends AppCompatActivity
{
    // هاد المتغير انا مستعمله عشان اعمل action bar مخصص للشاشات بدل ال action bar الي بحطه النظام
    private AppBarConfiguration mAppBarConfiguration ;

    private ActivityMotherSectionMotherActivityBinding binding ;

    String image_url  ;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super . onCreate (savedInstanceState ) ;
        getWindow ( ) . setFlags (WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ,WindowManager . LayoutParams . FLAG_LAYOUT_NO_LIMITS ) ;

        binding = ActivityMotherSectionMotherActivityBinding . inflate (getLayoutInflater ( ) ) ;

        setContentView ( binding . getRoot ( ) ) ;

        Mother_section_Initialization ( ) ;

        Get_Mother_Data ( ) ;
    }


    // هاد الفنكشن وظفيته يجهز الي الشاشة عشان اعرض فيها ال fragment الخاصين بقسم الام
    private void Mother_section_Initialization ( )
    {

        setSupportActionBar ( binding . MotherActivityAppBar . toolbar ) ;

        // شايفه هاد الكود الي تحت كله هاد عشان اجهز ال action bar و احدد الشاشات الي اسمائها موجوده في ال Navigation Drawer لحتى بس اضغط على اسمهم ننتقل الهم

        mAppBarConfiguration = new AppBarConfiguration .
        Builder (R . id . Mother , R . id . Doctors_And_Clinics , R . id . Advices , R . id . Dates , R . id . Kids )
        . setOpenableLayout ( binding . drawerLayout ) . build ( ) ;

        // هاد الكود الي تحت بجهز الي المتحكم الي وظفيته ينقلني على الشاشة الي بضغط على اسمها في ال Navigation Drawer والكود الي قوق هو الي بجهز ال action bar عشان بس اضغط على اسم وحده من الشاشات الي في ال Navigation Drawer ينعرض اسمها في ال action bar
        NavController navController = Navigation . findNavController (this ,R . id . mother_activity_nav_host ) ;
        NavigationUI . setupActionBarWithNavController (this ,navController ,mAppBarConfiguration ) ;
        NavigationUI . setupWithNavController (binding . navView ,navController ) ;

        Menu menu = binding . navView . getMenu ( ) ;
        menu . findItem (R . id . nav_logout ) . setOnMenuItemClickListener ( menuItem ->
        {
            FirebaseAuth . getInstance ( ) . signOut ( ) ;

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder ( GoogleSignInOptions.DEFAULT_SIGN_IN ).requestEmail ( ).build ( );

            GoogleSignInClient mGoogleApiClient = GoogleSignIn.getClient(this, gso);
            mGoogleApiClient.signOut (  ) ;

            Intent intent = new Intent (this , First_Screen . class ) ;
            startActivity ( intent ) ;
            return true;
        });
    }

    private void Get_Mother_Data ( )
    {
        View childLayout = getLayoutInflater ( ) . inflate (R . layout . layouts_mother_section_mother_activity_nav_header_layout ,null ) ;
        CircleImageView user_Profile_Image = childLayout . findViewById (R . id . User_Profile_Image ) ;
        TextView username_Tv = childLayout . findViewById (R . id . Username_Tv ) ;
        TextView user_Id_Tv = childLayout . findViewById (R . id . User_Id_Tv ) ;

        if ( getIntent ( ) . getExtras ( ) . getString ( "action" ) . equals ( "signup" ) )
        {
            username_Tv . setText ( "الاسم\u0020 : \u0020" + getIntent ( ) . getExtras ( ) . getString ( "name" ) ) ;
            user_Id_Tv  . setText ( "الرقم التعريفي\u0020 : \u0020" + getIntent ( ) . getExtras ( ) . getString ( "user Id" ) ) ;

            if ( getIntent ( ) . getExtras ( ) . getString ( "image form ?" ) . equals ( "uri" ) )
                user_Profile_Image . setImageURI ( getIntent ( ) . getData ( ) );
            else
                user_Profile_Image.setImageResource ( R.drawable.images_female_user_image );

            binding . navView . addHeaderView ( childLayout ) ;
        }
        else
        {
            FirebaseFirestore . getInstance ( ) . collection ("Mothers" ) . document (FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ) . get ( ) . addOnCompleteListener ( task ->
            {
                if ( task . isSuccessful ( ) )
                {
                    DocumentSnapshot document = task . getResult ( ) ;

                    if ( document . exists ( ) )
                    {
                        username_Tv . setText ( "الاسم\u0020 : \u0020" + document . get ( "name" ) ) ;
                        user_Id_Tv  . setText ( "الرقم التعريفي\u0020 : \u0020" + document . get ( "mother Id" ) ) ;
                        image_url = document . get ( "image Url" ) + "" ;


                            if ( image_url.isEmpty ( ) || image_url.equals ( null ) )
                                user_Profile_Image.setImageResource ( R.drawable.images_female_user_image );
                            else
                                FirebaseStorage.getInstance ( ).getReferenceFromUrl ( image_url ).getDownloadUrl ( ).addOnSuccessListener ( uri -> Picasso.get ( ).load ( uri ).into ( user_Profile_Image ) );

                        binding . navView . addHeaderView ( childLayout ) ;
                    }
                }
            });
        }
    }

    // هاد الفنكشن حاولت افهم من chat GPT متى بتنفذ و شو بعمل الكود الي جواته ما فهمت و لا عرفت لهيك انسيكي منه
    @Override
    public boolean onSupportNavigateUp ( )
    {
        NavController navController = Navigation . findNavController (this ,R . id . mother_activity_nav_host ) ;
        return NavigationUI . navigateUp (navController ,mAppBarConfiguration ) || super . onSupportNavigateUp ( ) ;
    }
}