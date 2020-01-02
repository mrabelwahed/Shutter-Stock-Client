package com.garhy.shutterstock.utils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

import java.util.concurrent.TimeUnit

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import java.util.concurrent.Executor


class RxImmediateSchedulerRule : TestRule {

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
            // this prevents StackOverflowErrors when scheduling with a delay
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
        }
    }


    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                println("rx immediate scheduler rule - before evaluation")
                RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
                RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
                try {
                    base.evaluate()
                } finally {
                    println("rx immediate scheduler rule - after evaluation")
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
