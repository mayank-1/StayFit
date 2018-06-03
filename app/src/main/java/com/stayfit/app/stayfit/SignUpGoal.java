package com.stayfit.app.stayfit;

import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.stayfit.app.stayfit.R;

/**
 * Created by bruker on 19.06.2017.
 */

public class SignUpGoal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_goal);


        /* Listener submit */
        Button buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpGoalSubmit();
            }
        });

        /* Remove error handling */
        hideErrorHandling();

        /* Mesurment used? */
        mesurmentUsed();

    } // onCreate

    /* signUpGoalSubmit ----------------------------------------------------- */
    public void signUpGoalSubmit(){
        /* Open database */
        DBAdapter db = new DBAdapter(this);
        db.open();

        /* Error */
        ImageView imageViewError = (ImageView)findViewById(R.id.imageViewError);
        TextView textViewErrorMessage = (TextView)findViewById(R.id.textViewErrorMessage);
        String errorMessage = "";


        /* Get target weight */
        EditText editTextTargetWeight = (EditText)findViewById(R.id.editTextTargetWeight);
        String stringTargetWeight = editTextTargetWeight.getText().toString();
        double doubleTargetWeight = 0;
        try{
            doubleTargetWeight = Double.parseDouble(stringTargetWeight);
        }
        catch(NumberFormatException nfe) {
            errorMessage = "Target weight has to be a number.";
        }

        /* Spinner IWantTo */
        // 0 - Loose weight
        // 1 - Gain weight
        Spinner spinnerIWantTo = (Spinner)findViewById(R.id.spinnerIWantTo);



        int intIWantTo = spinnerIWantTo.getSelectedItemPosition();

        /* Spinner spinnerWeeklyGoal */
        Spinner spinnerWeeklyGoal = (Spinner)findViewById(R.id.spinnerWeeklyGoal);
        String stringWeeklyGoal = spinnerWeeklyGoal.getSelectedItem().toString();


        /* Update fields */
        if(errorMessage.isEmpty()){

            long goalID = 1;

            double doubleTargetWeightSQL = db.quoteSmart(doubleTargetWeight);
            db.update("goal", "_id", goalID, "goal_target_weight", doubleTargetWeightSQL);

            int intIWantToSQL = db.quoteSmart(intIWantTo);
            db.update("goal", "_id", goalID, "goal_i_want_to", intIWantToSQL);

            String stringWeeklyGoalSQL = db.quoteSmart(stringWeeklyGoal);
            db.update("goal", "_id", goalID, "goal_weekly_goal", stringWeeklyGoalSQL);

        }

        /* Calculate energy */
        if(errorMessage.isEmpty()){

            // Get row number one from users
            long rowID = 1;
            String fields[] = new String[] {
                    "_id",
                    "user_dob",
                    "user_gender",
                    "user_height"
            };
            Cursor c = db.select("users", fields, "_id", rowID);
            String stringUserDob = c.getString(1);
            String stringUserGender  = c.getString(2);
            String stringUserHeight = c.getString(3);

            // Get weight actvity level
            rowID = 1;
            String fieldsGoal[] = new String[] {
                    "_id",
                    "goal_current_weight",
                    "goal_activity_level"
            };
            Cursor cGoal = db.select("goal", fieldsGoal, "_id", rowID);
            String stringUserCurrentWeight = cGoal.getString(1);
            String stringUserActivityLevel = cGoal.getString(2);

            // Get weight
            double doubleUserCurrentWeight = 0;
            try{
                doubleUserCurrentWeight = Double.parseDouble(stringUserCurrentWeight);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }


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

            // Height
            double doubleUserHeight = 0;
            try {
                doubleUserHeight = Double.parseDouble(stringUserHeight);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            //Toast.makeText(this, "DOB=" + stringUserDob + "\nAge=" + stringUserAge + "\nGender=" + stringUserGender + "\nHeight=" + stringUserHeight + "\nActivity level=" + stringUserActivityLevel, Toast.LENGTH_LONG).show();


            long goalID = 1;


            /* 1: BRM */
            // Start calculation
            double goalEnergyBMR = 0;
            if(stringUserGender.startsWith("m")){
                // Male
                // BMR = 66.5 + (13.75 x kg body weight) + (5.003 x height in cm) - (6.755 x age)
                goalEnergyBMR = 66.5+(13.75*doubleUserCurrentWeight)+(5.003*doubleUserHeight)-(6.755*intUserAge);
                //bmr = Math.round(bmr);
                //Toast.makeText(this, "BMR formula: 66.5+(13.75*" + doubleUserCurrentWeight + ")+(5.003*" + doubleUserHeight + ")-(6.755*" + intUserAge + " = " + goalEnergyBMR, Toast.LENGTH_LONG).show();

            } // if(stringUserGender.startsWith("m")){
            else{
                // Female
                // BMR = 55.1 + (9.563 x kg body weight) + (1.850 x height in cm) - (4.676 x age)
                goalEnergyBMR = 655+(9.563*doubleUserCurrentWeight)+(1.850*doubleUserHeight)-(4.676*intUserAge);
                //bmr = Math.round(bmr);
            }
            goalEnergyBMR = Math.round(goalEnergyBMR);
            double energyBmrSQL = db.quoteSmart(goalEnergyBMR);
            db.update("goal", "_id", goalID, "goal_energy_bmr", energyBmrSQL);
            //Toast.makeText(this, "BMR before activity: " + bmr, Toast.LENGTH_LONG).show();

            // Proteins, carbs and fat with BMR
            // 20-25 % protein
            // 40-50 % carbs
            // 25-35 % fat
            double proteinsBmr = Math.round(goalEnergyBMR*25/100);
            double carbsBmr = Math.round(goalEnergyBMR*50/100);
            double fatBmr = Math.round(goalEnergyBMR*25/100);

            double proteinsBmrSQL = db.quoteSmart(proteinsBmr);
            double carbsBmrSQL = db.quoteSmart(carbsBmr);
            double fatBmrQL = db.quoteSmart(fatBmr);
            db.update("goal", "_id", goalID, "goal_proteins_bmr", proteinsBmrSQL);
            db.update("goal", "_id", goalID, "goal_carbs_bmr", carbsBmrSQL);
            db.update("goal", "_id", goalID, "goal_fat_bmr", fatBmrQL);

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
            db.update("goal", "_id", goalID, "goal_energy_diet", energyDietSQL);

            // Proteins, carbs and fat diet
            // 20-25 % protein
            // 40-50 % carbs
            // 25-35 % fat
            double proteinsDiet = Math.round(energyDiet*25/100);
            double carbsDiet = Math.round(energyDiet*50/100);
            double fatDiet = Math.round(energyDiet*25/100);

            double proteinsDietSQL = db.quoteSmart(proteinsDiet);
            double carbsDietSQL = db.quoteSmart(carbsDiet);
            double fatDietQL = db.quoteSmart(fatDiet);
            db.update("goal", "_id", goalID, "goal_proteins_diet", proteinsDietSQL);
            db.update("goal", "_id", goalID, "goal_carbs_diet", carbsDietSQL);
            db.update("goal", "_id", goalID, "goal_fat_diet", fatDietQL);


            /* 3: With activity */
            // Taking in to account activity
            double energyWithActivity = 0;
            if(stringUserActivityLevel.equals("0")) {
                energyWithActivity = goalEnergyBMR * 1.2;
            }
            else if(stringUserActivityLevel.equals("1")) {
                energyWithActivity = goalEnergyBMR * 1.375; // slightly_active
            }
            else if(stringUserActivityLevel.equals("2")) {
                energyWithActivity = goalEnergyBMR*1.55; // moderately_active
            }
            else if(stringUserActivityLevel.equals("3")) {
                energyWithActivity = goalEnergyBMR*1.725; // active_lifestyle
            }
            else if(stringUserActivityLevel.equals("4")) {
                energyWithActivity = goalEnergyBMR * 1.9; // very_active
            }
            energyWithActivity = Math.round(energyWithActivity);
            double energyWithActivitySQL = db.quoteSmart(energyWithActivity);
            db.update("goal", "_id", goalID, "goal_energy_with_activity", energyWithActivitySQL);
            //Toast.makeText(this, "BMR after activity: " + bmr, Toast.LENGTH_LONG).show();

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
            db.update("goal", "_id", goalID, "goal_proteins_with_activity", proteinsWithActivitySQL);
            db.update("goal", "_id", goalID, "goal_carbs_with_activity", carbsWithActivitySQL);
            db.update("goal", "_id", goalID, "goal_fat_with_activity", fatWithActivityQL);



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

            if(stringUserActivityLevel.equals("0")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet* 1.2;
            }
            else if(stringUserActivityLevel.equals("1")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet* 1.375; // slightly_active
            }
            else if(stringUserActivityLevel.equals("2")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet*1.55; // moderately_active
            }
            else if(stringUserActivityLevel.equals("3")) {
                energyWithActivityAndDiet= energyWithActivityAndDiet*1.725; // active_lifestyle
            }
            else if(stringUserActivityLevel.equals("4")) {
                energyWithActivityAndDiet = energyWithActivityAndDiet* 1.9; // very_active
            }
            energyWithActivityAndDiet = Math.round(energyWithActivityAndDiet);

            // Update database
            double energyWithActivityAndDietSQL = db.quoteSmart(energyWithActivityAndDiet);
            db.update("goal", "_id", goalID, "goal_energy_with_activity_and_diet", energyWithActivityAndDietSQL);


            // Calcualte proteins
            // 20-25 % protein
            // 40-50 % carbs
            // 25-35 % fat
            double proteins = Math.round(energyWithActivityAndDiet*25/100);
            double carbs = Math.round(energyWithActivityAndDiet*50/100);
            double fat = Math.round(energyWithActivityAndDiet*25/100);

            double proteinsSQL = db.quoteSmart(proteins);
            double carbsSQL = db.quoteSmart(carbs);
            double fatSQL = db.quoteSmart(fat);
            db.update("goal", "_id", goalID, "goal_proteins_with_activity_and_diet", proteinsSQL);
            db.update("goal", "_id", goalID, "goal_carbs_with_activity_and_diet", carbsSQL);
            db.update("goal", "_id", goalID, "goal_fat_with_activity_and_diet", fatSQL);

        } //  /* Calculate energy */



        // Error handling
        if(!(errorMessage.isEmpty())){
            // There is error
            textViewErrorMessage.setText(errorMessage);
            imageViewError.setVisibility(View.VISIBLE);
            textViewErrorMessage.setVisibility(View.VISIBLE);

        }

        /* Close db */
        db.close();

        /* Move to main activity */
        if(errorMessage.isEmpty()){
            Intent i = new Intent(SignUpGoal.this, MainActivity.class);
            startActivity(i);
        }
    } // signUpGoalSubmit

    /* hideErrorHandling --------------------------------------------------- */
    public void hideErrorHandling(){
        /* Hide error icon and message */
        ImageView imageViewError = (ImageView)findViewById(R.id.imageViewError);
        imageViewError.setVisibility(View.GONE);

        TextView textViewErrorMessage = (TextView)findViewById(R.id.textViewErrorMessage);
        textViewErrorMessage.setVisibility(View.GONE);

    }

    /* mesurmentUsed ------------------------------------------------------- */
    public void mesurmentUsed(){
        /* Open database */
        DBAdapter db = new DBAdapter(this);
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

        // Metric or imperial?
        if(mesurment.startsWith("m")){
            // Metric
        }
        else{
            // Imperial

            // Kg to punds
            TextView textViewTargetMesurmentType = (TextView)findViewById(R.id.textViewTargetMesurmentType);
            textViewTargetMesurmentType.setText("pounds");


            // Kg each week to pounds each week
            TextView textViewKgEachWeek = (TextView)findViewById(R.id.textViewKgEachWeek);
            textViewKgEachWeek.setText("pounds each week");
        }


        /* Close database */
        db.close();
    }

    /* getAge -------------------------------------------------------------- */
    private String getAge(int year, int month, int day){
        Calendar dob = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dob = Calendar.getInstance();
        }
        Calendar today = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            today = Calendar.getInstance();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dob.set(year, month, day);
        }

        int age = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
                age--;
            }
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
