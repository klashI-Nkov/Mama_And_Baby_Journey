package com.example.mamababyjourney.Create_An_Account_And_Sign_In;

import com.example.mamababyjourney.R;
import com.example.mamababyjourney.databinding.ActivityMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.snackbar.Snackbar;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;

@SuppressWarnings ( { "ConstantConditions" , "CommentedOutCode" , "SpellCheckingInspection" , "deprecation" } )
@SuppressLint ( "MissingPermission" )

public class Map extends FragmentActivity implements OnMapReadyCallback
{

    // اول شي المتغيرات الي مش حاط الها شرح في كومت بتلاقي شرحها في الفنكشن الي انا مستعملها فيه و بكون عامل هيك لانه شرحها بكون طويل شوي

    private ConnectivityManager connectivity_Manager;

    private ConnectivityManager.NetworkCallback network_Callback;

    private BroadcastReceiver location_Receiver = null;

    private LocationManager location_Manager;

    // هاد المتغير وظيفة انه يكون true في حالة كان النت شغال و false في حالة كان طافي
    private boolean is_Internet_Connected;

    // هاد المتغير وظيفة انه يكون true في حالة كان الموقع شغال و false في حالة كان طافي
    boolean is_Location_Enabled;

    private ActivityMapBinding binding;

    private GoogleMap mMap;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {


        super.onCreate ( savedInstanceState );
        binding = ActivityMapBinding.inflate ( getLayoutInflater ( ) );
        setContentView ( binding.getRoot ( ) );

        getWindow ( ).setStatusBarColor ( Color.TRANSPARENT );

        Check_Location ( );
        Check_Internet ( );

        Map_Initialization ( );


        binding.SearchEditText.setOnQueryTextListener ( new SearchView.OnQueryTextListener ( )
        {
            @Override
            public boolean onQueryTextSubmit ( String query )
            {

                Find_a_place ( );

                return false;
            }

            @Override
            public boolean onQueryTextChange ( String newText )
            {
                return false;
            }

        } );
    }

    @Override
    public void onMapReady ( @NonNull GoogleMap googleMap )
    {
        mMap = googleMap;

        mMap.setMapType ( GoogleMap.MAP_TYPE_HYBRID );


        mMap.setMyLocationEnabled ( true );

        mMap.getUiSettings ( ).setZoomControlsEnabled ( true );
        mMap.setBuildingsEnabled ( true );

        // for find my location automatically when activity start

        //LatLng sydney = new LatLng ( currentLocation.getLatitude ( ) , currentLocation.getLongitude ( ) );
        //mMap.addMarker ( new MarkerOptions ( ).position ( sydney ).title ( "Marker in Sydney" ) );
        //mMap.moveCamera ( CameraUpdateFactory.newLatLngZoom ( sydney,18 ) );

        mMap.setOnMapClickListener ( latLng -> mMap.clear ( ) );

        mMap.setOnMapLongClickListener ( latLng ->
        {
            mMap.clear ( );
            LatLng sydney = new LatLng ( latLng.latitude , latLng.longitude );
            mMap.addMarker ( new MarkerOptions ( ).position ( sydney ).title ( "Marker in Sydney" ) );
            mMap.moveCamera ( CameraUpdateFactory.newLatLng ( latLng ) );
        } );

    }


    // هاد الفنشكن مجرد ما تطلعي من الخارطه رح يتنفذ وما رح تفهمي الي جواته حتى لو قريتي الكومنتات لازم تشوقي الشرح الي في فنشكن ال Check_Location و الي في فنكشن ال Check_Internet عشان تفهمي شو الي جواته
    @Override
    protected void onPause ( )
    {
        super.onPause ( );
        connectivity_Manager.unregisterNetworkCallback ( network_Callback );
        unregisterReceiver ( location_Receiver );
    }

    // هاد الفنكشن هو الي بس تشتغل الخارطه بجهزها للعرض و مستعمله في فنكشن onCreate الي بستدعى او ما تفتحي الشاشه فرح يستدعى لما تفتحي الخارطه
    private void Map_Initialization ( )
    {
        // inti
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( ).findFragmentById ( R.id.map );
        assert mapFragment != null;
        mapFragment.getMapAsync ( this );
    }

