package capps.jotpad.others

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NoteSaveWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        return try {
//            saveNote()

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}