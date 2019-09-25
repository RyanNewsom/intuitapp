package com.ryannewsom.intuitinterviewapp

import android.app.Application
import timber.log.Timber.DebugTree
import timber.log.Timber

class InterviewAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }
}