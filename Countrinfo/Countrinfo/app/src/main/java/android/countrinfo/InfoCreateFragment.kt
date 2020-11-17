package android.countrinfo

import android.content.Context
import android.countrinfo.events.GetCountriesResponseEvent
import android.countrinfo.model.Info
import android.countrinfo.network.CountriesInteractor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_create.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class InfoCreateFragment : DialogFragment() {

    private lateinit var listener: InfoCreatedListener
    var allCountries = mutableListOf<String>()
    lateinit var adapter: ArrayAdapter<String>
    lateinit var interactor: CountriesInteractor

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
        interactor = CountriesInteractor()
        interactor.getCountries()
        try {
            listener = if (targetFragment != null) {
                targetFragment as InfoCreatedListener
            } else {
                activity as InfoCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        dialog?.setTitle(R.string.itemCreateInfo)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countryName = arguments?.getString("countryName") ?: ""
        tvCountryName.setText(countryName)
        adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            allCountries
        )
        tvCountryName.setAdapter(adapter)
        adapter.notifyDataSetChanged()
        btnCreateInfo.setOnClickListener {
            if (allCountries.contains(tvCountryName.text.toString())) {
                listener.onInfoCreated(
                    Info(
                        title = tvCountryName.text.toString(),
                        description = etInfoDescription.text.toString()
                    )
                )
                dismiss()
            } else Toast.makeText(
                requireContext(),
                "This country does not seem to exist",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnCancelCreateInfo.setOnClickListener {
            dismiss()
        }

    }

    interface InfoCreatedListener {
        fun onInfoCreated(info: Info)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetCountriesResponse(event: GetCountriesResponseEvent) {
        allCountries.clear()
        for (country in event.response) {
            allCountries.add(country.name)
        }
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

}