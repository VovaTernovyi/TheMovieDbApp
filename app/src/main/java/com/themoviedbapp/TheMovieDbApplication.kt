package com.themoviedbapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.themoviedbapp.di.modules.*
import com.themoviedbapp.extension.onNotReleaseBuild
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TheMovieDbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDependencyInjector()
        initMonitoring()
    }

    private fun initDependencyInjector() {
        startKoin {
            androidContext(this@TheMovieDbApplication)
            modules(
                KoinApiModule,
                KoinDatabaseModule,
                KoinRepositoriesModule,
                KoinArchitectureComponentViewModels,
                KoinOtherModule
            )
        }
    }

    private fun initMonitoring() =
        onNotReleaseBuild {
            Stetho.initializeWithDefaults(this)
        }

    companion object {
        lateinit var instance: TheMovieDbApplication
            private set
    }
}