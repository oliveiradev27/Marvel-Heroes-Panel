package br.espartano.marvelheroescatalog.app

import android.app.Application
import br.espartano.marvelheroescatalog.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            androidFileProperties()
            modules(appModules)
        }
    }
}