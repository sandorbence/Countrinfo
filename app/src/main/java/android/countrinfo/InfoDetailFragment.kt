package android.countrinfo

import android.countrinfo.events.ErrorEvent
import android.countrinfo.events.GetCountrybyNameResponseEvent
import android.countrinfo.model.Base
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.countrinfo.model.Info
import android.countrinfo.network.CountriesInteractor
import android.countrinfo.viewmodel.InfoViewModel
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.info_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

class InfoDetailFragment : Fragment() {

    private var selectedInfo: Info? = null
    private lateinit var country: Base
    private lateinit var viewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        arguments?.let { args ->
            selectedInfo = Info(
                title = args.getString(NAME) ?: "",
                description = args.getString(KEY_INFO_DESCRIPTION) ?: ""
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        val interactor = CountriesInteractor()
        interactor.getCountryByName(arguments?.getString(NAME) ?: selectedInfo!!.title)
        if (selectedInfo?.description == "") etComment.setHint(R.string.commentDefault)
        etComment.setText(selectedInfo?.description)
        btnComment.text = resources.getString(R.string.btnComment)
        btnComment.idleText = resources.getString(R.string.btnComment)
        btnComment.completeText = resources.getString(R.string.btnComplete)
        btnComment.setOnClickListener {
            selectedInfo?.description = etComment.text.toString()
            viewModel.update(selectedInfo!!)
            btnComment.progress = 100
        }
        etComment.addTextChangedListener {
            btnComment.progress = 0
        }
    }

    companion object {

        const val KEY_INFO_DESCRIPTION = "KEY_INFO_DESCRIPTION"
        const val NAME = "name"
        fun newInstance(name: String?, infoDesc: String?): InfoDetailFragment {
            val args = Bundle()
            args.putString(KEY_INFO_DESCRIPTION, infoDesc)
            args.putString(NAME, name)
            val result = InfoDetailFragment()
            result.arguments = args
            return result
        }
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetCountryByNameResponse(event: GetCountrybyNameResponseEvent) {
        country = event.response.get(0)
        setupFragment(country)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onErrorResponse(event: ErrorEvent) {
        Toast.makeText(
            requireContext(),
            "Could not load data. Check your internet connection.",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupFragment(country: Base) {
        tvCapital.text = resources.getString(R.string.capital, country.capital)
        tvPopulation.text = resources.getString(R.string.population, country.population.toString())
        val langs = mutableListOf<String>()
        for (lang in country.languages) {
            langs.add(lang.name)
        }
        val currNames = mutableListOf<String>()
        val currCodes = mutableListOf<String>()
        for (curr in country.currencies) {
            currNames.add(curr.name)
            currCodes.add(curr.code)
        }
        tvLanguage.text = resources.getString(R.string.language, langs.joinToString())
        tvCurrency.text = resources.getString(
            R.string.currency,
            currNames.joinToString(),
            currCodes.joinToString()
        )
        tvArea.text = resources.getString(R.string.area, country.area.toString())
        tvNative.text = resources.getString(R.string.nativeName, country.nativeName)

    }

}
