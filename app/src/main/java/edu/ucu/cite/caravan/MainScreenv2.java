package edu.ucu.cite.caravan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.ucu.cite.caravan.Adapters.PagerAdapter;

public class MainScreenv2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private long backPressedTime;
    private Toast backToast;

    // Drawer Main
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //ViewPager
    ViewPager viewPager;
    TabLayout tabLayoutRents;
    TabItem pendingTab, approveTab, completedTab;
    PagerAdapter pagerAdapter;

    FloatingActionButton fbAddRents;

    //Dialog Rent Widgets and Strings
    Button BTN_next;
    String Location="default_value",SpickupDate,Spickuptime,SreturnDate,Sreturntime;
    Long RentDays;

    private ImageButton pickupDate;
    private ImageButton returnDate;

    //for displaying time
    String displaypickupTime,displaypickupHour,displaypickupMinute,displaypickupAMPM;
    String displayreturnTime,displayreturnHour,displayreturnMinute,displayreturnAMPM;

    //para macheck kung pwede na iclick yung return date
    boolean click=false;


    //dates //Initialize variable
    TextView tv_pickupdate,tv_returndate,tv_pickuptime,tv_returntime;

    //Searchable Spinner
    TextView textView;
    ArrayList<String> arrayList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreenv2);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Initialize array list
        //Add value in array list
        arrayList.add("Agno"); arrayList.add("Aguilar"); arrayList.add("Alaminos"); arrayList.add("Alcala"); arrayList.add("Anda");
        arrayList.add("Asingan"); arrayList.add("Balungao"); arrayList.add("Bani"); arrayList.add("Basista"); arrayList.add("Bautista");
        arrayList.add("Bayambang"); arrayList.add("Binalonan"); arrayList.add("Binmaley"); arrayList.add("Bolinao"); arrayList.add("Bugallon");
        arrayList.add("Burgos"); arrayList.add("Calasiao"); arrayList.add("Dagupan City"); arrayList.add("Dasol"); arrayList.add("Infanta");
        arrayList.add("Labrador"); arrayList.add("Laoac"); arrayList.add("Lingayen"); arrayList.add("Mabini"); arrayList.add("Malasiqui");
        arrayList.add("Manaoag"); arrayList.add("Mangaldan"); arrayList.add("Mangatarem"); arrayList.add("Mapandan"); arrayList.add("Natividad");
        arrayList.add("Pozorrubio"); arrayList.add("Rosales"); arrayList.add("San Carlos City"); arrayList.add("San Fabian"); arrayList.add("San Jacinto");
        arrayList.add("San Manuel"); arrayList.add("San Nicolas"); arrayList.add("San Quintin"); arrayList.add("Sison"); arrayList.add("Sta. Barbara");
        arrayList.add("Sta. Maria"); arrayList.add("Sto. Tomas"); arrayList.add("Sual"); arrayList.add("Tayug"); arrayList.add("Umingan");
        arrayList.add("Urbiztondo"); arrayList.add("Urdaneta"); arrayList.add("Villasis");

        //Initialize and Assign Variable
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //ViewPager and Tablayout
        viewPager = findViewById(R.id.viewpager);
        tabLayoutRents = findViewById(R.id.tablayout);
        pendingTab = findViewById(R.id.pendingtab);
        approveTab = findViewById(R.id.approvetab);
        //completedTab = findViewById(R.id.completedtab);

        // Toolbar
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Initialize PagerAdapter
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayoutRents.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        //Handling TabItem Click
        tabLayoutRents.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutRents));

        fbAddRents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bottomsheet mall dialog show
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainScreenv2.this);
                bottomSheetDialog.setContentView(R.layout.dialog_add_rent);
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                //Assign variable for button
                textView = bottomSheetDialog.findViewById(R.id.searchable_spinner);
                BTN_next = bottomSheetDialog.findViewById(R.id.BTNnext);
                BTN_next.setEnabled(false);

                //Assign variable
                tv_pickupdate = bottomSheetDialog.findViewById(R.id.idtv_pickupdate);
                tv_pickuptime = bottomSheetDialog.findViewById(R.id.idtv_pickuptime);

                tv_returndate = bottomSheetDialog.findViewById(R.id.idtv_returndate);
                tv_returntime = bottomSheetDialog.findViewById(R.id.idtv_returntime);

                pickupDate = bottomSheetDialog.findViewById(R.id.pickUpDate);
                returnDate = bottomSheetDialog.findViewById(R.id.returnDate);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog = new Dialog(MainScreenv2.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dialog_search_spinner);
                        dialog.show();
                        //Initialize and assign variable
                        EditText editText = dialog.findViewById(R.id.select_spinner);
                        ListView listView = dialog.findViewById(R.id.list_view);

                        //Initialize array adapter
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainScreenv2.this
                                , android.R.layout.simple_list_item_1,arrayList);
                        //Set adapter
                        listView.setAdapter(adapter);
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                //Filter array list
                                adapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //When item selected from list
                                //Set selected item on text view
                                textView.setText(adapter.getItem(i));
                                Location = adapter.getItem(i);



                                //Dismiss dialog
                                dialog.dismiss();
                            }
                        });
                    }
                });

                //Initialize calendar
                Calendar calendar = Calendar.getInstance();
                //Get year
                int year = calendar.get(Calendar.YEAR);
                //Get Month
                int month = calendar.get(Calendar.MONTH);

                int month2 = calendar.get(Calendar.MONTH) +1;
                //Get year
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                String daymonthyear = day+"-"+month2+"-"+year;
                pickupDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BTN_next.setEnabled(false);
                        //Initialize datepicker dialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                MainScreenv2.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int pickupyear, int pickupmonth, int pickupday) {
                                //Store in string
                                pickupmonth=pickupmonth+1;
                                SpickupDate = pickupday+"-"+pickupmonth+"-"+pickupyear;


                                //set date on text view
                                tv_pickupdate.setText(SpickupDate);

                                //time picker dialog
                                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int pickupHour, int pickupMinute) {

                                        Calendar datetime = Calendar.getInstance();
                                        Calendar c = Calendar.getInstance();
                                        datetime.set(Calendar.HOUR_OF_DAY, pickupHour);
                                        datetime.set(Calendar.MINUTE, pickupMinute);

                                        if(datetime.getTimeInMillis() >= c.getTimeInMillis() && SpickupDate.equals(daymonthyear)) {

                                        if(pickupHour<12){
                                            displaypickupAMPM = "am";
                                        }else{
                                            displaypickupAMPM = "pm";
                                        }

                                        if(pickupHour==0 || pickupHour==12){
                                            displaypickupHour = "12";
                                        }
                                        else if(pickupHour==1 || pickupHour==13){
                                            displaypickupHour = "01";
                                        }
                                        else if(pickupHour==2 || pickupHour==14){
                                            displaypickupHour = "02";
                                        }
                                        else if(pickupHour==3 || pickupHour==15){
                                            displaypickupHour = "03";
                                        }
                                        else if(pickupHour==4 || pickupHour==16){
                                            displaypickupHour = "04";
                                        }
                                        else if(pickupHour==5 || pickupHour==17){
                                            displaypickupHour = "05";
                                        }
                                        else if(pickupHour==6 || pickupHour==18){
                                            displaypickupHour = "06";
                                        }
                                        else if(pickupHour==7 || pickupHour==19){
                                            displaypickupHour = "07";
                                        }
                                        else if(pickupHour==8 || pickupHour==20){
                                            displaypickupHour = "08";
                                        }
                                        else if(pickupHour==9 || pickupHour==21){
                                            displaypickupHour = "09";
                                        }
                                        else if(pickupHour==10 || pickupHour==22){
                                            displaypickupHour = "10";
                                        }
                                        else if(pickupHour==11 || pickupHour==23){
                                            displaypickupHour = "11";
                                        }


                                        if(pickupMinute==0){
                                            displaypickupMinute = "00";
                                        }
                                        else if(pickupMinute==1){
                                            displaypickupMinute = "01";
                                        }
                                        else if(pickupMinute==2){
                                            displaypickupMinute = "02";
                                        }
                                        else if(pickupMinute==3){
                                            displaypickupMinute = "03";
                                        }
                                        else if(pickupMinute==4){
                                            displaypickupMinute = "04";
                                        }
                                        else if(pickupMinute==5){
                                            displaypickupMinute = "05";
                                        }
                                        else if(pickupMinute==6){
                                            displaypickupMinute = "06";
                                        }
                                        else if(pickupMinute==7){
                                            displaypickupMinute = "07";
                                        }
                                        else if(pickupMinute==8){
                                            displaypickupMinute = "08";
                                        }
                                        else if(pickupMinute==9){
                                            displaypickupMinute = "09";
                                        }else{
                                            displaypickupMinute = Integer.toString(pickupMinute);
                                        }

                                        displaypickupTime = displaypickupHour + ":"+displaypickupMinute+displaypickupAMPM;
                                        GlobalVariables.pckptime = displaypickupTime;

                                        Spickuptime = pickupHour+":"+pickupMinute;

                                        calendar.set(Calendar.HOUR_OF_DAY,pickupHour);
                                        calendar.set(Calendar.MINUTE,pickupMinute);



                                        //set date on text view
                                        tv_pickuptime.setText(displaypickupTime);

                                        click=true;


                                        }

                                        else if(datetime.getTimeInMillis() <= c.getTimeInMillis()  && !SpickupDate.equals(daymonthyear)) {

                                            if(pickupHour<12){
                                                displaypickupAMPM = "am";
                                            }else{
                                                displaypickupAMPM = "pm";
                                            }

                                            if(pickupHour==0 || pickupHour==12){
                                                displaypickupHour = "12";
                                            }
                                            else if(pickupHour==1 || pickupHour==13){
                                                displaypickupHour = "01";
                                            }
                                            else if(pickupHour==2 || pickupHour==14){
                                                displaypickupHour = "02";
                                            }
                                            else if(pickupHour==3 || pickupHour==15){
                                                displaypickupHour = "03";
                                            }
                                            else if(pickupHour==4 || pickupHour==16){
                                                displaypickupHour = "04";
                                            }
                                            else if(pickupHour==5 || pickupHour==17){
                                                displaypickupHour = "05";
                                            }
                                            else if(pickupHour==6 || pickupHour==18){
                                                displaypickupHour = "06";
                                            }
                                            else if(pickupHour==7 || pickupHour==19){
                                                displaypickupHour = "07";
                                            }
                                            else if(pickupHour==8 || pickupHour==20){
                                                displaypickupHour = "08";
                                            }
                                            else if(pickupHour==9 || pickupHour==21){
                                                displaypickupHour = "09";
                                            }
                                            else if(pickupHour==10 || pickupHour==22){
                                                displaypickupHour = "10";
                                            }
                                            else if(pickupHour==11 || pickupHour==23){
                                                displaypickupHour = "11";
                                            }


                                            if(pickupMinute==0){
                                                displaypickupMinute = "00";
                                            }
                                            else if(pickupMinute==1){
                                                displaypickupMinute = "01";
                                            }
                                            else if(pickupMinute==2){
                                                displaypickupMinute = "02";
                                            }
                                            else if(pickupMinute==3){
                                                displaypickupMinute = "03";
                                            }
                                            else if(pickupMinute==4){
                                                displaypickupMinute = "04";
                                            }
                                            else if(pickupMinute==5){
                                                displaypickupMinute = "05";
                                            }
                                            else if(pickupMinute==6){
                                                displaypickupMinute = "06";
                                            }
                                            else if(pickupMinute==7){
                                                displaypickupMinute = "07";
                                            }
                                            else if(pickupMinute==8){
                                                displaypickupMinute = "08";
                                            }
                                            else if(pickupMinute==9){
                                                displaypickupMinute = "09";
                                            }else{
                                                displaypickupMinute = Integer.toString(pickupMinute);
                                            }

                                            displaypickupTime = displaypickupHour + ":"+displaypickupMinute+displaypickupAMPM;
                                            GlobalVariables.pckptime = displaypickupTime;

                                            Spickuptime = pickupHour+":"+pickupMinute;

                                            calendar.set(Calendar.HOUR_OF_DAY,pickupHour);
                                            calendar.set(Calendar.MINUTE,pickupMinute);



                                            //set date on text view
                                            tv_pickuptime.setText(displaypickupTime);

                                            click=true;


                                        }

                                        else if(datetime.getTimeInMillis() >= c.getTimeInMillis()  && !SpickupDate.equals(daymonthyear)) {

                                            if(pickupHour<12){
                                                displaypickupAMPM = "am";
                                            }else{
                                                displaypickupAMPM = "pm";
                                            }

                                            if(pickupHour==0 || pickupHour==12){
                                                displaypickupHour = "12";
                                            }
                                            else if(pickupHour==1 || pickupHour==13){
                                                displaypickupHour = "01";
                                            }
                                            else if(pickupHour==2 || pickupHour==14){
                                                displaypickupHour = "02";
                                            }
                                            else if(pickupHour==3 || pickupHour==15){
                                                displaypickupHour = "03";
                                            }
                                            else if(pickupHour==4 || pickupHour==16){
                                                displaypickupHour = "04";
                                            }
                                            else if(pickupHour==5 || pickupHour==17){
                                                displaypickupHour = "05";
                                            }
                                            else if(pickupHour==6 || pickupHour==18){
                                                displaypickupHour = "06";
                                            }
                                            else if(pickupHour==7 || pickupHour==19){
                                                displaypickupHour = "07";
                                            }
                                            else if(pickupHour==8 || pickupHour==20){
                                                displaypickupHour = "08";
                                            }
                                            else if(pickupHour==9 || pickupHour==21){
                                                displaypickupHour = "09";
                                            }
                                            else if(pickupHour==10 || pickupHour==22){
                                                displaypickupHour = "10";
                                            }
                                            else if(pickupHour==11 || pickupHour==23){
                                                displaypickupHour = "11";
                                            }


                                            if(pickupMinute==0){
                                                displaypickupMinute = "00";
                                            }
                                            else if(pickupMinute==1){
                                                displaypickupMinute = "01";
                                            }
                                            else if(pickupMinute==2){
                                                displaypickupMinute = "02";
                                            }
                                            else if(pickupMinute==3){
                                                displaypickupMinute = "03";
                                            }
                                            else if(pickupMinute==4){
                                                displaypickupMinute = "04";
                                            }
                                            else if(pickupMinute==5){
                                                displaypickupMinute = "05";
                                            }
                                            else if(pickupMinute==6){
                                                displaypickupMinute = "06";
                                            }
                                            else if(pickupMinute==7){
                                                displaypickupMinute = "07";
                                            }
                                            else if(pickupMinute==8){
                                                displaypickupMinute = "08";
                                            }
                                            else if(pickupMinute==9){
                                                displaypickupMinute = "09";
                                            }else{
                                                displaypickupMinute = Integer.toString(pickupMinute);
                                            }

                                            displaypickupTime = displaypickupHour + ":"+displaypickupMinute+displaypickupAMPM;
                                            GlobalVariables.pckptime = displaypickupTime;

                                            Spickuptime = pickupHour+":"+pickupMinute;

                                            calendar.set(Calendar.HOUR_OF_DAY,pickupHour);
                                            calendar.set(Calendar.MINUTE,pickupMinute);



                                            //set date on text view
                                            tv_pickuptime.setText(displaypickupTime);

                                            click=true;


                                        }

                                        else if(datetime.getTimeInMillis() < c.getTimeInMillis() && SpickupDate.equals(daymonthyear)){
                                            Toast.makeText(MainScreenv2.this, "You can't choose past time", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                };

                                new TimePickerDialog(MainScreenv2.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                                //time picker dialog end

                            }
                        },year,month,day
                        );
                        //Disable past date
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                        //Show date picker
                        datePickerDialog.show();
                    }
                });


                returnDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!click){


                            Toast.makeText(MainScreenv2.this, "Please select pickup schedule first.", Toast.LENGTH_SHORT).show();
                        }else{
                            String FirstDate = SpickupDate.trim();
                            String Pick[] = FirstDate.split("-");
                            int Pyear = Integer.parseInt(Pick[2]);
                            int Pmonth = Integer.parseInt(Pick[1]);
                            Pmonth = Pmonth - 1;
                            int Pday = Integer.parseInt(Pick[0]);
                            Pday = Pday + 1;
                            int final_pickupmonth = Pmonth;
                            final Calendar c = Calendar.getInstance();
                            c.set(Pyear, Pmonth, Pday);

                            //Initialize datepicker dialog
                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    MainScreenv2.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int Pyear, int Pkmonth, int Pday) {

                                    //Store in string
                                    Pkmonth = Pkmonth + 1;
                                    SreturnDate = Pday + "-" + Pkmonth + "-" + Pyear;
                                    //set date on text view
                                    tv_returndate.setText(SreturnDate);

                                    //return time picker dialog

                                    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker timePicker, int returnHour, int returnMinute) {

                                                if (returnHour < 12) {
                                                    displayreturnAMPM = "am";
                                                } else {
                                                    displayreturnAMPM = "pm";
                                                }

                                                if (returnHour == 0 || returnHour == 12) {
                                                    displayreturnHour = "12";
                                                } else if (returnHour == 1 || returnHour == 13) {
                                                    displayreturnHour = "01";
                                                } else if (returnHour == 2 || returnHour == 14) {
                                                    displayreturnHour = "02";
                                                } else if (returnHour == 3 || returnHour == 15) {
                                                    displayreturnHour = "03";
                                                } else if (returnHour == 4 || returnHour == 16) {
                                                    displayreturnHour = "04";
                                                } else if (returnHour == 5 || returnHour == 17) {
                                                    displayreturnHour = "05";
                                                } else if (returnHour == 6 || returnHour == 18) {
                                                    displayreturnHour = "06";
                                                } else if (returnHour == 7 || returnHour == 19) {
                                                    displayreturnHour = "07";
                                                } else if (returnHour == 8 || returnHour == 20) {
                                                    displayreturnHour = "08";
                                                } else if (returnHour == 9 || returnHour == 21) {
                                                    displayreturnHour = "09";
                                                } else if (returnHour == 10 || returnHour == 22) {
                                                    displayreturnHour = "10";
                                                } else if (returnHour == 11 || returnHour == 23) {
                                                    displayreturnHour = "11";
                                                }


                                                if (returnMinute == 0) {
                                                    displayreturnMinute = "00";
                                                } else if (returnMinute == 1) {
                                                    displayreturnMinute = "01";
                                                } else if (returnMinute == 2) {
                                                    displayreturnMinute = "02";
                                                } else if (returnMinute == 3) {
                                                    displayreturnMinute = "03";
                                                } else if (returnMinute == 4) {
                                                    displayreturnMinute = "04";
                                                } else if (returnMinute == 5) {
                                                    displayreturnMinute = "05";
                                                } else if (returnMinute == 6) {
                                                    displayreturnMinute = "06";
                                                } else if (returnMinute == 7) {
                                                    displayreturnMinute = "07";
                                                } else if (returnMinute == 8) {
                                                    displayreturnMinute = "08";
                                                } else if (returnMinute == 9) {
                                                    displayreturnMinute = "09";
                                                } else {
                                                    displayreturnMinute = Integer.toString(returnMinute);
                                                }

                                                displayreturnTime = displayreturnHour + ":" + displayreturnMinute + displayreturnAMPM;
                                                GlobalVariables.rtrntime = displayreturnTime;


                                                Sreturntime = returnHour + ":" + returnMinute;
                                                calendar.set(Calendar.HOUR_OF_DAY, returnHour);
                                                calendar.set(Calendar.MINUTE, returnMinute);

                                                //set date on text view
                                                tv_returntime.setText(displayreturnTime);

                                                String SecondDate = tv_returndate.getText().toString().trim();
                                                String Return[] = SecondDate.split("-");
                                                int Ryear = Integer.parseInt(Return[2]);
                                                int Rmonth = Integer.parseInt(Return[1]);

                                                //try lang
                                                Rmonth = Rmonth - 1;

                                                int Rday = Integer.parseInt(Return[0]);
                                                final Calendar d = Calendar.getInstance();
                                                d.set(Ryear, Rmonth, Rday);

                                                String PickupTime = Spickuptime.trim();
                                                String Picktime[] = PickupTime.split(":");
                                                int Phour = Integer.parseInt(Picktime[0]);
                                                int Pminute = Integer.parseInt(Picktime[1]);

                                                String ReturnTime = Sreturntime.trim();
                                                String Return_time[] = ReturnTime.split(":");
                                                int Rhour = Integer.parseInt(Return_time[0]);
                                                int Rminute = Integer.parseInt(Return_time[1]);

                                                //CALCULATE DAYS BETWEEN TWO DATES
                                                Date fdate = c.getTime();
                                                Date sdate = d.getTime();
                                                Long diff = sdate.getTime() - fdate.getTime();
                                                RentDays = diff / (24 * 60 * 60 * 1000) + 1;
                                                GlobalVariables.F_Rentdays = RentDays.intValue();
                                                //RentDays = (int) RentDays;


                                                BTN_next.setEnabled(true);
                                        }
                                    };

                                    new TimePickerDialog(MainScreenv2.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

                                    // returntime picker dialog end


                                }

                            }, year, month, day
                            );


                            //Disable past date
                            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                            //Show date picker
                            datePickerDialog.show();
                        }
                    }
                });

                BTN_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(Location.equals("default_value") || (!click)){
                            Toast.makeText(MainScreenv2.this, "Please select Location first.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent intent = new Intent(MainScreenv2.this,selectvehicle.class);

                            intent.putExtra("nextlocation",Location.toString());
                            intent.putExtra("nextdate",SpickupDate.toString());
                            intent.putExtra("nextpickuphour",Spickuptime.toString());
                            intent.putExtra("nextreturndate",SreturnDate.toString());
                            intent.putExtra("nextreturntime",Sreturntime.toString());
                            intent.putExtra("nextRentdays", RentDays);

                            GlobalVariables.F_Location=Location;
                            GlobalVariables.DisplayPickupDate = SpickupDate;
                            GlobalVariables.DisplayReturnDate = SreturnDate;
                            GlobalVariables.F_Rentdays=RentDays.intValue();

                            startActivity(intent);
                        }
                    }
                });

                bottomSheetDialog.show();
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

            if(backPressedTime + 2000 > System.currentTimeMillis()){
                backToast.cancel();
                super.onBackPressed();
                return;
            }else{
                backToast = Toast.makeText(getBaseContext(),"Press back again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }

            backPressedTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent a = new Intent(MainScreenv2.this, profile.class);
                startActivity(a);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

          /*  case R.id.pendingrents:
                Intent e = new Intent(Mainscreen.this, Pndng_Rents.class);
                startActivity(e);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

            case R.id.rents:
                Intent f = new Intent(Mainscreen.this, Apprvd_Rents.class);
                startActivity(f);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

            case R.id.history:
                Intent g = new Intent(Mainscreen.this, Cmpltd_Rents.class);
                startActivity(g);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break; */

            case R.id.repairandservices:
                /*Intent b = new Intent(MainScreenv2.this, MapsRepairShop.class);
                startActivity(b);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);*/
                Toast.makeText(MainScreenv2.this, "Under construction..to be followed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = " https://play.google.com/store/apps/details?id=com.example.administrator";
                shareIntent.putExtra(Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                Intent d = new Intent(MainScreenv2.this, LoginActivity.class);
                startActivity(d);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}