package com.stayfit.app.stayfitBharat;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Calendar;

public class AddFoodToDiaryFragment extends Fragment {

    /*- 01 Class Variables -------------------------------------------------------------- */
    private View mainView;
    private Cursor listCursorCategory;
    private Cursor listCursorFood;

    // Action buttons on toolbar
    private MenuItem menuItemEdit;
    private MenuItem menuItemDelete;

    // Holder
    private String currentMealNumber;
    private String currentCategoryId;
    private String currentCategoryName;
    private String currentFoodId;
    private String currentFoodName;

    private boolean lockPortionSizeByPcs;
    private boolean lockPortionSizeByGram;


    /*- 02 Fragment Variables ----------------------------------------------------------- */
    // Nessesary for making fragment run
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    /*- 03 Constructur ------------------------------------------------------------------ */
    // Nessesary for having Fragment as class
    public AddFoodToDiaryFragment() {
        // Required empty public constructor
    }

    /*- 04 Creating Fragment ------------------------------------------------------------- */
    public static AddFoodToDiaryFragment newInstance(String param1, String param2) {
        AddFoodToDiaryFragment fragment = new AddFoodToDiaryFragment();
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
        ((FragmentActivity)getActivity()).getSupportActionBar().setTitle("Add food to diary");


        /* Get data from fragment */
        String stringAction = "";
        Bundle bundle = this.getArguments();
        if(bundle != null){
            currentMealNumber = bundle.getString("mealNumber");
            currentFoodId = bundle.getString("currentFoodId");
            stringAction = bundle.getString("action");
        }

        if(stringAction.equals("")) {
            // Populate the list of categories
            populateListWithCategories("0", "");
        }
        else if(stringAction.equals("foodInCategoryListItemClicked")){
            preFoodInCategoryListItemClicked();
        }

        // Create menu
        // setHasOptionsMenu(true);


    } // onActivityCreated

    /*- 06 On create view ---------------------------------------------------------------- */
    // Sets main View variable to the view, so we can change views in fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_add_food_to_diary, container, false);
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
        ((FragmentActivity)getActivity()).getMenuInflater().inflate(R.menu.menu_categories, menu);

        // Assign menu items to variables
        menuItemEdit = menu.findItem(R.id.action_edit);
        menuItemDelete = menu.findItem(R.id.action_delete);

