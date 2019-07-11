package assessment.com.myapplication.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    // Inserts/updates the locations stored locally
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocations(List<RoomLocation> locations);

    // Inserts a single new location to the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocation(RoomLocation location);

    // Fetches all stored locations
    @Query("SELECT * FROM RoomLocation")
    List<RoomLocation> getAll();

    // Fetches one stored location by unique id
    @Query("SELECT * FROM RoomLocation WHERE id = :id")
    RoomLocation getSingle(int id);
}
