package sample.huy.gitpullrequestview
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import sample.huy.gitpullrequestview.UI.Fragment.PrListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