        // Hide as default
        menuItemEdit.setVisible(false);
        menuItemDelete.setVisible(false);
    }


    /*- 09 on Options Item Selected ------------------------------------------------------ */
    // Action icon clicked on
    // Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
       /* if (id == R.id.action_add) {
            createNewCategory();
        }*/
        return super.onOptionsItemSelected(menuItem);
    }



    /*- Our own methods -*/

    /*- Populate list with categories -------------------------------------------------------- */
    public void populateListWithCategories(String stringCategoryParentID, String stringCatgoryName){

        /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        // Get categories
        String fields[] = new String[] {
                "_id",
                "category_name",
                "category_parent_id"
        };
        listCursorCategory = db.select("categories", fields, "category_parent_id", stringCategoryParentID, "category_name", "ASC");

        // Createa a array
        ArrayList<String> values = new ArrayList<String>();

        // Convert categories to string
        int categoriesCount = listCursorCategory.getCount();
        for(int x=0;x<categoriesCount;x++){
            values.add(listCursorCategory.getString(listCursorCategory.getColumnIndex("category_name")));

            /* Toast.makeText(getActivity(),
                    "Id: " + categoriesCursor.getString(0) + "\n" +
                            "Name: " + categoriesCursor.getString(1), Toast.LENGTH_SHORT).show();*/
            listCursorCategory.moveToNext();
        }


        // Close cursor
        // categoriesCursor.close();

        // Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);

        // Set Adapter
        ListView lv = (ListView)getActivity().findViewById(R.id.listViewAddFoodToDiary);
        lv.setAdapter(adapter);

        // OnClick
        if(stringCategoryParentID.equals("0")) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    categoryListItemClicked(arg2);
                }
            });
        }

        // Close db
        db.close();

    } // populateListWithCategories

    /*- Category list item clicked ---------------------------------------------------- */
    public void categoryListItemClicked(int listItemIndexClicked){

        // Move cursor to ID clicked
        listCursorCategory.moveToPosition(listItemIndexClicked);

        // Get ID and name from cursor
        // Set current name and id
        currentCategoryId = listCursorCategory.getString(0);
        currentCategoryName = listCursorCategory.getString(1);
        String parentCategoryID = listCursorCategory.getString(2);

        // Change title
        ((FragmentActivity)getActivity()).getSupportActionBar().setTitle("Add food from " + currentCategoryName + " to diary");


        // Move to sub class
        populateListWithCategories(currentCategoryId, currentCategoryName);


        // Show food in category
        showFoodInCategory(currentCategoryId, currentCategoryName, parentCategoryID);


    }

    /*- Show food in category ----------------------------------------------------------------- */
    public void showFoodInCategory(String categoryId, String categoryName, String categoryParentID){
        if(!(categoryParentID.equals("0"))) {
            //Toast.makeText(getActivity(), "Cat: + " + categoryName + " + Parent: " + categoryParentID, Toast.LENGTH_SHORT).show();

            /* Change layout */
            int id = R.layout.fragment_food;
            setMainView(id);

            /* Database */
            DBAdapter db = new DBAdapter(getActivity());
            db.open();

            // Get categories
            String fields[] = new String[] {
                    "_id",
                    "food_name",
                    "food_manufactor_name",
                    "food_description",
                    "food_serving_size_gram",
                    "food_serving_size_gram_mesurment",
                    "food_serving_size_pcs",
                    "food_serving_size_pcs_mesurment",
                    "food_energy_calculated"
            };
            listCursorFood = db.select("food", fields, "food_category_id", categoryId, "food_name", "ASC");


            // Find ListView to populate
            ListView lvItems = (ListView)getActivity().findViewById(R.id.listViewFood);



            // Setup cursor adapter using cursor from last step
            FoodCursorAdapter continentsAdapter = new FoodCursorAdapter(getActivity(), listCursorFood);

            // Attach cursor adapter to the ListView
            try{
                lvItems.setAdapter(continentsAdapter); // uses ContinensCursorAdapter
            }
            catch (Exception e){
                Toast.makeText(getActivity(), "E: " + e.toString(), Toast.LENGTH_LONG).show();
            }


            // OnClick
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    foodInCategoryListItemClicked(arg2);
                }
            });


            // Close db
            db.close();

        } //categoryParentID.equals
    } // showFoodInCategory


    /*- Pre Food in category list item clicked ------------------------------------------------ */
    // We are coming from another class
    public void preFoodInCategoryListItemClicked() {

            /* Database */
        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        // Get categories
        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size_gram",
                "food_serving_size_gram_mesurment",
                "food_serving_size_pcs",
                "food_serving_size_pcs_mesurment",
                "food_energy_calculated"
        };

        String currentFoodIdSQL = db.quoteSmart(currentFoodId);

        listCursorFood = db.select("food", fields, "_id", currentFoodIdSQL);

        int simulatedIndex = 0;
        foodInCategoryListItemClicked(simulatedIndex);

    } // preFoodInCategoryListItemClicked

    /*- Food in category list item clicked ------------------------------------------------ */
    public void foodInCategoryListItemClicked(int listItemFoodIndexClicked){

        // Toast.makeText(getActivity(), "Meal number: "+ currentMealNumber, Toast.LENGTH_LONG).show();

        /* Change layout */
        int id = R.layout.fragment_add_food_to_diary_view_food;
        setMainView(id);

        // Move cursor to ID clicked
        listCursorFood.moveToPosition(listItemFoodIndexClicked);

        // Get ID and name from cursor
        // Set current name and id
        currentFoodId = listCursorFood.getString(0);
        currentFoodName = listCursorFood.getString(1);

        // Change title
        ((FragmentActivity)getActivity()).getSupportActionBar().setTitle("Add " + currentFoodName);


        /*  Get data from database */

        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();


        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size_gram",
                "food_serving_size_gram_mesurment",
                "food_serving_size_pcs",
                "food_serving_size_pcs_mesurment",
                "food_energy",
                "food_proteins",
                "food_carbohydrates",
                "food_fat",
                "food_energy_calculated",
                "food_proteins_calculated",
                "food_carbohydrates_calculated",
                "food_fat_calculated",
                "food_user_id",
                "food_barcode",
                "food_category_id",
                "food_image_a",
                "food_image_b",
                "food_image_c"
        };
        String currentIdSQL = db.quoteSmart(currentFoodId);
        Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

        // Convert cursor to strings
        String stringId = foodCursor.getString(0);
        String stringName = foodCursor.getString(1);
        String stringManufactorName = foodCursor.getString(2);
        String stringDescription = foodCursor.getString(3);
        String stringServingSize = foodCursor.getString(4);
        String stringServingMesurment = foodCursor.getString(5);
        String stringServingNameNumber = foodCursor.getString(6);
        String stringServingNameWord = foodCursor.getString(7);
        String stringEnergy = foodCursor.getString(8);
        String stringProteins = foodCursor.getString(9);
        String stringCarbohydrates = foodCursor.getString(10);
        String stringFat = foodCursor.getString(11);
        String stringEnergyCalculated = foodCursor.getString(12);
        String stringProteinsCalculated = foodCursor.getString(13);
        String stringCarbohydratesCalculated = foodCursor.getString(14);
        String stringFatCalculated = foodCursor.getString(15);
        String stringUserId = foodCursor.getString(16);
        String stringBarcode = foodCursor.getString(17);
        String stringCategoryId = foodCursor.getString(18);
        String stringImageA = foodCursor.getString(19);
        String stringImageB = foodCursor.getString(20);
        String stringImageC = foodCursor.getString(21);


        // Headline
        TextView textViewViewFoodName = (TextView) getView().findViewById(R.id.textViewViewFoodName);
        textViewViewFoodName.setText(stringName);

        // Sub headline
        TextView textViewViewFoodManufactorName = (TextView) getView().findViewById(R.id.textViewViewFoodManufactorName);
        textViewViewFoodManufactorName.setText(stringManufactorName);


        // Portion size
        EditText editTextPortionSizePcs = (EditText)getActivity().findViewById(R.id.editTextPortionSizePcs);
        editTextPortionSizePcs.setText(stringServingNameNumber);

        TextView textViewPcs = (TextView)getActivity().findViewById(R.id.textViewPcs);
        textViewPcs.setText(stringServingNameWord);

        // Portion gram
        EditText editTextPortionSizeGram = (EditText)getActivity().findViewById(R.id.editTextPortionSizeGram);
        editTextPortionSizeGram.setText(stringServingSize);


        // Image

        // Calculation line
        TextView textViewViewFoodAbout = (TextView) getView().findViewById(R.id.textViewViewFoodAbout);
        String foodAbout = stringServingSize + " " + stringServingMesurment + " = " +
                stringServingNameNumber  + " " + stringServingNameWord + ".";
        textViewViewFoodAbout.setText(foodAbout);

        // Description
        TextView textViewViewFoodDescription = (TextView) getView().findViewById(R.id.textViewViewFoodDescription);
        textViewViewFoodDescription.setText(stringDescription);

        // Calories table
        TextView textViewViewFoodEnergyPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodEnergyPerHundred);
        TextView textViewViewFoodProteinsPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodProteinsPerHundred);
        TextView textViewViewFoodCarbsPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodCarbsPerHundred);
        TextView textViewViewFoodFatPerHundred = (TextView) getView().findViewById(R.id.textViewViewFoodFatPerHundred);

        TextView textViewViewFoodEnergyPerN = (TextView) getView().findViewById(R.id.textViewViewFoodEnergyPerN);
        TextView textViewViewFoodProteinsPerN = (TextView) getView().findViewById(R.id.textViewViewFoodProteinsPerN);
        TextView textViewViewFoodCarbsPerN = (TextView) getView().findViewById(R.id.textViewViewFoodCarbsPerN);
        TextView textViewViewFoodFatPerN = (TextView) getView().findViewById(R.id.textViewViewFoodFatPerN);

        textViewViewFoodEnergyPerHundred.setText(stringEnergy);
        textViewViewFoodProteinsPerHundred.setText(stringProteins);
        textViewViewFoodCarbsPerHundred.setText(stringCarbohydrates);
        textViewViewFoodFatPerHundred.setText(stringFat);

        textViewViewFoodEnergyPerN.setText(stringEnergyCalculated);
        textViewViewFoodProteinsPerN.setText(stringProteinsCalculated);
        textViewViewFoodCarbsPerN.setText(stringCarbohydratesCalculated);
        textViewViewFoodFatPerN.setText(stringFatCalculated);

        /* Listener for editTextPortionSizePcs */
        editTextPortionSizePcs.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))){
                    // My code here
                    editTextPortionSizePcsOnChange();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        editTextPortionSizePcs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                }else {
                    String lock = "portionSizePcs";
                    releaseLock(lock);
                }
            }
        });

        editTextPortionSizeGram.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))){
                    // My code here
                    editTextPortionSizeGramOnChange();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        editTextPortionSizeGram.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                }else {
                    String lock = "portionSizeGram";
                    releaseLock(lock);
                }
            }
        });



        /* Listener for editTextPortionSizeGram */
        /* Listner for add */
        Button buttonSubmit = (Button)getActivity().findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToDiary();
            }
        });

        /* Close db */
        db.close();




    } // foodInCategoryListItemClicked

    private void releaseLock(String lock){
        if(lock.equals("portionSizeGram")){
            lockPortionSizeByGram = false;
        }
        else {
            lockPortionSizeByPcs = false;
        }
    }

    /*- editTextPortionSizePcsOnChange ---------------------------------------------------- */
    public void editTextPortionSizePcsOnChange(){
        if(!(lockPortionSizeByGram)) {
            // Lock
            lockPortionSizeByPcs = true;

            // Get value of pcs
            EditText editTextPortionSizePcs = (EditText) getActivity().findViewById(R.id.editTextPortionSizePcs);
            String stringPortionSizePcs = editTextPortionSizePcs.getText().toString();

            double doublePortionSizePcs = 0;

            if (stringPortionSizePcs.equals("")) {
                doublePortionSizePcs = 0;
            } else {
                try {
                    doublePortionSizePcs = Double.parseDouble(stringPortionSizePcs);
                } catch (NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
            }


            // Database
            DBAdapter db = new DBAdapter(getActivity());
            db.open();

            String fields[] = new String[]{
                    "food_serving_size_gram"
            };
            String currentIdSQL = db.quoteSmart(currentFoodId);
            Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

            // Convert cursor to strings
            String stringServingSize = foodCursor.getString(0);
            db.close();


            // Convert cursor to double
            double doubleServingSize = 0;
            try {
                doubleServingSize = Double.parseDouble(stringServingSize);
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            // Calculate how much n portionsize is in gram
            // We are changing pcs
            // Update gram
            double doublePortionSizeGram = Math.round(doublePortionSizePcs * doubleServingSize);

            // Update portion size gram
            EditText editTextPortionSizeGram = (EditText) getActivity().findViewById(R.id.editTextPortionSizeGram);
            editTextPortionSizeGram.setText("" + doublePortionSizeGram);
        }
    } // editTextPortionSizePcs

    /*- editTextPortionSizeGramOnChange ---------------------------------------------------- */
    public void editTextPortionSizeGramOnChange(){
        if(!(lockPortionSizeByPcs)) {

            // Lock
            lockPortionSizeByGram = true;

            // Get value of gram
            EditText editTextPortionSizeGram = (EditText) getActivity().findViewById(R.id.editTextPortionSizeGram);
            String stringPortionSizeGram = editTextPortionSizeGram.getText().toString();
            double doublePortionSizeGram = 0;
            try {
                doublePortionSizeGram = Double.parseDouble(stringPortionSizeGram);
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            // Database
            DBAdapter db = new DBAdapter(getActivity());
            db.open();

            String fields[] = new String[]{
                    "food_serving_size_gram"
            };
            String currentIdSQL = db.quoteSmart(currentFoodId);
            Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

            // Convert cursor to strings
            String stringServingSizeGram = foodCursor.getString(0);
            db.close();


            // Convert cursor to double
            double doubleServingSizeGram = 0;
            try {
                doubleServingSizeGram = Double.parseDouble(stringServingSizeGram);
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }


            // Calculate pcs
            double doublePortionSizePcs = Math.round(doublePortionSizeGram / doubleServingSizeGram);


            // Update
            // Get value of pcs
            EditText editTextPortionSizePcs = (EditText) getActivity().findViewById(R.id.editTextPortionSizePcs);
            editTextPortionSizePcs.setText("" + doublePortionSizePcs);
        }

    } // editTextPortionSizeGramOnChange

    /* Add food to diary ------------------------------------------------------------------- */
    public void addFoodToDiary(){
        // We want to add food
        // Error
        int error = 0;

        // Database
        DBAdapter db = new DBAdapter(getActivity());
        db.open();


        String fields[] = new String[] {
                "_id",
                "food_name",
                "food_manufactor_name",
                "food_description",
                "food_serving_size_gram",
                "food_serving_size_gram_mesurment",
                "food_serving_size_pcs",
                "food_serving_size_pcs_mesurment",
                "food_energy",
                "food_proteins",
                "food_carbohydrates",
                "food_fat",
                "food_energy_calculated",
                "food_proteins_calculated",
                "food_carbohydrates_calculated",
                "food_fat_calculated",
                "food_user_id",
                "food_barcode",
                "food_category_id",
                "food_image_a",
                "food_image_b",
                "food_image_c"
        };
        String currentIdSQL = db.quoteSmart(currentFoodId);
        Cursor foodCursor = db.select("food", fields, "_id", currentIdSQL);

        // Convert cursor to strings
        String stringId = foodCursor.getString(0);
        String stringName = foodCursor.getString(1);
        String stringManufactorName = foodCursor.getString(2);
        String stringDescription = foodCursor.getString(3);
        String stringServingSizeGram = foodCursor.getString(4);
        String stringServingSizeGramMesurment = foodCursor.getString(5);
        String stringServingSizePcs = foodCursor.getString(6);
        String stringServingSizePcsMesurment = foodCursor.getString(7);
        String stringEnergy = foodCursor.getString(8);
        String stringProteins = foodCursor.getString(9);
        String stringCarbohydrates = foodCursor.getString(10);
        String stringFat = foodCursor.getString(11);
        String stringEnergyCalculated = foodCursor.getString(12);
        String stringProteinsCalculated = foodCursor.getString(13);
        String stringCarbohydratesCalculated = foodCursor.getString(14);
        String stringFatCalculated = foodCursor.getString(15);
        String stringUserId = foodCursor.getString(16);
        String stringBarcode = foodCursor.getString(17);
        String stringCategoryId = foodCursor.getString(18);
        String stringImageA = foodCursor.getString(19);
        String stringImageB = foodCursor.getString(20);
        String stringImageC = foodCursor.getString(21);


        // Get gram
        EditText editTextPortionSizeGram = (EditText)getActivity().findViewById(R.id.editTextPortionSizeGram);
        String fdServingSizeGram = editTextPortionSizeGram.getText().toString();
        String fdServingSizeGramSQL = db.quoteSmart(fdServingSizeGram);
        double doublePortionSizeGram = 0;
        try{
            doublePortionSizeGram = Double.parseDouble(fdServingSizeGram);
        }
        catch (NumberFormatException nfe){
            error = 1;
            Toast.makeText(getActivity(), "Please fill inn a number in gram", Toast.LENGTH_SHORT).show();
        }
        if(fdServingSizeGram.equals("")){
            error = 1;
            Toast.makeText(getActivity(), "Gram cannot be empty", Toast.LENGTH_SHORT).show();
        }



        // Date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Month
        month = month+1; // Month starts with 0
        String stringMonth = "";
        if(month < 10){
            stringMonth = "0" + month;
        }
        else{
            stringMonth = "" + month;
        }
        // Day
        String stringDay = "";
        if(day < 10){
            stringDay = "0" + day;
        }
        else{
            stringDay = "" + day;
        }


        String stringFdDate = year + "-" + stringMonth + "-" + stringDay;
        String stringFdDateSQL = db.quoteSmart(stringFdDate);

        // Meal number
        String stringFdMealNumber = currentMealNumber;
        String stringFdMealNumberSQL = db.quoteSmart(stringFdMealNumber);

        // Food id
        String stringFdFoodId = currentFoodId;
        String StringFdFoodIdSQL = db.quoteSmart(stringFdFoodId);

        // Serving size
        String fdServingSizeGramMesurmentSQL = db.quoteSmart(stringServingSizeGramMesurment);

        // Serving size pcs
        double doubleServingSizeGram = 0;
        try {
            doubleServingSizeGram = Double.parseDouble(stringServingSizeGram);
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        double doublePortionSizePcs = Math.round(doublePortionSizeGram / doubleServingSizeGram);
        String stringFdServingSizePcs = "" + doublePortionSizePcs;
        String stringFdServingSizePcsSQL = db.quoteSmart(stringFdServingSizePcs);

        // Serving size pcs mesurment
        String stringFdServingSizePcsMesurmentSQL = db.quoteSmart(stringServingSizePcsMesurment);

        // Energy calcualted
        // Example:
        //          energy
        // pr 100   152 kcal
        //
        // I eat 21 g
        //
        // energy = myConsumotion*kcal/100
        // Toast.makeText(getActivity(), "Energy: " + doubleFdEnergyCalculated, Toast.LENGTH_LONG).show();
        double doubleEnergyPerHundred = Double.parseDouble(stringEnergy);

        double doubleFdEnergyCalculated = Math.round((doublePortionSizeGram*doubleEnergyPerHundred)/100);
        String stringFdEnergyCalcualted = "" + doubleFdEnergyCalculated;
        String stringFdEnergyCalcualtedSQL = db.quoteSmart(stringFdEnergyCalcualted);

        // Proteins calcualted
        double doubleProteinsPerHundred = Double.parseDouble(stringProteins);

        double doubleFdProteinsCalculated = Math.round((doublePortionSizeGram*doubleProteinsPerHundred)/100);
        String stringFdProteinsCalcualted = "" + doubleFdProteinsCalculated;
        String stringFdProteinsCalcualtedSQL = db.quoteSmart(stringFdProteinsCalcualted);


        // Carbohydrates calcualted
        double doubleCarbohydratesPerHundred = Double.parseDouble(stringCarbohydrates);

        double doubleFdCarbohydratesCalculated = Math.round((doublePortionSizeGram*doubleCarbohydratesPerHundred)/100);
        String stringFdCarbohydratesCalcualted = "" + doubleFdCarbohydratesCalculated;
        String stringFdCarbohydratesCalcualtedSQL = db.quoteSmart(stringFdCarbohydratesCalcualted);

        // Fat calcualted
        double doubleFatPerHundred = Double.parseDouble(stringFat);

        double doubleFdFatCalculated = Math.round((doublePortionSizeGram*doubleFatPerHundred)/100);
        String stringFdFatCalcualted = "" + doubleFdFatCalculated;
        String stringFdFatCalcualtedSQL = db.quoteSmart(stringFdFatCalcualted);

        // Insert to SQL
        if(error == 0){
            String inpFields = "_id, fd_date, fd_meal_number, fd_food_id," +
                    "fd_serving_size_gram, fd_serving_size_gram_mesurment," +
                    " fd_serving_size_pcs, fd_serving_size_pcs_mesurment," +
                    " fd_energy_calculated, fd_protein_calculated," +
                    " fd_carbohydrates_calculated, fd_fat_calculated";

            String inpValues = "NULL, " + stringFdDateSQL + ", " + stringFdMealNumberSQL + ", " + StringFdFoodIdSQL + ", " +
                    fdServingSizeGramSQL + ", " + fdServingSizeGramMesurmentSQL + ", " +
                    stringFdServingSizePcsSQL + ", " + stringFdServingSizePcsMesurmentSQL + ", " +
                    stringFdEnergyCalcualtedSQL + ", " + stringFdProteinsCalcualtedSQL + ", " +
                    stringFdCarbohydratesCalcualtedSQL + ", " + stringFdFatCalcualtedSQL;

            db.insert("food_diary", inpFields, inpValues);

            Toast.makeText(getActivity(), "Food diary updated", Toast.LENGTH_SHORT).show();


            // Change fragment to HomeFragment
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = HomeFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        }



        // Close db
        db.close();
    } // addFoodToDiary



    /*- Fragment methods -*/


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
