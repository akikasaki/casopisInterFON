package com.android.casopisinterfon.interfon.model;


/**
 * Contains types of category
 */
public enum Category {
    ALL(0),
    NEWS(45),
    INTERESTING(38),
    SCIENCE(960),
    CULTURE(1371),
    INTERVIEWS(1370),
    COLUMNS(758),
    PRACTICE(3759),
    SPORT(48),
    INTRAFON(37),
    FONNEWS(2085),
    POETS(4422);


    private int catId;

    Category() {
    }

    Category(int id) {
        this.catId = id;
    }

    public int getCatId() {
        return this.catId;
    }

    public String getName() {
        switch (this.catId) {
            default: // Ostalo je par kategorija - http://casopisinterfon.org/api/get_category_index/
            case 45:
                return "Vesti";
            case 2085:
                return "Vesti sa FONa";
            case 37:
                return "IntraFON";
            case 38:
                return "Interesantno";
            case 960:
                return "Nauka";
            case 1371:
                return "Kultura";
            case 1370:
                return "Intervjui";
            case 758:
                return "Kolumne";
            case 3759:
                return "Prakse";
            case 48:
                return "Sport";
            case 4422:
                return "Poetski kutak";
        }
    }

//    /**
//     * Method for returning position of category
//     * @return can be 0 to {@link com.android.casopisinterfon.interfon.activity.MainActivity#CATEGORY_COUNT} - 1;
//     */
//    public int getPosition(){
//
//    }


    public static Category getCategory(int pagePosition) {

        switch (pagePosition) {
            default:
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

        }
    }

    /**
     * Maps category to the server category based on server cat ID;
     * @param id category id retrieved from the server;
     */
    public static Category getCategoryById(int id) {
        switch (id) {
            default: // Ostalo je par kategorija - http://casopisinterfon.org/api/get_category_index/
            case 45:
                return NEWS;
            case 0:
                return ALL;
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