    // هاد الفنكشن هو المسؤول عن البحث عن الموقع باستخدام الاسم لما نكتبه في البحث و برضو لاني مستعمله في فنكشن onCreate و رح يستدعى لما تفتحي الخارطه
    @SuppressLint ( "StaticFieldLeak" )
    private void Find_a_place ( )
    {

        /*String location = binding.SearchEditText.getQuery ( ).toString ( );

        AtomicReference < List < Address > > listAddress = new AtomicReference < > ( );

        if ( !location.isEmpty ( ) )
        {
                System.setProperty("android.location.Geocoder.SEARCH_TIME_OUT", "20000");
                Geocoder geocoder = new Geocoder ( Map.this );


                try
                {
                    listAddress.set ( geocoder.getFromLocationName ( location , 1  ) );

                    if ( !listAddress.get ( ).isEmpty ( ) )
                    {
                        Address address = listAddress.get ( ).get ( 0 );

                        LatLng latlng = new LatLng ( address.getLatitude ( ) , address.getLongitude ( ) );
                        mMap.animateCamera ( CameraUpdateFactory.newLatLngZoom ( latlng , 18 ) );
                        mMap.addMarker ( new MarkerOptions ( ).position ( latlng ).title ( "Marker in Sydney" ) );
                    }
                    else
                    { Snack_Bar ( "لا يوجد مكان بهذا الاسم\n\n جرب كتابة اسم مكان اخر" ); }
                }
                catch ( IOException e )
                {
                    e.printStackTrace ( );
                    Snack_Bar ( "Search failed: " + e.getMessage ( ) );

                }
            }*/
        String location = binding.SearchEditText.getQuery().toString();

        if ( !location.isEmpty ( ) )
        {
            new AsyncTask < String, Void, List < Address > > ( )
            {
                @Override
                protected List < Address > doInBackground ( String... strings )
                {
                    try
                    {
                        Geocoder geocoder = new Geocoder ( Map.this );
                        return geocoder.getFromLocationName ( strings[ 0 ] , 1 );
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace ( );
                        return null;
                    }
                }





                @Override
                protected void onPostExecute ( List < Address > listAddress )
                {
                    if ( listAddress != null && !listAddress.isEmpty ( ) )
                    {
                        Address address = listAddress.get ( 0 );
                        LatLng latlng = new LatLng ( address.getLatitude ( ) , address.getLongitude ( ) );
                        mMap.animateCamera ( CameraUpdateFactory.newLatLngZoom ( latlng , 18 ) );
                        mMap.addMarker ( new MarkerOptions ( ).position ( latlng ).title ( "Marker in Sydney" ) );
                    }
                    else
                    {
                        Snack_Bar ( "There is no place with this name\n\nTry typing another place name" );
                    }
                }
            }.execute ( location );
        }

    }

