package name.marinchenko.partycalc.android.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.formatDayMonthYear
import name.marinchenko.partycalc.android.util.now
import name.marinchenko.partycalc.core.randomExcept

class SessionRepo(private val ctx: Context): ISessionRepo {

    private fun usedIds() = getAllSessionIds().toSet()

    override fun defaultSessionTitle(): String = ctx.getString(R.string.session_default_name)

    override fun createEmptySession(title: String): Session {
        val now = now()
        return Session(
                randomExcept(usedIds()),
                title,
                "${defaultSessionTitle()} ${now.formatDayMonthYear()}",
                now,
                emptyList(),
                emptyList()
        )
    }

    override fun getSession(id: Long): Session? {
        val json = ctx.readSession(id)
        return Gson().fromJson<Session>(json, object : TypeToken<Session>(){}.type)
    }

    override fun saveSession(session: Session) {
        val json = Gson().toJson(session)
        ctx.writeSession(session.id, json)
    }

    override fun deleteSession(id: Long) {
        ctx.removeSession(id)
    }

    override fun saveSessionId(id: Long) {
        val list = mutableListOf<Long>()
        list.addAll(getAllSessionIds())
        list.add(id)
        saveAllSessionIds(list)
    }

    override fun getAllSessionIds(): List<Long> {
        val json = ctx.readFile(FILE_SESSION_IDS)
        return Gson().fromJson<List<Long>>(
                json,
                object : TypeToken<List<Long>>(){}.type
        ) ?: emptyList()
    }

    override fun saveAllSessionIds(list: List<Long>) {
        val json = Gson().toJson(list)
        ctx.writeFile(FILE_SESSION_IDS, json)
    }

    override fun sessionCount() = getAllSessionIds().size
}
