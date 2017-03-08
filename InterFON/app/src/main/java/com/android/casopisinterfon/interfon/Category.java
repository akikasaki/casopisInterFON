package com.android.casopisinterfon.interfon;


/**
 * Contains types of category
 */
public enum Category {
    ALL,
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
                return INTERESTING;
            case 2:
             return SCIENCE;
            case 3:
                return CULTURE;
            case 4:
               return INTERVIEWS;
            case 5:
                return COLUMNS;
            case 6:
                return PRACTICE;
            case 7:
                return SPORT;
            default: return null;
        }


    }


    // TODO - finish adding categories - TRAMPA


}