    // للتحقق اذا كان النت شغال او مش شغال و برضو لاني مستعمله في فنكشن onCreate و رح يستدعى لما تفتحي الخارطه
    private void Check_Internet ( )
    {

        // ال ConnectivityManager من اسمها يعني مدير الاتصال و هي عبارة عن كلاس مسؤول عن كل شي بتعلق بالانترنت و الاتصال بالشبكه في التلفون

        // ال CONNECTIVITY_SERVICE ببساطه هي عباره عن خدمه بتوفر معلومات عن حالة الاتصال بالشبكه الحالية و هي جزء من كلاس  ConnectivityManager
        /*
             هون في السطر الي تحت بروح بعمل اوبجكت من  CONNECTIVITY_SERVICE من خلال فنكشن getSystemService و بحولها ل ConnectivityManager من خلال هاد ( ConnectivityManager ) عشان المتغير من نوع ConnectivityManager فلازم احوله ل ConnectivityManager عشان يجيب معلومات عن حالة الانترنت الحاليه لانه مثل ما قلنا فوق انه ال CONNECTIVITY_SERVICE بتوفر معلومات عن الحاله الحاليه للاتصال بالشبكه عشان يقدر من خلالها يحدد هل متصل بالنت او لا
         */
        connectivity_Manager = ( ConnectivityManager ) getSystemService ( Context.CONNECTIVITY_SERVICE );

        // في هاد السطر الي تحت هاد networkCallback  هو المسؤول عن مراقبة التغيير في حالة الاتصال وعدم الاتصال بالانترنت وهو الي بتصرف وقتها بشو الي رح يصير
        /*
            ال NetworkCallback هي عباره عن كلاس موجوده داخل كلاس ConnectivityManager لهيك تحت حاكي اله ConnectivityManager.NetworkCallback في هاد السطر
            network_Callback = new ConnectivityManager.NetworkCallback ( )

            لما اعطيت قيمه لهاد المتغير network_Callback
            وهو المسؤول انه يتصرف بشو الي بده يصير لما يطفي او يشتغل النت
        */
        network_Callback = new ConnectivityManager.NetworkCallback ( )
        {

            // هاي الفنكشن بتستدعى اول ما يشتغل النت
            @Override
            public void onAvailable ( Network network )
            {
                /*
                     هون اول ما يشتغل النت بخلي قيمة المتغير  is_Internet_Connected تساوي true لانها كانت false بس كان النت طافي و بعدها بستدعي Check_Location_And_Internet لحتى يتحقق اذا كان النت طافي او شغال ويحدث الشاشة بناء على حالة النت مطفي او شغال
                 */
                is_Internet_Connected = true;
                Check_Location_And_Internet ( );

            }

            // هاي الفنكشن بستدعى اول ما يرجع النت يشتغل
            @Override
            public void onLost ( Network network )
            {
                /*
                     هون اول ما يفصل النت بخلي قيمة المتغير  is_Internet_Connected تساوي false لانها كانت false بس كان النت شغال و بعدها بستدعي Check_Location_And_Internet لحتى يتحقق اذا كان النت طافي او شغال ويحدث الشاشة بناء على حالة النت مطفي او شغال
                 */
                is_Internet_Connected = false;
                Check_Location_And_Internet ( );

            }
        };

        /*
         في هاد السطر
          connectivity_Manager.registerDefaultNetworkCallback ( network_Callback );

          تسجيل ل connectivity_Manager عشان يقدر يتحقق من النت اذا كان شغال او طافي عن طريق انه يستقبل التغيير في حالة الاتصال بالانترنت و وقتها بجي دور هاد الي فوق الي هو هاد

           network_Callback = new ConnectivityManager.NetworkCallback ( )

            وبعدل على قيمة is_Internet_Connected حسب حالة الاتصال بالانترنت
         */
        connectivity_Manager.registerDefaultNetworkCallback ( network_Callback );

        /*
            ال NetworkInfo هي كلاس الي هي بالزبط بتعطيني الصافي انه النت متصل او فاصل
            وفي السطر التحت حاكي اله باستعمال الفنكشن  getActiveNetworkInfo الي هي داخل الكلاس  ConnectivityManager استعملها مع المتغير الي اسمه connectivity_Manager لانه فوق اخذ معلومات عن الحاله الحالية للاتصال بالانترنت لما قلنا في هاد االحكي
            connectivity_Manager = ( ConnectivityManager ) getSystemService ( Context.CONNECTIVITY_SERVICE );

            فلهيك خليته يروح لهاد المتغير connectivity_Manager و يشوف الي شو وضع النت الحالي
            ويخزنه في هاد المتغير active_Network_Info
         */
        NetworkInfo active_Network_Info = connectivity_Manager.getActiveNetworkInfo ( );

        /*

            ليه انا معطي هاد المتغير is_Internet_Connected هون بالزبط

            بسبب انه network_Callback ما بشتغل شغله اول ما تفتحي الخارطه واول ما تفتح الخارطه القيمه الافتراضيه للهاد المتغير network_Callback بتكون تساوي false فلو استدعينا هذا الفنكشن  Check_Location_And_Internet و اجى شيك هل شغال ولا لا رح يلاقيه مش شغال حتى لو انه شغال بسبب ان قيمة هاد is_Internet_Connected بتكون false اول ما تشتغل الشاشه لهيك انا لازم احطه قبل ما استدعي هاد الفنكشن Check_Location_And_Internet
         */
        is_Internet_Connected = active_Network_Info != null && active_Network_Info.isConnected ( );
        Check_Location_And_Internet ( );
    }

