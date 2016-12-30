package com.guilhermeholz.whereislunch.viewmodel

import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.utils.RxJavaTestingUtils
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rx.Observable

class RestaurantDetailViewModelTest {

    @Mock
    lateinit var repository: RestaurantsRepository
    lateinit var viewModel: RestaurantDetailViewModel

    @Before
    fun setUp() {
        RxJavaTestingUtils.setupRxSchedulers()
        MockitoAnnotations.initMocks(this)
        viewModel = RestaurantDetailViewModel(repository)
    }

    @Test
    fun shouldBindOnSuccessfulRequest() {
        val detail = getRestaurantDetail()
        whenever(repository.getRestaurant(""))
                .thenReturn(Observable.just(detail))

        viewModel.loadRestaurant("")

        assertCorrectBinding(detail)
    }

    @Test
    fun shouldDisplayVotingClosedMessageIfClosed() {
        val detail = getRestaurantDetail()
        whenever(repository.getRestaurant(""))
                .thenReturn(Observable.just(detail))
        whenever(repository.isVotingClosed())
                .thenReturn(true)
        viewModel.loadRestaurant("")

        assert(viewModel.isVotingClosed.get())
    }

    @Test
    fun shouldNotDisplayVotingClosedMessageIfNotClosed() {
        val detail = getRestaurantDetail()
        whenever(repository.getRestaurant(""))
                .thenReturn(Observable.just(detail))
        whenever(repository.isVotingClosed())
                .thenReturn(false)
        viewModel.loadRestaurant("")

        assert(!viewModel.isVotingClosed.get())
    }

    @Test
    fun shouldVoteOnlyOnce() {
        val detail = getRestaurantDetail()
        val votedDetail = getRestaurantVotedDetail()
        whenever(repository.getRestaurant(""))
                .thenReturn(Observable.just(detail))
        whenever(repository.canVote(""))
                .thenReturn(true)
        whenever(repository.vote(detail))
                .thenReturn(Observable.just(votedDetail))

        viewModel.loadRestaurant("")
        assert(viewModel.canVote.get())

        viewModel.onClickVote()
        assert(!viewModel.canVote.get())
    }

    @Test
    fun shouldRebindAfterVote() {
        val detail = getRestaurantDetail()
        val votedDetail = getRestaurantVotedDetail()
        whenever(repository.getRestaurant(""))
                .thenReturn(Observable.just(detail))
        whenever(repository.canVote(""))
                .thenReturn(true)
        whenever(repository.vote(detail))
                .thenReturn(Observable.just(votedDetail))

        viewModel.loadRestaurant("")
        viewModel.onClickVote()

        assertCorrectBinding(votedDetail)
    }

    private fun assertCorrectBinding(detail: RestaurantDetail) {
        assert(viewModel.title.get() == detail.name)
        assert(viewModel.image.get() == detail.image)
        assert(viewModel.address.get() == detail.address)
        assert(viewModel.rating.get() == detail.rating)
    }

    private fun getRestaurantDetail(): RestaurantDetail {
        return RestaurantDetail("id", "name", "+555193068790", "image", 4.5f, "address", 3)
    }

    private fun getRestaurantVotedDetail(): RestaurantDetail {
        return RestaurantDetail("id", "name", "+555193068790", "image", 4.5f, "address", 4)
    }

    @After
    fun tearDown() {
        RxJavaTestingUtils.resetSchedulers()
    }
}