package relawan.covidmonitor.view.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import relawan.covidmonitor.R
import relawan.covidmonitor.databinding.FragmentSearchBinding
import relawan.covidmonitor.repository.Repository

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    lateinit var searchViewModel: SearchViewModel

    private val repository = Repository()

    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    private lateinit var cardView: CardView
    private lateinit var countryText: TextView
    private lateinit var confirmText: TextView
    private lateinit var deathText: TextView
    private lateinit var recoverText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchBinding.inflate(inflater)

        // lifeCycleOwner
        binding.lifecycleOwner = this

        searchView = binding.searchData
        progressBar = binding.progressSearch
        errorText = binding.errorSearch
        cardView = binding.cardView
        countryText = binding.countryText
        confirmText = binding.confirmText
        deathText = binding.deathText
        recoverText = binding.recoverText

        val viewModelFactory = SearchModelFactory(repository)

        searchViewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)


        initSearch()
        searchData()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun searchData() {

        searchViewModel.searchData.observe(viewLifecycleOwner, Observer {search ->
            if (search != null) {
                Log.d(TAG, search.country)

                countryText.text = search.country
                confirmText.text = search.cases.toString()
                deathText.text = search.deaths.toString()
                recoverText.text = search.recovered.toString()

                cardView.setOnClickListener {view ->
                    val extras = FragmentNavigatorExtras(view to "search_container")
                    val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(search)
                    findNavController().navigate(action, extras)
                }
                progressBar.visibility = View.GONE
                cardView.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                errorText.visibility = View.VISIBLE
            }
        })
    }

    private fun initSearch() {

        val searchManager = this.context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        searchView.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))

        // activate keyboard
        searchView.onActionViewExpanded()

        val imm = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()) {

                    searchViewModel.getSearchData(query)
                    progressBar.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    // close keyboard after click enter
                    searchView.clearFocus()

                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // close keyboard after click back up button
        searchView.clearFocus()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }


}
