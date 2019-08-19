package name.marinchenko.partycalc.android.storage


interface ISessionRepo {

    fun defaultSessionTitle(): String

    fun createEmptySession(title: String = ""): Session

    fun getSession(id: Long): Session?

    fun saveSession(session: Session)

    fun deleteSession(id: Long)

    fun saveSessionId(id: Long)

    fun getAllSessionIds(): List<Long>

    fun saveAllSessionIds(list: List<Long>)

    fun sessionCount(): Int
}