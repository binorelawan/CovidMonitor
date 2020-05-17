package relawan.covidmonitor.view.splash

import android.content.Context
import android.content.DialogInterface
import android.icu.util.TimeUnit
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

import relawan.covidmonitor.databinding.FragmentSplashBinding
import relawan.covidmonitor.model.Global
import relawan.covidmonitor.model.Indonesia
import relawan.covidmonitor.repository.Repository
import kotlin.concurrent.thread
import kotlin.system.exitProcess

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    private lateinit var splashViewModel: SplashViewModel

    private val repository = Repository()

    var indonesiaData: Indonesia? = null
    var globalData: Global? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSplashBinding.inflate(inflater)

        // lifeCycleOwner
        binding.lifecycleOwner = this

        val viewModelFactory = SplashModelFactory(repository)

        splashViewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        
        if(isWorkingInternetPersent()){
            // test()
            dataModel()
        }
        else {
            showAlertDialog();
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

    }


    @Suppress("DEPRECATION")
    fun isWorkingInternetPersent(): Boolean {
        var result = false
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("No Internet Connection")
        builder.setMessage("Anda Ingin Tutup Aplikasi ?")
        builder.setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { _, _ ->
            activity?.finish()
            exitProcess(0)
        })
        builder.setNegativeButton("Refresh", DialogInterface.OnClickListener { dialogInterface, _ ->
            dialogInterface.dismiss()
            if (isWorkingInternetPersent()) {
                dataModel()
            } else {
                showAlertDialog()
            }
        })
        builder.setCancelable(false)
        builder.show()
    }

    private fun dataModel() {
        splashViewModel.globalData.observe(viewLifecycleOwner, Observer { global ->
            Log.d(TAG, global.affectedCountries.toString())
            globalData = global


            splashViewModel.indonesiaData.observe(viewLifecycleOwner, Observer { indonesia ->
                Log.d(TAG, indonesia.country!!)
                indonesiaData = indonesia
            })

            CoroutineScope(Dispatchers.Main).launch {
                delay(1000L)
                withContext(Dispatchers.Main) {
                    Log.d("TAG", "this will be called after 3 seconds")
                    Log.d(
                        TAG,
                        "indonesiaData = ${indonesiaData?.country} globalData = ${globalData?.affectedCountries}"
                    )
                    if (indonesiaData != null && globalData != null) {
                        Log.d(
                            TAG,
                            "indonesiaData = ${indonesiaData?.country} globalData = ${globalData?.affectedCountries}"
                        )
                        val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment(
                            indonesiaData,
                            globalData
                        )
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    companion object {
        private val TAG = SplashFragment::class.java.simpleName
    }

}
