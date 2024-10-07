package ru.mirea.wholesalestore.app

import android.app.Application
import com.google.firebase.FirebaseApp
import ru.mirea.wholesalestore.di.appModule
import ru.mirea.wholesalestore.di.dataModule
import ru.mirea.wholesalestore.di.domainModule
import ru.mirea.wholesalestore.di.featuresModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(applicationContext)
        FirebaseApp.getInstance().apply {
            if (options.apiKey.isEmpty()) {
                throw RuntimeException("Firebase API key is missing. Check your google-services.json file.")
            }
        }

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                dataModule,
                domainModule,
                featuresModule
            )
        }
    }
}