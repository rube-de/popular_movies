package de.ruf2.popularmovies.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Bernhard Ruf on 31.10.2015.
 */
public interface MovieColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String ID = "id";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String TITLE = "title";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String DESCRIPTION = "description";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String RELEASE_DATE = "release_date";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String RATING = "rating";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String LANGUAGE = "language";
    @DataType(DataType.Type.TEXT)@NotNull
    public static final String PATH = "path";
}
