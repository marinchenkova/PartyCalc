package name.marinchenko.partycalc.android.session

class SessionRepo {

    fun getSession(id: SessionId): Session {
        return Session(id, "New Session", "New Label", emptyList(), emptyList())
    }

}