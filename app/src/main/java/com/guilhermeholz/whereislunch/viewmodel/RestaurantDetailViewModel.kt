package com.guilhermeholz.whereislunch.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableFloat
import android.support.annotation.VisibleForTesting
import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.utils.OpenForTesting
import com.guilhermeholz.whereislunch.utils.logError
import org.threeten.bp.LocalTime
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@OpenForTesting
class RestaurantDetailViewModel(val repository: RestaurantsRepository) : BaseObservable() {

    val title: ObservableField<String> = ObservableField()
    val image: ObservableField<String> = ObservableField()
    val address: ObservableField<String> = ObservableField()
    val phone: ObservableField<String> = ObservableField()
    val votingLabel: ObservableField<String> = ObservableField()
    val rating: ObservableFloat = ObservableFloat()
    val loading: ObservableBoolean = ObservableBoolean(true)
    val voting: ObservableBoolean = ObservableBoolean(false)
    val canVote: ObservableBoolean = ObservableBoolean(false)
    val isVotingClosed: ObservableBoolean = ObservableBoolean(false)

    private var restaurant: RestaurantDetail? = null

    fun loadRestaurant(id: String) {
        canVote.set(repository.canVote(id))
        repository.getRestaurant(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loading.set(false)
                    bind(it)
                }, {
                    loading.set(false)
                    logError(it)
                })
    }

    fun onClickVote() {
        voting.set(true)
        restaurant?.let {
            repository.vote(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        voting.set(false)
                        canVote.set(false)
                        bind(it)
                    }, {
                        voting.set(false)
                        logError(it)
                    })
        }
    }

    private fun bind(restaurant: RestaurantDetail) {
        title.set(restaurant.name)
        image.set(restaurant.image)
        address.set(restaurant.address)
        phone.set("${restaurant.phone.substring(0..2)} ${restaurant.phone.substring(2)}")
        rating.set(restaurant.rating)
        votingLabel.set("Vote (${restaurant.votes})")
        isVotingClosed.set(repository.isVotingClosed())
        this.restaurant = restaurant
    }
}