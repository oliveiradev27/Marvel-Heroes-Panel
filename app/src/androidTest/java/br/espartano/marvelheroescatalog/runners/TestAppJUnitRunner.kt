package br.espartano.marvelheroescatalog.runners

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import br.espartano.marvelheroescatalog.app.TestApplication

class TestAppJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}
