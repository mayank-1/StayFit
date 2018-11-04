package com.stayfit.app.stayfitBharat;

/**
 * Created by bruker on 19.06.2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteException;

public class DBSetupInsert {

    /* Variables */
    private final Context context;

    /* Public Class ------------------------------------------------------ */
    public DBSetupInsert(Context ctx){
        this.context = ctx;
    }



    /* Setup Insert To Categories ----------------------------------------- */
    // To insert to category table
    public void setupInsertToCategories(String values){
        try{
            DBAdapter db = new DBAdapter(context);
            db.open();
            db.insert("categories",
                    "_id, category_name, category_parent_id, category_icon, category_note",
                    values);
            db.close();
        }
        catch (SQLiteException e){
            // Toast.makeText(context, "Error; Could not insert categories.", Toast.LENGTH_SHORT).show();
        }
    }
    public void insertAllCategories(){
        setupInsertToCategories("NULL, 'Bread', '0', '', NULL");
        setupInsertToCategories("NULL, 'Bread', '1', '', NULL");
        setupInsertToCategories("NULL, 'Cereals', '1', '', NULL");
        setupInsertToCategories("NULL, 'Frozen bread and rolls', '1', '', NULL");
        setupInsertToCategories("NULL, 'Crispbread', '1', '', NULL");

        // Parent id: 6
        setupInsertToCategories("NULL, 'Dessert and baking', '0', '', NULL");
        setupInsertToCategories("NULL, 'Baking', '6', '', NULL");
        setupInsertToCategories("NULL, 'Biscuit', '6', '', NULL");


        setupInsertToCategories("NULL, 'Drinks', '0', '', NULL");
        setupInsertToCategories("NULL, 'Soda', '9', '', NULL");


        setupInsertToCategories("NULL, 'Fruit and vegetables', '0', '', NULL");
        setupInsertToCategories("NULL, 'Frozen fruits and vegetables', '11', '', NULL");
        setupInsertToCategories("NULL, 'Fruit', '11', '', NULL");
        setupInsertToCategories("NULL, 'Vegetables', '11', '', NULL");
        setupInsertToCategories("NULL, 'Canned fruits and vegetables', '11', '', NULL");


        setupInsertToCategories("NULL, 'Health', '0', '', NULL");
        setupInsertToCategories("NULL, 'Meal substitutes', '16', '', NULL");
        setupInsertToCategories("NULL, 'Protein bars', '16', '', NULL");
        setupInsertToCategories("NULL, 'Protein powder', '16', '', NULL");


        setupInsertToCategories("NULL, 'Meat, chicken and fish', '0', '', NULL");
        setupInsertToCategories("NULL, 'Meat', '20', '', NULL");
        setupInsertToCategories("NULL, 'Chicken', '20', '', NULL");
        setupInsertToCategories("NULL, 'Seafood', '20', '', NULL");


        setupInsertToCategories("NULL, 'Dairy and eggs', '0', '', NULL");
        setupInsertToCategories("NULL, 'Eggs', '24', '', NULL");
        setupInsertToCategories("NULL, 'Cream and sour cream', '24', '', NULL");
        setupInsertToCategories("NULL, 'Yogurt', '24', '', NULL");


        setupInsertToCategories("NULL, 'Dinner', '0', '', NULL");
        setupInsertToCategories("NULL, 'Ready dinner dishes', '28', '', NULL");
        setupInsertToCategories("NULL, 'Pizza', '28', '', NULL");
        setupInsertToCategories("NULL, 'Noodle', '28', '', NULL");
        setupInsertToCategories("NULL, 'Pasta', '28', '', NULL");
        setupInsertToCategories("NULL, 'Rice', '28', '', NULL");
        setupInsertToCategories("NULL, 'Taco', '28', '', NULL");


        setupInsertToCategories("NULL, 'Cheese', '0', '', NULL");
        setupInsertToCategories("NULL, 'Cream cheese', '35', '', NULL");


        setupInsertToCategories("NULL, 'On bread', '0', '', NULL");
        setupInsertToCategories("NULL, 'Cold meats', '37', '', NULL");
        setupInsertToCategories("NULL, 'Sweet spreads', '37', '', NULL");
        setupInsertToCategories("NULL, 'Jam', '37', '', NULL");


        setupInsertToCategories("NULL, 'Snacks', '0', '', NULL");
        setupInsertToCategories("NULL, 'Nuts', '41', '', NULL");
        setupInsertToCategories("NULL, 'Potato chips', '41', '', NULL");
    }




    /* Setup Insert To Food ----------------------------------------------- */
    // To insert food to food table
    public void setupInsertToFood(String values){

        try {
            DBAdapter db = new DBAdapter(context);
            db.open();
            db.insert("food",
                    "_id, food_name, food_manufactor_name, food_serving_size_gram, food_serving_size_gram_mesurment, food_serving_size_pcs, food_serving_size_pcs_mesurment, food_energy, food_proteins, food_carbohydrates, food_fat, food_energy_calculated, food_proteins_calculated, food_carbohydrates_calculated, food_fat_calculated, food_user_id, food_barcode, food_category_id, food_thumb, food_image_a, food_image_b, food_image_c, food_notes",
                    values);
            db.close();
        }
        catch (SQLiteException e){
            // Toast.makeText(context, "Error; Could not insert food.", Toast.LENGTH_SHORT).show();
        }

    }
    // Insert all food into food database
    public void insertAllFood(){
        setupInsertToFood("NULL, 'Speltlompe med havre', 'Aulie', '26', 'gram', '1', 'stk', '122', '3.5', '23.4', '1', '32', '1', '6', '0', NULL, NULL, '2', 'aulie_speltlomper_med_havre_thumb.jpg', 'aulie_speltlomper_med_havre_a.jpg', 'aulie_speltlomper_med_havre_b.jpg', 'aulie_speltlomper_med_havre_c.jpg', NULL");
        setupInsertToFood("NULL, 'Havregryn lettkokt', 'Axa', '60', 'gram', '60', 'g', '389', '11.4', '63.1', '7.8', '233', '7', '38', '5', NULL, NULL, '3', 'axa_havregryn_lettkokt_thumb.jpg', 'axa_havregryn_lettkokt_a.jpg', 'axa_havregryn_lettkokt_b.jpg', 'axa_havregryn_lettkokt_c.jpg', NULL");
        setupInsertToFood("NULL, 'Havregryn store', 'Axa', '80', 'gram', '80', 'g', '380', '13', '61', '7', '304', '10', '49', '6', NULL, NULL, '3', 'axa_havregryn_store_thumb.jpg', 'axa_havregryn_store_a.jpg', 'axa_havregryn_store_b.jpg', 'axa_havregryn_store_c.jpg', NULL");
        setupInsertToFood("NULL, 'Frosne pitaprød', 'Hatting', '80', 'gram', '1', 'stk', '251', '8.1', '50', '1.5', '201', '6', '40', '1', NULL, NULL, '4', 'hatting_frosne_pitabrod_thumb.jpg', 'hatting_frosne_pitabrod_a.jpg', 'hatting_frosne_pitabrod_b.jpg', 'hatting_frosne_pitabrod_c.jpg', NULL");
        setupInsertToFood("NULL, 'Frosne ekstra grove rundstykker', 'Hvita hjertegod', '70', 'gram', '1', 'stk', '250', '18', '25', '6', '175', '13', '18', '4', NULL, NULL, '4', 'hvita_hjertegod_frosne_ekstra_grove_rundstykker_thumb.jpg', 'hvita_hjertegod_frosne_ekstra_grove_rundstykker_a.jpg', 'hvita_hjertegod_frosne_ekstra_grove_rundstykker_b.jpg', 'hvita_hjertegod_frosne_ekstra_grove_rundstykker_c.jpg', NULL");
        setupInsertToFood("NULL, 'Steinovnsbakte solsikkebriks', 'Rema 1000', '60', 'gram', '1', 'stk', '260', '10', '39', '0.5', '156', '6', '23', '0', NULL, NULL, '4', 'rema_1000_steinovnsbakte_solsikkebriks_thumb.jpg', 'rema_1000_steinovnsbakte_solsikkebriks_a.jpg', 'rema_1000_steinovnsbakte_solsikkebriks_b.jpg', 'rema_1000_steinovnsbakte_solsikkebriks_c.jpg', NULL");
        setupInsertToFood("NULL, 'Fiber Balance', 'Wasa', '10', 'gram', '1', 'stk', '330', '14', '43', '5.5', '33', '1', '4', '1', NULL, NULL, '5', 'wasa_fiber_balance_thumb.jpg', 'wasa_fiber_balance_a.jpg', 'wasa_fiber_balance_b.jpg', 'wasa_fiber_balance_c.jpg', NULL");
        setupInsertToFood("NULL, 'Sport +', 'Wasa', '16', 'gram', '1', 'stk', '338', '10.5', '55.5', '3.5', '54', '2', '9', '1', NULL, NULL, '5', 'wasa_sport_pluss_thumb.jpg', 'wasa_sport_pluss_a.jpg', 'wasa_sport_pluss_b.jpg', 'wasa_sport_pluss_c.jpg', NULL");
        setupInsertToFood("NULL, 'Sukker', 'Dan Sukker', '100', 'gram', '100', 'gram', '400', '0', '100', '0', '400', '0', '100', '0', NULL, NULL, '7', 'dan_sukker_sukker_thumb.jpg', 'dan_sukker_sukker_a.jpg', 'dan_sukker_sukker_b.jpg', 'dan_sukker_sukker_c.jpg', NULL");
        setupInsertToFood("NULL, 'Siktet hvetemel', 'Møllerens', '100', 'gram', '100', 'gram', '341', '10.2', '69.6', '1.6', '341', '10', '70', '2', NULL, NULL, '7', 'moollerens_siktet_hvetemel_thumb.jpg', 'moollerens_siktet_hvetemel_a.jpg', 'moollerens_siktet_hvetemel_b.jpg', 'moollerens_siktet_hvetemel_c.jpg', NULL");
        setupInsertToFood("NULL, 'Ritz Crackers', 'Mondelez Norge', '200', 'gram', '1', 'pakke', '490', '7.8', '61', '23', '980', '16', '122', '46', NULL, NULL, '8', 'mondelez_norge_ritz_crackers_thumb.jpg', 'mondelez_norge_ritz_crackers_a.jpg', 'mondelez_norge_ritz_crackers_b.jpg', 'mondelez_norge_ritz_crackers_c.jpg', NULL");
        setupInsertToFood("NULL, 'Battery Energy Drink 50cl', 'Ringnes', '500', 'gram', '1', 'boks', '50', '0.4', '11.5', '0', '250', '2', '58', '0', NULL, NULL, '10', 'ringnes_battery_energy_drink_50cl_thumb.jpg', 'ringnes_battery_energy_drink_50cl_a.jpg', 'ringnes_battery_energy_drink_50cl_b.jpg', 'ringnes_battery_energy_drink_50cl_c.jpg', NULL");
        setupInsertToFood("NULL, 'Frossen brokkoliblanding', 'Eldorado', '250', 'gram', '0.5', 'pose', '26', '1.9', '4.2', '0.2', '65', '5', '11', '1', NULL, NULL, '12', 'eldorado_frossen_brokkoliblanding_thumb.jpg', 'eldorado_frossen_brokkoliblanding_a.jpg', 'eldorado_frossen_brokkoliblanding_b.jpg', 'eldorado_frossen_brokkoliblanding_c.jpg', NULL");
        setupInsertToFood("NULL, 'Fosne brokkolitopper', 'Rema 1000', '225', 'gram', '0.5', 'pose', '27', '2.8', '1.9', '0.5', '61', '6', '4', '1', NULL, NULL, '12', 'rema_1000_frosne_brokkolitopper_thumb.jpg', 'rema_1000_frosne_brokkolitopper_a.jpg', 'rema_1000_frosne_brokkolitopper_b.jpg', 'rema_1000_frosne_brokkolitopper_c.jpg', NULL");
        setupInsertToFood("NULL, 'Røde druer', 'Bama', '200', 'gram', '0.5', 'pakke', '62', '0.7', '13.8', '0.2', '124', '1', '28', '0', NULL, NULL, '13', 'bama_rode_druer_thumb.jpg', 'bama_rode_druer_a.jpg', 'bama_rode_druer_b.jpg', 'bama_rode_druer_c.jpg', NULL");
        setupInsertToFood("NULL, 'Brokkoli', 'Bama', '300', 'gram', '0.5', 'stk', '33', '2.8', '7', '0.4', '99', '8', '21', '1', NULL, NULL, '14', 'bama_brokkoli_thumb.jpg', 'bama_brokkoli_a.jpg', 'bama_brokkoli_b.jpg', 'bama_brokkoli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Gulrot', 'Bama', '44', 'gram', '1', 'stk', '41', '0.9', '10', '0.2', '18', '0', '4', '0', NULL, NULL, '14', 'bama_gulrot_thumb.jpg', 'bama_gulrot_a.jpg', 'bama_gulrot_b.jpg', 'bama_gulrot_c.jpg', NULL");
        setupInsertToFood("NULL, 'Isberg mix', 'Bama', '125', 'gram', '0.5', 'pakke', '14', '0.8', '2.2', '0.1', '18', '1', '3', '0', NULL, NULL, '14', 'bama_isberg_mix_thumb.jpg', 'bama_isberg_mix_a.jpg', 'bama_isberg_mix_b.jpg', 'bama_isberg_mix_c.jpg', NULL");
        setupInsertToFood("NULL, 'Isbergsalat', 'Bama', '50', 'gram', '1', 'neve', '12', '0.8', '1.5', '0.1', '6', '0', '1', '0', NULL, NULL, '14', 'bama_isbergsalat_thumb.jpg', 'bama_isbergsalat_a.jpg', 'bama_isbergsalat_b.jpg', 'bama_isbergsalat_c.jpg', NULL");
        setupInsertToFood("NULL, 'Meksikansk salat', 'Bama', '135', 'gram', '0.5', 'pakke', '140', '3', '3.1', '11.2', '189', '4', '4', '15', NULL, NULL, '14', 'bama_meksikansk_salat_thumb.jpg', 'bama_meksikansk_salat_a.jpg', 'bama_meksikansk_salat_b.jpg', 'bama_meksikansk_salat_c.jpg', NULL");
        setupInsertToFood("NULL, 'Rød paprika', 'Bama', '36', 'gram', '0.25', 'stk', '30', '1', '4.7', '0.4', '11', '0', '2', '0', NULL, NULL, '14', 'bama_rod_paprika_thumb.jpg', 'bama_rod_paprika_a.jpg', 'bama_rod_paprika_b.jpg', 'bama_rod_paprika_c.jpg', NULL");
        setupInsertToFood("NULL, 'Romano mix', 'Bama', '88', 'gram', '0.5', 'pakke', '13', '1.3', '1.6', '0.2', '11', '1', '1', '0', NULL, NULL, '14', 'bama_romano_mix_thumb.jpg', 'bama_romano_mix_a.jpg', 'bama_romano_mix_b.jpg', 'bama_romano_mix_c.jpg', NULL");
        setupInsertToFood("NULL, 'Baked beans', 'Coop', '420', 'gram', '1', 'boks', '116', '5', '19', '0.5', '487', '21', '80', '2', NULL, NULL, '15', 'coop_baked_beans_thumb.jpg', 'coop_baked_beans_a.jpg', 'coop_baked_beans_b.jpg', 'coop_baked_beans_c.jpg', NULL");
        setupInsertToFood("NULL, 'Kokosmelk', 'Eldorado', '60', 'gram', '0.25', 'boks', '68', '0.8', '3.3', '5.7', '41', '0', '2', '3', NULL, NULL, '15', 'eldorado_kokosmelk_thumb.jpg', 'eldorado_kokosmelk_a.jpg', 'eldorado_kokosmelk_b.jpg', 'eldorado_kokosmelk_c.jpg', NULL");
        setupInsertToFood("NULL, 'Lett kokosmelk', 'Eldorado', '60', 'gram', '0.25', 'boks', '68', '0.8', '3.3', '5.7', '41', '0', '2', '3', NULL, NULL, '15', 'eldorado_lett_kokosmelk_thumb.jpg', 'eldorado_lett_kokosmelk_a.jpg', 'eldorado_lett_kokosmelk_b.jpg', 'eldorado_lett_kokosmelk_c.jpg', NULL");
        setupInsertToFood("NULL, 'Maiskorn', 'Eldorado', '99', 'gram', '0.5', 'boks', '76', '2.3', '14', '1', '75', '2', '14', '1', NULL, NULL, '15', 'eldorado_maiskorn_thumb.jpg', 'eldorado_maiskorn_a.jpg', 'eldorado_maiskorn_b.jpg', 'eldorado_maiskorn_c.jpg', NULL");
        setupInsertToFood("NULL, 'Tomatbønner', 'Nora', '420', 'gram', '1', 'boks', '83', '3.8', '14', '0.6', '349', '16', '59', '3', NULL, NULL, '15', 'nora_tomatboonner_thumb.jpg', 'nora_tomatboonner_a.jpg', 'nora_tomatboonner_b.jpg', 'nora_tomatboonner_c.jpg', NULL");
        setupInsertToFood("NULL, 'Bønner i tomatsaus', 'Rema 1000', '420', 'gram', '1', 'boks', '95', '5.3', '15', '0.6', '399', '22', '63', '3', NULL, NULL, '15', 'rema_1000_boonner_i_tomatsaus_thumb.jpg', 'rema_1000_boonner_i_tomatsaus_a.jpg', 'rema_1000_boonner_i_tomatsaus_b.jpg', 'rema_1000_boonner_i_tomatsaus_c.jpg', NULL");
        setupInsertToFood("NULL, 'Power Meal Bringebærsmak', 'Strongr', '80', 'gram', '1', 'skje', '396', '37.5', '39.5', '9.6', '317', '30', '32', '8', NULL, NULL, '17', 'strongr_power_meal_thumb.jpg', 'strongr_power_meal_a.jpg', 'strongr_power_meal_b.jpg', 'strongr_power_meal_c.jpg', NULL");
        setupInsertToFood("NULL, 'Pure Protein Meal Sjokoladesmak', 'Strongr', '70', 'gram', '1', 'skje', '382', '43.3', '43.2', '4.8', '267', '30', '30', '3', NULL, NULL, '17', 'strongr_pure_protein_meal_sjokoladesmak_thumb.jpg', 'strongr_pure_protein_meal_sjokoladesmak_a.jpg', 'strongr_pure_protein_meal_sjokoladesmak_b.jpg', 'strongr_pure_protein_meal_sjokoladesmak_c.jpg', NULL");
        setupInsertToFood("NULL, 'MealTech', 'Tech Nutrition', '80', 'gram', '1', 'skje', '403', '49.5', '28', '9', '322', '40', '22', '7', NULL, NULL, '17', 'tech_nutrition_mealtech_thumb.jpg', 'tech_nutrition_mealtech_a.jpg', 'tech_nutrition_mealtech_b.jpg', 'tech_nutrition_mealtech_c.jpg', NULL");
        setupInsertToFood("NULL, 'Chocolate peanut', 'Atkins', '60', 'gram', '1', 'stk', '386', '29.4', '24.8', '19', '232', '18', '15', '11', NULL, NULL, '18', 'atkins_chocolate_peanut_thumb.jpg', 'atkins_chocolate_peanut_a.jpg', 'atkins_chocolate_peanut_b.jpg', 'atkins_chocolate_peanut_c.jpg', NULL");
        setupInsertToFood("NULL, 'Protein bar almond and caramel flavour', 'Maxim', '50', 'gram', '1', 'stk', '328', '41', '27', '9.2', '164', '21', '14', '5', NULL, NULL, '18', 'maxim_protein_bar_almond_and_caramel_flavour_thumb.jpg', 'maxim_protein_bar_almond_and_caramel_flavour_a.jpg', 'maxim_protein_bar_almond_and_caramel_flavour_b.jpg', 'maxim_protein_bar_clmond_cnd_caramel_flavour_c.jpg', NULL");
        setupInsertToFood("NULL, 'YT 1 Oppladning før trening - Søt og salt med sjokolade', 'Tine', '50', 'gram', '1', 'stk', '427', '21', '45', '17', '214', '11', '23', '9', NULL, NULL, '18', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_thumb.jpg', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_a.jpg', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_b.jpg', 'tine_yt_1_oppladning_for_trening_sot_og_salt_med_sjokolade_c.jpg', NULL");
        setupInsertToFood("NULL, 'YT Restitusjonsbar', 'Tine', '65', 'gram', '1', 'stk', '377', '29', '48', '8.3', '245', '19', '31', '5', NULL, NULL, '18', 'tine_yt_restitusjonsbar_thumb.jpg', 'tine_yt_restitusjonsbar_a.jpg', 'tine_yt_restitusjonsbar_b.jpg', 'tine_yt_restitusjonsbar_c.jpg', NULL");
        setupInsertToFood("NULL, 'Lean Whey Chocolate Milkshake', 'Gymgrossisten Optimum Nutrition', '30', 'gram', '1', 'skje', '410', '20', '1.4', '1.4', '123', '6', '0', '0', NULL, NULL, '19', 'gymgrossisten_100_lean_whey_chocolate_milkshake_thumb.jpg', 'gymgrossisten_100_lean_whey_chocolate_milkshake_a.jpg', 'gymgrossisten_100_lean_whey_chocolate_milkshake_b.jpg', 'gymgrossisten_100_lean_whey_chocolate_milkshake_c.jpg', NULL");
        setupInsertToFood("NULL, '100% Whey Gold Standard Cookies and Cream', 'Gymgrossisten', '30', 'gram', '1', 'skje', '363', '78.9', '7.8', '2.5', '109', '24', '2', '1', NULL, NULL, '19', 'gymgrossisten_100_whey_gold_standard_cookies_and_cream_thumb.jpg', 'gymgrossisten_100_whey_gold_standard_cookies_and_cream_a.jpg', 'gymgrossisten_100_whey_gold_standard_cookies_and_cream_b.jpg', 'gymgrossisten_100_whey_gold_standard_cookies_cnd_cream_c.jpg', NULL");
        setupInsertToFood("NULL, '100% Whey Gold Standard Delicious Strawberry', 'Gymgrossisten', '30', 'gram', '1', 'skje', '375', '82.5', '4.5', '2.9', '113', '25', '1', '1', NULL, NULL, '19', 'gymgrossisten_100_whey_gold_standard_delicious_strawberry_thumb.jpg', 'gymgrossisten_100_whey_gold_standard_delicious_strawberry_a.jpg', 'gymgrossisten_100_whey_gold_standard_delicious_strawberry_b.jpg', 'gymgrossisten_100_whey_gold_standard_delicious_strawberry_c.jpg', NULL");
        setupInsertToFood("NULL, 'Lean Protein Blueberry Raspberry Ice Cream', 'Gymgrossisten', '30', 'gram', '1', 'skje', '378', '80', '4', '5', '113', '24', '1', '2', NULL, NULL, '19', 'gymgrossisten_lean_protein_thumb.jpg', 'gymgrossisten_lean_protein_a.jpg', 'gymgrossisten_lean_protein_b.jpg', 'gymgrossisten_lean_protein_c.jpg', NULL");
        setupInsertToFood("NULL, 'Whey 80 Ice Coffee', 'Gymgrossisten', '30', 'gram', '1', 'skje', '396', '76', '4', '8', '119', '23', '1', '2', NULL, NULL, '19', 'gymgrossisten_whey_80_ice_coffee_thumb.jpg', 'gymgrossisten_whey_80_ice_coffee_a.jpg', 'gymgrossisten_whey_80_ice_coffee_b.jpg', 'gymgrossisten_whey_80_ice_coffee_c.jpg', NULL");
        setupInsertToFood("NULL, 'Prozyme proteinpulver bringebær', 'Tech Nutrition', '40', 'gram', '1', 'skje', '368', '82.5', '5', '1.3', '147', '33', '2', '1', NULL, NULL, '19', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_thumb.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_a.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_b.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Sprek proteinsmoothie', 'Tech Nutrition', '28', 'gram', '1', 'skje', '262', '71', '23.3', '1', '73', '20', '7', '0', NULL, NULL, '19', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_thumb.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_a.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_b.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Stjernebacon', 'Gilde', '23', 'gram', '1', 'skive', '301', '14', '0.5', '27', '69', '3', '0', '6', NULL, NULL, '21', 'gilde_stjernebacon_thumb.jpg', 'gilde_stjernebacon_a.jpg', 'gilde_stjernebacon_b.jpg', 'gilde_stjernebacon_c.jpg', NULL");
        setupInsertToFood("NULL, 'Kvernet deig av storfe (5%)', 'Nordfjord', '200', 'gram', '0.5', 'pakke', '123', '19', '0', '5', '246', '38', '0', '10', NULL, NULL, '21', 'nordfjord_kvernet_deig_av_storfe_thumb.jpg', 'nordfjord_kvernet_deig_av_storfe_a.jpg', 'nordfjord_kvernet_deig_av_storfe_b.jpg', 'nordfjord_kvernet_deig_cv_storfe_c.jpg', NULL");
        setupInsertToFood("NULL, 'Kvernet deig av storfe', 'Rema 1000', '200', 'gram', '0.5', 'pakke', '123', '19', '0', '1.9', '246', '38', '0', '4', NULL, NULL, '21', 'rema_1000_kvernet_deig_av_storfe_thumb.jpg', 'rema_1000_kvernet_deig_av_storfe_a.jpg', 'rema_1000_kvernet_deig_av_storfe_b.jpg', 'rema_1000_kvernet_deig_cv_storfe_c.jpg', NULL");
        setupInsertToFood("NULL, 'Strimlet svinekjøtt av bog', 'Rema 1000', '200', 'gram', '0.5', 'pakke', '147', '21', '0', '7', '294', '42', '0', '14', NULL, NULL, '21', 'rema_1000_strimlet_svinekjott_av_bog_thumb.jpg', 'rema_1000_strimlet_svinekjott_av_bog_a.jpg', 'rema_1000_strimlet_svinekjott_av_bog_b.jpg', 'rema_1000_strimlet_svinekjott_cv_bog_c.jpg', NULL");
        setupInsertToFood("NULL, 'Strimlet svinekjøtt av bog', 'Rema 1000', '192', 'gram', '0.5', 'pakke', '110', '24', '0', '1.6', '211', '46', '0', '3', NULL, NULL, '21', 'rema_1000_tynnskaaret_svinefilet_thumb.jpg', 'rema_1000_tynnskaaret_svinefilet_a.jpg', 'rema_1000_tynnskaaret_svinefilet_b.jpg', 'rema_1000_tynnskaaret_svinefilet_c.jpg', NULL");
        setupInsertToFood("NULL, 'Frossen god helg kylling bbq', 'Den stolte hane', '412', 'gram', '0.5', 'pakke', '179', '16.8', '0.1', '12.2', '737', '69', '0', '50', NULL, NULL, '22', 'den_stolte_hane_frossen_god_helg_kylling_bbq_thumb.jpg', 'den_stolte_hane_frossen_god_helg_kylling_bbq_a.jpg', 'den_stolte_hane_frossen_god_helg_kylling_bbq_b.jpg', 'den_stolte_hane_frossen_god_helg_kylling_bbq_c.jpg', NULL");
        setupInsertToFood("NULL, 'Frossen kyllingfilet', 'First Price', '200', 'gram', '1', 'stk', '93', '21', '0.2', '0.9', '186', '42', '0', '2', NULL, NULL, '22', 'first_price_frossen_kyllingfilet_thumb.jpg', 'first_price_frossen_kyllingfilet_a.jpg', 'first_price_frossen_kyllingfilet_b.jpg', 'first_price_frossen_kyllingfilet_c.jpg', NULL");
        setupInsertToFood("NULL, 'Kalkunfilet salt og pepper (fersk)', 'Prior', '100', 'gram', '1', 'stk', '113', '25', '0.2', '1.4', '113', '25', '0', '1', NULL, NULL, '22', 'prior_kalkunfilet_salt_og_pepper_thumb.jpg', 'prior_kalkunfilet_salt_og_pepper_a.jpg', 'prior_kalkunfilet_salt_og_pepper_b.jpg', 'prior_kalkunfilet_salt_og_pepper_c.jpg', NULL");
        setupInsertToFood("NULL, 'Reker i lake', 'Fiskemannen', '200', 'gram', '1', 'boks', '79', '16', '1.5', '1', '158', '32', '3', '2', NULL, NULL, '23', 'fiskemannen_reker_i_lake_thumb.jpg', 'fiskemannen_reker_i_lake_a.jpg', 'fiskemannen_reker_i_lake_b.jpg', 'fiskemannen_reker_i_lake_c.jpg', NULL");
        setupInsertToFood("NULL, 'Egg eggehvite', 'First Price', '30', 'gram', '1', 'stk', '42', '10.2', '0.4', '0', '13', '3', '0', '0', NULL, NULL, '25', 'first_price_egg_eggehvite_thumb.jpg', 'first_price_egg_eggehvite_a.jpg', 'first_price_egg_eggehvite_b.jpg', 'first_price_egg_eggehvite_c.jpg', NULL");
        setupInsertToFood("NULL, 'Egg eggeplomme', 'First Price', '15', 'gram', '1', 'stk', '308', '15.8', '0.2', '27.1', '46', '2', '0', '4', NULL, NULL, '25', 'first_price_egg_eggeplomme_thumb.jpg', 'first_price_egg_eggeplomme_a.jpg', 'first_price_egg_eggeplomme_b.jpg', 'first_price_egg_eggeplomme_c.jpg', NULL");
        setupInsertToFood("NULL, 'Egg (kokt)', 'First Price', '63', 'gram', '1', 'stk', '143', '12.4', '0.3', '10.2', '90', '8', '0', '6', NULL, NULL, '25', 'first_price_egg_kokt_thumb.jpg', 'first_price_egg_kokt_a.jpg', 'first_price_egg_kokt_b.jpg', 'first_price_egg_kokt_c.jpg', NULL");
        setupInsertToFood("NULL, 'Egg', 'Flemming', '63', 'gram', '1', 'stk', '142', '12.4', '0.3', '10.1', '89', '8', '0', '6', NULL, NULL, '25', 'flemming_egg_thumb.jpg', 'flemming_egg_a.jpg', 'flemming_egg_b.jpg', 'flemming_egg_c.jpg', NULL");
        setupInsertToFood("NULL, 'Cottage Cheese original', 'Tine', '250', 'gram', '1', 'boks', '96', '13', '1.5', '4.3', '240', '33', '4', '11', NULL, NULL, '26', 'tine_cottage_cheese_thumb.jpg', 'tine_cottage_cheese_a.jpg', 'tine_cottage_cheese_b.jpg', 'tine_cottage_cheese_c.jpg', NULL");
        setupInsertToFood("NULL, 'Mager Cottage Cheese 400g', 'Tine', '200', 'gram', '0.5', 'boks', '79', '13', '2.1', '2', '158', '26', '4', '4', NULL, NULL, '26', 'tine_mager_cottage_cheese_400g_thumb.jpg', 'tine_mager_cottage_cheese_400g_a.jpg', 'tine_mager_cottage_cheese_400g_b.jpg', 'tine_mager_cottage_cheese_400g_c.jpg', NULL");
        setupInsertToFood("NULL, 'Mager cottage cheese med eple, pære og vanilje', 'Tine', '250', 'gram', '1', 'boks', '92', '11', '7.6', '1.7', '230', '28', '19', '4', NULL, NULL, '26', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_thumb.jpg', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_a.jpg', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_b.jpg', 'tine_mager_cottage_cheese_eple_paere_og_vanilje_c.jpg', NULL");
        setupInsertToFood("NULL, 'Liten skyr blåbær', 'Q Meieriene', '160', 'gram', '1', 'stk', '58', '10', '4', '0.2', '93', '16', '6', '0', NULL, NULL, '27', 'q_meieriene_liten_skyr_blabaer_thumb.jpg', 'q_meieriene_liten_skyr_blabaer_a.jpg', 'q_meieriene_liten_skyr_blabaer_b.jpg', 'q_meieriene_liten_skyr_blabaer_c.jpg', NULL");
        setupInsertToFood("NULL, 'Liten skyr jorbær og lime', 'Q Meieriene', '160', 'gram', '1', 'stk', '58', '10', '4', '0.2', '93', '16', '6', '0', NULL, NULL, '27', 'q_meieriene_liten_skyr_jordbaer_og_lime_thumb.jpg', 'q_meieriene_liten_skyr_jordbaer_og_lime_a.jpg', 'q_meieriene_liten_skyr_jordbaer_og_lime_b.jpg', 'q_meieriene_liten_skyr_jordbaer_og_lime_c.jpg', NULL");
        setupInsertToFood("NULL, 'Skyr skogsbærkick', 'Q Meieriene', '170', 'gram', '1', 'stk', '108', '10', '15', '0.4', '184', '17', '26', '1', NULL, NULL, '27', 'q_meieriene_skyr_skogsbaerkick_thumb.jpg', 'q_meieriene_skyr_skogsbaerkick_a.jpg', 'q_meieriene_skyr_skogsbaerkick_b.jpg', 'q_meieriene_skyr_skogsbaerkick_c.jpg', NULL");
        setupInsertToFood("NULL, 'Go morgen melon- og pasjonsfruktyoghurt med 4-kornmusli', 'Tine', '195', 'gram', '1', 'stk', '125', '4.4', '20', '2.9', '244', '9', '39', '6', NULL, NULL, '27', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_thumb.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_a.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_b.jpg', 'tine_go_morgen_melon-_og_pasjonsfruktyoghurt_med_4-kornmusli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Go morgen skogsbæryoghurt med 4-kormusli', 'Tine', '195', 'gram', '1', 'stk', '126', '4.5', '20', '3', '246', '9', '39', '6', NULL, NULL, '27', 'tine_go_morgen_skogsbaeryoghurt_med_4-kornmusli_thumb.jpg', 'tine_go_morgen_skogsbaeryoghurt_med_4-kornmusli_a.jpg', 'tine_go_morgen_skogsbaeryoghurt_med_4-kornmusli_b.jpg', 'tine_go_morgen_skogsbaeryoghurt_med_4-kornmusli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Double 0% Protein - Plus', 'Yoplait', '125', 'gram', '1', 'stk', '63', '8', '6.8', '0.4', '79', '10', '9', '1', NULL, NULL, '27', 'yoplait_double_protein_thumb.jpg', 'yoplait_double_protein_a.jpg', 'yoplait_double_protein_b.jpg', 'yoplait_double_protein_c.jpg', NULL");
        setupInsertToFood("NULL, 'Finnbiff med poteter og tyttebærsyltetøy', 'Fjordland', '485', 'gram', '1', 'pakke', '81', '4.1', '14', '3', '393', '20', '68', '15', NULL, NULL, '29', 'fjordland_finnbiff_med_poteter_og_tyttebaersyltetooy_thumb.jpg', 'fjordland_finnbiff_med_poteter_og_tyttebaersyltetooy_a.jpg', 'fjordland_finnbiff_med_poteter_og_tyttebaersyltetooy_b.jpg', 'fjordland_finnbiff_med_poteter_og_tyttebaersyltetooy_c.jpg', NULL");
        setupInsertToFood("NULL, 'Ristorante Pizza Spesiale', 'Dr. Oetker', '330', 'gram', '1', 'pizza', '254', '10', '23', '13', '838', '33', '76', '43', NULL, NULL, '30', 'dr_oetker_ristorante_pizza_speciale_thumb.jpg', 'dr_oetker_ristorante_pizza_speciale_a.jpg', 'dr_oetker_ristorante_pizza_speciale_b.jpg', 'dr_oetker_ristorante_pizza_speciale_c.jpg', NULL");
        setupInsertToFood("NULL, 'Nudler med kjøttsmak', 'First Price', '85', 'gram', '1', 'pakke', '349', '11.2', '66.6', '3.5', '297', '10', '57', '3', NULL, NULL, '31', 'first_price_nudler_med_kjootsmak_thumb.jpg', 'first_price_nudler_med_kjootsmak_a.jpg', 'first_price_nudler_med_kjootsmak_b.jpg', 'first_price_nudler_med_kjootsmak_c.jpg', NULL");
        setupInsertToFood("NULL, 'Fullkornspasta fusilli', 'Sopps', '60', 'gram', '60', 'g', '350', '14', '65', '2', '210', '8', '39', '1', NULL, NULL, '32', 'sopps_fullkornspasta_fusilli_thumb.jpg', 'sopps_fullkornspasta_fusilli_a.jpg', 'sopps_fullkornspasta_fusilli_b.jpg', 'sopps_fullkornspasta_fusilli_c.jpg', NULL");
        setupInsertToFood("NULL, 'Fullkornspasta penne', 'Sopps', '60', 'gram', '60', 'g', '350', '14', '65', '2', '210', '8', '39', '1', NULL, NULL, '32', 'sopps_fullkornspasta_penne_thumb.jpg', 'sopps_fullkornspasta_penne_a.jpg', 'sopps_fullkornspasta_penne_b.jpg', 'sopps_fullkornspasta_penne_c.jpg', NULL");
        setupInsertToFood("NULL, 'Jasminris', 'Eldorado', '60', 'gram', '60', 'g', '360', '7', '80', '0.9', '216', '4', '48', '1', NULL, NULL, '33', 'eldorado_jasminris_thumb.jpg', 'eldorado_jasminris_a.jpg', 'eldorado_jasminris_b.jpg', 'eldorado_jasminris_c.jpg', NULL");
        setupInsertToFood("NULL, 'Fullkorns couscous', 'Go Green', '70', 'gram', '70', 'g', '350', '12', '34', '2', '245', '8', '24', '1', NULL, NULL, '33', 'gogreen_fullkorns_couscous_thumb.jpg', 'gogreen_fullkorns_couscous_a.jpg', 'gogreen_fullkorns_couscous_b.jpg', 'gogreen_fullkorns_couscous_c.jpg', NULL");
        setupInsertToFood("NULL, 'Thai hom mali rice jasminris', 'Rema 1000', '60', 'gram', '60', 'g', '316', '7.1', '78', '0.6', '190', '4', '47', '0', NULL, NULL, '33', 'rema_1000_thai_hom_mali_rice_jasminris_thumb.jpg', 'rema_1000_thai_hom_mali_rice_jasminris_a.jpg', 'rema_1000_thai_hom_mali_rice_jasminris_b.jpg', 'rema_1000_thai_hom_mali_rice_jasminris_c.jpg', NULL");
        setupInsertToFood("NULL, 'Fullkornsris', 'Uncle Bens', '60', 'gram', '60', 'g', '344', '8', '73', '2.2', '206', '5', '44', '1', NULL, NULL, '33', 'uncle_bens_fullkornsris_thumb.jpg', 'uncle_bens_fullkornsris_a.jpg', 'uncle_bens_fullkornsris_b.jpg', 'uncle_bens_fullkornsris_c.jpg', NULL");
        setupInsertToFood("NULL, 'Text Mex Taco Sauce ', 'Coop', '15', 'gram', '1', 'ts', '34', '1.2', '6.5', '0.2', '5', '0', '1', '0', NULL, NULL, '34', 'co-op_tex_mex_taco_sauce_thumb.jpg', 'co-op_tex_mex_taco_sauce_a.jpg', 'co-op_tex_mex_taco_sauce_b.jpg', 'co-op_tex_mex_taco_sauce_c.jpg', NULL");
        setupInsertToFood("NULL, 'Myke tortilla-lefser', 'First Price', '40', 'gram', '1', 'stk', '332', '9.3', '54.2', '8', '133', '4', '22', '3', NULL, NULL, '34', 'first_prince_myke_tortilla-lefser_thumb.jpg', 'first_prince_myke_tortilla-lefser_a.jpg', 'first_prince_myke_tortilla-lefser_b.jpg', 'first_prince_myke_tortilla-lefser_c.jpg', NULL");
        setupInsertToFood("NULL, '8 Tortillas Whole Weat', 'Old El Paso', '41', 'gram', '1', 'stk', '286', '8.3', '46.6', '6', '117', '3', '19', '2', NULL, NULL, '34', 'old_el_paso_whole_wheete_thumb.jpg', 'old_el_paso_whole_wheete_a.jpg', 'old_el_paso_whole_wheete_b.jpg', 'old_el_paso_whole_wheete_c.jpg', NULL");
        setupInsertToFood("NULL, 'Fullkorn tortillas 6 stk', 'Rema 1000', '62', 'gram', '1', 'stk', '234', '9.8', '47', '5.8', '145', '6', '29', '4', NULL, NULL, '34', 'rema_1000_fullkorn_tortillas_6_stk_thumb.jpg', 'rema_1000_fullkorn_tortillas_6_stk_a.jpg', 'rema_1000_fullkorn_tortillas_6_stk_b.jpg', 'rema_1000_fullkorn_tortillas_6_stk_c.jpg', NULL");
        setupInsertToFood("NULL, 'Orginal Wrap Tortilla Big Size', 'Santa Maria', '62', 'gram', '1', 'stk', '300', '8.5', '53', '5', '186', '5', '33', '3', NULL, NULL, '34', 'santa_maria_orginal_wrap_tortilla_big_size_thumb.jpg', 'santa_maria_orginal_wrap_tortilla_big_size_a.jpg', 'santa_maria_orginal_wrap_tortilla_big_size_b.jpg', 'santa_maria_orginal_wrap_tortilla_big_size_c.jpg', NULL");
        setupInsertToFood("NULL, '8 Tortillas With Whole Weat', 'Santa Maria', '40', 'gram', '1', 'stk', '280', '8', '47', '5.5', '112', '3', '19', '2', NULL, NULL, '34', 'santa_maria_tortilla_with_whole_weat_thumb.jpg', 'santa_maria_tortilla_with_whole_weat_a.jpg', 'santa_maria_tortilla_with_whole_weat_b.jpg', 'santa_maria_tortilla_with_whole_weat_c.jpg', NULL");
        setupInsertToFood("NULL, 'Philadelphia naturell light', 'Kraft', '21', 'gram', '1', 'teskje', '152', '7.4', '5.1', '11', '32', '2', '1', '2', NULL, NULL, '36', 'kraft_philadelphia_naturell_light_thumb.jpg', 'kraft_philadelphia_naturell_light_a.jpg', 'kraft_philadelphia_naturell_light_b.jpg', 'kraft_philadelphia_naturell_light_c.jpg', NULL");
        setupInsertToFood("NULL, 'Lammerull pålegg', 'Gilde', '13', 'gram', '1', 'skive', '173', '17', '1.4', '11', '22', '2', '0', '1', NULL, NULL, '38', 'gilde_lammerull_palegg_thumb.jpg', 'gilde_lammerull_palegg_a.jpg', 'gilde_lammerull_palegg_b.jpg', 'gilde_lammerull_palegg_c.jpg', NULL");
        setupInsertToFood("NULL, 'Roastbiff pålegg', 'Gilde', '50', 'gram', '1', 'skive', '117', '24', '1.3', '1.8', '59', '12', '1', '1', NULL, NULL, '38', 'gilde_roastbiff_palegg_thumb.jpg', 'gilde_roastbiff_palegg_a.jpg', 'gilde_roastbiff_palegg_b.jpg', 'gilde_roastbiff_palegg_c.jpg', NULL");
        setupInsertToFood("NULL, 'Kalkunfilet med peppermix', 'Kylling Norge', '50', 'gram', '1', 'skive', '103', '21.8', '1', '1.2', '52', '11', '1', '1', NULL, NULL, '38', 'kylling_norge_kalkunfilet_med_peppermix_thumb.jpg', 'kylling_norge_kalkunfilet_med_peppermix_a.jpg', 'kylling_norge_kalkunfilet_med_peppermix_b.jpg', 'kylling_norge_kalkunfilet_med_peppermix_c.jpg', NULL");
        setupInsertToFood("NULL, 'Jalapeñoskinke', 'Nordfjord', '9', 'gram', '1', 'skive', '104', '18', '1.2', '3', '9', '2', '0', '0', NULL, NULL, '38', 'nordfjord_jalapenoskinke_thumb.jpg', 'nordfjord_jalapenoskinke_a.jpg', 'nordfjord_jalapenoskinke_b.jpg', 'nordfjord_jalapenoskinke_c.jpg', NULL");
        setupInsertToFood("NULL, 'Kalkunfilet pålegg', 'Prior', '50', 'gram', '1', 'skive', '104', '21', '2', '1.2', '52', '11', '1', '1', NULL, NULL, '38', 'prior_kalkunfilet_palegg_thumb.jpg', 'prior_kalkunfilet_palegg_a.jpg', 'prior_kalkunfilet_palegg_b.jpg', 'prior_kalkunfilet_palegg_c.jpg', NULL");
        setupInsertToFood("NULL, 'Kyllingfilet pålegg', 'Solvinge', '50', 'gram', '1', 'skive', '197', '20.3', '2', '0.3', '99', '10', '1', '0', NULL, NULL, '38', 'solvinge_kyllingfilet_palegg_thumb.jpg', 'solvinge_kyllingfilet_palegg_a.jpg', 'solvinge_kyllingfilet_palegg_b.jpg', 'solvinge_kyllingfilet_palegg_c.jpg', NULL");
        setupInsertToFood("NULL, 'Hapå', 'Kavli', '30', 'gram', '1', 'spiseskje', '314', '7', '58', '6', '94', '2', '17', '2', NULL, NULL, '39', 'kavli_hapaa_thumb.jpg', 'kavli_hapaa_a.jpg', 'kavli_hapaa_b.jpg', 'kavli_hapaa_c.jpg', NULL");
        setupInsertToFood("NULL, 'Bringebærsyltetøy', 'First Price', '30', 'gram', '1', 'spiseskje', '193', '0.4', '46', '0.2', '58', '0', '14', '0', NULL, NULL, '40', 'first_price_bringaebaersyltetooy_thumb.jpg', 'first_price_bringaebaersyltetooy_a.jpg', 'first_price_bringaebaersyltetooy_b.jpg', 'first_price_bringaebaersyltetooy_c.jpg', NULL");
        setupInsertToFood("NULL, 'Utvalde kirsebær', 'Lerums', '30', 'gram', '1', 'spiseskje', '159', '0.3', '39', '0', '48', '0', '12', '0', NULL, NULL, '40', 'lerums_utvalde_kirsebear_thumb.jpg', 'lerums_utvalde_kirsebear_a.jpg', 'lerums_utvalde_kirsebear_b.jpg', 'lerums_utvalde_kirsebear_c.jpg', NULL");
        setupInsertToFood("NULL, 'Nøttemiks', 'First Price', '600', 'gram', '1', 'pakke', '512', '16.1', '37.1', '32.3', '3 072', '97', '223', '194', NULL, NULL, '42', 'first_price_noottemiks_thumb.jpg', 'first_price_noottemiks_a.jpg', 'first_price_noottemiks_b.jpg', 'first_price_noottemiks_c.jpg', NULL");
        setupInsertToFood("NULL, 'Micropop', 'Eldorado', '100', 'gram', '1', 'stk', '389', '7', '35', '19.1', '389', '7', '35', '19', NULL, NULL, '43', 'eldorado_micropop_thumb.jpg', 'eldorado_micropop_a.jpg', 'eldorado_micropop_b.jpg', 'eldorado_micropop_c.jpg', NULL");
        setupInsertToFood("NULL, 'Sørlandschips Spansk paprika med persille', 'Snacks', '200', 'gram', '1', 'pakke', '504', '7', '58', '26', '1 008', '14', '116', '52', NULL, NULL, '43', 'soorlandschips_spansk_paprika_med_persille_thumb.jpg', 'soorlandschips_spansk_paprika_med_persille_a.jpg', 'soorlandschips_spansk_paprika_med_persille_b.jpg', 'soorlandschips_spansk_paprika_med_persille_c.jpg', NULL");
    }


}
