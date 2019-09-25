package com.ryannewsom.intuitinterviewapp.util

import io.reactivex.Scheduler

/**
 * Provides various schedulers for Rx
 */
interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
}