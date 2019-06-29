package assessment.com.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assessment.com.myapplication.map.MapFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MapFragment())
            .addToBackStack(null)
            .commit()
    }
}
