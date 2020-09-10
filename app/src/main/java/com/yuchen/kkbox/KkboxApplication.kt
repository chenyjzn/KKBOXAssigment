package com.yuchen.kkbox

import android.app.Application
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.util.ServiceLocator
import kotlin.properties.Delegates

class KkboxApplication : Application() {

    val kkboxRepository: KkboxRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: KkboxApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
