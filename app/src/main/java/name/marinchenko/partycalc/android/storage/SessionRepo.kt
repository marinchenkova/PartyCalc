package name.marinchenko.partycalc.android.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.util.formatDayMonthYear
import name.marinchenko.partycalc.android.util.now
import name.marinchenko.partycalc.core.item.Result
import name.marinchenko.partycalc.core.randomExcept

class SessionRepo(private val ctx: Context): ISessionRepo {

    private fun usedIds() = getAllSessionIds().toSet()

    private fun defaultSessionTitle(): String = ctx.getString(R.string.session_default_name)

    override fun createEmptySession(title: String): Session {
        val now = now()
        return Session(
                randomExcept(usedIds()),
                title,
                "${defaultSessionTitle()} ${now.formatDayMonthYear()}",
                now,
                emptyList(),
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
        saveSessionId(session.id)
    }

    override fun removeSession(id: Long) {
        ctx.removeSession(id)
        removeSessionId(id)
    }

    private fun saveSessionId(id: Long) {
        val set = mutableSetOf<Long>()
        set.addAll(getAllSessionIds())
        set.add(id)
        saveAllSessionIds(set)
    }

    private fun removeSessionId(id: Long) {
        val set = mutableSetOf<Long>()
        set.addAll(getAllSessionIds())
        set.remove(id)
        saveAllSessionIds(set)
    }

    private fun getAllSessionIds(): Set<Long> {
        val json = ctx.readFile(FILE_SESSION_IDS)
        return Gson().fromJson<Set<Long>>(
                json,
                object : TypeToken<Set<Long>>(){}.type
        ) ?: emptySet()
    }

    private fun saveAllSessionIds(set: Set<Long>) {
        val json = Gson().toJson(set)
        ctx.writeFile(FILE_SESSION_IDS, json)
    }

    override fun getAllSessions(): List<Session> = getAllSessionIds()
            .mapNotNull { getSession(it) }

    override fun saveAllSessions(list: List<Session>) {
        list.forEach { saveSession(it) }
    }

    override fun deleteAllSessions() {
        getAllSessionIds().forEach { removeSession(it) }
    }

    override fun sessionCount() = getAllSessionIds().size
}
