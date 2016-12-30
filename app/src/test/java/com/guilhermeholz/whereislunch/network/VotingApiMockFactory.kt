package com.guilhermeholz.whereislunch.network

import com.guilhermeholz.whereislunch.network.model.votes.Vote
import com.guilhermeholz.whereislunch.network.model.votes.VotesResponse
import com.guilhermeholz.whereislunch.utils.OpenForTesting
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import rx.Observable

@OpenForTesting
class VotingApiMockFactory {
    companion object {
        fun getApi(): VotingApi {
            return mock {
                on {
                    it.getVotes(any())
                } doReturn (Observable.just(getVotesResponse(10)))

                on {
                    it.getVotesById(any(), any())
                } doReturn (Observable.just(getVote(0)))

                on {
                    it.getWinner(any())
                } doReturn (Observable.just(getVote(0)))

                on {
                    it.getWinners()
                } doReturn (Observable.just(getVotesResponse(7)))

                on {
                    it.vote(any(), any())
                } doReturn (Observable.just(getVote(1)))
            }
        }

        private fun getVotesResponse(size: Int): VotesResponse {
            return VotesResponse(getVotes(size))
        }

        private fun getVotes(size: Int): List<Vote> {
            return (0..size).map { getVote(it) }
        }

        private fun getVote(index: Int): Vote {
            return Vote("id_$index", index)
        }
    }
}
