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
    SPORT;


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


}
