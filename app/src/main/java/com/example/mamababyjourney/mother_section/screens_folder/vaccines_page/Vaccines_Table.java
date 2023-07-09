package com.example.mamababyjourney.mother_section.screens_folder.vaccines_page;

import com.example.mamababyjourney.databinding.FragmentMotherSectionVaccinesPageVaccinesTableFragmentBinding;
import com.example.mamababyjourney.mother_section.screens_folder.kids_page.Child_Activity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import java.util.HashMap;

@SuppressWarnings ( { "unchecked" , "ConstantConditions" } )
@SuppressLint ( "SetTextI18n" )
public class Vaccines_Table extends Fragment
{
    private FragmentMotherSectionVaccinesPageVaccinesTableFragmentBinding binding ;

    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        binding = FragmentMotherSectionVaccinesPageVaccinesTableFragmentBinding . inflate (inflater ,container ,false ) ;
        Get_Child_Vaccines ( ) ;
        return binding . getRoot ( ) ;
    }

    // هاد الفنكشن الي بجيب الي تواريخ جرعات مطاعيم الطفل
    private void Get_Child_Vaccines ( )
    {
        String email = FirebaseAuth . getInstance ( ) . getCurrentUser ( ) . getEmail ( ) ;

        FirebaseFirestore . getInstance ( )
        .collection ("Mothers/" + email + "/Children's" )
        .document ( Child_Activity . name ) . get ( ) . addOnCompleteListener ( task ->
        {
            DocumentSnapshot document = task . getResult ( ) ;
            if ( document . exists ( ) )
            {
                HashMap < String , Object > vaccines = ( HashMap < String, Object > ) document . get ( "Child vaccines" ) ;

                binding . TuberculosisVaccineTv . setText ( vaccines . get ( "مطعوم السل" ) + "" );

                // ؛map مطعوم شلل الاطفال العضلي
                HashMap < String , Object > intramuscular_Polio_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم شلل الاطفال العضلي" );
                binding . IntramuscularPolioVaccine1Tv . setText ( intramuscular_Polio_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . IntramuscularPolioVaccine2Tv . setText ( intramuscular_Polio_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;
                binding . IntramuscularPolioVaccine3Tv . setText ( intramuscular_Polio_Vaccine_Map . get ( "الجرعه الثالثة" ) + "" ) ;

                // ؛map مطعوم شلل الاطفال الفموي
                HashMap < String , Object > oral_Polio_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم شلل الاطفال الفموي" );
                binding . OralPolioVaccine1Tv . setText ( oral_Polio_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . OralPolioVaccine2Tv . setText ( oral_Polio_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;
                binding . OralPolioVaccine3Tv . setText ( oral_Polio_Vaccine_Map . get ( "الجرعه الثالثة" ) + "" ) ;
                binding . OralPolioVaccine4Tv . setText ( oral_Polio_Vaccine_Map . get ( "الجرعه المدعمة" ) + "" ) ;

                // ؛map مطعوم الخانوق والسعال الديكي و الكزاز
                HashMap < String , Object > diphtheria_Whooping_Cough_And_Tetanus_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم الخانوق والسعال الديكي و الكزاز" );
                binding . DiphtheriaWhoopingCoughAndTetanusVaccine1Tv . setText ( diphtheria_Whooping_Cough_And_Tetanus_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . DiphtheriaWhoopingCoughAndTetanusVaccine2Tv . setText ( diphtheria_Whooping_Cough_And_Tetanus_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;
                binding . DiphtheriaWhoopingCoughAndTetanusVaccine3Tv . setText ( diphtheria_Whooping_Cough_And_Tetanus_Vaccine_Map . get ( "الجرعه الثالثة" ) + "" ) ;
                binding . DiphtheriaWhoopingCoughAndTetanusVaccine4Tv . setText ( diphtheria_Whooping_Cough_And_Tetanus_Vaccine_Map . get ( "الجرعه المدعمة" ) + "" ) ;

                // ؛map مطعوم المستدمية النزليه ( السحايا )
                HashMap < String , Object > haemophilus_Influenzae_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم المستدمية النزليه ( السحايا )" );
                binding . HaemophilusInfluenzaeVaccine1Tv . setText ( haemophilus_Influenzae_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . HaemophilusInfluenzaeVaccine2Tv . setText ( haemophilus_Influenzae_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;
                binding . HaemophilusInfluenzaeVaccine3Tv . setText ( haemophilus_Influenzae_Vaccine_Map . get ( "الجرعه الثالثة" ) + "" ) ;

                // ؛map مطعوم التهاب الكبد الوبائي ( Hepatitis B )
                HashMap < String , Object > hepatitis_B_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم التهاب الكبد الوبائي ( Hepatitis B )" );
                binding . HepatitisBVaccine1Tv . setText ( hepatitis_B_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . HepatitisBVaccine2Tv . setText ( hepatitis_B_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;
                binding . HepatitisBVaccine3Tv . setText ( hepatitis_B_Vaccine_Map . get ( "الجرعه الثالثة" ) + "" ) ;

                // ؛map مطعوم الحصبه
                HashMap < String , Object > measles_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم الحصبه" );
                binding . MeaslesVaccine1Tv . setText ( measles_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . MeaslesVaccine2Tv . setText ( measles_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;

                // ؛map مطعوم الحصبه الالمانيه و النكاف MMR
                HashMap < String , Object > german_Measles_And_Mumps_MMR_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم الحصبه الالمانيه و النكاف MMR" );
                binding . GermanMeaslesAndMumpsMMRVaccine1Tv . setText ( german_Measles_And_Mumps_MMR_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . GermanMeaslesAndMumpsMMRVaccine2Tv . setText ( german_Measles_And_Mumps_MMR_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;

                // ؛map مطعوم روتا فيروس
                HashMap < String , Object > rotavirus_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "مطعوم روتا فيروس" );
                binding . RotavirusVaccine1Tv . setText ( rotavirus_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . RotavirusVaccine2Tv . setText ( rotavirus_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;
                binding . RotavirusVaccine3Tv . setText ( rotavirus_Vaccine_Map . get ( "الجرعه الثالثة" ) + "" ) ;

                // ؛map المطعوم السداسي hexaxim
                HashMap < String , Object > the_Hexagonal_Vaccine_Map = ( HashMap < String , Object > ) vaccines . get ( "hexaxim المطعوم السداسي" );
                binding . TheHexagonalVaccine1Tv . setText ( the_Hexagonal_Vaccine_Map . get ( "الجرعه الاولى"   ) + "" ) ;
                binding . TheHexagonalVaccine2Tv . setText ( the_Hexagonal_Vaccine_Map . get ( "الجرعه الثانية" ) + "" ) ;
            }
        });
    }
}