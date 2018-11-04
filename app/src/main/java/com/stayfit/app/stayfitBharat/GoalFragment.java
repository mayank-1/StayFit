package com.stayfit.app.stayfitBharat;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GoalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalFragment extends Fragment {

    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;


    // Action buttons on toolbar
    private MenuItem menuItemEdit;
    private MenuItem menuItemDelete;

    // Holder for buttons on toolbar
    private String currentId;
    private String currentName;




    /*- 02 Fragment Variables ----------------------------------------------------------- */
    // Nessesary for making fragment run
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*- 03 Constructur ------------------------------------------------------------------ */
    // Nessesary for having Fragment as class
    public GoalFragment() {
        // Required empty public constructor
    }


    /*- 04 Creating Fragment ------------------------------------------------------------- */
    public static GoalFragment newInstance(String param1, String param2) {
        GoalFragment fragment = new GoalFragment();
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
        ((FragmentActivity)getActivity()).getSupportActionBar().setTitle("Goal");

        // getDataFromDbAndDisplay
        initalizeGetDataFromDbAndDisplay();

        // Create menu
        setHasOptionsMenu(true);
    } // onActivityCreated


    /*- 06 On create view ---------------------------------------------------------------- */
    // Sets main View variable to the view, so we can change views in fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_goal, container, false);
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
        if (id == R.id.menu_action_goal_edit) {
            goalEdit();
        }
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
                "user_mesurment"
        };
        Cursor c = db.select("users", fields, "_id", rowID);
        String mesurment;
        mesurment = c.getString(1);



        // Get Goal data
        String fieldsGoal[] = new String[] {
                "_id",
                "goal_current_weight",
                "goal_target_weight",
                "goal_i_want_to",
                "goal_weekly_goal",
                "goal_activity_level",
                "goal_date",
        };
        Cursor goalCursor = db.select("goal", fieldsGoal, "", "", "_id", "DESC");


        // Ready as variables
        String goalID = goalCursor.getString(0);
        String goalCurrentWeight = goalCursor.getString(1);
        String goalTargetWeight = goalCursor.getString(2);
        String goalIWantTo = goalCursor.getString(3);
        String goalWeeklyGoal = goalCursor.getString(4);
        String goalActivityLevel = goalCursor.getString(5);
        String goalDate = goalCursor.getString(6);

        /* Status */

        // Current weight
        TextView textViewGoalCurrentWeightNumber = (TextView)getActivity().findViewById(R.id.textViewGoalCurrentWeightNumber);
        if(mesurment.startsWith("m")) {
            // Metric
            textViewGoalCurrentWeightNumber.setText(goalCurrentWeight + " kg (" + goalDate + ")");
        }
        else{
            // Imperial
            // Kg to punds
            double currentWeightNumber = 0;

            try {
                currentWeightNumber = Double.parseDouble(goalCurrentWeight);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            // kg to pounds
            double currentWeightNumberPounds =  Math.round(currentWeightNumber / 0.45359237);


            textViewGoalCurrentWeightNumber.setText(currentWeightNumberPounds + " pounds (" + goalDate + ")");
        }

        //  Target
        TextView textViewGoalCurrentTargetNumber = (TextView)getActivity().findViewById(R.id.textViewGoalCurrentTargetNumber);
        if(mesurment.startsWith("m")) {
            // Metric
            textViewGoalCurrentTargetNumber.setText(goalTargetWeight + " kg");
        }
        else{
            // Imperial
            // Kg to punds
            double targetWeightNumber = 0;

            try {
                targetWeightNumber = Double.parseDouble(goalTargetWeight);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            // kg to pounds
            double targetWeightNumberPounds =  Math.round(targetWeightNumber / 0.45359237);


            textViewGoalCurrentTargetNumber.setText(targetWeightNumberPounds + " pounds");
        }


        // Method
        TextView textViewGoalMethodText = (TextView)getActivity().findViewById(R.id.textViewGoalMethodText);

        String method = "";
        if(goalIWantTo.equals("0")){
            method = "Loose "  + goalWeeklyGoal;
        }
        else{
            method = "Gain "  + goalWeeklyGoal;
        }
        if(mesurment.startsWith("m")) {
            method = method + " kg/week";
        }
       else{
            method = method + " pounds/week";
        }
        textViewGoalMethodText.setText(method);


        /* Activity level */
        TextView textViewActivityLevel = (TextView)getActivity().findViewById(R.id.textViewActivityLevel);
        if(goalActivityLevel.equals("0")){
            textViewActivityLevel.setText("Little to no exercise");
        }
        else if(goalActivityLevel.equals("1")){
            textViewActivityLevel.setText("Light exercise (1–3 days per week)");
        }
        else if(goalActivityLevel.equals("2")){
            textViewActivityLevel.setText("Moderate exercise (3–5 days per week)");
        }
        else if(goalActivityLevel.equals("3")){
            textViewActivityLevel.setText("Heavy exercise (6–7 days per week)");
        }
        else if(goalActivityLevel.equals("4")){
            textViewActivityLevel.setText("Very heavy exercise (twice per day, extra heavy workouts)");
        }


        /* Numbers */
        updateNumberTable();


        // Hide fields
        toggleNumbersViewGoal(false);

        // Checkbox toggle
        CheckBox checkBoxAdvanced = (CheckBox)getActivity().findViewById(R.id.checkBoxGoalToggle);

        checkBoxAdvanced.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                toggleNumbersViewGoal(isChecked);
            }
        }
        );
        // Close db
        db.close();
    } // initalizeGetDataFromDbAndDisplay


    public void toggleNumbersViewGoal(boolean isChecked){


        // Remove table rows
        TableRow textViewGoalMethodRowA = (TableRow)getActivity().findViewById(R.id.textViewGoalMethodRowA);

        TableRow textViewGoalMethodRowB = (TableRow)getActivity().findViewById(R.id.textViewGoalMethodRowB);

        // Hide fields
        TextView textViewGoalHeadcellEnergy = (TextView)getActivity().findViewById(R.id.textViewGoalHeadcellEnergy );
        TextView textViewGoalHeadcellProteins = (TextView)getActivity().findViewById(R.id.textViewGoalHeadcellProteins );
        TextView textViewGoalHeadcellCarbs = (TextView)getActivity().findViewById(R.id.textViewGoalHeadcellCarbs );
        TextView textViewGoalHeadcellFat = (TextView)getActivity().findViewById(R.id.textViewGoalHeadcellFat );

        TextView textViewGoalProteinsBMR = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsBMR );
        TextView textViewGoalCarbsBMR = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsBMR);
        TextView textViewGoalFatBMR = (TextView)getActivity().findViewById(R.id.textViewGoalFatBMR);

        TextView textViewGoalProteinsDiet = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsDiet);
        TextView textViewGoalCarbsDiet = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsDiet);
        TextView textViewGoalFatDiet = (TextView)getActivity().findViewById(R.id.textViewGoalFatDiet);

        TextView textViewGoalProteinsWithActivity = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsWithActivity);
        TextView textViewGoalCarbsWithActivity = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsWithActivity);
        TextView textViewGoalFatWithActivity = (TextView)getActivity().findViewById(R.id.textViewGoalFatWithActivity);

        TextView textViewGoalProteinsWithActivityAndDiet = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsWithActivityAndDiet);
        TextView textViewGoalCarbsWithActivityAndDiet = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsWithActivityAndDiet);
        TextView textViewGoalFatWithActivityAndDiet = (TextView)getActivity().findViewById(R.id.textViewGoalFatWithActivityAndDiet);

        if(isChecked == false){
            textViewGoalMethodRowA.setVisibility(View.GONE);
            textViewGoalMethodRowB.setVisibility(View.GONE);
            textViewGoalHeadcellEnergy.setVisibility(View.GONE);
            textViewGoalHeadcellProteins.setVisibility(View.GONE);
            textViewGoalHeadcellCarbs.setVisibility(View.GONE);
            textViewGoalHeadcellFat.setVisibility(View.GONE);
            textViewGoalProteinsBMR.setVisibility(View.GONE);
            textViewGoalCarbsBMR.setVisibility(View.GONE);
            textViewGoalFatBMR.setVisibility(View.GONE);
            textViewGoalProteinsDiet.setVisibility(View.GONE);
            textViewGoalCarbsDiet.setVisibility(View.GONE);
            textViewGoalFatDiet.setVisibility(View.GONE);
            textViewGoalProteinsWithActivity.setVisibility(View.GONE);
            textViewGoalCarbsWithActivity.setVisibility(View.GONE);
            textViewGoalFatWithActivity.setVisibility(View.GONE);
            textViewGoalProteinsWithActivityAndDiet.setVisibility(View.GONE);
            textViewGoalCarbsWithActivityAndDiet.setVisibility(View.GONE);
            textViewGoalFatWithActivityAndDiet.setVisibility(View.GONE);
        }
        else {
            textViewGoalMethodRowA.setVisibility(View.VISIBLE);
            textViewGoalMethodRowB.setVisibility(View.VISIBLE);
            textViewGoalHeadcellEnergy.setVisibility(View.VISIBLE);
            textViewGoalHeadcellProteins.setVisibility(View.VISIBLE);
            textViewGoalHeadcellCarbs.setVisibility(View.VISIBLE);
            textViewGoalHeadcellFat.setVisibility(View.VISIBLE);
            textViewGoalProteinsBMR.setVisibility(View.VISIBLE);
            textViewGoalCarbsBMR.setVisibility(View.VISIBLE);
            textViewGoalFatBMR.setVisibility(View.VISIBLE);
            textViewGoalProteinsDiet.setVisibility(View.VISIBLE);
            textViewGoalCarbsDiet.setVisibility(View.VISIBLE);
            textViewGoalFatDiet.setVisibility(View.VISIBLE);
            textViewGoalProteinsWithActivity.setVisibility(View.VISIBLE);
            textViewGoalCarbsWithActivity.setVisibility(View.VISIBLE);
            textViewGoalFatWithActivity.setVisibility(View.VISIBLE);
            textViewGoalProteinsWithActivityAndDiet.setVisibility(View.VISIBLE);
            textViewGoalCarbsWithActivityAndDiet.setVisibility(View.VISIBLE);
            textViewGoalFatWithActivityAndDiet.setVisibility(View.VISIBLE);

        }
    }

    /*- Goal edit ------------------------------------------------------------------ */
    public void goalEdit(){
        /* Change layout */
        int id = R.layout.fragment_goal_edit;
        setMainView(id);


        /*  Get data from database */

        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();


        /* Get row number one from users */
        long rowID = 1;
        String fields[] = new String[] {
                "_id",
                "user_mesurment"
        };
        Cursor c = db.select("users", fields, "_id", rowID);
        String mesurment;
        mesurment = c.getString(1);

        // Get Goal data
        String fieldsGoal[] = new String[] {
                "_id",
                "goal_current_weight",
                "goal_target_weight",
                "goal_i_want_to",
                "goal_weekly_goal",
                "goal_activity_level"
        };
        Cursor goalCursor = db.select("goal", fieldsGoal, "", "", "_id", "DESC");

        // Ready as variables
        String goalID = goalCursor.getString(0);
        String goalCurrentWeight = goalCursor.getString(1);
        String goalTargetWeight = goalCursor.getString(2);
        String goalIWantTo = goalCursor.getString(3);
        String goalWeeklyGoal = goalCursor.getString(4);
        String goalActivityLevel = goalCursor.getString(5);

        // Current weight
        EditText editTextGoalCurrentWeight = (EditText) getActivity().findViewById(R.id.editTextGoalCurrentWeight);
        if(mesurment.startsWith("m")) {
            // Metric
            editTextGoalCurrentWeight.setText(goalCurrentWeight);
        }
        else{
            // Imperial
            // Kg to punds
            double currentWeightNumber = 0;

            try {
                currentWeightNumber = Double.parseDouble(goalCurrentWeight);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            // kg to pounds
            double currentWeightNumberPounds =  Math.round(currentWeightNumber / 0.45359237);


            editTextGoalCurrentWeight.setText(currentWeightNumberPounds+"");

            // Edit kg to pound
            TextView textViewGoalCurrentWeightType = (TextView)getActivity().findViewById(R.id.textViewGoalCurrentWeightType);
            textViewGoalCurrentWeightType.setText("pounds");
        }


        //  Target
        TextView editTextGoalTargetWeight = (TextView)getActivity().findViewById(R.id.editTextGoalTargetWeight);
        if(mesurment.startsWith("m")) {
            // Metric
            editTextGoalTargetWeight.setText(goalTargetWeight);
        }
        else{
            // Imperial
            // Kg to punds
            double targetWeightNumber = 0;

            try {
                targetWeightNumber = Double.parseDouble(goalTargetWeight);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            // kg to pounds
            double targetWeightNumberPounds =  Math.round(targetWeightNumber / 0.45359237);


            editTextGoalTargetWeight.setText(targetWeightNumberPounds + "");


            // Edit kg to pound
            TextView textViewTargetWeightType = (TextView)getActivity().findViewById(R.id.textViewTargetWeightType);
            textViewTargetWeightType.setText("pounds/week");
        }

        // I want to
        Spinner spinnerIWantTo = (Spinner)getActivity().findViewById(R.id.spinnerIWantTo);
        if(goalIWantTo.equals("0")){
            spinnerIWantTo.setSelection(0);
        }
        else{
            spinnerIWantTo.setSelection(1);
        }

        // Weekly goal
        Spinner spinnerWeeklyGoal = (Spinner)getActivity().findViewById(R.id.spinnerWeeklyGoal);
        if(goalWeeklyGoal.equals("0.5")){
            spinnerWeeklyGoal.setSelection(0);
        }
        else if(goalWeeklyGoal.equals("1")){
            spinnerWeeklyGoal.setSelection(1);
        }
        else if(goalWeeklyGoal.equals("1.5")){
            spinnerWeeklyGoal.setSelection(2);
        }

        // Activity level
        Spinner spinnerActivityLevel = (Spinner)getActivity().findViewById(R.id.spinnerActivityLevel);
        int intActivityLevel = 0;
        try{
            intActivityLevel = Integer.parseInt(goalActivityLevel);
        }
        catch (NumberFormatException e){

        }
        spinnerActivityLevel.setSelection(intActivityLevel);



        // Update table
        updateNumberTable();

        /* SubmitButton listener */
        Button buttonGoalSubmit = (Button)getActivity().findViewById(R.id.buttonGoalSubmit);
        buttonGoalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGoalSubmitOnClick();
            }
        });

        // Close db
        db.close();



    } // goalEdit

    /*- editGoalSubmitOnClick ------------------------------------------------------ */
    public void editGoalSubmitOnClick(){

        /* Error */
        int error = 0;

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
        String mesurment = c.getString(4);


        // Get Age
        String[] items1 = stringUserDob.split("-");
        String stringYear = items1[0];
        String stringMonth = items1[1];
        String stringDay = items1[2];

        int intYear = 0;
        try {
            intYear = Integer.parseInt(stringYear);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        int intMonth = 0;
        try {
            intMonth = Integer.parseInt(stringMonth);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        int intDay = 0;
        try {
            intDay = Integer.parseInt(stringDay);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        String stringUserAge = getAge(intYear, intMonth, intDay);

        int intUserAge = 0;
        try {
            intUserAge = Integer.parseInt(stringUserAge);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        // Get height
        double doubleUserHeight = 0;

        try {
            doubleUserHeight = Double.parseDouble(stringUserHeight);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }




        // Current weight
        EditText editTextGoalCurrentWeight = (EditText) getActivity().findViewById(R.id.editTextGoalCurrentWeight);
        String stringCurrentWeight = editTextGoalCurrentWeight.getText().toString();
        double doubleCurrentWeight = 0;
        if(stringCurrentWeight.isEmpty()){
            Toast.makeText(getActivity(), "Please enter current weight", Toast.LENGTH_LONG).show();
             error = 1;
        }
        else{
            try {
                doubleCurrentWeight = Double.parseDouble(stringCurrentWeight);
            }
            catch(NumberFormatException nfe) {
                Toast.makeText(getActivity(), "Current weight has to be a number.\nError: " + nfe.toString(), Toast.LENGTH_LONG).show();
                error = 1;
            }
        }
        String stringCurrentWeightSQL = db.quoteSmart(stringCurrentWeight);

        // Target weight
        EditText editTextGoalTargetWeight = (EditText) getActivity().findViewById(R.id.editTextGoalTargetWeight);
        String stringTargetWeight = editTextGoalTargetWeight.getText().toString();
        double doubleTargetWeight = 0;
        if(stringTargetWeight.isEmpty()){
            Toast.makeText(getActivity(), "Please enter target weight", Toast.LENGTH_LONG).show();
            error = 1;
        }
        else{
            try {
                doubleTargetWeight = Double.parseDouble(stringTargetWeight);
            }
            catch(NumberFormatException nfe) {
                Toast.makeText(getActivity(), "Target weight has to be a number.\nError: " + nfe.toString(), Toast.LENGTH_LONG).show();
                error = 1;
            }
        }
        String stringTargetWeightSQL = db.quoteSmart(stringTargetWeight);

        // I want to
        Spinner spinnerIWantTo = (Spinner)getActivity().findViewById(R.id.spinnerIWantTo);
        int intIWantTo = spinnerIWantTo.getSelectedItemPosition();
        String stringIWantTo = "" + intIWantTo;
        String stringIWantToSQL = db.quoteSmart(stringIWantTo);

        /* Spinner spinnerWeeklyGoal */
        Spinner spinnerWeeklyGoal = (Spinner)getActivity().findViewById(R.id.spinnerWeeklyGoal);
        String stringWeeklyGoal = spinnerWeeklyGoal.getSelectedItem().toString();
        String stringWeeklyGoalSQL = db.quoteSmart(stringWeeklyGoal);

        /* Activity level */
        Spinner spinnerActivityLevel = (Spinner)getActivity().findViewById(R.id.spinnerActivityLevel);
        //  0: Little to no exercise
        // 1: Light exercise (1–3 days per week)
        // 2: Moderate exercise (3–5 days per week)
        // 3: Heavy exercise (6–7 days per week)
        // 4: Very heavy exercise (twice per day, extra heavy workouts)
        int intActivityLevel = spinnerActivityLevel.getSelectedItemPosition();
        String stringActivityLevel = ""+intActivityLevel;
        String stringActivityLevelSQL = db.quoteSmart(stringActivityLevel);

        /* TextView Calculation */
        TextView textViewCalculation = (TextView)getActivity().findViewById(R.id.textViewCalculation);

        if(error == 0) {
            /* Insert into database */

            // Date
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//foramt date
            String goalDate = df1.format(Calendar.getInstance().getTime());
            String goalDateSQL = db.quoteSmart(goalDate);



            /* 1. BMR: Energy */
            double goalEnergyBMR = 0;

            if(stringUserGender.startsWith("m")){
                // Male
                // BMR = 66.5 + (13.75 x kg body weight) + (5.003 x height in cm) - (6.755 x age)
                goalEnergyBMR = 66.5+(13.75*doubleCurrentWeight)+(5.003*doubleUserHeight)-(6.755*intUserAge);
                //bmr = Math.round(bmr);
                //textViewCalculation.setText("BMR formula male:\n 66.5+(13.75*" + doubleCurrentWeight + ")+(5.003*" + doubleUserHeight + ")-(6.755*" + intUserAge + ")");

            } // if(stringUserGender.startsWith("m")){
            else{
                // Female
                // BMR = 55.1 + (9.563 x kg body weight) + (1.850 x height in cm) - (4.676 x age)
                goalEnergyBMR = 655+(9.563*doubleCurrentWeight)+(1.850*doubleUserHeight)-(4.676*intUserAge);
                //bmr = Math.round(bmr);
            }
            goalEnergyBMR = Math.round(goalEnergyBMR);
            String goalEnergyBMRSQL = db.quoteSmart(""+goalEnergyBMR);

            // BRM: Proteins, carbs, fat
            double proteinsBMR = Math.round(goalEnergyBMR*25/100);
            double carbsBMR = Math.round(goalEnergyBMR*50/100);
            double fatBMR = Math.round(goalEnergyBMR*25/100);

            double proteinsBMRSQL = db.quoteSmart(proteinsBMR);
            double carbsBMRSQL = db.quoteSmart(carbsBMR);
            double fatBBRSQL = db.quoteSmart(fatBMR);




            /* 2: Diet */
            // If you want to loose weight
            // without activity (Little to no exercise)
            // Loose or gain weight?
            double doubleWeeklyGoal = 0;
            try {
                doubleWeeklyGoal = Double.parseDouble(stringWeeklyGoal);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            // 1 kg fat = 7700 kcal
            double kcal = 0;
            double energyDiet = 0;
            kcal = 7700*doubleWeeklyGoal;
            if(intIWantTo == 0){
                // Loose weight
                energyDiet = Math.round((goalEnergyBMR - (kcal/7)) * 1.2);

            }
            else{
                // Gain weight
                energyDiet = Math.round((goalEnergyBMR + (kcal/7)) * 1.2);
            }

            // Update database
            double energyDietSQL = db.quoteSmart(energyDiet);

            // Proteins, carbs and fat diet
            // 20-25 % protein
            // 40-50 % carbs
            // 25-35 % fat
            double proteinsDiet = Math.round(energyDiet*25/100);
            double carbsDiet = Math.round(energyDiet*50/100);
            double fatDiet = Math.round(energyDiet*25/100);

            double proteinsDietSQL = db.quoteSmart(proteinsDiet);
            double carbsDietSQL = db.quoteSmart(carbsDiet);
            double fatDietSQL = db.quoteSmart(fatDiet);




            /* 3: With activity */
            // If you want to keep your weight
            //
            // Taking in to account activity
            double energyWithActivity = 0;
            if(stringActivityLevel.equals("0")) {
                energyWithActivity = goalEnergyBMR * 1.2;
            }
            else if(stringActivityLevel.equals("1")) {
                energyWithActivity = goalEnergyBMR * 1.375; // slightly_active
            }
            else if(stringActivityLevel.equals("2")) {
                energyWithActivity = goalEnergyBMR*1.55; // moderately_active
            }
            else if(stringActivityLevel.equals("3")) {
                energyWithActivity = goalEnergyBMR*1.725; // active_lifestyle
            }
            else if(stringActivityLevel.equals("4")) {
                energyWithActivity = goalEnergyBMR * 1.9; // very_active
            }
            energyWithActivity = Math.round(energyWithActivity);
            double energyWithActivitySQL = db.quoteSmart(energyWithActivity);
            // Proteins, carbs and fat diet
            // 20-25 % protein
            // 40-50 % carbs
            // 25-35 % fat
            double proteinsWithActivity = Math.round(energyWithActivity*25/100);
            double carbsWithActivity = Math.round(energyWithActivity*50/100);
            double fatWithActivity = Math.round(energyWithActivity*25/100);

            double proteinsWithActivitySQL = db.quoteSmart(proteinsWithActivity);
            double carbsWithActivitySQL = db.quoteSmart(carbsWithActivity);
            double fatWithActivityQL = db.quoteSmart(fatWithActivity);



            /* 4: With_activity_and_diet */
            // If you want to loose your weight
            // With activity
            // 1 kg fat = 7700 kcal
            kcal = 0;
            double energyWithActivityAndDiet = 0;
            kcal = 7700*doubleWeeklyGoal;
            if(intIWantTo == 0){
                // Loose weight
                energyWithActivityAndDiet = goalEnergyBMR - (kcal/7);
            }
            else{
                // Gain weight
                energyWithActivityAndDiet = goalEnergyBMR + (kcal/7);
            }
            if(stringActivityLevel.equals("0")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet* 1.2;
            }
            else if(stringActivityLevel.equals("1")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet* 1.375; // slightly_active
            }
            else if(stringActivityLevel.equals("2")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet*1.55; // moderately_active
            }
            else if(stringActivityLevel.equals("3")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet*1.725; // active_lifestyle
            }
            else if(stringActivityLevel.equals("4")) {
                energyWithActivityAndDiet = energyWithActivityAndDiet* 1.9; // very_active
            }
            energyWithActivityAndDiet = Math.round(energyWithActivityAndDiet);

            // Update database
            double energyWithActivityAndDietSQL = db.quoteSmart(energyWithActivityAndDiet);


            // Calcualte proteins
            // 20-25 % protein
            // 40-50 % carbs
            // 25-35 % fat
            double proteinsWithActivityAndDiet = Math.round(energyWithActivityAndDiet*25/100);
            double carbsWithActivityAndDiet = Math.round(energyWithActivityAndDiet*50/100);
            double fatWithActivityAndDiet = Math.round(energyWithActivityAndDiet*25/100);

            double proteinsWithActivityAndDietSQL = db.quoteSmart(proteinsWithActivityAndDiet);
            double carbsWithActivityAndDietSQL = db.quoteSmart(carbsWithActivityAndDiet);
            double fatWithActivityAndDietSQL = db.quoteSmart(fatWithActivityAndDiet);



            // Insert
            String inpFields = "'_id', " +
                    "'goal_current_weight', " +
                    "'goal_target_weight', " +
                    "'goal_i_want_to', " +
                    "'goal_weekly_goal', " +
                    "'goal_date'," +
                    "'goal_activity_level'," +
                    "'goal_energy_bmr'," +
                    "'goal_proteins_bmr'," +
                    "'goal_carbs_bmr'," +
                    "'goal_fat_bmr'," +
                    "'goal_energy_diet'," +
                    "'goal_proteins_diet'," +
                    "'goal_carbs_diet'," +
                    "'goal_fat_diet'," +
                    "'goal_energy_with_activity'," +
                    "'goal_proteins_with_activity'," +
                    "'goal_carbs_with_activity'," +
                    "'goal_fat_with_activity'," +

                    "'goal_energy_with_activity_and_diet'," +
                    "'goal_proteins_with_activity_and_diet'," +
                    "'goal_carbs_with_activity_and_diet'," +
                    "'goal_fat_with_activity_and_diet'";

            String inpValues = "NULL, " +
                    stringCurrentWeightSQL + ", " +
                    stringTargetWeightSQL + ", " +
                    stringIWantToSQL  + ", " +
                    stringWeeklyGoalSQL + ", " +
                    goalDateSQL  + ", " +
                    stringActivityLevelSQL + ", " +
                    goalEnergyBMRSQL + ", " +
                    proteinsBMRSQL + ", " +
                    carbsBMRSQL  + ", " +
                    fatBBRSQL + ", " +

                    energyDietSQL + ", " +
                    proteinsDietSQL + ", " +
                    carbsDietSQL  + ", " +
                    fatDietSQL + ", " +

                    energyWithActivity + ", " +
                    proteinsWithActivitySQL  + ", " +
                    carbsWithActivitySQL   + ", " +
                    fatWithActivityQL   + ", " +

                    energyWithActivityAndDietSQL + ", " +
                    proteinsWithActivityAndDietSQL + ", " +
                    carbsWithActivityAndDietSQL + ", " +
                    fatWithActivityAndDietSQL;

            db.insert("goal", inpFields, inpValues);


            // Update table
            updateNumberTable();


            // Give feedback
            Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();


            // Move user back to correct design
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.flContent, new GoalFragment(), GoalFragment.class.getName()).commit();


        } // error == 0

    } // editGoalSubmitOnClick


    /* getAge -------------------------------------------------------------- */
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    /* Update table ------------------------------------------------------- */
    private void updateNumberTable(){
        DBAdapter db = new DBAdapter(getActivity());
        db.open();
        // Get Goal data
        String fieldsGoal[] = new String[] {
                "goal_energy_bmr",
                "goal_proteins_bmr",
                "goal_carbs_bmr",
                "goal_fat_bmr",
                "goal_energy_diet",
                "goal_proteins_diet",
                "goal_carbs_diet",
                "goal_fat_diet",
                "goal_energy_with_activity",
                "goal_proteins_with_activity",
                "goal_carbs_with_activity",
                "goal_fat_with_activity",
                "goal_energy_with_activity_and_diet",
                "goal_proteins_with_activity_and_diet",
                "goal_carbs_with_activity_and_diet",
                "goal_fat_with_activity_and_diet"
        };
        Cursor goalCursor = db.select("goal", fieldsGoal, "", "", "_id", "DESC");


        // Ready as variables
        String goalEnergyBmr = goalCursor.getString(0);
        String goalProteinsBmr = goalCursor.getString(1);
        String goalCarbsBmr = goalCursor.getString(2);
        String goalFatBmr = goalCursor.getString(3);
        String goalEnergyDiet = goalCursor.getString(4);
        String goalProteinsDiet = goalCursor.getString(5);
        String goalCarbsDiet = goalCursor.getString(6);
        String goalFatDiet = goalCursor.getString(7);
        String goalEnergyWithActivity = goalCursor.getString(8);
        String goalProteinsWithActivity = goalCursor.getString(9);
        String goalCarbsWithActivity = goalCursor.getString(10);
        String goalFatWithActivity = goalCursor.getString(11);
        String goalEnergyWithActivityAndDiet = goalCursor.getString(12);
        String goalProteinsWithActivityAndDiet = goalCursor.getString(13);
        String goalCarbsWithActivityAndDiet = goalCursor.getString(14);
        String goalFatWithActivityAndDiet = goalCursor.getString(15);



        /* Numbers */

        // 1 Diet
        TextView textViewGoalEnergyDiet = (TextView)getActivity().findViewById(R.id.textViewGoalEnergyDiet);
        textViewGoalEnergyDiet.setText(goalEnergyDiet);
        TextView textViewGoalProteinsDiet = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsDiet);
        textViewGoalProteinsDiet.setText(goalProteinsDiet);
        TextView textViewGoalCarbsDiet = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsDiet);
        textViewGoalCarbsDiet.setText(goalCarbsDiet);
        TextView textViewGoalFatDiet = (TextView)getActivity().findViewById(R.id.textViewGoalFatDiet);
        textViewGoalFatDiet.setText(goalFatDiet);

        // 2 WithActivityAndDiet
        TextView textViewGoalEnergyWithActivityAndDiet = (TextView)getActivity().findViewById(R.id.textViewGoalEnergyWithActivityAndDiet);
        textViewGoalEnergyWithActivityAndDiet.setText(goalEnergyWithActivityAndDiet);
        TextView textViewGoalProteinsWithActivityAndDiet = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsWithActivityAndDiet);
        textViewGoalProteinsWithActivityAndDiet.setText(goalProteinsWithActivityAndDiet);
        TextView textViewGoalCarbsWithActivityAndDiet = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsWithActivityAndDiet);
        textViewGoalCarbsWithActivityAndDiet.setText(goalCarbsWithActivityAndDiet);
        TextView textViewGoalFatWithActivityAndDiet = (TextView)getActivity().findViewById(R.id.textViewGoalFatWithActivityAndDiet);
        textViewGoalFatWithActivityAndDiet.setText(goalFatWithActivityAndDiet);

        // 3 BMR
        TextView textViewGoalEnergyBMR = (TextView)getActivity().findViewById(R.id.textViewGoalEnergyBMR);
        textViewGoalEnergyBMR.setText(goalEnergyBmr);
        TextView textViewGoalProteinsBMR = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsBMR);
        textViewGoalProteinsBMR.setText(goalProteinsBmr);
        TextView textViewGoalCarbsBMR = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsBMR);
        textViewGoalCarbsBMR.setText(goalCarbsBmr);
        TextView textViewGoalFatBMR = (TextView)getActivity().findViewById(R.id.textViewGoalFatBMR);
        textViewGoalFatBMR.setText(goalFatBmr);


        // 4 WithActivity
        TextView textViewGoalEnergyWithActivity = (TextView)getActivity().findViewById(R.id.textViewGoalEnergyWithActivity);
        textViewGoalEnergyWithActivity.setText(goalEnergyWithActivity);
        TextView textViewGoalProteinsWithActivity = (TextView)getActivity().findViewById(R.id.textViewGoalProteinsWithActivity);
        textViewGoalProteinsWithActivity.setText(goalProteinsWithActivity);
        TextView textViewGoalCarbsWithActivity = (TextView)getActivity().findViewById(R.id.textViewGoalCarbsWithActivity);
        textViewGoalCarbsWithActivity.setText(goalCarbsWithActivity);
        TextView textViewGoalFatWithActivity = (TextView)getActivity().findViewById(R.id.textViewGoalFatWithActivity);
        textViewGoalFatWithActivity.setText(goalFatWithActivity);



        db.close();
    } // updateTable



    /*- Fragment  methods -*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
