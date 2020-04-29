package relawan.covidmonitor.view.country

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.list_country.*
import relawan.covidmonitor.R

import relawan.covidmonitor.databinding.FragmentCountryBinding
import relawan.covidmonitor.repository.Repository

/**
 * A simple [Fragment] subclass.
 */
class CountryFragment : Fragment() {

    private lateinit var countryViewModel: CountryViewModel

    private val repository = Repository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentCountryBinding.inflate(inflater)

        // lifeCycleOwner
        binding.lifecycleOwner = this

        setActionBarTitle(getString(R.string.global_title))

        val viewModelFactory = CountryModelFactory(repository)

        countryViewModel = ViewModelProvider(this, viewModelFactory).get(CountryViewModel::class.java)


        val adapter = CountryAdapter(CountryAdapter.OnClickListener {country ->
            val action = CountryFragmentDirections.actionCountryFragmentToDetailFragment(country)
            findNavController().navigate(action)
        })

        binding.countryList.adapter = adapter

        countryViewModel.countryData.observe(viewLifecycleOwner, Observer {country ->
            if (country != null) {
                binding.progressCountry.visibility = View.GONE
                adapter.data = country
            } else {
                binding.progressCountry.visibility = View.GONE
                binding.errorCountry.visibility = View.VISIBLE
            }

        })

        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId) {
            R.id.action_search -> {
                // navigate to SearchTeamFragment
                view?.findNavController()?.navigate(R.id.action_countryFragment_to_searchFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }


    }


}
