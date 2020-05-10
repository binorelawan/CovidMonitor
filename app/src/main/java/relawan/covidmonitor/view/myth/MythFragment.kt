package relawan.covidmonitor.view.myth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import relawan.covidmonitor.R
import relawan.covidmonitor.databinding.FragmentMythBinding

/**
 * A simple [Fragment] subclass.
 */
class MythFragment : Fragment() {

    private lateinit var mythViewModel: MythViewModel

    lateinit var container: ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMythBinding.inflate(inflater)

        // lifeCycleOwner
        binding.lifecycleOwner = this

        val myth = arguments?.let { MythFragmentArgs.fromBundle(it).myth.toList() }

        val viewModelFactory = MythModelFactory(myth!!)

        mythViewModel = ViewModelProvider(this, viewModelFactory).get(MythViewModel::class.java)

        val adapter = MythAdapter(MythAdapter.OnClickListener {
            it.expanded
        })

        binding.mythList.adapter = adapter

        mythViewModel.mythList.observe(viewLifecycleOwner, Observer {list ->

            adapter.data = list
        })
        return binding.root
    }

}
