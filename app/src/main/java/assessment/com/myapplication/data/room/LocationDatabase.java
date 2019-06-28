package assessment.com.myapplication.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RoomLocation.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
