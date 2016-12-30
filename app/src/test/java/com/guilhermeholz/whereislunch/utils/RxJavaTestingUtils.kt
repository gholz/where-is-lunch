package com.guilhermeholz.whereislunch.utils

import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaHooks
import rx.schedulers.Schedulers

class RxJavaTestingUtils {
    companion object {
        @JvmStatic
        fun setupRxSchedulers() {
            RxJavaHooks.setOnIOScheduler { Schedulers.immediate() }
            RxAndroidPlugins.getInstance()
                    .registerSchedulersHook(object : RxAndroidSchedulersHook() {
                        override fun getMainThreadScheduler(): Scheduler {
                            return Schedulers.immediate()
                        }
                    })
        }

        @JvmStatic
        fun resetSchedulers() {
            RxJavaHooks.reset()
            RxAndroidPlugins.getInstance().reset()
        }
    }
}