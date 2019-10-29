package br.espartano.marvelheroescatalog.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun computation() : Scheduler
    fun io() : Scheduler
    fun ui() : Scheduler
}

class BaseSchedulerProvider: SchedulerProvider {
    override fun computation() = Schedulers.computation()
    override fun io() = Schedulers.io()
    override fun ui() = AndroidSchedulers.mainThread()
}

class TrampolineSchedulerProvider: SchedulerProvider {
    override fun computation() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
}

class TestSchedulerProvider(private val scheduler: Scheduler) : SchedulerProvider {
    override fun computation() = scheduler
    override fun io() = scheduler
    override fun ui() = scheduler
}