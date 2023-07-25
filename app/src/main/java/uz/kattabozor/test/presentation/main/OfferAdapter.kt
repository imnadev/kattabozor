package uz.kattabozor.test.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kattabozor.test.databinding.ItemOfferBinding
import uz.kattabozor.test.domain.model.offer.Offer

class OfferAdapter : ListAdapter<Offer, OfferAdapter.OfferViewHolder>(OfferDiffUtil) {

    class OfferViewHolder(private val binding: ItemOfferBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(offer: Offer) = with(binding) {
            Glide.with(root).load(offer.image.url).into(image)
            name.text = offer.name
            brand.text = offer.brand
            category.text = offer.category
            merchant.text = offer.merchant
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OfferViewHolder(
        ItemOfferBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object OfferDiffUtil : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean =
            oldItem == newItem
    }
}