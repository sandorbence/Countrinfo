package android.countrinfo

import android.content.Intent
import android.countrinfo.adapter.SimpleItemRecyclerViewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.countrinfo.model.Info
import android.countrinfo.viewmodel.InfoViewModel
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_info_list.*
import kotlinx.android.synthetic.main.info_list.*

class InfoListActivity : AppCompatActivity(), SimpleItemRecyclerViewAdapter.InfoItemClickListener,
    InfoCreateFragment.InfoCreatedListener {

    private lateinit var infoViewModel: InfoViewModel

    override fun onItemClick(info: Info) {

        if (twoPane) {
            val fragment = InfoDetailFragment.newInstance(info.title, info.description)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.info_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(this, InfoDetailActivity::class.java)
            intent.putExtra(InfoDetailActivity.KEY_DESC, info.description)
            intent.putExtra("countryName", info.title)
            startActivity(intent)
        }
    }

    override fun onItemLongClick(position: Int, view: View, info: Info): Boolean {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.info_menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    infoViewModel.delete(info)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemCreateInfo) {
            val infoCreateFragment = InfoCreateFragment()
            infoCreateFragment.show(supportFragmentManager, "TAG")
        }
        if (item.itemId == R.id.itemDeleteAll) {
            infoViewModel.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private var twoPane: Boolean = false
    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_list)
        setSupportActionBar(toolbar)
        toolbar.title = title

        setScreen()

        fab.setOnClickListener {
            val infoCreateFragment = InfoCreateFragment()
            infoCreateFragment.show(supportFragmentManager, "TAG")
        }

        if (info_detail_container != null) {
            twoPane = true
        }

        setupRecyclerView()

        infoViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        infoViewModel.allInfos.observe(this) { infos ->
            simpleItemRecyclerViewAdapter.addAll(infos)
        }
    }

    private fun setupRecyclerView() {
        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        info_list.adapter = simpleItemRecyclerViewAdapter
    }

    override fun onInfoCreated(info: Info) {
        infoViewModel.insert(info)
    }


    private fun setScreen() {
        if (intent.getBooleanExtra("fromMarker", false)) {
            val infoCreateFragment = InfoCreateFragment()
            val bundle = Bundle()
            bundle.putString("countryName", intent.getStringExtra("countryName"))
            infoCreateFragment.arguments = bundle
            infoCreateFragment.show(supportFragmentManager, "TAG")
        }
        return
    }

}