    // للتحقق اذا كان الموقع شغال او مش شغال وبرضو لاني مستعمله في فنكشن onCreate و رح يستدعى لما تفتحي الخارطه
    private void Check_Location ( )
    {

        /*
            ملاحظه صغيره بس هون قبل ما تبلشي تقري كومنتات الشرح الي جوا الفنكشن
            ال BroadcastReceiver و ال LocationManager طريقة شغلهم تقريبا مشابهه
            لطريقة شغل ال ConnectivityManager و ال NetworkCallback
         */

        /*
            هسه شو هو ال LocationManager شو بعمل شو بساوي بالمختصر مثل ما حكالي chat gpt هيك حالكي
            يعد LocationManager خدمة نظام توفر الوصول إلى الخدمات المتعلقة بالموقع ، مثل GPS والموقع المستند إلى الشبكة.

            ولحتى توضح الصوره بسمح للتطبيق بالحصول على الموقع الحالي للجهاز و تتبع التغيرات في الموقع ( هون بقصد مكان الشخص مش خدمة الموقع ) و طبعا هاد مستحيل يشتغل اذا ما اعطينا permission الموقع في ملف ال  AndroidManifest وبعدها طلبنا من المستخدم انه يمنح صلاحية الموقع للتطبيق غير هيك ما رح يشتغل ابدا ولا رح يستفيد التطبيق منه
         */
        location_Manager = ( LocationManager ) getSystemService ( Context.LOCATION_SERVICE );

        // هون عملنا اوبحكت اعطينا قيمه للاوجكت location_Receiver الي عرفناه فوق قبل فنكشن onCreate
        location_Receiver = new BroadcastReceiver ( )
        {
            // هاض الفنشكن بستدعى لما ال location_Receiver يستقبل BroadcastReceiver
            @Override
            public void onReceive ( Context context , Intent intent )
            {
                // هون هاد المتغير الي برضو عرفناه قبل onCreate بتخزن فيه الحاله الحاليه لخدمة الموقع اذا كانت مشغله بتكون قيمته true و اذا كانت طافيه بتكون قيمته false
                /*
                    ال GPS_PROVIDER هو عباره عن خدمه موفرها نظام الاندرويد و وظيفته يوفر معلومات عن الموقع باستعمال خدمة ال GPS في التلفون
                 */
                is_Location_Enabled = location_Manager.isProviderEnabled ( LocationManager.GPS_PROVIDER );

                // هون شو ما كانت قيمة is_Location_Enabled كانت true او كانت false هاد الي تحت رح يتنفذ لانو قلنا انه فنكشن ال onReceive بستدعى لما
                Check_Location_And_Internet ( );
            }
        };

        // في هاد السطر بنعرف اوبجكت من الكلاس  IntentFilter و اسمه  filter و وظيفته او لازمته انه يضل متبع مع حالة خدمة الموقع لما تطفي او تشتغل

        /*
            ال PROVIDERS_CHANGED_ACTION ه عبارة عن intent لكن مش intent عادي مثل الي بعمل توجيه بين الصفحات
            هو صح انه intent  لكنه intent من نوع broadcast يعني هو broadcast intent و وظيفته انه

         */
        IntentFilter filter = new IntentFilter ( LocationManager.PROVIDERS_CHANGED_ACTION );

        /*
         في هاد السطر بنعمل تسجيل ل location_Receiver عشان يقدر يتحقق من الموقع اذا كان شغال او طافي عن طريق انه يستقبل التغيير في حالة خدمة الموقع و وقتها بستدعي هاد الفنكشن onReceive فوق وبعدل على قيمة is_Location_Enabled حسب حالة خدمة الموقع
         */
        registerReceiver ( location_Receiver , filter );


        /*
            في السطرالي تحت الي هو هاد
            is_Location_Enabled = location_Manager.isProviderEnabled ( LocationManager.GPS_PROVIDER );

            انا ليه راجع مخزن الحاله الحاليه لخدمة الموقع في is_Location_Enabled
            لانه لو ترجعي للفوق بتلاحظي انه location_Receiver و فنشكن onReceive ما بتنفذو الا في حالة صار تغيير على حالة خدمة الموقع والي كان يصير قبل ما احط هاد السطر انه لما يجي فنكشن Check_Location_And_Internet يشيك على متغير is_Location_Enabled يلاقيه false حتى لو كان الموقع شغال لهيك اضظريت اني احط هاد السطر لحتى ياخد الحاله الحالية لخدمة الموقع انها شغاله لما تفتح الخارطه كونه ال location_Receiver و فنكشن onReceive انا ما بستفيد منهم ابدا ولا بعدلو على قيمة is_Location_Enabled الا بعد ما تفتح الخارطه وخلال استعمال الخارطه بجو بعدلو على قيمة is_Location_Enabled عرفتي هلا ليه معين قيمه مرتين داخل هاد الفنشكن للمتغير is_Location_Enabled

              يعني بالنهايه هون القصه نفس قصة Check_Internet مع ال is_Internet_Connected ومع ال onLost و onAvailable

             الي في فنكشن ال Check_Internet داخل هاد

             networkCallback = new ConnectivityManager.NetworkCallback ( )

            لكن هون الفرق انه في ال is_Internet_Connected ما انجبرت اني احطه داخل هاد

            networkCallback = new ConnectivityManager.NetworkCallback ()

            ممكن لانه BroadcastReceiver و ال LocationManager

            الي في هاد الفنكشن الي هو فنكشن Check_Location طريقة شغلهم بتفرق عن طريقة شغل

             ال ConnectivityManager و ال NetworkCallback الي في فنكشن Check_Internet

            ف ممكن انه هاد السبب الي ما خلاني في فتكشن ال Check_Internet
            انجبر احط هاد is_Internet_Connected داخل هاد

            networkCallback = new ConnectivityManager.NetworkCallback ()

         */
        is_Location_Enabled = location_Manager.isProviderEnabled ( LocationManager.GPS_PROVIDER );

        // هون بس بستدعي الفنكشن المسؤول عن عرض المسج اذا كان النت او الموقع واحد منهم مطفي او الاثنين مطفيين
        Check_Location_And_Internet ( );

    }

