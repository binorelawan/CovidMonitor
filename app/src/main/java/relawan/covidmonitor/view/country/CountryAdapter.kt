package relawan.covidmonitor.view.country

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_country.*
import kotlinx.android.synthetic.main.list_country.view.*
import relawan.covidmonitor.databinding.ListCountryBinding
import relawan.covidmonitor.model.Country
import relawan.covidmonitor.model.CountryItem
import java.util.*
import kotlin.collections.ArrayList

class CountryAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(){



    var data = listOf<Country>()
        get() {
            return field.sortedByDescending {
                it.cases
            }
        }
        set(value) {
            field = value
            notifyDataSetChanged()
        }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "${data.size}")
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }

        holder.bind(item)

    }

    class ViewHolder(val binding: ListCountryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Country) {

            binding.countryText.text = item.country
            binding.confirmText.text = item.cases.toString()
            binding.deathText.text = item.deaths.toString()
            binding.recoverText.text = item.recovered.toString()
        }


        companion object {
            // binding viewHolder
            fun from (parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ListCountryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

        }
    }

    // click listener
    class OnClickListener(val clickListener: (country: Country) -> Unit) {
        fun onClick(country: Country) = clickListener(country)
    }


    companion object {
        private val TAG = CountryAdapter::class.java.simpleName
    }

}