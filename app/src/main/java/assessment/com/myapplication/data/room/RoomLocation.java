package assessment.com.myapplication.data.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomLocation {
    @PrimaryKey
    public int id;

    public String name;

    public Double latitude;

    public Double longitude;

    public String description;
}
