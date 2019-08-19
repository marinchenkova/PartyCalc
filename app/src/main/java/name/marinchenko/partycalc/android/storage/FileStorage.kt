package name.marinchenko.partycalc.android.storage

import android.content.Context
import java.io.File


const val FILE_SESSION_IDS = "session_ids"


private fun Context.getFile(name: String, create: Boolean): File? {
    val file = File(filesDir, name)
    if (create) file.createNewFile()
    if (!file.exists()) return null
    return file
}

fun Context.readFile(name: String) = getFile(name, false)?.readText() ?: ""
fun Context.writeFile(name: String, data: String?) = getFile(name, true)?.writeText(data ?: "")
fun Context.removeFile(name: String) = getFile(name, false)?.delete()

fun Context.readSession(id: Long) = readFile(id.toString())
fun Context.writeSession(id: Long, data: String?) = writeFile(id.toString(), data)
fun Context.removeSession(id: Long) = removeFile(id.toString())
