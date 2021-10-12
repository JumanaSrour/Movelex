package com.example.newmovlex.feature.app.onboarding.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newmovlex.R
import com.example.newmovlex.ui.models.OnboardingItem
import kotlinx.android.synthetic.main.onboarding_item_container.view.*

class OnboardingItemsAdapter(private val onBoardingItems: List<OnboardingItem>):
    RecyclerView.Adapter<OnboardingItemsAdapter.OnboardingItemViewHolder>(){

    inner class OnboardingItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val imageOnboarding = view.imageOnboarding
        private val desc = view.textDescription

        fun bind(onboardingItem: OnboardingItem){
            imageOnboarding.setImageResource(onboardingItem.onboardingImage)
            desc.text = onboardingItem.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        return OnboardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount() = onBoardingItems.size
}