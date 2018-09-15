package sample.huy.gitpullrequestview
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import sample.huy.gitpullrequestview.UI.Fragment.PrListFragment


class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = "GPT-MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreateView")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_layout, PrListFragment.newInstance(), "Pull Request List")
                    .commit()
        }
    }
}
