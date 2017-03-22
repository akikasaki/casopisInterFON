package com.android.casopisinterfon.interfon.model;


/**
 * Contains types of category
 */
public enum Category {
    ALL,
    NEWS,
    INTERESTING,
    SCIENCE,
    CULTURE,
    INTERVIEWS,
    COLUMNS,
    PRACTICE,
    SPORT,
    INTRAFON,
    FONNEWS,
    POETS;

    public static Category getCategory(int position) {

        switch (position){
            case 0:
                return ALL;
            case 1:
                return NEWS;
            case 2:
             return INTERESTING;
            case 3:
                return SCIENCE;
            case 4:
               return CULTURE;
            case 5:
                return INTERVIEWS;
            case 6:
                return COLUMNS;
            case 7:
                return PRACTICE;
            case 8:
                return SPORT;
            default: return null;
        }
    }

    public static Category getCategoryById(int id) {
        switch (id){
            default: // Ostalo je par kategorija - http://casopisinterfon.org/api/get_category_index/
            case 45:
                return NEWS;
            case 2085:
                return FONNEWS;
            case 37:
                return INTRAFON;
            case 38:
                return INTERESTING;
            case 960:
                return SCIENCE;
            case 1371:
                return CULTURE;
            case 1370:
                return INTERVIEWS;
            case 758:
                return COLUMNS;
            case 3759:
                return PRACTICE;
            case 48:
                return SPORT;
            case 4422:
                return POETS;
        }
    }


}
