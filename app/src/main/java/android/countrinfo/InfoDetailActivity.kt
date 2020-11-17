package android.countrinfo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_info_detail.*


class InfoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val name = intent.getStringExtra("countryName")
            val comment = intent.getStringExtra(KEY_DESC)
            val fragment = InfoDetailFragment.newInstance(name, comment)
            supportActionBar?.title = name
            supportFragmentManager.beginTransaction()
                .add(R.id.info_detail_container, fragment)
                .commit()
        }
    }

    companion object {
        const val KEY_DESC = "KEY_DESC"
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, InfoListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}
