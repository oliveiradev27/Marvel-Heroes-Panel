package br.espartano.marvelheroescatalog.runners

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import br.espartano.marvelheroescatalog.app.TestApplication
import dagger.hilt.android.testing.HiltTestApplication

class TestAppJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
