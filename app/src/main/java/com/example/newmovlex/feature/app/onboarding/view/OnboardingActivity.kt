package com.example.newmovlex.feature.app.onboarding.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.example.newmovlex.R
import com.example.newmovlex.feature.app.onboarding.adapters.OnboardingItemsAdapter
import com.example.newmovlex.feature.user.signup.view.SignupActivity
import com.example.newmovlex.ui.models.OnboardingItem
import com.example.newmovlex.utils.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {
    private lateinit var adapter: OnboardingItemsAdapter
    private lateinit var OnBoardingViewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setFlags()
        setEventListener()
        initOnBoardingAdapter()
    }

    private fun setFlags() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initOnBoardingAdapter()
    }

    private fun setEventListener() {
        btn_getStarted.setOnClickListener {
            SharedPrefManager.setFirstOpen(true)
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }

    private fun initOnBoardingAdapter() {
        val onBoardingData = ArrayList<OnboardingItem>();
        onBoardingData.add(
            OnboardingItem(
                onboardingImage = R.drawable.onboarding_img1,
                description = getString(R.string.get_first_movie)
            )
        )
        onBoardingData.add(
            OnboardingItem(
                onboardingImage = R.drawable.onboarding_img2,
                description = getString(R.string.know_the_movie)
            )
        )
        onBoardingData.add(
            OnboardingItem(
                onboardingImage = R.drawable.onboarding_img3,
                description = getString(R.string.realtime_updates)
            )
        )
        // bind data source with adapter
        adapter = OnboardingItemsAdapter(onBoardingData)
         // init viewpager
        OnBoardingViewPager = onboarding_viewpager

        // set viewpager adapter
        OnBoardingViewPager.adapter = adapter

        // set indicator adapter
        indicator.setViewPager(OnBoardingViewPager)

        // set onBoarding event listener
        btn_next.setOnClickListener {
            when (OnBoardingViewPager.currentItem) {
                0 -> {
                    OnBoardingViewPager.setCurrentItem(onBoardingData.size - 2, true)
                }

                1 -> {
                    OnBoardingViewPager.setCurrentItem(onBoardingData.size - 1, true)
                }
                2 -> {
                    OnBoardingViewPager.setCurrentItem(onBoardingData.size, true)

                }
            }
        }
        // set onPage scroll state
        CheckOnPageScrollState()
    }

    private fun CheckOnPageScrollState() {
        this.OnBoardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {


                when (position) {
                    0 -> {
                        btn_next.visibility = View.VISIBLE
                        btn_getStarted.visibility = View.INVISIBLE
                    }
                    1 -> {
                        btn_next.visibility = View.VISIBLE
                        btn_getStarted.visibility = View.INVISIBLE
                    }
                    else -> {
                        btn_next.visibility = View.INVISIBLE
                        btn_getStarted.visibility = View.VISIBLE
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

}