    // هاد الفنكشن المسؤول عن عرض المسج اذا كان النت او الموقع واحد منهم مطفي او الاثنين مطفيين وهاد الفنكشن مستعمله في فنكشن Check_Location و فنشكن Check_Internet
    private void Check_Location_And_Internet ( )
    {
        /*
          هاي الفنكشن runOnUiThread مستعمله عشان ما يصير خطا لما يعدل محتوى الشاشه الي احنا فيها حاليا
            والي هي شاشة الخارطة والخطا الي بصير انه اوك لو النت طافي بقبل منك انه يعرض الشاشه الي بتقله
            يغير النت لكن لما يشتغل النت ويجي يرجع يعرض شاشة الخارطه هون رح يعطيكي خطا لانه بحاول يغير
             محتوى شاشة من كلاس الجافا الي مش خاص فيها و العكس برضو صحيح بحيث انه لو كان النت شغال و
              اجى طفاه رح يعطيه خطا فلهيك انا مستعمل هاد الفنكشن واذا بتلاحظي مستعمله في كل الحالات انا
         */

        // هاد الشرط بحكي اذا كان اللوكشن مش مفعل او النت مش مفعل او الاثنين مش مفعلين ادخل جوا الاف غير هيك اعرض الخارطة
        if ( !is_Internet_Connected || !is_Location_Enabled || ( !is_Internet_Connected && !is_Location_Enabled ) )
        {
            // هون اذا كان النت طافي ادخل جوا الاف وجيب الشاشه الي بظهر فيها المسج واعرضها بدل الخارطة و حط فيها هاد المسج
            if ( !is_Internet_Connected )
            {
                runOnUiThread ( ( ) ->
                {
                    // هون بنجيب الشاشة الي فيها المسج و بنعرضها بدل الخارطه باستعمال فنشكن setContentView المسؤول عن تحديد الشاشة الي رح تنعرض للمستخدم
                    setContentView ( R.layout.check_location_and_internet_view );

                    // هون عرفنا اوبجكت من الكلاس  TextView اسمه textView عشان نقدر نحدد النص الي بدنا نعرض للمتستخدم في حالة
                    TextView textView = findViewById ( R.id.textVie );
                    textView.setText ( "لا يوجد انترنت يرجى التحقق من اتصالك بالانترنت و المحاوله مره اخرى" );
                } );
            }

            // هون نفس الشي بس في حالة كان الموقع مطفي
            if ( !is_Location_Enabled )
            {
                runOnUiThread ( ( ) ->
                {
                    setContentView ( R.layout.check_location_and_internet_view );
                    TextView textView = findViewById ( R.id.textVie );
                    textView.setText ( "خدمة الموقع لديك متوقفه يرجى تشغيل خدمة الموقع للمتابعة" );
                } );
            }

            // هون في حالة كان الموقع و النت مطفيين
            if ( !is_Internet_Connected && !is_Location_Enabled )
            {
                runOnUiThread ( ( ) ->
                {
                    setContentView ( R.layout.check_location_and_internet_view );
                    TextView textView = findViewById ( R.id.textVie );
                    textView.setText ( "لا يوجد اتصال بالانترنت و خدمة الموقع لديك متوقفه يرجى التحقق من الاتصال بالانترنت و تشغيل خدمة الموقع للمتابعة" );
                } );
            }
        }
        else
        { runOnUiThread ( ( ) -> setContentView ( binding.getRoot ( ) ) ); }
    }

