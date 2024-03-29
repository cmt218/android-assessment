package assessment.com.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assessment.com.myapplication.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display listings by default if there is no saved instance
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ListFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
