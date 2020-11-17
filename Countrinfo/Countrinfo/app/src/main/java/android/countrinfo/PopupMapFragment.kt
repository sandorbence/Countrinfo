package android.countrinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.fragment_popupmap.*

class PopupMapFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popupmap, fragment_container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddFromMarker.setOnClickListener {
            val countryName = arguments?.getString("countryName")
            val intent = Intent(activity, InfoListActivity::class.java)
            intent.putExtra("countryName", countryName)
            intent.putExtra("fromMarker", true)
            startActivity(intent)
            dismiss()
        }
        btnBack.setOnClickListener {
            dismiss()
        }
    }

}