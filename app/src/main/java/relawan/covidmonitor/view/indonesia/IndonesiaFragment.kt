package relawan.covidmonitor.view.indonesia

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_indonesia.*

import com.google.android.material.transition.MaterialContainerTransform

import relawan.covidmonitor.databinding.FragmentIndonesiaBinding

/**
 * A simple [Fragment] subclass.
 */
class IndonesiaFragment : Fragment(), OnMapReadyCallback {

    private lateinit var indonesiaViewModel: IndonesiaViewModel

    private lateinit var mMap: GoogleMap

    var lat = 0.0
    var long = 0.0

    var displayName = ""

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentIndonesiaBinding.inflate(inflater)

        // lifeCycleOwner
        binding.lifecycleOwner = this

        val indonesia = arguments?.let { IndonesiaFragmentArgs.fromBundle(it).indonesia }

        val viewModelFactory = IndonesiaModelFactory(indonesia)

        indonesiaViewModel = ViewModelProvider(this, viewModelFactory).get(IndonesiaViewModel::class.java)

        indonesiaViewModel.coordinate.observe(viewLifecycleOwner, Observer {coordinate ->
            Log.d(TAG, coordinate.toString())
            lat = coordinate[0]
            long = coordinate[1]
        })

        indonesiaViewModel.indonesiaData.observe(viewLifecycleOwner, Observer {
            displayName = it.country
            setActionBarTitle(it.country)

            binding.flagDetail.let {view ->
                Glide
                    .with(view.context)
                    .load(it.countryInfo.flag)
                    .apply(RequestOptions()).override(150, 100)
                    .into(binding.flagDetail)
            }
            binding.confirmTextIndo.text = it.cases.toString()
            binding.deathTextIndo.text = it.deaths.toString()
            binding.recoverTextIndo.text = it.recovered.toString()

            binding.activeCaseText.text = it.active.toString()
            binding.casePerMillionText.text = it.casesPerOneMillion.toString()
            binding.todayCaseText.text  = it.todayCases.toString()
            binding.todayDeathsText.text = it.todayDeaths.toString()
            binding.deathsPerMillionText.text = it.deathsPerOneMillion.toString()
            binding.criticalText.text = it.critical.toString()
            binding.testsText.text = it.tests.toString()
            binding.testsPerMillionText.text = it.testsPerOneMillion.toString()
            binding.continentText.text = it.continent
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transform = MaterialContainerTransform()
        transform.duration = 1000
        sharedElementEnterTransition = transform
    }

    private fun setActionBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap

            val coordinate = LatLng(lat, long)
            Log.d(TAG, "coordinate $lat $long")
            Log.d(TAG, displayName)

            val zoomLevel = 3.0f
            mMap.addMarker(MarkerOptions().position(coordinate).title(displayName))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, zoomLevel))
        }
    }

    companion object {
        private val TAG = IndonesiaFragment::class.java.simpleName
    }

}
