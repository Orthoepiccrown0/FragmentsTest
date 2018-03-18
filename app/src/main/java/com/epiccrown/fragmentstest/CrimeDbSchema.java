package com.epiccrown.fragmentstest;

/**
 * Created by Epiccrown on 17.03.2018.
 */

public class CrimeDbSchema {
    public static class CrimeTable{
        public static String NAME = "Crimes";

        public static class Cols{
            public static String UUID = "uuid";
            public static String Title = "Title";
            public static String Description = "Description";
            public static String Date = "Date";
            public static String Solved = "Solved";
        }
    }
}
