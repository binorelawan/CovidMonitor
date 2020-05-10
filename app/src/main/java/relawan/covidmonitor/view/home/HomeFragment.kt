package relawan.covidmonitor.view.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import relawan.covidmonitor.R
import relawan.covidmonitor.databinding.FragmentHomeBinding
import relawan.covidmonitor.repository.Repository
import relawan.covidmonitor.utils.DateTime


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater)

        // lifeCycleOwner
        binding.lifecycleOwner = this

        setActionBarTitle(getString(R.string.app_name))

        val viewModelFactory = HomeModelFactory(context, repository)

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        homeViewModel.globalData.observe(viewLifecycleOwner, Observer {global ->

            if (global != null) {
                Log.d(TAG, global.affectedCountries.toString())

                binding.activeText.text = global.active.toString()
                binding.deathText.text = global.deaths.toString()
                binding.recoverText.text = global.recovered.toString()
                val date = global.updated?.let {
                    DateTime.getDateFormatted(it)
                }
                binding.dateText.text = date

                binding.cardViewGlobal.setOnClickListener {view ->
                    val extras = FragmentNavigatorExtras(view to "global_container")
                    val action = HomeFragmentDirections.actionHomeFragmentToCountryFragment()
                    findNavController().navigate(action, extras)
                }



                val listPie = ArrayList<PieEntry>()
                val listColors = ArrayList<Int>()
                listPie.add(PieEntry(global.active!!.toFloat(), global.active.toString()))
                listColors.add(resources.getColor(R.color.active))
                listPie.add(PieEntry(global.deaths!!.toFloat(), global.deaths.toString()))
                listColors.add(resources.getColor(R.color.death))
                listPie.add(PieEntry(global.recovered!!.toFloat(), global.recovered.toString()))
                listColors.add(resources.getColor(R.color.recovered))

                val pieDataSet = PieDataSet(listPie, "")
                pieDataSet.sliceSpace = 2f
                pieDataSet.colors = listColors

                val pieData = PieData(pieDataSet)
                pieData.setDrawValues(false)

                val text = global.cases.toString()

                binding.worldwide.setUsePercentValues(false)
                binding.worldwide.description.isEnabled = false
                binding.worldwide.setDrawEntryLabels(false)
                binding.worldwide.legend.isEnabled = false

                // hole
                binding.worldwide.isDrawHoleEnabled = true
                binding.worldwide.holeRadius = 60f
                binding.worldwide.setHoleColor(Color.TRANSPARENT)

                // text
                binding.worldwide.setDrawCenterText(true)
                binding.worldwide.isDrawCenterTextEnabled
                binding.worldwide.setCenterTextSize(16f)
                binding.worldwide.setCenterTextColor(resources.getColor(R.color.cases))
                binding.worldwide.centerText = "Total Case\n${global.cases}"

                binding.worldwide.data = pieData


                binding.progressGlobal.visibility = View.GONE
                binding.showGlobal.visibility = View.VISIBLE
            } else {
                binding.progressGlobal.visibility = View.GONE
                binding.errorGlobal.visibility = View.VISIBLE
            }

        })

        homeViewModel.indonesiaData.observe(viewLifecycleOwner, Observer { indonesia ->
            if (indonesia != null) {
                Log.d(TAG, indonesia.country)
                binding.cardViewIndo.setOnClickListener {view ->
                    val extras = FragmentNavigatorExtras(view to "indonesia_container")
                    val action = HomeFragmentDirections.actionHomeFragmentToIndonesiaFragment(indonesia)
                    findNavController().navigate(action, extras)
                }
                binding.indonesiaText.text = indonesia.country
                binding.confirmTextIndo.text = indonesia.cases.toString()
                binding.deathTextIndo.text = indonesia.deaths.toString()
                binding.recoverTextIndo.text = indonesia.recovered.toString()
                binding.flagImage.let { view ->
                    Glide
                        .with(view.context)
                        .load(indonesia.countryInfo.flag)
                        .apply(RequestOptions()).override(90, 30)
                        .into(binding.flagImage)
                }

                binding.progressIndonesia.visibility = View.GONE
                binding.showIndonesia.visibility = View.VISIBLE
            } else {
                binding.progressIndonesia.visibility = View.GONE
                binding.errorIndonesia.visibility = View.VISIBLE
            }

        })

        homeViewModel.mythBuster.observe(viewLifecycleOwner, Observer {

//            val listToArray = it.toTypedArray()
            binding.cardViewMyth.setOnClickListener {view ->
//                Log.d(TAG, "arrayToList = ${listToArray.toList()}")
//                Toast.makeText(context, "${it}", Toast.LENGTH_LONG).show()
                val action = HomeFragmentDirections.actionHomeFragmentToMythFragment(it.toTypedArray())
                view.findNavController().navigate(action)
            }
        })
        return binding.root
    }

    private fun setActionBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    companion object {

        private val TAG = HomeFragment::class.java.simpleName
    }

}
