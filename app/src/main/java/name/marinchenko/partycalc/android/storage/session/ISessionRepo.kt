package name.marinchenko.partycalc.android.storage.session


interface ISessionRepo {

    fun createEmptySession(title: String = ""): Session

    fun getSession(id: Long): Session?

    fun saveSession(session: Session)

    fun removeSession(id: Long)

    fun getAllSessions(): List<Session>

    fun saveAllSessions(list: List<Session>)

    fun deleteAllSessions()

}