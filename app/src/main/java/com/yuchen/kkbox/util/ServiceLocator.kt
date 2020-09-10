package com.yuchen.kkbox.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.yuchen.kkbox.data.source.DefultKkboxRepository
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.data.source.remote.KkboxRemoteDataSource

object ServiceLocator {

    @Volatile
    var kkboxRepository: KkboxRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): KkboxRepository {
        synchronized(this) {
            return kkboxRepository ?: createKkboxRepository(context)
        }
    }

    private fun createKkboxRepository(context: Context): KkboxRepository {
        return DefultKkboxRepository(KkboxRemoteDataSource)
    }

}