package com.stayfit.app.stayfit;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stayfit.app.stayfit.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;


    // Action buttons on toolbar
    private MenuItem menuItemEdit;
    private MenuItem menuItemDelete;

    /*- 02 Fragment Variables ----------------------------------------------------------- */
    // Nessesary for making fragment run
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*- 03 Constructur ------------------------------------------------------------------ */
    // Nessesary for having Fragment as class
    public ProfileFragment() {
        // Required empty public constructor
    }

    /*- 04 Creating Fragment ------------------------------------------------------------- */
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*- 05 on Activity Created ---------------------------------------------------------- */
    // Run methods when started
    // Set toolbar menu items
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Set title */
        ((FragmentActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        // getDataFromDbAndDisplay
        initalizeGetDataFromDbAndDisplay();

        // Create menu
        // setHasOptionsMenu(true);
    } // onActivityCreated


    /*- 06 On create view ---------------------------------------------------------------- */
    // Sets main View variable to the view, so we can change views in fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        return mainView;
    }


    /*- 07 set main view ----------------------------------------------------------------- */
    // Changing view method in fragmetn
    private void setMainView(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(mainView);
    }

    /*- 08 on Create Options Menu -------------------------------------------------------- */
    // Creating action icon on toolbar
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate menu
        MenuInflater menuInflater = ((FragmentActivity)getActivity()).getMenuInflater();
        inflater.inflate(R.menu.menu_goal, menu);

        // Assign menu items to variables
        menuItemEdit = menu.findItem(R.id.menu_action_food_edit);
        //menuItemDelete = menu.findItem(R.id.menu_action_food_delete);

        // Hide as default
        // menuItemEdit.setVisible(false);
        //menuItemDelete.setVisible(false);
    }

    /*- 09 on Options Item Selected ------------------------------------------------------ */
    // Action icon clicked on
    // Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        //if (id == R.id.menu_action_goal_edit) {

        //}
        return super.onOptionsItemSelected(menuItem);
    }
    /*- Our own methods -*/

    /*- Get data from db and display --------------------------------------------- */
    public void initalizeGetDataFromDbAndDisplay(){

        /*  Get data from database */
        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        /* Get row number one from users */
        long rowID = 1;
        String fields[] = new String[] {
                "_id",
                "user_dob",
                "user_gender",
                "user_height",
                "user_mesurment"
        };
        Cursor c = db.select("users", fields, "_id", rowID);
        String stringUserDob = c.getString(1);
        String stringUserGender  = c.getString(2);
        String stringUserHeight = c.getString(3);
        String stringUserMesurment = c.getString(4);

        /* DOB */
        String[] items1 = stringUserDob.split("-");
        String stringUserDobYear  = items1[0];
        String stringUserDobMonth = items1[1];
        String stringUserDobYDay  = items1[2];

        /* DOB: Day */

        // Fill numbers for date of birth days
        int spinnerDOBDaySelectedIndex = 0;
        //Toast.makeText(getActivity(), "Day: " + stringUserDobYDay, Toast.LENGTH_LONG).show();
        String[] arraySpinnerDOBDay = new String[31];
        int human_counter = 0;
        for(int x=0;x<31;x++){
            human_counter=x+1;
            arraySpinnerDOBDay[x] = "" + human_counter;

            if(stringUserDobYDay.equals("0" + human_counter) || stringUserDobYDay.equals(""+human_counter)){
                spinnerDOBDaySelectedIndex = x;
                //Toast.makeText(getActivity(), "Day: " + stringUserDobYDay + " Index: " + spinnerDOBDaySelectedIndex, Toast.LENGTH_LONG).show();
            }

        }
        Spinner spinnerDOBDay = (Spinner) getActivity().findViewById(R.id.spinnerEditProfileDOBDay);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinnerDOBDay);
        spinnerDOBDay.setAdapter(adapter);

        spinnerDOBDay.setSelection(spinnerDOBDaySelectedIndex); // Select index


        /* DOB: Month */
        int intUserDobMonth = 0;
        stringUserDobYDay.replace("0", "");
        try {
            intUserDobMonth = Integer.parseInt(stringUserDobMonth);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        intUserDobMonth = intUserDobMonth-1;
        Spinner spinnerDOBMonth = (Spinner) getActivity().findViewById(R.id.spinnerEditProfileDOBMonth);
        spinnerDOBMonth.setSelection(intUserDobMonth); // Select index

        /* DOB: Year */
        // Fill numbers for date of birth year

        int spinnerDOBYearSelectedIndex = 0;

        // get current year、month and day
        String[] arraySpinnerDOBYear = new String[100];
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int end = year-100;
        int index = 0;
        for(int x=year;x>end;x--){
            arraySpinnerDOBYear[index] = "" + x;
            // Toast.makeText(this, "x = " + x, Toast.LENGTH_SHORT).show();

            if(stringUserDobYear.equals(""+x)){
                spinnerDOBYearSelectedIndex = index;
                //Toast.makeText(getActivity(), "Year: " + x + " Index: " + spinnerDOBYearSelectedIndex, Toast.LENGTH_LONG).show();
            }
            index++;
        }
        Spinner spinnerDOBYear = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileDOBYear);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinnerDOBYear);
        spinnerDOBYear.setAdapter(adapterYear);
        spinnerDOBYear.setSelection(spinnerDOBYearSelectedIndex); // Select index


        /* Gender */
        RadioButton radioButtonGenderMale = (RadioButton)getActivity().findViewById(R.id.radioButtonGenderMale);
        RadioButton radioButtonGenderFemale = (RadioButton)getActivity().findViewById(R.id.radioButtonGenderFemale);
        if(stringUserGender.startsWith("m")){
            radioButtonGenderMale.setChecked(true);
            radioButtonGenderFemale.setChecked(false);
        }
        else{
            radioButtonGenderMale.setChecked(false);
            radioButtonGenderFemale.setChecked(true);
        }

        /* Height */
        EditText editTextEditProfileHeightCm = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightCm);
        EditText editTextEditProfileHeightInches = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightInches);
        TextView textViewEditProfileCm = (TextView)getActivity().findViewById(R.id.textViewEditProfileCm);
        if(stringUserMesurment.startsWith("m")) {
            editTextEditProfileHeightInches.setVisibility(View.GONE);
            editTextEditProfileHeightCm.setText(stringUserHeight);
        }
        else{
            textViewEditProfileCm.setText("feet and inches");
            double heightCm = 0;
            double heightFeet = 0;
            double heightInches = 0;

            // Find feet
            try {
                heightCm = Double.parseDouble(stringUserHeight);
            }
            catch(NumberFormatException nfe) {

            }
            if(heightCm != 0){
                // Convert CM into feet
                // feet = cm * 0.3937008)/12
                heightFeet = (heightCm * 0.3937008)/12;
                // heightFeet = Math.round(heightFeet);
                int intHeightFeet = (int) heightFeet;

                editTextEditProfileHeightCm.setText("" + intHeightFeet);

            }

        }




        /* Mesurment */
        Spinner spinnerEditProfileMesurment = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileMesurment);
        if(stringUserMesurment.startsWith("m")) {
            spinnerEditProfileMesurment.setSelection(0); // Select index

        }
        else{
            spinnerEditProfileMesurment.setSelection(1); // Select index
        }



        /* Listener Mesurment spinner */
        spinnerEditProfileMesurment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mesurmentChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // mesurmentChanged();
            }
        });


        /* Listener buttonSignUp */
        Button buttonEditProfileSubmit = (Button)getActivity().findViewById(R.id.buttonEditProfileSubmit);
        buttonEditProfileSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editProfileSubmit();
            }
        });



        // Close db
        db.close();

    }


    /*- Mesurment changed ----------------------------------------------------------------- */
    public void mesurmentChanged() {

        // Mesurment spinner
        Spinner spinnerMesurment = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileMesurment);
        String stringMesurment = spinnerMesurment.getSelectedItem().toString();


        EditText editTextEditProfileHeightCm = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightCm);
        EditText editTextEditProfileHeightInches = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightInches);

        TextView textViewEditProfileCm = (TextView)getActivity().findViewById(R.id.textViewEditProfileCm);



        if(stringMesurment.startsWith("M")) {
            // Metric
            editTextEditProfileHeightInches.setVisibility(View.GONE);
            textViewEditProfileCm.setText("cm");
        }
        else{
            // Imperial
            editTextEditProfileHeightInches.setVisibility(View.VISIBLE);
            textViewEditProfileCm.setText("feet and inches");

        }


    } // public voild messuredChanged


    /*- edit profile submit --------------------------------------------------------------- */
    private void editProfileSubmit(){
        /*  Get data from database */
        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();


        /* Error? */
        int error = 0;


        // Date of Birth Day
        Spinner spinnerDOBDay = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileDOBDay);
        String stringDOBDay = spinnerDOBDay.getSelectedItem().toString();
        int intDOBDay = 0;
        try {
            intDOBDay = Integer.parseInt(stringDOBDay);

            if(intDOBDay < 10){
                stringDOBDay = "0" + stringDOBDay;
            }

        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            error = 1;
            Toast.makeText(getActivity(), "Please select a day for your birthday.", Toast.LENGTH_SHORT).show();
        }


        // Date of Birth Month
        Spinner spinnerDOBMonth = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileDOBMonth);
        String stringDOBMonth = spinnerDOBMonth.getSelectedItem().toString();
        int positionDOBMonth = spinnerDOBMonth.getSelectedItemPosition();
        int month = positionDOBMonth+1;
        if(month < 10){
            stringDOBMonth = "0" + month;
        }
        else{
            stringDOBMonth = "" + month;
        }
        // Toast.makeText(this, "Month: " + stringDOBMonth, Toast.LENGTH_LONG).show();


        // Date of Birth Year
        Spinner spinnerDOBYear = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileDOBYear);
        String stringDOBYear = spinnerDOBYear.getSelectedItem().toString();
        int intDOBYear = 0;
        try {
            intDOBYear = Integer.parseInt(stringDOBYear);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            error = 1;
            Toast.makeText(getActivity(), "Please select a year for your birthday.", Toast.LENGTH_SHORT).show();
        }

        // Put date of birth togheter
        String dateOfBirth = intDOBYear + "-" + stringDOBMonth + "-" + stringDOBDay;
        String dateOfBirthSQL = db.quoteSmart(dateOfBirth);


        // Gender
        RadioGroup radioGroupGender = (RadioGroup)getActivity().findViewById(R.id.radioGroupGender);
        int radioButtonID = radioGroupGender.getCheckedRadioButtonId(); // get selected radio button from radioGroup
        View radioButtonGender = radioGroupGender.findViewById(radioButtonID);
        int position = radioGroupGender.indexOfChild(radioButtonGender); // If you want position of Radiobutton

        String stringGender = "";
        if(position == 0){
            stringGender = "male";
        }
        else{
            stringGender = "female";
        }
        String genderSQL = db.quoteSmart(stringGender);



        /* Height */
        EditText editTextHeightCm = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightCm);
        EditText editTextHeightInches = (EditText)getActivity().findViewById(R.id.editTextEditProfileHeightInches);
        String stringHeightCm = editTextHeightCm.getText().toString();
        String stringHeightInches = editTextHeightInches.getText().toString();

        double heightCm = 0;
        double heightFeet = 0;
        double heightInches = 0;
        boolean metric = true;



        // Metric or imperial?
        Spinner spinnerMesurment = (Spinner)getActivity().findViewById(R.id.spinnerEditProfileMesurment);
        String stringMesurment = spinnerMesurment.getSelectedItem().toString();

        int intMesurment = spinnerMesurment.getSelectedItemPosition();
        if(intMesurment == 0){
            stringMesurment = "metric";
        }
        else{
            stringMesurment = "imperial";
            metric = false;
        }
        String mesurmentSQL = db.quoteSmart(stringMesurment);

        if(metric == true) {

            // Convert CM
            try {
                heightCm = Double.parseDouble(stringHeightCm);
                heightCm = Math.round(heightCm);
            }
            catch(NumberFormatException nfe) {
                error = 1;
                Toast.makeText(getActivity(), "Height (cm) has to be a number.", Toast.LENGTH_SHORT).show();
            }
        }
        else {

            // Convert Feet
            try {
                heightFeet = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {
                error = 1;
                Toast.makeText(getActivity(), "Height (feet) has to be a number.", Toast.LENGTH_SHORT).show();
            }

            // Convert inches
            try {
                heightInches = Double.parseDouble(stringHeightInches);
            }
            catch(NumberFormatException nfe) {
                error = 1;
                Toast.makeText(getActivity(), "Height (inches) has to be a number.", Toast.LENGTH_SHORT).show();
            }

            // Need to convert, we want to save the number in cm
            // cm = ((foot * 12) + inches) * 2.54
            heightCm = ((heightFeet * 12) + heightInches) * 2.54;
            heightCm = Math.round(heightCm);
        }
        stringHeightCm = "" + heightCm;
        String heightCmSQL = db.quoteSmart(stringHeightCm);



        if(error == 0){

            long id = 1;

            String fields[] = new String[] {
                    "user_dob",
                    "user_gender",
                    "user_height",
                    "user_mesurment"
            };
            String values[] = new String[] {
                    dateOfBirthSQL,
                    genderSQL,
                    heightCmSQL,
                    mesurmentSQL
            };

            db.update("users", "_id", id, fields, values);

            Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();

        } // error == 0



        // Close db
        db.close();

    } // editProfileSubmit






    /*- Fragment  methods -*/


    /*- On create ----------------------------------------------------------------- */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
