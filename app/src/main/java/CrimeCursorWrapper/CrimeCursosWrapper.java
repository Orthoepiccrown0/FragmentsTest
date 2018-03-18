package CrimeCursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.epiccrown.fragmentstest.Crime;
import com.epiccrown.fragmentstest.CrimeDbSchema;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Epiccrown on 17.03.2018.
 */

public class CrimeCursosWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursosWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.Title));
        String desc = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.Description));
        long date_long = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.Date));
        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.Solved));

        UUID uuid = UUID.fromString(uuidString);
        Crime crime = new Crime(uuid,desc,title,isSolved!=0,new Date(date_long));
        return crime;
    }

}
