package de.ruf2.popularmovies.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Bernhard Ruf on 31.10.2015.
 */
@Database(version =  MovieDatabase.VERSION)
public class MovieDatabase {
    public static final int VERSION = 1;

    @Table(MovieColumns.class)
    public  static final String MOVIES = "movies";

}
