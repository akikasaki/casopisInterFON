package com.interfon.android.model;


/**
 * Contains types of category
 */
public enum Category {
    ALL(0),
    NEWS(45),
    INTERVIEWS(1370),
    PRACTICE(3759),
    BI(8171),
    INTERESTING(38),
    CULTURE(1371),
    SPORT(48),
    SCIENCE(960),
    COLUMNS(758),
    FONNEWS(2085),
    POETS(4422),
    IT(8170),
    MARKETING(8172);

    /**
     * Total number of categories on interFON casopis
     */
    public static final int CATEGORY_COUNT = 8;
    public static final String tabTitles[] = {"Sve", "Vesti", "Intervjui",
            "Prakse", "Biznis Insajder", "Interesantno", "Kultura", "Sport"};


    private int catId;

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
            case 8171:
                return "Biznis Insajder";
            case 8170:
                return "IT";
            case 8172:
                return "Marketing";
        }
    }

//    /**
//     * Method for returning position of category
//     * @return can be 0 to {@link MainActivity#CATEGORY_COUNT} - 1;
//     */
//    public int getPosition(){
//
//    }


    public static Category getCategoryByPagePos(int pagePosition) {

        switch (pagePosition) {
            default:
            case 0:
                return ALL;
            case 1:
                return NEWS;
            case 2:
                return INTERVIEWS;
            case 3:
                return PRACTICE;
            case 4:
                return BI;
            case 5:
                return INTERESTING;
            case 6:
                return CULTURE;
            case 7:
                return SPORT;
        }
    }

    /**
     * Maps category to the server category based on server cat ID;
     * @param id category id retrieved from the server;
     */
    public static Category getCategoryById(int id) {
        switch (id) {
            default: // kategorije - http://casopisinterfon.org/api/get_category_index/
            case 45:
                return NEWS;
            case 0:
                return ALL;
            case 2085:
                return FONNEWS;
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
            case 8170:
                return IT;
            case 8171:
                return BI;
            case 8172:
                return MARKETING;
        }
    }




}
