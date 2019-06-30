package assessment.com.myapplication.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLocations(List<RoomLocation> locations);

    @Query("SELECT * FROM RoomLocation")
    List<RoomLocation> getAll();

    @Query("SELECT * FROM RoomLocation WHERE id = :id")
    RoomLocation getSingle(int id);
}
