package relawan.covidmonitor.view.myth

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import relawan.covidmonitor.databinding.ListMythBinding
import relawan.covidmonitor.model.Myth

class MythAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<MythAdapter.ViewHolder>() {

    var data = listOf<Myth>()
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

        holder.bind(item)
        holder.itemView.setOnClickListener {

            onClickListener.onClick(item)
            // Get the current state of the item
            item.expanded = !item.expanded
            Log.d(TAG, "holder = ${item.expanded}")
            notifyItemChanged(position)

        }
    }

    class ViewHolder(val binding: ListMythBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Myth) {
            binding.containerDetail.visibility = if (item.expanded) View.VISIBLE else View.GONE
            Log.d(TAG, "bind = ${item.expanded}")
            binding.mythTitle.text = item.title
            binding.mythDetail.text = item.detail

        }
        companion object {
            // binding viewHolder
            fun from (parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ListMythBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

        }
    }

    // click listener
    class OnClickListener(val clickListener: (myth: Myth) -> Unit) {
        fun onClick(myth: Myth) = clickListener(myth)
    }

    companion object {
        private val TAG = MythAdapter::class.java.simpleName
    }
}