    // لعرض رسالة في حالة المستخدم بحث عن اسم موقع غير موجود وهاد الفنكشن مستعمله في فنكشن Find_a_place المسؤول عن البحث عن الاماكن من خلال الاسم ولو جيتي حطيتي مؤشر الماوس عليه و ضغطتي ctrl مع كبسه يسار على الماوس رح يوديكي على فنكشن Find_a_place
    private void Snack_Bar ( String Message )
    {
        /*
         هون عرفنا اوبجكت من ال Snackbar

         و من خلال فنكشن ال make بنشء سناك بار وباخد 3 متيغرات
         الاول هو الشاشه او المكان الي رح يظهر فيها

         الثاني هي المسج الي بدي اعرضها للمستخدم وهاي االمج جايه من االمتغير الي اسمه message الي فوق وهاي بعطيها القيمه الي جواتها لما استدعي الفنكشن

         المتغير الثالث هو المده الي رح يضل فيها ظاهر يعني يا اما بظهر لمدة طويلة او لمده قصيره وال LENGTH_LONG يعني مده طويله و LENGTH_SHORT يعني بظهر لفتره قصيرة
        */
        Snackbar snackbar = Snackbar.make ( binding.constraint , Message , Snackbar.LENGTH_LONG );

        // السطر الي تحت عشان اغير خلفية السناك بار
        snackbar.getView ( ).setBackgroundTintList ( ColorStateList.valueOf ( ContextCompat.getColor ( this , R.color.Snack_bar_BG_Color ) ) );

        /*
            هدول السطرين الي تحت عملناهم عشان نقدر نوصل للتكتست تاع السناك بار و عشان نعدل في لون وحجم الخط الخاص فيه

             وكونه السناك بار اداة مثل اي اداة اخرى فيعتبر view فبنعرف اوبجكت من الكلاس view و بعدها بنستعمل الاوبجكت الي عرفناه فوق من كلاس snackbar لحتى تقله getview ونصل ل اداة التكست الي مستعملها ليعرض فيها المسج ونعدل في حجم و لون الخط تبعها
         */
        View snackbarView = snackbar.getView ( );
        TextView textView = snackbarView.findViewById ( com.google.android.material.R.id.snackbar_text );

        // هاد السطر قلنا اله انه ما بدي كل المسج تظهر في سطر واحد بدي لو كانت اكثر من سطر تنعرض مثل ما هي مش كلها في سطر واحد
        textView.setSingleLine ( false );

        // هون غيرنا لون الخط للتكست
        textView.setTextColor ( ContextCompat.getColor ( this , R.color.white ) );

        // هون غيرنا حجم الخط
        textView.setTextSize ( 15 );

        // هون غيرنا محاذاة النص و خلينا النص يصير في النص بدل ما هو على اليمين
        textView.setTextAlignment ( View.TEXT_ALIGNMENT_CENTER );

        // وهاد السطر بشغل السناك بار و بعرض المسج الي بعثناها للفنكشن
        snackbar.show ( );

    }

}





